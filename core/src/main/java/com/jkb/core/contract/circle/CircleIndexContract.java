package com.jkb.core.contract.circle;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;
import com.jkb.core.data.dynamic.circle.DynamicInCircle;
import com.jkb.core.data.dynamic.dynamic.DynamicBaseData;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子首页的页面协议类
 * Created by JustKiddingBaby on 2016/8/29.
 */

public interface CircleIndexContract {


    interface View extends BaseView<Presenter> {

        /**
         * 提供圈子的id
         */
        int provideCircleId();

        /**
         * 显示视图
         */
        void showContentView();

        /**
         * 隐藏视图
         */
        void hideContentView();

        /**
         * 显示刷新视图
         */
        void showRefreshingView();

        /**
         * 隐藏刷新视图
         */
        void hideRefreshingView();

        /**
         * 显示分享的视图
         *
         * @param position 条木数
         */
        void showShareView(@NonNull int position);

        /**
         * 设置标题栏名称
         */
        void setTitleName(String titleName);

        /**
         * 设置图片
         */
        void setCirclePicture(String picture);

        /**
         * 设置圈子名称
         */
        void setCircleName(String name);

        /**
         * 设置圈子类型
         */
        void setCircleType(String circleType);

        /**
         * 设置圈子简介信息
         */
        void setCircleIntroduction(String circleIntroduction);

        /**
         * 设置圈子订阅人数
         */
        void setCircleSubscribe_count(int count);

        /**
         * 设置圈子内容总数
         */
        void setCircleOperation_count(int count);

        /**
         * 设置是否订阅的状态
         */
        void setSubscribeStatus(boolean isSubscribe);


        /**
         * 设置圈子东动态数据
         *
         * @param dynamicInCircle 圈子动态数据
         * @param isCircleCreator 是否是圈子创建者
         */
        void setDynamicInCircle(List<DynamicInCircle> dynamicInCircle, boolean isCircleCreator);

        /**
         * 返回
         */
        void back();

        /**
         * 设置
         */
        void setting();

        /**
         * 聊天
         */
        void chat();

        /**
         * 订阅/取消订阅圈子
         */
        void subscribeOrCancel();

        /**
         * 显示发表动态的视图
         */
        void showWriteDynamicView();

        /**
         * 显示关注圈子的提示
         */
        void showHintForPayAttentionCircle();

        /**
         * 显示是否加入聊天室
         *
         * @param circleName 圈子名称
         */
        void showHintForJoinChatRoot(String circleName);

        /**
         * 加入聊天室
         *
         * @param circle_id 圈子id
         */
        void joinChatRoom(@NonNull int circle_id);

        /**
         * 打开动态详情页面
         *
         * @param dynamic_id  动态id
         * @param dynamicType 动态类型
         */
        void startDynamicActivity(@NonNull int dynamic_id, @NonNull String dynamicType);

        /**
         * 打开评论页面
         *
         * @param dynamic_id 动态id
         */
        void startCommentActivity(@NonNull int dynamic_id);

        /**
         * 打开个人中心页面
         *
         * @param user_id 用户id
         */
        void startPersonCenter(@NonNull int user_id);

        /**
         * 显示用户圈子设置
         */
        void showUserCircleSetting();

        /**
         * 显示访客圈子设置
         */
        void showVisitorCircleSetting();

        /**
         * 显示是否拉黑动态的提示框
         */
        void showHintForPutDynamicToBlackList(int position);

        /**
         * 显示大图预览
         */
        void showImageBrowserView(ArrayList<String> PictureUrls, int position);
    }

    interface Presenter extends BasePresenter {

        /**
         * 刷新数据
         */
        void onRefresh();

        /**
         * 加载更多
         */
        void onLoadMore();

        /**
         * 得到圈子id
         */
        void initCircleId();

        /**
         * 初始化圈子数据
         */
        void initCircleData();

        /**
         * 初始化圈子中动态数据
         */
        void initDynamicInCircle();

        /**
         * 绑定数据到视图中
         */
        void bindData();

        /**
         * 请求订阅/取消订阅圈子
         */
        void subscribeOrCancleCircle();

        /**
         * 圈子内动态条目点击事件
         *
         * @param position 条目数
         */
        void onDynamicInCircleItemClick(int position);

        /**
         * 头像的点击监听事件
         *
         * @param position 条目数
         */
        void onHeadImgItemClick(int position);

        /**
         * 评论的点击监听事件
         *
         * @param position 条目数
         */
        void onCommentItemClick(int position);

        /**
         * 喜欢的点击监听事件
         *
         * @param position 条目数
         */
        void onLikeItemClick(int position);

        /**
         * 加入聊天室的点击监听
         */
        void onJoinChatRoomClick();

        /**
         * 当圈子设置按钮被点击的时候
         */
        void onCircleSettingClick();

        /**
         * 拉黑动态
         *
         * @param position 条目数
         */
        void putDynamicInBlackList(int position);

        /**
         * 当圈子图片被点击的时候触发
         */
        void onCirclePictureClick();
    }
}
