package com.jkb.core.contract.search;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.search.detail.SearchData;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索详情的页面协议类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public interface SearchDetailContract {

    interface View extends BaseView<Presenter> {

        /**
         * 设置搜索结果的数目
         *
         * @param allCount     总数
         * @param userCount    用户数
         * @param circleCount  圈子数
         * @param dynamicCount 动态数
         * @param subjectCount 专题数
         */
        void setSearchResultCount(
                int allCount, int userCount, int circleCount, int dynamicCount, int subjectCount);

        /**
         * 设置搜索的结果
         */
        void setSearchResult(List<SearchData> searchData);

        /**
         * 显示无搜索结果视图
         */
        void showNonResultView();

        /**
         * 得到搜索的关键字
         */
        String getSearchKeyWords();

        /**
         * 显示刷新视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新
         */
        void hideRefreshingView();

        /**
         * 滑动到顶部
         */
        void scrollToTop();

        /**
         * 显示图片预览视图
         */
        void showPicturesBrowserView(ArrayList<String> pictures, int position);

        /**
         * 打开圈子首页
         */
        void startCircleIndex(int circleId);

        /**
         * 打开文章动态
         */
        void startArticleDynamic(int dynamicId);

        /**
         * 打开普通动态
         */
        void startNormalDynamic(int dynamicId);

        /**
         * 打开话题动态
         */
        void startTopicDynamic(int dynamicId);

        /**
         * 打开个人中心
         */
        void startPersonCenter(int userId);

        /**
         * 打开专题：表白墙
         */
        void startSubjectConfession(int dynamicId);

        /**
         * 打开专题：吐槽
         */
        void startSubjectTaunted(int dynamicId);

        /**
         * 打开专题：跳蚤市场
         */
        void startSubjectFleaMarket(int dynamicId);

        /**
         * 打开专题：失物招領
         */
        void startSubjectLostAndFound(int dynamicId);

        /**
         * 打开专题：求学霸
         */
        void startSubjectWantedSavant(int dynamicId);

        /**
         * 打开专题：寻伙伴
         */
        void startSubjectPartner(int dynamicId);
    }

    interface Presenter extends BasePresenter {

        int SEARCH_TYPE_ALL = 1001;
        int SEARCH_TYPE_USER = 1002;
        int SEARCH_TYPE_CIRCLE = 1003;
        int SEARCH_TYPE_DYNAMIC = 1004;
        int SEARCH_TYPE_SUBJECT = 1005;
        int SEARCH_TYPE_DYNAMICINCIRCLE = 1006;

        /**
         * 设置搜索类型
         */
        void setSearchType(int searchType);

        /**
         * 绑定数据
         */
        void bindDataToView();

        /**
         * 初始化搜索数据
         */
        void initSearchData();

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 更新关键字
         */
        void updateKeyWords(String keyWords);

        /**
         * 搜索条目被点击时回调
         */
        void onSearchItemClick(int position);

        /**
         * 图片条目被点击的时候
         */
        void onPictureItemClick(int position, int childPosition);

        /**
         * 图片条目被点击的时候
         */
        void onPictureItemClick(int position);

        /**
         * 用户头像被点击
         */
        void onUserHeadImgClick(int position);

        /**
         * 圈子条目被点击
         */
        void onCircleItemClick(int position);

        /**
         * 用户关注条目被点击
         */
        void onUserAttentionItemClick(int position);
    }
}
