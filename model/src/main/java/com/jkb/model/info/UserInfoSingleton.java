package com.jkb.model.info;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;

/**
 * 用户信息的单例类
 * Created by JustKiddingBaby on 2016/8/7.
 */

public class UserInfoSingleton {

    private Users users;
    private UserAuths userAuths;

    private static UserInfoSingleton mInstance;

    private UserInfoSingleton() {
        if (users == null) {
            users = new Users();
        }
        if (userAuths == null) {
            userAuths = new UserAuths();
        }
    }


    public static UserInfoSingleton getInstance() {
        if (mInstance == null) {
            mInstance = new UserInfoSingleton();
        }
        return mInstance;
    }


    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        if (users == null) {
            return;
        }
        this.users = users;
    }

    public UserAuths getUserAuths() {
        return userAuths;
    }

    public void setUserAuths(UserAuths userAuths) {
        if (userAuths == null) {
            return;
        }
        this.userAuths = userAuths;
    }

    /**
     * 释放资源
     */
    public void recycleData() {
        if (userAuths != null) {
            userAuths = new UserAuths();
        }
        if (users != null) {
            users = new Users();
        }
    }
}
