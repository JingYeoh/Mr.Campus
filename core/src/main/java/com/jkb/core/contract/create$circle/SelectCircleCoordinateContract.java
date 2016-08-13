package com.jkb.core.contract.create$circle;

import android.graphics.Bitmap;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.model.info.LocationInfoSingleton;

/**
 * 选择圈子坐标点的页面协议类
 * Created by JustKiddingBaby on 2016/8/11.
 */

public interface SelectCircleCoordinateContract {

    interface View extends BaseView<Presenter> {

        /**
         * 关闭页面
         */
        void close();

        /**
         * 地图移动中
         */
        void mapMoving();

        /**
         * 地图移动完成
         */
        void mapMoveComplated();

        /**
         * 移动到当前地址
         */
        void moveToLocation();

        /**
         * 移动到坐标点
         *
         * @param longitude 纬度
         * @param latitude  经度
         */
        void moveToCoordinate(double longitude, double latitude);

        /**
         * 设置学校名称
         *
         * @param schoolName 学校名称
         */
        void setSchoolName(String schoolName);

        /**
         * 设置地址名称
         *
         * @param addressName 地址名称
         */
        void setAddressName(String addressName);

        /**
         * 显示确定的地址框视图
         */
        void showDetermineView();

        /**
         * 隐藏确定的地址框视图
         */
        void hideDetermineView();

        /**
         * 确定选择的地点
         */
        void determineSelected();

        /**
         * 设置当前头像定位的Bitmap位图对象
         */
        void setLocationUserHeadImgBitmap(Bitmap bitmap);
    }


    interface Presenter extends BasePresenter {

        /**
         * 移动到当前地址
         */
        void moveToLocation();

        /**
         * 移动到学校中心点
         */
        void moveToSchoolCenterCoordinate();

        /**
         * 请求位置信息
         */
        void getGeocode(double lantitude, double longitude);
    }
}
