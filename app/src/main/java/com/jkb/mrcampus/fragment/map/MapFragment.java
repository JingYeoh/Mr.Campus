package com.jkb.mrcampus.fragment.map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jkb.core.contract.map.MapContract;
import com.jkb.core.presenter.map.MapPresenter;
import com.jkb.core.utils.BitmapUils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.activity.MapActivity;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 地圖的View层
 * Created by JustKiddingBaby on 2016/7/26.
 */
public class MapFragment extends BaseFragment implements MapContract.View {

    //View层
    private MapView mMapView = null;

    //百度地图数据
    private BaiduMap mBaiduMap = null;

    private MapActivity mapActivity;

    private MapPresenter mPresenter;

    public MapFragment() {
    }

    /**
     * 获得一个实例化的MapFragment对象
     *
     * @return
     */
    public static MapFragment newInstance() {
        return new MapFragment();
    }

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
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mBaiduMap = mMapView.getMap();
    }

    @Override
    protected void initView() {
        if (mActivity instanceof MapActivity) {
            mapActivity = (MapActivity) mActivity;
        }
        mMapView = (MapView) rootView.findViewById(R.id.bmapView);
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
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
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

    /**
     * 截取当前地图的截图
     */
    @Override
    public void getMapSnapShot() {
        mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                mPresenter.setMapSnapShort(bitmap);
            }
        });
    }

    @Override
    public void showMapSnapShort(Bitmap bitmap) {
        if (bitmap == null) {
            bitmap = BitmapUils.getBitmapFromDrawable(context, R.drawable.welcome);
        }
    }

    @Override
    public void hideMapSnapShort() {
        mMapView.setVisibility(View.VISIBLE);
        mPresenter.recycleSnapShort();
    }

    @Override
    public void chooseSchool() {
        showShortToash("chooseSchool");
    }

    @Override
    public void location() {
        showShortToash("location");
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
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        mPresenter = (MapPresenter) presenter;
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
}
