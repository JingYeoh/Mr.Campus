package com.jkb.core.contract.map;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * MapActivity的页面协议类
 * Created by JustKiddingBaby on 2016/7/28.
 */
public interface MapAtyContract {


    interface View extends BaseView<Presenter> {

        /**
         * 显示地图视图
         */
        void showMapView();

        /**
         * 显示列表视图
         */
        void showListView();

        /**
         * 返回到上级，隐藏自身页面
         */
        void hideCurrent();

        /**
         * 显示Fragment
         * 0表示Map视图
         * 1表示列表视图
         *
         * @param position
         */
        void showFragment(int position);
    }

    interface Presenter extends BasePresenter {

        /**
         * 选择合适的Fragment
         * 0表示Map视图
         * 1表示列表视图
         */
        void chooseFragment(int position);
    }
}
