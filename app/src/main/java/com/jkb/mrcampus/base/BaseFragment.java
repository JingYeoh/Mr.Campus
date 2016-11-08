package com.jkb.mrcampus.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.roger.gifloadinglibrary.GifLoadingView;

import dalvik.annotation.TestTarget;

/**
 * 所有Fragment类的基类
 * Fragment类：MVP架构中的V层
 * Created by JustKiddingBaby on 2016/7/20.
 */
public abstract class BaseFragment extends Fragment {

    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    protected String TAG = this.getClass().getSimpleName();
    protected Context context;
    protected Activity mActivity;
    protected View rootView;
    private int rootViewId;

    //颜色
    protected int COLOR_MAIN_THEME_GREEN;
    protected int COLOR_MAIN_THEME;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (this.rootView == null) {
            this.rootView = inflater.inflate(rootViewId, container, false);
            initColor();
        }
        //处理页面是否隐藏的问题
        if (savedInstanceState != null) {
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
        return this.rootView;
    }

    private void initColor() {
        COLOR_MAIN_THEME_GREEN = mActivity.getResources().getColor(R.color.main_theme_green);
        COLOR_MAIN_THEME = mActivity.getResources().getColor(R.color.main_theme);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context.getApplicationContext();
        this.mActivity = (Activity) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        this.context = activity;
        this.mActivity = activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mActivity = null;
        this.context = null;
        this.rootView = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    /**
     * 初始化方法
     */
    protected void init(Bundle savedInstanceState) {
        initView();
        initData(savedInstanceState);
        initListener();
    }

    /**
     * 初始化监听器
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 设置父布局的ID
     */
    protected void setRootView(int rootViewId) {
        this.rootViewId = rootViewId;
    }

    /**
     * 顯示Toast信息：短的
     */
    public void showShortToash(String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toast信息：长的
     */
    public void showLongToash(String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
    }
}
