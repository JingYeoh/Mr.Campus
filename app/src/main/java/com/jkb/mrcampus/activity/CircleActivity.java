package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.circle.CircleAttentionUserListPresenter;
import com.jkb.core.presenter.circle.CircleDynamicInCircleBlackListPresenter;
import com.jkb.core.presenter.circle.CircleIndexPresenter;
import com.jkb.core.presenter.circle.CircleSettingUserPresenter;
import com.jkb.core.presenter.circle.CircleSettingVisitorPresenter;
import com.jkb.core.presenter.circle.CircleUserInBlackListPresenter;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.circle.CircleAttentionUserListFragment;
import com.jkb.mrcampus.fragment.circle.CircleDynamicInCircleBlackListFragment;
import com.jkb.mrcampus.fragment.circle.CircleIndexFragment;
import com.jkb.mrcampus.fragment.circle.CircleUserSettingFragment;
import com.jkb.mrcampus.fragment.circle.CircleVisitorSettingFragment;
import com.jkb.mrcampus.fragment.personCenter.CircleUserInCircleBlackListFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.helper.FragmentStack;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 圈子相关的页面控制器
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleActivity extends BaseActivity {

    //View
    private int contentViewId = 0;//布局的父类布局容器

    //圈子首页
    private CircleIndexFragment circleIndexFragment;
    private CircleIndexPresenter circleIndexPresenter;

    //用户圈子设置
    private CircleUserSettingFragment circleUserSettingFragment;
    private CircleSettingUserPresenter circleSettingUserPresenter;

    //访客圈子设置
    private CircleVisitorSettingFragment circleVisitorSettingFragment;
    private CircleSettingVisitorPresenter circleSettingVisitorPresenter;

    //圈子内成员
    private CircleAttentionUserListFragment circleAttentionUserListFragment;
    private CircleAttentionUserListPresenter circleAttentionUserListPresenter;
    private boolean isCircleCreator;

    //禁闭室
    private CircleUserInCircleBlackListFragment circleUserInCircleBlackListFragment;
    private CircleUserInBlackListPresenter circleUserInBlackListPresenter;

    //小黑屋
    private CircleDynamicInCircleBlackListFragment circleDynamicInCircleBlackListFragment;
    private CircleDynamicInCircleBlackListPresenter circleDynamicInCircleBlackListPresenter;

    //data
    private int circleId = 0;//圈子id
    private static final String SAVED_CIRCLE_ID = "saved_circle_id";
    //保存入栈的Fragment顺序
    private FragmentStack fragmentStack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_circle);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        contentViewId = R.id.circleFrame;//设置布局的id
        fm = getSupportFragmentManager();
        fragmentStack = new FragmentStack();

        if (!savedInstanceStateValued) {
            //初始化数据
            Intent intent = getIntent();
            circleId = intent.getIntExtra(Config.INTENT_KEY_CIRCLE_ID, 0);
            //显示数据
            showFragment(ClassUtils.getClassName(CircleIndexFragment.class));
        } else {
            circleId = savedInstanceState.getInt(SAVED_CIRCLE_ID, 0);
            //恢复Fragment
            restoreFragments();
            //恢复保存的栈数据
            fragmentStack.setFragmetStackNames(
                    savedInstanceState.getStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK));
        }
        Log.d(TAG, "circleId=" + circleId);
        //判断是否有数据
        if (circleId == 0) {
            //返回
            showShortToast("圈子不存在");
            onBackPressed();
            return;
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
            //设置常量
            if (ClassUtils.isNameEquals(fragmentName, CircleIndexFragment.class)) {
                showCircleIndex();
            } else if (ClassUtils.isNameEquals(fragmentName, CircleUserSettingFragment.class)) {
                showCircleSettingUser();
            } else if (ClassUtils.isNameEquals(fragmentName, CircleVisitorSettingFragment.class)) {
                showCircleSettingVisitor();
            } else if (ClassUtils.isNameEquals(fragmentName, CircleAttentionUserListFragment.class)) {
                showCircleAttentionUserList();
            } else if (ClassUtils.isNameEquals(fragmentName, CircleUserInCircleBlackListFragment.class)) {
                showCircleUserInBlackList();
            } else if (ClassUtils.isNameEquals(fragmentName, CircleDynamicInCircleBlackListFragment.class)) {
                showCircleDynamicInBlackList();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, CircleIndexFragment.class)) {
            circleIndexFragment = (CircleIndexFragment) fm.findFragmentByTag(fragmentTAG);
            circleIndexPresenter = new CircleIndexPresenter(circleIndexFragment,
                    Injection.provideCircleIndexDataResponsitiry(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, CircleUserSettingFragment.class)) {
            circleUserSettingFragment = (CircleUserSettingFragment)
                    fm.findFragmentByTag(fragmentTAG);
            circleSettingUserPresenter = new CircleSettingUserPresenter(circleUserSettingFragment,
                    Injection.provideCircleSettingUserDataRepertory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, CircleVisitorSettingFragment.class)) {
            circleVisitorSettingFragment = (CircleVisitorSettingFragment)
                    fm.findFragmentByTag(fragmentTAG);
            circleSettingVisitorPresenter = new CircleSettingVisitorPresenter(circleVisitorSettingFragment,
                    Injection.provideCircleSettingUserDataRepertory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, CircleAttentionUserListFragment.class)) {
            circleAttentionUserListFragment = (CircleAttentionUserListFragment)
                    fm.findFragmentByTag(fragmentTAG);
            circleAttentionUserListPresenter = new CircleAttentionUserListPresenter(
                    circleAttentionUserListFragment,
                    Injection.provideCircleAttentionUserListRepertory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, CircleUserInCircleBlackListFragment.class)) {
            circleUserInCircleBlackListFragment = (CircleUserInCircleBlackListFragment)
                    fm.findFragmentByTag(fragmentTAG);
            circleUserInBlackListPresenter = new
                    CircleUserInBlackListPresenter(circleUserInCircleBlackListFragment,
                    Injection.provideCircleAttentionUserListRepertory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, CircleDynamicInCircleBlackListFragment.class)) {
            circleDynamicInCircleBlackListFragment = (CircleDynamicInCircleBlackListFragment)
                    fm.findFragmentByTag(fragmentTAG);
            circleDynamicInCircleBlackListPresenter = new
                    CircleDynamicInCircleBlackListPresenter(circleDynamicInCircleBlackListFragment,
                    Injection.provideCircleDynamicInCircleBlackListRepertory(getApplicationContext()));
        }
    }


    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, CircleIndexFragment.class)) {
            initCircleIndex();
        } else if (ClassUtils.isNameEquals(fragmentTAG, CircleUserSettingFragment.class)) {
            initCircleSettingUser();
        } else if (ClassUtils.isNameEquals(fragmentTAG, CircleVisitorSettingFragment.class)) {
            initCircleSettingVisitor();
        } else if (ClassUtils.isNameEquals(fragmentTAG, CircleAttentionUserListFragment.class)) {
            initCircleAttentionUserList();
        } else if (ClassUtils.isNameEquals(fragmentTAG, CircleUserInCircleBlackListFragment.class)) {
            initCircleUserInBlackList();
        } else if (ClassUtils.isNameEquals(fragmentTAG, CircleDynamicInCircleBlackListFragment.class)) {
            initCircleDynamicInBlackList();
        }
    }

    /**
     * 初始化圈子首页
     */
    private void initCircleIndex() {
        if (circleIndexFragment == null) {
            circleIndexFragment = circleIndexFragment.newInstance(circleId);
            ActivityUtils.addFragmentToActivity(fm, circleIndexFragment, contentViewId);
        }
        circleIndexPresenter = new CircleIndexPresenter(circleIndexFragment,
                Injection.provideCircleIndexDataResponsitiry(getApplicationContext()));
    }

    /**
     * 初始化访客圈子设置
     */
    private void initCircleSettingVisitor() {
        LogUtils.d(TAG, "initCircleSettingVisitor");
        if (circleVisitorSettingFragment == null) {
            circleVisitorSettingFragment = CircleVisitorSettingFragment.newInstance(circleId);
            ActivityUtils.addFragmentToActivity(fm, circleVisitorSettingFragment, contentViewId);
        }
        circleSettingVisitorPresenter = new CircleSettingVisitorPresenter(circleVisitorSettingFragment,
                Injection.provideCircleSettingUserDataRepertory(getApplicationContext()));
    }

    /**
     * 初始化用户圈子设置
     */
    private void initCircleSettingUser() {
        LogUtils.d(TAG, "initCircleSettingUser");
        if (circleUserSettingFragment == null) {
            circleUserSettingFragment = CircleUserSettingFragment.newInstance(circleId);
            ActivityUtils.addFragmentToActivity(fm, circleUserSettingFragment, contentViewId);
        }
        circleSettingUserPresenter = new CircleSettingUserPresenter(circleUserSettingFragment,
                Injection.provideCircleSettingUserDataRepertory(getApplicationContext()));
    }

    /**
     * 初始化圈子成员列表
     */
    private void initCircleAttentionUserList() {
        LogUtils.d(TAG, "initCircleAttentionUserList");
        if (circleAttentionUserListFragment == null) {
            circleAttentionUserListFragment =
                    CircleAttentionUserListFragment.newInstance(circleId, isCircleCreator);
            ActivityUtils.addFragmentToActivity(fm, circleAttentionUserListFragment, contentViewId);
        }
        circleAttentionUserListPresenter = new CircleAttentionUserListPresenter(
                circleAttentionUserListFragment,
                Injection.provideCircleAttentionUserListRepertory(getApplicationContext()));
    }

    /**
     * 初始化圈子黑名单用户
     */
    private void initCircleUserInBlackList() {
        LogUtils.d(TAG, "initCircleUserInBlackList");
        if (circleUserInCircleBlackListFragment == null) {
            circleUserInCircleBlackListFragment =
                    CircleUserInCircleBlackListFragment.newInstance(circleId);
            ActivityUtils.addFragmentToActivity(
                    fm, circleUserInCircleBlackListFragment, contentViewId);
        }
        circleUserInBlackListPresenter = new
                CircleUserInBlackListPresenter(circleUserInCircleBlackListFragment,
                Injection.provideCircleAttentionUserListRepertory(getApplicationContext()));
    }

    /**
     * 初始化圈子动态黑名单
     */
    private void initCircleDynamicInBlackList() {
        LogUtils.d(TAG, "initCircleDynamicInBlackList");
        if (circleDynamicInCircleBlackListFragment == null) {
            circleDynamicInCircleBlackListFragment =
                    CircleDynamicInCircleBlackListFragment.newInstance(circleId);
            ActivityUtils.addFragmentToActivity(
                    fm, circleDynamicInCircleBlackListFragment, contentViewId);
        }
        circleDynamicInCircleBlackListPresenter = new
                CircleDynamicInCircleBlackListPresenter(circleDynamicInCircleBlackListFragment,
                Injection.provideCircleDynamicInCircleBlackListRepertory(getApplicationContext()));
    }

    /**
     * 显示圈子首页
     */
    private void showCircleIndex() {
        Log.d(TAG, "showCircleIndex");
        ActivityUtils.showFragment(fm, circleIndexFragment);
    }

    /**
     * 显示访客圈子设置页面
     */
    private void showCircleSettingVisitor() {
        Log.d(TAG, "showCircleSettingVisitor");
        ActivityUtils.showFragment(fm, circleVisitorSettingFragment);
    }

    /**
     * 显示用户圈子设置页面
     */
    private void showCircleSettingUser() {
        Log.d(TAG, "showCircleSettingUser");
        ActivityUtils.showFragment(fm, circleUserSettingFragment);
    }

    /**
     * 显示圈子内用户列表
     */
    private void showCircleAttentionUserList() {
        Log.d(TAG, "showCircleAttentionUserList");
        ActivityUtils.showFragment(fm, circleAttentionUserListFragment);
    }

    /**
     * 显示圈子用户黑名单
     */
    private void showCircleUserInBlackList() {
        Log.d(TAG, "showCircleUserInBlackList");
        ActivityUtils.showFragment(fm, circleUserInCircleBlackListFragment);
    }

    /**
     * 显示圈子动态黑名单
     */
    private void showCircleDynamicInBlackList() {
        Log.d(TAG, "showCircleDynamicInBlackList");
        ActivityUtils.showFragment(fm, circleDynamicInCircleBlackListFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_CIRCLE_ID, circleId);
        outState.putStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK,
                fragmentStack.getFragmetStackNames());
    }

    @Override
    public void onBackPressed() {
        backToLastView();
    }

    /**
     * 返回到上级页面
     */
    public void backToLastView() {
        //如果当前页面就是登录页面的时候
        if (ClassUtils.isNameEquals(fragmentStack.getCurrentFragmentName(),
                CircleIndexFragment.class)) {
            super.onBackPressed();
            return;
        }
        //先销毁使用过后的Fragment
        removeCurrentFragment();

        String fragmentName = fragmentStack.getCurrentFragmentName();
        //如果到最顶层栈或者到了登录页面的时候，再次回退就直接销毁当前页面
        if (fragmentName == null) {
            //返回上级页面
            super.onBackPressed();
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

    /**
     * 显示用户圈子设置页面
     */
    public void showUserCircleSetting() {
        showFragment(ClassUtils.getClassName(CircleUserSettingFragment.class));
    }

    /**
     * 显示访客圈子设置页面
     */
    public void showVisitorCircleSetting() {
        showFragment(ClassUtils.getClassName(CircleVisitorSettingFragment.class));
    }

    /**
     * 显示圈子成员
     */
    public void showAttentionUserList(boolean isCircleCreator) {
        this.isCircleCreator = isCircleCreator;
        showFragment(ClassUtils.getClassName(CircleAttentionUserListFragment.class));
    }

    /**
     * 显示圈子内用户黑名单
     */
    public void showUserInCircleBlackList() {
        showFragment(ClassUtils.getClassName(CircleUserInCircleBlackListFragment.class));
    }

    /**
     * 显示圈子动态黑名单
     */
    public void showDynamicInBlackList() {
        showFragment(ClassUtils.getClassName(CircleDynamicInCircleBlackListFragment.class));
    }
}
