package com.jkb.core.presenter.personCenter;

import com.jkb.core.contract.personCenter.PersonSettingContract;

/**
 * 个人设置的P层
 * Created by JustKiddingBaby on 2016/8/23.
 */

public class PersonSettingPresenter implements PersonSettingContract.Presenter {

    private PersonSettingContract.View view;

    public PersonSettingPresenter(PersonSettingContract.View view) {
        this.view = view;

        this.view.setPresenter(this);
    }

    @Override
    public void initInformation() {

    }

    @Override
    public void start() {
        initInformation();//初始化个人信息
    }
}
