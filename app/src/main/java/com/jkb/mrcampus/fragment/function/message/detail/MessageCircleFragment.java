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

import com.jkb.core.contract.message.MessageCircleContract;
import com.jkb.core.control.messageState.MessageObservable;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.activity.MessageActivity;
import com.jkb.mrcampus.adapter.recycler.itemDecoration.DividerItemDecoration;
import com.jkb.mrcampus.adapter.recycler.message.circle.MessageCircleAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jkb.mrcampus.db.entity.Messages;

/**
 * 消息详情：圈子的View层
 * Created by JustKiddingBaby on 2016/10/10.
 */

public class MessageCircleFragment extends BaseFragment implements
        MessageCircleContract.View,
        View.OnClickListener, Observer, SwipeRefreshLayout.OnRefreshListener {

    public static MessageCircleFragment newInstance() {
        MessageCircleFragment INSTANCE = new MessageCircleFragment();
        return INSTANCE;
    }

    //data
    private MessageActivity messageActivity;
    private MessageCircleContract.Presenter mPresenter;

    //View
    private SwipeRefreshLayout refreshLayout;

    //列表
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageCircleAdapter messageCircleAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        messageActivity = (MessageActivity) mActivity;
        setRootView(R.layout.frg_message_circle);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        //view
        refreshLayout.setOnRefreshListener(this);
        messageCircleAdapter.setOnMessageCircleItemClickListener(onMessageCircleItemClickListener);
        //设置为消息读取的观察者
        MessageObservable.newInstance().addObserver(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        messageCircleAdapter = new MessageCircleAdapter(context, null);
        recyclerView.setAdapter(messageCircleAdapter);
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("圈子");
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.imc_srl);
        //列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.imc_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(context, LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.line), 1));//添加分割线
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
        recyclerView = null;
        linearLayoutManager = null;
        refreshLayout = null;
        messageCircleAdapter = null;
        MessageObservable.newInstance().deleteObserver(this);
    }

    @Override
    public void setCircleMessages(List<Messages> circleMessages) {
        refreshLayout.setRefreshing(false);
        messageCircleAdapter.circleMessages = circleMessages;
        messageCircleAdapter.notifyDataSetChanged();
    }

    @Override
    public void readCircleMessage(Messages message) {
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
    public void startPersonCenter(@NonNull int user_id) {
        messageActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void startCircleIndex(@NonNull int circle_id) {
        messageActivity.startCircleActivity(circle_id);
    }

    @Override
    public void setPresenter(MessageCircleContract.Presenter presenter) {
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
        showShortToash(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void update(Observable o, Object arg) {
        //拉取圈子数据
        pullCircleMessages();
    }

    /**
     * 拉取圈子数据
     */
    private void pullCircleMessages() {
        refreshLayout.setRefreshing(true);
        List<Messages> circleMessages = MessageObservable.newInstance().getAllCircleMessage();
        mPresenter.setDynamicMessages(circleMessages);
    }

    @Override
    public void onRefresh() {
        pullCircleMessages();
    }

    /**
     * 设置圈子消息的条目点击事件
     */
    private MessageCircleAdapter.OnMessageCircleItemClickListener onMessageCircleItemClickListener =
            new MessageCircleAdapter.OnMessageCircleItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mPresenter.onItemClick(position);
                }

                @Override
                public void onCircleItemClick(int position) {
                    mPresenter.onCircleItemClick(position);
                }
            };
}
