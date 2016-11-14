package com.jkb.mrcampus.fragment.function.map.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MapActivity;
import com.jkb.mrcampus.adapter.fragmentPager.MapListAdapter;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 用来承载附近的人和附近的用户的载体
 * Created by JustKiddingBaby on 2016/11/14.
 */

public class MapListFragment2 extends BaseFragment implements View.OnClickListener {

    public static MapListFragment2 newInstance() {
        Bundle args = new Bundle();
        MapListFragment2 fragment = new MapListFragment2();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private MapActivity mapActivity;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mapActivity = (MapActivity) mActivity;
        setRootView(R.layout.frg_map_list2);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("附近");
        initTabView();
    }

    /**
     * 初始化TabView
     */
    private void initTabView() {
        TabLayout mTab = (TabLayout) rootView.findViewById(R.id.fml_tab);
        ViewPager mViewPager = (ViewPager) rootView.findViewById(R.id.fml_vp);
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        mViewPager.setAdapter(new MapListAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts4_ib_left:
                mapActivity.onBackPressed();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapActivity = null;
    }
}
