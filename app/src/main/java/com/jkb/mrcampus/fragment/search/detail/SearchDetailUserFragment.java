package com.jkb.mrcampus.fragment.search.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.core.Injection;
import com.jkb.core.contract.search.SearchDetailContract;
import com.jkb.core.data.search.detail.SearchData;
import com.jkb.core.presenter.search.detail.SearchDetailPresenter;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.activity.SearchActivity;
import com.jkb.mrcampus.adapter.recycler.itemDecoration.DividerItemDecoration;
import com.jkb.mrcampus.adapter.recycler.search.SearchDetailAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.search.SearchFragment;
import com.jkb.mrcampus.fragment.search.function.SearchDetailFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索详情：用户的V层
 * Created by JustKiddingBaby on 2016/11/25.
 */

public class SearchDetailUserFragment extends BaseFragment implements
        SearchDetailContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public static SearchDetailUserFragment newInstance() {
        Bundle args = new Bundle();
        SearchDetailUserFragment fragment = new SearchDetailUserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private String searchKeyWords;
    private SearchDetailFragment searchDetailFragment;
    private SearchActivity searchActivity;
    private SearchDetailContract.Presenter mPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SearchDetailAdapter searchDetailAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchActivity = (SearchActivity) mActivity;
        searchDetailFragment = (SearchDetailFragment) getParentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_search_detail_all);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setSearchType(SearchDetailContract.Presenter.SEARCH_TYPE_USER);
        mPresenter.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.setSearchType(SearchDetailContract.Presenter.SEARCH_TYPE_USER);
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.fsda_iv_floatBt_top).setOnClickListener(this);
        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(onScrollListener);

        searchDetailAdapter.setOnSearchResultItemClickListener(onSearchResultItemClickListener);
        searchDetailFragment.addSearchKeyWordsChangedListener(onKeyWordsChangedListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //初始化P层
        mPresenter = new SearchDetailPresenter(this,
                Injection.provideSearchDetailRepertory(searchActivity.getApplicationContext()));
        if (savedInstanceState == null) {
            searchKeyWords = searchDetailFragment.getSearchKeyWords();
        } else {
            searchKeyWords = savedInstanceState.getString(Config.BUNDLE_KEY_SEARCH_KEY_WORDS);
        }
        searchDetailAdapter = new SearchDetailAdapter(context, null);
        recyclerView.setAdapter(searchDetailAdapter);
    }

    @Override
    protected void initView() {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.searchDetail_srl);
        //列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.searchDetail_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(context, LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.gravy_20), 5));//添加分割线
    }


    @Override
    public void setSearchResultCount(
            int allCount, int userCount, int circleCount, int dynamicCount, int subjectCount) {
        searchDetailFragment.setSearchResultCount(
                allCount, userCount, circleCount, dynamicCount, subjectCount);
    }

    @Override
    public void setSearchResult(List<SearchData> searchData) {
        rootView.findViewById(R.id.searchDetail_nonResult).setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        searchDetailAdapter.mSearchData = searchData;
        searchDetailAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNonResultView() {
        rootView.findViewById(R.id.searchDetail_nonResult).setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    public String getSearchKeyWords() {
        return searchKeyWords;
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
    public void scrollToTop() {
        int totalItemCount = linearLayoutManager.getItemCount();
        if (totalItemCount > 0) {
            recyclerView.scrollToPosition(0);
        }
    }

    @Override
    public void showPicturesBrowserView(ArrayList<String> pictures, int position) {
        searchActivity.showPictureBrowserView(pictures, position);
    }

    @Override
    public void startCircleIndex(int circleId) {
        searchActivity.startCircleActivity(circleId);
    }

    @Override
    public void startArticleDynamic(int dynamicId) {
        searchActivity.startDynamicActivity(dynamicId,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_ARTICLE);
    }

    @Override
    public void startNormalDynamic(int dynamicId) {
        searchActivity.startDynamicActivity(dynamicId,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_NORMAL);
    }

    @Override
    public void startTopicDynamic(int dynamicId) {
        searchActivity.startDynamicActivity(dynamicId,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_TOPIC);
    }

    @Override
    public void startPersonCenter(int userId) {
        searchActivity.startPersonalCenterActivity(userId);
    }

    @Override
    public void startSubjectConfession(int dynamicId) {
        searchActivity.startSpecialDetailConfession(dynamicId);
    }

    @Override
    public void startSubjectTaunted(int dynamicId) {
        searchActivity.startSpecialDetailTaunted(dynamicId);
    }

    @Override
    public void startSubjectFleaMarket(int dynamicId) {
        searchActivity.startSpecialDetailFleaMarket(dynamicId);
    }

    @Override
    public void startSubjectLostAndFound(int dynamicId) {
        searchActivity.startSpecialDetailLostAndFound(dynamicId);
    }

    @Override
    public void startSubjectWantedSavant(int dynamicId) {
        searchActivity.startSpecialDetailWantedSavant(dynamicId);
    }

    @Override
    public void startSubjectPartner(int dynamicId) {
        searchActivity.startSpecialDetailWantedPartner(dynamicId);
    }

    @Override
    public void setPresenter(SearchDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        searchActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        searchActivity.dismissLoading();
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
        searchActivity = null;
        searchDetailAdapter = null;
        //view
        refreshLayout = null;
        recyclerView = null;
        linearLayoutManager = null;
        //listener
        onScrollListener = null;
        searchDetailFragment.removeSearchKeyWordsChangedListener(onKeyWordsChangedListener);
        onSearchResultItemClickListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Config.BUNDLE_KEY_SEARCH_KEY_WORDS, searchKeyWords);
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
            int lastVisibleItem = (linearLayoutManager).findLastVisibleItemPosition();
            int totalItemCount = linearLayoutManager.getItemCount();
            if (lastVisibleItem > 5) {
                rootView.findViewById(R.id.fsda_iv_floatBt_top).setVisibility(View.VISIBLE);
            } else {
                rootView.findViewById(R.id.fsda_iv_floatBt_top).setVisibility(View.GONE);
            }
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fsda_iv_floatBt_top:
                scrollToTop();
                break;
        }
    }

    //搜索条目的点击监听事件
    private SearchDetailAdapter.OnSearchResultItemClickListener onSearchResultItemClickListener =
            new SearchDetailAdapter.OnSearchResultItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mPresenter.onSearchItemClick(position);
                }

                @Override
                public void onPictureItemClickListener(int position, int picturePosition) {
                    mPresenter.onPictureItemClick(position, picturePosition);
                }

                @Override
                public void onPictureItemClickListener(int position) {
                    mPresenter.onPictureItemClick(position);
                }

                @Override
                public void onCircleItemClick(int position) {
                    mPresenter.onCircleItemClick(position);
                }

                @Override
                public void onUserAttentionItemClick(int position) {
                    mPresenter.onUserAttentionItemClick(position);
                }

                @Override
                public void onUserHeadItemClick(int position) {
                    mPresenter.onUserHeadImgClick(position);
                }
            };
    /**
     * 搜索关键字变化的监听这
     */
    private SearchFragment.OnKeyWordsChangedListener onKeyWordsChangedListener =
            new SearchFragment.OnKeyWordsChangedListener() {
                @Override
                public void onKeyWordsChanged(String keyWords) {
                    searchKeyWords = keyWords;
                    mPresenter.updateKeyWords(keyWords);
                }
            };
}
