package com.jkb.core.presenter.personCenter.original.myDynamic;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicArticleListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailArticleData;
import com.jkb.core.contract.personCenter.original.myDynamic.MyDynamicArticleContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.personCenter.original.myDynamic.article.MyDynamicArticleRepertory;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 我的普通动态P层
 * Created by JustKiddingBaby on 2016/10/14.
 */

public class MyDynamicArticlePresenter implements MyDynamicArticleContract.Presenter {

    private static final String TAG = "MyDynamicNormalP";
    private MyDynamicArticleContract.View view;
    private MyDynamicArticleRepertory repertory;

    //data
    private int user_id = -1;
    private boolean isCached = false;
    private List<DynamicDetailArticleData> dynamicDetailArticleDatas;

    //刷新
    private boolean isLoading = false;
    private PageControlEntity pageControl;


    public MyDynamicArticlePresenter(
            @NonNull MyDynamicArticleContract.View view, @NonNull MyDynamicArticleRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        dynamicDetailArticleDatas = new ArrayList<>();
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void initUser_id() {
        user_id = view.bindUser_id();
    }

    @Override
    public void initDynamic() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void bindDataToView() {
        isCached = true;
        isLoading = false;
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        view.hideRefreshingView();

        view.setArticleMyDynamic(dynamicDetailArticleDatas);
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        isCached = false;
        isLoading = true;
        dynamicDetailArticleDatas.clear();
        pageControl.setCurrent_page(0);
        reqMyDynamicArticle();
    }


    @Override
    public void onLoadMore() {
        if (isLoading) {
            return;
        }
        if (pageControl.getCurrent_page() >= pageControl.getLast_page()) {
            return;
        }
        isCached = false;
        isLoading = true;
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqMyDynamicArticle();
    }

    @Override
    public void onItemDeleteClick(final int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        DynamicDetailArticleData articleData = dynamicDetailArticleDatas.get(position);
        int id = articleData.getId();
        String Authorization = Config.HEADER_BEARER + getUserAuths().getToken();
        view.showLoading("");
        repertory.deleteDynamic(Authorization, id, getUserAuths().getUser_id(),
                new ApiCallback<ApiResponse<DynamicActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<DynamicActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("删除成功");
                            dynamicDetailArticleDatas.remove(position);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResponse<DynamicActionEntity>> response, String error,
                                        ApiResponse<DynamicActionEntity> apiResponse) {
                        if (view.isActive()) {
//                            view.showReqResult("删除失败");
                            view.showReqResult(error);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            view.showReqResult("请求失败");
                            bindDataToView();
                        }
                    }
                });
    }

    @Override
    public void onItemDynamicClick(int position) {
        DynamicDetailArticleData articleData = dynamicDetailArticleDatas.get(position);
        int id = articleData.getId();
        view.startDynamicDetailArticle(id);
    }

    @Override
    public void onItemLikeClick(final int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        DynamicDetailArticleData articleData = dynamicDetailArticleDatas.get(position);
        int id = articleData.getId();
        String Authorization = Config.HEADER_BEARER + getUserAuths().getToken();
        view.showLoading("");
        //局部刷新
        repertory.favorite(Authorization, getUserAuths().getUser_id(), id,
                new ApiCallback<ApiResponse<OperationActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("操作成功");
                            //局部刷新
                            DynamicDetailArticleData article = dynamicDetailArticleDatas.get(position);
                            boolean hasFavorite = article.isHasFavorite();
                            hasFavorite = !hasFavorite;
                            article.setHasFavorite(hasFavorite);
                            int operation_count = article.getOperation_count();
                            if (hasFavorite) {
                                operation_count++;
                            } else {
                                operation_count--;
                            }
                            article.setOperation_count(operation_count);
                            dynamicDetailArticleDatas.set(position, article);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                        String error, ApiResponse<OperationActionEntity> apiResponse) {
                        if (view.isActive()) {
                            bindDataToView();
                            view.showReqResult(error);
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            bindDataToView();
                            view.showReqResult("请求失败");
                        }
                    }
                });
    }

    @Override
    public void onItemCommentClick(int position) {
        DynamicDetailArticleData articleData = dynamicDetailArticleDatas.get(position);
        int id = articleData.getId();
        view.startCommentList(id);
    }

    @Override
    public void start() {
        initUser_id();
        initDynamic();
    }

    /**
     * 请求我的普通动态数据接口
     */
    private void reqMyDynamicArticle() {
        String Authorization;
        if (LoginContext.getInstance().isLogined()) {
            Authorization = Config.HEADER_BEARER + getUserAuths().getToken();
        } else {
            Authorization = null;
        }
        Log.d(TAG, "user_id=" + user_id);
        repertory.getMyDynamicArticle(Authorization, user_id,
                pageControl.getCurrent_page(), myDynamicArticleApiCallback);
    }

    /**
     * 得到用戶信息
     */
    private UserAuths getUserAuths() {
        UserAuths userAuths = UserInfoSingleton.getInstance().getUserAuths();
        return userAuths;
    }

    /**
     * 是否原创
     *
     * @return 判断是否原创
     */
    private boolean isOriginal() {
        boolean isOriginal;
        if (LoginContext.getInstance().isLogined()) {
            if (getUserAuths().getUser_id() == user_id) {
                isOriginal = true;
            } else {
                isOriginal = false;
            }
        } else {
            isOriginal = false;
        }
        return isOriginal;
    }

    /**
     * 我的普通动态接口回调
     */
    private ApiCallback<ApiResponse<DynamicArticleListEntity>> myDynamicArticleApiCallback =
            new ApiCallback<ApiResponse<DynamicArticleListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicArticleListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 * 解析数据
                 */
                private void handleData(ApiResponse<DynamicArticleListEntity> body) {
                    if (body == null) {
                        bindDataToView();
                        dynamicDetailArticleDatas.clear();
                        return;
                    }
                    handleDynamicData(body.getMsg());
                }

                /**
                 * 解析动态数据
                 */
                private void handleDynamicData(DynamicArticleListEntity msg) {
                    if (msg == null) {
                        bindDataToView();
                        dynamicDetailArticleDatas.clear();
                        return;
                    }

                    //设置页码控制器
                    pageControl.setTotal(msg.getTotal());
                    pageControl.setPer_page(msg.getPer_page());
                    pageControl.setCurrent_page(msg.getCurrent_page());
                    pageControl.setLast_page(msg.getLast_page());
                    pageControl.setNext_page_url(msg.getNext_page_url());
                    pageControl.setPrev_page_url(msg.getPrev_page_url());
                    pageControl.setFrom(msg.getFrom());
                    pageControl.setTo(msg.getTo());

                    //解析具体的数据
                    List<DynamicArticleListEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                        return;
                    }
                    for (DynamicArticleListEntity.DataBean bean : data) {
                        DynamicDetailArticleData articleData = handleDynamicArticleData(bean);
                        if (articleData != null) {
                            dynamicDetailArticleDatas.add(articleData);
                        }
                    }
                    bindDataToView();
                }

                /**
                 * 解析普通动态数据
                 */
                private DynamicDetailArticleData handleDynamicArticleData
                (DynamicArticleListEntity.DataBean bean) {
                    if (bean == null) {
                        return null;
                    }
                    DynamicDetailArticleData articleData = new DynamicDetailArticleData();
                    articleData.setId(bean.getDynamic_id());
                    articleData.setTitle(bean.getTitle());
                    //设置内容
                    DynamicArticleListEntity.DataBean.ContentBean content = bean.getContent();
                    if (content == null) {
                        return null;
                    }

                    List<DynamicArticleListEntity.DataBean.ContentBean.ArticleBean> article =
                            content.getArticle();
                    if (article == null) {
                        return null;
                    }
                    List<DynamicDetailArticleData.ArticleContent> articles = new ArrayList<>();
                    for (DynamicArticleListEntity.DataBean.ContentBean.ArticleBean articleBean :
                            article) {
                        DynamicDetailArticleData.ArticleContent content1 =
                                new DynamicDetailArticleData.ArticleContent();
                        content1.setDoc(articleBean.getDoc());
                        content1.setImg(articleBean.getImg());

                        articles.add(content1);
                    }
                    articleData.setArticles(articles);
                    //其他数据
                    articleData.setTag(bean.getTag());
                    articleData.setComments_count(bean.getCount_of_comment());
                    articleData.setOperation_count(bean.getCount_of_favorite());
                    articleData.setCreated_at(bean.getDynamic_create_time());
                    if (bean.getHas_favorite() == 0) {
                        articleData.setHasFavorite(false);
                    } else {
                        articleData.setHasFavorite(true);
                    }
                    articleData.setOriginal(isOriginal());
                    return articleData;
                }

                @Override
                public void onError(Response<ApiResponse<DynamicArticleListEntity>> response, String error,
                                    ApiResponse<DynamicArticleListEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                        bindDataToView();
                    }
                }
            };
}
