package com.jkb.mrcampus.fragment.function;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jkb.core.contract.function.HomePageContract;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.adapter.fragmentPager.HomePageAdapter;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 首页的页面视图
 * Created by JustKiddingBaby on 2016/7/25.
 */
public class HomePageFragment extends BaseFragment implements
        HomePageContract.View, View.OnClickListener {

    private HomePageContract.Presenter mPresenter;
    private MainActivity mainActivity;

    //View层
    private TabLayout mTab;
    private ViewPager mViewPager;

    private ImageView ivRightMenu;

    public HomePageFragment() {
    }

    /**
     * 获得一个实例化的HomePageFragment对象
     */
    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_function_index);
        mainActivity = (MainActivity) mActivity;
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
        rootView.findViewById(R.id.fi_title_left).setOnClickListener(this);
        rootView.findViewById(R.id.fi_title_right).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        initTabView();
        ivRightMenu = (ImageView) rootView.findViewById(R.id.fi_title_right);
    }

    /**
     * 初始化TabView
     */
    private void initTabView() {
        mTab = (TabLayout) rootView.findViewById(R.id.fi_tab);
        mViewPager = (ViewPager) rootView.findViewById(R.id.fi_vp);

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
                showHot();//用不到，交给了TabLayout+ViewPager来控制
                break;
            case R.id.fh_bt_showMatters:
                showMatters();//用不到，交给了TabLayout+ViewPager来控制
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
        mainActivity.showLeftMenu();
    }


    @Override
    public void showRightMenu() {
        Log.v(TAG, "showRightMenu");
        mainActivity.showRightMenu();
    }

    @Override
    public void setLoginRightMenuView() {
        if (ivRightMenu != null) {
            ivRightMenu.setImageResource(R.drawable.ic_comment_black);
        }
    }

    @Override
    public void setLogoutRightMenuView() {
        if (ivRightMenu != null) {
            ivRightMenu.setImageResource(R.drawable.ic_user_head);
        }
    }

    /**
     * /**
     * //用不到，交给了TabLayout+ViewPager来控制
     */
    @Deprecated
    @Override
    public void showHot() {

    }

    /**
     * /**
     * //用不到，交给了TabLayout+ViewPager来控制
     */
    @Deprecated
    @Override
    public void showMatters() {

    }

    @Override
    public void setPresenter(HomePageContract.Presenter presenter) {
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
        showShortToash(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
