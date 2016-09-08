package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.circleList.CircleListPresenter;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.circle.CircleListUserPayAttentionFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.helper.FragmentStack;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 圈子列表的View层
 * 其中包含圈子列表视图
 * 传递信息：必须传递进来用户的id
 * 功能：显示用户关注的所有圈子
 * Created by JustKiddingBaby on 2016/9/1.
 */

public class CircleListActivity extends BaseActivity {

    //Data
    private int user_id = -1;//用户的id
    private String currentShowFragment;

    //保存入栈的Fragment顺序
    private FragmentStack fragmentStack;

    //常量
    private static final String CURRENT_SHOW_FRAGMENT = "current_show_fragment";

    //View
    private int contentView;

    //要显示的View
    //圈子列表
    private CircleListUserPayAttentionFragment circleListFragment;
    private CircleListPresenter circleListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_circle_list);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        fragmentStack = new FragmentStack();

        if (savedInstanceState == null) {
            //初始化用户id
            Intent intent = getIntent();
            user_id = intent.getIntExtra(Config.INTENT_KEY_USER_ID, -1);
            currentShowFragment = ClassUtils.getClassName(CircleListUserPayAttentionFragment.class);
            showFragment(currentShowFragment);
        } else {
            user_id = savedInstanceState.getInt(Config.INTENT_KEY_USER_ID, -1);
            currentShowFragment = savedInstanceState.getString(CURRENT_SHOW_FRAGMENT);
            restoreFragments();
            fragmentStack.setFragmetStackNames(
                    savedInstanceState.getStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK));
        }
        if (user_id == -1) {
            //关闭页面
            showShortToast("用户不存在");
            onBackPressed();
            return;
        }
    }

    @Override
    protected void initView() {
        contentView = R.id.circleListFrame;
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

            if (ClassUtils.isNameEquals(fragmentName, CircleListUserPayAttentionFragment.class)) {
                showCircleList();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, CircleListUserPayAttentionFragment.class)) {
            circleListFragment = (CircleListUserPayAttentionFragment)
                    fm.findFragmentByTag(fragmentTAG);
            circleListPresenter = new CircleListPresenter(circleListFragment,
                    Injection.provideCircleListDataResponsitory(getApplicationContext()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();

        if (ClassUtils.isNameEquals(fragmentTAG, CircleListUserPayAttentionFragment.class)) {
            initCircleList();
        }
    }

    /**
     * 初始化圈子列表
     */
    private void initCircleList() {
        if (circleListFragment == null) {
            circleListFragment = CircleListUserPayAttentionFragment.newInstance(user_id);
            ActivityUtils.addFragmentToActivity(fm, circleListFragment, contentView);
        }
        if (circleListPresenter == null) {
            circleListPresenter = new CircleListPresenter(circleListFragment,
                    Injection.provideCircleListDataResponsitory(getApplicationContext()));
        }
    }

    /**
     * 显示圈子列表
     */
    private void showCircleList() {
        Log.d(TAG, "showCircleList");
        ActivityUtils.showFragment(fm, circleListFragment);
    }

    /**
     * 显示圈子页面
     */
    public void startCircleView(@NonNull int circle_id) {
        Intent intent = new Intent(this, CircleActivity.class);
        intent.putExtra(Config.INTENT_KEY_CIRCLE_ID, circle_id);
        startActivityWithPushLeftAnim(intent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_USER_ID, user_id);
        outState.putString(CURRENT_SHOW_FRAGMENT, currentShowFragment);
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
                CircleListUserPayAttentionFragment.class)) {
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
}
