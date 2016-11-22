package com.jkb.mrcampus.fragment.function.special.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.SpecialDetailActivity;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 专题详情：跳蚤市场
 * Created by JustKiddingBaby on 2016/11/20.
 */

public class SpecialDetailFleaMarketFragment extends BaseFragment {

    public static SpecialDetailFleaMarketFragment newInstance(int dynamicId) {
        Bundle args = new Bundle();
        args.putInt(Config.INTENT_KEY_DYNAMIC_ID, dynamicId);
        SpecialDetailFleaMarketFragment fragment = new SpecialDetailFleaMarketFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private int dynamicId = -1;
    private SpecialDetailActivity specialDetailActivity;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        specialDetailActivity = (SpecialDetailActivity) mActivity;
        super.onCreate(savedInstanceState);
        setRootView(R.layout.frg_special_detail_fleamarket);
        init(savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            dynamicId = args.getInt(Config.INTENT_KEY_DYNAMIC_ID);
        } else {
            dynamicId = savedInstanceState.getInt(Config.INTENT_KEY_DYNAMIC_ID);
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        specialDetailActivity = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_DYNAMIC_ID, dynamicId);
    }
}
