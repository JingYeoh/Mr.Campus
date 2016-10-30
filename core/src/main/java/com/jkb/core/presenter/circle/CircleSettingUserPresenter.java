package com.jkb.core.presenter.circle;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleActionEntity;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.core.contract.circle.CircleSettingUserContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.dataSource.circle.circleSetting.user.CircleSettingUserDataRepertory;
import com.jkb.model.dataSource.circle.data.CircleIndexData;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.FileUtils;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;

import jkb.mrcampus.db.entity.UserAuths;
import okhttp3.MultipartBody;
import retrofit2.Response;

/**
 * 用户圈子设置的P层
 * Created by JustKiddingBaby on 2016/10/30.
 */

public class CircleSettingUserPresenter implements CircleSettingUserContract.Presenter {

    private CircleSettingUserContract.View view;
    private CircleSettingUserDataRepertory repertory;

    //data
    private int circle_id;
    private CircleIndexData circleIndexData = null;
    private boolean isCached = false;
    private boolean isRequesting = false;

    public CircleSettingUserPresenter(CircleSettingUserContract.View view,
                                      CircleSettingUserDataRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        circleIndexData = new CircleIndexData();

        this.view.setPresenter(this);
    }

    @Override
    public void bindDataToView() {
        isRequesting = false;
        isCached = true;
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        //绑定数据
        view.setCircleName(circleIndexData.getCircleName());
        view.setCircleBref(circleIndexData.getCircleIntroducton());
        view.setCirclePicture(circleIndexData.getPicture());
        view.setCircleTag(circleIndexData.getCircleType());
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
            //请求接口
            reqCircleInfo();
        }
    }

    @Override
    public void updateCirclePicture(String circlePicture) {
        LogUtils.d(CircleSettingUserPresenter.class, "circlePicture=" + circlePicture);
        if (StringUtils.isEmpty(circlePicture)) {
            view.showReqResult("选择的图片无效，请重试");
            return;
        }
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("您未登录，请先登录后再进行操作");
            return;
        }
        UserAuths userAuths = getUserAuths();
        Integer user_id = userAuths.getUser_id();
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        MultipartBody.Part part = FileUtils.getPartFromFile(circlePicture, "image");
        if (part == null) {
            view.showReqResult("选择的图片无效，请重试");
            return;
        }
        view.showLoading("");
        repertory.updateCircleImage(Authorization, circle_id, part,
                user_id, updateCircleInfoApiCallback);
    }

    @Override
    public void updateCircleName(String circleName) {
        if (!LoginContext.getInstance().isLogined()) {
            return;
        }
        UserAuths userAuths = getUserAuths();
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        Integer user_id = userAuths.getUser_id();
        view.showLoading("");
        repertory.updateCircleInfo(Authorization, circle_id, Config.COLUMN_NAME,
                circleName, user_id, updateCircleInfoApiCallback);
    }

    @Override
    public void updateCircleBref(String circleBref) {
        if (!LoginContext.getInstance().isLogined()) {
            return;
        }
        UserAuths userAuths = getUserAuths();
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        Integer user_id = userAuths.getUser_id();
        view.showLoading("");
        repertory.updateCircleInfo(Authorization, circle_id, Config.COLUMN_INTRODUCTION,
                circleBref, user_id, updateCircleInfoApiCallback);
    }

    @Override
    public void updateCircleTag(String circleTag) {

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
        int userId = 0;
        if (LoginContext.getInstance().isLogined()) {
            userId = getUserAuths().getUser_id();
        }
        isRequesting = true;
        view.showLoading("");
        repertory.getCircleInfo(userId, circle_id, circleInfoApiCallback);
    }

    /**
     * 得到userAuths数据
     */
    private UserAuths getUserAuths() {
        return UserInfoSingleton.getInstance().getUserAuths();
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
                        view.showReqResult("请求错误，请重试");
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

    /**
     * 更新圈子信息的数据回调
     */
    private ApiCallback<ApiResponse<CircleActionEntity>> updateCircleInfoApiCallback =
            new ApiCallback<ApiResponse<CircleActionEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CircleActionEntity>> response) {
                    if (view.isActive()) {
                        view.showReqResult("操作成功");
                        view.dismissLoading();
                        reqCircleInfo();//重新请求圈子数据
                    }
                }

                @Override
                public void onError(Response<ApiResponse<CircleActionEntity>> response, String error,
                                    ApiResponse<CircleActionEntity> apiResponse) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("操作失败");
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("请求失败");
                    }
                }
            };
}
