package com.jkb.core.contract.personCenter;

import android.graphics.Bitmap;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 个人设置的页面协议类
 * Created by JustKiddingBaby on 2016/8/23.
 */

public interface PersonSettingContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置头像
         */
        void setHeadImg(Bitmap bitmap);

        /**
         * 设置名称
         */
        void setName(String name);

        /**
         * 设置性别
         */
        void setSex(String sex);

        /**
         * 设置昵称
         */
        void setNickName(String nickName);

        /**
         * 设置简介
         */
        void setBref_introduction(String bref_introduction);

        /**
         * 设置背景
         */
        void setBackGround(Bitmap bitmap);

        /**
         * 设置手机号
         */
        void setPhone(String phone);

        /**
         * 设置邮箱
         */
        void setEmail(String email);

        /**
         * 设置ID
         */
        void setID(String ID);

        /**
         * 设置注册时间
         */
        void setRegisterTime(String registerTime);

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
        void showInputTextView();
    }

    interface Presenter extends BasePresenter {
        /**
         * 初始化信息
         */
        void initInformation();
    }
}
