package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.web.browser.WebBrowserFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * web浏览的页面控制器
 * Created by JustKiddingBaby on 2016/11/28.
 */

public class WebBrowserActivity extends BaseActivity {

    private int contentView = R.id.webBrowserFrame;

    //webView
    private WebBrowserFragment webBrowserFragment;
    private String mWebUrl;
    private String mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_web_browser);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            mWebUrl = intent.getStringExtra(Config.BUNDLE_KEY_WEB_BROWSER_URL);
            mTitle = intent.getStringExtra(Config.BUNDLE_KEY_WEB_BROWSER_TITLE);
            showFragment(ClassUtils.getClassName(WebBrowserFragment.class));
        } else {
            mWebUrl = savedInstanceState.getString(Config.BUNDLE_KEY_WEB_BROWSER_URL);
            mTitle = savedInstanceState.getString(Config.BUNDLE_KEY_WEB_BROWSER_TITLE);
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
            if (ClassUtils.isNameEquals(fragmentName, WebBrowserFragment.class)) {
                showWebBrowser();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, WebBrowserFragment.class)) {
            webBrowserFragment = (WebBrowserFragment) fm.findFragmentByTag(fragmentTAG);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, WebBrowserFragment.class)) {
            initWebBrowser();
        }
    }

    /**
     * 初始化浏览器
     */
    private void initWebBrowser() {
        if (webBrowserFragment == null) {
            webBrowserFragment = WebBrowserFragment.newInstance(mWebUrl, mTitle);
            ActivityUtils.addFragmentToActivity(fm, webBrowserFragment, contentView);
        }
    }

    /**
     * 显示浏览器
     */
    private void showWebBrowser() {
        ActivityUtils.showFragment(fm, webBrowserFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Config.BUNDLE_KEY_WEB_BROWSER_URL, mWebUrl);
        outState.putString(Config.BUNDLE_KEY_WEB_BROWSER_TITLE, mTitle);
    }

    @Override
    public void onBackPressed() {
        if (webBrowserFragment != null) {
            webBrowserFragment.onBackPressed();
        }
    }

    /**
     * 结束该页面
     */
    public void finishActivity() {
        super.onBackPressed();
    }
}
