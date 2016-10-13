package com.jkb.mrcampus.base;

import android.support.annotation.NonNull;
import android.view.View;

import com.jkb.core.contract.dynamicCreate.data.CategoryTypeData;
import com.jkb.mrcampus.fragment.dialog.ShareDynamicDialogFragment;
import com.jkb.mrcampus.fragment.dialog.TagFloatFragment;
import com.jkb.mrcampus.fragment.dialog.WriteDynamicDialogFragment;

import java.util.List;

/**
 * Activity基类的动作接口
 * Created by JustKiddingBaby on 2016/9/6.
 */

public interface BaseActivityAction {
    /**
     * 显示写动态的视图
     */
    void showWriteDynamicView(WriteDynamicDialogFragment.OnWriteDynamicClickListener listener);

    /**
     * 显示分享动态的视图
     */
    void showShareDynamicView(ShareDynamicDialogFragment.OnShareItemClickListener listener);

    /**
     * 显示展示TAG的视图
     *
     * @param categoryTypeDatas 數據
     * @param listener          条目点击的监听器
     */
    void showTagFloatView(List<CategoryTypeData> categoryTypeDatas,
                          TagFloatFragment.OnTagItemClickListener listener);

    /**
     * 显示选择学校的视图
     */
    void showSelectSchoolView();


    /**
     * 分享
     *
     * @param title    title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
     * @param titleUrl titleUrl是标题的网络链接，仅在人人网和QQ空间使用
     * @param text     text是分享文本，所有平台都需要这个字段
     * @param imageUrl 分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
     * @param url      url仅在微信（包括好友和朋友圈）中使用
     * @param site     site是分享此内容的网站名称，仅在QQ空间使用
     * @param siteUrl  siteUrl是分享此内容的网站地址，仅在QQ空间使用
     */
    void share(
            String title, String titleUrl, String text, String imageUrl, String url,
            String site, String siteUrl);

    /**
     * 打开动态详情页面
     *
     * @param dynamic_id  动态id
     * @param dynamicType 动态类型
     */

    void startDynamicActivity(@NonNull int dynamic_id, @NonNull String dynamicType);

    /**
     * 打开圈子页面
     *
     * @param circle_id 圈子id
     */
    void startCircleActivity(@NonNull int circle_id);

    /**
     * 打开个人中心页面
     */
    void startPersonalCenterActivity(@NonNull int user_id);

    /**
     * 打开用户列表页面
     *
     * @param user_id 用户id
     * @param action  要显示的视图
     */
    void startUsersListActivity(@NonNull int user_id, String action);

    /**
     * 打开评论的页面
     *
     * @param dynamicId 目标id
     */
    void startCommentListActivity(@NonNull int dynamicId);

    /**
     * 打开圈子列表页面
     *
     * @param user_id 用户id
     */
    void startCircleListActivity(@NonNull int user_id);

    /**
     * 打开评论详情页面
     *
     * @param commentId 评论id
     * @param dynamicId 动态id
     */
    void startCommentSingleAllActivity(@NonNull int commentId, @NonNull int dynamicId);

    /**
     * 打开创建动态的Activity
     *
     * @param dynamicCreateType 动态创建类型
     */
    void startDynamicCreateActivity(@NonNull String dynamicCreateType);

    /**
     * 打开创建圈子的页面
     */
    void startCreateCircleActivity();

    /**
     * 打开消息中心的页面
     */
    void startMessageCenterActivity();

    /**
     * 打开具体的消息页面
     *
     * @param messageType 消息的类型
     */
    void startMessageActivity(@NonNull int messageType);

    /**
     * 顯示輸入
     **/
    void showSoftInputView();

    /**
     * 隐藏输入
     **/
    void hideSoftInputView();

    /**
     * 显示软键盘
     */
    void showSoftKeyboard(View view);

    /**
     * 隐藏软键盘
     */
    void hideSoftKeyboard(View view);

    /**
     * 判断软键盘是否开启
     */
    boolean isKeyboardShown(View view);

    /**
     * 取消显示所有视图
     */
    void dismiss();
}
