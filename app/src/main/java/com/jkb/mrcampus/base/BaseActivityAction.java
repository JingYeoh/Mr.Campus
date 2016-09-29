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
