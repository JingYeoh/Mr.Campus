package com.jkb.mrcampus.fragment.circle;

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

import com.jkb.core.contract.circle.CircleDynamicInCircleBlackListContract;
import com.jkb.core.data.circle.dynamicInBlackList.DynamicInBlackList;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CircleActivity;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.adapter.recycler.circle.dynamicInBlackList.CircleDynamicInBlackListAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;

/**
 * 圈子动态黑名单View层
 * Created by JustKiddingBaby on 2016/11/5.
 */

public class CircleDynamicInCircleBlackListFragment extends BaseFragment implements
        CircleDynamicInCircleBlackListContract.View,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static CircleDynamicInCircleBlackListFragment newInstance(@NonNull int circle_id) {
        Bundle args = new Bundle();
        args.putInt(Config.INTENT_KEY_CIRCLE_ID, circle_id);
        CircleDynamicInCircleBlackListFragment fragment =
                new CircleDynamicInCircleBlackListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private int circle_id;//圈子id
    private CircleActivity circleActivity;
    private CircleDynamicInCircleBlackListContract.Presenter mPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;

    //列表
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CircleDynamicInBlackListAdapter circleDynamicInBlackListAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        circleActivity = (CircleActivity) mActivity;
        setRootView(R.layout.frg_circle_black_list_dynamic);
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
        circleDynamicInBlackListAdapter.setOnDynamicInCircleBlackListItemClickListener(
                onDynamicInCircleBlackListItemClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            circle_id = args.getInt(Config.INTENT_KEY_CIRCLE_ID);
        } else {
            circle_id = savedInstanceState.getInt(Config.INTENT_KEY_CIRCLE_ID);
        }
        circleDynamicInBlackListAdapter = new CircleDynamicInBlackListAdapter(context, null);
        recyclerView.setAdapter(circleDynamicInBlackListAdapter);
    }

    @Override
    protected void initView() {
        //标题栏
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("小黑屋");
        //其他
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fcbld_srl);
        //列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fcbld_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_CIRCLE_ID, circle_id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        circleActivity = null;
        mPresenter = null;
        recyclerView = null;
        linearLayoutManager = null;
        refreshLayout = null;
        circleDynamicInBlackListAdapter = null;
        onDynamicInCircleBlackListItemClickListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                circleActivity.onBackPressed();
                break;
        }
    }

    @Override
    public int getCircleId() {
        return circle_id;
    }

    @Override
    public void setDynamicInBlackListData(List<DynamicInBlackList> dynamicInBlackListData) {
        circleDynamicInBlackListAdapter.dynamicInBlackLists = dynamicInBlackListData;
        circleDynamicInBlackListAdapter.notifyDataSetChanged();
        LogUtils.d(TAG, "我收到的数据一共有：" + dynamicInBlackListData.size());
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
    public void startDynamicDetailTopic(@NonNull int dynamic_id) {
        circleActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_TOPIC);
    }

    @Override
    public void startDynamicDetailArticle(@NonNull int dynamic_id) {
        circleActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_ARTICLE);
    }

    @Override
    public void startDynamicDetailNormal(@NonNull int dynamic_id) {
        circleActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_NORMAL);
    }

    @Override
    public void startPersonCenter(@NonNull int user_id) {
        circleActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void setPresenter(CircleDynamicInCircleBlackListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        circleActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        circleActivity.dismissLoading();
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
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    /**
     * 动态条目的点击监听事件
     */
    private CircleDynamicInBlackListAdapter.OnDynamicInCircleBlackListItemClickListener
            onDynamicInCircleBlackListItemClickListener =
            new CircleDynamicInBlackListAdapter.OnDynamicInCircleBlackListItemClickListener() {
                @Override
                public void onDynamicItemClick(int position) {
                    mPresenter.onDynamicItemClick(position);
                }

                @Override
                public void onUserItemClick(int position) {
                    mPresenter.onUserItemClick(position);
                }

                @Override
                public void onRestoreItemClick(int position) {
                    mPresenter.onPullDynamicOutBlackItemClick(position);
                }
            };
}
