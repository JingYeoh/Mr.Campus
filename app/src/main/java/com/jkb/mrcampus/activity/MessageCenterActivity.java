package com.jkb.mrcampus.activity;

import android.os.Bundle;
import android.util.Log;

import com.jkb.core.presenter.message.MessageCenterPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.function.message.MessageCenterFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 消息中心的页面控制器
 * Created by JustKiddingBaby on 2016/10/10.
 */

public class MessageCenterActivity extends BaseActivity {

    //data
    private int contentView = R.id.messageCenterFrame;
    private String showFragment;
    private static final String SAVED_CURRENT_SHOW_VIEW = "saved.current.show.view";

    //消息中心
    private MessageCenterFragment messageCenterFragment;
    private MessageCenterPresenter messageCenterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_message_center);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        //第一次进入时调用显示首页视图
        if (!savedInstanceStateValued) {
            showFragment = MessageCenterFragment.class.getName();
        } else {
            restoreFragments();
            //恢复保存的栈数据
            showFragment = savedInstanceState.getString(SAVED_CURRENT_SHOW_VIEW);
        }
        showFragment(showFragment);
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

            if (ClassUtils.isNameEquals(fragmentName, MessageCenterFragment.class)) {
                showMessageCenter();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, MessageCenterFragment.class)) {
            messageCenterFragment = (MessageCenterFragment) fm.findFragmentByTag(fragmentTAG);
            messageCenterPresenter = new MessageCenterPresenter(messageCenterFragment);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();

        if (ClassUtils.isNameEquals(fragmentTAG, MessageCenterFragment.class)) {
            initMessageCenter();
        }
    }

    /**
     * 初始化消息中心
     */
    private void initMessageCenter() {
        if (messageCenterFragment == null) {
            messageCenterFragment = MessageCenterFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, messageCenterFragment, contentView);
        }
        if (messageCenterPresenter == null) {
            messageCenterPresenter = new MessageCenterPresenter(messageCenterFragment);
        }
    }

    /**
     * 展示消息中心
     */
    private void showMessageCenter() {
        ActivityUtils.showFragment(fm, messageCenterFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_CURRENT_SHOW_VIEW, showFragment);
    }
}
