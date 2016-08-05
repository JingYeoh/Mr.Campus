package com.jkb.model.map.data;

import android.graphics.Bitmap;

/**
 * 学校的信息实体类
 * Created by JustKiddingBaby on 2016/7/28.
 */
public class SchoolData {

    //校徽
    private Bitmap schoolBadge;

    //学校名称
    private String schoolName;

    //学校ID
    private String schoolId;

    //学校坐标
    private String lng, lat;

    public Bitmap getSchoolBadge() {
        return schoolBadge;
    }

    public void setSchoolBadge(Bitmap schoolBadge) {
        this.schoolBadge = schoolBadge;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }
}
