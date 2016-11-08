package com.jkb.core.data.circle.userInCircle;

import com.jkb.core.data.info.user.UserInfo;

/**
 * 圈子成员
 * Created by JustKiddingBaby on 2016/11/3.
 */

public class UserInCircle {

    private UserInfo userInfo;

    private boolean hasInBlackList;

    private int circle_id;


    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isHasInBlackList() {
        return hasInBlackList;
    }

    public void setHasInBlackList(boolean hasInBlackList) {
        this.hasInBlackList = hasInBlackList;
    }

    public int getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(int circle_id) {
        this.circle_id = circle_id;
    }
}
