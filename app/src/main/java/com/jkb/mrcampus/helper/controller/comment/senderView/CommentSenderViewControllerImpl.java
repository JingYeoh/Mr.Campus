package com.jkb.mrcampus.helper.controller.comment.senderView;

import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.helper.controller.comment.CommentController;
import com.jkb.mrcampus.view.KeyboardLayout;

/**
 * 发送评论的控制器实现类
 * Created by JustKiddingBaby on 2016/11/24.
 */

public class CommentSenderViewControllerImpl implements CommentSenderViewController {

    //view
    private KeyboardLayout keyboardLayout;
    private EditText etInputView;
    private TextView tvCommentCount;
    private TextView tvCommentRemainderCount;
    private ImageView ivSendComment;

    //控制器
    private CommentController commentController;

    @Override
    public void setControlView(
            @NonNull KeyboardLayout keyboardLayout, @NonNull EditText etInputView,
            @NonNull TextView tvCommentCount, @NonNull TextView tvCommentRemainderCount,
            @NonNull ImageView ivSendComment) {
        this.keyboardLayout = keyboardLayout;
        this.etInputView = etInputView;
        this.tvCommentCount = tvCommentCount;
        this.tvCommentRemainderCount = tvCommentRemainderCount;
        this.ivSendComment = ivSendComment;

        //设置监听器
        keyboardLayout.setOnkbdStateListener(onKybdsChangeListener);
        etInputView.addTextChangedListener(textWatcherListener);
    }

    @Override
    public void setCommentController(CommentController commentController) {
        this.commentController = commentController;
    }

    @Override
    public void setTargetName(String targetName) {
        etInputView.setHint(targetName);
        etInputView.setText("");//切换用户的时候清空输入
    }

    @Override
    public String getCommentValue() {
        return etInputView.getText().toString();
    }

    @Override
    public void destroyControl() {
        keyboardLayout = null;
        etInputView = null;
        tvCommentCount = null;
        tvCommentRemainderCount = null;
        ivSendComment = null;
        onKybdsChangeListener = null;
        textWatcherListener = null;
    }

    /**
     * 键盘状态的监听器
     */
    private KeyboardLayout.onKybdsChangeListener onKybdsChangeListener =
            new KeyboardLayout.onKybdsChangeListener() {
                @Override
                public void onKeyBoardStateChange(int state) {
                    switch (state) {
                        case KeyboardLayout.KEYBOARD_STATE_HIDE:
                            judgeToClear();
                            break;
                        case KeyboardLayout.KEYBOARD_STATE_SHOW:
                            break;
                    }
                }
            };

    /**
     * 输入的监听事件
     */
    private TextWatcher textWatcherListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            int nSelLength = etInputView.getText().toString().length();
            if (nSelLength > 0) {
                ivSendComment.setImageResource(R.drawable.ic_comment_send);
            } else {
                ivSendComment.setImageResource(R.drawable.ic_comment_send_gray);
            }
            tvCommentCount.setText(nSelLength + "");
            tvCommentRemainderCount.setText(String.valueOf(String
                    .valueOf(MAX_COMMENT_COUNT - nSelLength)));
        }

        @Override
        public void afterTextChanged(Editable s) {
            int nSelStart;
            int nSelEnd;
            nSelStart = etInputView.getSelectionStart();
            nSelEnd = etInputView.getSelectionEnd();
            boolean nOverMaxLength;
            nOverMaxLength = (s.length() > MAX_COMMENT_COUNT);
            if (nOverMaxLength) {
                s.delete(nSelStart - 1, nSelEnd);
                etInputView.setTextKeepState(s);
            }
        }
    };

    /**
     * 判断是否清楚
     */
    private void judgeToClear() {
        if (StringUtils.isEmpty(getCommentValue())) {
            etInputView.setHint("您的评论会让作者更有动力");
            etInputView.setText("");
            if (commentController != null) {
                commentController.changeStatusToComment();
            }
        }
    }

    /**
     * 清楚评论状态
     */
    @Override
    public void clearCommentStatus() {
        etInputView.setHint("您的评论会让作者更有动力");
        etInputView.setText("");
        if (commentController != null) {
            commentController.changeStatusToComment();
        }
    }
}
