package com.jkb.mrcampus.fragment.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.core.contract.function.homepage.HomePagecontract;
import com.jkb.core.presenter.function.homepage.HomePagePresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.adapter.HomePageAdapter;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 首页的页面视图
 * Created by JustKiddingBaby on 2016/7/25.
 */
public class HomePageFragment extends BaseFragment implements HomePagecontract.View, View.OnClickListener {

    private HomePagePresenter mPresenter;

    //View层
    private TabLayout mTab;
    private ViewPager mViewPager;

    public HomePageFragment() {
    }

    /**
     * 获得一个实例化的HomePageFragment对象
     *
     * @return
     */
    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_function_index);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void initListener() {
//        rootView.findViewById(R.id.fh_bt_openLeftMenu).setOnClickListener(this);
//        rootView.findViewById(R.id.fh_bt_openRightMenu).setOnClickListener(this);
//        rootView.findViewById(R.id.fh_bt_showHot).setOnClickListener(this);
//        rootView.findViewById(R.id.fh_bt_showMap).setOnClickListener(this);
//        rootView.findViewById(R.id.fh_bt_showMatters).setOnClickListener(this);

        rootView.findViewById(R.id.fi_title_left).setOnClickListener(this);
        rootView.findViewById(R.id.fi_title_right).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        initTabView();
    }

    /**
     * 初始化TabView
     */
    private void initTabView() {
        mTab = (TabLayout) rootView.findViewById(R.id.fi_tab);
        mViewPager = (ViewPager) rootView.findViewById(R.id.fi_vp);

        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());

        mViewPager.setAdapter(new HomePageAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fh_bt_openLeftMenu:
                showLeftMenu();
                break;
            case R.id.fh_bt_openRightMenu:
                showRightMenu();
                break;
            case R.id.fh_bt_showHot:

                break;
            case R.id.fh_bt_showMap:
                showMap();
                break;
            case R.id.fh_bt_showMatters:

                break;
            case R.id.fi_title_left:
                showLeftMenu();
                break;
            case R.id.fi_title_right:
                showRightMenu();
                break;
        }
    }

    @Override
    public void showLeftMenu() {
        Log.v(TAG, "showLeftMenu");
        mPresenter.showLeftMenu();
    }

    @Override
    public void showRightMenu() {
        Log.v(TAG, "showRightMenu");
        mPresenter.showRightMenu();
    }

    @Override
    public void showHot() {

    }

    @Override
    public void showMap() {

    }

    @Override
    public void showMatters() {

    }

    @Override
    public void setPresenter(HomePagecontract.Presenter presenter) {
        mPresenter = (HomePagePresenter) presenter;
    }

    @Override
    public void showLoading(String value) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showReqResult(String value) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
