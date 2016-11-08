package com.jkb.core.contract.dynamicCreate.article;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.dynamicCreate.data.CategoryTypeData;
import com.jkb.core.contract.dynamicCreate.data.DynamicCreateArticleData;

import java.util.List;

/**
 * 创建文章动态的页面控制器
 * Created by JustKiddingBaby on 2016/9/27.
 */

public interface DynamicCreateArticleContract {

    interface View extends BaseView<Presenter> {
        /**
         * 得到圈子id
         *
         * @return 圈子id
         */
        int getCircleId();

        /**
         * 发布成功后执行的动作
         */
        void postSuccess();

        /**
         * 设置文章数据
         *
         * @param articleData 文章数据
         */
        void setArticleData(List<DynamicCreateArticleData> articleData);


        /**
         * 显示标签分类的视图
         *
         * @param categoryTypeDatas 标签分类数据
         */
        void showCategoryTypeView(List<CategoryTypeData> categoryTypeDatas);

        /**
         * 发送话题动态
         */
        void postArticleDynamic();

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
         * 绑定数据到视图中
         */
        void bindDataToView();

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
         * 发送话题动态
         *
         * @param title       标题栏
         * @param articleData 文章内容
         * @param tag         标签
         */
        void postArticleDynamic(String title, List<DynamicCreateArticleData> articleData, String tag);

        /**
         * 得到所有的标签类型
         */
        void getAllTag();
    }
}
