package com.jkb.core.presenter.personCenter;

import com.jkb.core.contract.personCenter.PersonCenterContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import jkb.mrcampus.db.entity.Users;

/**
 * 个人中心的P层
 * Created by JustKiddingBaby on 2016/8/15.
 */

public class PersonCenterPresenter implements PersonCenterContract.Presenter {

    private PersonCenterContract.View view;


    public PersonCenterPresenter(PersonCenterContract.View view) {
        this.view = view;

        view.setPresenter(this);
    }

    @Override
    public void initUserData() {
        //初始化用户数据
        UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
        Users users = userInfo.getUsers();
        if (users == null) {
            LoginContext.getInstance().setUserState(new LogoutState());
            view.showReqResult("登录过期，请重新登录~");
            return;
        }
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
    public void getCircleData() {
        //从网上获取圈子数据

        //判断数据是否为空
        view.showCircleNonDataView();
    }

    @Override
    public void start() {
        initUserData();
        //获取圈子数据
        getCircleData();
    }
}
