package com.jkb.mrcampus.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.fragment.dialog.GifLoadingView2;
import com.jkb.mrcampus.singleton.ActivityStackManager;
import com.jkb.mrcampus.helper.ActivityUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 帶有侧滑菜單的Activity基类
 * Created by JustKiddingBaby on 2016/7/21.
 */
public abstract class BaseSlideMenuActivity extends SlidingFragmentActivity {

    protected String TAG = this.getClass().getSimpleName();
    protected Context context;
    protected View rootView;
    //页面是否发生内存重启并且有数据
    protected boolean savedInstanceStateValued = false;

    private static boolean isExit = false;//连按两次退出程序的标识值

    protected FragmentManager fm;

    //展示视图
    protected GifLoadingView2 gifLoadingView;

    //单例类
    protected ActivityStackManager activityManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityManager = ActivityStackManager.getInstance();
        activityManager.addActivity(this);

        if (savedInstanceState != null) {
            savedInstanceStateValued = true;
        } else {
            savedInstanceStateValued = false;
        }
    }

    /**
     * 初始化方法
     *
     * @param savedInstanceState
     */
    protected void init(Bundle savedInstanceState) {
        context = this;
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
     *
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 设置父布局的ID
     *
     * @param rootViewId
     */
    protected void setRootView(@NonNull int rootViewId) {
//        checkNotNull(rootViewId, "传入布局id不能为空！");
        this.rootView = LayoutInflater.from(this).inflate(rootViewId, null);
        setContentView(this.rootView);
    }

    /**
     * 恢复各个View层的Presenter
     */
    protected void restorePresenters() {
        List<Fragment> fragments = fm.getFragments();
        for (Fragment fragment : fragments) {
            restorePresenter(fragment.getClass().getName());
        }
    }

    /**
     * 恢复添加过的Presenter
     *
     * @param fragmentTAG
     */
    protected abstract void restorePresenter(String fragmentTAG);

    /**
     * 初始化展示的Fragment步骤1
     *
     * @param fragmentClass
     */
    protected void initFragmentStep1(Class<?> fragmentClass) {
        //判断是否被添加过
        if (!ActivityUtils.isFragmentAdded(fm, fragmentClass.getName())) {
            initFragmentStep2(fragmentClass);
        } else {
            if (!savedInstanceStateValued) {//判断是否发生了内存重启
                initFragmentStep2(fragmentClass);
            }
        }
    }

    /**
     * 初始化Fragment步骤2
     */
    protected abstract void initFragmentStep2(Class<?> fragmentClass);

    /**
     * 开启新的Activity使用左侧进入动画
     *
     * @param intent
     */
    protected void startActivityWithPushLeftAnim(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.animator.push_left_in, R.animator.push_left_out);
    }

    /**
     * 顯示Toash信息：短的
     *
     * @param value
     */

    public void showShortToast(String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示Toash信息：长的
     *
     * @param value
     */
    public void showLongToast(String value) {
        if (value == null || value.isEmpty()) {
            return;
        }
        Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityManager.removeActivity(this);
    }

    /**
     * 连续按两次退出
     */
    public void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true;
            // 准备退出
            showShortToast("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            //退出程序
            activityManager.exit();
        }
    }

    /**
     * 显示Loading加载效果
     *
     * @param value
     */
    public void showLoading(String value) {
        if (gifLoadingView == null) {
            gifLoadingView = new GifLoadingView2();
        }
        gifLoadingView.setImageResource(R.drawable.num31);
        gifLoadingView.show(getFragmentManager(), null);
    }

    /**
     * 取消加载的loading效果
     */
    public void dismissLoading() {
        if (gifLoadingView != null) {
            gifLoadingView.dismiss();
        }
    }
}
