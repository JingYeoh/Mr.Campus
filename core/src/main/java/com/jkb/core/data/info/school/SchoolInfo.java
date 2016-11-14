package com.jkb.core.data.info.school;

/**
 * 学校的数据实体类
 * Created by JustKiddingBaby on 2016/11/13.
 */

public class SchoolInfo {


    /**
     * id : 1
     * sname : 金陵科技学院
     * badge : /home/wwwroot/image.lyfsmile.cn/school/jlkj.jpg
     * summary : jlkj
     * longitude : 118.905518
     * latitude : 31.912587
     */

    private int id;
    private String sname;
    private String badge;
    private String summary;
    private String longitude;
    private String latitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getBadge() {
        return badge;
    }

    public void setBadge(String badge) {
        this.badge = badge;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
