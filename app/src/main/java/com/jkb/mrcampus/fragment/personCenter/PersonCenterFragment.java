package com.jkb.mrcampus.fragment.personCenter;

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
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.personCenter.PersonCenterContract;
import com.jkb.core.presenter.personCenter.data.CircleData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicCreateActivity;
import com.jkb.mrcampus.activity.PersonCenterActivity;
import com.jkb.mrcampus.adapter.recycler.personCenter.PersonCenterCircleAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.WriteDynamicDialogFragment;
import com.jkb.mrcampus.fragment.usersList.AttentionFragment;
import com.jkb.mrcampus.fragment.usersList.FansFragment;
import com.jkb.mrcampus.fragment.usersList.VisitorFragment;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心的显示层
 * Created by JustKiddingBaby on 2016/8/14.
 */

public class PersonCenterFragment extends BaseFragment implements PersonCenterContract.View,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private static PersonCenterFragment INSTANCE = null;

    public PersonCenterFragment() {
    }

    public static PersonCenterFragment newInstance(int user_id) {
        INSTANCE = new PersonCenterFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.INTENT_KEY_USER_ID, user_id);
        INSTANCE.setArguments(bundle);
        return INSTANCE;
    }

    private PersonCenterActivity personCenterActivity;
    private PersonCenterContract.Presenter mPresenter;

    //View层数据
    private ImageView ivHeadImg;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    //Data数据
    private PersonCenterCircleAdapter circleAdapter;
    private boolean isUserSelf = true;//是否是用户本身

    //用户数据
    private int user_id = -1;//要显示的用户的数据
    private static final String SAVED_USER_ID = "saved_user_id";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_peronal_center_2);
        personCenterActivity = (PersonCenterActivity) mActivity;
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
        rootView.findViewById(R.id.ts3_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ts3_ib_right).setOnClickListener(this);
        rootView.findViewById(R.id.ts3_tv_attention).setOnClickListener(this);
        //浮动按钮
        rootView.findViewById(R.id.fpc_iv_floatBt).setOnClickListener(this);
        //个人信息
        rootView.findViewById(R.id.fpc_tv_sign).setOnClickListener(this);
        rootView.findViewById(R.id.fpc_iv_headImg).setOnClickListener(this);
        rootView.findViewById(R.id.fpc_ll_watched).setOnClickListener(this);
        rootView.findViewById(R.id.fpc_ll_fans).setOnClickListener(this);
        rootView.findViewById(R.id.fpc_ll_visitors).setOnClickListener(this);
        //分类信息
        rootView.findViewById(R.id.fpc_ll_allCircle).setOnClickListener(this);
        rootView.findViewById(R.id.fpc_ll_myLike).setOnClickListener(this);
        rootView.findViewById(R.id.fpc_ll_allPersonDynamic).setOnClickListener(this);
        rootView.findViewById(R.id.fpc_ll_article).setOnClickListener(this);
        rootView.findViewById(R.id.fpc_ll_topic).setOnClickListener(this);
        rootView.findViewById(R.id.fpc_ll_normal).setOnClickListener(this);
        rootView.findViewById(R.id.fpc_ll_circle).setOnClickListener(this);
        //刷新控件监听器
        refreshLayout.setOnRefreshListener(this);
        //设置点击事件
        circleAdapter.setOnCircleItemClickListener(circleItemClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            user_id = arguments.getInt(Config.INTENT_KEY_USER_ID);
        } else {
            if (user_id == -1) {//此层判断主要为了在页面销毁再次进入时保证是刷新数据而不是恢复数据
                user_id = savedInstanceState.getInt(SAVED_USER_ID);//恢复数据
            }
        }
        circleAdapter = new PersonCenterCircleAdapter(context, null);
        //绑定数据
        recyclerView.setAdapter(circleAdapter);
    }

    @Override
    protected void initView() {
        //下拉刷新控件
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fpc_srl);
        ivHeadImg = (ImageView) rootView.findViewById(R.id.fpc_iv_headImg);
        //初始化圈子视图信息，设置为横向的ListView效果
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fpc_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    /**
     * 圈子条目的点击事件监听
     */
    private PersonCenterCircleAdapter.OnCircleItemClickListener circleItemClickListener
            = new PersonCenterCircleAdapter.OnCircleItemClickListener() {
        @Override
        public void onItemClick(int position) {
            //得到圈子id
            personCenterActivity.startCircleView(mPresenter.getCircleId(position));
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ts3_ib_left://返回
                personCenterActivity.backToLastView();
                break;
            case R.id.ts3_ib_right://设置
                showPersonalSettingView();
                break;
            case R.id.ts3_tv_attention://关注按钮
                payAttentionOrCancle();
                break;
            case R.id.fpc_iv_floatBt://浮动按钮
                onFloatBtClick();
                break;
            case R.id.fpc_tv_sign://签名
                showSignView();
                break;
            case R.id.fpc_iv_headImg://头像
                showHeadImgView();
                break;
            case R.id.fpc_ll_watched://关注
                showWatchedView();
                break;
            case R.id.fpc_ll_fans://粉丝
                showFansView();
                break;
            case R.id.fpc_ll_visitors://访客
                showVisitorsView();
                break;
            case R.id.fpc_ll_allCircle://所有圈子
                showAllCirclesView();
                break;
            case R.id.fpc_ll_myLike://我的喜欢
                showMyLikeView();
                break;
            case R.id.fpc_ll_allPersonDynamic://所有动态
                showAllDynamicView();
                break;
            case R.id.fpc_ll_article://文章
                showDynamicArticleView();
                break;
            case R.id.fpc_ll_topic://话题
                showDynamicTopicView();
                break;
            case R.id.fpc_ll_normal://普通
                showDynamicNormalView();
                break;
            case R.id.fpc_ll_circle://圈子
                showDynamicCircleView();
                break;
        }
    }

    @Override
    public int getUser_id() {
        return this.user_id;
    }

    @Override
    public void setSelfConfig() {
        isUserSelf = true;
        Log.d(TAG, "isSelf=" + isUserSelf);
        showSelfTitleStyle();
        showWriteFloatBtView();
    }

    @Override
    public void setNonSelfConfig() {
        isUserSelf = false;
        Log.d(TAG, "isSelf=" + isUserSelf);
        showNonSelfTitleStyle();
        showChatFloatBtView();
    }

    @Override
    public void showSelfTitleStyle() {
        rootView.findViewById(R.id.ts3_tv_attention).setVisibility(View.GONE);
        rootView.findViewById(R.id.ts3_ib_right).setVisibility(View.VISIBLE);
    }

    @Override
    public void showNonSelfTitleStyle() {
        rootView.findViewById(R.id.ts3_ib_right).setVisibility(View.GONE);
        rootView.findViewById(R.id.ts3_tv_attention).setVisibility(View.GONE);
    }

    @Override
    public void showChatFloatBtView() {
        ((ImageView) rootView.findViewById(R.id.fpc_iv_floatBt))
                .setImageResource(R.drawable.ic_cafe_message);
    }

    @Override
    public void showWriteFloatBtView() {
        ((ImageView) rootView.findViewById(R.id.fpc_iv_floatBt))
                .setImageResource(R.drawable.ic_write);
    }

    @Override
    public void setHeadImg(String headImg) {
        ImageLoaderFactory.getInstance().displayImage(ivHeadImg, headImg);
//        ivHeadImg.setImageBitmap(headImg);
    }

    @Override
    public void setBackGround(String bitmap) {
        ImageView ivBg = ((ImageView) rootView.findViewById(R.id.fpc_iv_backGround));
        ImageLoaderFactory.getInstance().displayBlurImage(ivBg, bitmap, 15, 2);
//        bitmap = BitmapUtil.fastBlur(bitmap, 15, 2);//设置高斯模糊效果
    }

    @Override
    public void setUserName(String userName) {
        ((TextView) rootView.findViewById(R.id.fpc_tv_userName)).setText(userName);
    }

    @Override
    public void setName(String name) {
        ((TextView) rootView.findViewById(R.id.ts3_tv_name)).setText(name);
    }

    @Override
    public void setUserSign(String userSign) {
        ((TextView) rootView.findViewById(R.id.fpc_tv_sign)).setText(userSign);
    }

    @Override
    public void setWatchedNum(int watched) {
//        Log.d(TAG, "watched=" + watched);
        ((TextView) rootView.findViewById(R.id.fpc_tv_watchedNum)).setText(watched + "");
    }

    @Override
    public void setFansNum(int fans) {
//        Log.d(TAG, "fans=" + fans);
        ((TextView) rootView.findViewById(R.id.fpc_tv_fansNum)).setText(fans + "");
    }

    @Override
    public void setVistiorsNum(int visitors) {
//        Log.d(TAG, "visitors=" + visitors);
        ((TextView) rootView.findViewById(R.id.fpc_tv_visitorsNum)).setText(visitors + "");
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
    public void showCircleNonDataView() {
        rootView.findViewById(R.id.fpc_rv).setVisibility(View.GONE);
        rootView.findViewById(R.id.fpc_iv_nonCircleData).setVisibility(View.VISIBLE);
    }

    @Override
    public void setCircleViewData(List<CircleData> data) {
        rootView.findViewById(R.id.fpc_iv_nonCircleData).setVisibility(View.GONE);
        rootView.findViewById(R.id.fpc_rv).setVisibility(View.VISIBLE);
        Log.d(TAG, "data---一共有----:" + data.size());
        //设置数据
        circleAdapter.circleDatas = data;
        circleAdapter.notifyDataSetChanged();
    }


    @Override
    public void showPersonalSettingView() {
        //显示个人设置页面
        personCenterActivity.showFragment(ClassUtils.getClassName(PersonSettingFragment.class));
    }

    @Override
    public void showHeadImgView() {

    }

    @Override
    public void onFloatBtClick() {
        if (!isUserSelf) {
            chat();
        } else {
            showWriteDynamicView();
        }
    }

    @Override
    public void showSignView() {
        String sign = ((TextView) rootView.findViewById(R.id.fpc_tv_sign)).getText().toString();
        personCenterActivity.showTextFloatView(sign);
    }

    @Override
    public void showPayAttentionView() {
        TextView tvPayAttention = (TextView) rootView.findViewById(R.id.ts3_tv_attention);
        tvPayAttention.setVisibility(View.VISIBLE);
        tvPayAttention.setText("已关注");
        tvPayAttention.setBackgroundResource(
                R.drawable.bg_edittext_mainthemegravy_white_round_content);
    }

    @Override
    public void showUnPayAttentionView() {
        TextView tvPayAttention = (TextView) rootView.findViewById(R.id.ts3_tv_attention);
        tvPayAttention.setVisibility(View.VISIBLE);
        tvPayAttention.setText("关注");
        tvPayAttention.setBackgroundResource(R.drawable.bg_edittext_maintheme_white_round_content);
    }

    @Override
    public void payAttentionOrCancle() {
        mPresenter.payAttentionOrCancle();
    }

    @Override
    public void showWatchedView() {
        //显示粉丝视图
        String action = ClassUtils.getClassName(AttentionFragment.class);
        personCenterActivity.startUserListView(this.user_id, action);
    }

    @Override
    public void showFansView() {
        //显示粉丝视图
        String action = ClassUtils.getClassName(FansFragment.class);
        personCenterActivity.startUserListView(this.user_id, action);
    }

    @Override
    public void showVisitorsView() {
        //显示粉丝视图
        String action = ClassUtils.getClassName(VisitorFragment.class);
        personCenterActivity.startUserListView(this.user_id, action);
    }

    @Override
    public void showAllCirclesView() {
        personCenterActivity.startCircleListActivity(user_id);
    }

    @Override
    public void showMyLikeView() {
        personCenterActivity.startMyFavoriteActivity(user_id);
    }

    @Override
    public void showAllDynamicView() {

    }

    @Override
    public void showDynamicArticleView() {
        personCenterActivity.startMyDynamicArticleActivity(user_id);
    }

    @Override
    public void showDynamicTopicView() {
        personCenterActivity.startMyDynamicTopicActivity(user_id);
    }

    @Override
    public void showDynamicNormalView() {
        personCenterActivity.startMyDynamicNormalActivity(user_id);
    }

    @Override
    public void showDynamicCircleView() {
        personCenterActivity.startMyDynamicCircleActivity(user_id);
    }

    @Override
    public void chat() {
        mPresenter.onChatClick();
    }

    @Override
    public void showWriteDynamicView() {
        personCenterActivity.showWriteDynamicView(onWriteDynamicClickListener);
    }

    @Override
    public void hideContentView() {
        rootView.findViewById(R.id.fpc_content).setVisibility(View.GONE);
    }

    @Override
    public void showContentView() {
        rootView.findViewById(R.id.fpc_content).setVisibility(View.VISIBLE);
    }

    @Override
    public void startPrivateConversationActivity(@NonNull int user_id) {
        personCenterActivity.startPrivateConversation(user_id);
    }

    @Override
    public void setPresenter(PersonCenterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        if (!isHidden())
            personCenterActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        personCenterActivity.dismissLoading();
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_USER_ID, user_id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        personCenterActivity = null;
        mPresenter = null;
        recyclerView = null;
        refreshLayout = null;
        ivHeadImg = null;
        circleAdapter = null;
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    /**
     * 写动态页面的页面视图监听器
     */
    private WriteDynamicDialogFragment.OnWriteDynamicClickListener onWriteDynamicClickListener =
            new WriteDynamicDialogFragment.OnWriteDynamicClickListener() {
                @Override
                public void onTopicClick() {
                    personCenterActivity.startDynamicCreateActivity
                            (DynamicCreateActivity.DYNAMIC_CREATE_TYPE_TOPIC, 0);
                }

                @Override
                public void onArticleClick() {
                    personCenterActivity.startDynamicCreateActivity
                            (DynamicCreateActivity.DYNAMIC_CREATE_TYPE_ARTICLE, 0);
                }

                @Override
                public void onNormalClick() {
                    personCenterActivity.startDynamicCreateActivity
                            (DynamicCreateActivity.DYNAMIC_CREATE_TYPE_NORMAL, 0);
                }
            };
}
