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

        /**
         * 通过照相机选取图片
         */
        void choosePictureFromCamera();

        /**
         * 通过相册选取图片
         */
        void choosePictureFromAlbum();

        /**
         * 创建圈子
         */
        void createCircle();
    }

    interface Presenter extends BasePresenter {

        /**
         * 创建圈子
         *
         * @param school_id
         * @param name
         * @param introduction
         * @param longitude
         * @param latitude
         * @param imagePath
         */
        void createCircle(
                int school_id, String name,
                String introduction, double longitude, double latitude,
                String imagePath
        );
    }
}
