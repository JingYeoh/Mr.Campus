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
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.FileUtils;
import com.jkb.model.utils.StringUtils;

import jkb.mrcampus.db.entity.Schools;
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
        //设置学校名称
        initSchoolInfo();
    }

    @Override
    public void createCircle(
            int school_id, String name, String introduction, String tag, double longitude,
            double latitude, String imagePath) {
        Log.d(TAG, "imagePath=" + imagePath);
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请您先登录再进行操作哦");
            return;
        }
        if (!SchoolInfoSingleton.getInstance().isSelectedSchool()) {
            view.showReqResult("请您先选择学校");
            return;
        }
        //判断各个内容是否为空
        if (StringUtils.isEmpty(name)) {
            view.showReqResult("圈子名称不能为空");
            return;
        }
        if (StringUtils.isEmpty(introduction)) {
            view.showReqResult("圈子简介不能为空");
            return;
        }
        if (StringUtils.isEmpty(tag)) {
            view.showReqResult("圈子标签不能为空");
            return;
        }
        school_id = SchoolInfoSingleton.getInstance().getSchool().getSchool_id();
        int user_id = getUser_id();
        String token = getToken();
        String authorization = Config.HEADER_BEARER + token;
        view.showLoading("");
        if (StringUtils.isEmpty(imagePath)) {
            responsitory.createCircle(
                    user_id, school_id, name, introduction, tag, longitude, longitude,
                    authorization, null, null, circleCreateApiCallback);
        } else {
            //设置头像
            MultipartBody.Part part = FileUtils.getPartFromFile(imagePath, Config.KEY_IMAGE);
            responsitory.createCircle(
                    user_id, school_id, name, introduction, tag, longitude, longitude
                    , authorization, part, Config.FLAG_CIRCLE, circleCreateApiCallback);
        }
    }

    @Override
    public void initSchoolInfo() {
        if (!SchoolInfoSingleton.getInstance().isSelectedSchool()) {
            view.setSchoolName("");
            return;
        }
        Schools schools = getSchoolInfo();
        String school_name = schools.getSchool_name();
        view.setSchoolName(school_name);
    }

    @Override
    public Schools getSchoolInfo() {
        return SchoolInfoSingleton.getInstance().getSchool();
    }

    /**
     * 创建圈子的回调
     */
    private ApiCallback<ApiResponse<CircleCreateEntity>> circleCreateApiCallback
            = new ApiCallback<ApiResponse<CircleCreateEntity>>() {
        @Override
        public void onSuccess(Response<ApiResponse<CircleCreateEntity>> response) {
            if (view.isActive()) {
                view.dismissLoading();
                handleData(response.body());
            }
        }

        /**
         * 解析數據
         */
        private void handleData(ApiResponse<CircleCreateEntity> body) {
            if (body == null) {
                return;
            }
            handleCircleData(body.getMsg());
        }

        /**
         * 处理圈子数据
         */
        private void handleCircleData(CircleCreateEntity msg) {
            if (msg == null) {
                return;
            }
            CircleCreateEntity.CircleInfoBean circleInfo = msg.getCircleInfo();
            if (circleInfo == null) {
                return;
            }
            int id = circleInfo.getId();
            view.createCircleCompleted(id);
        }

        @Override
        public void onError(Response<ApiResponse<CircleCreateEntity>> response,
                            String error, ApiResponse<CircleCreateEntity> apiResponse) {
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
