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

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MessageActivity;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 消息详情：粉丝的View层
 * Created by JustKiddingBaby on 2016/10/10.
 */

public class MessageFansFragment extends BaseFragment implements
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {


    public MessageFansFragment() {
    }

    private static MessageFansFragment INSTANCE = null;

    public static MessageFansFragment newInstance() {
        INSTANCE = new MessageFansFragment();
        return INSTANCE;
    }

    //data
    private MessageActivity messageActivity;

    //view
    private SwipeRefreshLayout refreshLayout;

    //列表
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        messageActivity = (MessageActivity) mActivity;
        setRootView(R.layout.frg_message_fans);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fmf_srl);
        //列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fmf_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("粉丝");
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
        refreshLayout = null;
        linearLayoutManager = null;
    }

    @Override
    public void onRefresh() {

    }
}
