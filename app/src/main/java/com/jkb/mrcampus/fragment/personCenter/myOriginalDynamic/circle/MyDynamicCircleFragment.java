package com.jkb.mrcampus.fragment.personCenter.myOriginalDynamic.circle;

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

import com.jkb.core.contract.myDynamic.circle.MyDynamicCircleContract;
import com.jkb.core.data.dynamic.myOriginal.myCircle.DynamicInCircleDynamic;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.activity.MyOriginalDynamicActivity;
import com.jkb.mrcampus.adapter.recycler.personCenter.myDynamic.circle.CircleSelectorAdapter;
import com.jkb.mrcampus.adapter.recycler.personCenter.myDynamic.circle.MyDynamicCircleAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.HintDetermineFloatFragment;
import com.jkb.mrcampus.fragment.dialog.ShareDynamicDialogFragment;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.List;

/**
 * 我的动态：圈子View层
 * Created by JustKiddingBaby on 2016/10/13.
 */

public class MyDynamicCircleFragment extends BaseFragment implements
        MyDynamicCircleContract.View,
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    //用户id
    private int user_id = -1;

    public static MyDynamicCircleFragment newInstance(@NonNull int user_id) {
        MyDynamicCircleFragment INSTANCE = new MyDynamicCircleFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.INTENT_KEY_USER_ID, user_id);
        INSTANCE.setArguments(bundle);
        return INSTANCE;
    }

    //data
    private MyOriginalDynamicActivity myOriginalDynamicActivity;
    private MyDynamicCircleContract.Presenter mPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;

    //动态列表
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private MyDynamicCircleAdapter myDynamicCircleAdapter;

    //选择圈子
    private CircleSelectorFloatFragment circleSelectorFloatFragment;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myOriginalDynamicActivity = (MyOriginalDynamicActivity) mActivity;
        setRootView(R.layout.frg_my_dynamic_circle);
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
        mPresenter.start();
    }

    @Override
    protected void initListener() {
        //标题栏
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.fmdc_ib_moreCircle).setOnClickListener(this);

        rootView.findViewById(R.id.fmdc_iv_floatBt_top).setOnClickListener(this);
        //刷新加载
        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(onScrollListener);
        //设置子条目的点击事件
        myDynamicCircleAdapter.setOnMyCircleDynamicClickListener(onMyCircleDynamicClickListener);
        //选择圈子的回调
        circleSelectorFloatFragment.setOnCircleSelectorListener(onCircleSelectorListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            user_id = savedInstanceState.getInt(Config.INTENT_KEY_USER_ID);
        } else {
            Bundle arguments = getArguments();
            user_id = arguments.getInt(Config.INTENT_KEY_USER_ID);
        }
        //初始化选择圈子
        initCircleSelector();
        myDynamicCircleAdapter = new MyDynamicCircleAdapter(context, null);
        recyclerView.setAdapter(myDynamicCircleAdapter);
    }

    /**
     * 初始化选择圈子
     */
    private void initCircleSelector() {
        if (circleSelectorFloatFragment == null) {
            circleSelectorFloatFragment = CircleSelectorFloatFragment.newInstance(user_id);
        }
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("圈子");
        //刷新控件
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fmdc_srl);
        //初始化列表
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fmdc_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left://返回
                myOriginalDynamicActivity.onBackPressed();
                break;
            case R.id.fmdc_ib_moreCircle://筛选更多圈子
                showFilterCircleSelectorView();
                break;
            case R.id.fmdc_iv_floatBt_top://回到顶部
                scrollToTop();
                break;
        }
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
    public void setCircleSelectedName(String circleName) {
        ((TextView) rootView.findViewById(R.id.fmdc_tv_circleName)).setText(circleName);
    }

    @Override
    public void showToastOfDelete(final int position) {
        myOriginalDynamicActivity.showHintDetermineFloatView(
                "是否删除", "（注：动态删除后无法恢复）", "确定",
                "取消", new HintDetermineFloatFragment.OnDetermineItemClickListener() {
                    @Override
                    public void onFirstItemClick() {
                        mPresenter.onItemDeleteClick(position);
                        myOriginalDynamicActivity.dismiss();
                    }

                    @Override
                    public void onSecondItemClick() {
                        myOriginalDynamicActivity.dismiss();
                    }
                });
    }

    @Override
    public void showFilterCircleSelectorView() {
        //显示视图
        if (!circleSelectorFloatFragment.isActive()) {
            circleSelectorFloatFragment.show(mActivity.getFragmentManager(),
                    ClassUtils.getClassName(CircleSelectorFloatFragment.class));
        }
    }

    @Override
    public void setMyCircleDynamic(
            List<DynamicInCircleDynamic> myCircleDynamic, boolean isOriginal) {
        Log.d(TAG, "我收的到的总数是" + myCircleDynamic.size());
        myDynamicCircleAdapter.dynamicInCircleDynamics = myCircleDynamic;
        myDynamicCircleAdapter.isOriginal = isOriginal;
        myDynamicCircleAdapter.notifyDataSetChanged();
    }

    @Override
    public void startDynamicDetailArticle(@NonNull int dynamic_id) {
        myOriginalDynamicActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_ARTICLE);
    }

    @Override
    public void startDynamicDetailNormal(@NonNull int dynamic_id) {
        myOriginalDynamicActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_NORMAL);
    }

    @Override
    public void startDynamicDetailTopic(@NonNull int dynamic_id) {
        myOriginalDynamicActivity.startDynamicActivity(dynamic_id,
                DynamicDetailActivity.SHOW_DYNAMIC_TYPE_TOPIC);
    }

    @Override
    public void startCircleIndex(@NonNull int circle_id) {
        myOriginalDynamicActivity.startCircleActivity(circle_id);
    }

    @Override
    public void startCommentList(@NonNull int dynamic_id) {
        myOriginalDynamicActivity.startCommentListActivity(dynamic_id);
    }

    @Override
    public void setPresenter(MyDynamicCircleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        myOriginalDynamicActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        myOriginalDynamicActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        myOriginalDynamicActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myOriginalDynamicActivity = null;
        refreshLayout=null;
        recyclerView=null;
        linearLayoutManager=null;
        myDynamicCircleAdapter=null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_USER_ID, user_id);
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
                rootView.findViewById(R.id.fmdc_iv_floatBt_top).setVisibility(View.VISIBLE);
            } else {
                rootView.findViewById(R.id.fmdc_iv_floatBt_top).setVisibility(View.GONE);
            }
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };
    /**
     * 我的圈子动态的子条目点击监听
     */
    private MyDynamicCircleAdapter.OnMyCircleDynamicClickListener onMyCircleDynamicClickListener =
            new MyDynamicCircleAdapter.OnMyCircleDynamicClickListener() {
                @Override
                public void onDynamicClick(int position) {
                    mPresenter.onItemDynamicClick(position);
                }

                @Override
                public void onCirclePicClick(int position) {
                    mPresenter.onItemCircleClick(position);
                }

                @Override
                public void onLikeClick(int position) {
                    mPresenter.onItemLikeClick(position);
                }

                @Override
                public void onShareClick(int position) {
                    myOriginalDynamicActivity.showShareDynamicView(onShareItemClickListener);
                }

                @Override
                public void onCommentClick(int position) {
                    mPresenter.onItemCommentClick(position);
                }

                @Override
                public void onDeleteClick(int position) {
                    showToastOfDelete(position);
                }
            };
    /**
     * 分享的回调
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

    /**
     * 选择圈子的回调
     */
    private CircleSelectorAdapter.OnCircleSelectorListener onCircleSelectorListener =
            new CircleSelectorAdapter.OnCircleSelectorListener() {
                @Override
                public void onAllCircleClick() {
                    mPresenter.onAllCircleSelected();
                    circleSelectorFloatFragment.dismiss();
                }

                @Override
                public void onCircleSelected(CircleInfo circleInfo) {
                    mPresenter.onCircleSelected(circleInfo);
                    circleSelectorFloatFragment.dismiss();
                }
            };
}
