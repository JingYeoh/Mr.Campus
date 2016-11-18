package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.function.tools.ToolsCETPresenter;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.function.tools.function.ToolsCETFunctionFragment;
import com.jkb.mrcampus.fragment.function.tools.function.ToolsCourierFunctionFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 小工具的具体功能控制器
 * Created by JustKiddingBaby on 2016/11/15.
 */

public class ToolsFunctionActivity extends BaseActivity {

    //data
    private int contentView = R.id.toolsFunctionContent;
    private int currentToolsType;

    //CET查询
    private ToolsCETFunctionFragment toolsCETFunctionFragment;
    private ToolsCETPresenter toolsCETPresenter;

    //快递查询
    private ToolsCourierFunctionFragment toolsCourierFunctionFragment;

    //常量
    public static final int TOOLS_CET = 1001;
    public static final int TOOLS_COURIER = 1002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_tools_function);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            currentToolsType = intent.getIntExtra(Config.INTENT_KEY_TOOLS_TYPE, 0);
        } else {
            currentToolsType = savedInstanceState.getInt(Config.INTENT_KEY_TOOLS_TYPE, 0);
        }
        if (currentToolsType == 0) {
            showShortToast("无该类型");
            super.onBackPressed();
            return;
        }
        //判断是否有该选项
        filterToolsType();
    }

    /**
     * 筛选小工具类型
     */
    private void filterToolsType() {
        switch (currentToolsType) {
            case TOOLS_CET://四六级
                showFragment(ClassUtils.getClassName(ToolsCETFunctionFragment.class));
                break;
            case TOOLS_COURIER://快递
                showFragment(ClassUtils.getClassName(ToolsCourierFunctionFragment.class));
                break;
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

            if (ClassUtils.isNameEquals(fragmentName, ToolsCETFunctionFragment.class)) {//四六级
                showToolsFunctionCET();
            } else if (ClassUtils.isNameEquals(fragmentName, ToolsCourierFunctionFragment.class)) {
                showToolsFunctionCourier();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, ToolsCETFunctionFragment.class)) {//四六级
            toolsCETFunctionFragment = (ToolsCETFunctionFragment) fm.findFragmentByTag(fragmentTAG);
            toolsCETPresenter = new ToolsCETPresenter(toolsCETFunctionFragment,
                    Injection.provideToolsCETRepertory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, ToolsCourierFunctionFragment.class)) {
            toolsCourierFunctionFragment = (ToolsCourierFunctionFragment)
                    fm.findFragmentByTag(fragmentTAG);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();

        if (ClassUtils.isNameEquals(fragmentTAG, ToolsCETFunctionFragment.class)) {
            initToolsFunctionCET();
        } else if (ClassUtils.isNameEquals(fragmentTAG, ToolsCourierFunctionFragment.class)) {
            initToolsFunctionCourier();
        }
    }

    /**
     * 初始化快递查询
     */
    private void initToolsFunctionCourier() {
        if (toolsCourierFunctionFragment == null) {
            toolsCourierFunctionFragment = ToolsCourierFunctionFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, toolsCourierFunctionFragment, contentView);
        }
    }

    /**
     * 初始化四六级查询
     */
    private void initToolsFunctionCET() {
        if (toolsCETFunctionFragment == null) {
            toolsCETFunctionFragment = ToolsCETFunctionFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, toolsCETFunctionFragment, contentView);
        }
        toolsCETPresenter = new ToolsCETPresenter(toolsCETFunctionFragment,
                Injection.provideToolsCETRepertory(getApplicationContext()));
    }

    /**
     * 显示查询快递页面
     */
    private void showToolsFunctionCourier() {
        Log.d(TAG, "showToolsFunctionCourier");
        ActivityUtils.showFragment(fm, toolsCourierFunctionFragment);
    }

    /**
     * 显示四六级查询页面
     */
    private void showToolsFunctionCET() {
        Log.d(TAG, "showToolsFunctionCET");
        ActivityUtils.showFragment(fm, toolsCETFunctionFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_TOOLS_TYPE, currentToolsType);
    }
}
