package com.jkb.mrcampus.fragment.personCenter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.Text;
import com.jkb.core.contract.personCenter.PersonCenterContract;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.PersonCenterActivity;
import com.jkb.mrcampus.adapter.recycler.PersonCenterCircleAdapter;
import com.jkb.mrcampus.base.BaseFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心的显示层
 * Created by JustKiddingBaby on 2016/8/14.
 */

public class PersonCenterFragment extends BaseFragment implements PersonCenterContract.View, View.OnClickListener {

    private static PersonCenterFragment INSTANCE = null;

    public PersonCenterFragment() {
    }

    public static PersonCenterFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonCenterFragment();
        }
        return INSTANCE;
    }

    private PersonCenterActivity personCenterActivity;
    private PersonCenterContract.Presenter mPresenter;

    //View层数据
    private CircleImageView ivHeadImg;
    private RecyclerView recyclerView;

    //Data数据
    private PersonCenterCircleAdapter circleAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRootView(R.layout.frg_peronal_center_2);
        personCenterActivity = (PersonCenterActivity) mActivity;
        init(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void initListener() {
        //标题栏
        rootView.findViewById(R.id.ts3_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ts3_ib_right).setOnClickListener(this);
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
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        circleAdapter = new PersonCenterCircleAdapter(mActivity);
        //绑定数据
        recyclerView.setAdapter(circleAdapter);
    }

    @Override
    protected void initView() {
        ivHeadImg = (CircleImageView) rootView.findViewById(R.id.fpc_iv_headImg);
        //初始化圈子视图信息，设置为横向的ListView效果
        recyclerView = (RecyclerView) rootView.findViewById(R.id.fpc_rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ts3_ib_left://返回
                personCenterActivity.backToLastView();
                break;
            case R.id.ts3_ib_right://设置
                showPersonalSettingView();
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
                showArticleView();
                break;
            case R.id.fpc_ll_topic://话题
                showTopicView();
                break;
            case R.id.fpc_ll_normal://普通
                showNormalView();
                break;
            case R.id.fpc_ll_circle://圈子
                showCircleView();
                break;
        }
    }

    @Override
    public void setHeadImg(Bitmap headImg) {
        ivHeadImg.setImageBitmap(headImg);
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
        Log.d(TAG, "watched=" + watched);
        ((TextView) rootView.findViewById(R.id.fpc_tv_watchedNum)).setText(watched + "");
    }

    @Override
    public void setFansNum(int fans) {
        Log.d(TAG, "fans=" + fans);
        ((TextView) rootView.findViewById(R.id.fpc_tv_fansNum)).setText(fans + "");
    }

    @Override
    public void setVistiorsNum(int visitors) {
        Log.d(TAG, "visitors=" + visitors);
        ((TextView) rootView.findViewById(R.id.fpc_tv_visitorsNum)).setText(visitors + "");
    }

    @Override
    public void showCircleNonDataView() {
        rootView.findViewById(R.id.fpc_rv).setVisibility(View.GONE);
        rootView.findViewById(R.id.fpc_iv_nonCircleData).setVisibility(View.VISIBLE);
    }

    @Override
    public void showCircleView(Object data) {
        rootView.findViewById(R.id.fpc_iv_nonCircleData).setVisibility(View.GONE);
        rootView.findViewById(R.id.fpc_rv).setVisibility(View.VISIBLE);
        //设置数据
    }


    @Override
    public void showPersonalSettingView() {

    }

    @Override
    public void showHeadImgView() {

    }

    @Override
    public void showSignView() {
        String sign = ((TextView) rootView.findViewById(R.id.fpc_tv_sign)).getText().toString();
        personCenterActivity.showTextFloatView(sign);
    }

    @Override
    public void showWatchedView() {

    }

    @Override
    public void showFansView() {

    }

    @Override
    public void showVisitorsView() {

    }

    @Override
    public void showAllCirclesView() {

    }

    @Override
    public void showMyLikeView() {

    }

    @Override
    public void showAllDynamicView() {

    }

    @Override
    public void showArticleView() {

    }

    @Override
    public void showTopicView() {

    }

    @Override
    public void showNormalView() {

    }

    @Override
    public void showCircleView() {

    }

    @Override
    public void setPresenter(PersonCenterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
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
}
