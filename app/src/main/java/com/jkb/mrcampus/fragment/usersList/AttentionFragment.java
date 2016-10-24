package com.jkb.mrcampus.fragment.usersList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.contract.usersList.AttentionContract;
import com.jkb.core.presenter.usersList.data.UserData;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.UsersListActivity;
import com.jkb.mrcampus.adapter.recycler.userList.AttentionListAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;

/**
 * 关注的页面
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class AttentionFragment extends BaseFragment implements AttentionContract.View,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener,
        AttentionListAdapter.OnUserListItemsClickListener {


    public AttentionFragment() {

    }


    public static AttentionFragment INSTANCE = null;

    public static AttentionFragment newInstance(int user_id) {
        if (INSTANCE == null || user_id != -1) {
            INSTANCE = new AttentionFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(SAVED_USER_ID, user_id);
            INSTANCE.setArguments(bundle);
        }
        return INSTANCE;
    }

    //Presenter
    private AttentionContract.Presenter mPresenter;
    private UsersListActivity usersListActivity;
    //View
    private RecyclerView rv;
    private SwipeRefreshLayout refreshLayout;
    private LinearLayoutManager linearLayoutManager;
    //Data
    private int user_id = -1;
    private AttentionListAdapter attentionListAdapter;
    private static final String SAVED_USER_ID = "saved_user_id";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        usersListActivity = (UsersListActivity) mActivity;
        setRootView(R.layout.frg_userslist_attention);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void initListener() {
        rv.addOnScrollListener(onScrollListener);//设置滑动监听，设置是否下拉刷新
        refreshLayout.setOnRefreshListener(this);//设置刷新监听

        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);

        //设置子控件的监听
        attentionListAdapter.setOnUserListItemsClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            user_id = args.getInt(SAVED_USER_ID);
        } else {
            user_id = savedInstanceState.getInt(SAVED_USER_ID);//恢复数据
        }
        attentionListAdapter = new AttentionListAdapter(context, null);
        rv.setAdapter(attentionListAdapter);
    }

    @Override
    protected void initView() {
        //初始化标题栏
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText(R.string.Attention);
        //设置布局方向等
        rv = (RecyclerView) rootView.findViewById(R.id.fua_rv);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        //初始化刷新控件
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fua_srl);
    }

    @Override
    public void showPersonCenter(int user_id) {
        usersListActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void dismissRefresh$Loaded() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void showRefreshing() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public int getUser_id() {
        return this.user_id;
    }

    @Override
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public void updataViewData(List<UserData> userDatas) {
        Log.d(TAG, "当前条目数目是：" + userDatas.size());
        //更新数据
        attentionListAdapter.userDatas = userDatas;
        attentionListAdapter.notifyDataSetChanged();
    }

    @Override
    public void clickPayAttention(int position) {
        mPresenter.onPayAttentionCLicked(position);
    }

    @Override
    public void clickHeadImg(int position) {
        mPresenter.onHeadImgClicked(position);
    }

    @Override
    public void setPresenter(AttentionContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        usersListActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        usersListActivity.dismissLoading();
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
        mPresenter.onRefresh();//刷新数据
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
                mPresenter.onLoaded();//设置下拉加载
            }
        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_USER_ID, user_id);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ts4_ib_left:
                usersListActivity.onBackPressed();
                break;
        }
    }


    @Override
    public void onHeadImgClick(int position) {
        clickHeadImg(position);
    }

    @Override
    public void onAttentionClick(int position) {
        clickPayAttention(position);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        usersListActivity = null;
        LogUtils.d(TAG, "onDestroy");
    }
}
