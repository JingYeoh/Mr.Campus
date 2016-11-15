package com.jkb.mrcampus.fragment.function.map.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.core.Injection;
import com.jkb.core.contract.map.list.MapListNearCircleContract;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.presenter.map.list.MapListNearCirclePresenter;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MapActivity;
import com.jkb.mrcampus.adapter.recycler.map.MapListNearCircleAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 地图列表的附近的人的V层
 * Created by JustKiddingBaby on 2016/11/14.
 */

public class MapListNearCircleFragment extends BaseFragment implements
        MapListNearCircleContract.View, SwipeRefreshLayout.OnRefreshListener, Observer, View.OnClickListener {

    public static MapListNearCircleFragment newInstance() {
        Bundle args = new Bundle();
        MapListNearCircleFragment fragment = new MapListNearCircleFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private MapActivity mapActivity;
    private MapListNearCircleContract.Presenter mPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;

    //列表
    private RecyclerView recyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private MapListNearCircleAdapter mapListNearCircleAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapActivity = (MapActivity) mActivity;
        setRootView(R.layout.frg_map_list_near_circle);
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
        if (!isHidden()) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);

        rootView.findViewById(R.id.fmlnc_tv_selectSchool).setOnClickListener(this);

        recyclerView.addOnScrollListener(onScrollListener);
        mapListNearCircleAdapter.setCircleInSchoolItemClickListener(circleInSchoolItemClickListener);
        SchoolInfoSingleton.getInstance().addObserver(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //初始化P层
        if (mPresenter == null) {
            mPresenter = new MapListNearCirclePresenter(this,
                    Injection.provideMapListRepertory(mActivity.getApplicationContext()));
        }
        //初始化其他
        mapListNearCircleAdapter = new MapListNearCircleAdapter(context, null);
        recyclerView.setAdapter(mapListNearCircleAdapter);
    }

    @Override
    protected void initView() {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fmlnc_srl);
        //列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fmlnc_rv);
        staggeredGridLayoutManager = new
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    @Override
    public void showUnSelectedSchoolView() {
        rootView.findViewById(R.id.fmlnc_selectSchoolContent).setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showSchoolSelectedView() {
        rootView.findViewById(R.id.fmlnc_selectSchoolContent).setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        onRefresh();
    }

    @Override
    public void showRefreshing() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshing() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void setCircleInfo(List<CircleInfo> circleInfo) {
        mapListNearCircleAdapter.mCircleInfo = circleInfo;
        mapListNearCircleAdapter.notifyDataSetChanged();
    }

    @Override
    public void startCircleIndex(int circleId) {
        mapActivity.startCircleActivity(circleId);
    }

    @Override
    public void setPresenter(MapListNearCircleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        mapActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        mapActivity.dismissLoading();
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
    public void onDestroy() {
        super.onDestroy();
        //data
        mapActivity = null;
        mapListNearCircleAdapter = null;
        SchoolInfoSingleton.getInstance().deleteObserver(this);
        //view
        refreshLayout = null;
        recyclerView = null;
        staggeredGridLayoutManager = null;
        //监听器
        circleInSchoolItemClickListener = null;
        onScrollListener = null;
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

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
    /**
     * 学校中的圈子的条目的点击监听
     */
    private MapListNearCircleAdapter.CircleInSchoolItemClickListener circleInSchoolItemClickListener =
            new MapListNearCircleAdapter.CircleInSchoolItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mPresenter.onCircleItemClick(position);
                }
            };

    @Override
    public void update(Observable o, Object arg) {
        if (SchoolInfoSingleton.getInstance().isSelectedSchool()) {
            showSchoolSelectedView();
        } else {
            showUnSelectedSchoolView();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fmlnc_tv_selectSchool:
                mapActivity.showSelectSchoolView();
                break;
        }
    }
}
