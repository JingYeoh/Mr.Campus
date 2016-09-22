package com.jkb.mrcampus.helper.comment;

/**
 * 评论/回复状态的帮助类控制器
 * Created by JustKiddingBaby on 2016/9/22.
 */

public class Comment$ReplyStatusController {

    public static final int SUBMIT_TYPE_COMMENT = 1001;//评论
    public static final int SUBMIT_TYPE_REPLY = 1002;//回复
    public static final int SUBMIC_TYPE_REPLY_REPLY = 1003;//回复回复

    private int submitType;//提交的评论类型
    /**
     * 评论的条目
     */
    private int commentPosition;//回复的评论的条目
    /**
     * 回复的条目
     */
    private int replyPosition;


    public Comment$ReplyStatusController() {
        submitType = SUBMIT_TYPE_COMMENT;
        clearReplyStatus();
    }

    /**
     * 清楚回复状态
     */
    public void clearReplyStatus() {
        this.submitType = SUBMIT_TYPE_COMMENT;
        replyPosition = -1;
        commentPosition = -1;
    }

    /**
     * 转换为回复状态
     *
     * @param commentPosition 评论条目数
     * @param replyPosition   回复条目数
     */
    public void changeStatusToReplyReply(int commentPosition, int replyPosition) {
        this.commentPosition = commentPosition;
        this.replyPosition = replyPosition;
        submitType = SUBMIC_TYPE_REPLY_REPLY;
    }

    /**
     * 转换为回复状态
     *
     * @param commentPosition 评论条目数
     */
    public void changeStatusToReply(int commentPosition) {
        this.commentPosition = commentPosition;
        submitType = SUBMIT_TYPE_REPLY;
    }

    /**
     * 转换为评论状态
     */
    public void changeStatusToComment() {
        clearReplyStatus();
    }

    /**
     * 是否是评论状态
     */
    public boolean isCommentStatus() {
        boolean isComment = false;
        switch (submitType) {
            case SUBMIT_TYPE_COMMENT:
                isComment = true;
                break;
            case SUBMIT_TYPE_REPLY:
                isComment = false;
                break;
        }
        return isComment;
    }

    public int getSubmitType() {
        return submitType;
    }

    public void setSubmitType(int submitType) {
        this.submitType = submitType;
    }

    public int getCommentPosition() {
        return commentPosition;
    }

    public void setCommentPosition(int commentPosition) {
        this.commentPosition = commentPosition;
    }

    public int getReplyPosition() {
        return replyPosition;
    }

    public void setReplyPosition(int replyPosition) {
        this.replyPosition = replyPosition;
    }
}
