package com.jkb.mrcampus.helper.controller.comment;

/**
 * 评论的控制器
 * Created by JustKiddingBaby on 2016/11/24.
 */

public class CommentControllerImpl implements CommentController {

    //当前评论的状态
    private int targetCommentStatus;

    //回复的坐标
    private int commentPosition;
    private int replyPosition;

    public CommentControllerImpl() {
        targetCommentStatus = COMMENT_STATUS_COMMENT;
    }

    @Override
    public void changeStatusToComment() {
        targetCommentStatus = COMMENT_STATUS_COMMENT;
    }

    @Override
    public void changeStatusToReplyComment(int commentPosition) {
        targetCommentStatus = COMMENT_STATUS_REPLY_COMMENT;
        this.commentPosition = commentPosition;
    }

    @Override
    public void changeStatusToReplyReply(int commentPosition, int replyPosition) {
        targetCommentStatus = COMMENT_STATUS_REPLY_REPLY;
        this.commentPosition = commentPosition;
        this.replyPosition = replyPosition;
    }

    @Override
    public void sendComment(CommentController.CommentStatusSenderCallback callback) {
        switch (targetCommentStatus) {
            case COMMENT_STATUS_COMMENT:
                callback.onCommentSend();
                break;
            case COMMENT_STATUS_REPLY_COMMENT:
                callback.onReplyCommentSend(commentPosition);
                break;
            case COMMENT_STATUS_REPLY_REPLY:
                callback.onReplyReplySend(commentPosition, replyPosition);
                break;
        }
    }
}
