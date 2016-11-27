package com.jkb.core.data.search.detail.user;

import com.jkb.api.entity.search.SearchEntity;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.core.data.search.detail.SearchData;

/**
 * 搜索的用户数据类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchUserData extends SearchData {

    private UserInfo user;

    public SearchUserData(SearchEntity.DataBean dataBean) {
        handleUserData(dataBean);
    }

    @Override
    public SearchData getSearchData() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理用户数据
     */
    private void handleUserData(SearchEntity.DataBean dataBean) {
        if (dataBean == null) {
            isDataEffective = false;
            return;
        }
        SearchEntity.DataBean.UserBean reqUser = dataBean.getUser();
        if (reqUser == null) {
            isDataEffective = false;
            return;
        }
        user = new UserInfo();
        user.setId(reqUser.getId());
        user.setUID(reqUser.getUID());
        user.setNickName(reqUser.getNickname());
        user.setAvatar(reqUser.getAvatar());
        user.setCount_of_fan(reqUser.getCount_of_fan());
        user.setHas_attention(reqUser.getHas_attention() != 0);
        setUser(user);
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
