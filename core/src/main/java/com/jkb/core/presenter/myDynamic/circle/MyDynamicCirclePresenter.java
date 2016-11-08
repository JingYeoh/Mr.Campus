package com.jkb.core.presenter.myDynamic.circle;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicActionEntity;
import com.jkb.api.entity.dynamic.DynamicCircleListEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.myDynamic.circle.MyDynamicCircleContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.dynamic.myOriginal.myCircle.CircleArticleDynamic;
import com.jkb.core.data.dynamic.myOriginal.myCircle.CircleNormalDynamic;
import com.jkb.core.data.dynamic.myOriginal.myCircle.CircleTopicDynamic;
import com.jkb.core.data.dynamic.myOriginal.myCircle.DynamicInCircleDynamic;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.myDynamic.circle.MyDynamicCircleRepertory;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 我的圈子动态的层
 * Created by JustKiddingBaby on 2016/10/17.
 */

public class MyDynamicCirclePresenter implements MyDynamicCircleContract.Presenter {

    private static final String TAG = "MyDynamicCircleP";
    private MyDynamicCircleContract.View view;
    private MyDynamicCircleRepertory repertory;

    //data
    private int user_id = -1;
    private boolean isCached = false;
    private List<DynamicInCircleDynamic> dynamicInCircleDynamics;
    private int filterCircle_id = 0;

    //刷新
    private boolean isLoading = false;
    private PageControlEntity pageControl;

    public MyDynamicCirclePresenter(
            @NonNull MyDynamicCircleContract.View view, @NonNull MyDynamicCircleRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        dynamicInCircleDynamics = new ArrayList<>();
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void initUser_id() {
        user_id = view.bindUser_id();
//        Log.d(TAG,"user_id="+user_id);
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

        view.setMyCircleDynamic(dynamicInCircleDynamics, isOriginal());
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        isCached = false;
        isLoading = true;
        dynamicInCircleDynamics.clear();
        pageControl.setCurrent_page(0);
        reqMyDynamicCircle();
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
        reqMyDynamicCircle();
    }

    @Override
    public void onItemDeleteClick(final int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        DynamicInCircleDynamic circleDynamic = dynamicInCircleDynamics.get(position);
        int id = circleDynamic.getDynamic_id();
        String Authorization = Config.HEADER_BEARER + getUserAuths().getToken();
        view.showLoading("");
        repertory.deleteDynamic(Authorization, id, getUserAuths().getUser_id(),
                new ApiCallback<ApiResponse<DynamicActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<DynamicActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("删除成功");
                            dynamicInCircleDynamics.remove(position);
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
        DynamicInCircleDynamic circleDynamic = dynamicInCircleDynamics.get(position);
        int id = circleDynamic.getDynamic_id();
        if (circleDynamic instanceof CircleArticleDynamic) {
            view.startDynamicDetailArticle(id);
        } else if (circleDynamic instanceof CircleTopicDynamic) {
            view.startDynamicDetailTopic(id);
        } else if (circleDynamic instanceof CircleNormalDynamic) {
            view.startDynamicDetailNormal(id);
        }
    }

    @Override
    public void onItemLikeClick(final int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        DynamicInCircleDynamic circleDynamic = dynamicInCircleDynamics.get(position);
        int id = circleDynamic.getDynamic_id();
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
                            DynamicInCircleDynamic dynamic = dynamicInCircleDynamics.get(position);
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
                            dynamicInCircleDynamics.set(position, dynamic);
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
        DynamicInCircleDynamic dynamic = dynamicInCircleDynamics.get(position);
        int id = dynamic.getDynamic_id();
        view.startCommentList(id);
    }

    @Override
    public void onItemCircleClick(int position) {
        DynamicInCircleDynamic dynamicInCircleDynamic = dynamicInCircleDynamics.get(position);
        CircleInfo circle = dynamicInCircleDynamic.getCircle();
        if (circle == null) {
            view.showReqResult("圈子不存在");
            return;
        }
        view.startCircleIndex(circle.getCircleId());
    }

    @Override
    public void onAllCircleSelected() {
        filterCircle_id = 0;
        if (view.isActive()) {
            view.setCircleSelectedName("全部圈子");
            onRefresh();
        }
    }

    @Override
    public void onCircleSelected(CircleInfo circleInfo) {
        filterCircle_id = circleInfo.getCircleId();
        if (view.isActive()) {
            view.setCircleSelectedName(circleInfo.getCircleName());
            onRefresh();
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
     * 请求我的圈子动态接口
     */
    private void reqMyDynamicCircle() {
        String Authorization;
        if (LoginContext.getInstance().isLogined()) {
            Authorization = Config.HEADER_BEARER + getUserAuths().getToken();
        } else {
            Authorization = null;
        }
//        Log.d(TAG, "user_id=" + user_id);
        repertory.getMyDynamicCircle(Authorization, user_id, filterCircle_id,
                pageControl.getCurrent_page(), dynamicCircleApiCallback);
    }

    /**
     * 圈子内动态的Api回调接口
     */
    private ApiCallback<ApiResponse<DynamicCircleListEntity>> dynamicCircleApiCallback =
            new ApiCallback<ApiResponse<DynamicCircleListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicCircleListEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<DynamicCircleListEntity> body) {
                    if (body == null) {
                        bindDataToView();
                        return;
                    }
                    handleDynamicData(body.getMsg());
                }

                /**
                 * 处理动态数据
                 */
                private void handleDynamicData(DynamicCircleListEntity msg) {
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

                    //解析具体的数据
                    List<DynamicCircleListEntity.DataBean> data = msg.getData();
                    if (data == null || data.size() == 0) {
                        bindDataToView();
                        return;
                    }

                    for (DynamicCircleListEntity.DataBean dataBean :
                            data) {
                        handleDynamicInCircleData(dataBean);
                    }
                    bindDataToView();
                }

                /**
                 * 处理圈子内数据
                 */
                private void handleDynamicInCircleData(DynamicCircleListEntity.DataBean dataBean) {
                    if (dataBean != null) {
                        DynamicInCircleDynamic dynamicInCircleDynamic =
                                new DynamicInCircleDynamic(dataBean).getDynamicInCircle();
                        if (dynamicInCircleDynamic != null) {
                            dynamicInCircleDynamics.add(dynamicInCircleDynamic);
                        }
                    }
                }

                @Override
                public void onError(
                        Response<ApiResponse<DynamicCircleListEntity>> response,
                        String error, ApiResponse<DynamicCircleListEntity> apiResponse) {
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
