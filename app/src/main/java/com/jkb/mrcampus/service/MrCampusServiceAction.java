package com.jkb.mrcampus.service;

import com.baidu.mapapi.model.LatLng;

/**
 * 菌菌服务的动作规定
 * Created by JustKiddingBaby on 2016/11/10.
 */

public interface MrCampusServiceAction {

    /**
     * 登录时触发
     */
    void onLogin();

    /**
     * 退出登录时触发
     */
    void onLogout();

    /**
     * 更新定位
     */
    void updateRadarSearch();

    /**
     * 连接IM聊天
     */
    void connectIM();

    /**
     * 断开IM聊天
     */
    void breakIMConnect();

    /**
     * 绑定激光推送的别名
     */
    void bindJPushAlias();

    /**
     * 清楚极光推送的别名
     */
    void clearJPushAlias();

    /**
     * 自动上传信息
     */
    void uploadRadarInfoAuto();

    /**
     * 管理周边雷达
     */
    void clearRadarInfo();
}
