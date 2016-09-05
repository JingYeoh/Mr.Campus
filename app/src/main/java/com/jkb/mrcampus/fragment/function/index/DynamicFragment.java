package com.jkb.mrcampus.fragment.function.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.core.Injection;
import com.jkb.core.contract.function.index.DynamicContract;
import com.jkb.core.contract.menu.MenuContract;
import com.jkb.core.presenter.function.index.DynamicPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.adapter.recycler.DynamicAdapter;
import com.jkb.mrcampus.adapter.recycler.itemDecoration.LineDecoration;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 首页——动态的View层
 * Created by JustKiddingBaby on 2016/8/22.
 */

public class DynamicFragment extends BaseFragment implements DynamicContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    public DynamicFragment() {
    }

    private static DynamicFragment INSTANCE = null;

    public static DynamicFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DynamicFragment();
        }
        return INSTANCE;
    }

    //View
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    //Data
    private MainActivity mainActivity;
    private DynamicPresenter mPresenter;
    private DynamicAdapter dynamicAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) mActivity;
        setRootView(R.layout.frg_homepage_dynamic);
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
        refreshLayout.setOnRefreshListener(this);

        //设置登录状态改变时候的监听器
        mainActivity.setDynamicLoginStatusChangedListener(dynamicLoginStatusChangedListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        initPresenter();//初始化P层
        if (savedInstanceState == null) {

        } else {

        }
        dynamicAdapter = new DynamicAdapter(mActivity);
        recyclerView.setAdapter(dynamicAdapter);
    }

    @Override
    protected void initView() {
        //刷新控件
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fhd_srl);
        //初始化列表栏
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fhd_rv);
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new LineDecoration(mActivity, LineDecoration.VERTICAL_LIST));//添加分割线
    }

    /**
     * 初始化Presenter层
     */
    private void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new DynamicPresenter(this,
                    Injection.provideDynamicDataResponsitory(mActivity.getApplication()));
        }
    }

    @Override
    public void onRefresh() {
//        mPresenter.onRefresh();
    }

    @Override
    public void showLoginedView() {
        rootView.findViewById(R.id.fhd_srl).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fhd_content_unLoginView).setVisibility(View.GONE);
    }

    @Override
    public void showUnLoginView() {
        rootView.findViewById(R.id.fhd_srl).setVisibility(View.GONE);
        rootView.findViewById(R.id.fhd_content_unLoginView).setVisibility(View.VISIBLE);
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
    public void showCommentView() {

    }

    @Override
    public void showShareView() {

    }

    @Override
    public void startDynamicDetailView() {

    }

    @Override
    public void like() {

    }

    @Override
    public void setPresenter(DynamicContract.Presenter presenter) {
        mPresenter = (DynamicPresenter) presenter;
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

    /**
     * 登录状态改变时候的监听器
     */
    private MenuContract.View.DynamicLoginStatusChangedListener dynamicLoginStatusChangedListener
            = new MenuContract.View.DynamicLoginStatusChangedListener() {
        @Override
        public void showLoginDynamicView() {
            showLoginedView();
        }

        @Override
        public void showLogoutDynamicView() {
            showUnLoginView();
        }
    };
}
