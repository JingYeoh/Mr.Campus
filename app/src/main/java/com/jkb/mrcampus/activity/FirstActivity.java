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
import com.jkb.mrcampus.fragment.first.WelcomeFragment;
import com.jkb.mrcampus.helper.ActivityUtils;

import static com.google.common.base.Preconditions.checkNotNull;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        if (savedInstanceState == null) {
            savedInstanceStateValued = false;
        } else {
            savedInstanceStateValued = true;
        }

        fm = getSupportFragmentManager();
        initFirstData(savedInstanceState);//初始化本页面的逻辑层数据
    }

    /**
     * 初始化页面本身的数据
     *
     * @param savedInstanceState
     */
    private void initFirstData(Bundle savedInstanceState) {
        firstPresenter = new FirstPresenter(Injection.provideFirstResponsitory(getApplicationContext()), this);
    }

    @Override
    protected void initView() {
    }

    @Override
    public void showFragment(String fragmentName) {

    }

    @Override
    protected void restoreFragments(String fragmentTAG) {

    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {

    }

    @Override
    public void showFragment(int position) {

    }

    @Override
    public void showGuide() {
        Log.d(TAG, "showGuide");
    }

    @Override
    public void showAdvent() {
        Log.d(TAG, "showAdvent");
    }

    @Override
    public void showWelcome() {
        Log.d(TAG, "showWelcome");
        //设置Fragment的值
        if (savedInstanceStateValued) {
            //此时发生内存重启后此处有数据
            welcomeFragment = (WelcomeFragment) fm.findFragmentByTag(WelcomeFragment.class.getName());
        } else {
            //第一次进入
            if (welcomeFragment == null) {
                welcomeFragment = WelcomeFragment.newInstance();
                ActivityUtils.addFragmentToActivity(fm, welcomeFragment, R.id.firstFrame);
            }
        }
        //初始化要用到的Presenter层数据
        if (welcomePresenter == null) {
            welcomePresenter = new WelcomePresenter(
                    Injection.provideWelcomeResponsitory(getApplicationContext()), welcomeFragment);
        }
    }

    @Override
    public void startMenuActivity() {
        Log.d(TAG, "startMenuActivity");
        Intent intent = new Intent(this, MainActivity.class);
        startActivityWithPushLeftAnim(intent);
        finish();
    }

    @Override
    public void finishCurrent() {
        mPresenter = null;
        finish();
    }

    /**
     * 显示AdventFragment
     */
    private void showAdventFragment() {
        Log.d(TAG, "showAdventFragment");
    }

    /**
     * 显示GuideFragment
     */
    private void showGuideFragment() {
        Log.d(TAG, "showGuideFragment");
    }

    @Override
    public void setPresenter(FirstContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
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
