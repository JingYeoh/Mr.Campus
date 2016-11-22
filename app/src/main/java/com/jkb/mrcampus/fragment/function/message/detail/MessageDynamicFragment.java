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

import com.jkb.core.contract.message.MessageDynamicContract;
import com.jkb.core.control.messageState.MessageObservable;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.activity.MessageActivity;
import com.jkb.mrcampus.adapter.recycler.itemDecoration.DividerItemDecoration;
import com.jkb.mrcampus.adapter.recycler.message.dynamic.MessageDynamicAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jkb.mrcampus.db.entity.Messages;

/**
 * 消息详情：评论的View层
 * Created by JustKiddingBaby on 2016/10/10.
 */

public class MessageDynamicFragment extends BaseFragment implements
        MessageDynamicContract.View,
        View.OnClickListener, Observer, SwipeRefreshLayout.OnRefreshListener {

    public static MessageDynamicFragment newInstance() {
        return new MessageDynamicFragment();
    }

    //data
    private MessageActivity messageActivity;
    private MessageDynamicContract.Presenter mPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;

    //列表页面
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageDynamicAdapter messageDynamicAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        messageActivity = (MessageActivity) mActivity;
        setRootView(R.layout.frg_message_dynamic);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        //设置为消息读取的观察者
        MessageObservable.newInstance().addObserver(this);
        //设置消息条目的点击监听事件
        messageDynamicAdapter.setOnMessageDynamicItemClickListener(messageDynamicItemClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        messageDynamicAdapter = new MessageDynamicAdapter(context, null);
        recyclerView.setAdapter(messageDynamicAdapter);
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("动态");

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fmd_srl);
        //初始化列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fmd_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(context,
                        LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.gravy_20), 1));//添加分割线
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
        refreshLayout = null;
        recyclerView = null;
        linearLayoutManager = null;
        messageDynamicAdapter = null;
        MessageObservable.newInstance().deleteObserver(this);
    }

    @Override
    public void setDynamicMessages(List<Messages> dynamicMessages) {
        refreshLayout.setRefreshing(false);
        messageDynamicAdapter.dynamicMessages = dynamicMessages;
        messageDynamicAdapter.notifyDataSetChanged();
    }

    @Override
    public void readDynamicMessage(int message_id) {
        MessageObservable.newInstance().readMessage(message_id);
    }

    @Override
    public void readDynamicMessage(Messages message) {
        MessageObservable.newInstance().readMessage(message);
    }

    @Override
    public void startDynamicArticle(@NonNull int dynamic_id) {
        messageActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_ARTICLE);
    }

    @Override
    public void startDynamicNormal(@NonNull int dynamic_id) {
        messageActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_NORMAL);
    }

    @Override
    public void startDynamicTopic(@NonNull int dynamic_id) {
        messageActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_TOPIC);
    }

    @Override
    public void setPresenter(MessageDynamicContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        messageActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        messageActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        messageActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void update(Observable o, Object arg) {
        //抽取数据
        pullDynamicMessageData();
    }

    /**
     * 拉取动态消息数据
     */
    private void pullDynamicMessageData() {
        List<Messages> allDynamicMessage = MessageObservable.newInstance().getAllDynamicMessage();
        mPresenter.setDynamicMessages(allDynamicMessage);
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        pullDynamicMessageData();
    }

    /**
     * 动态消息的子条目点击监听事件
     */
    private MessageDynamicAdapter.OnMessageDynamicItemClickListener messageDynamicItemClickListener =
            new MessageDynamicAdapter.OnMessageDynamicItemClickListener() {
                @Override
                public void onMessageItemClick(int position) {
                    mPresenter.onDynamicMessageItemClick(position);
                }
            };
}
