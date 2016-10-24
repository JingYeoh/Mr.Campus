package com.jkb.mrcampus.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 引导页的Fragment：用于更新或者安装时候的引导应用
 * Created by JustKiddingBaby on 2016/7/21.
 */
public class GuideFragment extends BaseFragment {


    public GuideFragment() {
    }

    /**
     * 获得一个实例化的GuideFragment对象
     */
    public static GuideFragment newInstance() {
        return new GuideFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_first_guide);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

    }
}
