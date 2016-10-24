package com.jkb.mrcampus.fragment.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jkb.core.contract.menu.RightMenuContract;
import com.jkb.core.control.userstate.UserState;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.adapter.fragmentPager.ChatAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.usersList.AttentionFragment;
import com.jkb.mrcampus.fragment.usersList.FansFragment;
import com.jkb.mrcampus.fragment.usersList.VisitorFragment;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 右滑菜单：聊天页面
 * Created by JustKiddingBaby on 2016/7/24.
 */
public class RightMenuFragment extends BaseFragment implements RightMenuContract.View,
        View.OnClickListener {

    //View层
    private TabLayout mTab;
    private ViewPager mViewPager;
    //P层
    private RightMenuContract.Presenter mPresenter;

    //Data
    private MainActivity mainActivity;

    public RightMenuFragment() {
    }

    /**
     * 获得一个实例化的ChatFragment对象
     */
    public static RightMenuFragment newInstance() {
        return new RightMenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity = (MainActivity) mActivity;
        setRootView(R.layout.frg_menu_chat);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        mPresenter.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged");
        if (!hidden) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.fmc_ll_watched).setOnClickListener(this);
        rootView.findViewById(R.id.fmc_ll_fans).setOnClickListener(this);
        rootView.findViewById(R.id.fmc_ll_visitors).setOnClickListener(this);

        //设置菜单打开的监听器
        mainActivity.setMenuOpenedListener(onOpenedListener);
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
        mTab = (TabLayout) rootView.findViewById(R.id.fmc_tab);
        mViewPager = (ViewPager) rootView.findViewById(R.id.fmc_vp);

        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());

        mViewPager.setAdapter(new ChatAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fmc_ll_watched:
                showAttentionView();
                break;
            case R.id.fmc_ll_fans:
                showFansView();
                break;
            case R.id.fmc_ll_visitors:
                showVisitorView();
                break;
        }
    }

    /**
     * 右滑菜单打开时候的监听器
     */
    private SlidingMenu.OnOpenedListener onOpenedListener = new SlidingMenu.OnOpenedListener() {
        @Override
        public void onOpened() {
            mPresenter.start();//初始化数据
        }
    };

    @Override
    public void setVisitorCount(int count) {
        ((TextView) rootView.findViewById(R.id.fmc_tv_visitorsNum)).setText(count + "");
    }

    @Override
    public void setAttentionCount(int count) {
        ((TextView) rootView.findViewById(R.id.fmc_tv_watchedNum)).setText(count + "");
    }

    @Override
    public void setFansCount(int count) {
        ((TextView) rootView.findViewById(R.id.fmc_tv_fansNum)).setText(count + "");
    }

    @Override
    public UserState.UsersChangedListener onUserDataChangedListener() {
        return usersChangedListener;
    }

    @Override
    public void showAttentionView() {
        String action = ClassUtils.getClassName(AttentionFragment.class);
        int user_id = mPresenter.getUser_id();
        mainActivity.startUsersListActivity(user_id, action);
    }

    @Override
    public void showFansView() {
        String action = ClassUtils.getClassName(FansFragment.class);
        int user_id = mPresenter.getUser_id();
        mainActivity.startUsersListActivity(user_id, action);
    }

    @Override
    public void showVisitorView() {
        String action = ClassUtils.getClassName(VisitorFragment.class);
        int user_id = mPresenter.getUser_id();
        mainActivity.startUsersListActivity(user_id, action);
    }

    @Override
    public void setPresenter(RightMenuContract.Presenter presenter) {
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
        mainActivity.showReqResult(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity = null;
    }

    /**
     * 右滑数据变化时候的监听器
     */
    private UserState.UsersChangedListener usersChangedListener = new UserState.UsersChangedListener() {
        @Override
        public void onUserDataChanged() {
            mPresenter.getCountData();
        }
    };
}
