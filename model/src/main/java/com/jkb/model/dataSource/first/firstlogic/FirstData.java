package com.jkb.model.dataSource.first.firstlogic;

import jkb.mrcampus.db.entity.Status;

/**
 * 第一次进入APP页面FirstActivity的数据类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public class FirstData {

    /**
     * 得到当前缓存的版本号，匹配系統的版本號，不匹配时显示Welcome页面
     */
    private String version;

    /**
     * 是否缓存有广告，要是有缓存的广告的时候显示Advent页面
     */
    private boolean isCachedAdvent;

    /**
     * 是否有版本更新：版本更新后要显示Guide页面
     */
    private boolean isUpdated;

    private Status status;//保存的status表的数据


    /**
     * 显示的条目
     * 0：Welcome
     * 1：Guide
     * 2：Advent
     */
    private ShowPosition showPosition
            = ShowPosition.WELCOME;

    /**
     * 要显示的条目
     */
    public static enum ShowPosition {
        WELCOME, GUIDE, ADVENT;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isCachedAdvent() {
        return isCachedAdvent;
    }

    public void setIsCachedAdvent(boolean isCachedAdvent) {
        this.isCachedAdvent = isCachedAdvent;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(boolean isUpdated) {
        this.isUpdated = isUpdated;
    }

    public ShowPosition getShowPosition() {
        return showPosition;
    }

    public void setShowPosition(ShowPosition showPosition) {
        this.showPosition = showPosition;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
