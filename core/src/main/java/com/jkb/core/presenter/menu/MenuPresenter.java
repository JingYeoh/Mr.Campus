package com.jkb.core.presenter.menu;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.auth.LoginEntity;
import com.jkb.core.contract.menu.MenuContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LoginState;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.entering.login.LoginDataSource;
import com.jkb.model.dataSource.entering.login.LoginResponsitory;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import java.io.Serializable;

import jkb.mrcampus.db.entity.Status;
import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import retrofit2.Response;

/**
 * 主体的P层
 * Created by JustKiddingBaby on 2016/7/24.
 */
public class MenuPresenter implements MenuContract.Presenter {

    private MenuContract.View menuView;
    private LoginResponsitory loginResponsitory;
    private static final String TAG = "MenuPresenter";

    private String identity_type, userName, passWord;

    /**
     * 展示的视图
     */
    public enum SHOW_VIEW implements Serializable {
        /**
         * 首页
         */
        HOMEPAGE,
        /**
         * 设置
         */
        SETTING,
        /**
         * 工具
         */
        TOOLS,
        /**
         * 专题
         */
        FEATURE
    }

    /**
     * 展示的视图
     */
    private SHOW_VIEW currentView = SHOW_VIEW.HOMEPAGE;

//    public SHOW_VIEW getCurrentView() {
//        return currentView;
//    }

    public void setCurrentView(SHOW_VIEW currentView) {
        this.currentView = currentView;
//        showFragment();
    }

    public MenuPresenter(MenuContract.View menuView, LoginResponsitory loginResponsitory) {
        this.menuView = menuView;
        this.loginResponsitory = loginResponsitory;

        menuView.setPresenter(this);
    }

    @Override
    public boolean getIdentity() {
        return false;
    }

    @Override
    public void ReqLogin() {
        //判断是否选择了学校
        boolean selectedSchool = SchoolInfoSingleton.getInstance().isSelectedSchool();
        if (selectedSchool) {

        } else {

        }
        //判断是否登录
        if (!LoginContext.getInstance().isLogined()) {

        } else {
            UserInfoSingleton info = UserInfoSingleton.getInstance();
            UserAuths auths = info.getUserAuths();
            if (auths == null) {
                LoginContext.getInstance().setUserState(new LogoutState());
                return;
            }
            String identity_type = auths.getIdentity_type();
            String credential = auths.getCredential();
            String identifier = auths.getIdentifier();
            String nickName = info.getUsers().getNickname();
            //请求登录接口
            login(identity_type, identifier, credential, nickName);
        }
    }

    /**
     * 请求登录
     *
     * @param identity_type 帐号类型
     * @param identifier    帐号
     * @param credential    密码
     * @param nickName      昵称
     */
    private void login(String identity_type, String identifier, String credential, String nickName) {
        this.identity_type = identity_type;
        this.userName = identifier;
        this.passWord = credential;
        switch (identity_type) {
            case Config.IDENTITY_TYPE_PHONE://手机号登录
                loginResponsitory.loginWithPhone(identifier, credential, loginApiCallback);
                break;
            case Config.IDENTITY_TYPE_EMAIL://邮箱登录
                loginResponsitory.loginWithEmail(identifier, credential, loginApiCallback);
                break;
            case Config.IDENTITY_TYPE_QQ://第三方登录:QQ
            case Config.IDENTITY_TYPE_WECHAT://第三方登录:微信
            case Config.IDENTITY_TYPE_WEIBO://第三方登录:微博
            case Config.IDENTITY_TYPE_RENREN://第三方登录:人人
            case Config.IDENTITY_TYPE_DOUBAN://第三方登录:豆瓣
                loginResponsitory.loginByThirdPlatform(nickName, identifier,
                        identity_type, null, null, null, null, loginApiCallback);
                break;
        }
    }

    private ApiCallback<ApiResponse<LoginEntity>> loginApiCallback = new
            ApiCallback<ApiResponse<LoginEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<LoginEntity>> response) {
                    //存储到数据库中
                    saveDataToUserInfo(response.body());
                }

                @Override
                public void onError(
                        Response<ApiResponse<LoginEntity>> response, String error,
                        ApiResponse<LoginEntity> apiResponse) {
                    //设置为登录失败，切换身份
                    if (menuView.isActive()) {
                        menuView.showReqResult("登录过期，请重新登录");
                    }
                    changeStatusToLogout();
                }

                @Override
                public void onFail() {
                    //
                    if (menuView.isActive()) {
                        menuView.showReqResult("请求失败，请检查您的网络");
                    }
                }
            };

    /**
     * 保存到用户状态类中
     */
    private void saveDataToUserInfo(ApiResponse<LoginEntity> body) {
        if (body == null) {
            changeStatusToLogout();
            return;
        }
        LoginEntity entity = body.getMsg();
        if (entity == null) {
            changeStatusToLogout();
            return;
        }
        UserInfoSingleton.getInstance().setUserAuths(saveUserAuthsDataToDb(entity));//设置个人数据
        UserInfoSingleton.getInstance().setUsers(saveUsersDataToDb(entity));//设置个人数据
        saveStatusDataToDb(entity);//保存到状态表中
        //切换身份，设置为已经登录
        LoginContext.getInstance().setUserState(new LoginState());
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
        }
    }

    /**
     * 得到网络加载的bitmap对象回调
     */
    private LoginDataSource.BitmapDataCallback bitmapDataCallback = new
            LoginDataSource.BitmapDataCallback() {
                @Override
                public void onBitmapDataLoaded(Bitmap bitmap) {
                    Log.d(TAG, "bitmapDataCallback-------->获取头像成功！");
//            UserInfoSingleton.getInstance().setUserAvatar(bitmap);
                    String UID = UserInfoSingleton.getInstance().getUsers().getUID();

                    LoginContext.getInstance().setUserState(new LoginState());//更新回调接口数据
                    //缓存并且设置到本地
//                    loginResponsitory.cacheBitmapToFile(Config.PATH_ROOT_IMAGE, UID, bitmap,
//                            bitmapToFileDataCallback);
                }

                @Override
                public void onDataNotAvailable() {
                    Log.d(TAG, "bitmapDataCallback-------->获取头像失败！");
                }
            };
    /**
     * 转换bitmap为file并得到path的回调
     */
    private LoginDataSource.BitmapToFileDataCallback bitmapToFileDataCallback = new
            LoginDataSource.BitmapToFileDataCallback() {
                @Override
                public void onBitmapDataLoaded(String path) {
                    Log.d(TAG, "bitmapToFileDataCallback-------->存储头像成功！");
                    Users users = UserInfoSingleton.getInstance().getUsers();
                    users.setAvatarLocalPath(path);
                    loginResponsitory.saveUserToDb(users);
                    UserInfoSingleton.getInstance().setUsers(users);
                }

                @Override
                public void onDataNotAvailable(Bitmap bitmap) {
                    Log.d(TAG, "bitmapToFileDataCallback-------->存储头像失败！");
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
        boolean selectedSchool = SchoolInfoSingleton.getInstance().isSelectedSchool();
        if (!selectedSchool) {
            status.setFlag_select_school(false);
            status.setSchool_id(0);
        } else {
            status.setFlag_select_school(true);
            status.setSchool_id(SchoolInfoSingleton.getInstance().getSchool().getSchool_id());
        }
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
        users.setAttentionCount(bean.getAttentionCount());
        users.setAttentionUserCount(bean.getAttentionUserCount());
        users.setFansCount(bean.getFansCount());
        users.setVisitorCount(bean.getVisitorCount());
        LoginEntity.UserInfoBean.SchoolInfoBean schoolInfoBean = bean.getSchoolInfo();
        if (schoolInfoBean != null) {
            users.setSchool_id(schoolInfoBean.getId());
            users.setUpdated_at(StringUtils.getSystemCurrentTime());
        }
        loginResponsitory.saveUserToDb(users);
        return users;
    }

    /**
     * 切换为登录失败的状态
     */
    private void changeStatusToLogout() {
        LoginContext.getInstance().setUserState(new LogoutState());
        UserInfoSingleton.getInstance().recycleData();
    }

    @Override
    public void start() {
//        showFragment();
        ReqLogin();
    }
}
