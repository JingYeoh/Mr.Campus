package com.jkb.core.contract.first;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * FirstActivity的页面协议类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public interface FirstContract {


    interface View extends BaseView<Presenter> {
        /**
         * 显示Fragment
         *
         * @param position
         */
        void showFragment(int position);

        /**
         * 显示引导页面
         */
        void showGuide();

        /**
         * 显示广告页面
         */
        void showAdvent();

        /**
         * 显示欢迎页面
         */
        void showWelcome();

        /**
         * 开始到主程序
         */
        void startMenuActivity();

        /**
         * finish当前
         */
        void finishCurrent();
    }

    interface Presenter extends BasePresenter {
        /**
         * 选择合适的Fragment
         */
        void chooseFragment();
    }
}
