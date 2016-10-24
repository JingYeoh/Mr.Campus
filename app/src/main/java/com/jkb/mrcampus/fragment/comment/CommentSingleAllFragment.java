package com.jkb.mrcampus.fragment.comment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.comment.singleAll.CommentSingleAllContract;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentReplyData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CommentActivity;
import com.jkb.mrcampus.adapter.recycler.comment.CommentReplyAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.helper.comment.Comment$ReplyStatusController;
import com.jkb.mrcampus.view.KeyboardLayout;

import java.util.List;

/**
 * 单挑全部评论的View
 * Created by JustKiddingBaby on 2016/9/20.
 */

public class CommentSingleAllFragment extends BaseFragment implements CommentSingleAllContract.View,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    //data
    private int comment_id = -1;
    private int dynamic_id = -1;
    private CommentActivity commentActivity;

    public CommentSingleAllFragment() {
    }

    private static CommentSingleAllFragment INSTANCE = null;

    public static CommentSingleAllFragment newInstance(int dynamic_id, int comment_id) {
        if (INSTANCE == null || dynamic_id > 0 || comment_id > 0) {
            INSTANCE = new CommentSingleAllFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(Config.INTENT_KEY_COMMENT_ID, comment_id);
            bundle.putInt(Config.INTENT_KEY_DYNAMIC_ID, dynamic_id);
            INSTANCE.setArguments(bundle);
        }
        return INSTANCE;
    }

    //Data
    private CommentSingleAllContract.Presenter mPresenter;

    //View
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout refreshLayout;
    //评论
    private EditText etCommentInput;
    private TextView tvCommentCount;
    private TextView tvCommentRemainderCount;
    private ImageView ivSendComment;

    //键盘控制
    private KeyboardLayout keyboardLayout;

    //data
    private static final int MAX_COMMENT_COUNT = 360;
    private CommentReplyAdapter commentReplyAdapter;

    //评论和回复的状态
    private Comment$ReplyStatusController comment$ReplyStatusController;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        commentActivity = (CommentActivity) mActivity;
        setRootView(R.layout.frg_comment_single_all);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(this);

        commentReplyAdapter.setOnReplyUserClickListener(onReplyUserClickListener);
        commentReplyAdapter.setOnTargetReplyUserClickListener(onTargetReplyUserClickListener);
        commentReplyAdapter.setOnReplyReplyCommentClickListener(onReplyReplyCommentClickListener);

        //设置本地点击事件
        rootView.findViewById(R.id.icsa_iv_praise).setOnClickListener(this);
        rootView.findViewById(R.id.icsa_iv_headImg).setOnClickListener(this);
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.icsa_ll_sendComment).setOnClickListener(this);
        rootView.findViewById(R.id.icsa_tv_commentContent).setOnClickListener(this);


        //设置评论的内容数目的监听器
        etCommentInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int nSelLength = etCommentInput.getText().toString().length();
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
                nSelStart = etCommentInput.getSelectionStart();
                nSelEnd = etCommentInput.getSelectionEnd();
                boolean nOverMaxLength;
                nOverMaxLength = (s.length() > MAX_COMMENT_COUNT);
                if (nOverMaxLength) {
                    s.delete(nSelStart - 1, nSelEnd);
                    etCommentInput.setTextKeepState(s);
                }
            }
        });
        //设置软键盘弹起/隐藏状态的监听
        keyboardLayout.setOnkbdStateListener(onKybdsChangeListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        comment$ReplyStatusController = new Comment$ReplyStatusController();
        comment$ReplyStatusController.setSubmitType(Comment$ReplyStatusController.SUBMIT_TYPE_REPLY);
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            dynamic_id = arguments.getInt(Config.INTENT_KEY_DYNAMIC_ID);
            comment_id = arguments.getInt(Config.INTENT_KEY_COMMENT_ID);
        } else {
            comment_id = savedInstanceState.getInt(Config.INTENT_KEY_COMMENT_ID);
        }
        commentReplyAdapter = new CommentReplyAdapter(context, null);
        recyclerView.setAdapter(commentReplyAdapter);
    }

    @Override
    protected void initView() {
        //设置标题栏
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("评论详情");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.icsa_rv_comment);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setNestedScrollingEnabled(false);//禁止滑动

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.icsa_srl);

        //键盘
        keyboardLayout = (KeyboardLayout) rootView.findViewById(R.id.icsa_kl);

        //初始化输入
        etCommentInput = (EditText) rootView.findViewById(R.id.icsa_et_commentInput);
        //初始化其他
        tvCommentCount = (TextView) rootView.findViewById(R.id.icsa_tv_inputCount);
        tvCommentRemainderCount = (TextView) rootView.findViewById(R.id.icsa_tv_inputRemainderCount);
        ivSendComment = (ImageView) rootView.findViewById(R.id.icsa_iv_sendComment);
    }

    @Override
    public int bindCommentId() {
        return comment_id;
    }

    @Override
    public int bindDynamicId() {
        return dynamic_id;
    }

    @Override
    public void showRefreshView() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefreshView() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void clearComment$HideSoftInputView() {
        etCommentInput.setText("");//清楚文本框信息
//        commentActivity.hideSoftInputView();
        commentActivity.hideSoftKeyboard(etCommentInput);//设置软键盘不可见
    }

    @Override
    public void clearReplyStatus() {
        String comment = etCommentInput.getText().toString();
        if (!StringUtils.isEmpty(comment)) {
            return;
        }
        TextView tvNickName = (TextView) rootView.findViewById(R.id.icsa_tv_name);
        String name = tvNickName.getText().toString();
        etCommentInput.setHint("回复：" + name);
        comment$ReplyStatusController.changeStatusToReply(0);
    }

    @Override
    public void setReplyTargetNickName(String replyName) {
        etCommentInput.setHint("回复：" + replyName);
    }

    @Override
    public void setReplyTargetNickName(int commentPosition) {
        comment$ReplyStatusController.changeStatusToReply(commentPosition);
        //弹起软键盘
        etCommentInput.requestFocus();
        commentActivity.showSoftInputView();
        commentActivity.showSoftKeyboard(etCommentInput);
        //设置内容
        mPresenter.onReplyContentClick();
    }

    @Override
    public void setReplyReplyStatus(int commentPosition, int replyPosition) {
        comment$ReplyStatusController.changeStatusToReplyReply(commentPosition, replyPosition);
        //弹起软键盘
        etCommentInput.requestFocus();
        commentActivity.showSoftInputView();
        commentActivity.showSoftKeyboard(etCommentInput);
        //设置内容
        mPresenter.onReplyReplyContentClick(replyPosition);
    }

    @Override
    public void commitComment$Reply() {
        String comment = etCommentInput.getText().toString();
        //判断是发布评论还是回复
        int submitType = comment$ReplyStatusController.getSubmitType();
        switch (submitType) {
            case Comment$ReplyStatusController.SUBMIT_TYPE_REPLY: {//回复
                mPresenter.onReplyContentClick();
                mPresenter.commentReply(comment);
                break;
            }
            case Comment$ReplyStatusController.SUBMIC_TYPE_REPLY_REPLY://回复回复{
                int replyPosition = comment$ReplyStatusController.getReplyPosition();
                mPresenter.onReplyReplyContentClick(replyPosition);
                mPresenter.commentReply(replyPosition, comment);
                break;
        }
    }

    @Override
    public void setCommentContent(String commentContent) {
        TextView tvComment = (TextView) rootView.findViewById(R.id.icsa_tv_commentContent);
        tvComment.setText(commentContent);
    }

    @Override
    public void setCommentUserName(String commentUserName) {
        TextView tvCommentUserName = (TextView) rootView.findViewById(R.id.icsa_tv_name);
        tvCommentUserName.setText(commentUserName);
        etCommentInput.setHint("回复：" + commentUserName);
    }

    @Override
    public void setCommentUserAvatar(String commentUserAvatar) {
        ImageView ivUserHeadImg = (ImageView) rootView.findViewById(R.id.icsa_iv_headImg);
        if (StringUtils.isEmpty(commentUserAvatar)) {
            ivUserHeadImg.setImageResource(R.drawable.ic_user_head);
        } else {
            ImageLoaderFactory.getInstance().displayImage(ivUserHeadImg, commentUserAvatar);
        }
    }

    @Override
    public void setCommentCreate_Time(String commentCreate_time) {
        TextView tvTime = (TextView) rootView.findViewById(R.id.icsa_tv_time);
        tvTime.setText(commentCreate_time);
    }

    @Override
    public void setLikeCount(int likeCount) {
        TextView tvLikeCount = (TextView) rootView.findViewById(R.id.icsa_tv_praiseCount);
        tvLikeCount.setText(likeCount + "");
    }

    @Override
    public void setHasFavorite(boolean hasFavorite) {
        ImageView ivHasFavorite = (ImageView) rootView.findViewById(R.id.icsa_iv_praise);
        if (!hasFavorite) {
            ivHasFavorite.setImageResource(R.drawable.ic_praise_gray);
        } else {
            ivHasFavorite.setImageResource(R.drawable.ic_praise_yellow);
        }
    }

    @Override
    public void setReplyData(List<DynamicDetailCommentReplyData> replyDataList) {
        if (replyDataList == null || replyDataList.size() <= 0) {
            return;
        }
        commentReplyAdapter.replyDataList = replyDataList;
        commentReplyAdapter.notifyDataSetChanged();
    }

    @Override
    public void startPersonCenterView(int user_id) {
        commentActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void setPresenter(CommentSingleAllContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        commentActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        commentActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        commentActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        commentActivity = null;
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_COMMENT_ID, comment_id);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icsa_iv_praise://点赞
                mPresenter.onLikeClick();
                break;
            case R.id.icsa_iv_headImg://头像点击
                mPresenter.onCommentUserClick();
                break;
            case R.id.ts4_ib_left://点击回退按钮
                commentActivity.onBackPressed();
                break;
            case R.id.icsa_ll_sendComment://发送评论
                commitComment$Reply();
                break;
            case R.id.icsa_tv_commentContent://点击评论的时候
                commentActivity.showSoftInputView();
                setReplyTargetNickName(1);
                break;
        }
    }

    /**
     * 点击回复用户的监听器
     */
    private CommentReplyAdapter.OnReplyUserClickListener onReplyUserClickListener = new
            CommentReplyAdapter.OnReplyUserClickListener() {
                @Override
                public void onReplyUserClick(int parentPosition, int position) {
                    mPresenter.onReplyUserClick(position);
                }
            };
    /**
     * 点击目标挥回复用户的监听器
     */
    private CommentReplyAdapter.OnTargetReplyUserClickListener onTargetReplyUserClickListener = new
            CommentReplyAdapter.OnTargetReplyUserClickListener() {
                @Override
                public void onTargetReplyUserClick(int parentPosition, int position) {
                    mPresenter.onTargetReplyUserClick(position);
                }
            };
    /**
     * 设置评论的回复的内容的点击事件监听
     */
    private CommentReplyAdapter.OnReplyReplyCommentClickListener onReplyReplyCommentClickListener =
            new CommentReplyAdapter.OnReplyReplyCommentClickListener() {
                @Override
                public void onReplyReplyCommentClick(int parentPosition, int position) {
                    setReplyReplyStatus(parentPosition, position);
                    commentActivity.showSoftInputView();
                }
            };
    /**
     * 软键盘的弹起/关闭状态
     */
    private KeyboardLayout.onKybdsChangeListener onKybdsChangeListener = new
            KeyboardLayout.onKybdsChangeListener() {
                @Override
                public void onKeyBoardStateChange(int state) {
                    switch (state) {
                        case KeyboardLayout.KEYBOARD_STATE_HIDE:
//                            showReqResult("软键盘隐藏");
                            clearReplyStatus();
                            break;
                        case KeyboardLayout.KEYBOARD_STATE_SHOW:
//                            showReqResult("软键盘弹起");
                            break;
                    }
                }
            };
}
