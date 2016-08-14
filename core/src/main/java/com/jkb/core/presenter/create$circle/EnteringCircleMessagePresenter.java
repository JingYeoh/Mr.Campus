package com.jkb.core.presenter.create$circle;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.circle.CircleCreateEntity;
import com.jkb.core.contract.create$circle.EnteringCircleMessageContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.create$circle.CircleCreateDataResponsitory;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.FileUtils;
import com.jkb.model.utils.StringUtils;

import jkb.mrcampus.db.entity.UserAuths;
import okhttp3.MultipartBody;
import retrofit2.Response;

/**
 * 录入圈子信息的Presenter层
 * Created by JustKiddingBaby on 2016/8/11.
 */

public class EnteringCircleMessagePresenter implements EnteringCircleMessageContract.Presenter {

    private static final String TAG = "CircleMessagePresenter";
    private EnteringCircleMessageContract.View view;

    private CircleCreateDataResponsitory responsitory;

    public EnteringCircleMessagePresenter(
            @NonNull EnteringCircleMessageContract.View view,
            @NonNull CircleCreateDataResponsitory responsitory) {
        this.view = view;
        this.responsitory = responsitory;

        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void createCircle(
            int school_id, String name, String introduction, double longitude,
            double latitude, String imagePath) {
        Log.d(TAG, "imagePath=" + imagePath);
        //判断各个内容是否为空
        if (StringUtils.isEmpty(name)) {
            view.showReqResult("圈子名称不能为空！");
            return;
        }
        if (StringUtils.isEmpty(introduction)) {
            view.showReqResult("圈子简介不能为空！");
            return;
        }
        int user_id = getUser_id();
        String token = getToken();
        if (user_id == -1 || StringUtils.isEmpty(token)) {
            //关闭当前页面，退出登录
            view.showReqResult("宝宝先去登录再操作哦！");
            return;
        }
        String authorization = Config.HEADER_BEARER + getToken();
        view.showLoading("");
        if (StringUtils.isEmpty(imagePath)) {
            responsitory.createCircle(
                    user_id, school_id, name, introduction, longitude, longitude, authorization, null, null,
                    circleCreateApiCallback);
        } else {
            //设置头像
            MultipartBody.Part part = FileUtils.getPartFromFile(imagePath, Config.KEY_IMAGE);
            responsitory.createCircle(
                    user_id, school_id, name, introduction, longitude, longitude, authorization, part, Config.FLAG_CIRCLE,
                    circleCreateApiCallback);
        }
    }

    /**
     * 创建圈子的回调
     */
    private ApiCallback<ApiResponse<CircleCreateEntity>> circleCreateApiCallback = new ApiCallback<ApiResponse<CircleCreateEntity>>() {
        @Override
        public void onSuccess(Response<ApiResponse<CircleCreateEntity>> response) {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("创建成功，宝宝太棒了~");
            }
        }

        @Override
        public void onError(Response<ApiResponse<CircleCreateEntity>> response, String error, ApiResponse<CircleCreateEntity> apiResponse) {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("创建失败~");
            }
        }

        @Override
        public void onFail() {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("请求失败~");
            }
        }
    };

    /**
     * 得到用户id
     */
    private int getUser_id() {
        int user_id = -1;
        UserInfoSingleton info = UserInfoSingleton.getInstance();
        UserAuths auths = info.getUserAuths();
        if (auths != null) {
            user_id = auths.getUser_id();
        } else {
            //设置为未登录状态
            LoginContext.getInstance().setUserState(new LogoutState());
        }
        return user_id;
    }

    /**
     * 得到缓存的token
     */
    public String getToken() {
        String token = null;
        UserInfoSingleton info = UserInfoSingleton.getInstance();
        UserAuths auths = info.getUserAuths();
        if (auths != null) {
            token = auths.getToken();
        } else {
            //设置为未登录状态
            LoginContext.getInstance().setUserState(new LogoutState());
        }
        return token;
    }
}
