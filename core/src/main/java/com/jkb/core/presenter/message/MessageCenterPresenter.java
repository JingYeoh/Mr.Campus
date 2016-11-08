package com.jkb.core.presenter.message;

import com.jkb.core.contract.message.MessageCenterContract;

/**
 * 消息中心的P层
 * Created by JustKiddingBaby on 2016/10/10.
 */

public class MessageCenterPresenter implements MessageCenterContract.Presenter {

    private MessageCenterContract.View view;

    public MessageCenterPresenter(MessageCenterContract.View view) {
        this.view = view;

        this.view.setPresenter(this);
    }

    @Override
    public void initUserId() {

    }

    @Override
    public void initMessageData() {
        initUserId();
        onRefresh();
    }

    @Override
    public void onRefresh() {
        if(!view.isActive()){
            return;
        }
        view.initMessages();
    }

    @Override
    public void bindDataToView() {

    }

    @Override
    public void start() {
        initMessageData();
    }
}
