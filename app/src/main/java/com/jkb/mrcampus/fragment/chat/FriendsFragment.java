package com.jkb.mrcampus.fragment.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.core.Injection;
import com.jkb.core.contract.menu.data.FriendsData;
import com.jkb.core.contract.menu.friends.FriendsContract;
import com.jkb.core.presenter.menu.friends.FriendsPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.adapter.recycler.itemDecoration.LineDecoration;
import com.jkb.mrcampus.adapter.recycler.menuRight.FriendsAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;

/**
 * 右滑菜单——好友页面
 * Created by JustKiddingBaby on 2016/8/9.
 */

public class FriendsFragment extends BaseFragment implements
        FriendsContract.View, SwipeRefreshLayout.OnRefreshListener {

    //View
    private RecyclerView lvFriends;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout refreshLayout;

    //Data
    private MainActivity mainActivity;
    private FriendsAdapter friendsAdapter;
    private FriendsPresenter friendsPresenter;


    private static FriendsFragment INSTANCE = null;

    public FriendsFragment() {
    }

    public static FriendsFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FriendsFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) mActivity;
        setRootView(R.layout.frg_chat_friends);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        friendsPresenter.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            friendsPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);

        lvFriends.addOnScrollListener(onScrollListener);//设置滑动监听，设置是否下拉刷新
        //设置子组件点击监听
        friendsAdapter.setOnUserItemClickListener(onCircleItemClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {

        } else {

        }
        //初始化P层
        initPresenter();
        friendsAdapter = new FriendsAdapter(mActivity, null);
        lvFriends.setAdapter(friendsAdapter);
    }

    @Override
    protected void initView() {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fcf_srl);
        //初始化列表数据
        lvFriends = (RecyclerView) rootView.findViewById(R.id.fcf_lv);
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        lvFriends.setLayoutManager(linearLayoutManager);
        lvFriends.addItemDecoration(
                new LineDecoration(mActivity, LineDecoration.VERTICAL_LIST));//添加分割线
    }

    /**
     * 初始化P层
     */
    private void initPresenter() {
        if (friendsPresenter == null) {
            friendsPresenter = new FriendsPresenter(this,
                    Injection.provideFriendsRepertory(mainActivity.getApplication()));
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
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                friendsPresenter.onLoadMore();//设置下拉加载
            }
        }
    };
    /**
     * 好友列表条目的点击事件
     */
    private FriendsAdapter.OnCircleItemClickListener onCircleItemClickListener
            = new FriendsAdapter.OnCircleItemClickListener() {
        @Override
        public void onClick(int position) {
            //跳转到个人中心
            mainActivity.startPersonalCenterActivity(friendsPresenter.getUserId(position));
        }
    };

    @Override
    public void onRefresh() {
        friendsPresenter.onRefresh();//刷新数据
    }

    @Override
    public void showNonFriendsDataView() {

    }

    @Override
    public void setFriendsData(List<FriendsData> friendsData) {
        friendsAdapter.friendsDatas = friendsData;
        friendsAdapter.notifyDataSetChanged();
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
    public void setPresenter(FriendsContract.Presenter presenter) {
        friendsPresenter = (FriendsPresenter) presenter;
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
}
