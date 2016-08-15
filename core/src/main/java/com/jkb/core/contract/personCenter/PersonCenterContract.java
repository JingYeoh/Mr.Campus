package com.jkb.core.contract.personCenter;

import android.graphics.Bitmap;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 个人中心的页面控制器
 * Created by JustKiddingBaby on 2016/8/15.
 */

public interface PersonCenterContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置头像
         */
        void setHeadImg(Bitmap headImg);

        /**
         * 设置昵称
         */
        void setUserName(String userName);

        /**
         * 设置名称
         */
        void setName(String name);

        /**
         * 设置用户签名
         */
        void setUserSign(String userSign);

        /**
         * 设置关注的数目
         */
        void setWatchedNum(int watched);

        /**
         * 设置粉丝的数目
         */
        void setFansNum(int fans);

        /**
         * 设置访客的数目
         */
        void setVistiorsNum(int visitors);
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化用户数据
         */
        void initUserData();
    }
}
