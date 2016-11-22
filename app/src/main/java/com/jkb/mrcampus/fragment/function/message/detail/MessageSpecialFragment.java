package com.jkb.mrcampus.fragment.function.message.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.contract.message.MessageSubjectContract;
import com.jkb.core.control.messageState.MessageObservable;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MessageActivity;
import com.jkb.mrcampus.adapter.recycler.itemDecoration.DividerItemDecoration;
import com.jkb.mrcampus.adapter.recycler.message.subject.MessageSubjectAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jkb.mrcampus.db.entity.Messages;

/**
 * 专题的消息页面
 * Created by JustKiddingBaby on 2016/11/20.
 */

public class MessageSpecialFragment extends BaseFragment implements
        MessageSubjectContract.View,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, Observer {


    public static MessageSpecialFragment newInstance() {
        return new MessageSpecialFragment();
    }

    //data
    private MessageActivity messageActivity;
    private MessageSubjectContract.Presenter mpPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;

    //列表
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MessageSubjectAdapter messageSubjectAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        messageActivity = (MessageActivity) mActivity;
        setRootView(R.layout.frg_message_subject);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mpPresenter.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mpPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        messageSubjectAdapter.setOnSubjectItemClickListener(onSubjectItemClickListener);
        //设置为消息的监听者
        MessageObservable.newInstance().addObserver(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        messageSubjectAdapter = new MessageSubjectAdapter(context, null);
        recyclerView.setAdapter(messageSubjectAdapter);
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("专题");
        //组件
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fms_srl);
        //列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fms_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(context, LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.gravy_20), 1));//添加分割线
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
    public void setSubjectMessages(List<Messages> subjectMessages) {
        hideRefreshingView();
        messageSubjectAdapter.subjectMessages = subjectMessages;
        messageSubjectAdapter.notifyDataSetChanged();
    }

    @Override
    public void readSubjectMessage(Messages messages) {
        MessageObservable.newInstance().readMessage(messages);
    }

    @Override
    public void startSubjectDetailConfession(int dynamicId) {
        messageActivity.startSpecialDetailConfession(dynamicId);
    }

    @Override
    public void startSubjectDetailLostAndFound(int dynamicId) {
        messageActivity.startSpecialDetailLostAndFound(dynamicId);
    }

    @Override
    public void startSubjectDetailFleaMarket(int dynamicId) {
        messageActivity.startSpecialDetailFleaMarket(dynamicId);
    }

    @Override
    public void startSubjectDetailTaunted(int dynamicId) {
        messageActivity.startSpecialDetailTaunted(dynamicId);
    }

    @Override
    public void startSubjectDetailWantedPartner(int dynamicId) {
        messageActivity.startSpecialDetailWantedPartner(dynamicId);
    }

    @Override
    public void startSubjectDetailWantedSavant(int dynamicId) {
        messageActivity.startSpecialDetailWantedSavant(dynamicId);
    }

    @Override
    public void startPersonCenter(int userId) {
        messageActivity.startPersonalCenterActivity(userId);
    }

    @Override
    public void setPresenter(MessageSubjectContract.Presenter presenter) {
        mpPresenter = presenter;
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
        pullSubjectMessages();
    }

    /**
     * 拉取专题消息数据
     */
    private void pullSubjectMessages() {
        showRefreshingView();
        List<Messages> allSubjectMessage = MessageObservable.newInstance().getAllSubjectMessage();
        mpPresenter.setSubjectMessages(allSubjectMessage);
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
    public void onRefresh() {
        pullSubjectMessages();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        messageActivity = null;
        //控件
        refreshLayout = null;
        recyclerView = null;
        linearLayoutManager = null;
        messageSubjectAdapter = null;
        //监听
        MessageObservable.newInstance().deleteObserver(this);
        onSubjectItemClickListener = null;
    }

    /**
     * 专题消息的点击监听回调
     */
    private MessageSubjectAdapter.OnSubjectItemClickListener onSubjectItemClickListener =
            new MessageSubjectAdapter.OnSubjectItemClickListener() {
                @Override
                public void onSubjectItemClick(int position) {
                    mpPresenter.onSubjectItemClick(position);
                }

                @Override
                public void onUserItemClick(int position) {
                    mpPresenter.onUserItemClick(position);
                }
            };
}
