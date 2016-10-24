package com.jkb.mrcampus.fragment.first;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 用于广告显示的Fragment
 * Created by JustKiddingBaby on 2016/7/21.
 */
public class AdventFragment extends BaseFragment {

    public AdventFragment() {
    }

    /**
     * 获得一个实例化的AdventFragment对象
     */
    public static AdventFragment newInstance() {
        return new AdventFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.aty_first);
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
