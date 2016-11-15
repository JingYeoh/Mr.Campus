package com.jkb.mrcampus.fragment.personCenter.unOriginalDynamic;

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

import com.jkb.core.contract.myFavorite.MyFavoriteDynamicContract;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.MyFavoriteDynamicData;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.activity.MyUnOriginalDynamicActivity;
import com.jkb.mrcampus.adapter.recycler.personCenter.myUnOriginalDynamic.MyFavoriteDynamicAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.HintDetermineFloatFragment;
import com.jkb.mrcampus.fragment.dialog.ShareDynamicDialogFragment;

import java.util.List;

/**
 * 我的喜欢的动态View层
 * Created by JustKiddingBaby on 2016/10/16.
 */

public class MyFavoriteDynamicFragment extends BaseFragment implements
        MyFavoriteDynamicContract.View,
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    //data
    private int user_id = -1;

    public static MyFavoriteDynamicFragment newInstance(@NonNull int user_id) {
        MyFavoriteDynamicFragment INSTANCE = new MyFavoriteDynamicFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.INTENT_KEY_USER_ID, user_id);
        INSTANCE.setArguments(bundle);
        return INSTANCE;
    }

    //data
    private MyFavoriteDynamicContract.Presenter mPresenter;
    private MyUnOriginalDynamicActivity myUnOriginalDynamicActivity;

    //view
    private SwipeRefreshLayout refreshLayout;

    //关于数据
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyFavoriteDynamicAdapter myFavoriteDynamicAdapter;


    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myUnOriginalDynamicActivity = (MyUnOriginalDynamicActivity) mActivity;
        setRootView(R.layout.frg_my_unoriginal_dynamic);
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
    protected void initListener() {
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);

        rootView.findViewById(R.id.fmud_iv_floatBt_top).setOnClickListener(this);
        //刷新加载
        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(onScrollListener);
        //设置条目的点击事件
        myFavoriteDynamicAdapter.setOnMyFavoriteItemClickListener(onMyFavoriteItemClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            user_id = savedInstanceState.getInt(Config.INTENT_KEY_USER_ID);
        } else {
            Bundle arguments = getArguments();
            user_id = arguments.getInt(Config.INTENT_KEY_USER_ID);
        }
        myFavoriteDynamicAdapter = new MyFavoriteDynamicAdapter(context, null);
        recyclerView.setAdapter(myFavoriteDynamicAdapter);
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("我的喜欢");
        //初始化控件相关
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fmud_srl);
        //数据列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fmud_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                myUnOriginalDynamicActivity.onBackPressed();
                break;
            case R.id.fmud_iv_floatBt_top://设置滑动到顶部
                scrollToTop();
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_USER_ID, user_id);
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
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
    public void showHintDetermineView(final int position) {
        myUnOriginalDynamicActivity.showHintDetermineFloatView(
                "取消喜欢",
                "（注：取消喜欢后再次刷新该动态将不会出现在我的喜欢页面中，是否确定？）",
                "确定", "取消", new HintDetermineFloatFragment.OnDetermineItemClickListener() {
                    @Override
                    public void onFirstItemClick() {
                        mPresenter.favoriteDynamic(position);
                        myUnOriginalDynamicActivity.dismiss();
                    }

                    @Override
                    public void onSecondItemClick() {
                        myUnOriginalDynamicActivity.dismiss();
                    }
                });
    }

    @Override
    public void showShareDynamicView(int position) {
        myUnOriginalDynamicActivity.showShareDynamicView(onShareItemClickListener);
    }

    @Override
    public void setMyFavoriteDynamic(List<MyFavoriteDynamicData> articleDatas) {
        Log.d(TAG, "我受到的喜欢的动态数量是：" + articleDatas.size());
        myFavoriteDynamicAdapter.favoriteDynamicDatas = articleDatas;
        myFavoriteDynamicAdapter.notifyDataSetChanged();
    }

    @Override
    public void startDynamicDetailArticle(@NonNull int dynamic_id) {
        myUnOriginalDynamicActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_ARTICLE);
    }

    @Override
    public void startDynamicDetailNormal(@NonNull int dynamic_id) {
        myUnOriginalDynamicActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_NORMAL);
    }

    @Override
    public void startDynamicDetailTopic(@NonNull int dynamic_id) {
        myUnOriginalDynamicActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_TOPIC);
    }

    @Override
    public void startCommentList(@NonNull int dynamic_id) {
        myUnOriginalDynamicActivity.startCommentListActivity(dynamic_id);
    }

    @Override
    public void startPersonCenter(@NonNull int user_id) {
        myUnOriginalDynamicActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void startCircleIndex(@NonNull int circle_id) {
        myUnOriginalDynamicActivity.startCircleActivity(circle_id);
    }

    @Override
    public void setPresenter(MyFavoriteDynamicContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        myUnOriginalDynamicActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        myUnOriginalDynamicActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        myUnOriginalDynamicActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myUnOriginalDynamicActivity = null;
        refreshLayout = null;
        recyclerView = null;
        linearLayoutManager = null;
        myFavoriteDynamicAdapter = null;
    }

    /**
     * 滑动到顶部
     */
    public void scrollToTop() {
        int totalItemCount = linearLayoutManager.getItemCount();
        if (totalItemCount > 0) {
            recyclerView.scrollToPosition(0);
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
                rootView.findViewById(R.id.fmud_iv_floatBt_top).setVisibility(View.VISIBLE);
            } else {
                rootView.findViewById(R.id.fmud_iv_floatBt_top).setVisibility(View.GONE);
            }
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };
    /**
     * 我喜欢的动态的条目的点击监听事件
     */
    private MyFavoriteDynamicAdapter.OnMyFavoriteItemClickListener onMyFavoriteItemClickListener =
            new MyFavoriteDynamicAdapter.OnMyFavoriteItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mPresenter.onItemDynamicClick(position);
                }

                @Override
                public void onItemShareClick(int position) {
                    showShareDynamicView(position);
                }

                @Override
                public void onItemCommentClick(int position) {
                    mPresenter.onItemCommentClick(position);
                }

                @Override
                public void onItemFavoriteClick(int position) {
                    mPresenter.onItemLikeClick(position);
                }

                @Override
                public void onItemNameClick(int position) {
                    mPresenter.onItemNameClick(position);
                }
            };
    /**
     * 分享的点击回调
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
