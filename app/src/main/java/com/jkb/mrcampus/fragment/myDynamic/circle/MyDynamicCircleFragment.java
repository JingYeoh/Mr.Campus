package com.jkb.mrcampus.fragment.myDynamic.circle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 我的动态：圈子View层
 * Created by JustKiddingBaby on 2016/10/13.
 */

public class MyDynamicCircleFragment extends BaseFragment {

    //用户id
    private int user_id=-1;

    public MyDynamicCircleFragment() {
    }

    public MyDynamicCircleFragment(int user_id) {
        this.user_id = user_id;
    }

    private static MyDynamicCircleFragment INSTANCE = null;

    public static MyDynamicCircleFragment newInstance(@NonNull int user_id) {
        if (INSTANCE == null) {
            INSTANCE = new MyDynamicCircleFragment(user_id);
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(1);
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
