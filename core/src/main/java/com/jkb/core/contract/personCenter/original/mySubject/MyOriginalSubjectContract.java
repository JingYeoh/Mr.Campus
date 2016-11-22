package com.jkb.core.contract.personCenter.original.mySubject;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.special.SpecialData;

import java.util.List;

/**
 * 我的原创专题的页面协议类
 * Created by JustKiddingBaby on 2016/11/22.
 */

public interface MyOriginalSubjectContract {

    interface View extends BaseView<Presenter> {

        /**
         * 滑动到顶部
         */
        void scrollToTop();

        /**
         * 得到专题类型
         */
        int getSubjectType();

        /**
         * 设置专题的标题栏
         */
        void setSubjectTitle(String subjectTitle);

        /**
         * 设置专题数据
         */
        void setSubjectData(List<SpecialData> subjectData);

        /**
         * 显示刷新视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新视图
         */
        void hideRefreshingView();

        /**
         * 显示是否删除的提示视图
         */
        void showDeletedHintSelectorView(OnDeleteSelectorItemClickListener listener);

        /**
         * 打开评论列表页面
         *
         * @param dynamicId 动态id
         */
        void startCommentList(int dynamicId);

        /**
         * 打开专题详情：表白墙
         */
        void startSpecialDetailConfession(int dynamicId);

        /**
         * 打开专题详情：吐槽强
         */
        void startSpecialDetailTaunted(int dynamicId);

        /**
         * 打开专题详情：跳蚤市场
         */
        void startSpecialDetailFleaMarket(int dynamicId);

        /**
         * 打开专题详情：失物招领
         */
        void startSpecialDetailLostAndFound(int dynamicId);

        /**
         * 打开专题详情：求学霸
         */
        void startSpecialDetailWantedSavant(int dynamicId);

        /**
         * 打开专题详情：寻水友
         */
        void startSpecialDetailWantedPartner(int dynamicId);
    }

    interface Presenter extends BasePresenter {
        //常量
        int SUBJECY_TYPE_CONFESSION = 1001;
        int SUBJECT_TYPE_TAUNTED = 1002;
        int SUBJECT_TYPE_LOSTANDFOUND = 1003;
        int SUBJECT_TYPE_FLEAMARKET = 1004;
        int SUBJECT_TYPE_WANTED_SAVANT = 1005;
        int SUBJECT_TYPE_WANTED_PARTNER = 1006;

        /**
         * 绑定页面数据
         */
        void bindDataToView();

        /**
         * 初始化专题数据
         */
        void initSubjectData();

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载
         */
        void onLoadMore();

        /**
         * 删除条目点击事件
         */
        void onDeleteItemClick(int position);

        /**
         * 条目点击事件
         */
        void onItemClick(int position);

        /**
         * 喜欢的条目的点击事件
         */
        void onLikeItemClick(int position);

        /**
         * 回复条目的点击监听事件
         */
        void onCommentItemClick(int position);

        /**
         * 分享条目的点击监听事件
         */
        void onShareItemClick(int position);

        /**
         * 标记/取消标记条目的点击监听事件
         */
        void onMarkItemClick(int position);
    }

    /**
     * 是否删除的点击回调
     */
    interface OnDeleteSelectorItemClickListener {

        /**
         * 删除触发
         */
        void onDeletedClick();
    }
}
