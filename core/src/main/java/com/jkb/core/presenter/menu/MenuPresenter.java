package com.jkb.core.presenter.menu;

import android.support.annotation.NonNull;

import com.jkb.core.contract.menu.MenuContract;

import java.io.Serializable;

/**
 * Created by JustKiddingBaby on 2016/7/24.
 */
public class MenuPresenter implements MenuContract.Presenter {

    private MenuContract.View menuView;

    /**
     * 展示的视图
     */
    public static enum SHOW_VIEW implements Serializable {
        /**
         * 首页
         */
        HOMEPAGE,
        /**
         * 设置
         */
        SETTING,
        /**
         * 工具
         */
        TOOLS,
        /**
         * 专题
         */
        FEATURE,
        /**
         * 地图
         */
        MAP;
    }

    /**
     * 展示的视图
     */
    private SHOW_VIEW currentView = SHOW_VIEW.HOMEPAGE;

    public SHOW_VIEW getCurrentView() {
        return currentView;
    }

    public void setCurrentView(SHOW_VIEW currentView) {
        this.currentView = currentView;
//        showFragment();
    }

    /**
     * 初始化Presenter
     *
     * @param menuView
     */
    public MenuPresenter(@NonNull MenuContract.View menuView) {
        this.menuView = menuView;
    }

    @Override
    public boolean getIdentity() {

        return false;
    }

    @Override
    public void start() {
//        showFragment();
    }

    /**
     * 展示视图
     */
    private void showFragment() {
        if (currentView.equals(SHOW_VIEW.HOMEPAGE)) {
            menuView.showIndex();
        } else if (currentView.equals(SHOW_VIEW.FEATURE)) {
            menuView.showSpecialModel();
        } else if (currentView.equals(SHOW_VIEW.SETTING)) {
            menuView.showSetting();
        } else if (currentView.equals(SHOW_VIEW.TOOLS)) {
            menuView.showTools();
        }
    }


}
