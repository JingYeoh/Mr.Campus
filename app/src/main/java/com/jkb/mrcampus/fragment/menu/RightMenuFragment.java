package com.jkb.mrcampus.fragment.menu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.adapter.ChatAdapter;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 右滑菜单：聊天页面
 * Created by JustKiddingBaby on 2016/7/24.
 */
public class RightMenuFragment extends BaseFragment implements View.OnClickListener {

    //View层
    private TabLayout mTab;
    private ViewPager mViewPager;

    public RightMenuFragment() {
    }

    /**
     * 获得一个实例化的ChatFragment对象
     *
     * @return
     */
    public static RightMenuFragment newInstance() {
        return new RightMenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_menu_chat);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.fmc_ll_watched).setOnClickListener(this);
        rootView.findViewById(R.id.fmc_ll_fans).setOnClickListener(this);
        rootView.findViewById(R.id.fmc_ll_visitors).setOnClickListener(this);
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
                break;
            case R.id.fmc_ll_fans:
                break;
            case R.id.fmc_ll_visitors:
                break;
        }
    }
}
