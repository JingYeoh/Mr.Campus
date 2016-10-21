package com.jkb.mrcampus.fragment.personCenter.myOriginalDynamic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.api.config.Config;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailNormalData;
import com.jkb.core.contract.myDynamic.MyDynamicNormalContract;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.activity.MyOriginalDynamicActivity;
import com.jkb.mrcampus.adapter.recycler.personCenter.myDynamic.MyDynamicNormalAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.HintDetermineFloatFragment;
import com.jkb.mrcampus.fragment.dialog.ShareDynamicDialogFragment;

import java.util.List;

/**
 * 我的动态：普通View层
 * Created by JustKiddingBaby on 2016/10/13.
 */

public class MyDynamicNormalFragment extends BaseFragment implements
        MyDynamicNormalContract.View,
        SwipeRefreshLayout.OnRefreshListener,
        View.OnClickListener {

    //用户id
    private int user_id = -1;

    public MyDynamicNormalFragment() {
    }

    private static MyDynamicNormalFragment INSTANCE = null;

    public static MyDynamicNormalFragment newInstance(@NonNull int user_id) {
        if (INSTANCE == null || user_id > 0) {
            INSTANCE = new MyDynamicNormalFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(com.jkb.mrcampus.Config.INTENT_KEY_USER_ID, user_id);
            INSTANCE.setArguments(bundle);
        }
        return INSTANCE;
    }

    //data
    private MyOriginalDynamicActivity myDynamicActivity;
    private MyDynamicNormalContract.Presenter mPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;

    //条目
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyDynamicNormalAdapter myDynamicNormalAdapter;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myDynamicActivity = (MyOriginalDynamicActivity) mActivity;
        setRootView(R.layout.frg_my_dynamic_normal);
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
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        //下拉加载
        recyclerView.addOnScrollListener(onScrollListener);
        refreshLayout.setOnRefreshListener(this);

        myDynamicNormalAdapter.setOnMyDynamicClickListener(onMyDynamicClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            user_id = savedInstanceState.getInt(Config.KEY_USER_ID);
        } else {
            Bundle arguments = getArguments();
            user_id = arguments.getInt(com.jkb.mrcampus.Config.INTENT_KEY_USER_ID);
        }
        myDynamicNormalAdapter = new MyDynamicNormalAdapter(mActivity, null);
        recyclerView.setAdapter(myDynamicNormalAdapter);
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("普通");

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fmdn_srl);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fmdn_rv);
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public int bindUser_id() {
        return user_id;
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
    public void showToastOfDelete(final int position) {
        myDynamicActivity.showHintDetermineFloatView(
                "是否删除", "（注：动态删除后无法恢复）", "确定",
                "取消", new HintDetermineFloatFragment.OnDetermineItemClickListener() {
                    @Override
                    public void onFirstItemClick() {
                        mPresenter.onItemDeleteClick(position);
                        myDynamicActivity.dismiss();
                    }

                    @Override
                    public void onSecondItemClick() {
                        myDynamicActivity.dismiss();
                    }
                });
    }

    @Override
    public void setNormalMyDynamic(List<DynamicDetailNormalData> normalMyDynamic) {
        myDynamicNormalAdapter.dynamicDetailNormalDatas = normalMyDynamic;
        myDynamicNormalAdapter.notifyDataSetChanged();
    }

    @Override
    public void startDynamicDetailNormal(@NonNull int dynamic_id) {
        myDynamicActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_NORMAL);
    }

    @Override
    public void startCommentList(@NonNull int dynamic_id) {
        myDynamicActivity.startCommentListActivity(dynamic_id);
    }

    @Override
    public void setPresenter(MyDynamicNormalContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        myDynamicActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        myDynamicActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        myDynamicActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.KEY_USER_ID, user_id);
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
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                myDynamicActivity.onBackPressed();
                break;
        }
    }

    /**
     * 我的动态的点击回调
     */
    private MyDynamicNormalAdapter.OnMyDynamicClickListener onMyDynamicClickListener =
            new MyDynamicNormalAdapter.OnMyDynamicClickListener() {
                @Override
                public void onDynamicClick(int position) {
                    mPresenter.onItemDynamicClick(position);
                }

                @Override
                public void onLikeClick(int position) {
                    mPresenter.onItemLikeClick(position);
                }

                @Override
                public void onShareClick(int position) {
                    myDynamicActivity.showShareDynamicView(onShareItemClickListener);
                }

                @Override
                public void onCommentClick(int position) {
                    mPresenter.onItemCommentClick(position);
                }

                @Override
                public void onDeleteClick(final int position) {
                    //先显示是否删除的视图
                    myDynamicActivity.showHintDetermineFloatView(
                            "是否删除", "（注：动态删除后无法恢复）", "确定",
                            "取消", new HintDetermineFloatFragment.OnDetermineItemClickListener() {
                                @Override
                                public void onFirstItemClick() {
                                    mPresenter.onItemDeleteClick(position);
                                    myDynamicActivity.dismiss();
                                }

                                @Override
                                public void onSecondItemClick() {
                                    myDynamicActivity.dismiss();
                                }
                            });
                }
            };
    /**
     * 分享点击的回调视图方法
     */
    private ShareDynamicDialogFragment.OnShareItemClickListener onShareItemClickListener =
            new ShareDynamicDialogFragment.OnShareItemClickListener() {
                @Override
                public void onWechatClick() {

                }

                @Override
                public void onWechatCircleClick() {

                }

                @Override
                public void onQQClick() {

                }

                @Override
                public void onQQZoneClick() {

                }

                @Override
                public void onSinaClick() {

                }

                @Override
                public void onCircleClick() {

                }
            };
}
