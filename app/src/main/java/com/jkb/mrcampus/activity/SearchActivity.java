package com.jkb.mrcampus.activity;

import android.os.Bundle;
import android.util.Log;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.search.SearchFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 搜索的页面控制器
 * Created by JustKiddingBaby on 2016/11/15.
 */

public class SearchActivity extends BaseActivity {

    //data
    private int contentView = R.id.searchFrame;
    //搜索
    private SearchFragment searchFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_search);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            showFragment(SearchFragment.class.getName());
        } else {
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

            if (ClassUtils.isNameEquals(fragmentName, SearchFragment.class)) {
                showSearch();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, SearchFragment.class)) {
            searchFragment = (SearchFragment) fm.findFragmentByTag(fragmentTAG);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, SearchFragment.class)) {
            initSearch();
        }
    }

    /**
     * 初始化搜索
     */
    private void initSearch() {
        if (searchFragment == null) {
            searchFragment = SearchFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, searchFragment, contentView);
        }
    }

    /**
     * 显示搜索页面
     */
    private void showSearch() {
        ActivityUtils.showFragment(fm, searchFragment);
    }
}
