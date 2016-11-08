package com.jkb.core.contract.dynamicCreate.normal;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 创建普通动态的页面协议类
 * Created by JustKiddingBaby on 2016/9/27.
 */

public interface DynamicCreateNormalContract {


    interface View extends BaseView<Presenter> {
        /**
         * 得到圈子id
         *
         * @return 圈子id
         */
        int getCircleId();

        /**
         * 设置包含的图片
         *
         * @param imgUrls 图片网址
         */
        void setContentImgs(String[] imgUrls);

        /**
         * 发送话题动态
         */
        void postNormalDynamic();

        /**
         * 发送成功
         */
        void postSuccess();

        /**
         * 显示选取图片的视图
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
    }

    interface Presenter extends BasePresenter {

        /**
         * 替换图片
         *
         * @param position 图片位置
         */
        void replaceImage(int position, String imagePath);

        /**
         * 上传图片
         *
         * @param imgPath 图片本地路径
         */
        void uploadImage(String imgPath);

        /**
         * 发送普通动态
         *
         * @param content 内容
         */
        void postNormalDynamic(String content);

        /**
         * 是否允许添加图片，最多六张
         */
        boolean isAllowAddPicture();
    }
}
