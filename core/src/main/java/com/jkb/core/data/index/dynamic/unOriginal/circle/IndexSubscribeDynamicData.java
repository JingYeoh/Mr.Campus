package com.jkb.core.data.index.dynamic.unOriginal.circle;

import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.core.data.index.dynamic.IndexDynamicData;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.info.user.UserInfo;

/**
 * 首页的非原创订阅圈子动态
 * Created by JustKiddingBaby on 2016/11/7.
 */

public class IndexSubscribeDynamicData extends IndexDynamicData {

    private boolean isDataEffective = true;

    private CircleInfo circle;

    public IndexSubscribeDynamicData() {
    }

    public IndexSubscribeDynamicData(DynamicListEntity.DataBean.CircleBean circleBean) {
        handleSubscribeDynamicData(circleBean);
    }

    @Override
    public IndexDynamicData getIndexDynamic() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理订阅的数据
     */
    private void handleSubscribeDynamicData(DynamicListEntity.DataBean.CircleBean circleBean) {
        if (circleBean == null) {
            isDataEffective = false;
            return;
        }
        circle = new CircleInfo();
        circle.setCircleId(circleBean.getId());
        circle.setCircleName(circleBean.getName());
        circle.setCircleType(circleBean.getType());
        circle.setPictureUrl(circleBean.getPicture());
        circle.setIntroduction(circleBean.getIntroduction());
        circle.setLongitude(circleBean.getLongitude());
        circle.setLatitude(circleBean.getLatitude());
        //设置圈子原创作者信息
        DynamicListEntity.DataBean.CircleBean.UserBean userBean = circleBean.getUser();
        if (userBean != null) {
            UserInfo user = new UserInfo();
            user.setAvatar(userBean.getCircle_owner_avatar());
            user.setNickName(userBean.getCircle_owner_nickname());
            user.setId(userBean.getId());
            circle.setUser(user);
        } else {
            isDataEffective = false;
        }
    }

    public CircleInfo getCircle() {
        return circle;
    }

    public void setCircle(CircleInfo circle) {
        this.circle = circle;
    }
}
