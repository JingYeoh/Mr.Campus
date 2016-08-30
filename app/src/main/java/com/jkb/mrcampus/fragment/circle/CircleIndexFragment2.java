package com.jkb.mrcampus.fragment.circle;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
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

public class CircleIndexFragment2 extends BaseFragment
        implements CircleIndexContract.View, View.OnClickListener {


    private static CircleIndexFragment2 INSTANCE = null;

    public CircleIndexFragment2(int circleId) {
        this.circleId = circleId;
    }

    public CircleIndexFragment2() {
    }

    public static CircleIndexFragment2 newInstance(int circleId) {
        if (INSTANCE == null || circleId != 0) {
            INSTANCE = new CircleIndexFragment2(circleId);
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
    //滑动监听状态
    private CollapsingToolbarLayoutState state;
    private int color_translate;
    private int color_white;

    /**
     * 滑动监听状态
     */
    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        //新特性特效处理
        appBarLayout.addOnOffsetChangedListener(onOffsetChangedListener);
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
        //初始化颜色数据
        color_translate = mActivity.getResources().getColor(R.color.clarity);
        color_white = mActivity.getResources().getColor(R.color.white);

        ivBg = (ImageView) rootView.findViewById(R.id.fci_iv_backGround);
        ivHeadImg = (CircleImageView) rootView.findViewById(R.id.fci_iv_headImg);
        //初始化新特性
        appBarLayout = (AppBarLayout) rootView.findViewById(R.id.fci_abl);
        toolbar = (Toolbar) rootView.findViewById(R.id.fci_toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.fci_ctl);
    }

    /**
     * 滑动监听
     */
    private AppBarLayout.OnOffsetChangedListener onOffsetChangedListener
            = new AppBarLayout.OnOffsetChangedListener() {
        @Override
        public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (verticalOffset == 0) {
                if (state != CollapsingToolbarLayoutState.EXPANDED) {
                    state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                    collapsingToolbarLayout.setTitle("");//设置title为EXPANDED
                    toolbar.setBackgroundColor(color_translate);//设置颜色为透明点的颜色
                }
            } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                    collapsingToolbarLayout.setTitle("");//设置title不显示
                    toolbar.setBackgroundColor(color_white);//设置颜色为透明点的颜色
                    state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                }
            } else {
                if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                    if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                        toolbar.setBackgroundColor(color_translate);//设置颜色为不透明
                    }
                    collapsingToolbarLayout.setTitle("");//设置title为INTERNEDIATE
                    state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                }
            }
        }
    };

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
    public void setCirclePicture(Bitmap picture) {
        ivHeadImg.setImageBitmap(picture);
        //设置高斯模糊效果
        ivBg.setImageBitmap(BitmapUtil.fastBlur(picture, 8, 2));
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
