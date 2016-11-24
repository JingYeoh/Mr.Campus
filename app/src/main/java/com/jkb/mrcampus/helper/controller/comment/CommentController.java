package com.jkb.mrcampus.helper.controller.comment;

import android.support.annotation.NonNull;

/**
 * 评论控制器的动作规定
 * Created by JustKiddingBaby on 2016/11/24.
 */

public interface CommentController {
    //常量
    int COMMENT_STATUS_COMMENT = 1001;
    int COMMENT_STATUS_REPLY_COMMENT = 1002;
    int COMMENT_STATUS_REPLY_REPLY = 1003;

    /**
     * 转换为评论状态
     */
    void changeStatusToComment();

    /**
     * 转换为回复评论状态
     */
    void changeStatusToReplyComment(int commentPosition);

    /**
     * 转换为回复回复状态
     */
    void changeStatusToReplyReply(int commentPosition, int replyPosition);

    /**
     * 发送评论
     */
    void sendComment(@NonNull CommentStatusSenderCallback callback);

    /**
     * 评论发布的回调
     */
    interface CommentStatusSenderCallback {
        /**
         * 发送评论
         */
        void onCommentSend();

        /**
         * 发送回复
         */
        void onReplyCommentSend(int commentPosition);

        /**
         * 发送回复的回复
         */
        void onReplyReplySend(int commentPosition, int replyPosition);
    }
}
