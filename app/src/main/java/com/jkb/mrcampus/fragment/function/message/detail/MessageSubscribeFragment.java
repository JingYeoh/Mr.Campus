package com.jkb.mrcampus.fragment.function.message.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.contract.message.MessageSubscribeContract;
import com.jkb.core.control.messageState.MessageObservable;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MessageActivity;
import com.jkb.mrcampus.adapter.recycler.message.subscribe.MessageSubscribeAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jkb.mrcampus.db.entity.Messages;

/**
 * 消息详情：订阅的View层
 * Created by JustKiddingBaby on 2016/10/10.
 */

public class MessageSubscribeFragment extends BaseFragment implements
        MessageSubscribeContract.View,
        View.OnClickListener, Observer, SwipeRefreshLayout.OnRefreshListener {


    public MessageSubscribeFragment() {
    }

    private static MessageSubscribeFragment INSTANCE = null;

    public static MessageSubscribeFragment newInstance() {
        INSTANCE = new MessageSubscribeFragment();
        return INSTANCE;
    }

    //data
    private MessageActivity messageActivity;
    private MessageSubscribeContract.Presenter mPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;

    //列表
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageSubscribeAdapter messageSubscribeAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        messageActivity = (MessageActivity) mActivity;
        setRootView(R.layout.frg_message_subscribe);
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
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        messageSubscribeAdapter.setOnSubscribeMessageItemClickListener(
                onSubscribeMessageItemClickListener);
        //设置为消息读取的观察者
        MessageObservable.newInstance().addObserver(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        messageSubscribeAdapter = new MessageSubscribeAdapter(context, null);
        recyclerView.setAdapter(messageSubscribeAdapter);
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("订阅");
        //其他
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fms_srl);
        //列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fms_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                messageActivity.onBackPressed();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        messageActivity = null;
        MessageObservable.newInstance().deleteObserver(this);
        recyclerView = null;
        refreshLayout = null;
        messageSubscribeAdapter=null;
        linearLayoutManager=null;
        mPresenter = null;
    }

    @Override
    public void readAllSubscribeMessage() {
        MessageObservable.newInstance().readAllSubscribeMessage();
    }

    @Override
    public void setSubscribeMessages(List<Messages> subscribeMessages) {
        refreshLayout.setRefreshing(false);
        messageSubscribeAdapter.messages = subscribeMessages;
        messageSubscribeAdapter.notifyDataSetChanged();
    }

    @Override
    public void startPersonCenter(@NonNull int user_id) {
        messageActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void startCircleIndex(@NonNull int circle_id) {
        messageActivity.startCircleActivity(circle_id);
    }

    @Override
    public void setPresenter(MessageSubscribeContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        messageActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        messageActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        showShortToash(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void update(Observable o, Object arg) {
        //读取消息
        pullSubscribeMessage();
    }

    /**
     * 拉取订阅消息
     */
    private void pullSubscribeMessage() {
        List<Messages> allSubscribeMessage = MessageObservable.newInstance().getAllSubscribeMessage();
        mPresenter.setSubscribeMessages(allSubscribeMessage);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        pullSubscribeMessage();
    }

    /**
     * 设置订阅圈子的条目点击的监听器
     */
    private MessageSubscribeAdapter.OnSubscribeMessageItemClickListener
            onSubscribeMessageItemClickListener =
            new MessageSubscribeAdapter.OnSubscribeMessageItemClickListener() {
                @Override
                public void onUserItemClick(int position) {
                    mPresenter.onUserItemClick(position);
                }

                @Override
                public void onCircleItemClick(int position) {
                    mPresenter.onCircleItemClick(position);
                }
            };
}
