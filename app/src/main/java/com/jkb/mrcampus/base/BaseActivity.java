package com.jkb.mrcampus.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;


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

    protected FragmentManager fm;
    protected FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
