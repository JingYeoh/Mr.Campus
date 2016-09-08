package com.jkb.core.presenter.function.index;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.core.contract.function.data.dynamic.CircleData;
import com.jkb.core.contract.function.data.dynamic.DynamicBaseData;
import com.jkb.core.contract.function.data.dynamic.DynamicData;
import com.jkb.core.contract.function.index.DynamicContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.function.index.dynamic.DynamicDataResponsitory;
import com.jkb.model.info.UserInfoSingleton;

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
    private DynamicDataResponsitory responsitory;

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
            @NonNull DynamicContract.View view, @NonNull DynamicDataResponsitory responsitory) {
        this.view = view;
        this.responsitory = responsitory;

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
    public void likeDynamic(int position) {

    }

    @Override
    public void bindDataToView() {
        if (!view.isActive()) {
            return;
        }
        view.setDynamicDataToView(dynamicDatas);
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
        responsitory.getAllDynamic(Authorization, pageControl.getCurrent_page(), dynamicApiCallback);
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
                        bindDataToView();
                        return;
                    }
                    DynamicListEntity entity = body.getMsg();
                    if (entity == null) {
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
                        }
                    }
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
                    dynamicData.setCreated_at(bean.getCreated_at());
                    //设置是否原创的状态
                    int is_original = bean.getIs_original();
                    if (is_original == 0) {
                        dynamicData.setIs_orginal(false);
                    } else {
                        dynamicData.setIs_orginal(true);
                    }
                    dynamicData.setComments_count(bean.getComments_count());
                    dynamicData.setOperation_count(bean.getOperation_count());
                    dynamicData.setParticipation(bean.getParticipation());
                    dynamicData.setHasFavorite(bean.isHasFavorite());
                    //判断是否原创
                    if (dynamicData.is_orginal()) {
                        DynamicData.Originator originator = new DynamicData.Originator();
                        DynamicListEntity.DataBean.DynamicBean.OriginatorBean originatorBean =
                                bean.getOriginator();
                        if (originatorBean != null) {
                            originator.setOriginator_avatar(originatorBean.getOriginator_avatar());
                            originator.setOriginator_id(originatorBean.getOriginator_id());
                            originator.setOriginator_nickname(originatorBean.getOriginator_nickname());
                            dynamicData.setOriginator(originator);
                        }
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
                    }
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
                        view.hideRefreshingView();
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.hideRefreshingView();
                        bindDataToView();
                    }
                }
            };

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
