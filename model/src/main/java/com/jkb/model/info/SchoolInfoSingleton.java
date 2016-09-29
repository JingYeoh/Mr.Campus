package com.jkb.model.info;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.Schools;

/**
 * 选择学校的单例类
 * Created by JustKiddingBaby on 2016/9/29.
 */

public class SchoolInfoSingleton {

    private Schools school;//学校表
    private boolean isSelectedSchool;//是否选择学校

    //监听器
    private List<OnSchoolSelectedChangedListener> onSchoolSelectedChangedListeners;

    private SchoolInfoSingleton() {
        if (school == null) {
            school = new Schools();
        }
        isSelectedSchool = false;
        onSchoolSelectedChangedListeners = new ArrayList<>();
    }

    private static SchoolInfoSingleton INSTANCE = null;

    public static SchoolInfoSingleton getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SchoolInfoSingleton();
        }
        return INSTANCE;
    }

    public Schools getSchool() {
        return school;
    }

    public void setSchool(Schools school) {
        this.school = school;
    }

    public boolean isSelectedSchool() {
        return isSelectedSchool;
    }

    public void setSelectedSchool(boolean selectedSchool) {
        isSelectedSchool = selectedSchool;
        setSelectedChangedListenerData();
    }


    public void setOnSchoolSelectedChangedListener(
            OnSchoolSelectedChangedListener onSchoolSelectedChangedListener) {
        this.onSchoolSelectedChangedListeners.add(onSchoolSelectedChangedListener);
        setSelectedChangedListenerData();
    }

    /**
     * 设置学校选择状态改变的时候的监听器
     */
    private void setSelectedChangedListenerData() {
        if (onSchoolSelectedChangedListeners != null && onSchoolSelectedChangedListeners.size() > 0) {
            for (OnSchoolSelectedChangedListener listener : onSchoolSelectedChangedListeners)
                if (isSelectedSchool) {
                    listener.onSchoolSelected(school);
                } else {
                    listener.onSchoolNotSelected();
                }
        }
    }

    /**
     * 选择学校的监听器
     */
    public interface OnSchoolSelectedChangedListener {

        /**
         * 当学校选择的时候
         */
        void onSchoolSelected(Schools schools);

        /**
         * 当学校没有被选择的时候
         */
        void onSchoolNotSelected();
    }
}
