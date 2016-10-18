package com.jkb.core.presenter.myFavorite;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.myFavorite.MyFavoriteDynamicContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.MyFavoriteDynamicData;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle.CircleArticleDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle.CircleNormalDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle.CircleTopicDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle.DynamicInCircleDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.original.OriginalArticleDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.original.OriginalDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.original.OriginalNormalDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.original.OriginalTopicDynamic;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.myFavorite.MyFavoriteDynamicRepertory;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 我喜欢的动态的P层
 * Created by JustKiddingBaby on 2016/10/16.
 */

public class MyFavoriteDynamicPresenter implements MyFavoriteDynamicContract.Presenter {

    //data
    private static final String TAG = "MyFavoriteDynamicP";
    private MyFavoriteDynamicContract.View view;
    private MyFavoriteDynamicRepertory repertory;

    //data
    private int user_id = -1;
    private boolean isCached = false;
    private List<MyFavoriteDynamicData> favoriteDynamicDatas;

    //刷新
    private boolean isLoading = false;
    private PageControlEntity pageControl;

    public MyFavoriteDynamicPresenter(
            @NonNull MyFavoriteDynamicContract.View view,
            @NonNull MyFavoriteDynamicRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        favoriteDynamicDatas = new ArrayList<>();
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

        view.setMyFavoriteDynamic(favoriteDynamicDatas);
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        isCached = false;
        isLoading = true;
        favoriteDynamicDatas.clear();
        pageControl.setCurrent_page(0);
        reqMyDynamicArticle();
    }

    @Override
    public void onLoadMore() {
//        Log.d(TAG, pageControl.toString());
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
    public void onItemDynamicClick(int position) {
        MyFavoriteDynamicData dynamicData = favoriteDynamicDatas.get(position);
        int id = dynamicData.getDynamic_id();
        if (dynamicData instanceof DynamicInCircleDynamic) {
            if (dynamicData instanceof CircleArticleDynamic) {
                view.startDynamicDetailArticle(id);
            } else if (dynamicData instanceof CircleNormalDynamic) {
                view.startDynamicDetailNormal(id);
            } else if (dynamicData instanceof CircleTopicDynamic) {
                view.startDynamicDetailTopic(id);
            }
        } else if (dynamicData instanceof OriginalDynamic) {
            if (dynamicData instanceof OriginalArticleDynamic) {
                view.startDynamicDetailArticle(id);
            } else if (dynamicData instanceof OriginalNormalDynamic) {
                view.startDynamicDetailNormal(id);
            } else if (dynamicData instanceof OriginalTopicDynamic) {
                view.startDynamicDetailTopic(id);
            }
        }
    }

    @Override
    public void onItemLikeClick(final int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        if (isOriginal()) {
            //判断是否喜欢
            MyFavoriteDynamicData myFavoriteDynamicData = favoriteDynamicDatas.get(position);
            if (myFavoriteDynamicData.isHas_favorite()) {
                view.showHintDetermineView(position);
            } else {
                favoriteDynamic(position);
            }
        } else {
            favoriteDynamic(position);
        }
    }

    @Override
    public void favoriteDynamic(final int position) {
        MyFavoriteDynamicData dynamicData = favoriteDynamicDatas.get(position);
        int id = dynamicData.getDynamic_id();
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
                            MyFavoriteDynamicData dynamic = favoriteDynamicDatas.get(position);
                            boolean hasFavorite = dynamic.isHas_favorite();
                            hasFavorite = !hasFavorite;
                            dynamic.setHas_favorite(hasFavorite);
                            int operation_count = dynamic.getCount_of_favorite();
                            if (hasFavorite) {
                                operation_count++;
                            } else {
                                operation_count--;
                            }
                            dynamic.setCount_of_favorite(operation_count);
                            favoriteDynamicDatas.set(position, dynamic);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                        String error, ApiResponse<OperationActionEntity> apiResponse) {
                        if (view.isActive()) {
                            bindDataToView();
                            view.showReqResult("操作失败");
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
        MyFavoriteDynamicData myFavoriteDynamicData = favoriteDynamicDatas.get(position);
        int dynamic_id = myFavoriteDynamicData.getDynamic_id();
        view.startCommentList(dynamic_id);
    }

    @Override
    public void onItemNameClick(int position) {
        MyFavoriteDynamicData myFavoriteDynamicData = favoriteDynamicDatas.get(position);
        if (myFavoriteDynamicData instanceof DynamicInCircleDynamic) {
            int circleId = ((DynamicInCircleDynamic) myFavoriteDynamicData).getCircle().getCircleId();
            view.startCircleIndex(circleId);
        } else if (myFavoriteDynamicData instanceof OriginalDynamic) {
            int id = ((OriginalDynamic) myFavoriteDynamicData).getUser().getId();
            view.startPersonCenter(id);
        }
    }

    @Override
    public void start() {
        initUser_id();
        initDynamic();
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
     * 请求我的普通动态数据接口
     */
    private void reqMyDynamicArticle() {
        String Authorization;
        int visitor_id = 0;
        if (LoginContext.getInstance().isLogined()) {
            Authorization = Config.HEADER_BEARER + getUserAuths().getToken();
            visitor_id = getUserAuths().getUser_id();
        } else {
            Authorization = null;
        }
        Log.d(TAG, "user_id=" + user_id);
        repertory.getMyFavoriteAllDynamic(Authorization, visitor_id, user_id,
                pageControl.getCurrent_page(), dynamicMyFavoriteApiCallback);
    }

    /**
     * 我喜欢的动态
     */
    private ApiCallback<ApiResponse<DynamicMyFavoriteEntity>> dynamicMyFavoriteApiCallback =
            new ApiCallback<ApiResponse<DynamicMyFavoriteEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicMyFavoriteEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<DynamicMyFavoriteEntity> body) {
                    if (body == null) {
                        bindDataToView();
                        return;
                    }
                    handleDynamicData(body.getMsg());
                }

                /**
                 * 处理动态数据
                 */
                private void handleDynamicData(DynamicMyFavoriteEntity msg) {
                    if (msg == null) {
                        bindDataToView();
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

                    List<DynamicMyFavoriteEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                    } else {
                        for (DynamicMyFavoriteEntity.DataBean dataBean :
                                data) {
                            filterDynamicData(dataBean);
                        }
                        bindDataToView();
                    }
                }

                /**
                 * 解析动态数据
                 */
                private void filterDynamicData(DynamicMyFavoriteEntity.DataBean dataBean) {
                    MyFavoriteDynamicData myFavoriteDynamicData = new MyFavoriteDynamicData(dataBean)
                            .getMyFavoriteDynamic();
                    if (myFavoriteDynamicData != null) {
                        favoriteDynamicDatas.add(myFavoriteDynamicData);
                    }
                }

                @Override
                public void onError(Response<ApiResponse<DynamicMyFavoriteEntity>> response,
                                    String error, ApiResponse<DynamicMyFavoriteEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult("请求错误");
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
