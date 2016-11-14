package com.jkb.model.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jkb.mrcampus.db.entity.Schools;

/**
 * 选择学校的单例类
 * Created by JustKiddingBaby on 2016/9/29.
 */

public class SchoolInfoSingleton extends Observable {

    private Schools school;//学校表
    private boolean isSelectedSchool;//是否选择学校

    private SchoolInfoSingleton() {
        if (school == null) {
            school = new Schools();
        }
        isSelectedSchool = false;
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
        update();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        update();
    }

    /**
     * 更新
     */
    private void update(){
        setChanged();
        notifyObservers(isSelectedSchool);
    }
}
