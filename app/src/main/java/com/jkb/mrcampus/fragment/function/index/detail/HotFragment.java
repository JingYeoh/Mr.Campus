package com.jkb.mrcampus.fragment.function.index.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.api.config.Config;
import com.jkb.core.Injection;
import com.jkb.core.contract.function.index.HotContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.UserState;
import com.jkb.core.data.dynamic.hot.HotDynamic;
import com.jkb.core.presenter.function.index.hot.HotPresenter;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.adapter.recycler.NoAlphaItemAnimator;
import com.jkb.mrcampus.adapter.recycler.dynamic.HotDynamicAdapter;
import com.jkb.mrcampus.adapter.recycler.itemDecoration.DividerItemDecoration;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ShareDynamicDialogFragment;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import jkb.mrcampus.db.entity.Schools;

/**
 * 首页——热门的View层
 * Created by JustKiddingBaby on 2016/8/22.
 */

public class HotFragment extends BaseFragment implements HotContract.View,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    public HotFragment() {
    }

    private static HotFragment INSTANCE = null;

    public static HotFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HotFragment();
        }
        return INSTANCE;
    }

    //data
    private MainActivity mainActivity;
    private HotContract.Presenter mPresenter;

    //view
    private SwipeRefreshLayout refreshLayout;

    //热门动态列表
    private HotDynamicAdapter hotDynamicAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) mActivity;
        setRootView(R.layout.frg_homepage_hot);
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
        if (!isActive()) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        //设置是否选择学校的监听事件
        SchoolInfoSingleton.getInstance().setOnSchoolSelectedChangedListener(
                onSchoolSelectedChangedListener);
        LoginContext.getInstance().addObserver(loginObserver);
        //刷新
        refreshLayout.setOnRefreshListener(this);
        rootView.findViewById(R.id.fhh_iv_floatBt_top).setOnClickListener(this);
        //设置下拉加载
        recyclerView.addOnScrollListener(onScrollListener);//设置滑动监听，设置是否下拉刷新
        //设置动态条目的点击监听事件
        hotDynamicAdapter.setOnItemClickListener(onItemClickListener);
        hotDynamicAdapter.setOnUserAttentionClickListener(onUserAttentionClickListener);
        hotDynamicAdapter.setOnCircleSubscribeClickListener(onCircleSubscribeClickListener);
        hotDynamicAdapter.setOnHeadImgClickListener(onHeadImgClickListener);
        hotDynamicAdapter.setOnCommentClickListener(onCommentClickListener);
        hotDynamicAdapter.setOnLikeClickListener(onLikeClickListener);
        hotDynamicAdapter.setOnCreatorUserClickListener(onCreatorUserClickListener);
        hotDynamicAdapter.setOnShareClickListener(onShareClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initPresenter();
        hotDynamicAdapter = new HotDynamicAdapter(context, null);
        recyclerView.setAdapter(hotDynamicAdapter);
    }

    /**
     * 初始化Presenter
     */
    private void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new HotPresenter(this,
                    Injection.provideDynamicHotDataRepository(context.getApplicationContext()));
        }
    }

    @Override
    protected void initView() {
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fhh_srl);
        //初始化热门动态列表控件
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fhh_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setItemAnimator(new NoAlphaItemAnimator());
        recyclerView.getItemAnimator().setChangeDuration(0);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(context,
                        LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.line), 1));//添加分割线
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void hideHotView() {
        rootView.findViewById(R.id.fhh_srl).setVisibility(View.GONE);
        rootView.findViewById(R.id.fhh_content_unChooseSchoolView).setVisibility(View.VISIBLE);
    }

    @Override
    public void showHotView() {
        rootView.findViewById(R.id.fhh_srl).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fhh_content_unChooseSchoolView).setVisibility(View.GONE);
    }

    @Override
    public void scrollToTop() {
        int totalItemCount = linearLayoutManager.getItemCount();
        if (totalItemCount > 0) {
            recyclerView.scrollToPosition(0);
        }
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
    public void setHotDynamicData(List<HotDynamic> hotDynamicData) {
        hotDynamicAdapter.hotDynamics = hotDynamicData;
        hotDynamicAdapter.notifyDataSetChanged();
    }

    @Override
    public void startDynamicDetail(@NonNull int dynamic_id, @NonNull String dynamicType) {
        switch (dynamicType) {
            case Config.D_TYPE_ARTICLE:
                dynamicType = DynamicDetailActivity.SHOW_DYNAMIC_TYPE_ARTICLE;
                break;
            case Config.D_TYPE_NORMAL:
                dynamicType = DynamicDetailActivity.SHOW_DYNAMIC_TYPE_NORMAL;
                break;
            case Config.D_TYPE_TOPIC:
                dynamicType = DynamicDetailActivity.SHOW_DYNAMIC_TYPE_TOPIC;
                break;
        }
        mainActivity.startDynamicActivity(dynamic_id, dynamicType);
    }

    @Override
    public void startCommentList(@NonNull int dynamic_id) {
        mainActivity.startCommentListActivity(dynamic_id);
    }

    @Override
    public void startPersonCenter(@NonNull int user_id) {
        mainActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void startCircleIndex(@NonNull int circle_id) {
        mainActivity.startCircleActivity(circle_id);
    }

    @Override
    public void showShareView(@NonNull int position) {
        mainActivity.showShareDynamicView(
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
                });
    }

    @Override
    public void setPresenter(HotContract.Presenter presenter) {
        mPresenter = presenter;
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity = null;
        LoginContext.getInstance().deleteObserver(loginObserver);
    }

    /**
     * 是否选择学校的监听器
     */
    private SchoolInfoSingleton.OnSchoolSelectedChangedListener onSchoolSelectedChangedListener =
            new SchoolInfoSingleton.OnSchoolSelectedChangedListener() {

                @Override
                public void onSchoolSelected(Schools schools) {
                    showHotView();
                }

                @Override
                public void onSchoolNotSelected() {
                    hideHotView();
                }
            };

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
                rootView.findViewById(R.id.fhh_iv_floatBt_top).setVisibility(View.VISIBLE);
            } else {
                rootView.findViewById(R.id.fhh_iv_floatBt_top).setVisibility(View.GONE);
            }
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };
    /**
     * 热门动态条目的点击监听事件
     */
    private HotDynamicAdapter.OnItemClickListener onItemClickListener =
            new HotDynamicAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    mPresenter.onHotDynamicItemClick(position);
                }
            };
    /**
     * 用户关注按钮的点击监听事件
     */
    private HotDynamicAdapter.OnUserAttentionClickListener onUserAttentionClickListener =
            new HotDynamicAdapter.OnUserAttentionClickListener() {
                @Override
                public void onUserAttentionClick(int position) {
                    mPresenter.onUserAttentionItemClick(position);
                }
            };
    /**
     * 圈子订阅按钮的点击监听事件
     */
    private HotDynamicAdapter.OnCircleSubscribeClickListener onCircleSubscribeClickListener =
            new HotDynamicAdapter.OnCircleSubscribeClickListener() {
                @Override
                public void onCircleSubscribeClick(int position) {
                    mPresenter.onCircleSubscribeItemClick(position);
                }
            };
    /**
     * 头像的点击监听事件
     */
    private HotDynamicAdapter.OnHeadImgClickListener onHeadImgClickListener =
            new HotDynamicAdapter.OnHeadImgClickListener() {
                @Override
                public void onHeadImgClick(int position) {
                    mPresenter.onHeadImgItemClick(position);
                }
            };
    /**
     * 评论的点击回调
     */
    private HotDynamicAdapter.OnCommentClickListener onCommentClickListener =
            new HotDynamicAdapter.OnCommentClickListener() {
                @Override
                public void onCommentClick(int position) {
                    mPresenter.onCommentItemClick(position);
                }
            };
    /**
     * 喜欢动态的点击回调
     */
    private HotDynamicAdapter.OnLikeClickListener onLikeClickListener =
            new HotDynamicAdapter.OnLikeClickListener() {
                @Override
                public void onLikeClick(int position) {
                    mPresenter.onLikeItemClick(position);
                }
            };
    /**
     * 原作者的点击回调
     */
    private HotDynamicAdapter.OnCreatorUserClickListener onCreatorUserClickListener =
            new HotDynamicAdapter.OnCreatorUserClickListener() {
                @Override
                public void onCreatorUserClick(int position) {
                    mPresenter.onCreatorUserClick(position);
                }
            };

    /**
     * 分享的点击回调
     */
    private HotDynamicAdapter.OnShareClickListener onShareClickListener =
            new HotDynamicAdapter.OnShareClickListener() {
                @Override
                public void onShareClick(int position) {
                    showShareView(position);
                }
            };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fhh_iv_floatBt_top:
                scrollToTop();
                break;
        }
    }

    /**
     * 登录的Observer监听
     */
    private Observer loginObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            mPresenter.setCacheExpired();
        }
    };
}
