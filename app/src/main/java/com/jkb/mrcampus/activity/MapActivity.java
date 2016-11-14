package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.map.MapListPresenter;
import com.jkb.core.presenter.map.MapPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.function.map.MapFragment;
import com.jkb.mrcampus.fragment.function.map.MapListFragment;
import com.jkb.mrcampus.fragment.function.map.list.MapListFragment2;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.helper.FragmentStack;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 地图的Activity
 * Created by JustKiddingBaby on 2016/7/26.
 */
public class MapActivity extends BaseActivity {

    //该页面逻辑
    private int contentId = R.id.map_content;
    //保存入栈的Fragment顺序
    private FragmentStack fragmentStack;

    //地图视图
    private MapFragment mapFragment;
    private MapPresenter mapPresenter;

    //列表视图
    private MapListFragment2 mapListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_map);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        fragmentStack = new FragmentStack();
        //第一次进入时调用显示首页视图
        if (!savedInstanceStateValued) {
            showFragment(ClassUtils.getClassName(MapFragment.class));
        } else {
            fragmentStack.setFragmetStackNames(
                    savedInstanceState.getStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK));
            restoreFragments();
        }
    }


    @Override
    protected void initView() {

    }

    @Override
    public void showFragment(String fragmentName) {
        Log.d(TAG, "showFragment------->" + fragmentName);
        try {
            Class<?> clzFragment = Class.forName(fragmentName);
            //初始化Fragment
            initFragmentStep1(clzFragment);
            //隐藏掉所有的视图
            ActivityUtils.hideAllFragments(fm);
            //添加到回退栈中
            fragmentStack.addFragmentToStack(fragmentName);

            if (ClassUtils.isNameEquals(fragmentName, MapFragment.class)) {
                showMap();
            } else if (ClassUtils.isNameEquals(fragmentName, MapListFragment.class)) {
                showMapList();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        //恢复地图视图的信息
        if (MapFragment.class.getName().equals(fragmentTAG)) {
            mapFragment = (MapFragment) fm.findFragmentByTag(fragmentTAG);
            mapPresenter = new MapPresenter(mapFragment);
        } else if (ClassUtils.isNameEquals(fragmentTAG, MapListFragment.class)) {
            mapListFragment = (MapListFragment2) fm.findFragmentByTag(fragmentTAG);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, MapFragment.class)) {
            initMapFragment();
        } else if (ClassUtils.isNameEquals(fragmentTAG, MapListFragment.class)) {
            initMapList();
        }
    }

    /**
     * 初始化地图列表相关
     */
    private void initMapList() {
        if (mapListFragment == null) {
            mapListFragment = MapListFragment2.newInstance();
            ActivityUtils.addFragmentToActivity(fm, mapListFragment, contentId);
        }
    }

    /**
     * 初始化Map的View视图
     */
    private void initMapFragment() {
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, mapFragment, contentId);
        }
        if (mapPresenter == null) {
            mapPresenter = new MapPresenter(mapFragment);
        }
    }

    /**
     * 显示地图
     */
    private void showMap() {
        ActivityUtils.showFragment(fm, mapFragment);
    }

    /**
     * 显示地图列表
     */
    private void showMapList() {
        ActivityUtils.showFragment(fm, mapListFragment);
    }

    /**
     * 返回到主页面中
     */
    public void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        activitySwithPushRightAnim();
    }

    /**
     * 返回到上级页面
     */
    public void backToLastView() {
        //如果当前页面就是登录页面的时候
        if (ClassUtils.isNameEquals(fragmentStack.getCurrentFragmentName(), MapFragment.class)) {
            startMainActivity();
            return;
        }
        //先销毁使用过后的Fragment
        removeCurrentFragment();
        String fragmentName = fragmentStack.getCurrentFragmentName();
        //如果到最顶层栈或者到了登录页面的时候，再次回退就直接销毁当前页面
        if (fragmentName == null) {
            //返回上级页面
            startMainActivity();
        } else {
            //先回退一级栈保存的类信息，避免重复添加
            fragmentStack.removePopFragmentStack();
            //根据类名显示Fragment视图
            showFragment(fragmentName);
        }
    }

    /**
     * 销毁使用过后的Fragment
     */
    private void removeCurrentFragment() {
        //销毁的Fragment出栈
        fragmentStack.removePopFragmentStack();
    }

    @Override
    public void onBackPressed() {
        backToLastView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK,
                fragmentStack.getFragmetStackNames());
    }

    /**
     * 显示地图列表页面
     */
    public void showMapListFragment() {
        showFragment(ClassUtils.getClassName(MapListFragment.class));
    }
}
