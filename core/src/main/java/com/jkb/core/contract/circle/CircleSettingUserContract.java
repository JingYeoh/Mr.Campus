package com.jkb.core.contract.circle;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 用户的圈子设置页面协议类
 * Created by JustKiddingBaby on 2016/10/30.
 */

public interface CircleSettingUserContract {

    interface View extends BaseView<Presenter> {

        /**
         * 得到圈子id
         */
        int getCircleId();

        /**
         * 设置圈子图片
         */
        void setCirclePicture(String circlePicture);

        /**
         * 设置圈子名称
         */
        void setCircleName(String circleName);

        /**
         * 设置圈子简介
         */
        void setCircleBref(String circleBref);

        /**
         * 设置圈子标签
         */
        void setCircleTag(String circleTag);

        /**
         * 显示选取头像的视图
         */
        void showChoosePictureView();

        /**
         * 通过照相机选取图片
         */
        void choosePictureFromCamera();

        /**
         * 通过相册选取图片
         */
        void choosePictureFromAlbum();

        /**
         * 显示输入文字的View
         */
        void showInputTextView(int type);

        /**
         * 修改输入的内容
         *
         * @param type  類型
         * @param value 数据
         */
        void updateInputText(int type, String value);
    }

    interface Presenter extends BasePresenter {
        /**
         * 绑定信息
         */
        void bindDataToView();

        /**
         * 绑定圈子id
         */
        void initCircleId();

        /**
         * 初始化圈子数据
         */
        void initCircleData();

        /**
         * 更新圈子图片
         */
        void updateCirclePicture(String circlePicture);

        /**
         * 更新圈子名称
         */
        void updateCircleName(String circleName);

        /**
         * 更新圈子简介
         */
        void updateCircleBref(String circleBref);

        /**
         * 更新圈子标签
         */
        void updateCircleTag(String circleTag);
    }
}
