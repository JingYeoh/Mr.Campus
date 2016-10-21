package com.jkb.mrcampus.fragment.im.conversation;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.contract.im.conversation.ConversationContract;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.ConversationActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * 会话页面的Fragment
 * Created by JustKiddingBaby on 2016/10/20.
 */

public class ConversationFragment extends BaseFragment implements
        ConversationContract.View, View.OnClickListener {

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

    public ConversationFragment() {
    }

    private static ConversationFragment INSTANCE = null;


    public static ConversationFragment newInstance(
            String mTargetId, String mTargetIds, Conversation.ConversationType mConversationType) {
        if (INSTANCE == null || StringUtils.isEmpty(mTargetId) || StringUtils.isEmpty(mTargetIds)) {
            INSTANCE = new ConversationFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Config.INTENT_KEY_TARGETID, mTargetId);
            bundle.putString(Config.INTENT_KEY_TARGETIDS, mTargetIds);
            bundle.putSerializable(Config.INTENT_KEY_CONVERSATION_TYPE, mConversationType);
            INSTANCE.setArguments(bundle);
        }
        return INSTANCE;
    }

    //data
    private ConversationActivity conversationActivity;
    private ConversationContract.Presenter mPresenter;

    //聊天
    private io.rong.imkit.fragment.ConversationFragment conversationFragment;
    private int conversationContentView = R.id.fc_conversationContent;
    private Uri uri;
    private FragmentManager fm;

    //用户数据
    private List<UserInfo> userInfos;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        conversationActivity = (ConversationActivity) mActivity;
        setRootView(R.layout.frg_conversation);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getChildFragmentManager();
        if (savedInstanceState != null) {
            //恢复数据
            mTargetId = savedInstanceState.getString(SAVED_TARGETID);
            mTargetIds = savedInstanceState.getString(SAVED_TARGETIDS);
            mConversationType = (Conversation.ConversationType)
                    savedInstanceState.getSerializable(SAVED_CONVERSATIONTYPE);
            restoreConversation();
        } else {
            Bundle arguments = getArguments();
            mTargetId = arguments.getString(Config.INTENT_KEY_TARGETID);
            mTargetIds = arguments.getString(Config.INTENT_KEY_TARGETIDS);
            mConversationType = (Conversation.ConversationType)
                    arguments.getSerializable(Config.INTENT_KEY_CONVERSATION_TYPE);
            initConversation();
        }
        initConversationData();
        userInfos = new ArrayList<>();
        //根据会话类型进入相关页面
        bindRongUserInfo();
        showConversation();
    }

    /**
     * 初始化聊天页面的数据
     */
    private void initConversationData() {
        uri = Uri.parse("rong://" + mActivity.getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId).build();
    }

    /**
     * 初始化聊天页面
     */
    private void initConversation() {
        conversationFragment = new io.rong.imkit.fragment.ConversationFragment();
        conversationFragment.setUri(uri);
        ActivityUtils.addFragmentToActivity(fm, conversationFragment, conversationContentView);
    }

    /**
     * 恢复聊天会话页面
     */
    private void restoreConversation() {
        if (conversationFragment == null) {
            conversationFragment = (io.rong.imkit.fragment.ConversationFragment)
                    fm.findFragmentByTag(ClassUtils.getClassName(
                            io.rong.imkit.fragment.ConversationFragment.class));
        }
    }

    /**
     * 显示聊天页面
     */
    private void showConversation() {
        ActivityUtils.showFragment(fm, conversationFragment);
    }

    /**
     * 绑定融云的用户数据
     */
    private void bindRongUserInfo() {
        Log.d(TAG, "聊天类型是" + mConversationType);
        if (mConversationType == Conversation.ConversationType.PRIVATE) {
//            LogUtils.d(TAG, ",TargetId=" + mTargetId);
            mPresenter.setPrivateConversationUserId(Integer.parseInt(mTargetId));
            //设置融云的头像等数据
            RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {

                @Override
                public io.rong.imlib.model.UserInfo getUserInfo(String userId) {
                    Log.d(TAG, "我要得到用户" + userId + "的数据");
                    //根据 userId 去你的用户系统里查询对应的用户信息返回给融云 SDK。
                    return findRongUserById(userId);
                }
            }, true);
        } else if (mConversationType == Conversation.ConversationType.GROUP) {
            //设置群聊的融云的头像等数据
        } else if (mConversationType == Conversation.ConversationType.DISCUSSION) {

        } else if (mConversationType == Conversation.ConversationType.NONE) {
        }
    }

    /**
     * 得到融云的用户数据
     */
    private io.rong.imlib.model.UserInfo findRongUserById(String userId) {
        for (UserInfo userInfo : userInfos) {
            String user_id = String.valueOf(userInfo.getId());
            if (userId.equals(user_id)) {
                return new io.rong.imlib.model.UserInfo(
                        user_id, userInfo.getNickName(), Uri.parse(userInfo.getAvatar())
                );
            }
        }
        int user_id = Integer.parseInt(userId);
        mPresenter.reqUserInfoData(user_id);
        return null;
    }

    @Override
    protected void initView() {

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVED_TARGETID, mTargetId);
        outState.putString(SAVED_TARGETIDS, mTargetIds);
        outState.putSerializable(SAVED_CONVERSATIONTYPE, mConversationType);
    }

    @Override
    public void setTitleName(String titleName) {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText(titleName);
    }

    @Override
    public void setUserInfo(List<UserInfo> userInfo) {
        if (userInfo == null || userInfo.size() == 0) {
            return;
        }
        this.userInfos = userInfo;
        for (UserInfo info : userInfo) {
            io.rong.imlib.model.UserInfo rongUserInfo = new io.rong.imlib.model.UserInfo(
                    info.getId() + "",
                    info.getNickName(),
                    Uri.parse(info.getAvatar())
            );
            RongIM.getInstance().refreshUserInfoCache(rongUserInfo);
        }
    }

    @Override
    public void setPresenter(ConversationContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        conversationActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        conversationActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        conversationActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                conversationActivity.onBackPressed();
                break;
        }
    }
}
