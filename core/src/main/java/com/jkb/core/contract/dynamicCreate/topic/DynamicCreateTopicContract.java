package com.jkb.core.contract.dynamicCreate.topic;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.dynamicCreate.data.CategoryTypeData;

import java.util.List;

/**
 * 动态创建：话题页面协议类
 * Created by JustKiddingBaby on 2016/9/26.
 */

public interface DynamicCreateTopicContract {


    interface View extends BaseView<Presenter> {

        /**
         * 发布成功后执行的动作
         */
        void postSuccess();


        /**
         * 显示标签分类的视图
         *
         * @param categoryTypeDatas 标签分类数据
         */
        void showCategoryTypeView(List<CategoryTypeData> categoryTypeDatas);

        /**
         * 设置图片
         */
        void setPicture(String picture);

        /**
         * 发送话题动态
         */
        void postTopicDynamic();

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
    }

    interface Presenter extends BasePresenter {
        /**
         * 上传图片
         *
         * @param imgPath 图片本地路径
         */
        void uploadImage(String imgPath);

        /**
         * 发送话题动态
         *
         * @param title   标题栏
         * @param content 内容
         * @param tag     标签
         */
        void postTopicDynamic(String title, String content, String tag);

        /**
         * 得到所有的标签类型
         */
        void getAllTag();
    }
}
