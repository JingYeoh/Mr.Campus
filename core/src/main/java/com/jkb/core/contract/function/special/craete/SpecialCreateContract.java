package com.jkb.core.contract.function.special.craete;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

import java.util.List;

/**
 * 创建专题的页面协议类
 * Created by JustKiddingBaby on 2016/11/21.
 */

public interface SpecialCreateContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置要上传的图片
         */
        void setUploadPictures(List<String> uploadPictures);

        /**
         * 显示图片选择视图
         */
        void showPictureSelectorView();

        /**
         * 通过照相机选取图片
         */
        void choosePictureFromCamera();

        /**
         * 通过相册选取图片
         */
        void choosePictureFromAlbum();

        /**
         * 发布专题动态
         */
        void postSubjectDynamic();

        /**
         * 添加图片被点击的时候
         */
        void onAddPictureClick();

        /**
         * 发布成功
         */
        void postSuccess();
    }

    interface Presenter extends BasePresenter {

        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 添加图片
         */
        void addPicture(String picture);

        /**
         * 删除图片
         */
        void deletePicture(int position);

        /**
         * 替换图片
         */
        void replacePicture(int position, String picture);

        /**
         * 发布表白墙专题动态
         */
        void postSubjectConfession(String title, String content);

        /**
         * 发布吐槽墙专题动态
         */
        void postSubjectTaunted(String title, String content);

        /**
         * 发布失物招领专题动态
         */
        void postSubjectLostAndFound(String title, String content);

        /**
         * 发布跳蚤市场专题动态
         */
        void postSubjectFleaMarket(String title, String content);

        /**
         * 发布求学霸专题动态
         */
        void postSubjectWantedSavant(String title, String content);

        /**
         * 发布寻水友专题动态
         */
        void postSubjectWantedPartner(String title, String content);
    }
}
