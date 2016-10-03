package com.jkb.core.presenter.map;

import android.support.annotation.NonNull;

import com.jkb.core.contract.map.MapContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.info.LocationInfoSingleton;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.model.info.UserInfoSingleton;

import jkb.mrcampus.db.entity.Schools;
import jkb.mrcampus.db.entity.Users;

/**
 * Map的Presenter层
 * Created by JustKiddingBaby on 2016/7/26.
 */
public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mapView;

    //data
    private SchoolInfoSingleton schoolInfo;
    private LocationInfoSingleton locationInfo;

    public MapPresenter(@NonNull MapContract.View mapView) {
        this.mapView = mapView;

        this.mapView.setPresenter(this);
    }

    @Override
    public void start() {
        initLocation$SchoolData();//初始化学校和个人位置的数据
        initMapLocation();//初始化地图的位置
        initUserData();//初始化用户数据
    }

    @Override
    public void initLocation$SchoolData() {
        schoolInfo = SchoolInfoSingleton.getInstance();
        locationInfo = LocationInfoSingleton.getInstence();
    }

    @Override
    public void initUserData() {
        if (!LoginContext.getInstance().isLogined()) {
            mapView.showUserLocation(null);
        } else {
            UserInfoSingleton userInfo = UserInfoSingleton.getInstance();
            Users users = userInfo.getUsers();
            mapView.showUserLocation(users.getAvatar());
        }
    }

    @Override
    public void initMapLocation() {
        if (!schoolInfo.isSelectedSchool()) {
            mapView.showReqResult("请先选择学校");
            mapView.showSchoolSelectorView();
            moveMapToUserLocation();
        } else {
            moveMapToSchoolCenter();
        }
    }

    @Override
    public void moveMapToSchoolCenter() {
        if (!mapView.isActive()) {
            return;
        }
        Schools school = schoolInfo.getSchool();
        mapView.moveMapToLng(school.getLatitude(), school.getLongitude());
    }

    @Override
    public void moveMapToUserLocation() {
        if (!mapView.isActive()) {
            return;
        }
        mapView.moveMapToLng(locationInfo.latitude, locationInfo.longitude);
    }

    @Override
    public LocationInfoSingleton bindLocationInfo() {
        return locationInfo;
    }
}
