package com.jkb.core.presenter.create$circle;

import com.jkb.core.contract.create$circle.EnteringCircleMessageContract;

/**
 * 录入圈子信息的Presenter层
 * Created by JustKiddingBaby on 2016/8/11.
 */

public class EnteringCircleMessagePresenter implements EnteringCircleMessageContract.Presenter {

    private EnteringCircleMessageContract.View view;

    public EnteringCircleMessagePresenter(EnteringCircleMessageContract.View view) {
        this.view = view;

        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
