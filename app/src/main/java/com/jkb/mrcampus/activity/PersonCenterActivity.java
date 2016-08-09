package com.jkb.mrcampus.activity;

import android.os.Bundle;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;

/**
 * 个人中心的控制器
 * Created by JustKiddingBaby on 2016/8/8.
 */

public class PersonCenterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRootView(R.layout.aty_personcenter);
        init(savedInstanceState);
        super.onCreate(savedInstanceState);
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
