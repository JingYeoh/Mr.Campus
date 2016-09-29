package com.jkb.core.contract.school;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.contract.school.data.SchoolData;

import java.util.List;

/**
 * 选择学校的页面协议类
 * Created by JustKiddingBaby on 2016/9/29.
 */

public interface SelectSchoolContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置学校数据
         */
        void setSchoolData(List<SchoolData> schoolData);

        /**
         * 刷新数据
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 显示正在加载的视图
         */
        void showLoadingView();

        /**
         * 隐藏正在加载的视图
         */
        void hideLoadingView();
    }

    interface Presenter extends BasePresenter {

        /**
         * 初始化学校数据
         */
        void initSchoolData();

        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 选择学校
         */
        void onSchoolSelected(int position);
    }
}
