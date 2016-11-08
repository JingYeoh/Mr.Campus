package com.jkb.core.data.index.hot.user;

import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.core.data.index.hot.HotDynamic;

/**
 * 推荐用户的热门动态
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class UserDynamic extends HotDynamic {

    private boolean isDataEffective;
    private int user_id;
    private String nickname;
    private String avatar;
    private String sex;
    private String name;
    private String bref_introduction;
    private int count_of_fan;
    private int count_of_visitor;
    private boolean has_attention;

    public UserDynamic(DynamicPopularListEntity.DataBean hotDynamicData) {
        //解析数据
        handleUserDynamic(hotDynamicData);
    }

    @Override
    public HotDynamic getHotDynamic() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 解析用户数据
     */
    private void handleUserDynamic(DynamicPopularListEntity.DataBean hotDynamicData) {
        if (hotDynamicData == null) {
            isDataEffective = false;
            return;
        }
        DynamicPopularListEntity.DataBean.UserBean user = hotDynamicData.getUser();
        if (user == null) {
            isDataEffective = false;
            return;
        }
        isDataEffective = true;
        setUser_id(user.getUser_id());
        setNickname(user.getNickname());
        setAvatar(user.getAvatar());
        setSex(user.getSex());
        setBref_introduction(user.getBref_introduction());
        setCount_of_fan(user.getCount_of_fan());
        setCount_of_visitor(user.getCount_of_visitor());
        if (user.getHas_attention() == 0) {
            setHas_attention(false);
        } else {
            setHas_attention(true);
        }
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBref_introduction() {
        return bref_introduction;
    }

    public void setBref_introduction(String bref_introduction) {
        this.bref_introduction = bref_introduction;
    }

    public int getCount_of_fan() {
        return count_of_fan;
    }

    public void setCount_of_fan(int count_of_fan) {
        this.count_of_fan = count_of_fan;
    }

    public int getCount_of_visitor() {
        return count_of_visitor;
    }

    public void setCount_of_visitor(int count_of_visitor) {
        this.count_of_visitor = count_of_visitor;
    }

    public boolean isHas_attention() {
        return has_attention;
    }

    public void setHas_attention(boolean has_attention) {
        this.has_attention = has_attention;
    }
}
