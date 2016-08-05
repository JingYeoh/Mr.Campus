package com.jkb.mrcampus.fragment.entering;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 菌菌协议的页面
 * Created by JustKiddingBaby on 2016/8/1.
 */
public class MrCampusAgreementFragment extends BaseFragment {

    public MrCampusAgreementFragment() {
    }

    /**
     * 获得一个实例化的MrCampusAgreementFragment对象
     *
     * @return
     */
    public static MrCampusAgreementFragment newInstance() {
        return new MrCampusAgreementFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_entering_mrcampus_agreement);
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
