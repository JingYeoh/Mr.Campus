package com.jkb.core.contract.school.data;

/**
 * 学校的数据类
 * Created by JustKiddingBaby on 2016/9/29.
 */

public class SchoolData {

    private int school_id;
    private String schoolName;
    private String schoolBadge;
    private String summary;
    private double latitude;
    private double longitude;


    public int getSchool_id() {
        return school_id;
    }

    public void setSchool_id(int school_id) {
        this.school_id = school_id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getSchoolBadge() {
        return schoolBadge;
    }

    public void setSchoolBadge(String schoolBadge) {
        this.schoolBadge = schoolBadge;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
