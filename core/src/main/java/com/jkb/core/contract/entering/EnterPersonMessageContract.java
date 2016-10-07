package com.jkb.core.contract.entering;

import android.graphics.Bitmap;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 个人信息录入页面的页面协议类
 * Created by JustKiddingBaby on 2016/8/1.
 */
public interface EnterPersonMessageContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置头像
         */
        void setHeadImg(String headImg);

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
         * 注册
         */
        void register();

        /**
         * 登录进入系统
         */
        void loginSystem();

        /**
         * 清楚用户输入的数据
         */
        void clearUserInput();
    }

    interface Presenter extends BasePresenter {

        /**
         * 请求注册接口
         *
         * @param identifyCode 验证码
         * @param nickName     昵称
         * @param passWord     密码
         * @param identifier   帐号
         */
        void register(String identifyCode, String nickName, String passWord, String identifier);

        /**
         * 请求上传个人头像接口
         *
         * @param headImgPath
         */
        void sendReqToUpLoadHeadImg(String headImgPath);

        /**
         * 保存头像为文件
         *
         * @param bitmap 位图对象
         * @param name   图像名称
         */
        void saveHeadImgToFile(Bitmap bitmap, String name);

        /**
         * 设置头像路径
         *
         * @param imagePath 头像图片路径
         */
        void setHeadImagePath(String imagePath);
    }
}
