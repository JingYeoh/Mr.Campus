package com.jkb.core.contract.create$circle;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 录入圈子信息的页面协议类
 * Created by JustKiddingBaby on 2016/8/11.
 */

public interface EnteringCircleMessageContract {

    interface View extends BaseView<Presenter> {

        /**
         * 选择学校
         */
        void chooseSchool();

        /**
         * 选择背景图片
         */
        void chooseBackground();

        /**
         * 选择头像
         */
        void chooseHeadImg();

        /**
         * 选择地址
         */
        void chooseCoordinate();

        /**
         * 显示获取图片的方式的视图
         */
        void showSelectWayOfChoosePhotoView();
    }

    interface Presenter extends BasePresenter {

    }
}
