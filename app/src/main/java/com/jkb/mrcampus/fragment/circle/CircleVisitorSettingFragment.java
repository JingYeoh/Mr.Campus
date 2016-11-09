package com.jkb.mrcampus.fragment.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.circle.CircleSettingVisitorContract;
import com.jkb.core.presenter.circle.CircleSettingVisitorPresenter;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CircleActivity;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 访客的圈子设置页面
 * Created by JustKiddingBaby on 2016/10/7.
 */

public class CircleVisitorSettingFragment extends BaseFragment implements
        CircleSettingVisitorContract.View,
        View.OnClickListener {

    public static CircleVisitorSettingFragment newInstance(int circle_id) {
        CircleVisitorSettingFragment INSTANCE = new CircleVisitorSettingFragment();
        Bundle args = new Bundle();
        args.putInt(Config.INTENT_KEY_CIRCLE_ID, circle_id);
        INSTANCE.setArguments(args);
        return INSTANCE;
    }

    //data
    private int circle_id;//圈子id
    private CircleActivity circleActivity;
    private CircleSettingVisitorContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        circleActivity = (CircleActivity) mActivity;
        setRootView(R.layout.frg_circle_setting_visitor);
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
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        //圈子相关点击
        rootView.findViewById(R.id.fcsv_ll_userList).setOnClickListener(this);
        rootView.findViewById(R.id.fcsv_iv_switch_inCommonUse).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arg = getArguments();
            circle_id = arg.getInt(Config.INTENT_KEY_CIRCLE_ID);
        } else {
            circle_id = savedInstanceState.getInt(Config.INTENT_KEY_CIRCLE_ID);
        }
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("圈子设置");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                circleActivity.onBackPressed();
                break;
            case R.id.fcsv_ll_userList:
                showAttentionCircleUserList(mPresenter.isCircleCreator());
                break;
            case R.id.fcsv_iv_switch_inCommonUse:
                mPresenter.onInCommonUseSwitchClick();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        circleActivity = null;
        mPresenter = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_CIRCLE_ID, circle_id);
    }

    @Override
    public int getCircleId() {
        return circle_id;
    }

    @Override
    public void setInCommonUseStatus(boolean inCommonUseStatus) {
        ImageView imageView = (ImageView) rootView.findViewById(R.id.fcsv_iv_switch_inCommonUse);
        imageView.setSelected(inCommonUseStatus);
    }

    @Override
    public void showAttentionCircleUserList(boolean isCircleCreator) {
        circleActivity.showAttentionUserList(false);
    }

    @Override
    public void setPresenter(CircleSettingVisitorContract.Presenter presenter) {
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
        showShortToash(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
