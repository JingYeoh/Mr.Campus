package com.jkb.mrcampus.fragment.dynamicDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

/**
 * 文章的动态类型
 * Created by JustKiddingBaby on 2016/9/14.
 */

public class ArticleDynamicFragment extends BaseFragment {

    //data
    private int dynamic_id = -1;

    public ArticleDynamicFragment() {
    }

    public ArticleDynamicFragment(int dynamic_id) {
        this.dynamic_id = dynamic_id;
    }

    private static ArticleDynamicFragment INSTANCE = null;

    public static ArticleDynamicFragment newInstance(@NonNull int dynamic_id) {
        if (INSTANCE == null || dynamic_id != -1) {
            INSTANCE = new ArticleDynamicFragment(dynamic_id);
        }
        return INSTANCE;
    }

    //data
    private static final String SAVED_DYNAMIC_ID = "saved_dynamic_id";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setRootView(R.layout.frg_dynamic_detail_article);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {

        } else {
            dynamic_id = savedInstanceState.getInt(SAVED_DYNAMIC_ID);//恢复数据
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_DYNAMIC_ID, dynamic_id);
    }
}
