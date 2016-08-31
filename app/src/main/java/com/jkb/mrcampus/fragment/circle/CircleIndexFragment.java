package com.jkb.mrcampus.fragment.circle;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.circle.CircleIndexContract;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CircleActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.utils.BitmapUtil;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 圈子首页的V层
 * Created by JustKiddingBaby on 2016/8/29.
 */

public class CircleIndexFragment extends BaseFragment
        implements CircleIndexContract.View, View.OnClickListener {


    private static CircleIndexFragment INSTANCE = null;

    public CircleIndexFragment(int circleId) {
        this.circleId = circleId;
    }

    public CircleIndexFragment() {
    }

    public static CircleIndexFragment newInstance(int circleId) {
        if (INSTANCE == null || circleId != 0) {
            INSTANCE = new CircleIndexFragment(circleId);
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_circle_index);
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

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {

        } else {
            circleId = savedInstanceState.getInt(SAVED_CIRCLE_ID);
        }
    }

    @Override
    protected void initView() {
        ivBg = (ImageView) rootView.findViewById(R.id.fci_iv_backGround);
        ivHeadImg = (CircleImageView) rootView.findViewById(R.id.fci_iv_headImg);
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
    public void setTitleName(String titleName) {

    }

    @Override
    public void setCirclePicture(Bitmap picture) {
        ivHeadImg.setImageBitmap(picture);
        //设置高斯模糊效果
        ivBg.setImageBitmap(BitmapUtil.fastBlur(picture, 20, 2));
    }

    @Override
    public void setCircleName(String name) {
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
    public void showBigPictureView() {

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
}
