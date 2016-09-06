package com.jkb.mrcampus.fragment.create$circle;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.LogoPosition;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.jkb.core.Injection;
import com.jkb.core.contract.create$circle.SelectCircleCoordinateContract;
import com.jkb.core.presenter.create$circle.SelectCircleCoordinatePresenter;
import com.jkb.model.info.LocationInfoSingleton;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CreateCircleActivity;
import com.jkb.mrcampus.helper.map.MyOrientationListener;

import de.hdodenhof.circleimageview.CircleImageView;
import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * 弹出框：选择圈子坐标地址
 * Created by JustKiddingBaby on 2016/8/11.
 */

public class SelectCircleCoordinateFragment
        extends BlurDialogFragment implements SelectCircleCoordinateContract.View, View.OnClickListener, MyOrientationListener.OnOrientationListener, BaiduMap.OnMapStatusChangeListener {


    private static SelectCircleCoordinateFragment INSTANCE = null;

    public SelectCircleCoordinateFragment() {
    }

    public static SelectCircleCoordinateFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SelectCircleCoordinateFragment();
        }
        return INSTANCE;
    }

    private static final String TAG = "SelectCircleCoordinate";

    private View rootView;
    private Activity mActivity;
    private CreateCircleActivity createCircleActivity;

    private SelectCircleCoordinatePresenter mPresenter;

    private LocationInfoSingleton locationInfoSingleton;

    //百度地图相关
    private BaiduMap mBaiduMap = null;
    private MapView mMapView = null;
    private BitmapDescriptor bitmapDescriptor;//自定义图标
    private MyOrientationListener myOrientationListener;//重力传感器的监听器
    //坐标
    private LatLng centerLatLng;
    private int mXDirection = 0;//重力方向
    //View相关
    private TextView tvPopDetermine;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mActivity = getActivity();
        if (mActivity instanceof CreateCircleActivity) {
            createCircleActivity = (CreateCircleActivity) mActivity;
        } else {
            close();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        LayoutInflater inflater = mActivity.getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_create_circle_select_coordinate, null);
        builder.setView(rootView);
        //初始化操作
        init(savedInstanceState);
        return builder.create();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCanceledOnTouchOutside(false);//点击视图外部不可以取消视图
        getDialog().getWindow().setGravity(Gravity.CENTER);
        if (getDialog() != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            getDialog().getWindow().setLayout((int) (dm.widthPixels * 0.9), (int) (dm.heightPixels * 0.85));
        }
        //开启方向传感器
        myOrientationListener.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        myOrientationListener.stop();
    }

    /**
     * 初始化
     */
    private void init(Bundle savedInstanceState) {
        initView();
        initData(savedInstanceState);
        initListener();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
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
//        mPresenter.start();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        mPresenter.start();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        bitmapDescriptor.recycle();//回收资源
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        rootView.findViewById(R.id.fccsc_ib_location).setOnClickListener(this);
        rootView.findViewById(R.id.tcc_iv_left).setOnClickListener(this);

        //设置地图的移动监听器
        mBaiduMap.setOnMapStatusChangeListener(this);
        tvPopDetermine.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    private void initData(Bundle savedInstanceState) {
        locationInfoSingleton = LocationInfoSingleton.getInstence();
        initBaiduMap();
        if (savedInstanceState != null) {
            //恢复数据
        } else {
            //初始化
        }
        initSelectCircleCoordinate();
    }

    /**
     * 初始化百度地图
     */
    private void initBaiduMap() {
        mBaiduMap = mMapView.getMap();
        //设置建筑物不可见
        mBaiduMap.setBuildingsEnabled(false);
        mBaiduMap.setMyLocationEnabled(true);//设置是否允许定位图层
        mBaiduMap.setMaxAndMinZoomLevel(16, 19);
        //初始化定位图片
        bitmapDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.car_ic_map_location_compass);
        //初始化当前坐标
        centerLatLng = new LatLng(locationInfoSingleton.latitude, locationInfoSingleton.longitude);

        //初始化方向传感器极其配置
        initOritationListener();
    }

    /**
     * 初始化选择坐标的视图
     */
    private void initSelectCircleCoordinate() {
        if (mPresenter == null) {
            mPresenter = new SelectCircleCoordinatePresenter(this,
                    Injection.provideBaiduMapWebServiceResponsitory(mActivity.getApplicationContext()));
        }
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mMapView = (MapView) rootView.findViewById(R.id.fccsc_baiduMv);

        mMapView.setLogoPosition(LogoPosition.logoPostionRightTop);//设置LOGO显示在左上角
        mMapView.showZoomControls(false);//不设置比例尺控件

        tvPopDetermine = (TextView) rootView.findViewById(R.id.fccsc_tv_popDetermine);
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
    public void close() {
        dismiss();
    }

    @Override
    public void mapMoving() {
        hideDetermineView();
        setAddressName("位置获取中...");
    }

    @Override
    public void mapMoveComplated() {
        //请求地址信息
        mPresenter.getGeocode(centerLatLng.latitude, centerLatLng.longitude);
    }

    @Override
    public void moveToLocation() {
        mPresenter.moveToLocation();
    }

    @Override
    public void moveToCoordinate(double longitude, double latitude) {
        setCurrentLocationConfig();
        //地图移动到指定地点
        // // 设定中心点坐标
        LatLng cenpt = new LatLng(latitude, longitude);
        // // 定义地图状态
        MapStatus mMapStatus = new MapStatus.Builder().target(cenpt).zoom(18)
                .build();
        // 定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mMapStatus);
        mBaiduMap.animateMapStatus(mMapStatusUpdate);//以动画形式更新地图
//        mBaiduMap.setMapStatus(mMapStatusUpdate);
    }

    @Override
    public void setSchoolName(String schoolName) {
        ((TextView) rootView.findViewById(R.id.tcc_tv_name)).setText(schoolName);
    }

    @Override
    public void setAddressName(String addressName) {
        ((TextView) rootView.findViewById(R.id.fccsc_tv_address)).setText(addressName);
    }

    @Override
    public void showDetermineView() {
        tvPopDetermine.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDetermineView() {
        tvPopDetermine.setVisibility(View.GONE);
    }

    @Override
    public void determineSelected() {
        //确认选择的坐标
        Log.d(TAG, "determineSelected");
        createCircleActivity.setDeterminedLatLng(centerLatLng);
        close();
    }

    @Override
    public void setLocationUserHeadImgBitmap(Bitmap bitmap) {
        //设置头像
        if (bitmap != null) {
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            View view = inflater.inflate(R.layout.view_baidu_descriptor, null);
            ((CircleImageView) view.findViewById(R.id.vbd_iv_headImg)).setImageBitmap(bitmap);
            bitmapDescriptor = BitmapDescriptorFactory.fromView(view);
        }
    }

    @Override
    public void setPresenter(SelectCircleCoordinateContract.Presenter presenter) {
        mPresenter = (SelectCircleCoordinatePresenter) presenter;
    }

    @Override
    public void showLoading(String value) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showReqResult(String value) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fccsc_ib_location:
                moveToLocation();
                break;
            case R.id.tcc_iv_left:
                close();
                break;
            case R.id.fccsc_tv_popDetermine:
                determineSelected();
                break;
        }
    }

    @Override
    public void onOrientationChanged(float x) {
        //重力方向改变的时候
        mXDirection = (int) x;
//        Log.d(TAG, "方向变化了---->x=" + x);
        setCurrentLocationConfig();
    }

    /**
     * 设置当前的配置
     */
    private void setCurrentLocationConfig() {
        //移动到坐标点
        double latitude = locationInfoSingleton.latitude;
        double longitude = locationInfoSingleton.longitude;
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

    //地图状态改变时候的监听器
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {
        //隐藏View显示变化
        mapMoving();
    }

    @Override
    public void onMapStatusChange(MapStatus mapStatus) {
        //设置View变化
        mapMoving();
    }

    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
        //得到地图操作的中心点
        centerLatLng = mapStatus.target;
        //请求坐标的地址信息
        mapMoveComplated();
    }
}