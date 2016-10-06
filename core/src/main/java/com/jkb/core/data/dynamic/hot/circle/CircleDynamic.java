package com.jkb.core.data.dynamic.hot.circle;

import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.core.data.dynamic.hot.HotDynamic;
import com.jkb.core.data.user.UserInfo;

/**
 * 推荐的圈子热门动态
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class CircleDynamic extends HotDynamic {

    private boolean isDataEffective;

    private int circle_id;
    private String circle_name;
    private String circle_picture;
    private String circle_introduction;
    private String circle_type;
    private String circle_created_at;
    private boolean has_subscribe;
    private int count_of_dynamic;
    private int count_of_subscription;
    private double circle_longitude;
    private double circle_latitude;

    private UserInfo user;

    //学校的信息

    public CircleDynamic(DynamicPopularListEntity.DataBean hotDynamicData) {
        handleDynamicCircleData(hotDynamicData);
    }

    @Override
    public HotDynamic getHotDynamic() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理圈子动态数据
     */
    private void handleDynamicCircleData(DynamicPopularListEntity.DataBean hotDynamicData) {
        if (hotDynamicData == null) {
            isDataEffective = false;
            return;
        }
        DynamicPopularListEntity.DataBean.CircleBean circle = hotDynamicData.getCircle();
        if (circle == null) {
            isDataEffective = false;
            return;
        }
        isDataEffective = true;
        setCircle_id(circle.getCircle_id());
        setCircle_name(circle.getCircle_name());
        setCircle_picture(circle.getCircle_picture());
        setCircle_introduction(circle.getCircle_introduction());
        setCircle_type(circle.getCircle_type());
        setCircle_created_at(circle.getCircle_created_at());
        if (circle.getHas_subscribe() == 0) {
            setHas_subscribe(false);
        } else {
            setHas_subscribe(true);
        }
        setCount_of_dynamic(circle.getCount_of_dynamic());
        setCount_of_subscription(circle.getCount_of_subscription());

        //用户数据
        user = new UserInfo();
        user.setId(circle.getCreator_id());
        user.setNickName(circle.getCreator_nickname());
        user.setAvatar(circle.getCreator_avatar());
        setUser(user);
    }

    public int getCircle_id() {
        return circle_id;
    }

    public void setCircle_id(int circle_id) {
        this.circle_id = circle_id;
    }

    public String getCircle_name() {
        return circle_name;
    }

    public void setCircle_name(String circle_name) {
        this.circle_name = circle_name;
    }

    public String getCircle_picture() {
        return circle_picture;
    }

    public void setCircle_picture(String circle_picture) {
        this.circle_picture = circle_picture;
    }

    public String getCircle_introduction() {
        return circle_introduction;
    }

    public void setCircle_introduction(String circle_introduction) {
        this.circle_introduction = circle_introduction;
    }

    public String getCircle_type() {
        return circle_type;
    }

    public void setCircle_type(String circle_type) {
        this.circle_type = circle_type;
    }

    public String getCircle_created_at() {
        return circle_created_at;
    }

    public void setCircle_created_at(String circle_created_at) {
        this.circle_created_at = circle_created_at;
    }

    public boolean isHas_subscribe() {
        return has_subscribe;
    }

    public void setHas_subscribe(boolean has_subscribe) {
        this.has_subscribe = has_subscribe;
    }

    public int getCount_of_dynamic() {
        return count_of_dynamic;
    }

    public void setCount_of_dynamic(int count_of_dynamic) {
        this.count_of_dynamic = count_of_dynamic;
    }

    public int getCount_of_subscription() {
        return count_of_subscription;
    }

    public void setCount_of_subscription(int count_of_subscription) {
        this.count_of_subscription = count_of_subscription;
    }

    public double getCircle_longitude() {
        return circle_longitude;
    }

    public void setCircle_longitude(double circle_longitude) {
        this.circle_longitude = circle_longitude;
    }

    public double getCircle_latitude() {
        return circle_latitude;
    }

    public void setCircle_latitude(double circle_latitude) {
        this.circle_latitude = circle_latitude;
    }

    public UserInfo getUser() {
        return user;
    }

    public void setUser(UserInfo user) {
        this.user = user;
    }
}
