package com.jkb.core.contract.menu;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.control.userstate.UserState;

/**
 * 左滑菜单的页面协议类
 * Created by JustKiddingBaby on 2016/7/24.
 */
public interface SwitchFunctionContract {

    /**
     * 左滑菜单的View层接口
     */
    interface View extends BaseView<Presenter> {

        /**
         * 展示学校的视图
         */
        void showSchoolView(String schoolName, String schoolBadge, String summary);

        /**
         * 隐藏学校的视图
         */
        void hideSchoolView();

        /**
         * 个人中心视图的监听器
         *
         * @return
         */
        UserState.MenuPersonViewListener onPersonViewListener();

        /**
         * 返回个人中心的View对象
         *
         * @return
         */
        android.view.View getPersonView();

        /**
         * 打开圈子列表页面
         *
         * @param user_id 用户id
         */
        void startCircleList(@NonNull int user_id);
    }

    /**
     * 左滑菜单的Presenter层接口
     */
    interface Presenter extends BasePresenter {

        /**
         * 是否登录
         *
         * @return
         */
        boolean isLogined();

        /**
         * 得到当前学校信息
         */
        void getCurrentSchool();

        /**
         * 得到个人信息
         */
        void getPersonalData();

        /**
         * 个人中心视图的监听器
         *
         * @param listener
         */
        void setOnPersonViewListener(UserState.MenuPersonViewListener listener);

        /**
         * 得到当前的昵称
         */
        String getCurrentNickName();

        /**
         * 得到当前的头像
         */
        String getCurrentHeadImg();

        /**
         * 得到用户id
         *
         * @return 得到用户的id
         */
        int getUser_id();

        /**
         * 当圈子列表被点击的时候
         */
        void onCircleListClick();
    }
}
