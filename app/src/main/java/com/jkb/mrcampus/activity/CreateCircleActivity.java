package com.jkb.mrcampus.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.jkb.core.Injection;
import com.jkb.core.presenter.create$circle.EnteringCircleMessagePresenter;
import com.jkb.core.presenter.create$circle.SelectCircleCoordinatePresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.create$circle.EnteringCircleMessageFragment;
import com.jkb.mrcampus.fragment.create$circle.SelectCircleCoordinateFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.helper.FragmentStack;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 创建圈子的控制器
 * Created by JustKiddingBaby on 2016/8/11.
 */

public class CreateCircleActivity extends BaseActivity {

    //录入学校信息
    private EnteringCircleMessageFragment enteringCircleMessageFragment;
    private EnteringCircleMessagePresenter enteringCircleMessagePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_create_circle);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        //第一次进入时调用显示首页视图
        if (!savedInstanceStateValued) {
            showFragment(EnteringCircleMessageFragment.class.getName());//初始化选择的页面是录入圈子信息
        } else {
            restoreFragments();
            //恢复保存的栈数据
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

            if (ClassUtils.isNameEquals(fragmentName, EnteringCircleMessageFragment.class)) {
                showEnteringCircleMessage();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, EnteringCircleMessageFragment.class)) {
            enteringCircleMessageFragment = (EnteringCircleMessageFragment) fm.findFragmentByTag(fragmentTAG);
            enteringCircleMessagePresenter = new EnteringCircleMessagePresenter(enteringCircleMessageFragment
                    , Injection.provideCircleCreateDataResponsitory(getApplicationContext()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();

        if (ClassUtils.isNameEquals(fragmentTAG, EnteringCircleMessageFragment.class)) {
            initEnteringCircleMessageFragment();
        }
    }

    /**
     * 初始化录入圈子信息的Fragment相关数据
     */
    private void initEnteringCircleMessageFragment() {
        if (enteringCircleMessageFragment == null) {
            enteringCircleMessageFragment = EnteringCircleMessageFragment.newInstance();
        }
        if (enteringCircleMessagePresenter == null) {
            enteringCircleMessagePresenter = new EnteringCircleMessagePresenter(
                    enteringCircleMessageFragment, Injection.provideCircleCreateDataResponsitory(getApplicationContext()));
        }
        ActivityUtils.addFragmentToActivity(fm, enteringCircleMessageFragment, R.id.createCircleFrame);
    }

    /**
     * 显示录入圈子信息的Fragment
     */
    private void showEnteringCircleMessage() {
        Log.d(TAG, "showEnteringCircleMessage");
        ActivityUtils.showFragment(fm, enteringCircleMessageFragment);
    }

    /**
     * 设置选择的地址的坐标
     *
     * @param latLng LatLng坐标
     */
    public void setDeterminedLatLng(LatLng latLng) {
        if (enteringCircleMessageFragment != null) {
            enteringCircleMessageFragment.setDetermineLatLng(latLng);
        }
    }
}
