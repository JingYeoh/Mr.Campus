package com.jkb.core.data.info.map;

import com.jkb.core.data.info.circle.CircleInfo;

import java.util.List;

/**
 * 地图标记：圈子信息类
 * Created by JustKiddingBaby on 2016/10/4.
 */

public class MapMarkCircleInfo {
    private List<CircleInfo> circles;

    public List<CircleInfo> getCircles() {
        return circles;
    }

    public void setCircles(List<CircleInfo> circles) {
        this.circles = circles;
    }
}
