package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.contract.first.FirstContract;
import com.jkb.core.presenter.first.FirstPresenter;
import com.jkb.core.presenter.first.WelcomePresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.first.GuideFragment;
import com.jkb.mrcampus.fragment.first.WelcomeFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 进入APP的第一个页面控制器
 * 包含三个页面：GuideFragment、WelcomeFragment、AdventFragment
 * GuideFragment：引导页（第一次进入APP或者版本更新的引导页面）
 * WelcomeFragment：欢迎页面，初始化APP页面数据时用到
 * AdventFragment：广告页面，用于显示推送的广告
 * Created by JustKiddingBaby on 2016/7/21.
 */
public class FirstActivity extends BaseActivity implements FirstContract.View {

    //有关WelcomeFragment页面的数据
    private WelcomePresenter welcomePresenter;
    private WelcomeFragment welcomeFragment;

    //有关First本页面的逻辑
    private FirstContract.Presenter mPresenter;
    private FirstPresenter firstPresenter;

    //引导页面
    private GuideFragment guideFragment;

    //要跳转到的数据
    private Bundle jumpBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar_Fullscreen);
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_first);
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
        initFirstPresenter();
        if (savedInstanceStateValued) {
            restoreFragments();//回复数据
        } else {
            jumpBundle = getIntent().getExtras();
        }
    }

    /**
     * 初始化Presenter层
     */
    private void initFirstPresenter() {
        if (firstPresenter == null) {
            firstPresenter = new FirstPresenter(
                    Injection.provideFirstResponsitory(getApplicationContext()), this);
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

            if (ClassUtils.isNameEquals(fragmentName, WelcomeFragment.class)) {
                showWelcome();
            } else if (ClassUtils.isNameEquals(fragmentName, GuideFragment.class)) {
                showGuide();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, WelcomeFragment.class)) {
            welcomeFragment = (WelcomeFragment) fm.findFragmentByTag(fragmentTAG);
            welcomePresenter = new WelcomePresenter(
                    Injection.provideWelcomeResponsitory(getApplicationContext()), welcomeFragment);
        } else if (ClassUtils.isNameEquals(fragmentTAG, GuideFragment.class)) {
            guideFragment = (GuideFragment) fm.findFragmentByTag(fragmentTAG);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, WelcomeFragment.class)) {
            initWelcomeFragment();
        } else if (ClassUtils.isNameEquals(fragmentTAG, GuideFragment.class)) {
            initGuideFragment();
        }
    }

    /**
     * 初始化GuideFragment
     */
    private void initGuideFragment() {
        Log.i(TAG, "initGuideFragment");
        if (guideFragment == null) {
            guideFragment = GuideFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, guideFragment, R.id.firstFrame);
        }
    }

    /**
     * 初始化WelcomeFragment
     */
    private void initWelcomeFragment() {
        Log.i(TAG, "initWelcomeFragment");
        if (welcomeFragment == null) {
//            welcomeFragment = WelcomeFragment.newInstance();
            welcomeFragment = new WelcomeFragment();
            ActivityUtils.addFragmentToActivity(fm, welcomeFragment, R.id.firstFrame);
        }
        if (welcomePresenter == null) {
            welcomePresenter = new WelcomePresenter(
                    Injection.provideWelcomeResponsitory(getApplicationContext()), welcomeFragment);
        }
    }

    @Override
    public void showFragment(int position) {
        switch (position) {
            case 0:
                showFragment(WelcomeFragment.class.getName());
                break;
            case 1:
                showFragment(GuideFragment.class.getName());
                break;
            case 2:
                showAdvent();
                break;
        }
    }

    @Override
    public void showGuide() {
        Log.d(TAG, "showGuide");
        ActivityUtils.showFragment(fm, guideFragment);
    }

    @Override
    public void showAdvent() {
        Log.d(TAG, "showAdvent");
    }

    @Override
    public void showWelcome() {
        Log.d(TAG, "showWelcome");
        ActivityUtils.showFragment(fm, welcomeFragment);
    }

    @Override
    public void startMenuActivity() {
        Log.d(TAG, "startMenuActivity");
        Intent intent = new Intent(this, MainActivity.class);
        if (jumpBundle != null) {
            intent.putExtras(jumpBundle);
        }
        startActivityWithPushLeftAnim(intent);
        finish();
    }

    @Override
    public void finishCurrent() {
        mPresenter = null;
        finish();
    }

    @Override
    public void setPresenter(FirstContract.Presenter presenter) {
        mPresenter = presenter;
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

    /**
     * 关闭并finish当前页面
     */
    public void close$Finish() {
        //remove所有的Fragment
//        ActivityUtils.removeAllFragment(fm);
        finish();
        activitySwithPushRightAnim();
    }

    @Override
    public void onBackPressed() {
        if (welcomeFragment != null && !welcomeFragment.isHidden()) {
            //说明是Welcome页面
            //此頁面不允許回退
        } else {
            //remove所有的Fragment
            super.onBackPressed();
//            ActivityUtils.removeAllFragment(fm);
//            finish();
//            activitySwithPushRightAnim();
        }
    }
}
