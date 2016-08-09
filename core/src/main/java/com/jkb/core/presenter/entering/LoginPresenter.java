package com.jkb.core.presenter.entering;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.LoginEntity;
import com.jkb.api.config.Config;
import com.jkb.core.contract.entering.LoginContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LoginState;
import com.jkb.model.entering.login.LoginDataSource;
import com.jkb.model.entering.login.LoginResponsitory;
import com.jkb.model.entering.login.ThirdPlatformData;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.FormatUtils;
import com.jkb.model.utils.StringUtils;

import java.util.List;

import jkb.mrcampus.db.entity.Status;
import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import retrofit2.Response;

/**
 * 登录页面的Presenter层
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class LoginPresenter implements LoginContract.Presenter {
    public static final String TAG = "LoginPresenter";

    private LoginContract.View loginView;
    private LoginResponsitory loginResponsitory;

    private String identity_type;
    private String userName;
    private String passWord;

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
        //判断手机号还是邮箱号
        if (FormatUtils.judgeEmailFormat(userName)) {
            //邮箱号登录
            loginView.showLoading("请稍等...");
            loginByEmail(userName, passWord);
        } else if (FormatUtils.judgePhoneFormat(userName)) {
            //手机号登录
            loginView.showLoading("请稍等...");
            loginByPhone(userName, passWord);
        }
    }

    /**
     * 手机号登录
     */
    private void loginByPhone(String userName, String passWord) {
        identity_type = "phone";
        this.userName = userName;
        this.passWord = passWord;
        loginResponsitory.loginWithPhone(userName, passWord, loginApiCallback);
    }

    /**
     * 通过邮箱登录
     */
    private void loginByEmail(String userName, String passWord) {
        identity_type = "email";
        this.userName = userName;
        this.passWord = passWord;
        loginResponsitory.loginWithEmail(userName, passWord, loginApiCallback);
    }

    @Override
    public void loginByQQ() {
//        loginView.showLoading("请稍等...");
        Log.d(TAG, "loginByQQ");
        identity_type = "qq";
        loginResponsitory.loginByQQ(thirdPlatformListener);
    }

    @Override
    public void loginByWeChat() {
        loginView.showLoading("请稍等...");
        Log.d(TAG, "loginByWeChat");
        identity_type = "wechat";
        loginResponsitory.loginByWechat(thirdPlatformListener);
    }

    @Override
    public void loginByWeiBo() {
        loginView.showLoading("请稍等...");
        Log.d(TAG, "loginByWeiBo");
        identity_type = "weibo";
        loginResponsitory.loginByWeibo(thirdPlatformListener);
    }

    @Override
    public void loginByRenRen() {
        Log.d(TAG, "loginByRenRen");
        identity_type = "renren";
        loginResponsitory.loginByRenRen(thirdPlatformListener);
    }

    @Override
    public void loginByDouBan() {
        loginView.showLoading("请稍等...");
        Log.d(TAG, "loginByDouBan");
        identity_type = "douban";
        loginResponsitory.loginByDouBan(thirdPlatformListener);
    }

    @Override
    public void lazyLoginIntput(String userName, String passWord) {
        //设置用户名、密码
        loginView.setUserName(userName);//设置帐号
        loginView.setPassWord(passWord);//设置密码
    }

    /**
     * 登录的回调接口
     */
    private ApiCallback<ApiResponse<LoginEntity>> loginApiCallback = new ApiCallback<ApiResponse<LoginEntity>>() {
        @Override
        public void onSuccess(Response<ApiResponse<LoginEntity>> response) {
            loginView.dismissLoading();
            loginView.showReqResult("登录成功，宝宝好棒");
            //存储数据到数据库中
            saveUserDataToDb(response.body().getMsg());
        }

        @Override
        public void onError(Response<ApiResponse<LoginEntity>> response, String error, ApiResponse<LoginEntity> apiResponse) {
            loginView.dismissLoading();
            loginView.showReqResult("帐号或密码错误");
        }

        @Override
        public void onFail() {
            if (loginView.isActive()) {
                loginView.dismissLoading();
                loginView.showReqResult("请求失败，网路错误，请重试");
            }
        }
    };

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
                new ApiCallback<ApiResponse<LoginEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<LoginEntity>> response) {
                        loginView.dismissLoading();
                        loginView.showReqResult("登录成功");
                        //清空帐号密码
                        userName = null;
                        passWord = null;
                        //保存数据
                        saveUserDataToDb(response.body().getMsg());
                    }

                    @Override
                    public void onError(Response<ApiResponse<LoginEntity>> response, String error, ApiResponse<LoginEntity> apiResponse) {
                        loginView.dismissLoading();
                        loginView.showReqResult("登录失败");
                    }

                    @Override
                    public void onFail() {
                        loginView.dismissLoading();
                        loginView.showReqResult("连接失败");
                    }
                }
        );
    }

    /**
     * 保存得到的用户数据
     */
    private void saveUserDataToDb(LoginEntity body) {
        Log.d(TAG, "saveUserDataToDb");
        Users users = saveUsersDataToDb(body);
        UserAuths userAuths = saveUserAuthsDataToDb(body);
        saveStatusDataToDb(body);//保存到状态表中
        //切换身份，设置为已经登录
        LoginContext.getInstance().setUserState(new LoginState());
        UserInfoSingleton.getInstance().setUserAuths(userAuths);//设置个人数据
        UserInfoSingleton.getInstance().setUsers(users);//设置个人数据
        //设置头像到系统中
        saveUserAvatarBitmap();
    }

    /**
     * 保存用户头像
     */
    private void saveUserAvatarBitmap() {
        Log.d(TAG, "saveUserAvatarBitmap");
        Users users = UserInfoSingleton.getInstance().getUsers();
        String avatarUrl = users.getAvatar();
        if (!StringUtils.isEmpty(avatarUrl)) {
            loginResponsitory.getBitmapFromUrl(avatarUrl, bitmapDataCallback);
        } else {
            loginSystem();//进入系统
        }
    }

    /**
     * 得到网络加载的bitmap对象回调
     */
    private LoginDataSource.BitmapDataCallback bitmapDataCallback = new LoginDataSource.BitmapDataCallback() {
        @Override
        public void onBitmapDataLoaded(Bitmap bitmap) {
            Log.d(TAG, "bitmapDataCallback-------->获取头像成功！");
            UserInfoSingleton.getInstance().setUserAvatar(bitmap);
            String UID = UserInfoSingleton.getInstance().getUsers().getUID();
            //缓存并且设置到本地
            loginResponsitory.cacheBitmapToFile(Config.PATH_ROOT_IMAGE, UID, bitmap, bitmapToFileDataCallback);
        }

        @Override
        public void onDataNotAvailable() {
            Log.d(TAG, "bitmapDataCallback-------->获取头像失败！");
            UserInfoSingleton.getInstance().setUserAvatar(null);
            //进入系统
            loginSystem();
        }
    };
    /**
     * 转换bitmap为file并得到path的回调
     */
    private LoginDataSource.BitmapToFileDataCallback bitmapToFileDataCallback = new LoginDataSource.BitmapToFileDataCallback() {
        @Override
        public void onBitmapDataLoaded(String path) {
            Log.d(TAG, "bitmapToFileDataCallback-------->存储头像成功！");
            Users users = UserInfoSingleton.getInstance().getUsers();
            users.setAvatarLocalPath(path);
            loginResponsitory.saveUserToDb(users);
            UserInfoSingleton.getInstance().setUsers(users);
            //进入系统
            loginSystem();
        }

        @Override
        public void onDataNotAvailable(Bitmap bitmap) {
            Log.d(TAG, "bitmapToFileDataCallback-------->存储头像失败！");
            loginSystem();
        }
    };


    /**
     * 保存Status数据到数据库表中
     */
    private void saveStatusDataToDb(LoginEntity body) {
        Log.d(TAG, "saveStatusDataToDb");
        LoginEntity.UserInfoBean bean = body.getUserInfo();
        if (bean == null) {
            return;
        }
        int userId = bean.getId();
        Status status = new Status();
        status.setVersion(loginResponsitory.getCurrentVersion());
        status.setFlag_login(true);
        status.setUser_id(userId);
        status.setCreated_at(StringUtils.getSystemCurrentTime());
        loginResponsitory.saveStatusToDb(status);
    }

    /**
     * 保存UserAuths到数据库
     */
    private UserAuths saveUserAuthsDataToDb(LoginEntity body) {
        Log.d(TAG, "saveUserAuthsDataToDb");
        LoginEntity.UserInfoBean bean = body.getUserInfo();
        if (bean == null) {
            return null;
        }
        UserAuths userAuths = new UserAuths();
        userAuths.setUser_id(bean.getId());
        userAuths.setToken(body.getToken());
        userAuths.setIdentity_type(identity_type);
        userAuths.setUpdated_at(StringUtils.getSystemCurrentTime());
        userAuths.setCredential(passWord);
        userAuths.setIdentifier(userName);
        loginResponsitory.saveUserAuthsToDb(userAuths);
        return userAuths;
    }

    /**
     * 保存Users表的数据
     */
    private Users saveUsersDataToDb(LoginEntity body) {
        Log.d(TAG, "saveUsersDataToDb");
        LoginEntity.UserInfoBean bean = body.getUserInfo();
        if (bean == null) {
            return null;
        }
        Users users = new Users();
        users.setUser_id(bean.getId());
        users.setUID(bean.getUID());
        users.setNickname(bean.getNickname());
        users.setAvatar(bean.getAvatar());
        users.setSex(bean.getSex());
        users.setName(bean.getName());
        users.setBref_introduction(bean.getBref_introduction());
        users.setBackground(bean.getBackground());
        users.setUpdated_at(StringUtils.getSystemCurrentTime());

        LoginEntity.UserInfoBean.SchoolInfoBean schoolInfoBean = bean.getSchoolInfo();
        if (schoolInfoBean != null) {
            users.setSchool_id(schoolInfoBean.getId());
            users.setUpdated_at(StringUtils.getSystemCurrentTime());
        }

        loginResponsitory.saveUserToDb(users);
        return users;
    }

    /**
     * 从数据库中取出Users和UserAuths数据
     */
    private void getUserDataFromDb() {
        Log.d(TAG, "getUserDataFromDb");
        loginResponsitory.getUserAuthsDataFromDb(new LoginDataSource.UserAuthsDataCallback() {
            @Override
            public void onUserDataLoaded(List<UserAuths> userAuthses) {
                String userName;
                String passWord;
                //初始化帐号密码
                for (int i = userAuthses.size() - 1; i >= 0; i--) {
                    UserAuths auths = userAuthses.get(i);
                    userName = auths.getIdentifier();
                    passWord = auths.getCredential();
                    Log.i(TAG, "userName------->>" + userName);
                    Log.i(TAG, "passWord------->>" + passWord);
                    Log.i(TAG, "identifyType=" + auths.getIdentity_type());
                    if (userName != null && passWord != null) {
                        lazyLoginIntput(auths.getIdentifier(), auths.getCredential());
                        break;
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onDataNotAvailable-------->没有数据");
                lazyLoginIntput("", "");
            }
        });
    }

    /**
     * 进入系统
     */
    private void loginSystem() {
        if (loginView.isActive()) {
            loginView.loginSuccess();
        }
    }

    @Override
    public void start() {
        //得到缓存的用户名密码
        getUserDataFromDb();
    }

}
