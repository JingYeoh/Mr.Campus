package com.jkb.mrcampus.fragment.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.jkb.core.contract.map.MapContract;
import com.jkb.core.data.map.MapMarkCircleInfo;
import com.jkb.core.data.map.MapMarkUserInfo;
import com.jkb.model.info.LocationInfoSingleton;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MapActivity;
import com.jkb.mrcampus.adapter.custom.map.MapMarkCircleAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.helper.map.MyOrientationListener;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 地圖的View层
 * Created by JustKiddingBaby on 2016/7/26.
 */
public class MapFragment extends BaseFragment implements MapContract.View,
        BaiduMap.OnMapStatusChangeListener,
        MyOrientationListener.OnOrientationListener {

    public MapFragment() {
    }

    /**
     * 获得一个实例化的MapFragment对象
     */
    public static MapFragment newInstance() {
        return new MapFragment();
    }

    //data
    private MapContract.Presenter mPresenter;
    private MapActivity mapActivity;

    //View层
    private MapView mMapView = null;

    //百度地图数据
    private BaiduMap mBaiduMap = null;
    private LatLng centerLatLng;

    //与自己相关的视图,自己的位置以及其他
    private BitmapDescriptor bitmapDescriptor;//自定义图标
    private MyOrientationListener myOrientationListener;//重力传感器的监听器
    private int mXDirection = 0;//重力方向

    //圈子标注
    private MapMarkCircleAdapter mapMarkCircleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_map);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void initListener() {
        rootView.findViewById(R.id.tm_iv_left).setOnClickListener(titleClickListener);
        rootView.findViewById(R.id.tm_iv_right).setOnClickListener(titleClickListener);

        rootView.findViewById(R.id.fm_fb_chooseSchool).setOnClickListener(floatButtonCliclListener);
        rootView.findViewById(R.id.fm_fb_filterSex).setOnClickListener(floatButtonCliclListener);
        rootView.findViewById(R.id.fm_fb_location).setOnClickListener(floatButtonCliclListener);
        rootView.findViewById(R.id.fm_fb_locationSwitch).setOnClickListener(floatButtonCliclListener);
        rootView.findViewById(R.id.fm_fb_nearUserSwitch).setOnClickListener(floatButtonCliclListener);

        //百度地图
        //设置地图的移动监听器
        mBaiduMap.setOnMapStatusChangeListener(this);
        initOritationListener();//初始化方向传感器变化
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initBaiDuMap();
        mapMarkCircleAdapter = new MapMarkCircleAdapter(mActivity, mBaiduMap, null);
    }

    /**
     * 初始化百度地图
     */
    private void initBaiDuMap() {
        mBaiduMap = mMapView.getMap();
        //设置建筑物不可见
        mBaiduMap.setBuildingsEnabled(false);
        mBaiduMap.setMyLocationEnabled(true);//设置是否允许定位图层
        mBaiduMap.setMaxAndMinZoomLevel(16, 19);
    }

    @Override
    protected void initView() {
        if (mActivity instanceof MapActivity) {
            mapActivity = (MapActivity) mActivity;
        }
        mMapView = (MapView) rootView.findViewById(R.id.bmapView);
    }

    /**
     * 初始化方向传感器
     */
    private void initOritationListener() {
        myOrientationListener = new MyOrientationListener(
                mActivity.getApplicationContext());
        myOrientationListener
                .setOnOrientationListener(this);
    }

    @Override
    public void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        mPresenter.start();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.start();
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
        //开启方向传感器
        myOrientationListener.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        myOrientationListener.stop();
    }

    /**
     * 标题栏的点击事件监听器
     */
    private View.OnClickListener titleClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tm_iv_left:
                    mapActivity.hideCurrent();
                    break;
                case R.id.tm_iv_right:
                    mapActivity.showListView();
                    break;
            }
        }
    };

    /**
     * 浮动按钮的点击时间的监听器
     */
    private View.OnClickListener floatButtonCliclListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.fm_fb_chooseSchool:
                    chooseSchool();
                    break;
                case R.id.fm_fb_filterSex:
                    filterSex();
                    break;
                case R.id.fm_fb_location:
                    location();
                    break;
                case R.id.fm_fb_locationSwitch:
                    locationSwitch();
                    break;
                case R.id.fm_fb_nearUserSwitch:
                    nearUserSwitch();
                    break;
            }
        }
    };

    @Override
    public void chooseSchool() {
        showSchoolSelectorView();
    }

    @Override
    public void location() {
        mPresenter.moveMapToUserLocation();
    }

    @Override
    public void locationSwitch() {
        showShortToash("locationSwitch");
    }

    @Override
    public void nearUserSwitch() {
        showShortToash("nearUserSwitch");
    }

    @Override
    public void filterSex() {
        showShortToash("filterSex");
    }

    @Override
    public void showSchoolSelectorView() {
        mapActivity.showSelectSchoolView();
    }

    @Override
    public void showSchoolView(String schoolBadge) {
        ImageView ivSchoolBadge = (ImageView) rootView.findViewById(R.id.fm_fb_chooseSchool);
        if (StringUtils.isEmpty(schoolBadge)) {
            ivSchoolBadge.setImageResource(R.drawable.ic_write);
        } else {
            ImageLoaderFactory.getInstance().displayImage(ivSchoolBadge, schoolBadge);
        }
    }

    @Override
    public void showUserLocation(String userAvatar) {
        Log.d(TAG, "userAvatar=" + userAvatar);
        //设置用户头像
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.view_baidu_descriptor, null);
        CircleImageView ivHeadImg = ((CircleImageView) view.findViewById(R.id.vbd_iv_headImg));
        if (!StringUtils.isEmpty(userAvatar)) {
            ImageLoaderFactory.getInstance().displayImage(
                    ivHeadImg, userAvatar);
        } else {
            ivHeadImg.setImageResource(R.drawable.ic_user_head);
        }
        bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
    }


    @Override
    public void moveMapToLng(double latitude, double longitude) {
        LatLng cenpt = new LatLng(latitude, longitude);
        // // 定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(18)
                .build();
        // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);//以动画形式更新地图
    }

    @Override
    public void setMapMarkCircles(MapMarkCircleInfo mapMarkCircles) {
        mapMarkCircleAdapter.mapMarkCircleInfo = mapMarkCircles;
        mapMarkCircleAdapter.notifyDataSetChanged();
        mapMarkCircleAdapter.notifyDataSetChanged();//两次调用主要是为了防止图片不加载的BUG
    }

    @Override
    public void setMapMarkUsers(MapMarkUserInfo mapMarkUsers) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        mapActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        mapActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        mapActivity.showShortToast(value);
    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {

    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        centerLatLng = mapStatus.target;
    }

    //重力感应变化
    @Override
    public void onOrientationChanged(float x) {
        //重力方向改变的时候
        mXDirection = (int) x;
//        Log.d(TAG, "方向变化了---->x=" + x);
        initUserLocationConfig();
    }

    /**
     * 设置用户位置的配置
     */
    private void initUserLocationConfig() {
        LocationInfoSingleton locationInfo = mPresenter.bindLocationInfo();
        //移动到坐标点
        double latitude = locationInfo.latitude;
        double longitude = locationInfo.longitude;
        // 构造定位数据
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(0)//设置精度
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(mXDirection)//设置方向
                .latitude(latitude)
                .longitude(longitude).build();
        // 设置定位数据
        mBaiduMap.setMyLocationData(locData);
        MyLocationConfiguration config = new MyLocationConfiguration(
                MyLocationConfiguration.LocationMode.NORMAL, true, bitmapDescriptor);
        mBaiduMap.setMyLocationConfigeration(config);
    }
}
