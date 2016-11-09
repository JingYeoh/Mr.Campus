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

import com.jkb.core.contract.comment.list.CommentListContract;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.CommentActivity;
import com.jkb.mrcampus.adapter.recycler.comment.CommentListAdapter;
import com.jkb.mrcampus.adapter.recycler.comment.CommentReplyAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.helper.comment.Comment$ReplyStatusController;
import com.jkb.mrcampus.view.KeyboardLayout;

import java.util.List;

/**
 * 评论列表的View
 * Created by JustKiddingBaby on 2016/9/20.
 */

public class CommentListFragment extends BaseFragment implements CommentListContract.View,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private int dynamic_id = -1;

    public static CommentListFragment newInstance(int dynamic_id) {
        CommentListFragment INSTANCE = new CommentListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Config.INTENT_KEY_DYNAMIC_ID, dynamic_id);
        INSTANCE.setArguments(bundle);
        return INSTANCE;
    }

    //View
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private SwipeRefreshLayout refreshLayout;
    //评论
    private KeyboardLayout keyboardLayout;
    private EditText etCommentInput;
    private TextView tvCommentCount;
    private TextView tvCommentRemainderCount;
    private ImageView ivSendComment;

    //Data
    private CommentActivity commentActivity;
    private CommentListContract.Presenter mPresenter;
    private CommentListAdapter commentListAdapter;
    private static final int MAX_COMMENT_COUNT = 360;

    //评论和回复的状态
    private Comment$ReplyStatusController comment$ReplyStatusController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        commentActivity = (CommentActivity) mActivity;
        setRootView(R.layout.frg_comment_list);
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
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

        recyclerView.addOnScrollListener(onScrollListener);//设置滑动监听，设置是否下拉刷新

        //评论
        commentListAdapter.setOnLikeClickListener(onLikeClickListener);
        commentListAdapter.setOnHeadImgClickListener(onHeadImgClickListener);
        commentListAdapter.setOnReplyUserClickListener(onReplyUserClickListener);
        commentListAdapter.setOnTargetReplyUserClickListener(onTargetReplyUserClickListener);
        commentListAdapter.setOnViewAllCommentClickListener(onViewAllCommentClickListener);
        commentListAdapter.setOnCommentValueClickListener(onCommentValueClickListener);
        commentListAdapter.setOnReplyReplyCommentClickListener(onReplyReplyCommentClickListener);

        rootView.findViewById(R.id.fcl_ll_sendComment).setOnClickListener(this);
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);

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
        //初始化回复/评论的控制器
        comment$ReplyStatusController = new Comment$ReplyStatusController();
        if (savedInstanceState == null) {
            Bundle arguments = getArguments();
            dynamic_id = arguments.getInt(Config.INTENT_KEY_DYNAMIC_ID);
        } else {
            dynamic_id = savedInstanceState.getInt(Config.INTENT_KEY_DYNAMIC_ID);
        }
        commentListAdapter = new CommentListAdapter(context, null);
        recyclerView.setAdapter(commentListAdapter);
    }

    @Override
    protected void initView() {
        //设置标题栏
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText("评论");

        recyclerView = (RecyclerView) rootView.findViewById(R.id.fcl_rv);
        linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fcl_srl);

        keyboardLayout = (KeyboardLayout) rootView.findViewById(R.id.fcl_contentKeyBoard);
        //初始化输入
        etCommentInput = (EditText) rootView.findViewById(R.id.fcl_et_commentInput);
        //初始化其他
        tvCommentCount = (TextView) rootView.findViewById(R.id.fcl_tv_inputCount);
        tvCommentRemainderCount = (TextView) rootView.findViewById(R.id.fcl_tv_inputRemainderCount);
        ivSendComment = (ImageView) rootView.findViewById(R.id.fcl_iv_sendComment);
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
    public void setCommentData(List<DynamicDetailCommentData> commentsData) {
        if (commentsData == null || commentsData.size() <= 0) {
            return;
        }
        commentListAdapter.commentDataList = commentsData;
        commentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void commitComment$Reply() {
        String comment = etCommentInput.getText().toString();
        //判断是发布评论还是回复
        int submitType = comment$ReplyStatusController.getSubmitType();
        switch (submitType) {
            case Comment$ReplyStatusController.SUBMIT_TYPE_COMMENT://评论
                mPresenter.commentComment(comment);
                break;
            case Comment$ReplyStatusController.SUBMIT_TYPE_REPLY: {//回复
                int commentPosition = comment$ReplyStatusController.getCommentPosition();
                mPresenter.onReplyContentClick(commentPosition);
                mPresenter.commitReply(commentPosition, comment);
                break;
            }
            case Comment$ReplyStatusController.SUBMIC_TYPE_REPLY_REPLY://回复回复{
                int commentPosition = comment$ReplyStatusController.getCommentPosition();
                int replyPosition = comment$ReplyStatusController.getReplyPosition();
                mPresenter.onReplyReplyContentClick(commentPosition, replyPosition);
                mPresenter.commitReply(commentPosition, replyPosition, comment);
                break;
        }
    }

    @Override
    public void clearComment$HideSoftInputView() {
        etCommentInput.setText("");//清楚文本框信息
//        etCommentInput.clearFocus();
//        commentActivity.hideSoftInputView();
        commentActivity.hideSoftKeyboard(etCommentInput);//设置软键盘不可见
    }

    @Override
    public void clearReplyStatus() {
        String comment = etCommentInput.getText().toString();
        if (!StringUtils.isEmpty(comment)) {
            return;
        }
        etCommentInput.setHint(R.string.Your_comment_will_be_more_motivated_author);
        comment$ReplyStatusController.clearReplyStatus();
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
//        commentActivity.showSoftInputView();
        commentActivity.showSoftKeyboard(etCommentInput);
        //设置内容
        mPresenter.onReplyContentClick(commentPosition);
    }

    @Override
    public void setReplyReplyStatus(int commentPosition, int replyPosition) {
        comment$ReplyStatusController.changeStatusToReplyReply(commentPosition, replyPosition);
        //弹起软键盘
        etCommentInput.requestFocus();
        commentActivity.showSoftInputView();
        commentActivity.showSoftKeyboard(etCommentInput);
        //设置内容
        mPresenter.onReplyReplyContentClick(commentPosition, replyPosition);
    }

    @Override
    public void startPersonCenterView(int user_id) {
        commentActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void showViewAllComment$ReplyView(int comment_id) {
        //显示所有的评论和回复的页面
        commentActivity.startCommentSingleAllActivity(comment_id, dynamic_id);
    }

    @Override
    public void setPresenter(CommentListContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        if (!isHidden())
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
        recyclerView = null;
        refreshLayout = null;
        linearLayoutManager = null;
        keyboardLayout = null;
        etCommentInput = null;
        tvCommentCount = null;
        tvCommentRemainderCount = null;
        ivSendComment = null;
        commentListAdapter = null;
        comment$ReplyStatusController = null;
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_DYNAMIC_ID, dynamic_id);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fcl_ll_sendComment:
                commitComment$Reply();
                break;
            case R.id.ts4_ib_left:
                commentActivity.onBackPressed();
                break;
        }
    }

    /**
     * 滑动的监听器
     */
    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int lastVisibleItem = (linearLayoutManager).findLastVisibleItemPosition();
            int totalItemCount = linearLayoutManager.getItemCount();
            if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                mPresenter.onLoadMore();//设置下拉加载
            }
        }
    };
    /**
     * 设置点击喜欢的点击事件监听
     */
    private CommentListAdapter.OnLikeClickListener onLikeClickListener = new
            CommentListAdapter.OnLikeClickListener() {
                @Override
                public void onLikeClick(int position) {
                    mPresenter.onLikeCommentClick(position);
                }
            };
    /**
     * 设置点击头像的点击事件监听
     */
    private CommentListAdapter.OnHeadImgClickListener onHeadImgClickListener = new
            CommentListAdapter.OnHeadImgClickListener() {
                @Override
                public void onHeadImgClick(int position) {
                    mPresenter.onCommentUserClick(position);
                }
            };
    /**
     * 回复的目标用户点击回调
     */
    private CommentReplyAdapter.OnTargetReplyUserClickListener onTargetReplyUserClickListener =
            new CommentReplyAdapter.OnTargetReplyUserClickListener() {
                @Override
                public void onTargetReplyUserClick(int parentPosition, int position) {
                    Log.d(TAG, "parentPosition=" + parentPosition + "\n"
                            + "position=" + position);
                    mPresenter.onTargetReplyUserClick(parentPosition, position);
                }
            };
    /**
     * 回复的用户点击回调
     */
    private CommentReplyAdapter.OnReplyUserClickListener onReplyUserClickListener =
            new CommentReplyAdapter.OnReplyUserClickListener() {
                @Override
                public void onReplyUserClick(int parentPosition, int position) {
                    Log.d(TAG, "parentPosition=" + parentPosition + "\n"
                            + "position=" + position);
                    mPresenter.onReplyUserClick(parentPosition, position);
                }
            };
    /**
     * 查看所有回复的点击回调
     */
    private CommentListAdapter.OnViewAllCommentClickListener onViewAllCommentClickListener =
            new CommentListAdapter.OnViewAllCommentClickListener() {
                @Override
                public void OnViewAllCommentClick(int position) {
                    mPresenter.onViewAllComment$ReplyClick(position);
                }
            };
    /**
     * 点击评论内容的点击回调
     */
    private CommentListAdapter.OnCommentValueClickListener onCommentValueClickListener =
            new CommentListAdapter.OnCommentValueClickListener() {
                @Override
                public void onCommentValueClick(int position) {
                    commentActivity.showSoftInputView();
                    setReplyTargetNickName(position);
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
