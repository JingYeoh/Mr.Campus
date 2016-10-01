package com.jkb.mrcampus.fragment.function.index;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.core.Injection;
import com.jkb.core.contract.function.data.hot.HotDynamic;
import com.jkb.core.contract.function.index.HotContract;
import com.jkb.core.presenter.function.index.hot.HotPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.base.BaseFragment;

import java.util.List;

/**
 * 首页——热门的View层
 * Created by JustKiddingBaby on 2016/8/22.
 */

public class HotFragment extends BaseFragment implements HotContract.View {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) mActivity;
        setRootView(R.layout.frg_homepage_hot);
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
        if (!isActive()) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initPresenter();
    }

    /**
     * 初始化Presenter
     */
    private void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new HotPresenter(this,
                    Injection.provideDynamicHotDataRepository(mActivity.getApplicationContext()));
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public void hideHotView() {

    }

    @Override
    public void showHotView() {

    }

    @Override
    public void showRefreshingView() {

    }

    @Override
    public void hideRefreshingView() {

    }

    @Override
    public void setHotDynamicData(List<HotDynamic> hotDynamicData) {

    }

    @Override
    public void startDynamicDetail(@NonNull int dynamic_id, @NonNull String dynamicType) {
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
}
