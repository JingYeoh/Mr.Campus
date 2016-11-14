package com.jkb.mrcampus.manager;

import java.util.Observable;
import java.util.Observer;

/**
 * 地图的管理类
 * 用于存储地图的信息
 * 如：是否运行定位、周边雷达是否运行发送数据等
 * Created by JustKiddingBaby on 2016/11/10.
 */

public class MapManagerSingleton extends Observable implements MapManagerAction {

    private boolean isAbleLocation;
    private boolean isAbleRadarSearch;

    //周边雷达的自动上传时间
    public static final int TIME_UPLOAD_RADAR = 5000;
    //周边雷达搜索的相关参数
    public static final int RADAR_SEARCH_RADIUS = 999999999;
    public static final int RADAR_SEARCH_PAGECAPACITY = 20;//每页返回的数据
    //定位的时间间隔
    public static final int TIME_UPLOAD_LOCATION = 5000;

    private MapManagerSingleton() {
        isAbleLocation = true;
        isAbleRadarSearch = true;
    }

    private static MapManagerSingleton INSTANCE = new MapManagerSingleton();

    public static MapManagerSingleton getInstance() {
        return INSTANCE;
    }

    /**
     * 更新消息
     */
    private void update() {
        setChanged();
        notifyObservers();
    }

    @Override
    public void switchLocationAbleStatus() {
        isAbleLocation = !isAbleLocation;
        setAbleLocation(isAbleLocation);
        update();
    }

    @Override
    public void switchNearSearchAbleStatus() {
        isAbleRadarSearch = !isAbleRadarSearch;
        setAbleRadarSearch(isAbleRadarSearch);
        update();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
//        update();
    }

    private void setAbleLocation(boolean ableLocation) {
        isAbleLocation = ableLocation;
    }

    private void setAbleRadarSearch(boolean ableRadarSearch) {
        isAbleRadarSearch = ableRadarSearch;
    }

    public boolean isAbleLocation() {
        return isAbleLocation;
    }

    public boolean isAbleRadarSearch() {
        return isAbleRadarSearch;
    }
}
