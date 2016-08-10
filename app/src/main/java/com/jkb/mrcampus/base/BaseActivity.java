package com.jkb.mrcampus.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.fragment.dialog.ChoosePictureFragment;
import com.jkb.mrcampus.fragment.dialog.GifLoadingView2;
import com.jkb.mrcampus.singleton.ActivityStackManager;
import com.jkb.mrcampus.helper.ActivityUtils;

import java.util.List;


/**
 * 所有Activity类的基类
 * Activity类：全局的控制者，负责创建View以及Presenter的实例，并且将二者联系起来
 * 注：在此处Activity不是视图的载体，View的显示及UI的载体为Fragment
 * Created by JustKiddingBaby on 2016/7/20.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = this.getClass().getSimpleName();
    protected Context context;
    protected View rootView;
    //页面是否发生内存重启并且有数据
    protected boolean savedInstanceStateValued = false;

    protected FragmentManager fm;

    //展示视图
    protected GifLoadingView2 gifLoadingView;
    private ChoosePictureFragment choosePictureFragment;

    //单例类
    protected ActivityStackManager activityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //添加Activity到管理者
        activityManager = ActivityStackManager.getInstance();
        activityManager.addActivity(this);
        //初始化是否内存重启的标识
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
        fm=getSupportFragmentManager();
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
     * 显示Fragment
     *
     * @param fragmentName 类的Name
     */
    public abstract void showFragment(String fragmentName);

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
     * 恢复添加过的Presenter
     *
     * @param fragmentTAG
     */
    protected abstract void restoreFragments(String fragmentTAG);

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
                Log.i(TAG, "发生了内存重启需要初始化fragment----------------");
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
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeCustomAnimation(this,
                R.animator.push_left_in, R.animator.push_left_out);
        ActivityCompat.startActivity(this, intent, compat.toBundle());
//        startActivity(intent);
//        overridePendingTransition(R.animator.push_left_in, R.animator.push_left_out);
    }

    /**
     * 关闭当前Activity并使用右侧滑出动画
     */
    protected void activitySwithPushRightAnim() {
        overridePendingTransition(R.animator.push_right_in, R.animator.push_right_out);
//        ActivityCompat.finishAfterTransition(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        activitySwithPushRightAnim();
    }

    /**
     * 顯示Toast信息：短的
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
     * 显示Toast信息：长的
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
        //销毁所有的Fragment
        ActivityUtils.removeAllFragment(fm);
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

    /**
     * 显示加载图片的Dialog
     */
    public void showChoosePictureDialog() {
        if (choosePictureFragment == null) {
            choosePictureFragment = new ChoosePictureFragment();
        }
        choosePictureFragment.show(getFragmentManager(), "showChoosePictureDialog");
    }

    /**
     * 选择图片方式的监听器
     *
     * @param listener
     */
    public void setChoosePictureWayListener(ChoosePictureFragment.PictureChooseWayListener listener) {
        if (choosePictureFragment != null) {
            choosePictureFragment.setPictureSelectedListener(listener);
        }
    }

    /**
     * 取消所有的显示子视图
     */
    public void dismiss() {
        if (choosePictureFragment != null) {
            choosePictureFragment.dismiss();
        }
        dismissLoading();
    }
}
