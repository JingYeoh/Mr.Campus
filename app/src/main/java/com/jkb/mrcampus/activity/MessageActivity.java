package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.core.presenter.message.MessageCirclePresenter;
import com.jkb.core.presenter.message.MessageDynamicPresenter;
import com.jkb.core.presenter.message.MessageSubscribePresenter;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.function.message.detail.MessageCircleFragment;
import com.jkb.mrcampus.fragment.function.message.detail.MessageDynamicFragment;
import com.jkb.mrcampus.fragment.function.message.detail.MessageFansFragment;
import com.jkb.mrcampus.fragment.function.message.detail.MessageSubscribeFragment;
import com.jkb.mrcampus.fragment.function.message.detail.MessageSystemFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 消息的页面控制器
 * Created by JustKiddingBaby on 2016/10/10.
 */

public class MessageActivity extends BaseActivity {

    //data
    private int contentView = R.id.messageFrame;
    private String showFragment;
    private static final String SAVED_CURRENT_SHOW_VIEW = "saved.current.show.view";

    //常量
    public static final int MESSAGE_TYPE_DYNAMIC = 1002;
    public static final int MESSAGE_TYPE_FANS = 1003;
    public static final int MESSAGE_TYPE_SUBSCRIBE = 1004;
    public static final int MESSAGE_TYPE_CIRCLE = 1005;
    public static final int MESSAGE_TYPE_SYSTEM = 1006;

    //動態
    private MessageDynamicFragment messageDynamicFragment;
    private MessageDynamicPresenter messageDynamicPresenter;

    //粉丝
    private MessageFansFragment messageFansFragment;

    //订阅
    private MessageSubscribeFragment messageSubscribeFragment;
    private MessageSubscribePresenter messageSubscribePresenter;

    //圈子
    private MessageCircleFragment messageCircleFragment;
    private MessageCirclePresenter messageCirclePresenter;

    //系统
    private MessageSystemFragment messageSystemFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_message);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            int messageType = intent.getIntExtra(Config.INTENT_KEY_MESSAGE_TYPE, -1);
            filterMessageType(messageType);
        } else {
            restoreFragments();
            //恢复保存的栈数据
            showFragment = savedInstanceState.getString(SAVED_CURRENT_SHOW_VIEW);
        }
        if (StringUtils.isEmpty(showFragment)) {
            showShortToast("页面不存在");
            super.onBackPressed();
            return;
        }
        showFragment(showFragment);
    }

    /**
     * 筛选消息类型
     */
    private void filterMessageType(int messageType) {
        switch (messageType) {
            case MESSAGE_TYPE_DYNAMIC:
                showFragment = ClassUtils.getClassName(MessageDynamicFragment.class);
                break;
            case MESSAGE_TYPE_FANS:
                showFragment = ClassUtils.getClassName(MessageFansFragment.class);
                break;
            case MESSAGE_TYPE_SUBSCRIBE:
                showFragment = ClassUtils.getClassName(MessageSubscribeFragment.class);
                break;
            case MESSAGE_TYPE_CIRCLE:
                showFragment = ClassUtils.getClassName(MessageCircleFragment.class);
                break;
            case MESSAGE_TYPE_SYSTEM:
                showFragment = ClassUtils.getClassName(MessageSystemFragment.class);
                break;
            default:
                showFragment = null;
                break;
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

            if (ClassUtils.isNameEquals(fragmentName, MessageDynamicFragment.class)) {
                showMessageDynamic();
            } else if (ClassUtils.isNameEquals(fragmentName, MessageFansFragment.class)) {
                showMessageFans();
            } else if (ClassUtils.isNameEquals(fragmentName, MessageSubscribeFragment.class)) {
                showMessageSubscribe();
            } else if (ClassUtils.isNameEquals(fragmentName, MessageCircleFragment.class)) {
                showMessageCircle();
            } else if (ClassUtils.isNameEquals(fragmentName, MessageSystemFragment.class)) {
                showMessageSystem();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, MessageDynamicFragment.class)) {
            messageDynamicFragment = (MessageDynamicFragment) fm.findFragmentByTag(fragmentTAG);
            messageDynamicPresenter = new MessageDynamicPresenter(messageDynamicFragment);
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageFansFragment.class)) {
            messageFansFragment = (MessageFansFragment) fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageSubscribeFragment.class)) {
            messageSubscribeFragment = (MessageSubscribeFragment) fm.findFragmentByTag(fragmentTAG);
            messageSubscribePresenter = new MessageSubscribePresenter(messageSubscribeFragment);
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageCircleFragment.class)) {
            messageCircleFragment = (MessageCircleFragment) fm.findFragmentByTag(fragmentTAG);
            messageCirclePresenter = new MessageCirclePresenter(messageCircleFragment);
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageSystemFragment.class)) {
            messageSystemFragment = (MessageSystemFragment) fm.findFragmentByTag(fragmentTAG);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, MessageDynamicFragment.class)) {
            initMessageDynamic();
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageFansFragment.class)) {
            initMessageFans();
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageSubscribeFragment.class)) {
            initMessageSubscribe();
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageCircleFragment.class)) {
            initMessageCircle();
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageSystemFragment.class)) {
            initMessageSystem();
        }
    }

    /**
     * 初始化系统的消息
     */
    private void initMessageSystem() {
        if (messageSystemFragment == null) {
            messageSystemFragment = MessageSystemFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, messageSystemFragment, contentView);
        }
    }

    /**
     * 初始化圈子的消息
     */
    private void initMessageCircle() {
        if (messageCircleFragment == null) {
            messageCircleFragment = MessageCircleFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, messageCircleFragment, contentView);
        }
        messageCirclePresenter = new MessageCirclePresenter(messageCircleFragment);
    }

    /**
     * 初始化订阅的消息
     */
    private void initMessageSubscribe() {
        if (messageSubscribeFragment == null) {
            messageSubscribeFragment = MessageSubscribeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, messageSubscribeFragment, contentView);
        }
        messageSubscribePresenter = new MessageSubscribePresenter(messageSubscribeFragment);
    }

    /**
     * 初始化粉丝的消息
     */
    private void initMessageFans() {
        if (messageFansFragment == null) {
            messageFansFragment = MessageFansFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, messageFansFragment, contentView);
        }
    }

    /**
     * 初始化评论的消息
     */
    private void initMessageDynamic() {
        if (messageDynamicFragment == null) {
            messageDynamicFragment = MessageDynamicFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, messageDynamicFragment, contentView);
        }
        messageDynamicPresenter = new MessageDynamicPresenter(messageDynamicFragment);
    }


    /**
     * 显示系统的消息详情页面
     */
    private void showMessageSystem() {
        ActivityUtils.showFragment(fm, messageSystemFragment);
    }

    /**
     * 显示圈子的消息详情页面
     */
    private void showMessageCircle() {
        ActivityUtils.showFragment(fm, messageCircleFragment);
    }

    /**
     * 显示订阅的消息详情页面
     */
    private void showMessageSubscribe() {
        ActivityUtils.showFragment(fm, messageSubscribeFragment);
    }

    /**
     * 显示粉丝的消息详情页面
     */
    private void showMessageFans() {
        ActivityUtils.showFragment(fm, messageFansFragment);
    }

    /**
     * 显示动态的消息详情页面
     */
    private void showMessageDynamic() {
        ActivityUtils.showFragment(fm, messageDynamicFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_CURRENT_SHOW_VIEW, showFragment);
    }
}
