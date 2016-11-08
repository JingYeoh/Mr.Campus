package com.jkb.core.presenter.circle;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.circle.CircleSettingVisitorContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.dataSource.circle.circleSetting.user.CircleSettingUserDataSource;
import com.jkb.model.dataSource.circle.data.CircleIndexData;
import com.jkb.model.info.UserInfoSingleton;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 圈子访客设置的P层
 * Created by JustKiddingBaby on 2016/11/6.
 */

public class CircleSettingVisitorPresenter implements CircleSettingVisitorContract.Presenter {

    private CircleSettingVisitorContract.View view;
    private CircleSettingUserDataSource repertory;

    //data
    private int circle_id;
    private boolean isCached;
    private boolean isRequesting;
    private CircleIndexData circleIndexData;

    public CircleSettingVisitorPresenter(
            CircleSettingVisitorContract.View view, CircleSettingUserDataSource repertory) {
        this.view = view;
        this.repertory = repertory;

        circleIndexData = new CircleIndexData();

        this.view.setPresenter(this);
    }

    @Override
    public void bindDataToView() {
        isCached = true;
        isRequesting = false;
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        view.setInCommonUseStatus(circleIndexData.isHasInCommonUse());
    }

    @Override
    public void initCircleId() {
        circle_id = view.getCircleId();
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
    public void onRefresh() {
        reqCircleInfo();
    }

    @Override
    public void onInCommonUseSwitchClick() {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        String Authorization = getAuthorization();
        int user_id = getUser_id();
        view.showLoading("");
        repertory.setInCommonUseCircleOrCancel(Authorization, user_id, circle_id,
                inCommonUseApiCallback);
    }

    @Override
    public boolean isCircleCreator() {
        /*boolean isCircleCreator;
        if (LoginContext.getInstance().isLogined()) {
            if (getUser_id() == circleIndexData.getUser_id()) {
                isCircleCreator = true;
            } else {
                isCircleCreator = false;
            }
        } else {
            isCircleCreator = false;
        }
        return isCircleCreator;*/
        return false;
    }

    @Override
    public void start() {
        initCircleId();
        initCircleData();
    }

    /**
     * 请求圈子信息
     */
    private void reqCircleInfo() {
        if (isRequesting) {
            return;
        }
        int userId = getUser_id();
        if (userId == 0) {
            view.showReqResult("请您先去登录再进行操作");
            return;
        }
        isRequesting = true;
        view.showLoading("");
        repertory.getCircleInfo(userId, circle_id, circleInfoApiCallback);
    }

    /**
     * 得到用戶id
     */
    private int getUser_id() {
        int user_id = 0;
        if (LoginContext.getInstance().isLogined()) {
            user_id = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
        }
        return user_id;
    }

    /**
     * 得到Authorization
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
     * 圈子信息的数据回调
     */
    private ApiCallback<ApiResponse<CircleInfoEntity>> circleInfoApiCallback =
            new ApiCallback<ApiResponse<CircleInfoEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CircleInfoEntity>> response) {
                    if (view.isActive()) {
                        handleCircleInfoData(response.body().getMsg());
                    }
                }

                /**
                 * 处理圈子数据
                 */
                private void handleCircleInfoData(CircleInfoEntity entity) {
                    if (entity == null) {
                        bindDataToView();
                        return;
                    }
                    CircleInfoEntity.CircleBean bean = entity.getCircle();
                    if (bean == null) {
                        bindDataToView();
                        return;
                    }
                    changeCircleData(bean);
                    bindDataToView();
                }

                /**
                 * 转换圈子数据为可用的数据
                 */
                private void changeCircleData(CircleInfoEntity.CircleBean bean) {
                    circleIndexData.setCircleName(bean.getName());
                    circleIndexData.setCircleType(bean.getType());
                    circleIndexData.setCircleIntroducton(bean.getIntroduction());
                    circleIndexData.setDynamicsCount(bean.getDynamics_count());
                    circleIndexData.setSubsribeCount(bean.getSubscribe_count());
                    int hasInCommonUse = bean.getHasInCommonUse();
                    if (hasInCommonUse == 0) {
                        circleIndexData.setHasInCommonUse(false);
                    } else {
                        circleIndexData.setHasInCommonUse(true);
                    }
                    if (bean.getHasSubscribe() == 0) {
                        circleIndexData.setHasSubscribe(false);
                    } else {
                        circleIndexData.setHasSubscribe(true);
                    }
                    circleIndexData.setPicture(bean.getPicture());
                    CircleInfoEntity.CircleBean.UserBean user = bean.getUser();
                    if (user != null) {
                        circleIndexData.setUser_id(user.getId());
                    }
                }

                @Override
                public void onError(Response<ApiResponse<CircleInfoEntity>> response,
                                    String error, ApiResponse<CircleInfoEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                        isCached = false;
                        view.dismissLoading();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                        isCached = false;
                        view.dismissLoading();
                    }
                }
            };
    /**
     * 设置为常用圈子的数据回调
     */
    private ApiCallback<ApiResponse<OperationActionEntity>> inCommonUseApiCallback =
            new ApiCallback<ApiResponse<OperationActionEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                    if (view.isActive()) {
                        view.showReqResult("操作成功");
                        boolean hasInCommonUse = circleIndexData.isHasInCommonUse();
                        hasInCommonUse = !hasInCommonUse;
                        circleIndexData.setHasInCommonUse(hasInCommonUse);
                        bindDataToView();
                    }
                }

                @Override
                public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                    String error, ApiResponse<OperationActionEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                        isCached = false;
                        view.dismissLoading();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("操作失败");
                        isCached = false;
                        view.dismissLoading();
                    }
                }
            };
}
