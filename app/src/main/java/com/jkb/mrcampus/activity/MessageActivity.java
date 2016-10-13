package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.function.message.detail.MessageCircleFragment;
import com.jkb.mrcampus.fragment.function.message.detail.MessageCommentFragment;
import com.jkb.mrcampus.fragment.function.message.detail.MessageFansFragment;
import com.jkb.mrcampus.fragment.function.message.detail.MessageLikeFragment;
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
    public static final int MESSAGE_TYPE_LIKE = 1001;
    public static final int MESSAGE_TYPE_COMMENT = 1002;
    public static final int MESSAGE_TYPE_FANS = 1003;
    public static final int MESSAGE_TYPE_SUBSCRIBE = 1004;
    public static final int MESSAGE_TYPE_CIRCLE = 1005;
    public static final int MESSAGE_TYPE_SYSTEM = 1006;

    //喜欢
    private MessageLikeFragment messageLikeFragment;

    //评论
    private MessageCommentFragment messageCommentFragment;

    //粉丝
    private MessageFansFragment messageFansFragment;

    //订阅
    private MessageSubscribeFragment messageSubscribeFragment;

    //圈子
    private MessageCircleFragment messageCircleFragment;

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
            case MESSAGE_TYPE_LIKE:
                showFragment = ClassUtils.getClassName(MessageLikeFragment.class);
                break;
            case MESSAGE_TYPE_COMMENT:
                showFragment = ClassUtils.getClassName(MessageCommentFragment.class);
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

            if (ClassUtils.isNameEquals(fragmentName, MessageLikeFragment.class)) {
                showMessageLike();
            } else if (ClassUtils.isNameEquals(fragmentName, MessageCommentFragment.class)) {
                showMessageComment();
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
        if (ClassUtils.isNameEquals(fragmentTAG, MessageLikeFragment.class)) {
            messageLikeFragment = (MessageLikeFragment) fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageCommentFragment.class)) {
            messageCommentFragment = (MessageCommentFragment) fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageFansFragment.class)) {
            messageFansFragment = (MessageFansFragment) fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageSubscribeFragment.class)) {
            messageSubscribeFragment = (MessageSubscribeFragment) fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageCircleFragment.class)) {
            messageCircleFragment = (MessageCircleFragment) fm.findFragmentByTag(fragmentTAG);
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageSystemFragment.class)) {
            messageSystemFragment = (MessageSystemFragment) fm.findFragmentByTag(fragmentTAG);
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, MessageLikeFragment.class)) {
            initMessageLike();
        } else if (ClassUtils.isNameEquals(fragmentTAG, MessageCommentFragment.class)) {
            initMessageComment();
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
    }

    /**
     * 初始化订阅的消息
     */
    private void initMessageSubscribe() {
        if (messageSubscribeFragment == null) {
            messageSubscribeFragment = MessageSubscribeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, messageSubscribeFragment, contentView);
        }
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
    private void initMessageComment() {
        if (messageCommentFragment == null) {
            messageCommentFragment = MessageCommentFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, messageCommentFragment, contentView);
        }
    }

    /**
     * 初始化喜欢的消息
     */
    private void initMessageLike() {
        if (messageLikeFragment == null) {
            messageLikeFragment = MessageLikeFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, messageLikeFragment, contentView);
        }
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
     * 显示评论的消息详情页面
     */
    private void showMessageComment() {
        ActivityUtils.showFragment(fm, messageCommentFragment);
    }

    /**
     * 显示喜欢的消息详情页面
     */
    private void showMessageLike() {
        ActivityUtils.showFragment(fm, messageLikeFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_CURRENT_SHOW_VIEW, showFragment);
    }
}
