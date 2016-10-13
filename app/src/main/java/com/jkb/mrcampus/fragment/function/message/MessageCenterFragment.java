package com.jkb.mrcampus.fragment.function.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.contract.message.MessageCenterContract;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MessageActivity;
import com.jkb.mrcampus.activity.MessageCenterActivity;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 消息中心的View层
 * Created by JustKiddingBaby on 2016/10/10.
 */

public class MessageCenterFragment extends BaseFragment implements
        MessageCenterContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener {

    public MessageCenterFragment() {
    }

    private static MessageCenterFragment INSTANCE = null;

    public static MessageCenterFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MessageCenterFragment();
        }
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
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
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
        rootView.findViewById(R.id.fmsgc_content_like).setOnClickListener(this);
        rootView.findViewById(R.id.fmsgc_content_comment).setOnClickListener(this);
        rootView.findViewById(R.id.fmsgc_content_fans).setOnClickListener(this);
        rootView.findViewById(R.id.fmsgc_content_subscribe).setOnClickListener(this);
        rootView.findViewById(R.id.fmsgc_content_circle).setOnClickListener(this);
        rootView.findViewById(R.id.fmsgc_content_system).setOnClickListener(this);
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
        mPresenter.onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fmsgc_content_like:
                showMessageLikeView();
                break;
            case R.id.fmsgc_content_comment:
                showMessageCommentView();
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
    public void setLikeMessageCount(int unReadCount, int allCount) {
        ((TextView) rootView.findViewById(R.id.fmsgc_tv_allCount_like)).setText(allCount);
        if (unReadCount == 0) {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_like).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_like).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fmsgc_tv_unReadCount_like)).setText(unReadCount);
        }
    }

    @Override
    public void setCommentMessageCount(int unReadCount, int allCount) {
        ((TextView) rootView.findViewById(R.id.fmsgc_tv_allCount_comment)).setText(allCount);
        if (unReadCount == 0) {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_comment).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_comment).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fmsgc_tv_unReadCount_comment)).setText(unReadCount);
        }
    }

    @Override
    public void setFansMessageCount(int unReadCount, int allCount) {
        ((TextView) rootView.findViewById(R.id.fmsgc_tv_allCount_fans)).setText(allCount);
        if (unReadCount == 0) {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_fans).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_fans).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fmsgc_tv_unReadCount_fans)).setText(unReadCount);
        }
    }

    @Override
    public void setSubscribeMessageCount(int unReadCount, int allCount) {
        ((TextView) rootView.findViewById(R.id.fmsgc_tv_allCount_subscribe)).setText(allCount);
        if (unReadCount == 0) {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_subscribe).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_subscribe).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fmsgc_tv_unReadCount_subscribe)).setText(unReadCount);
        }
    }

    @Override
    public void setCircleMessageCount(int unReadCount, int allCount) {
        ((TextView) rootView.findViewById(R.id.fmsgc_tv_allCount_circle)).setText(allCount);
        if (unReadCount == 0) {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_circle).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_circle).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fmsgc_tv_unReadCount_circle)).setText(unReadCount);
        }
    }

    @Override
    public void setSystemMessageCount(int unReadCount, int allCount) {
        ((TextView) rootView.findViewById(R.id.fmsgc_tv_allCount_system)).setText(allCount);
        if (unReadCount == 0) {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_system).setVisibility(View.GONE);
        } else {
            rootView.findViewById(R.id.fmsgc_tv_unReadCount_system).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fmsgc_tv_unReadCount_system)).setText(unReadCount);
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
    public void showMessageLikeView() {
        messageCenterActivity.startMessageActivity(MessageActivity.MESSAGE_TYPE_LIKE);
    }

    @Override
    public void showMessageCommentView() {
        messageCenterActivity.startMessageActivity(MessageActivity.MESSAGE_TYPE_COMMENT);
    }

    @Override
    public void showMessageFansView() {
        messageCenterActivity.startMessageActivity(MessageActivity.MESSAGE_TYPE_FANS);
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
}
