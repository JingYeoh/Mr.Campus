package com.jkb.core.presenter.function.index.dynamic;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.function.index.DynamicContract2;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.index.dynamic.IndexDynamicData;
import com.jkb.core.data.index.dynamic.original.IndexOriginalArticleDynamicData;
import com.jkb.core.data.index.dynamic.original.IndexOriginalDynamicData;
import com.jkb.core.data.index.dynamic.original.IndexOriginalNormalDynamicData;
import com.jkb.core.data.index.dynamic.original.IndexOriginalTopicDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.circle.IndexSubscribeDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.favorite.IndexFavoriteDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.postInCircle.IndexCircleDynamicData;
import com.jkb.core.data.info.dynamic.content.DynamicContentArticleInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentNormalInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentTopicInfo;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.function.index.dynamic.DynamicDataSource;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 动态的P层
 * Created by JustKiddingBaby on 2016/11/8.
 */

public class DynamicPresenter2 implements DynamicContract2.Presenter {

    private DynamicContract2.View view;
    private DynamicDataSource repertory;

    //data
    private List<IndexDynamicData> dynamicDatas;
    private boolean isCached;
    private boolean isRefreshing;
    private PageControlEntity pageControl;

    public DynamicPresenter2(DynamicContract2.View view, DynamicDataSource repertory) {
        this.view = view;
        this.repertory = repertory;

        dynamicDatas = new ArrayList<>();
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void onRefresh() {
        if (isRefreshing) {
            return;
        }
        view.showRefreshingView();
        pageControl.setCurrent_page(1);
        dynamicDatas.clear();
        reqUserDynamic();
    }

    @Override
    public void onLoadMore() {
        if (isRefreshing) {
            return;
        }
        if (pageControl.getLast_page() <= pageControl.getCurrent_page()) {
            return;
        }
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqUserDynamic();
    }

    @Override
    public void initDynamicData() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void bindDataToView() {
        if (dynamicDatas.size() == 0) {
            isCached = false;
        } else {
            isCached = true;
        }
        isRefreshing = false;
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        view.hideRefreshingView();
        view.setDynamicDataToView(dynamicDatas);
    }

    @Override
    public void onContentItemClick(int position) {
        if (!view.isActive()) {
            return;
        }
        IndexDynamicData dynamicData = dynamicDatas.get(position);
        boolean isCircleId = false;
        int target_id = dynamicData.getTarget_id();
        String dynamicType = dynamicData.getDynamicType();
        if (dynamicData instanceof IndexSubscribeDynamicData) {
            isCircleId = true;
        }
        if (isCircleId) {
            view.startCircleIndex(target_id);
        } else {
            switch (dynamicType) {
                case Config.D_TYPE_ARTICLE:
                    view.startArticleDynamicDetail(target_id);
                    break;
                case Config.D_TYPE_NORMAL:
                    view.startNormalDynamicDetail(target_id);
                    break;
                case Config.D_TYPE_TOPIC:
                    view.startTopicDynamicDetail(target_id);
                    break;
            }
        }
    }

    @Override
    public void onHeadImgItemClick(int position) {
        IndexDynamicData dynamicData = dynamicDatas.get(position);
        int user_id = 0;
        int circle_id = 0;
        if (dynamicData instanceof IndexOriginalDynamicData) {
            user_id = ((IndexOriginalDynamicData) dynamicData).getUser().getId();
        } else if (dynamicData instanceof IndexCircleDynamicData) {
            circle_id = ((IndexCircleDynamicData) dynamicData).getCircle().getCircleId();
        } else if (dynamicData instanceof IndexFavoriteDynamicData) {
            user_id = ((IndexFavoriteDynamicData) dynamicData).getUser().getId();
        } else if (dynamicData instanceof IndexSubscribeDynamicData) {
            user_id = dynamicData.getCreator_id();
        }
        if (!view.isActive()) {
            return;
        }
        if (user_id > 0) {
            view.startPersonCenter(user_id);
        } else if (circle_id > 0) {
            view.startCircleIndex(circle_id);
        }
    }

    @Override
    public void onShareItemClick(int position) {

    }

    @Override
    public void onCommentItemClick(int position) {
        IndexDynamicData dynamicData = dynamicDatas.get(position);
        int dynamic_id = 0;
        if (dynamicData instanceof IndexOriginalDynamicData) {
            dynamic_id = ((IndexOriginalDynamicData) dynamicData).getId();
        } else if (dynamicData instanceof IndexCircleDynamicData) {
            dynamic_id = ((IndexCircleDynamicData) dynamicData).getId();
        } else if (dynamicData instanceof IndexFavoriteDynamicData) {
            dynamic_id = ((IndexFavoriteDynamicData) dynamicData).getId();
        }
        if (view == null) {
            return;
        }
        if (dynamic_id > 0) {
            view.startCommentActivity(dynamic_id);
        }
    }

    @Override
    public void onLikeItemClick(final int position) {
        IndexDynamicData dynamicData = dynamicDatas.get(position);
        int target_id = dynamicData.getTarget_id();
        if (dynamicData instanceof IndexSubscribeDynamicData) {
            return;
        }
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        view.showLoading("");
        int user_id = getUser_id();
        repertory.favorite(authorization, user_id, target_id,
                new ApiCallback<ApiResponse<OperationActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                        if (view.isActive()) {
                            IndexDynamicData dynamic = dynamicDatas.get(position);
                            if (dynamic instanceof IndexOriginalDynamicData) {
                                handleOriginalDynamic((IndexOriginalDynamicData) dynamic);
                            } else if (dynamic instanceof IndexFavoriteDynamicData) {
                                handleFavoriteDynamic((IndexFavoriteDynamicData) dynamic);
                            } else if (dynamic instanceof IndexCircleDynamicData) {
                                handleCircleDynamic((IndexCircleDynamicData) dynamic);
                            }
                            view.showReqResult("操作成功");
                            bindDataToView();
                        }
                    }

                    private void handleCircleDynamic(IndexCircleDynamicData dynamic) {
                        boolean hasFavorite = dynamic.isHasFavorite();
                        hasFavorite = !hasFavorite;
                        dynamic.setHasFavorite(hasFavorite);
                        int count_of_favorite = dynamic.getCount_of_favorite();
                        if (hasFavorite) {
                            count_of_favorite++;
                        } else {
                            count_of_favorite--;
                        }
                        dynamic.setCount_of_favorite(count_of_favorite);
                    }

                    private void handleFavoriteDynamic(IndexFavoriteDynamicData dynamic) {
                        boolean hasFavorite = dynamic.isHasFavorite();
                        hasFavorite = !hasFavorite;
                        dynamic.setHasFavorite(hasFavorite);
                        int count_of_favorite = dynamic.getCount_of_favorite();
                        if (hasFavorite) {
                            count_of_favorite++;
                        } else {
                            count_of_favorite--;
                        }
                        dynamic.setCount_of_favorite(count_of_favorite);
                    }

                    private void handleOriginalDynamic(IndexOriginalDynamicData dynamic) {
                        boolean hasFavorite = dynamic.isHasFavorite();
                        hasFavorite = !hasFavorite;
                        dynamic.setHasFavorite(hasFavorite);
                        int count_of_favorite = dynamic.getCount_of_favorite();
                        if (hasFavorite) {
                            count_of_favorite++;
                        } else {
                            count_of_favorite--;
                        }
                        dynamic.setCount_of_favorite(count_of_favorite);
                    }

                    @Override
                    public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                        String error, ApiResponse<OperationActionEntity> apiResponse) {
                        if (view.isActive()) {
                            view.showReqResult(error);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            view.showReqResult("请求失败，请检测您的网络连接");
                            bindDataToView();
                        }
                    }
                });
    }

    @Override
    public void onCreatorItemClick(int position) {
        IndexDynamicData dynamicData = dynamicDatas.get(position);
        int user_id = 0;
        int circle_id = 0;
        if (dynamicData instanceof IndexOriginalDynamicData) {
            user_id = ((IndexOriginalDynamicData) dynamicData).getUser().getId();
        } else if (dynamicData instanceof IndexCircleDynamicData) {
            user_id = ((IndexCircleDynamicData) dynamicData).getUser().getId();
        } else if (dynamicData instanceof IndexFavoriteDynamicData) {
            if (((IndexFavoriteDynamicData) dynamicData).isCircleDynamic()) {
                circle_id = ((IndexFavoriteDynamicData) dynamicData).getCircle().getCircleId();
            } else {
                user_id = ((IndexFavoriteDynamicData) dynamicData).getUser().getId();
            }
        }
        if (!view.isActive()) {
            return;
        }
        if (user_id > 0) {
            view.startPersonCenter(user_id);
        } else if (circle_id > 0) {
            view.startCircleIndex(circle_id);
        }
    }

    @Override
    public void setCacheExpired() {
        isCached = false;
    }

    @Override
    public void onPicturesClick(int position, int clickPosition) {
        //点击大图的时候
        IndexDynamicData dynamicData = dynamicDatas.get(position);
        if (dynamicData instanceof IndexOriginalArticleDynamicData) {
            handlePictureBrowserArticle((IndexOriginalArticleDynamicData) dynamicData);
        } else if (dynamicData instanceof IndexOriginalTopicDynamicData) {
            handlePictureBrowserTopic((IndexOriginalTopicDynamicData) dynamicData);
        } else if (dynamicData instanceof IndexOriginalNormalDynamicData) {
            handlePictureBrowserNormal((IndexOriginalNormalDynamicData) dynamicData, clickPosition);
        }
    }

    /**
     * 处理大图预览的动态条目
     *
     * @param clickPosition 被点击的图片条目
     */
    private void handlePictureBrowserNormal(IndexOriginalNormalDynamicData dynamicData,
                                            int clickPosition) {
        DynamicContentNormalInfo normal = dynamicData.getNormal();
        if (normal == null) {
            return;
        }
        List<String> img = normal.getImg();
        view.showImagesBrowserView((ArrayList<String>) img, clickPosition);
    }

    /**
     * 处理大图预览的话题动态
     */
    private void handlePictureBrowserTopic(IndexOriginalTopicDynamicData dynamicData) {
        DynamicContentTopicInfo topic = dynamicData.getTopic();
        if (topic == null) {
            return;
        }
        String img = topic.getImg();
        if (!StringUtils.isEmpty(img)) {
            ArrayList<String> arr = new ArrayList<>();
            arr.add(img);
            view.showImagesBrowserView(arr, 0);
        }
    }

    /**
     * 处理大图预览的文章动态
     */
    private void handlePictureBrowserArticle(IndexOriginalArticleDynamicData dynamicData) {
        DynamicContentArticleInfo article = dynamicData.getArticle();
        if (article != null) {
            List<DynamicContentArticleInfo.Article> articles = article.getArticle();
            if (articles == null || articles.size() == 0) {
                return;
            }
            for (DynamicContentArticleInfo.Article art :
                    articles) {
                if (!StringUtils.isEmpty(art.getImg())) {
                    ArrayList<String> arr = new ArrayList<>();
                    arr.add(art.getImg());
                    view.showImagesBrowserView(arr, 0);
                }
            }
        }
    }

    @Override
    public void start() {
        initDynamicData();
    }

    /**
     * 得到头
     */
    private String getAuthorization() {
        String authorization = null;
        if (LoginContext.getInstance().isLogined()) {
            authorization = Config.HEADER_BEARER +
                    UserInfoSingleton.getInstance().getUserAuths().getToken();
        }
        return authorization;
    }

    /**
     * 得到用户id
     */
    private int getUser_id() {
        int user_id = 0;
        if (LoginContext.getInstance().isLogined()) {
            user_id = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
        }
        return user_id;
    }

    /**
     * 请求用户动态
     */
    private void reqUserDynamic() {
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            return;
        }
        isRefreshing = true;
        isCached = false;
        repertory.getAllDynamic(authorization, pageControl.getCurrent_page(), dynamicApiCallback);
    }

    /**
     * 动态的数据请求回调
     */
    private ApiCallback<ApiResponse<DynamicListEntity>> dynamicApiCallback =
            new ApiCallback<ApiResponse<DynamicListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<DynamicListEntity> body) {
                    DynamicListEntity msg = body.getMsg();
                    //处理分页数据
                    pageControl.setTotal(msg.getTotal());
                    pageControl.setPer_page(msg.getPer_page());
                    pageControl.setCurrent_page(msg.getCurrent_page());
                    pageControl.setLast_page(msg.getLast_page());
                    pageControl.setNext_page_url(msg.getNext_page_url());
                    pageControl.setPrev_page_url(msg.getPrev_page_url());
                    pageControl.setFrom(msg.getFrom());
                    pageControl.setTo(msg.getTo());

                    List<DynamicListEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                        return;
                    }
                    for (DynamicListEntity.DataBean dataBean :
                            data) {
                        bindDynamicData(dataBean);
                    }
                    bindDataToView();
                }

                /**
                 * 绑定动态数据
                 */
                private void bindDynamicData(DynamicListEntity.DataBean dataBean) {
                    if (dataBean == null) {
                        return;
                    }
                    IndexDynamicData dynamicData = new IndexDynamicData(dataBean).getIndexDynamic();
                    if (dynamicData != null) {
                        dynamicDatas.add(dynamicData);
                    }
                }

                @Override
                public void onError(Response<ApiResponse<DynamicListEntity>> response, String
                        error, ApiResponse<DynamicListEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败，请检查您的网络连接");
                        bindDataToView();
                    }
                }
            };
}
