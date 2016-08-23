package com.jkb.core.presenter.function.homepage;

import com.jkb.core.contract.function.homepage.DynamicContract;

/**
 * 动态列表的P层
 * Created by JustKiddingBaby on 2016/8/23.
 */

public class DynamicPresenter implements DynamicContract.Presenter {

    private DynamicContract.View view;

    public DynamicPresenter(DynamicContract.View view) {
        this.view = view;

        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
