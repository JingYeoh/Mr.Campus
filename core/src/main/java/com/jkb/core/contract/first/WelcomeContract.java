package com.jkb.core.contract.first;

import android.graphics.Bitmap;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * Welcome页面的协议类
 * Created by JustKiddingBaby on 2016/7/21.
 */
public interface WelcomeContract {


    interface View extends BaseView<Presenter> {

        /**
         * 开始倒计时
         */
        void startCount();

        /**
         * 停止倒计时
         */
        void stopCount();

        /**
         * 完成倒计时
         */
        void completedCount();

        /**
         * 显示背景图片
         *
         * @param bitmap
         */
        void showBackGround(Bitmap bitmap);

        /**
         * 是否被销毁
         *
         * @return
         */
        boolean isActive();
    }


    interface Presenter extends BasePresenter {

        /**
         * 设置背景图片
         */
        void setBackgroundBitmap(Bitmap bitmap);

        /**
         * 选择图片来源
         */
        void chooseBitmapResource();

//        /**
//         * 得到本地的图片
//         *
//         * @param resourceId
//         */
//        Bitmap getBitmapByNative(int resourceId);
//
//        /**
//         * 得到缓存手机文件图片
//         *
//         * @param path
//         * @return
//         */
//        Bitmap getBitmapByCacheFile(String path);

    }

}
