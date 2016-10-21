package com.jkb.core.presenter.function.index.dynamic;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.data.dynamic.dynamic.CircleData;
import com.jkb.core.data.dynamic.dynamic.CircleInCommonUseData;
import com.jkb.core.data.dynamic.dynamic.DynamicBaseData;
import com.jkb.core.data.dynamic.dynamic.DynamicData;
import com.jkb.core.contract.function.index.DynamicContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.function.index.dynamic.DynamicDataRepository;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 动态列表的P层
 * Created by JustKiddingBaby on 2016/8/23.
 */

public class DynamicPresenter implements DynamicContract.Presenter {

    private static final String TAG = "DynamicPresenter";
    private DynamicContract.View view;
    private DynamicDataRepository resRepository;

    //data
    private List<DynamicBaseData> dynamicDatas;
    private boolean isCached = false;
    //分页
    private PageControlEntity pageControl;
    private int action = ACTION_REFRESH;
    private static final int ACTION_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;
    private boolean isLoading = false;//正在加载

    public DynamicPresenter(
            @NonNull DynamicContract.View view, @NonNull DynamicDataRepository responsitory) {
        this.view = view;
        this.resRepository = responsitory;

        this.view.setPresenter(this);

        pageControl = new PageControlEntity();
        dynamicDatas = new ArrayList<>();
    }

    @Override
    public void start() {
        initDynamicData();
    }

    @Override
    public void onRefresh() {
        Log.d(TAG, "onRefresh");
        if (isLoading) {
            return;
        }
        view.showRefreshingView();//设置刷新动画
        action = ACTION_REFRESH;
        pageControl.setCurrent_page(1);
        reqDynamicListData();
    }

    @Override
    public void onLoadMore() {
//        Log.d(TAG, "onLoadMore");
        if (isLoading) {
            return;
        }
        action = ACTION_LOADMORE;
        if (pageControl.getCurrent_page() >= pageControl.getLast_page()) {
            return;
        }
        //设置当前页数+1
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqDynamicListData();
    }

    @Override
    public void initDynamicData() {
        //判断是否登录
        if (!LoginContext.getInstance().isLogined()) {
            view.showUnLoginView();
            return;
        }
        //判断是否有缓存数据
        if (isCached) {
            bindDataToView();
            return;
        }
        onRefresh();
    }

    @Override
    public void likeDynamic(final int position) {
        //请求喜欢的接口
        UserAuths userAuths = getUserAuths();
        if (userAuths == null) {
            return;
        }
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        //得到用户id
        int user_id = userAuths.getUser_id();
        //得到目标id
        DynamicBaseData dynamicBaseData = dynamicDatas.get(position);
        int target_id = dynamicBaseData.getTarget_id();
        resRepository.favorite(Authorization, user_id, target_id,
                new ApiCallback<ApiResponse<OperationActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                        if (view.isActive()) {
                            DynamicBaseData dynamic = dynamicDatas.get(position);
                            if (dynamic instanceof DynamicData) {
                                DynamicData dynamicData = (DynamicData) dynamic;
                                dynamicData.setHasFavorite(!dynamicData.isHasFavorite());
                                //设置数目刷新
                                if (dynamicData.isHasFavorite()) {
                                    dynamicData.setOperation_count(dynamicData.getOperation_count() + 1);
                                } else {
                                    dynamicData.setOperation_count(dynamicData.getOperation_count() - 1);
                                }
                            } else {
                                CircleInCommonUseData circleInCommonUseData
                                        = (CircleInCommonUseData) dynamic;
                                circleInCommonUseData.getDynamic().setHasFavorite(
                                        !circleInCommonUseData.getDynamic().isHasFavorite());
                                //设置数目刷新
                                if (circleInCommonUseData.getDynamic().isHasFavorite()) {
                                    circleInCommonUseData.getDynamic().setOperation_count(
                                            circleInCommonUseData.getDynamic().getOperation_count() + 1
                                    );
                                } else {
                                    circleInCommonUseData.getDynamic().setOperation_count(
                                            circleInCommonUseData.getDynamic().getOperation_count() - 1
                                    );
                                }
                            }
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(
                            Response<ApiResponse<OperationActionEntity>> response,
                            String error, ApiResponse<OperationActionEntity> apiResponse) {
                        if (view.isActive()) {
                            view.showReqResult("操作失败");
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            view.showReqResult("操作失败");
                        }
                    }
                });
    }

    @Override
    public void bindDataToView() {
        if (!view.isActive()) {
            return;
        }
        filterDynamicData();
        view.setDynamicDataToView(dynamicDatas);
    }

    @Override
    public int getCreator_id(int position) {
        return dynamicDatas.get(position).getCreator_id();
    }

    @Override
    public int getOriginator_user_id(int position) {
        int id;
        DynamicBaseData dynamicBaseData = dynamicDatas.get(position);
        switch (dynamicBaseData.getTarget_type()) {
            case Config.TARGET_TYPE_DYNAMIC:
                id = ((DynamicData) dynamicBaseData).getOriginator().getOriginator_id();
                break;
            case Config.TARGET_TYPE_CIRCLEINCOMMONUSE:
                id = ((CircleInCommonUseData) dynamicBaseData).getDynamic().getUser().getId();
                break;
            default:
                id = -1;
                break;
        }
        return id;
    }

    @Override
    public int getCircleId(int position) {
        int id;
        DynamicBaseData dynamicBaseData = dynamicDatas.get(position);
        switch (dynamicBaseData.getTarget_type()) {
            case Config.TARGET_TYPE_CIRCLE:
                id = dynamicBaseData.getTarget_id();
                break;
            case Config.TARGET_TYPE_CIRCLEINCOMMONUSE:
                id = ((CircleInCommonUseData) dynamicBaseData).getDynamic().getCircle().getId();
                break;
            default:
                id = -1;
                break;
        }
        return id;
    }

    @Override
    public void startDynamicDetailView(int position) {
        if (!view.isActive()) {
            return;
        }
        int dynamic_id;
        String dynamic_type;
        DynamicBaseData dynamicBaseData = dynamicDatas.get(position);
        switch (dynamicBaseData.getTarget_type()) {//判断是否动态
            case Config.TARGET_TYPE_DYNAMIC:
                dynamic_id = dynamicBaseData.getTarget_id();
                break;
            default:
                dynamic_id = -1;
                break;
        }
        if (dynamic_id == -1) {
            view.showReqResult("该动态不存在");
            return;
        }
        String dType = ((DynamicData) dynamicBaseData).getDtype();
        switch (dType) {
            case Config.D_TYPE_ARTICLE:
                dynamic_type = Config.D_TYPE_ARTICLE;
                break;
            case Config.D_TYPE_NORMAL:
                dynamic_type = Config.D_TYPE_NORMAL;
                break;
            case Config.D_TYPE_TOPIC:
                dynamic_type = Config.D_TYPE_TOPIC;
                break;
            default:
                dynamic_type = null;
                break;
        }
        if (StringUtils.isEmpty(dynamic_type)) {
            view.showReqResult("该动态不存在");
            return;
        }
        view.startDynamicActivity(dynamic_id, dynamic_type);
    }

    @Override
    public void onCommentClick(int position) {
        DynamicBaseData dynamicBaseData = dynamicDatas.get(position);
        int dynamic_id;
        switch (dynamicBaseData.getTarget_type()) {//判断是否动态
            case Config.TARGET_TYPE_DYNAMIC:
                dynamic_id = dynamicBaseData.getTarget_id();
                break;
            default:
                dynamic_id = -1;
                break;
        }
        view.startCommentActivity(dynamic_id);
    }

    @Override
    public void setCacheExpired() {
        isCached = false;
    }

    /**
     * 得到动态数据
     */
    public void reqDynamicListData() {
        isLoading = true;
        if (!LoginContext.getInstance().isLogined()) {
            return;
        }
        UserAuths auths = getUserAuths();
        if (auths == null) {
            return;
        }
        String Authorization = Config.HEADER_BEARER + auths.getToken();
        resRepository.getAllDynamic(Authorization, pageControl.getCurrent_page(), dynamicApiCallback);
    }

    /**
     * 动态的数据回调接口
     */
    private ApiCallback<ApiResponse<DynamicListEntity>> dynamicApiCallback =
            new ApiCallback<ApiResponse<DynamicListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicListEntity>> response) {
                    if (view.isActive()) {
                        isLoading = false;
                        view.hideRefreshingView();
                        handleRespData(response.body());
                    }
                }

                /**
                 * 解析动态数据
                 */
                private void handleRespData(ApiResponse<DynamicListEntity> body) {
                    if (body == null) {
                        dynamicDatas.clear();
                        bindDataToView();
                        return;
                    }
                    DynamicListEntity entity = body.getMsg();
                    if (entity == null) {
                        dynamicDatas.clear();
                        bindDataToView();
                        return;
                    }
                    //判断是否添加数据或者清空数据
                    isCached = true;//设置为已缓存

                    //设置页码控制器
                    pageControl.setTotal(entity.getTotal());
                    pageControl.setPer_page(entity.getPer_page());
                    pageControl.setCurrent_page(entity.getCurrent_page());
                    pageControl.setLast_page(entity.getLast_page());
                    pageControl.setNext_page_url(entity.getNext_page_url());
                    pageControl.setPrev_page_url(entity.getPrev_page_url());
                    pageControl.setFrom(entity.getFrom());
                    pageControl.setTo(entity.getTo());

                    //处理数据
                    handleDynamicData(entity);

                    bindDataToView();
                }

                /**
                 * 处理动态数据
                 */
                private void handleDynamicData(DynamicListEntity entity) {
                    //判断操作动作
                    switch (action) {
                        case ACTION_REFRESH://刷新
                            dynamicDatas.clear();
                            break;
                        case ACTION_LOADMORE://加载
                            break;
                    }
                    //更新数据进去
                    List<DynamicListEntity.DataBean> dataBeen = entity.getData();
                    if (dataBeen == null) {
                        dynamicDatas.clear();
                        return;
                    }
                    for (int i = 0; i < dataBeen.size(); i++) {
                        //添加数据
                        DynamicListEntity.DataBean data = dataBeen.get(i);
                        //判断身份
                        String target_type = data.getTarget_type();
                        switch (target_type) {
                            case Config.TARGET_TYPE_CIRCLE:
                                dynamicDatas.add(changeToCircleData(data));
                                break;
                            case Config.TARGET_TYPE_DYNAMIC:
                                dynamicDatas.add(changeToDynamicData(data));
                                break;
                            case Config.TARGET_TYPE_CIRCLEINCOMMONUSE:
                                dynamicDatas.add(changeToCircleInCommonUseData(data));
                                break;
                        }
                    }
                }

                /**
                 * 转化为设置为常用圈子的动态数据
                 */
                private DynamicBaseData changeToCircleInCommonUseData(DynamicListEntity.DataBean data) {

                    CircleInCommonUseData circleInCommonUseData = new CircleInCommonUseData();
                    handleCommonData(circleInCommonUseData, data);

                    //设置动态数据
                    CircleInCommonUseData.DynamicBean dynamicBean =
                            new CircleInCommonUseData.DynamicBean();
                    DynamicListEntity.DataBean.DynamicBean bean = data.getDynamic();
                    dynamicBean.setId(bean.getId());
                    dynamicBean.setDtype(bean.getDtype());
                    dynamicBean.setTitle(bean.getTitle());
                    dynamicBean.setTag(bean.getTag());
                    dynamicBean.setCreated_at(bean.getCreated_at());
                    dynamicBean.setComments_count(bean.getComments_count());
                    dynamicBean.setOperation_count(bean.getOperation_count());
                    dynamicBean.setParticipation(bean.getParticipation());
                    dynamicBean.setHasFavorite(bean.isHasFavorite());

                    //设置圈子信息
                    DynamicListEntity.DataBean.DynamicBean.CircleBean circle =
                            bean.getCircle();
                    if (circle != null) {
                        CircleInCommonUseData.DynamicBean.CircleBean circleBean =
                                new CircleInCommonUseData.DynamicBean.CircleBean();
                        circleBean.setId(circle.getId());
                        circleBean.setName(circle.getName());
                        circleBean.setType(circle.getType());
                        circleBean.setPicture(circle.getPicture());
                        circleBean.setIntroduction(circle.getIntroduction());
                        circleBean.setLongitude(circle.getLongitude());
                        circleBean.setLatitude(circle.getLatitude());
                        circleBean.setCreated_at(circle.getCreated_at());
                        //设置圈子原创作者信息
                        DynamicListEntity.DataBean.DynamicBean.CircleBean.UserBean user = circle.getUser();
                        if (user != null) {
                            CircleInCommonUseData.DynamicBean.CircleBean.UserBean userBean =
                                    new CircleInCommonUseData.DynamicBean.CircleBean.UserBean();
                            userBean.setCircle_owner_avatar(user.getCircle_owner_avatar());
                            userBean.setCircle_owner_nickname(user.getCircle_owner_nickname());
                            userBean.setId(user.getId());

                            circleBean.setUser(userBean);
                        }
                        //设置圈子原创学校信息
                        DynamicListEntity.DataBean.DynamicBean.CircleBean.SchoolBean school =
                                circle.getSchool();
                        if (school != null) {
                            CircleInCommonUseData.DynamicBean.CircleBean.SchoolBean schoolBean =
                                    new CircleInCommonUseData.DynamicBean.CircleBean.SchoolBean();
                            schoolBean.setId(school.getId());
                            schoolBean.setBadge(school.getBadge());
                            schoolBean.setSname(school.getSname());

                            circleBean.setSchool(schoolBean);
                        }
                        dynamicBean.setCircle(circleBean);
                    } else {
                        //去掉这条数据
                    }
                    //设置为不是原创的
                    DynamicListEntity.DataBean.DynamicBean.ContentBean content = bean.getContent();
                    CircleInCommonUseData.DynamicBean.ContentBean contentBean
                            = new CircleInCommonUseData.DynamicBean.ContentBean();
                    //设置动态类型
                    switch (dynamicBean.getDtype()) {
                        case Config.D_TYPE_NORMAL:
                            handleCircleInCommonUseNormalContent(contentBean, content);
                            break;
                        case Config.D_TYPE_ARTICLE:
                            handleCircleInCommonUseArticleContent(contentBean, content);
                            break;
                        case Config.D_TYPE_TOPIC:
                            handleCircleInCommonUseTopicContent(contentBean, content);
                            break;
                    }
                    dynamicBean.setContent(contentBean);
                    //设置用户信息
                    DynamicListEntity.DataBean.DynamicBean.UserBean user = bean.getUser();
                    if (user != null) {
                        CircleInCommonUseData.DynamicBean.UserBean userBean =
                                new CircleInCommonUseData.DynamicBean.UserBean();
                        userBean.setAvatar(user.getAvatar());
                        userBean.setId(user.getId());
                        userBean.setNickname(user.getNickname());

                        dynamicBean.setUser(userBean);
                    }
                    circleInCommonUseData.setDynamic(dynamicBean);
                    return circleInCommonUseData;
                }

                /**
                 * 解析设为常用圈子数据：话题
                 */
                private void handleCircleInCommonUseTopicContent(
                        CircleInCommonUseData.DynamicBean.ContentBean contentBean,
                        DynamicListEntity.DataBean.DynamicBean.ContentBean content) {
                    if (content == null) {
                        return;
                    }
                    DynamicListEntity.DataBean.DynamicBean.ContentBean.TopicBean topicBean =
                            content.getTopic();
                    if (topicBean == null) {
                        return;
                    }
                    CircleInCommonUseData.DynamicBean.ContentBean.TopicBean topic =
                            new CircleInCommonUseData.DynamicBean.ContentBean.TopicBean();
                    topic.setImg(topicBean.getImg());
                    topic.setDoc(topicBean.getDoc());

                    contentBean.setTopic(topic);
                }

                /**
                 * 解析设为常用圈子数据：文章
                 */
                private void handleCircleInCommonUseArticleContent(
                        CircleInCommonUseData.DynamicBean.ContentBean contentBean,
                        DynamicListEntity.DataBean.DynamicBean.ContentBean content) {
                    if (content == null) {
                        return;
                    }
                    List<DynamicListEntity.DataBean.DynamicBean.ContentBean.ArticleBean> articleBeans =
                            content.getArticle();
                    if (articleBeans == null || articleBeans.size() == 0) {
                        return;
                    }
                    List<CircleInCommonUseData.DynamicBean.ContentBean.ArticleBean> articles
                            = new ArrayList<>();
                    for (int i = 0; i < articleBeans.size(); i++) {
                        CircleInCommonUseData.DynamicBean.ContentBean.ArticleBean article =
                                new CircleInCommonUseData.DynamicBean.ContentBean.ArticleBean();
                        article.setImg(articleBeans.get(i).getImg());
                        article.setDoc(articleBeans.get(i).getDoc());

                        articles.add(article);
                    }
                    contentBean.setArticle(articles);
                }

                /**
                 * 解析设为常用圈子数据：普通
                 */
                private void handleCircleInCommonUseNormalContent(
                        CircleInCommonUseData.DynamicBean.ContentBean contentBean,
                        DynamicListEntity.DataBean.DynamicBean.ContentBean content) {
                    if (content == null) {
                        return;
                    }
                    DynamicListEntity.DataBean.DynamicBean.ContentBean.NormalBean normalBean =
                            content.getNormal();
                    if (normalBean == null) {
                        return;
                    }
                    CircleInCommonUseData.DynamicBean.ContentBean.NormalBean normal =
                            new CircleInCommonUseData.DynamicBean.ContentBean.NormalBean();
                    normal.setImg(normalBean.getImg());
                    normal.setDoc(normalBean.getDoc());

                    contentBean.setNormal(normal);
                }

                /**
                 * 转换为可用到动态数据
                 */
                private DynamicBaseData changeToDynamicData(DynamicListEntity.DataBean data) {
                    DynamicData dynamicData = new DynamicData();
                    handleCommonData(dynamicData, data);
                    //设置具体的动态数据
                    DynamicListEntity.DataBean.DynamicBean bean = data.getDynamic();
                    dynamicData.setId(bean.getId());
                    dynamicData.setDtype(bean.getDtype());
                    dynamicData.setTitle(bean.getTitle());
                    dynamicData.setTag(bean.getTag());
                    dynamicData.setCreate_time(bean.getCreated_at());
                    //设置是否原创的状态
                    String action = dynamicData.getAction();
                    if (Config.ACTION_TYPE_POST.equals(action)) {
                        dynamicData.setIs_orginal(true);
                    } else {
                        dynamicData.setIs_orginal(false);
                    }
                    dynamicData.setComments_count(bean.getComments_count());
                    dynamicData.setOperation_count(bean.getOperation_count());
                    dynamicData.setParticipation(bean.getParticipation());
                    dynamicData.setHasFavorite(bean.isHasFavorite());
                    //判断是否原创
                    if (!dynamicData.is_orginal()) {
                        DynamicData.Originator originator = new DynamicData.Originator();
                        DynamicListEntity.DataBean.DynamicBean.UserBean userBean =
                                bean.getUser();
                        if (userBean != null) {
                            originator.setOriginator_avatar(userBean.getAvatar());
                            originator.setOriginator_id(userBean.getId());
                            originator.setOriginator_nickname(userBean.getNickname());

                            dynamicData.setOriginator(originator);
                        }
                    } else {
                        dynamicData.setOriginator(null);
                    }
                    //设置内容
                    handleDynamicContentData(dynamicData, bean);
                    return dynamicData;
                }

                /**
                 * 处理动态——内容数据
                 */
                private void handleDynamicContentData(DynamicData dynamicData,
                                                      DynamicListEntity.DataBean.DynamicBean bean) {
                    DynamicListEntity.DataBean.DynamicBean.ContentBean contentBean = bean.getContent();
                    if (contentBean == null) {
                        return;
                    }
                    switch (dynamicData.getDtype()) {
                        case Config.D_TYPE_TOPIC://设置话题数据
                            handleDynamicTopicData(dynamicData, contentBean);
                            break;
                        case Config.D_TYPE_ARTICLE://设置文章数据
                            handleDynamicArticleData(dynamicData, contentBean);
                            break;
                        case Config.D_TYPE_NORMAL://设置普通数据
                            handleDynamicNormalData(dynamicData, contentBean);
                            break;
                    }
                }

                /**
                 * 处理动态——内容——普通数据
                 */
                private void handleDynamicNormalData(
                        DynamicData dynamicData,
                        DynamicListEntity.DataBean.DynamicBean.ContentBean contentBean) {
                    //初始化话题数据
                    DynamicListEntity.DataBean.DynamicBean
                            .ContentBean.NormalBean normalBean = contentBean.getNormal();
                    if (normalBean == null) {
                        return;
                    }
                    DynamicData.Normal normal = new DynamicData.Normal();
                    normal.setDoc(normalBean.getDoc());
                    normal.setImg(normalBean.getImg());
                    dynamicData.setNormal(normal);
                }

                /**
                 * 处理动态——内容——话题数据
                 */
                private void handleDynamicTopicData(
                        DynamicData dynamicData,
                        DynamicListEntity.DataBean.DynamicBean.ContentBean contentBean) {
                    //初始化话题数据
                    DynamicListEntity.DataBean.DynamicBean
                            .ContentBean.TopicBean topicBean = contentBean.getTopic();
                    if (topicBean == null) {
                        return;
                    }
                    DynamicData.Topic topic = new DynamicData.Topic();
                    DynamicData.Topic.TopicBean topicBean1 = new DynamicData.Topic.TopicBean();
                    //设置数据
                    topicBean1.setDoc(topicBean.getDoc());
                    topicBean1.setImg(topicBean.getImg());
                    topic.setTopic(topicBean1);
                    dynamicData.setTopic(topic);
                }

                /**
                 * 处理动态——内容——文章数据
                 */
                private void handleDynamicArticleData(
                        DynamicData dynamicData,
                        DynamicListEntity.DataBean.DynamicBean.ContentBean contentBean) {
                    //初始化文章数据
                    List<DynamicListEntity.DataBean.DynamicBean
                            .ContentBean.ArticleBean> articleBean = contentBean.getArticle();
                    DynamicData.Article article = new DynamicData.Article();
                    List<DynamicData.Article.ArticleBean> articles = new ArrayList<>();
                    article.setArticle(articles);
                    //设置数据
                    if (articleBean == null) {
                        dynamicData.setArticle(article);
                        return;
                    }
                    for (int i = 0; i < articleBean.size(); i++) {
                        DynamicData.Article.ArticleBean art = new DynamicData.Article.ArticleBean();
                        DynamicListEntity.DataBean.DynamicBean
                                .ContentBean.ArticleBean artReq = articleBean.get(i);
                        art.setImg(artReq.getImg());
                        art.setDoc(artReq.getDoc());
                        articles.add(art);
                    }
                    dynamicData.setArticle(article);
                }

                /**
                 * 转换为可用的圈子数据
                 */
                private DynamicBaseData changeToCircleData(DynamicListEntity.DataBean data) {
                    CircleData circleData = new CircleData();
                    handleCommonData(circleData, data);
                    //设置具体的圈子数据
                    DynamicListEntity.DataBean.CircleBean circleBean = data.getCircle();
                    if (circleBean == null) {
                        return circleData;
                    }
                    //设置圈子数据
                    circleData.setId(circleBean.getId());
                    circleData.setName(circleBean.getName());
                    circleData.setType(circleBean.getType());
                    circleData.setPicture(circleBean.getPicture());
                    circleData.setIntroduction(circleBean.getIntroduction());
                    circleData.setLatitude(circleBean.getLatitude());
                    circleData.setLongitude(circleBean.getLongitude());
                    circleData.setCreate_time(circleBean.getCreated_at());
                    circleData.setDynamics_count(circleBean.getDynamics_count());
                    circleData.setOperation_count(circleBean.getOperation_count());
                    circleData.setHasSubscribe(circleBean.isHasSubscribe());
                    //设置作者信息-暂时不需要
                    //设置学校信息-暂时不需要
                    return circleData;
                }

                /**
                 * 处理公有的数据
                 */
                private void handleCommonData(DynamicBaseData dynamicData, DynamicListEntity.DataBean data) {
                    dynamicData.setTarget_type(data.getTarget_type());
                    dynamicData.setAction(data.getAction());
                    dynamicData.setActionTitle(data.getTitle());
                    dynamicData.setTarget_id(data.getTarget_id());
                    dynamicData.setCreated_at(data.getCreated_at());
                    dynamicData.setCreator_id(data.getCreator_id());
                    dynamicData.setCreator_nickname(data.getCreator_nickname());
                    dynamicData.setCreator_avatar(data.getCreator_avatar());
                }

                @Override
                public void onError(Response<ApiResponse<DynamicListEntity>> response,
                                    String error, ApiResponse<DynamicListEntity> apiResponse) {
                    if (view.isActive()) {
                        isLoading = false;
                        view.hideRefreshingView();
                        dynamicDatas.clear();
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        isLoading = false;
                        view.hideRefreshingView();
                        dynamicDatas.clear();
                        bindDataToView();
                    }
                }
            };

    /**
     * 筛选无用的数据
     */
    private void filterDynamicData() {
        if (dynamicDatas == null || dynamicDatas.size() == 0) {
            return;
        }
        for (int i = 0; i < dynamicDatas.size(); i++) {
            DynamicBaseData dynamicBaseData = dynamicDatas.get(i);
            if (dynamicBaseData == null) {
                dynamicDatas.remove(dynamicDatas);
                continue;
            }
            //筛选其他数据
            String target_type = dynamicBaseData.getTarget_type();
            switch (target_type) {
                case Config.TARGET_TYPE_CIRCLE:
                    filterDynamic_Circle(dynamicBaseData);
                    break;
                case Config.TARGET_TYPE_CIRCLEINCOMMONUSE:
                    filterDynamic_CircleInCommonUse(dynamicBaseData);
                    break;
                case Config.TARGET_TYPE_DYNAMIC:
                    filterDynamic_Dynamic(dynamicBaseData);
                    break;
            }
        }
    }

    /**
     * 筛选正常动态数据
     */
    private void filterDynamic_Dynamic(DynamicBaseData dynamicBaseData) {
        if (!(dynamicBaseData instanceof DynamicData)) {
            dynamicDatas.remove(dynamicBaseData);
            return;
        }
        DynamicData dynamicData = (DynamicData) dynamicBaseData;
        if (dynamicData == null) {
            dynamicDatas.remove(dynamicBaseData);
            return;
        }
        String dtype = dynamicData.getDtype();
        switch (dtype) {
            case Config.D_TYPE_ARTICLE:
                DynamicData.Article article = dynamicData.getArticle();
                if (article == null) {
                    dynamicDatas.remove(dynamicBaseData);
                    return;
                } else {
                    if (article.getArticle() == null) {
                        dynamicDatas.remove(dynamicBaseData);
                        return;
                    }
                }
                break;
            case Config.D_TYPE_NORMAL:
                DynamicData.Normal normal = dynamicData.getNormal();
                if (normal == null) {
                    dynamicDatas.remove(dynamicBaseData);
                    return;
                } else {

                }
                break;
            case Config.D_TYPE_TOPIC:
                DynamicData.Topic topic = dynamicData.getTopic();
                if (topic == null) {
                    dynamicDatas.remove(dynamicBaseData);
                    return;
                } else {
                    if (topic.getTopic() == null) {
                        dynamicDatas.remove(dynamicBaseData);
                        return;
                    }
                }
                break;
        }
    }

    /**
     * 筛选设置为常用圈子数据
     */
    private void filterDynamic_CircleInCommonUse(DynamicBaseData dynamicBaseData) {
        if (!(dynamicBaseData instanceof CircleInCommonUseData)) {
            dynamicDatas.remove(dynamicBaseData);
            return;
        }
        CircleInCommonUseData circleInCommonUseData = (CircleInCommonUseData) dynamicBaseData;
        CircleInCommonUseData.DynamicBean dynamic = circleInCommonUseData.getDynamic();
        if (dynamic == null) {
            dynamicDatas.remove(dynamicBaseData);
            return;
        }
        CircleInCommonUseData.DynamicBean.CircleBean circle = dynamic.getCircle();
        if (circle == null) {
            dynamicDatas.remove(dynamicBaseData);
            return;
        }
        CircleInCommonUseData.DynamicBean.ContentBean content = dynamic.getContent();
        if (content == null) {
            dynamicDatas.remove(dynamicBaseData);
            return;
        }
    }

    /**
     * 筛选动态：订阅圈子动态
     */
    private void filterDynamic_Circle(DynamicBaseData dynamicBaseData) {
        if (!(dynamicBaseData instanceof CircleData)) {
            dynamicDatas.remove(dynamicBaseData);
            return;
        }
        CircleData circleData = (CircleData) dynamicBaseData;
        if (circleData == null) {
            dynamicDatas.remove(dynamicBaseData);
            return;
        }
    }

    /**
     * 得到用户数据
     */
    private UserAuths getUserAuths() {
        UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
        UserAuths users = userInfo.getUserAuths();
        if (users == null) {
            LoginContext.getInstance().setUserState(new LogoutState());
            view.showReqResult("登录过期，请重新登录~");
        }
        return users;
    }
}
