package com.jkb.mrcampus.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.im.conversation.ConversationPresenter;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.entering.EnteringPersonMessageFragment;
import com.jkb.mrcampus.fragment.entering.IdentifyFragment;
import com.jkb.mrcampus.fragment.entering.LoginFragment;
import com.jkb.mrcampus.fragment.entering.MrCampusAgreementFragment;
import com.jkb.mrcampus.fragment.entering.ResetPasswordFragment;
import com.jkb.mrcampus.fragment.im.conversation.ConversationFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.Locale;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * 聊天的页面控制器
 * Created by JustKiddingBaby on 2016/10/19.
 */

public class ConversationActivity extends BaseActivity {

    /**
     * 目标 Id
     */
    private String mTargetId;

    /**
     * 刚刚创建完讨论组后获得讨论组的id 为targetIds，需要根据 为targetIds 获取 targetId
     */
    private String mTargetIds;

    /**
     * 会话类型
     */
    private Conversation.ConversationType mConversationType;

    //常量
    private static final String SAVED_TARGETID = "saved_target_id";
    private static final String SAVED_TARGETIDS = "saved_targetIds";
    private static final String SAVED_CONVERSATIONTYPE = "saved_conversationType";


    //data
    private int contentView = R.id.conversation_content;

    //会话页面
    private ConversationFragment conversationFragment;
    private ConversationPresenter conversationPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_conversation);
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
            getIntentDate(intent);
        } else {
            //从保存的数据中取数据
            mTargetId = savedInstanceState.getString(SAVED_TARGETID);
            mTargetIds = savedInstanceState.getString(SAVED_TARGETIDS);
            mConversationType = (Conversation.ConversationType)
                    savedInstanceState.getSerializable(SAVED_CONVERSATIONTYPE);
        }
        //加载自己的Fragment
        showFragment(ClassUtils.getClassName(ConversationFragment.class));
    }

    /**
     * 展示如何从 Intent 中得到 融云会话页面传递的 Uri
     */
    private void getIntentDate(Intent intent) {
        mTargetId = intent.getData().getQueryParameter("targetId");
        mTargetIds = intent.getData().getQueryParameter("targetIds");
        //intent.getData().getLastPathSegment();//获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(
                intent.getData().getLastPathSegment().toUpperCase(Locale.getDefault()));
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

            if (ClassUtils.isNameEquals(fragmentName, ConversationFragment.class)) {
                showConversation();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, ConversationFragment.class)) {
            conversationFragment = (ConversationFragment)
                    fm.findFragmentByTag(ClassUtils.getClassName(ConversationFragment.class));
            conversationPresenter = new ConversationPresenter(conversationFragment,
                    Injection.provideConversationRepertory(getApplicationContext()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();
        if (ClassUtils.isNameEquals(fragmentTAG, ConversationFragment.class)) {
            initConversation();
        }
    }

    /**
     * 初始化会话页面
     */
    private void initConversation() {
        if (conversationFragment == null) {
            conversationFragment = ConversationFragment.newInstance(
                    mTargetId, mTargetIds, mConversationType);
            ActivityUtils.addFragmentToActivity(fm, conversationFragment, contentView);
        }
        if (conversationPresenter == null) {
            conversationPresenter = new ConversationPresenter(conversationFragment,
                    Injection.provideConversationRepertory(getApplicationContext()));
        }
    }

    /**
     * 显示会话页面
     */
    private void showConversation() {
        ActivityUtils.showFragment(fm, conversationFragment);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_TARGETID, mTargetId);
        outState.putString(SAVED_TARGETIDS, mTargetIds);
        outState.putSerializable(SAVED_CONVERSATIONTYPE, mConversationType);
    }

    @Override
    public void onBackPressed() {
        if (mConversationType == Conversation.ConversationType.CHATROOM) {
            RongIM.getInstance().quitChatRoom(mTargetId, new RongIMClient.OperationCallback() {
                @Override
                public void onSuccess() {
                    showShortToast("退出聊天室成功");
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtils.w(TAG, "退出聊天室-----errorCode-->" + errorCode.getValue());
                }
            });
        }
        super.onBackPressed();
    }
}
