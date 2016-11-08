package com.jkb.mrcampus.fragment.function.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.contract.message.MessageCenterContract;
import com.jkb.core.control.messageState.MessageObservable;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MessageActivity;
import com.jkb.mrcampus.activity.MessageCenterActivity;
import com.jkb.mrcampus.activity.UsersListActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.usersList.FansFragment;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.Observable;
import java.util.Observer;

/**
 * 消息中心的View层
 * Created by JustKiddingBaby on 2016/10/10.
 */

public class MessageCenterFragment extends BaseFragment implements
        MessageCenterContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener, Observer {

    public MessageCenterFragment() {
    }

    private static MessageCenterFragment INSTANCE = null;

    public static MessageCenterFragment newInstance() {
        INSTANCE = new MessageCenterFragment();
        return INSTANCE;
    }

    //data
    private MessageCenterActivity messageCenterActivity;
    private MessageCenterContract.Presenter mPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        messageCenterActivity = (MessageCenterActivity) mActivity;
        setRootView(R.layout.frg_message_center);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        //初始化刷新
        refreshLayout.setOnRefreshListener(this);
        //各个点击事件
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.fmsgc_content_dynamic).setOnClickListener(this);
        rootView.findViewById(R.id.fmsgc_content_fans).setOnClickListener(this);
        rootView.findViewById(R.id.fmsgc_content_subscribe).setOnClickListener(this);
        rootView.findViewById(R.id.fmsgc_content_circle).setOnClickListener(this);
        rootView.findViewById(R.id.fmsgc_content_system).setOnClickListener(this);
        //添加为消息的观察者
        MessageObservable.newInstance().addObserver(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("消息中心");
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fmsgc_srl);
    }

    @Override
    public void onRefresh() {
//        mPresenter.onRefresh();
        initMessages();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fmsgc_content_dynamic:
                showMessageDynamicView();
                break;
            case R.id.fmsgc_content_fans:
                showMessageFansView();
                break;
            case R.id.fmsgc_content_subscribe:
                showMessageSubscribeView();
                break;
            case R.id.fmsgc_content_circle:
                showMessageCircleView();
                break;
            case R.id.fmsgc_content_system:
                showMessageSystemView();
                break;
            case R.id.ts4_ib_left:
                messageCenterActivity.onBackPressed();
                break;
        }
    }

    @Override
    public void setDynamicMessageCount(int unReadCount, int allCount) {
        ((TextView) rootView.findViewById(R.id.fmsgc_tv_allCount_dynamic)).setText(allCount + "");
        if (unReadCount == 0) {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_dynamic).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_dynamic).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fmsgc_tv_unReadCount_dynamic))
                    .setText(unReadCount + "");
        }
    }

    @Override
    public void setFansMessageCount(int unReadCount, int allCount) {
        ((TextView) rootView.findViewById(R.id.fmsgc_tv_allCount_fans))
                .setText(allCount + "");
        if (unReadCount == 0) {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_fans).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_fans).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fmsgc_tv_unReadCount_fans))
                    .setText(unReadCount + "");
        }
    }

    @Override
    public void setSubscribeMessageCount(int unReadCount, int allCount) {
        ((TextView) rootView.findViewById(R.id.fmsgc_tv_allCount_subscribe))
                .setText(allCount + "");
        if (unReadCount == 0) {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_subscribe).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_subscribe).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fmsgc_tv_unReadCount_subscribe))
                    .setText(unReadCount + "");
        }
    }

    @Override
    public void setCircleMessageCount(int unReadCount, int allCount) {
        ((TextView) rootView.findViewById(R.id.fmsgc_tv_allCount_circle))
                .setText(allCount + "");
        if (unReadCount == 0) {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_circle).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_circle).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fmsgc_tv_unReadCount_circle))
                    .setText(unReadCount + "");
        }
    }

    @Override
    public void setSystemMessageCount(int unReadCount, int allCount) {
        ((TextView) rootView.findViewById(R.id.fmsgc_tv_allCount_system))
                .setText(allCount + "");
        if (unReadCount == 0) {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_system).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_system).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fmsgc_tv_unReadCount_system))
                    .setText(unReadCount + "");
        }
    }

    @Override
    public void showRefreshingView() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshingView() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessageDynamicView() {
        messageCenterActivity.startMessageActivity(MessageActivity.MESSAGE_TYPE_DYNAMIC);
    }

    @Override
    public void showMessageFansView() {
//        messageCenterActivity.startMessageActivity(MessageActivity.MESSAGE_TYPE_FANS);
        if (LoginContext.getInstance().isLogined()) {
            String action = ClassUtils.getClassName(FansFragment.class);
            int user_id = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
            messageCenterActivity.startUsersListActivity(user_id, action);
        } else {
            showReqResult("请先登录再进行操作");
        }
    }

    @Override
    public void showMessageSubscribeView() {
        messageCenterActivity.startMessageActivity(MessageActivity.MESSAGE_TYPE_SUBSCRIBE);
    }

    @Override
    public void showMessageCircleView() {
        messageCenterActivity.startMessageActivity(MessageActivity.MESSAGE_TYPE_CIRCLE);
    }

    @Override
    public void showMessageSystemView() {
        messageCenterActivity.startMessageActivity(MessageActivity.MESSAGE_TYPE_SYSTEM);
    }

    @Override
    public void setPresenter(MessageCenterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        if (!isHidden())
            messageCenterActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        messageCenterActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        messageCenterActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        messageCenterActivity = null;
        MessageObservable.newInstance().deleteObserver(this);
    }

    @Override
    public void update(Observable o, Object arg) {
        //得到各种数据
        initMessages();
    }

    /**
     * 初始化消息
     */
    @Override
    public void initMessages() {
        hideRefreshingView();
        initDynamicMessage();
        initSubscribeMessage();
        initFansMessage();
    }

    /**
     * 初始化动态消息
     */
    private void initDynamicMessage() {
        int unReadCount = MessageObservable.newInstance().getAllUnReadDynamicMessageCount();
        int dynamicCount = MessageObservable.newInstance().getAllDynamicMessageCount();
        setDynamicMessageCount(unReadCount, dynamicCount);
    }

    /**
     * 初始化订阅的消息
     */
    private void initSubscribeMessage() {
        int unReadCount = MessageObservable.newInstance().getAllUnReadSubscribeMessageCount();
        int allCount = MessageObservable.newInstance().getAllSubscribeMessageCount();
        setSubscribeMessageCount(unReadCount, allCount);
    }

    /**
     * 初始化粉丝消息
     */
    private void initFansMessage() {
        int unReadCount = MessageObservable.newInstance().getAllUnReadFansMessageCount();
        int AllCount = 0;
        if (LoginContext.getInstance().isLogined()) {
            AllCount = UserInfoSingleton.getInstance().getUsers().getFansCount();
        }
        setFansMessageCount(unReadCount, AllCount);
    }

}
