package com.jkb.core.presenter.first;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.core.contract.first.FirstContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LoginState;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.first.firstlogic.FirstDataResponsitory;
import com.jkb.model.dataSource.first.firstlogic.FirstDataSource;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import jkb.mrcampus.db.entity.Status;
import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * FirstActivity控制器的逻辑Presenter类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public class FirstPresenter implements FirstContract.Presenter {

    private FirstDataResponsitory firstDataResponsitory;
    private FirstContract.View firstView;

    private static final String TAG = "FirstPresenter";

    /**
     * 初始化Presenter并且绑定View
     *
     * @param firstDataResponsitory
     * @param firstView
     */
    public FirstPresenter(@NonNull FirstDataResponsitory firstDataResponsitory, @NonNull FirstContract.View firstView) {
        this.firstDataResponsitory = checkNotNull(firstDataResponsitory, "firstDataResponsitory不能为空");
        this.firstView = checkNotNull(firstView, "firstView不能为空");

        this.firstView.setPresenter(this);
    }

    @Override
    public void start() {
        chooseFragment();
    }

    @Override
    public void chooseFragment() {
        firstDataResponsitory.getStatusData(statusDataCallback);
    }

    FirstDataSource.StatusDataCallback statusDataCallback = new FirstDataSource.StatusDataCallback() {
        @Override
        public void onStatusDataLoaded(Status status) {
            boolean isLogin = status.getFlag_login();
            String cacheVersion = status.getVersion();
            String currentVersion = firstDataResponsitory.getCurrentVersion();
            if (cacheVersion.equals(currentVersion)) {
                showWelcome();
            } else {
                firstDataResponsitory.cacheStatus(currentVersion, status.getFlag_login(), status.getUser_id(),
                        StringUtils.getSystemCurrentTime());
                showGuide();
            }
            //设置为未登录状态
            if (isLogin) {
                LoginContext loginContext = LoginContext.getInstance();
                loginContext.setUserState(new LoginState());
                //得到个人信息并设置到个人的信息单例类中
                getUsersData(status.getUser_id());
                getUserAuthData(status.getUser_id());
            } else {//设置为已登录状态
                LoginContext loginContext = LoginContext.getInstance();
                loginContext.setUserState(new LogoutState());
            }
        }

        @Override
        public void onDataNotAvailable() {
            Log.d(TAG, "getStatusData-------->>没有得到数据");
            firstView.showFragment(1);
            //设置状态为未登录状态
            firstDataResponsitory.cacheStatus(firstDataResponsitory.getCurrentVersion(), false, 0,
                    StringUtils.getSystemCurrentTime());
            //设置为未登录在状态
            LoginContext loginContext = LoginContext.getInstance();
            loginContext.setUserState(new LogoutState());
        }
    };

    /**
     * 得到个人的信息
     */
    private void getUsersData(int userId) {
        firstDataResponsitory.getUsersData(userId, new FirstDataSource.UsersDataCallback() {
            @Override
            public void onUserDataLoaded(Users users) {
                UserInfoSingleton.getInstance().setUsers(users);
                //设置头像
//                String avatarLocalPath = users.getAvatarLocalPath();
//                if (!StringUtils.isEmpty(avatarLocalPath)) {
//                    firstDataResponsitory.loadHeadImgByLocalPath(avatarLocalPath, bitmapDataCallback);
//                } else {
//                    String avatarUrl = users.getAvatar();
//                    firstDataResponsitory.loadHeadImgByUrl(avatarUrl, bitmapDataCallback);
//                }
            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "getUsersData-->onDataNotAvailable-------->>没有得到数据");
                LoginContext loginContext = LoginContext.getInstance();
                loginContext.setUserState(new LogoutState());
                firstDataResponsitory.cacheStatus(
                        firstDataResponsitory.getCurrentVersion(), false, 0, StringUtils.getSystemCurrentTime());//缓存为未登录
            }
        });
    }

//    /**
//     * 得到头像的Bitmap的回调方法
//     */
//    private FirstDataSource.BitmapDataCallback bitmapDataCallback = new FirstDataSource.BitmapDataCallback() {
//        @Override
//        public void onBitmapDataLoaded(Bitmap bitmap) {
//            UserInfoSingleton.getInstance().setUserAvatar(bitmap);
//        }
//
//        @Override
//        public void onDataNotAvailable(String url) {
//            UserInfoSingleton.getInstance().setUserAvatar(null);
//            //加载本地图片失败后加载网络图片
//            firstDataResponsitory.loadHeadImgByUrl(url, bitmapDataCallback2);
//        }
//    };
//    /**
//     * 得到头像的Bitmap的回调方法
//     */
//    private FirstDataSource.BitmapDataCallback bitmapDataCallback2 = new FirstDataSource.BitmapDataCallback() {
//        @Override
//        public void onBitmapDataLoaded(Bitmap bitmap) {
//            UserInfoSingleton.getInstance().setUserAvatar(bitmap);
//        }
//
//        @Override
//        public void onDataNotAvailable(String url) {
//            UserInfoSingleton.getInstance().setUserAvatar(null);
//        }
//    };

    /**
     * 得到userAuth数据
     */
    private void getUserAuthData(int userId) {
        firstDataResponsitory.getUserAuthData(userId, new FirstDataSource.UserAuthsDataCallback() {
            @Override
            public void onUserDataLoaded(UserAuths userAuths) {
                UserInfoSingleton.getInstance().setUserAuths(userAuths);
            }

            @Override
            public void onDataNotAvailable() {//设置为未登录状态
                Log.d(TAG, "getUserAuthData-->onDataNotAvailable-------->>没有得到数据");
                LoginContext loginContext = LoginContext.getInstance();
                loginContext.setUserState(new LogoutState());
                firstDataResponsitory.cacheStatus(
                        firstDataResponsitory.getCurrentVersion(), false, 0, StringUtils.getSystemCurrentTime());//缓存为未登录
            }
        });
    }

    /**
     * 显示引导页面
     */
    private void showGuide() {
        firstView.showFragment(1);
    }

    /**
     * 显示欢迎页面
     */
    private void showWelcome() {
        firstView.showFragment(0);
    }
}
