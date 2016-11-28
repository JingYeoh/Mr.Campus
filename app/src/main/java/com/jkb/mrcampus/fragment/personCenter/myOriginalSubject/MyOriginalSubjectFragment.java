package com.jkb.mrcampus.fragment.personCenter.myOriginalSubject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.contract.personCenter.original.mySubject.MyOriginalSubjectContract;
import com.jkb.core.data.special.SpecialData;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MyOriginalSubjectActivity;
import com.jkb.mrcampus.adapter.recycler.personCenter.original.mySubject.MyOriginalSubjectAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.HintDetermineFloatFragment;

import java.util.List;

/**
 * 我的原创专题的V层
 * Created by JustKiddingBaby on 2016/11/22.
 */

public class MyOriginalSubjectFragment extends BaseFragment implements
        MyOriginalSubjectContract.View,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    public static MyOriginalSubjectFragment newInstance(int subjectType) {
        Bundle args = new Bundle();
        args.putInt(Config.BUNDLE_KEY_SUBJECT_TYPE, subjectType);
        MyOriginalSubjectFragment fragment = new MyOriginalSubjectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private int subjectType = -1;
    private MyOriginalSubjectActivity myOriginalSubjectActivity;
    private MyOriginalSubjectContract.Presenter mPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;

    //列表
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    //适配器
    private MyOriginalSubjectAdapter myOriginalSubjectAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myOriginalSubjectActivity = (MyOriginalSubjectActivity) mActivity;
        setRootView(R.layout.frg_my_original_subject);
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
        rootView.findViewById(R.id.fmos_iv_floatBt_top).setOnClickListener(this);

        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(onScrollListener);
        myOriginalSubjectAdapter.setOnOriginalSubjectItemClickListener(
                onOriginalSubjectItemClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            subjectType = args.getInt(Config.BUNDLE_KEY_SUBJECT_TYPE);
        } else {
            subjectType = savedInstanceState.getInt(Config.BUNDLE_KEY_SUBJECT_TYPE);
        }
        myOriginalSubjectAdapter = new MyOriginalSubjectAdapter(context, null);
        recyclerView.setAdapter(myOriginalSubjectAdapter);
    }

    @Override
    protected void initView() {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fmos_srl);
        //列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fmos_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void scrollToTop() {
        int totalItemCount = linearLayoutManager.getItemCount();
        if (totalItemCount > 0) {
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public int getSubjectType() {
        switch (subjectType) {
            case MyOriginalSubjectActivity.SUBJECT_TYPE_FLEAMARKET:
                setSubjectTitle("我的表白墙");
                return MyOriginalSubjectContract.Presenter.SUBJECT_TYPE_FLEAMARKET;
            case MyOriginalSubjectActivity.SUBJECT_TYPE_LOSTANDFOUND:
                setSubjectTitle("我的失物招领");
                return MyOriginalSubjectContract.Presenter.SUBJECT_TYPE_LOSTANDFOUND;
            case MyOriginalSubjectActivity.SUBJECT_TYPE_TAUNTED:
                setSubjectTitle("我的吐槽墙");
                return MyOriginalSubjectContract.Presenter.SUBJECT_TYPE_TAUNTED;
            case MyOriginalSubjectActivity.SUBJECT_TYPE_WANTED_PARTNER:
                setSubjectTitle("我的寻水友");
                return MyOriginalSubjectContract.Presenter.SUBJECT_TYPE_WANTED_PARTNER;
            case MyOriginalSubjectActivity.SUBJECT_TYPE_WANTED_SAVANT:
                setSubjectTitle("我的求学霸");
                return MyOriginalSubjectContract.Presenter.SUBJECT_TYPE_WANTED_SAVANT;
            case MyOriginalSubjectActivity.SUBJECT_TYPE_CONFESSION:
                setSubjectTitle("我的表白墙");
                return MyOriginalSubjectContract.Presenter.SUBJECY_TYPE_CONFESSION;
        }
        return -1;
    }

    @Override
    public void setSubjectTitle(String subjectTitle) {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText(subjectTitle);
    }

    @Override
    public void setSubjectData(List<SpecialData> subjectData) {
        LogUtils.d(TAG, "我收到的数据是：" + subjectData.size());
        myOriginalSubjectAdapter.mSpecialData = subjectData;
        myOriginalSubjectAdapter.notifyDataSetChanged();
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
    public void showDeletedHintSelectorView(
            final MyOriginalSubjectContract.OnDeleteSelectorItemClickListener listener) {
        myOriginalSubjectActivity.showHintDetermineFloatView("是否删除？",
                "（注：专题删除后不可恢复，是否确定？）", "确定", "取消",
                new HintDetermineFloatFragment.OnDetermineItemClickListener() {
                    @Override
                    public void onFirstItemClick() {
                        listener.onDeletedClick();
                        myOriginalSubjectActivity.dismiss();
                    }

                    @Override
                    public void onSecondItemClick() {
                        myOriginalSubjectActivity.dismiss();
                    }
                });
    }

    @Override
    public void startCommentList(int dynamicId) {
        myOriginalSubjectActivity.startCommentListActivity(dynamicId);
    }

    @Override
    public void startSpecialDetailConfession(int dynamicId) {
        myOriginalSubjectActivity.startSpecialDetailConfession(dynamicId);
    }

    @Override
    public void startSpecialDetailTaunted(int dynamicId) {
        myOriginalSubjectActivity.startSpecialDetailTaunted(dynamicId);
    }

    @Override
    public void startSpecialDetailFleaMarket(int dynamicId) {
        myOriginalSubjectActivity.startSpecialDetailFleaMarket(dynamicId);
    }

    @Override
    public void startSpecialDetailLostAndFound(int dynamicId) {
        myOriginalSubjectActivity.startSpecialDetailLostAndFound(dynamicId);
    }

    @Override
    public void startSpecialDetailWantedSavant(int dynamicId) {
        myOriginalSubjectActivity.startSpecialDetailWantedSavant(dynamicId);
    }

    @Override
    public void startSpecialDetailWantedPartner(int dynamicId) {
        myOriginalSubjectActivity.startSpecialDetailWantedPartner(dynamicId);
    }


    @Override
    public void share(
            String title, String titleUrl, String text, String imageUrl, String url,
            String site, String siteUrl) {
        myOriginalSubjectActivity.share(title, titleUrl, text, imageUrl, url, site, siteUrl);
    }

    @Override
    public void setPresenter(MyOriginalSubjectContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        myOriginalSubjectActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        myOriginalSubjectActivity.dismissLoading();
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
        myOriginalSubjectActivity = null;
        //view
        refreshLayout = null;
        recyclerView = null;
        linearLayoutManager = null;
        onOriginalSubjectItemClickListener = null;
        myOriginalSubjectAdapter = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.BUNDLE_KEY_SUBJECT_TYPE, subjectType);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                myOriginalSubjectActivity.onBackPressed();
                break;
            case R.id.fmos_iv_floatBt_top:
                scrollToTop();
                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    /**
     * 专题的条目点击事件回调
     */
    private MyOriginalSubjectAdapter.OnOriginalSubjectItemClickListener
            onOriginalSubjectItemClickListener =
            new MyOriginalSubjectAdapter.OnOriginalSubjectItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mPresenter.onItemClick(position);
                }

                @Override
                public void onShareItemClick(int position) {
                    mPresenter.onShareItemClick(position);
                }

                @Override
                public void onCommentItemClick(int position) {
                    mPresenter.onCommentItemClick(position);
                }

                @Override
                public void onLikeItemClick(int position) {
                    mPresenter.onLikeItemClick(position);
                }

                @Override
                public void onDeleteItemClick(int position) {
                    mPresenter.onDeleteItemClick(position);
                }

                @Override
                public void onMarkItemClick(int position) {
                    mPresenter.onMarkItemClick(position);
                }
            };

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
                rootView.findViewById(R.id.fmos_iv_floatBt_top).setVisibility(View.VISIBLE);
            } else {
                rootView.findViewById(R.id.fmos_iv_floatBt_top).setVisibility(View.GONE);
            }
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };
}
