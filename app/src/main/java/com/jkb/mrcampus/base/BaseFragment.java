package com.jkb.mrcampus.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.roger.gifloadinglibrary.GifLoadingView;

import java.util.List;

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
    protected FragmentManager fm;
    protected boolean savedInstanceStateValued = false;

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
            savedInstanceStateValued = true;
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
     * 显示Fragment
     *
     * @param fragmentName 类的Name
     */
    public void showFragment(String fragmentName) {
        //等待子类实现
    }

    /**
     * 恢复添加过的Presenter
     */
    protected void restoreFragments(String fragmentTAG) {
        //等待子类实现
    }

    /**
     * 恢复各个View层的Presenter
     */
    protected void restoreFragments() {
        List<Fragment> fragments = fm.getFragments();
        if (fragments == null || fragments.size() == 0) {
            return;
        }
        for (Fragment fragment : fragments) {
            if (fragment == null) {
                continue;
            }
            restoreFragments(fragment.getClass().getName());
        }
    }

    /**
     * 初始化展示的Fragment步骤1
     */
    protected void initFragmentStep1(Class<?> fragmentClass) {
        //判断是否被添加过
        if (!ActivityUtils.isFragmentAdded(fm, fragmentClass.getName())) {
            initFragmentStep2(fragmentClass);
        } else {
            if (savedInstanceStateValued) {//判断是否发生了内存重启
                Log.i(TAG, "发生了内存重启需要初始化fragment----------------");
                initFragmentStep2(fragmentClass);
            }
        }
    }

    /**
     * 初始化Fragment步骤2
     */
    protected void initFragmentStep2(Class<?> fragmentClass) {
        //等待子类实现
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
