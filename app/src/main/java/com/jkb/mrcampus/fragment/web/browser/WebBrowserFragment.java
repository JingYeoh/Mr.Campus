package com.jkb.mrcampus.fragment.web.browser;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.WebBrowserActivity;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * web浏览器的V层
 * Created by JustKiddingBaby on 2016/11/28.
 */

public class WebBrowserFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public static WebBrowserFragment newInstance(String webUrl, String title) {
        Bundle args = new Bundle();
        args.putString(Config.BUNDLE_KEY_WEB_BROWSER_URL, webUrl);
        args.putString(Config.BUNDLE_KEY_WEB_BROWSER_TITLE, title);
        WebBrowserFragment fragment = new WebBrowserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private String mWebUrl;
    private String mTitle;
    private WebBrowserActivity webBrowserActivity;
    //view
    private WebView mWebView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        webBrowserActivity = (WebBrowserActivity) mActivity;
        setRootView(R.layout.frg_web_browser);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            mWebUrl = args.getString(Config.BUNDLE_KEY_WEB_BROWSER_URL);
            mTitle = args.getString(Config.BUNDLE_KEY_WEB_BROWSER_TITLE, "菌菌");
        } else {
            mWebUrl = savedInstanceState.getString(Config.BUNDLE_KEY_WEB_BROWSER_URL);
            mTitle = savedInstanceState.getString(Config.BUNDLE_KEY_WEB_BROWSER_TITLE);
        }
        //设置标题
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText(mTitle);
        initWebView();
    }

    @Override
    protected void initView() {
        mWebView = (WebView) rootView.findViewById(R.id.fwb_wv);
        progressBar = (ProgressBar) rootView.findViewById(R.id.fwb_pb);
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fwb_srl);
    }

    /**
     * 初始化WebView
     */
    private void initWebView() {
        mWebView.loadUrl(mWebUrl);
        // /////////////////设置缓存/////////////////////////////////////////////
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置
        // 缓存模式
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        // 开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = webBrowserActivity.getFilesDir().getAbsolutePath()
                + com.jkb.api.config.Config.APP_CACAHE_DIRNAME_WEBVIEW;
        // String cacheDirPath =
        // getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
        Log.i(TAG, "cacheDirPath=" + cacheDirPath);
        // 设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        // 设置 Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        // 开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
        // /////////////////////////////////////////////////////////////////////////////////////
        mWebView.setWebViewClient(webViewClient);
        // 设置加载进度条
        mWebView.setWebChromeClient(webChromeClient);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Config.BUNDLE_KEY_WEB_BROWSER_URL, mWebUrl);
        outState.putString(Config.BUNDLE_KEY_WEB_BROWSER_TITLE, mTitle);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webBrowserActivity = null;
        //view
        mWebUrl = null;
        progressBar = null;
        refreshLayout = null;
        //listener
        webViewClient = null;
        webChromeClient = null;
    }

    /**
     * 回退
     */
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            webBrowserActivity.finishActivity();
        }
    }

    //加载过程
    private WebChromeClient webChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
                // 设置刷新控件不可见
                refreshLayout.setRefreshing(false);
            } else {
                if (View.GONE == progressBar.getVisibility()) {
                    progressBar.setVisibility(View.VISIBLE);
                }
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }
    };
    //监听加载
    private WebViewClient webViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            // showLoad("加载中");
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
        }
    };

    @Override
    public void onRefresh() {
        mWebView.loadUrl(mWebUrl);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                webBrowserActivity.finishActivity();
                break;
        }
    }
}
