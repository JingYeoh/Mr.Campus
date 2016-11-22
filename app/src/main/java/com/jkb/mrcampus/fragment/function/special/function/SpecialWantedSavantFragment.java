package com.jkb.mrcampus.fragment.function.special.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.core.Injection;
import com.jkb.core.contract.function.special.function.SpecialWantedSavantContract;
import com.jkb.core.data.special.SpecialData;
import com.jkb.core.presenter.function.special.function.SpecialWantedSavantPresenter;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.adapter.recycler.special.function.SpecialWantedSavantAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 求学霸的V层
 * Created by JustKiddingBaby on 2016/11/15.
 */

public class SpecialWantedSavantFragment extends BaseFragment implements
        SpecialWantedSavantContract.View, SwipeRefreshLayout.OnRefreshListener,
        Observer, View.OnClickListener {

    public static SpecialWantedSavantFragment newInstance() {
        Bundle args = new Bundle();
        SpecialWantedSavantFragment fragment = new SpecialWantedSavantFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private MainActivity mainActivity;
    private SpecialWantedSavantContract.Presenter mPresenter;

    //列表
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SpecialWantedSavantAdapter specialWantedSavantAdapter;

    //view
    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) mActivity;
        setRootView(R.layout.frg_special_confession);
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
        rootView.findViewById(R.id.fsc_iv_floatBt_top).setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(onScrollListener);
        specialWantedSavantAdapter.setOnSpecialConfessionItemClickListener(
                onSpecialConfessionItemClickListener);
        SchoolInfoSingleton.getInstance().addObserver(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //绑定P层
        mPresenter = new SpecialWantedSavantPresenter(this,
                Injection.provideSpecialRepertory(mActivity.getApplicationContext()));

        specialWantedSavantAdapter = new SpecialWantedSavantAdapter(context, null);
        recyclerView.setAdapter(specialWantedSavantAdapter);
    }

    @Override
    protected void initView() {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fsc_srl);
        //列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fsc_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity = null;
        SchoolInfoSingleton.getInstance().deleteObserver(this);
        //view
        refreshLayout = null;
        //列表
        recyclerView = null;
        linearLayoutManager = null;
    }

    @Override
    public int getSchoolId() {
        if (!SchoolInfoSingleton.getInstance().isSelectedSchool()) {
            return -1;
        } else {
            return SchoolInfoSingleton.getInstance().getSchool().getSchool_id();
        }
    }

    @Override
    public void scrollToTop() {
        int totalItemCount = linearLayoutManager.getItemCount();
        if (totalItemCount > 0) {
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void showSpecialView() {
        rootView.findViewById(R.id.fsc_contentShow).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fsc_contentUnShow).setVisibility(View.GONE);
    }

    @Override
    public void hideSpecialView() {
        rootView.findViewById(R.id.fsc_contentShow).setVisibility(View.GONE);
        rootView.findViewById(R.id.fsc_contentUnShow).setVisibility(View.VISIBLE);
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
    public void setSpecialData(List<SpecialData> specialData) {
        specialWantedSavantAdapter.specialData = specialData;
        specialWantedSavantAdapter.notifyDataSetChanged();
    }

    @Override
    public void startSpecialWantedSavantDetail(int dynamicId) {
        mainActivity.startSpecialDetailWantedSavant(dynamicId);
    }

    @Override
    public void startCommentList(int dynamicId) {
        mainActivity.startCommentListActivity(dynamicId);
    }

    @Override
    public void setPresenter(SpecialWantedSavantContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        mainActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        mainActivity.dismiss();
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

    @Override
    public void update(Observable o, Object arg) {
        if (SchoolInfoSingleton.getInstance().isSelectedSchool()) {
            showSpecialView();
        } else {
            hideSpecialView();
        }
    }

    /**
     * 滑动的监听器
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int lastVisibleItem = (linearLayoutManager).findLastVisibleItemPosition();
            int totalItemCount = linearLayoutManager.getItemCount();
            if (lastVisibleItem > 5) {
                rootView.findViewById(R.id.fsc_iv_floatBt_top).setVisibility(View.VISIBLE);
            } else {
                rootView.findViewById(R.id.fsc_iv_floatBt_top).setVisibility(View.GONE);
            }
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fsc_iv_floatBt_top:
                scrollToTop();
                break;
        }
    }

    /**
     * 表白墙的条目点击事件监听
     */
    private SpecialWantedSavantAdapter.OnSpecialConfessionItemClickListener
            onSpecialConfessionItemClickListener =
            new SpecialWantedSavantAdapter.OnSpecialConfessionItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mPresenter.onItemClick(position);
                }

                @Override
                public void onCommentItemClick(int position) {
                    mPresenter.onCommentItemClick(position);
                }

                @Override
                public void onShareItemClick(int position) {
                    mPresenter.onShareItemClick(position);
                }

                @Override
                public void onHeartItemClick(int position) {
                    mPresenter.onHeartItemClick(position);
                }
            };
}
