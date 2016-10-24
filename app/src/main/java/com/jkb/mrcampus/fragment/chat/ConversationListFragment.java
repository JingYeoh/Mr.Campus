package com.jkb.mrcampus.fragment.chat;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.core.contract.im.conversationList.ConversationListContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.UserState;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.activity.callback.RongIMConnectCallBack;
import com.jkb.mrcampus.adapter.conversation.MyConversationListAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.utils.ClassUtils;

import io.rong.imkit.RongContext;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * 聊天列表的Fragment类
 * Created by JustKiddingBaby on 2016/10/21.
 */

public class ConversationListFragment extends BaseFragment implements
        ConversationListContract.View, SwipeRefreshLayout.OnRefreshListener {

    private int contentView = R.id.fc_conversationListContent;

    private static ConversationListFragment INSTANCE;

    public static ConversationListFragment newInstance() {
        if (INSTANCE == null) {
            Bundle args = new Bundle();
            INSTANCE = new ConversationListFragment();
            INSTANCE.setArguments(args);
        }
        return INSTANCE;
    }

    //data
    private MainActivity mainActivity;
    private FragmentManager fm;

    //聊天列表
    private io.rong.imkit.fragment.ConversationListFragment conversationListFragment;
    private MyConversationListAdapter myConversationListAdapter;
    private Uri uri;

    //view
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) mActivity;
        setRootView(R.layout.frg_conversationlist);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListener() {
        //设置是否退出登录的状态监听
        LoginContext.getInstance().setLoginStatusChangedListener(loginStatusChangedListener);
        mainActivity.setRongIMConnectCallBack(rongIMConnectCallBack);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getChildFragmentManager();
        initConversationListData();
        if (savedInstanceState == null) {
            initConversationList();
        } else {
            restoreConversationList();
        }
        myConversationListAdapter = new MyConversationListAdapter(RongContext.getInstance());
        conversationListFragment.setAdapter(myConversationListAdapter);
        showConversationList();
    }

    /**
     * 恢复会话列表
     */
    private void restoreConversationList() {
        LogUtils.d(TAG, "restoreConversationList");
        conversationListFragment = (io.rong.imkit.fragment.ConversationListFragment)
                fm.findFragmentByTag(ClassUtils.getClassName(
                        io.rong.imkit.fragment.ConversationListFragment.class));
    }

    /**
     * 初始化有关的fragment
     */
    private void initConversationList() {
        LogUtils.d(TAG, "initConversationList");
        conversationListFragment = new io.rong.imkit.fragment.ConversationListFragment();
        conversationListFragment.setUri(uri);
        ActivityUtils.addFragmentToActivity(fm, conversationListFragment, contentView);
    }

    /**
     * 显示聊天列表页面
     */
    private void showConversationList() {
        LogUtils.d(TAG, "showConversationList");
        ActivityUtils.showFragment(fm, conversationListFragment);
    }

    /**
     * 初始化聊天列表相关
     */

    private void initConversationListData() {
        uri = Uri.parse("rong://" + context.getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")//设置群组会话聚合显示
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")//设置讨论组会话非聚合显示
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")//设置系统会话非聚合显示
                .build();
    }

    @Override
    protected void initView() {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fc_srl);
    }

    /**
     * 登录状态的监听
     */
    private UserState.LoginStatusChangedListener loginStatusChangedListener =
            new UserState.LoginStatusChangedListener() {
                @Override
                public void onLogin() {
                    refreshConversationList();
                }

                @Override
                public void onLogout() {

                }
            };
    /**
     * 连接聊天服务器的回调
     */
    private RongIMConnectCallBack rongIMConnectCallBack = new RongIMConnectCallBack() {
        @Override
        public void onTokenIncorrect() {
//            rootView.findViewById(contentView).setVisibility(View.GONE);
        }

        @Override
        public void onSuccess(int user_id) {
//            rootView.findViewById(contentView).setVisibility(View.VISIBLE);
            myConversationListAdapter =
                    new MyConversationListAdapter(RongContext.getInstance());
            conversationListFragment.setAdapter(myConversationListAdapter);
            conversationListFragment.setUri(uri);
        }

        @Override
        public void onError(RongIMClient.ErrorCode errorCode) {
//            rootView.findViewById(contentView).setVisibility(View.GONE);
        }
    };

    @Override
    public void refreshConversationList() {
        myConversationListAdapter =
                new MyConversationListAdapter(RongContext.getInstance());
        conversationListFragment.setAdapter(myConversationListAdapter);
        conversationListFragment.setUri(uri);
        hideRefreshView();
    }

    @Override
    public void showRefreshView() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshView() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void setPresenter(ConversationListContract.Presenter presenter) {

    }

    @Override
    public void showLoading(String value) {
        mainActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        mainActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        mainActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity = null;
    }

    @Override
    public void onRefresh() {
        showRefreshView();
        refreshConversationList();
    }
}
