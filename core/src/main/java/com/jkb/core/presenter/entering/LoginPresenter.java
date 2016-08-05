package com.jkb.core.presenter.entering;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.entity.auth.LoginEntity;
import com.jkb.api.config.Config;
import com.jkb.core.contract.entering.LoginContract;
import com.jkb.model.entering.login.LoginDataSource;
import com.jkb.model.entering.login.LoginResponsitory;
import com.jkb.model.entering.login.ThirdPlatformData;

import retrofit2.Response;

/**
 * 登录页面的Presenter层
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class LoginPresenter implements LoginContract.Presenter {
    public static final String TAG = "LoginPresenter";

    private LoginContract.View loginView;
    private LoginResponsitory loginResponsitory;

    public LoginPresenter(@NonNull LoginContract.View loginView, @NonNull LoginResponsitory loginResponsitory) {
        this.loginView = loginView;
        this.loginResponsitory = loginResponsitory;

        this.loginView.setPresenter(this);
    }

    @Override
    public void loginByInput(String userName, String passWord) {
        if (userName.trim().isEmpty() || passWord.trim().isEmpty()) {
            loginView.showLoginResult("帐号或密码不能为空！");
            return;
        }
        //请求登录
        loginView.showLoading("请稍等...");
    }

    @Override
    public void loginByQQ() {
//        loginView.showLoading("请稍等...");
        Log.d(TAG, "loginByQQ");
        loginResponsitory.loginByQQ(thirdPlatformListener);
    }

    @Override
    public void loginByWeChat() {
        loginView.showLoading("请稍等...");
        Log.d(TAG, "loginByWeChat");
        loginResponsitory.loginByWechat(thirdPlatformListener);
    }

    @Override
    public void loginByWeiBo() {
        loginView.showLoading("请稍等...");
        Log.d(TAG, "loginByWeiBo");
        loginResponsitory.loginByWeibo(thirdPlatformListener);
    }

    @Override
    public void loginByRenRen() {
        Log.d(TAG, "loginByRenRen");
        loginResponsitory.loginByRenRen(thirdPlatformListener);
    }

    @Override
    public void loginByDouBan() {
        loginView.showLoading("请稍等...");
        Log.d(TAG, "loginByDouBan");
        loginResponsitory.loginByDouBan(thirdPlatformListener);
    }

    /**
     * 请求的监听接口
     */
    private LoginDataSource.ThirdPlatformListener thirdPlatformListener
            = new LoginDataSource.ThirdPlatformListener() {
        @Override
        public void onSuccess(ThirdPlatformData data) {
            Log.d(TAG, "thirdPlatformListener.onSuccess");
//            loginView.dismissLoading();
            //再次请求登录接口
            loginByThirdPlatform(data);
        }

        @Override
        public void onFail(String value) {
            loginView.showReqResult(value);
        }

        @Override
        public void onCancle() {
//            loginView.dismissLoading();
            loginView.showReqResult("取消登录");
        }
    };

    /**
     * 第三方登录接口
     *
     * @param data
     */
    private void loginByThirdPlatform(ThirdPlatformData data) {
        Log.d(TAG, "loginByThirdPlatform");
        loginView.showLoading("加载中...");
        String sex = data.getGender();
        if (sex != null) {
            if (sex.equals(Config.GENDER_M)) {
                sex = Config.SEX_MAN;
            } else if (sex.equals(Config.GENDER_F)) {
                sex = Config.SEX_FEMAN;
            }
        }
        String token = data.getToken();
        String icon = data.getIcon();//头像
        String userID = data.getUserID();//用户id
        String nickname = data.getNickname();//昵称
        String identity_type = data.getIdentity_type();
        //请求登录接口
        loginResponsitory.loginByThirdPlatform(
                nickname, userID, identity_type, sex, icon, token,
                null,
                new ApiCallback<LoginEntity>() {
                    @Override
                    public void onSuccess(Response<LoginEntity> response) {
                        loginView.dismissLoading();
                        loginView.showReqResult("登录成功");
                        //处理请求成功的数据,切换身份
                    }

                    @Override
                    public void onError(Response<LoginEntity> response, String error,
                                        LoginEntity apiResponse) {
                        loginView.dismissLoading();
                        loginView.showReqResult("登录失败");
                    }

                    @Override
                    public void onFail() {
                        loginView.dismissLoading();
                        loginView.showReqResult("登录失败");
                    }
                }
        );
    }

    @Override
    public void start() {
        //得到缓存的用户名密码

        //设置用户名、密码
        loginView.setUserName("");//设置帐号
        loginView.setPassWord("");//设置密码
    }
}
