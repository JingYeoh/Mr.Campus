package com.jkb.core.contract.create$circle;

import android.support.annotation.NonNull;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.model.info.SchoolInfoSingleton;

import jkb.mrcampus.db.entity.Schools;

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

        /**
         * 设置学校名称
         *
         * @param schoolName 学校名称
         */
        void setSchoolName(String schoolName);

        /**
         * 创建圈子完成
         *
         * @param circle_id 圈子id
         */
        void createCircleCompleted(@NonNull int circle_id);
    }

    interface Presenter extends BasePresenter {
        /**
         * 创建圈子
         *
         * @param school_id    学校id
         * @param name         名称
         * @param introduction 简介
         * @param tag          标签
         * @param longitude    坐标
         * @param latitude     坐标
         * @param imagePath    图片路径
         */
        void createCircle(
                int school_id, String name,
                String introduction, String tag, double longitude, double latitude,
                String imagePath
        );

        /**
         * 初始化学校信息
         */
        void initSchoolInfo();

        /**
         * 得到学校信息
         *
         * @return 学校信息
         */
        Schools getSchoolInfo();
    }
}
