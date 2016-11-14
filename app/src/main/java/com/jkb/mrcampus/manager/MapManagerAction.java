package com.jkb.mrcampus.manager;

/**
 * 地图管理类的动作
 * Created by JustKiddingBaby on 2016/11/11.
 */

public interface MapManagerAction {

    /**
     * 切换定位的运行状态
     */
    void switchLocationAbleStatus();

    /**
     * 切换周边雷达的允许状态
     */
    void switchNearSearchAbleStatus();
}
