package com.jkb.core.presenter.personCenter.original.myDynamic.circle;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserActionCircleEntity;
import com.jkb.core.contract.personCenter.original.myDynamic.circle.CircleSelectorContract;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.personCenter.original.myDynamic.circleSelector.CircleSelectorRepertory;
import com.jkb.model.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 圈子选择的P层
 * Created by JustKiddingBaby on 2016/10/18.
 */

public class CircleSelectorPresenter implements CircleSelectorContract.Presenter {

    private static final String TAG = "CircleSelectorP";
    private CircleSelectorContract.View view;
    private CircleSelectorRepertory repertory;

    public CircleSelectorPresenter(
            @NonNull CircleSelectorContract.View view,
            @NonNull CircleSelectorRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        circleInfos = new ArrayList<>();
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    //data
    private int user_id;
    private List<CircleInfo> circleInfos;
    private boolean isCached = false;

    //分页
    private PageControlEntity pageControl;
    private boolean isRefreshing = false;

    @Override
    public void onRefresh() {
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        isCached = false;
        circleInfos.clear();
        pageControl.setCurrent_page(0);
        view.showRefreshingView();
        reqSubscribeCircle();
    }

    @Override
    public void onLoadMore() {
        if (isRefreshing) {
            return;
        }
        if (pageControl.getCurrent_page() >= pageControl.getLast_page()) {
            return;
        }
        isRefreshing = true;
        isCached = false;
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqSubscribeCircle();
    }

    @Override
    public void bindDataToView() {
        isRefreshing = false;
        if (circleInfos != null && circleInfos.size() > 0) {
            isCached = true;
        } else {
            isCached = false;
        }
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        view.hideRefreshingView();
        view.setCircleDatas(circleInfos);
    }

    @Override
    public void initUserId() {
        user_id = view.bindUserId();
    }

    @Override
    public void initCircleData() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void onCircleItemClick(int position) {
        CircleInfo circleInfo = circleInfos.get(position);
        int circleId = circleInfo.getCircleId();
    }

    @Override
    public void start() {
        initUserId();
        initCircleData();
    }

    /**
     * 请求订阅的圈子数据
     */
    private void reqSubscribeCircle() {
        LogUtils.d(TAG, "req user_id=" + user_id);
        repertory.subscribeCircle(user_id, 0, pageControl.getCurrent_page(),
                subscribeCircleApiCallback);
    }

    /**
     * 获取订阅的圈子数据
     */
    private ApiCallback<ApiResponse<UserActionCircleEntity>> subscribeCircleApiCallback =
            new ApiCallback<ApiResponse<UserActionCircleEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<UserActionCircleEntity>> response) {
                    //设置数据
                    if (view.isActive()) {
                        handleSubscribeCircleData(response.body());
                    }
                }

                /**
                 * 处理返回的结果
                 */
                private void handleSubscribeCircleData(ApiResponse<UserActionCircleEntity> body) {
                    if (body == null) {
                        bindDataToView();
                        return;
                    }
                    UserActionCircleEntity entity = body.getMsg();
                    if (entity == null) {
                        bindDataToView();
                        return;
                    }
                    //设置分页数据
                    pageControl.setTotal(entity.getTotal());
                    pageControl.setPer_page(entity.getPer_page());
                    pageControl.setCurrent_page(entity.getCurrent_page());
                    pageControl.setLast_page(entity.getLast_page());
                    pageControl.setNext_page_url(entity.getNext_page_url());
                    pageControl.setPrev_page_url(entity.getPrev_page_url());
                    pageControl.setFrom(entity.getFrom());
                    pageControl.setTo(entity.getTo());

                    List<UserActionCircleEntity.DataBean> circles = entity.getData();
                    if (circles == null || circles.size() == 0) {
                        bindDataToView();
                        return;
                    }
                    //设置数据进去
                    changeToCircleData(circles);
                }

                /**
                 * 转换为可用的数据
                 */
                private void changeToCircleData(
                        List<UserActionCircleEntity.DataBean> circles) {
                    for (int i = 0; i < circles.size(); i++) {
                        CircleInfo data = new CircleInfo();
                        UserActionCircleEntity.DataBean bean = circles.get(i);
                        data.setCircleName(bean.getName());
                        data.setCircleType(bean.getType());
                        data.setPictureUrl(bean.getPicture());
                        data.setDynamicsCount(bean.getDynamics_count());
                        data.setDynamicsCount(bean.getOperation_count());
                        data.setCircleId(bean.getId());
                        circleInfos.add(data);
                    }
                    bindDataToView();
                }

                @Override
                public void onError(
                        Response<ApiResponse<UserActionCircleEntity>> response,
                        String error, ApiResponse<UserActionCircleEntity> apiResponse) {
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
