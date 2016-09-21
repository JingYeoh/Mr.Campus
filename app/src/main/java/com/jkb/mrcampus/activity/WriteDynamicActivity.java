package com.jkb.mrcampus.activity;

import android.os.Bundle;

import com.jkb.mrcampus.base.BaseActivity;

/**
 * 写动态的Activity
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class WriteDynamicActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(1);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

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
}
