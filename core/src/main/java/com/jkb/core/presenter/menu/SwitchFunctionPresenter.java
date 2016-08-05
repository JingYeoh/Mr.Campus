package com.jkb.core.presenter.menu;

import android.support.annotation.NonNull;

import com.jkb.core.contract.menu.MenuContract;
import com.jkb.core.contract.menu.SwitchFunctionContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.UserState;

/**
 * Created by JustKiddingBaby on 2016/7/24.
 */
public class SwitchFunctionPresenter implements SwitchFunctionContract.Presenter {


    private SwitchFunctionContract.View functionView;//左滑菜单的视图
    private MenuContract.View menuView;//菜單总体控制的视图

    /**
     * 得到Presenter对象
     *
     * @param functionView
     */
    public SwitchFunctionPresenter(@NonNull MenuContract.View menuView, @NonNull SwitchFunctionContract.View functionView) {
        this.functionView = functionView;
        this.menuView = menuView;
        //向View层中注入Presenter层对象
        this.functionView.setPresenter(this);
    }

    @Override
    public void start() {
        getCurrentSchool();
        getPersonalData();
        //设置个人信息头像
        setOnPersonViewListener(functionView.onPersonViewListener());

        //设置当前学校视图

    }

    @Override
    public boolean isLogined() {
        return false;
    }

    @Override
    public void getCurrentSchool() {

    }

    @Override
    public void getPersonalData() {

    }

    @Override
    public void setOnPersonViewListener(UserState.MenuPersonViewListener listener) {
        LoginContext loginContext = LoginContext.getInstance();
        loginContext.setOnMenuPersonViewListener(functionView.getPersonView(), listener);
    }

}
