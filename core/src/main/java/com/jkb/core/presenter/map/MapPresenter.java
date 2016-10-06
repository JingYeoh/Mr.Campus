package com.jkb.core.presenter.map;

import android.support.annotation.NonNull;

import com.jkb.core.contract.map.MapContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.circle.CircleInfo;
import com.jkb.core.data.map.MapMarkCircleInfo;
import com.jkb.core.data.map.MapMarkUserInfo;
import com.jkb.model.info.LocationInfoSingleton;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.model.info.UserInfoSingleton;

import java.util.ArrayList;
import java.util.List;

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

    //mapMark
    private MapMarkCircleInfo mapMarkCircleInfo;
    private MapMarkUserInfo mapMarkUserInfo;

    private boolean isCached;

    public MapPresenter(@NonNull MapContract.View mapView) {
        this.mapView = mapView;

        mapMarkCircleInfo = new MapMarkCircleInfo();
        mapMarkUserInfo = new MapMarkUserInfo();

        this.mapView.setPresenter(this);
    }

    @Override
    public void start() {
        initLocation$SchoolData();//初始化学校和个人位置的数据
        initMapLocation();//初始化地图的位置
        initUserData();//初始化用户数据
        initMapMarkInfo();//初始化地图标记信息
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

    @Override
    public void onRefresh() {
        isCached = false;
        //初始化地图标记圈子数据
        reqMapMarkCircleInfo();
        //初始化地图标记用户数据
    }

    @Override
    public void initMapMarkInfo() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void bindDataToView() {
        isCached = true;
        if (!mapView.isActive()) {
            return;
        }

        //设置地图标记的圈子数据
        mapView.setMapMarkCircles(mapMarkCircleInfo);
    }

    /**
     * 请求地图标记圈子数据
     */
    private void reqMapMarkCircleInfo() {
        List<CircleInfo> circles = new ArrayList<>();

        String[] circleUrls = new String[]{
                "http://pic15.nipic.com/20110616/2451392_104419837268_2.jpg",
                "http://pic.58pic.com/58pic/13/72/07/55Z58PICKka_1024.jpg",
                "http://pic27.nipic.com/20130320/3822951_105204803000_2.jpg",
                "http://pic31.nipic.com/20130731/12440028_171838650000_2.jpg",
                "http://pic15.nipic.com/20110621/2678842_143658366148_2.jpg",
                "http://www.bz55.com/uploads1/allimg/120212/1_120212213716_1.jpg",
                "http://img2.3lian.com/2014/c6/18/d/61.jpg",
                "http://e.hiphotos.baidu.com/image/pic/item/7dd98d1001e93901e5dc0e2079ec54e737d196d6.jpg",
                "http://a.hiphotos.baidu.com/image/h%3D200/sign=7eb72439d309b3def4bfe368fcbe6cd3/d1160924ab18972bfcfe839be4cd7b899f510a9e.jpg",
                "http://g.hiphotos.baidu.com/image/h%3D200/sign=476eb6199d22720e64cee5fa4bca0a3a/adaf2edda3cc7cd9c38927a23c01213fb80e9120.jpg"
        };
        double latitude = 31.912587;
        double longitude = 118.905518;

        for (int i = 0; i < circleUrls.length; i++) {
            CircleInfo circleInfo = new CircleInfo();
            circleInfo.setCircleId(i);
            circleInfo.setCircleName("圈子测试" + i);
            circleInfo.setCircleType("测试类型" + i);
            circleInfo.setPictureUrl(circleUrls[i]);
            circleInfo.setOperatorCount(i);
            circleInfo.setDynamicsCount(i);
            circleInfo.setIntroduction("圈子简介" + i);
            circleInfo.setLatitude(latitude);
            circleInfo.setLongitude(longitude);
            latitude -= 0.0005;
            longitude -= 0.0005;

            circles.add(circleInfo);
        }
        mapMarkCircleInfo.setCircles(circles);

        bindDataToView();
    }
}
