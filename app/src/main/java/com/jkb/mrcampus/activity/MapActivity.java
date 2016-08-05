package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;

import com.jkb.core.contract.map.MapAtyContract;
import com.jkb.core.presenter.map.MapAtyPresenter;
import com.jkb.core.presenter.map.MapPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.function.SettingFragment;
import com.jkb.mrcampus.fragment.map.MapFragment;
import com.jkb.mrcampus.helper.ActivityUtils;

/**
 * 地图的Activity
 * Created by JustKiddingBaby on 2016/7/26.
 */
public class MapActivity extends BaseActivity implements MapAtyContract.View {

    //该页面逻辑
    private MapAtyPresenter mPresenter;

    //地图视图
    private MapFragment mapFragment;
    private MapPresenter mapPresenter;

    //列表视图

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_map);
        init(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        //初始化本页面的逻辑数据
        mPresenter = new MapAtyPresenter(this);

        //第一次进入时调用显示首页视图
        if (!savedInstanceStateValued) {
            showMapView();
        } else {
            restoreFragments();
        }
    }


    @Override
    protected void initView() {

    }

    @Override
    public void showFragment(String fragmentName) {

    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        //恢复地图视图的信息
        if (MapFragment.class.getName().equals(fragmentTAG)) {
            mapFragment = (MapFragment) fm.findFragmentByTag(fragmentTAG);
            mapPresenter = new MapPresenter(mapFragment);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (MapFragment.class.getName().equals(fragmentTAG)) {
            initMapFragment();
        } else if (SettingFragment.class.getName().equals(fragmentTAG)) {
            initMapFragment();
        }
    }

    /**
     * 初始化Map的View视图
     */
    private void initMapFragment() {
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, mapFragment, R.id.map_content);
        }
        if (mapPresenter == null) {
            mapPresenter = new MapPresenter(mapFragment);
        }
    }

    /**
     * 返回到主页面中
     */
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        activitySwithPushRightAnim();
    }


    @Override
    public void onBackPressed() {
        startMainActivity();
    }

    @Override
    public void showMapView() {
        initFragmentStep1(MapFragment.class);
        ActivityUtils.showFragment(fm, mapFragment);
    }

    @Override
    public void showListView() {

    }

    @Override
    public void hideCurrent() {
        startMainActivity();
    }

    @Override
    public void showFragment(int position) {

    }

    @Override
    public void setPresenter(MapAtyContract.Presenter presenter) {
        mPresenter = (MapAtyPresenter) presenter;
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
        return false;
    }
}
