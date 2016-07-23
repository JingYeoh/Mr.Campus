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
    }

    interface Presenter extends BasePresenter {
        /**
         * 选择合适的Fragment
         */
        void chooseFragment();
    }
}
