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
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CircleActivity;
import com.jkb.mrcampus.activity.DynamicCreateActivity;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.adapter.recycler.DynamicCircleAdapter;
import com.jkb.mrcampus.adapter.recycler.itemDecoration.DividerItemDecoration;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.HintDetermineFloatFragment;
import com.jkb.mrcampus.fragment.dialog.ShareDynamicDialogFragment;
import com.jkb.mrcampus.fragment.dialog.WriteDynamicDialogFragment;
import com.jkb.mrcampus.utils.SystemUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.push.RongPushClient;

/**
 * 圈子首页的V层
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleIndexFragment extends BaseFragment
        implements CircleIndexContract.View,
        View.OnClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static CircleIndexFragment newInstance(int circleId) {
        CircleIndexFragment INSTANCE = new CircleIndexFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(SAVED_CIRCLE_ID, circleId);
        INSTANCE.setArguments(bundle);
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

        rootView.findViewById(R.id.fci_floatBt).setOnClickListener(this);

        //设置适配器的条目监听点击事件
        dynamicCircleAdapter.setOnDynamicInCircleItemClickListener(onDynamicInCircleItemClickListener);
        dynamicCircleAdapter.setOnShareClickListener(onShareClickListener);
        dynamicCircleAdapter.setOnCommentClickListener(onCommentClickListener);
        dynamicCircleAdapter.setOnHeadImgClickListener(onHeadImgClickListener);
        dynamicCircleAdapter.setOnLikeClickListener(onLikeClickListener);
        dynamicCircleAdapter.setOnCircleDynamicItemClickListener(onCircleDynamicItemClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            circleId = arguments.getInt(SAVED_CIRCLE_ID);
        } else {
            circleId = savedInstanceState.getInt(SAVED_CIRCLE_ID);
        }
        if (circleId <= 0) {
            circleActivity.showShortToast("圈子不存在");
            circleActivity.onBackPressed();
            return;
        }
        dynamicCircleAdapter = new DynamicCircleAdapter(context, null);
        recyclerView.setAdapter(dynamicCircleAdapter);
    }

    @Override
    protected void initView() {
        //初始化颜色数据
        color_translate = context.getResources().getColor(R.color.clarity);
        color_white = context.getResources().getColor(R.color.white);

        ivBg = (ImageView) rootView.findViewById(R.id.fci_iv_backGround);
        ivHeadImg = (CircleImageView) rootView.findViewById(R.id.fci_iv_headImg);
        //初始化新特性
        appBarLayout = (AppBarLayout) rootView.findViewById(R.id.fci_abl);
        toolbar = (Toolbar) rootView.findViewById(R.id.fci_toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.fci_ctl);

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fci_srl);
        //初始化动态的控件
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fci_rv_dynamic);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);//为了解决滑动冲突的问题
        recyclerView.addItemDecoration(
                new DividerItemDecoration(context,
                        LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.line), 1));//添加分割线
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
                mPresenter.onCirclePictureClick();
                break;
            case R.id.fci_iv_subscribe:
                subscribeOrCancel();
                break;
            case R.id.fci_floatBt://发表动态
                showWriteDynamicView();
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
    public void setDynamicInCircle(List<DynamicInCircle> dynamicInCircle, boolean isCircleCreator) {
        dynamicCircleAdapter.dynamicInCircles = dynamicInCircle;
        dynamicCircleAdapter.isCircleCreaor = isCircleCreator;
        dynamicCircleAdapter.notifyDataSetChanged();
    }

    @Override
    public void back() {
        circleActivity.onBackPressed();
    }

    @Override
    public void setting() {
        mPresenter.onCircleSettingClick();
    }

    @Override
    public void chat() {
        mPresenter.onJoinChatRoomClick();
    }

    @Override
    public void subscribeOrCancel() {
        Log.d(TAG, "subscribeOrCancel");
        mPresenter.subscribeOrCancleCircle();
    }

    @Override
    public void showWriteDynamicView() {
        //显示写动态
        circleActivity.showWriteDynamicView(onWriteDynamicClickListener);
    }

    @Override
    public void showHintForPayAttentionCircle() {
        //显示提示是否加入聊天室
        circleActivity.showNewHintDetermineFloatView("是否关注该圈子？",
                "（注：您还不是该圈内成员，必须先关注该圈子才可以进入圈子聊天室，是否关注？）",
                "关注", "取消", new HintDetermineFloatFragment.OnDetermineItemClickListener() {
                    @Override
                    public void onFirstItemClick() {
                        circleActivity.dismiss();
                        mPresenter.subscribeOrCancleCircle();
                    }

                    @Override
                    public void onSecondItemClick() {
                        circleActivity.dismiss();
                    }
                });
    }

    @Override
    public void showHintForJoinChatRoot(String circleName) {
        //显示提示是否加入聊天室
        circleActivity.showNewHintDetermineFloatView("是否加入【" + circleName + "】聊天室？",
                "（注：聊天室支持实时聊天，退出聊天室后将不再接收到聊天室消息？）",
                "加入", "取消", new HintDetermineFloatFragment.OnDetermineItemClickListener() {
                    @Override
                    public void onFirstItemClick() {
                        circleActivity.dismiss();
                        joinChatRoom(circleId);
                    }

                    @Override
                    public void onSecondItemClick() {
                        circleActivity.dismiss();
                    }
                });
    }

    @Override
    public void joinChatRoom(@NonNull final int circle_id) {
        RongIM.getInstance().joinChatRoom(circle_id + "", 20,
                new RongIMClient.OperationCallback() {
                    @Override
                    public void onSuccess() {
                        SystemUtils.startConversationPrivateActivity(context,
                                RongPushClient.ConversationType.CHATROOM, circle_id + "", "标题");
                        showReqResult("加入成功");
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        showReqResult("加入失败");
                        LogUtils.w(TAG, "加入聊天室失败-------errorCode>" + errorCode.getValue());
                    }
                });
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
    public void showUserCircleSetting() {
        circleActivity.showUserCircleSetting();
    }

    @Override
    public void showVisitorCircleSetting() {
        circleActivity.showVisitorCircleSetting();
    }

    @Override
    public void showHintForPutDynamicToBlackList(final int position) {
        circleActivity.showHintDetermineFloatView("是否拉黑",
                "（注：拉黑动态后，【圈子首页】中将不会出现该条动态，该动态可在【小黑屋】中看到和恢复）",
                "确定", "取消",
                new HintDetermineFloatFragment.OnDetermineItemClickListener() {
                    @Override
                    public void onFirstItemClick() {
                        //拉黑动态并删除
                        circleActivity.dismiss();
                        mPresenter.putDynamicInBlackList(position);
                    }

                    @Override
                    public void onSecondItemClick() {
                        circleActivity.dismiss();
                    }
                });
    }

    @Override
    public void showImageBrowserView(ArrayList<String> PictureUrls, int position) {
        circleActivity.showPictureBrowserView(PictureUrls, position);
    }

    @Override
    public void share(String title, String titleUrl, String text, String imageUrl, String url,
                      String site, String siteUrl) {
        circleActivity.share(title, titleUrl, text, imageUrl, url, site, siteUrl);
    }

    @Override
    public void setPresenter(CircleIndexContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        circleActivity.showLoading(value, this);
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
    public void onDestroy() {
        super.onDestroy();
        circleActivity = null;
        ivBg = null;
        ivHeadImg = null;
        appBarLayout = null;
        toolbar = null;
        collapsingToolbarLayout = null;
        refreshLayout = null;
        dynamicCircleAdapter = null;
        recyclerView = null;
        linearLayoutManager = null;
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
     * 发表动态的点击回调
     */
    private WriteDynamicDialogFragment.OnWriteDynamicClickListener onWriteDynamicClickListener =
            new WriteDynamicDialogFragment.OnWriteDynamicClickListener() {
                @Override
                public void onTopicClick() {
                    circleActivity.startDynamicCreateActivity(
                            DynamicCreateActivity.DYNAMIC_CREATE_TYPE_TOPIC, circleId);
                }

                @Override
                public void onArticleClick() {
                    circleActivity.startDynamicCreateActivity(
                            DynamicCreateActivity.DYNAMIC_CREATE_TYPE_ARTICLE, circleId);
                }

                @Override
                public void onNormalClick() {
                    circleActivity.startDynamicCreateActivity(
                            DynamicCreateActivity.DYNAMIC_CREATE_TYPE_NORMAL, circleId);
                }
            };

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
                    mPresenter.onShareItemClick(position);
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
    /**
     * 圈子动态的条目点击监听事件
     */
    private DynamicCircleAdapter.OnCircleDynamicItemClickListener onCircleDynamicItemClickListener =
            new DynamicCircleAdapter.OnCircleDynamicItemClickListener() {
                @Override
                public void onPutInBlackListItemClick(int position) {
                    showHintForPutDynamicToBlackList(position);
                }
            };
}
