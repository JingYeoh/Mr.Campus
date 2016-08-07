package com.jkb.core.contract.map;

import android.graphics.Bitmap;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * MapFragment的页面协议类
 * Created by JustKiddingBaby on 2016/7/26.
 */
public interface MapContract {

    interface View extends BaseView<Presenter> {

        /**
         * 得到地图的截图
         */
        void getMapSnapShot();

        /**
         * 显示地图截图：在侧滑时候调用
         * 为了解决滑动黑边问题：现已过期
         *
         * @param bitmap
         */
        @Deprecated
        void showMapSnapShort(Bitmap bitmap);

        /**
         * 隐藏地图截图：在侧滑完成后调用
         * 为了解决滑动黑边问题：现已过期
         */
        @Deprecated
        void hideMapSnapShort();

        /**
         * 选择学校
         */
        void chooseSchool();

        /**
         * 定位到当前
         */
        void location();

        /**
         * 位置暴露开关
         */
        void locationSwitch();

        /**
         * 附近的人开关
         */
        void nearUserSwitch();

        /**
         * 筛选性别
         */
        void filterSex();

        /**
         * 判断该页面是否活动
         *
         * @return
         */
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        /**
         * 得到地图截图
         *
         * @return
         */
        void setMapSnapShort(Bitmap bitmap);

        /**
         * 销毁地图截图的Bitmap缓存对象
         */
        void recycleSnapShort();

        /**
         * 得到地图截图
         *
         * @return
         */
        Bitmap getMapSnapShort();
    }
}
