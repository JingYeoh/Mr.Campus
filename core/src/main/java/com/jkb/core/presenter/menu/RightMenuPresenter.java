package com.jkb.core.presenter.menu;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.core.contract.menu.RightMenuContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.core.control.userstate.UserState;
import com.jkb.model.dataSource.menuRight.rightMenu.RightMenuDataRepertory;
import com.jkb.model.info.UserInfoSingleton;

import jkb.mrcampus.db.entity.Users;
import retrofit2.Response;

/**
 * 右滑菜单的P层
 * Created by JustKiddingBaby on 2016/8/16.
 */

public class RightMenuPresenter implements RightMenuContract.Presenter {

    private RightMenuContract.View view;
    private RightMenuDataRepertory repertory;

    public RightMenuPresenter(
            @NonNull RightMenuContract.View view,
            @NonNull RightMenuDataRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        this.view.setPresenter(this);
    }

    //请求数据
    private boolean isRequesting = false;

    @Override
    public void getCountData() {
        //设置计数的数据
        UserInfoSingleton info = UserInfoSingleton.getInstance();
        Users users = info.getUsers();
        if (users == null) {
            //切换身份
            LoginContext.getInstance().setUserState(new LogoutState());
            return;
        }
        if (view.isActive()) {
            int attentionUserCount = (users.getAttentionUserCount() == null ? 0 :
                    users.getAttentionUserCount());
            int fansCount = (users.getFansCount() == null ? 0 : users.getFansCount());
            int visitorCount = (users.getVisitorCount() == null ? 0 : users.getVisitorCount());
            view.setAttentionCount(attentionUserCount);
            view.setFansCount(fansCount);
            view.setVisitorCount(visitorCount);
        }
    }

    @Override
    public int getUser_id() {
        UserInfoSingleton info = UserInfoSingleton.getInstance();
        Users users = info.getUsers();
        if (users == null) {
            return 0;
        }
        return users.getUser_id();
    }

    @Override
    public void updatePersonInfo() {
        if (isRequesting) {
            return;
        }
        isRequesting = true;
        if (LoginContext.getInstance().isLogined()) {
            repertory.getUserInfo(getUser_id(), userInfoApiCallback);
        }
    }

    @Override
    public void start() {
        if (LoginContext.getInstance().isLogined()) {
            getUser_id();
            getCountData();
        }
    }

    /**
     * 得到个人数据的回调
     */
    private ApiCallback<ApiResponse<UserInfoEntity>> userInfoApiCallback =
            new ApiCallback<ApiResponse<UserInfoEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<UserInfoEntity>> response) {
                    isRequesting = false;
                    handleData(response.body());
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<UserInfoEntity> body) {
                    if (body == null) {
                        return;
                    }
                    handlePersonInfo(body.getMsg());
                }

                /**
                 * 处理个人数据
                 */
                private void handlePersonInfo(UserInfoEntity msg) {
                    UserInfoEntity.UserInfoBean userInfo = msg.getUserInfo();
                    if (userInfo == null) {
                        return;
                    }
                    UserInfoSingleton instance = UserInfoSingleton.getInstance();
                    Users users = instance.getUsers();
                    users.setAttentionCount(userInfo.getAttentionCount());
                    users.setFansCount(userInfo.getFansCount());
                    users.setVisitorCount(userInfo.getVisitorCount());
                    users.setAvatar(userInfo.getAvatar());
                    users.setNickname(userInfo.getNickname());
                    UserInfoSingleton.getInstance().setUsers(users);
                    getCountData();
                }

                @Override
                public void onError(Response<ApiResponse<UserInfoEntity>> response,
                                    String error, ApiResponse<UserInfoEntity> apiResponse) {
                    isRequesting = false;
                    if (view.isActive()) {
                        view.showReqResult(error);
                    }
                }

                @Override
                public void onFail() {
                    isRequesting = false;
                }
            };
}
