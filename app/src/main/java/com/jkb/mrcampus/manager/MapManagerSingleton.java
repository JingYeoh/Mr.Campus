package com.jkb.mrcampus.manager;

/**
 * 地图的管理类
 * 用于存储地图的信息
 * 如：是否运行定位、周边雷达是否运行发送数据等
 * Created by JustKiddingBaby on 2016/11/10.
 */

public class MapManagerSingleton {

    private boolean isAbleLocation;
    private boolean isAbleRadarSearch;

    //周边雷达的自动上传时间
    public static final int TIME_UPLOAD_RADER = 5000;
    public static final int TIME_UPLOAD_LOCATION = 5000;

    private MapManagerSingleton() {
        isAbleLocation = true;
        isAbleRadarSearch = true;
    }

    private static MapManagerSingleton INSTANCE = new MapManagerSingleton();

    public static MapManagerSingleton getInstance() {
        return INSTANCE;
    }

    public boolean isAbleLocation() {
        return isAbleLocation;
    }

    public void setAbleLocation(boolean ableLocation) {
        isAbleLocation = ableLocation;
    }

    public boolean isAbleRadarSearch() {
        return isAbleRadarSearch;
    }

    public void setAbleRadarSearch(boolean ableRadarSearch) {
        isAbleRadarSearch = ableRadarSearch;
    }
}
