package com.jkb.mrcampus.fragment.circle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.api.config.Config;
import com.jkb.core.contract.circle.CircleIndexContract;
import com.jkb.core.data.dynamic.circle.DynamicInCircle;
import com.jkb.core.data.dynamic.dynamic.DynamicBaseData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CircleActivity;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.adapter.recycler.DynamicCircleAdapter;
import com.jkb.mrcampus.adapter.recycler.itemDecoration.LineDecoration;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ShareDynamicDialogFragment;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 圈子首页的V层
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleIndexFragment extends BaseFragment
        implements CircleIndexContract.View,
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    private static CircleIndexFragment INSTANCE = null;

    public CircleIndexFragment() {
    }

    public static CircleIndexFragment newInstance(int circleId) {
        if (INSTANCE == null || circleId >= 0) {
            INSTANCE = new CircleIndexFragment();
            Bundle bundle=new Bundle();
            bundle.putInt(SAVED_CIRCLE_ID,circleId);
            INSTANCE.setArguments(bundle);
        }
        return INSTANCE;
    }

    //data
    private int circleId = 0;
    private CircleActivity circleActivity;
    private CircleIndexContract.Presenter mPresenter;
    private static final String SAVED_CIRCLE_ID = "saved_circle_id";

    //View
    private ImageView ivBg;
    private CircleImageView ivHeadImg;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private SwipeRefreshLayout refreshLayout;

    //圈子动态
    private DynamicCircleAdapter dynamicCircleAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    //滑动监听状态
    private int collapsingState = 0;
    private int color_translate;
    private int color_white;
    private String circleName = "";

    //滑动监听的三种状态
    private static final int COLLAPSING_STATE_EXOANDED = 1001;
    private static final int COLLAPSING_STATE_COLLAPSED = 1002;
    private static final int COLLAPSING_STATE_INTERNEDIATE = 1003;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRootView(R.layout.frg_circle_index2);
        circleActivity = (CircleActivity) mActivity;
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
        //标题栏
        rootView.findViewById(R.id.ts5_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ts5_ib_right_0).setOnClickListener(this);
        rootView.findViewById(R.id.ts5_ib_right_1).setOnClickListener(this);
        //头像点击事件
        ivHeadImg.setOnClickListener(this);
        rootView.findViewById(R.id.fci_iv_subscribe).setOnClickListener(this);
        //新特性特效处理
        appBarLayout.addOnOffsetChangedListener(onOffsetChangedListener);
        //设置下拉加载
        recyclerView.addOnScrollListener(onScrollListener);//设置滑动监听，设置是否下拉刷新
        refreshLayout.setOnRefreshListener(this);

        //设置适配器的条目监听点击事件
        dynamicCircleAdapter.setOnDynamicInCircleItemClickListener(onDynamicInCircleItemClickListener);
        dynamicCircleAdapter.setOnShareClickListener(onShareClickListener);
        dynamicCircleAdapter.setOnCommentClickListener(onCommentClickListener);
        dynamicCircleAdapter.setOnHeadImgClickListener(onHeadImgClickListener);
        dynamicCircleAdapter.setOnLikeClickListener(onLikeClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            circleId=arguments.getInt(SAVED_CIRCLE_ID);
        } else {
            circleId = savedInstanceState.getInt(SAVED_CIRCLE_ID);
        }
        if (circleId <= 0) {
            circleActivity.showShortToast("圈子不存在");
            circleActivity.onBackPressed();
            return;
        }
        dynamicCircleAdapter = new DynamicCircleAdapter(mActivity, null);
        recyclerView.setAdapter(dynamicCircleAdapter);
    }

    @Override
    protected void initView() {
        //初始化颜色数据
        color_translate = mActivity.getResources().getColor(R.color.clarity);
        color_white = mActivity.getResources().getColor(R.color.white);

        ivBg = (ImageView) rootView.findViewById(R.id.fci_iv_backGround);
        ivHeadImg = (CircleImageView) rootView.findViewById(R.id.fci_iv_headImg);
        //初始化新特性
        appBarLayout = (AppBarLayout) rootView.findViewById(R.id.fci_abl);
        toolbar = (Toolbar) rootView.findViewById(R.id.fci_toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.fci_ctl);

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fci_srl);
        //初始化动态的控件
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fci_rv_dynamic);
        linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);//为了解决滑动冲突的问题
        recyclerView.addItemDecoration(
                new LineDecoration(mActivity, LineDecoration.VERTICAL_LIST));//添加分割线
    }

    /**
     * 滑动监听
     */
    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener
            = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (verticalOffset == 0) {
                if (collapsingState != COLLAPSING_STATE_EXOANDED) {
                    collapsingState = COLLAPSING_STATE_EXOANDED;//修改状态标记为展开
                    collapsingToolbarLayout.setTitle("");//设置title为EXPANDED
                    toolbar.setBackgroundColor(color_translate);//设置颜色为透明点的颜色
                    //展开时候进行的操作
                    expandedWork();
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (collapsingState != COLLAPSING_STATE_COLLAPSED) {
                    collapsingToolbarLayout.setTitle("");//设置title不显示
                    toolbar.setBackgroundColor(color_white);//设置颜色为透明点的颜色
                    collapsingState = COLLAPSING_STATE_COLLAPSED;//修改状态标记为折叠
                    //折叠起来的动作
                    collapsedWork();
                }
            } else {
                if (collapsingState != COLLAPSING_STATE_INTERNEDIATE) {
                    if (collapsingState == COLLAPSING_STATE_COLLAPSED) {
                        toolbar.setBackgroundColor(color_translate);//设置颜色为不透明
                    }
                    collapsingToolbarLayout.setTitle("");//设置title为INTERNEDIATE
                    collapsingState = COLLAPSING_STATE_INTERNEDIATE;//修改状态标记为中间
                }
            }
        }
    };

    /**
     * 折叠起来的工作
     * 设置标题栏名称为圈子名称
     */
    private void collapsedWork() {
        setTitleName(circleName);
    }

    /**
     * 展开时候的工作
     * 设置标题栏名称不可见
     * 请求圈子信息
     * 请求动态信息
     */
    private void expandedWork() {
        setTitleName("");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ts5_ib_left://返回
                back();
                break;
            case R.id.ts5_ib_right_0://聊天
                chat();
                break;
            case R.id.ts5_ib_right_1://设置
                setting();
                break;
            case R.id.fci_iv_headImg://头像点击
                showBigPictureView();
                break;
            case R.id.fci_iv_subscribe:
                subscribeOrNot();
                break;
        }
    }


    @Override
    public int provideCircleId() {
        return this.circleId;
    }

    @Override
    public void showContentView() {
        rootView.findViewById(R.id.fci_contentView).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContentView() {
        rootView.findViewById(R.id.fci_contentView).setVisibility(View.GONE);
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
    public void showShareView(@NonNull int position) {
        circleActivity.showShareDynamicView(
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
    public void setTitleName(String titleName) {
        ((TextView) rootView.findViewById(R.id.ts5_tv_name)).setText(titleName);
    }

    @Override
    public void setCirclePicture(String picture) {
        ImageLoaderFactory.getInstance().displayImage(ivHeadImg, picture);
        ImageLoaderFactory.getInstance().displayBlurImage(ivBg, picture, 5, 2);
    }

    @Override
    public void setCircleName(String name) {
        circleName = name;
        ((TextView) rootView.findViewById(R.id.fci_tv_name)).setText(name);
    }

    @Override
    public void setCircleType(String circleType) {
        ((TextView) rootView.findViewById(R.id.fci_tv_type)).setText(circleType);
    }

    @Override
    public void setCircleIntroduction(String circleIntroduction) {
        ((TextView) rootView.findViewById(R.id.fci_tv_introduction)).setText(circleIntroduction);
    }

    @Override
    public void setCircleSubscribe_count(int count) {
        ((TextView) rootView.findViewById(R.id.fci_tv_subscribeCount)).setText(count + "");
    }

    @Override
    public void setCircleOperation_count(int count) {
        ((TextView) rootView.findViewById(R.id.fci_tv_operationCount)).setText(count + "");
    }

    @Override
    public void setSubscribeStatus(boolean isSubscribe) {
        if (!isSubscribe) {//没订阅状态
            ((ImageView) rootView.findViewById(R.id.fci_iv_subscribe)).
                    setImageResource(R.drawable.ic_subscription_essays);
        } else {//订阅状态
            ((ImageView) rootView.findViewById(R.id.fci_iv_subscribe)).
                    setImageResource(R.drawable.ic_subscription_already_essays);
        }
    }

    @Override
    public void setDynamicInCircle(List<DynamicInCircle> dynamicInCircle) {
        dynamicCircleAdapter.dynamicInCircles = dynamicInCircle;
        dynamicCircleAdapter.notifyDataSetChanged();
    }

    @Override
    public void back() {
        circleActivity.onBackPressed();
    }

    @Override
    public void setting() {

    }

    @Override
    public void chat() {

    }

    @Override
    public void subscribeOrNot() {
        Log.d(TAG, "subscribeOrNot");
        mPresenter.subscribeOrCancleCircle();
    }

    @Override
    public void showBigPictureView() {

    }

    @Override
    public void startDynamicActivity(@NonNull int dynamic_id, @NonNull String dynamicType) {
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
        circleActivity.startDynamicActivity(dynamic_id, dynamicType);
    }

    @Override
    public void startCommentActivity(@NonNull int dynamic_id) {
        circleActivity.startCommentListActivity(dynamic_id);
    }

    @Override
    public void startPersonCenter(@NonNull int user_id) {
        circleActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void setPresenter(CircleIndexContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        circleActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        circleActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        circleActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_CIRCLE_ID, circleId);
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
    public void onRefresh() {
        //刷新数据
        mPresenter.onRefresh();//无用
    }

    /**
     * 圈子内动态的条目点击事件
     */
    private DynamicCircleAdapter.OnDynamicInCircleItemClickListener
            onDynamicInCircleItemClickListener =
            new DynamicCircleAdapter.OnDynamicInCircleItemClickListener() {
                @Override
                public void onDynamicInCircleClick(int position) {
                    mPresenter.onDynamicInCircleItemClick(position);
                }
            };
    /**
     * 分享的点击监听事件
     */
    private DynamicCircleAdapter.OnShareClickListener onShareClickListener =
            new DynamicCircleAdapter.OnShareClickListener() {
                @Override
                public void onShareClick(int position) {
                    showShareView(position);
                }
            };
    /**
     * 评论的点击监听事件
     */
    private DynamicCircleAdapter.OnCommentClickListener onCommentClickListener =
            new DynamicCircleAdapter.OnCommentClickListener() {
                @Override
                public void onCommentClick(int position) {
                    mPresenter.onCommentItemClick(position);
                }
            };
    /**
     * 头像的点击监听事件
     */
    private DynamicCircleAdapter.OnHeadImgClickListener onHeadImgClickListener =
            new DynamicCircleAdapter.OnHeadImgClickListener() {
                @Override
                public void onHeadImgClick(int position) {
                    mPresenter.onHeadImgItemClick(position);
                }
            };
    /**
     * 点赞的点击监听事件
     */
    private DynamicCircleAdapter.OnLikeClickListener onLikeClickListener =
            new DynamicCircleAdapter.OnLikeClickListener() {
                @Override
                public void onLikeClick(int position) {
                    mPresenter.onLikeItemClick(position);
                }
            };
}
