package com.jkb.core.presenter.personCenter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.core.contract.personCenter.PersonCenterContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.personCenter.PersonCenterDataResponsitory;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.intfc.BitmapLoadedCallback;
import com.jkb.model.utils.StringUtils;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import retrofit2.Response;

/**
 * 个人中心的P层
 * Created by JustKiddingBaby on 2016/8/15.
 */

public class PersonCenterPresenter implements PersonCenterContract.Presenter {

    private PersonCenterContract.View view;
    private int user_id = -1;
    private PersonCenterDataResponsitory responsitory;


    public PersonCenterPresenter(@NonNull PersonCenterContract.View view,
                                 @NonNull PersonCenterDataResponsitory responsitory) {
        this.view = view;
        this.responsitory = responsitory;

        view.setPresenter(this);
    }

    @Override
    public void initSelfUserData() {
        Users users = getUsers();
        if (users == null) {
            return;
        }

        view.showContentView();//显示视图

        UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
        view.setHeadImg(userInfo.getUserAvatar());
        view.setUserName(users.getNickname());
        view.setUserSign(users.getBref_introduction());
        String name = (StringUtils.isEmpty(users.getName()) ? users.getNickname() + "菌" : users.getName());
        view.setName(name);

        //设置关注栏的数据
        view.setFansNum(users.getFansCount());
        view.setVistiorsNum(users.getVisitorCount());
        view.setWatchedNum(users.getAttentionCount());
    }

    @Override
    public void initNonSelfUserData() {
        //初始化非自己的数据
        UserAuths auths = getUserAuths();
        if (auths == null) {
            return;
        }
        String Authorization = Config.HEADER_BEARER + auths.getToken();

        responsitory.getUserInfo(Authorization, user_id, userInfoApiCallback);
    }

    @Override
    public void getUserData() {
        //初始化非自己的数据
        UserAuths auths = getUserAuths();
        if (auths == null) {
            return;
        }
        String Authorization = Config.HEADER_BEARER + auths.getToken();

        responsitory.getUserInfo(Authorization, user_id, userInfoApiCallback);
    }

    /**
     * 获取用户数据的回调接口
     */
    private ApiCallback<ApiResponse<UserInfoEntity>> userInfoApiCallback = new ApiCallback<ApiResponse<UserInfoEntity>>() {
        @Override
        public void onSuccess(Response<ApiResponse<UserInfoEntity>> response) {
            if (view.isActive()) {
                view.dismissLoading();
                //处理数据并显示
                showNonSelfUserData(response.body());
            }
        }

        @Override
        public void onError(Response<ApiResponse<UserInfoEntity>> response, String error, ApiResponse<UserInfoEntity> apiResponse) {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("请求失败");
            }
        }

        @Override
        public void onFail() {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("请求失败，请检查您的网络连接是否正常");
            }
        }
    };

    /**
     * 显示数据
     */
    private void showNonSelfUserData(ApiResponse<UserInfoEntity> body) {
        UserInfoEntity entity = body.getMsg();
        UserInfoEntity.UserInfoBean infoBean = entity.getUserInfo();
        if (infoBean == null) {
            return;
        }

        view.showContentView();//显示视图

        if (!StringUtils.isEmpty(infoBean.getAvatar())) {
            //加载头像
            responsitory.loadHeadImgByUrl(infoBean.getAvatar(), bitmapLoadedCallback);
        }

        view.setUserName(infoBean.getNickname());
        view.setUserSign(infoBean.getBref_introduction());
        String name = (StringUtils.isEmpty(infoBean.getName()) ? infoBean.getNickname() + "菌" : infoBean.getName());
        view.setName(name);

        //设置关注栏的数据
        view.setFansNum(infoBean.getFansCount());
        view.setVistiorsNum(infoBean.getVisitorCount());
        view.setWatchedNum(infoBean.getAttentionCount());
    }

    /**
     * 头像加载的回调
     */
    private BitmapLoadedCallback bitmapLoadedCallback = new BitmapLoadedCallback() {
        @Override
        public void onBitmapDataLoaded(Bitmap bitmap) {
            if (view.isActive()) {
                view.setHeadImg(bitmap);
            }
        }

        @Override
        public void onDataNotAvailable(String url) {
        }
    };
    /**
     * 访客的回调接口
     */
    private ApiCallback<ApiResponse<OperationActionEntity>> visitorApiCallback = new
            ApiCallback<ApiResponse<OperationActionEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                    getUserData();
                }

                @Override
                public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                    String error, ApiResponse<OperationActionEntity> apiResponse) {
                    getUserData();
                }

                @Override
                public void onFail() {
                    getUserData();
                }
            };

    @Override
    public void judgeUserToSetTitleStyle() {
        Users users = getUsers();
        if (user_id == users.getUser_id()) {
            //设置自己的样式
            view.showSelfTitleStyle();
            //初始化自己的数据
//            initSelfUserData();
        } else {
            //设置非自己的样式
            view.showNonSelfTitleStyle();
            //初始化非自己的数据
        }
//        initNonSelfUserData();
    }

    @Override
    public void getCircleData() {
        //从网上获取圈子数据

        //判断数据是否为空
        view.showCircleNonDataView();
    }

    @Override
    public void visit() {
        if (!LoginContext.getInstance().isLogined()) {
            return;
        }
        UserAuths auths = getUserAuths();
        String Authorization = Config.HEADER_BEARER + auths.getToken();
        responsitory.visit(Authorization, auths.getUser_id(), user_id, visitorApiCallback);
    }

    /**
     * 得到用户数据
     */
    private Users getUsers() {
        UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
        Users users = userInfo.getUsers();
        if (users == null) {
            LoginContext.getInstance().setUserState(new LogoutState());
            view.showReqResult("登录过期，请重新登录~");
        }
        return users;
    }

    /**
     * 得到用户Auth数据
     */
    private UserAuths getUserAuths() {
        UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
        UserAuths auths = userInfo.getUserAuths();
        if (auths == null) {
            LoginContext.getInstance().setUserState(new LogoutState());
            view.showReqResult("登录过期，请重新登录~");
        }
        return auths;
    }

    @Override
    public void start() {
        user_id = view.getUser_id();//得到用户的id
        view.hideContentView();//隐藏显示
        visit();//请求访客接口
        judgeUserToSetTitleStyle();
        //获取圈子数据
        getCircleData();
    }
}
