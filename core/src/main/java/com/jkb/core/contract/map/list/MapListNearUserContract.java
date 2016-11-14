package com.jkb.core.contract.map.list;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.info.user.UserInfo;

import java.util.List;

/**
 * 附近的用戶页面协议类
 * Created by JustKiddingBaby on 2016/11/14.
 */

public interface MapListNearUserContract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示刷新视图
         */
        void showRefreshing();

        /**
         * 隐藏刷新视图
         */
        void hideRefreshing();

        /**
         * 请求附近的人的数据
         */
        void reqNearUserInfo(int currentPage);

        /**
         * 关闭附近的用户搜索
         */
        void closeNearUserSearch();

        /**
         * 打开附近的人的显搜索
         */
        void openNearUserSearch();

        /**
         * 设置用户信息
         */
        void setUserInfo(List<UserInfo> userInfo);

        /**
         * 提醒打開附近的人
         */
        void showHintForOpenNearUser();

        /**
         * 打开个人中心
         */
        void startUserCenter(int userId);
    }

    interface Presenter extends BasePresenter {
        /**
         * 绑定数据到视图中
         */
        void bindDataToView();

        /**
         * 初始化附近的用户数据
         */
        void initNearUserData();

        /**
         * 刷新
         */
        void onRefresh();

        /**
         * 加载
         */
        void onLoadMore();

        /**
         * 无筛选条件被选择的时候
         */
        void onNoFilterSelected();

        /**
         * 当性别男被选择的时候
         */
        void onMaleSelected();

        /**
         * 当性别女被选择的时候
         */
        void onFeMaleSelected();

        /**
         * 设置附近的用户，只有id和坐标有用
         */
        void setNearUserInfo(List<UserInfo> nearUserInfo, int currentPage, int lastPage);

        /**
         * 用户条目被点击的时候
         */
        void onUserItemClick(int position);
    }
}
