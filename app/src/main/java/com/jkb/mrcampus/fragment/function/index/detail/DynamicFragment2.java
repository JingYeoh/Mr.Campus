package com.jkb.mrcampus.fragment.function.index.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.core.Injection;
import com.jkb.core.contract.function.index.DynamicContract2;
import com.jkb.core.control.messageState.MessageObservable;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.index.dynamic.IndexDynamicData;
import com.jkb.core.presenter.function.index.dynamic.DynamicPresenter2;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicCreateActivity;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.activity.MessageActivity;
import com.jkb.mrcampus.adapter.recycler.NoAlphaItemAnimator;
import com.jkb.mrcampus.adapter.recycler.dynamic.DynamicAdapter2;
import com.jkb.mrcampus.adapter.recycler.itemDecoration.DividerItemDecoration;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.WriteDynamicDialogFragment;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * 首页——动态的V层
 * Created by JustKiddingBaby on 2016/11/8.
 */

public class DynamicFragment2 extends BaseFragment implements
        DynamicContract2.View,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener, Observer {

    public static DynamicFragment2 newInstance() {
        return new DynamicFragment2();
    }

    //View
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    //Data
    private MainActivity mainActivity;
    private DynamicContract2.Presenter mPresenter;
    private DynamicAdapter2 dynamicAdapter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) mActivity;
        setRootView(R.layout.frg_homepage_dynamic);
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
        refreshLayout.setOnRefreshListener(this);
        //设置添加动态按钮监听器
        rootView.findViewById(R.id.fhd_iv_floatBt).setOnClickListener(this);
        rootView.findViewById(R.id.fhd_iv_floatBt_top).setOnClickListener(this);
        rootView.findViewById(R.id.fhd_content_unReadMessage).setOnClickListener(this);
        //设置下拉加载
        recyclerView.addOnScrollListener(onScrollListener);//设置滑动监听，设置是否下拉刷新
        //设置条目点击事件
        dynamicAdapter.setOnDynamicItemClickListener(onDynamicItemClickListener);
        //登录状态变化的监听器
        LoginContext.getInstance().addObserver(loginObserver);
        //消息的观察者模式
        MessageObservable.newInstance().addObserver(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initPresenter();
        dynamicAdapter = new DynamicAdapter2(context, null);
        recyclerView.setAdapter(dynamicAdapter);
    }


    @Override
    protected void initView() {
        //刷新控件
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fhd_srl);
        //初始化列表栏
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fhd_rv);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new NoAlphaItemAnimator());
        recyclerView.getItemAnimator().setChangeDuration(0);
        recyclerView.addItemDecoration(
                new DividerItemDecoration(context,
                        LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.line), 1));//添加分割线
    }

    /**
     * 初始化P层
     */
    private void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new DynamicPresenter2(this,
                    Injection.provideDynamicDataResponsitory(context));
        }
    }

    @Override
    public void setUnReadDynamicMessageCount(int count) {
        if (count > 0) {
            rootView.findViewById(R.id.fhd_content_unReadMessage).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.fhd_tv_unReadMessageCount)).setText(count + "");
        } else {
            rootView.findViewById(R.id.fhd_content_unReadMessage).setVisibility(View.GONE);
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
    public void showLoginView() {
        rootView.findViewById(R.id.fhd_srl).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fhd_content_unLoginView).setVisibility(View.GONE);
    }

    @Override
    public void showLogoutView() {
        rootView.findViewById(R.id.fhd_srl).setVisibility(View.GONE);
        rootView.findViewById(R.id.fhd_content_unLoginView).setVisibility(View.VISIBLE);
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
    public void showWriteDynamicView() {
        mainActivity.showWriteDynamicView(onWriteDynamicClickListener);
    }

    @Override
    public void showCommentView() {

    }

    @Override
    public void showShareView() {

    }

    @Override
    public void setDynamicDataToView(List<IndexDynamicData> dynamicData) {
        dynamicAdapter.dynamicDatas = dynamicData;
        dynamicAdapter.notifyDataSetChanged();
    }

    @Override
    public void startArticleDynamicDetail(int dynamic_id) {
        mainActivity.startDynamicActivity(dynamic_id, DynamicDetailActivity.SHOW_DYNAMIC_TYPE_ARTICLE);
    }

    @Override
    public void startNormalDynamicDetail(int dynamic_id) {
        mainActivity.startDynamicActivity(dynamic_id, DynamicDetailActivity.SHOW_DYNAMIC_TYPE_NORMAL);
    }

    @Override
    public void startTopicDynamicDetail(int dynamic_id) {
        mainActivity.startDynamicActivity(dynamic_id, DynamicDetailActivity.SHOW_DYNAMIC_TYPE_TOPIC);
    }

    @Override
    public void startCommentActivity(int dynamic_id) {
        mainActivity.startCommentListActivity(dynamic_id);
    }

    @Override
    public void startDynamicMessageActivity() {
        mainActivity.startMessageActivity(MessageActivity.MESSAGE_TYPE_DYNAMIC);
    }

    @Override
    public void startPersonCenter(int user_id) {
        mainActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void startCircleIndex(int circle_id) {
        mainActivity.startCircleActivity(circle_id);
    }

    @Override
    public void setPresenter(DynamicContract2.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        mainActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        mainActivity.dismissLoading();
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
        mainActivity = null;
        refreshLayout = null;
        recyclerView = null;
        linearLayoutManager = null;
        dynamicAdapter = null;
        onScrollListener = null;
        LoginContext.getInstance().deleteObserver(loginObserver);
        MessageObservable.newInstance().deleteObserver(this);
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fhd_iv_floatBt:
                showWriteDynamicView();
                break;
            case R.id.fhd_iv_floatBt_top://滚动到顶部
                scrollToTop();
                break;
            case R.id.fhd_content_unReadMessage://未读消息的点击事件
                startDynamicMessageActivity();
                break;
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
                rootView.findViewById(R.id.fhd_iv_floatBt_top).setVisibility(View.VISIBLE);
            } else {
                rootView.findViewById(R.id.fhd_iv_floatBt_top).setVisibility(View.GONE);
            }
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };
    /**
     * 动态条目的点击监听
     */
    private DynamicAdapter2.OnDynamicItemClickListener onDynamicItemClickListener =
            new DynamicAdapter2.OnDynamicItemClickListener() {
                @Override
                public void onContentItemClick(int position) {
                    mPresenter.onContentItemClick(position);
                }

                @Override
                public void onHeadImgItemClick(int position) {
                    mPresenter.onHeadImgItemClick(position);
                }

                @Override
                public void onCreatorItemClick(int position) {
                    mPresenter.onCreatorItemClick(position);
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
            };
    /**
     * 写动态的监听器
     */
    private WriteDynamicDialogFragment.OnWriteDynamicClickListener onWriteDynamicClickListener
            = new WriteDynamicDialogFragment.OnWriteDynamicClickListener() {
        @Override
        public void onTopicClick() {
            mainActivity.startDynamicCreateActivity(
                    DynamicCreateActivity.DYNAMIC_CREATE_TYPE_TOPIC, 0);
        }

        @Override
        public void onArticleClick() {
            mainActivity.startDynamicCreateActivity(
                    DynamicCreateActivity.DYNAMIC_CREATE_TYPE_ARTICLE, 0);
        }

        @Override
        public void onNormalClick() {
            mainActivity.startDynamicCreateActivity(
                    DynamicCreateActivity.DYNAMIC_CREATE_TYPE_NORMAL, 0);
        }
    };
    /**
     * 登录状态改变时候的监听器
     */
    private Observer loginObserver = new Observer() {
        @Override
        public void update(Observable o, Object arg) {
            boolean logined = LoginContext.getInstance().isLogined();
            if (logined) {
                showLoginView();
            } else {
                showLogoutView();
                mPresenter.setCacheExpired();
            }
        }
    };

    @Override
    public void update(Observable o, Object arg) {
        //订阅消息数目
        int count = MessageObservable.newInstance().getAllUnReadDynamicMessageCount();
        LogUtils.d(TAG, "functionMenu---unReadMessageCount->" + count);
        setUnReadDynamicMessageCount(count);
    }
}
