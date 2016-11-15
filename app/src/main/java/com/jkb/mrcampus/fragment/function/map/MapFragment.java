package com.jkb.mrcampus.fragment.function.map;

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
import com.baidu.mapapi.radar.RadarNearbyInfo;
import com.baidu.mapapi.radar.RadarNearbyResult;
import com.baidu.mapapi.radar.RadarNearbySearchOption;
import com.baidu.mapapi.radar.RadarSearchError;
import com.baidu.mapapi.radar.RadarSearchListener;
import com.baidu.mapapi.radar.RadarSearchManager;
import com.jkb.core.contract.map.MapContract;
import com.jkb.core.data.info.map.MapMarkCircleInfo;
import com.jkb.core.data.info.map.MapMarkUserInfo;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.info.LocationInfoSingleton;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MapActivity;
import com.jkb.mrcampus.adapter.custom.map.MapMarkCircleAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.helper.map.MyOrientationListener;
import com.jkb.mrcampus.manager.MapManagerSingleton;
import com.jkb.mrcampus.service.data.NearUserInfo;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 地圖的View层
 * Created by JustKiddingBaby on 2016/7/26.
 */
public class MapFragment extends BaseFragment implements MapContract.View,
        MyOrientationListener.OnOrientationListener, Observer {

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
    //屏幕中心点的坐标
    private LatLng centerLatLng;
    //周边雷达相关
    private RadarSearchManager radarSearchManager;
    private PageControlEntity radarSearchPageControl;//周边雷达搜索的分页控制器

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
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }


    @Override
    protected void initListener() {
        rootView.findViewById(R.id.tm_iv_left).setOnClickListener(titleClickListener);
        rootView.findViewById(R.id.tm_iv_right).setOnClickListener(titleClickListener);

        rootView.findViewById(R.id.fm_fb_chooseSchool).setOnClickListener(floatButtonClickListener);
        rootView.findViewById(R.id.fm_fb_filterSex).setOnClickListener(floatButtonClickListener);
        rootView.findViewById(R.id.fm_fb_location).setOnClickListener(floatButtonClickListener);
        rootView.findViewById(R.id.fm_fb_locationSwitch).setOnClickListener(floatButtonClickListener);
        rootView.findViewById(R.id.fm_fb_nearUserSwitch).setOnClickListener(floatButtonClickListener);
        //百度地图
        //设置地图的移动监听器
        mBaiduMap.setOnMapStatusChangeListener(onMapStatusChangeListener);
        initOritationListener();//初始化方向传感器变化
        //设置为地图管理这的观察者
        MapManagerSingleton.getInstance().addObserver(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initBaiDuMap();
        mapMarkCircleAdapter = new MapMarkCircleAdapter(context, mBaiduMap, null);
        //初始化周边雷达
        radarSearchManager = RadarSearchManager.getInstance();
        radarSearchManager.addNearbyInfoListener(radarSearchListener);
        radarSearchPageControl = new PageControlEntity();
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
                context.getApplicationContext());
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
        MapManagerSingleton.getInstance().deleteObserver(this);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        centerLatLng = null;
        mBaiduMap = null;
        myOrientationListener = null;
        mapActivity = null;
        bitmapDescriptor = null;
        onMapStatusChangeListener = null;
        //清除周边雷达相关
        radarSearchManager.removeNearbyInfoListener(radarSearchListener);//移除监听
        radarSearchManager.clearUserInfo();
        radarSearchManager.destroy();
        radarSearchManager = null;
        radarSearchListener = null;
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
                    mapActivity.onBackPressed();
                    break;
                case R.id.tm_iv_right:
                    mapActivity.showMapListFragment();
                    break;
            }
        }
    };

    /**
     * 浮动按钮的点击时间的监听器
     */
    private View.OnClickListener floatButtonClickListener = new View.OnClickListener() {
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
        showReqResult("切换是否允许定位");
    }

    @Override
    public void nearUserSwitch() {
        MapManagerSingleton.getInstance().switchNearSearchAbleStatus();
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
    public void setNearSearchSwitchView(boolean isAble) {
        ImageView imageView = (ImageView) rootView.findViewById(R.id.fm_fb_nearUserSwitch);
        if (!isAble) {
            imageView.setImageResource(R.drawable.ic_draft_delete);
        } else {
            imageView.setImageResource(R.drawable.ssdk_oks_classic_wechat);
        }
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
        LayoutInflater inflater = LayoutInflater.from(context);
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
    public void startSearchNearUserInfo() {
        //开始搜索附近的用户
        reqRadarSearch(radarSearchPageControl.getCurrent_page());
    }

    /**
     * 请求周边雷达
     */
    private void reqRadarSearch(int current_page) {
        if (radarSearchPageControl.getCurrent_page() >= radarSearchPageControl.getLast_page()) {
            return;
        }
        RadarNearbySearchOption option = new RadarNearbySearchOption()
                .pageCapacity(MapManagerSingleton.RADAR_SEARCH_PAGECAPACITY)
                .radius(MapManagerSingleton.RADAR_SEARCH_RADIUS)
                .centerPt(centerLatLng)
                .pageNum(current_page);
        radarSearchManager.nearbyInfoRequest(option);
    }

    @Override
    public void stopSearchNearUserInfo() {
        //停止搜索附近的用户
        clearRadarSearch();
    }

    @Override
    public void clearRadarSearch() {
        radarSearchManager.clearUserInfo();//清除用户信息
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
        mapActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        mapActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        mapActivity.showShortToast(value);
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

    @Override
    public void update(Observable o, Object arg) {
        //搜索附近的用户
        if (MapManagerSingleton.getInstance().isAbleRadarSearch()) {
//            startSearchNearUserInfo();
            setNearSearchSwitchView(true);
        } else {
//            stopSearchNearUserInfo();
            setNearSearchSwitchView(false);
        }
    }

    /**
     * 当地图状态变化时候的监听器
     */
    private BaiduMap.OnMapStatusChangeListener onMapStatusChangeListener =
            new BaiduMap.OnMapStatusChangeListener() {
                @Override
                public void onMapStatusChangeStart(MapStatus mapStatus) {
                }

                @Override
                public void onMapStatusChange(MapStatus mapStatus) {
                }

                @Override
                public void onMapStatusChangeFinish(MapStatus mapStatus) {
                    centerLatLng = mapStatus.target;  //得到地图操作的中心点
                }
            };
    /**
     * 周边雷达的监听器
     */
    private RadarSearchListener radarSearchListener = new RadarSearchListener() {
        @Override
        public void onGetNearbyInfoList(RadarNearbyResult radarNearbyResult,
                                        RadarSearchError radarSearchError) {
            if (radarSearchError == RadarSearchError.RADAR_NO_ERROR) {
                //获取成功，处理数据
                List<RadarNearbyInfo> radarNearbyInfos = radarNearbyResult.infoList;
                if (radarNearbyInfos == null || radarNearbyInfos.size() == 0) {
                    return;
                }
                for (RadarNearbyInfo info : radarNearbyInfos) {
                    NearUserInfo userInfo = new NearUserInfo();
                    userInfo.setLatitude(info.pt.latitude);
                    userInfo.setLongitude(info.pt.longitude);
                    String conmments = info.comments;
                    //转换

                }
            } else {
                LogUtils.d(TAG, "getNearList failed：" + radarSearchError);
            }
        }

        @Override
        public void onGetUploadState(RadarSearchError radarSearchError) {
        }

        @Override
        public void onGetClearInfoState(RadarSearchError radarSearchError) {
        }
    };
}
