package com.jkb.mrcampus.helper.controller.comment.senderView;

import android.support.annotation.NonNull;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.mrcampus.helper.controller.comment.CommentController;
import com.jkb.mrcampus.view.KeyboardLayout;

/**
 * 发送的视图控制器
 * Created by JustKiddingBaby on 2016/11/24.
 */

public interface CommentSenderViewController {

    int MAX_COMMENT_COUNT = 300;//最多的评论数目

    /**
     * 设置控制器视图
     *
     * @param keyboardLayout          监听键盘弹起的控制器
     * @param etInputView             输入的控件
     * @param tvCommentCount          发送数据的控件
     * @param tvCommentRemainderCount 剩余发送数目的控件
     * @param ivSendComment           是否可发送的标识
     */
    void setControlView(
            @NonNull KeyboardLayout keyboardLayout, @NonNull EditText etInputView,
            @NonNull TextView tvCommentCount, @NonNull TextView tvCommentRemainderCount,
            @NonNull ImageView ivSendComment);

    /**
     * 设置控制器
     */
    void setCommentController(CommentController commentController);

    /**
     * 设置目标名称
     */
    void setTargetName(String targetName);

    /**
     * 得到回复的内容
     */
    String getCommentValue();

    /**
     * 清楚评论状态
     */
    void clearCommentStatus();

    /**
     * 销毁控制器
     */
    void destroyControl();
}
