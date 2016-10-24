package com.jkb.mrcampus.fragment.function.special;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 专题的页面View层
 * Created by JustKiddingBaby on 2016/10/11.
 */

public class SubjectFragment extends BaseFragment {

    public SubjectFragment() {
    }

    private static SubjectFragment INSTANCE = null;

    public static SubjectFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SubjectFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_funxtion_subject);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
