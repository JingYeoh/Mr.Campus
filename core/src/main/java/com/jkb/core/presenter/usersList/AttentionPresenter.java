package com.jkb.core.presenter.usersList;

import com.jkb.core.contract.usersList.AttentionContract;
import com.jkb.model.dataSource.usersList.attention.AttentionDataResponsitory;

/**
 * 关注用户列表的P层
 * Created by JustKiddingBaby on 2016/8/17.
 */

public class AttentionPresenter implements AttentionContract.Presenter {

    private AttentionContract.View view;
    private AttentionDataResponsitory responsitory;

    public AttentionPresenter(AttentionContract.View view, AttentionDataResponsitory responsitory) {
        this.view = view;
        this.responsitory = responsitory;

        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        //请求数据
    }

    @Override
    public void onUserClicked(int position) {

    }
}
