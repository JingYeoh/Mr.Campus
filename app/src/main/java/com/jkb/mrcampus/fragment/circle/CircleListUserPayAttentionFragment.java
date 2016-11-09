package com.jkb.mrcampus.fragment.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.contract.circleList.CircleListContract;
import com.jkb.core.presenter.personCenter.data.CircleData;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CircleListActivity;
import com.jkb.mrcampus.adapter.recycler.CircleListAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;

/**
 * 圈子列表的View层
 * 显示用户关注的圈子页面
 * Created by JustKiddingBaby on 2016/9/1.
 */

public class CircleListUserPayAttentionFragment extends BaseFragment
        implements CircleListContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener {

    public static CircleListUserPayAttentionFragment newInstance(int user_id) {
        CircleListUserPayAttentionFragment INSTANCE = new CircleListUserPayAttentionFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.INTENT_KEY_USER_ID, user_id);
        INSTANCE.setArguments(bundle);
        return INSTANCE;
    }

    //data
    private int user_id = 0;
    private CircleListContract.Presenter mPresenter;
    private CircleListActivity circleListActivity;

    private CircleListAdapter circleListAdapter;

    //View
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        circleListActivity = (CircleListActivity) mActivity;
        setRootView(R.layout.frg_circle_list_user_attention);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {
        //刷新监听
        refreshLayout.setOnRefreshListener(this);
        circleListAdapter.setOnCircleItemClickListener(onClircleItemClickListener);

        recyclerView.addOnScrollListener(onScrollListener);//设置滑动监听，设置是否下拉刷新

        //其他点击事件
        rootView.findViewById(R.id.ts8_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ts8_tv_right).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            user_id = arguments.getInt(Config.INTENT_KEY_USER_ID);
        } else {
            user_id = savedInstanceState.getInt(Config.INTENT_KEY_USER_ID, 0);
        }
        if (user_id == 0) {
            showReqResult("用户不存在");
            circleListActivity.onBackPressed();
            return;
        }

        //初始化页面数据
        circleListAdapter = new CircleListAdapter(context, null);
        recyclerView.setAdapter(circleListAdapter);
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
    protected void initView() {
        //初始化控件
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fclua_srl);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fclua_rv);

        //初始化列表样式
        staggeredGridLayoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        //初始化标题栏
        ((TextView) rootView.findViewById(R.id.ts8_tv_name)).setText("所有圈子");
        ((TextView) rootView.findViewById(R.id.ts8_tv_right)).setText("创建圈子");
    }

    /**
     * 圈子列表条目被点击的时候的监听器
     */
    private CircleListAdapter.OnCircleItemClickListener onClircleItemClickListener
            = new CircleListAdapter.OnCircleItemClickListener() {
        @Override
        public void onClick(int position) {
            circleListActivity.startCircleView(mPresenter.getCircleId(position));//打开圈子首页页面
        }
    };
    /**
     * 滑动的监听器
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int[] lastItems = new int[2];
            staggeredGridLayoutManager
                    .findLastCompletelyVisibleItemPositions(lastItems);
            int lastVisibleItem = Math.max(lastItems[0], lastItems[1]);
            int totalItemCount = staggeredGridLayoutManager.getItemCount();
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ts8_ib_left://返回
                circleListActivity.backToLastView();
                break;
            case R.id.ts8_tv_right://创建圈子
                mPresenter.onCreateCircleClick();
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void showCircleNonDataView() {

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
    public void setCircleData(List<CircleData> circleData) {
        circleListAdapter.circleDatas = circleData;
        circleListAdapter.notifyDataSetChanged();
    }

    @Override
    public void startCreateCircleActivity() {
        circleListActivity.startCreateCircleActivity();
    }

    @Override
    public int bindUser_id() {
        return user_id;
    }

    @Override
    public void setPresenter(CircleListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        if (!isHidden()) circleListActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        circleListActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        circleListActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        circleListActivity = null;
        circleListAdapter = null;
        recyclerView = null;
        refreshLayout = null;
        staggeredGridLayoutManager = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_USER_ID, user_id);
    }
}
