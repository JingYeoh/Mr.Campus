package com.jkb.mrcampus.fragment.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 访客的圈子设置页面
 * Created by JustKiddingBaby on 2016/10/7.
 */

public class CircleVisitorSettingFragment extends BaseFragment {

    private int circle_id;//圈子id

    private static CircleVisitorSettingFragment INSTANCE = null;

    public static CircleVisitorSettingFragment newInstance(int circle_id) {
        if (INSTANCE == null) {
            INSTANCE = new CircleVisitorSettingFragment();
            Bundle args = new Bundle();
            args.putInt(Config.INTENT_KEY_CIRCLE_ID, circle_id);
            INSTANCE.setArguments(args);
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_circle_setting_visitor);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle arg = getArguments();
            circle_id = arg.getInt(Config.INTENT_KEY_CIRCLE_ID);
        } else {
            circle_id = savedInstanceState.getInt(Config.INTENT_KEY_CIRCLE_ID);
        }
    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("圈子设置");
    }
}
