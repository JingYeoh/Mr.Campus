package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.circle.CircleIndexPresenter;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.circle.CircleIndexFragment;
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

    //data
    private int circleId = 0;//圈子id
    private static final String SAVED_CIRCLE_ID = "saved_circle_id";
    private static final String SAVED_CURRENT_SHOW_FRAGMENT = "saved_current_show_fragment";
    //保存入栈的Fragment顺序
    private FragmentStack fragmentStack;
    private String currentShowFragment;


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
            currentShowFragment = ClassUtils.getClassName(CircleIndexFragment.class);
        } else {
            circleId = savedInstanceState.getInt(SAVED_CIRCLE_ID, 0);
            currentShowFragment = savedInstanceState.getString(SAVED_CURRENT_SHOW_FRAGMENT);
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
        //显示数据
        showFragment(currentShowFragment);
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

            if (ClassUtils.isNameEquals(fragmentName, CircleIndexFragment.class)) {
                showCircleIndex();
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
        }
    }


    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, CircleIndexFragment.class)) {
            initCircleIndex();
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
        if (circleIndexPresenter == null) {
            circleIndexPresenter = new CircleIndexPresenter(circleIndexFragment,
                    Injection.provideCircleIndexDataResponsitiry(getApplicationContext()));
        }
    }

    /**
     * 显示圈子首页
     */
    private void showCircleIndex() {
        Log.d(TAG, "showCircleIndex");
        ActivityUtils.showFragment(fm, circleIndexFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_CIRCLE_ID, circleId);
        outState.putString(SAVED_CURRENT_SHOW_FRAGMENT, currentShowFragment);
        outState.putStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK,
                fragmentStack.getFragmetStackNames());
    }
}
