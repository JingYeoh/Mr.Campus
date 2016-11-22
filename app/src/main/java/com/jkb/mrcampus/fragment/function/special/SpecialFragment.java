package com.jkb.mrcampus.fragment.function.special;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.MainActivity;
import com.jkb.mrcampus.activity.SpecialCreateActivity;
import com.jkb.mrcampus.adapter.fragmentPager.SpecialAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.WriteSpecialDialogFragment;

/**
 * 专题的页面View层
 * Created by JustKiddingBaby on 2016/10/11.
 */

public class SpecialFragment extends BaseFragment implements View.OnClickListener {

    public static SpecialFragment newInstance() {
        return new SpecialFragment();
    }

    //data
    private MainActivity mainActivity;

    //view
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainActivity = (MainActivity) mActivity;
        setRootView(R.layout.frg_funxtion_subject);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ffs_iv_floatBt_top).setOnClickListener(this);
        rootView.findViewById(R.id.ffs_iv_floatBt).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        rootView.findViewById(R.id.fi_title_left).setOnClickListener(this);
        rootView.findViewById(R.id.fi_title_right).setVisibility(View.GONE);

        initTabView();//初始化滑动
    }

    /**
     * 初始化TabView
     */
    private void initTabView() {
        TabLayout mTab = (TabLayout) rootView.findViewById(R.id.fi_tab);
        viewPager = (ViewPager) rootView.findViewById(R.id.ffs_vp);
        mTab.addTab(mTab.newTab());
        mTab.addTab(mTab.newTab());
        viewPager.setAdapter(new SpecialAdapter(getChildFragmentManager()));
        mTab.setupWithViewPager(viewPager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainActivity = null;
        viewPager = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fi_title_left://返回
                mainActivity.showLeftMenuView();
                break;
            case R.id.ffs_iv_floatBt://发表
                mainActivity.showWriteSpecialDynamicFloatView(onWriteSpecialItemClickListener);
                break;
            case R.id.ffs_iv_floatBt_top://返回顶部
                break;
        }
    }

    /**
     * 发布专题动态的回调方法
     */
    private WriteSpecialDialogFragment.OnWriteSpecialItemClickListener
            onWriteSpecialItemClickListener =
            new WriteSpecialDialogFragment.OnWriteSpecialItemClickListener() {
                @Override
                public void onClosed() {
                    mainActivity.dismiss();
                }

                @Override
                public void onConfessionClick() {
                    mainActivity.startSpecialCreate(
                            SpecialCreateActivity.SUBJECT_CREATE_TYPE_CONFESSION);
                }

                @Override
                public void onFleaMarketClick() {
                    mainActivity.startSpecialCreate(
                            SpecialCreateActivity.SUBJECT_CREATE_TYPE_FLEAMARKET);
                }

                @Override
                public void onFoundPartnerClick() {
                    mainActivity.startSpecialCreate(
                            SpecialCreateActivity.SUBJECT_CREATE_TYPE_WANTED_PARTNER);
                }

                @Override
                public void onLost$FoundClick() {
                    mainActivity.startSpecialCreate(
                            SpecialCreateActivity.SUBJECT_CREATE_TYPE_LOSTANDFOUND);
                }

                @Override
                public void onQuestionClick() {
                    mainActivity.startSpecialCreate(
                            SpecialCreateActivity.SUBJECT_CREATE_TYPE_WANTED_SAVANT);
                }

                @Override
                public void onTauntedClick() {
                    mainActivity.startSpecialCreate(
                            SpecialCreateActivity.SUBJECT_CREATE_TYPE_TAUNTED);
                }
            };
}
