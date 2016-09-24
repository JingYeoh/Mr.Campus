package com.jkb.mrcampus.fragment.dynamicDetail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.core.contract.dynamicDetail.topic.DynamicDetailTopicContract;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.model.utils.TimeUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.adapter.recycler.comment.CommentListAdapter;
import com.jkb.mrcampus.adapter.recycler.comment.CommentReplyAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ShareDynamicDialogFragment;
import com.jkb.mrcampus.helper.comment.Comment$ReplyStatusController;
import com.jkb.mrcampus.view.KeyboardLayout;

import java.util.List;

/**
 * 话题的动态类型
 * Created by JustKiddingBaby on 2016/9/14.
 */

public class TopicDynamicFragment extends BaseFragment implements DynamicDetailTopicContract.View,
        View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    //data
    private int dynamic_id = -1;

    public TopicDynamicFragment() {
    }

    public TopicDynamicFragment(int dynamic_id) {
        this.dynamic_id = dynamic_id;
    }

    private static TopicDynamicFragment INSTANCE = null;

    public static TopicDynamicFragment newInstance(@NonNull int dynamic_id) {
        if (INSTANCE == null || dynamic_id != -1) {
            INSTANCE = new TopicDynamicFragment(dynamic_id);
        }
        return INSTANCE;
    }

    //data
    private DynamicDetailActivity dynamicDetailActivity;
    private DynamicDetailTopicContract.Presenter mPresenter;
    private static final String SAVED_DYNAMIC_ID = "saved_dynamic_id";
    private static final int MAX_COMMENT_COUNT = 360;
    //适配器
    private CommentListAdapter commentListAdapter;
    //评论和回复的状态
    private Comment$ReplyStatusController comment$ReplyStatusController;

    //View
    private KeyboardLayout keyboardLayout;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView commentRecyclerView;
    private LinearLayoutManager linearLayoutManager;

    //输入
    private EditText etCommentInput;
    private TextView tvCommentCount;
    private TextView tvCommentRemainderCount;
    private ImageView ivSendComment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dynamicDetailActivity = (DynamicDetailActivity) mActivity;
        setRootView(R.layout.frg_dynamic_detail_topic);
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
        //标题栏的点击事件
        rootView.findViewById(R.id.ts6_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_0).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_1).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_2).setOnClickListener(this);
        //头像的点击事件
        rootView.findViewById(R.id.fddt_iv_headImg).setOnClickListener(this);
        //加载更多的监听器
        rootView.findViewById(R.id.fddt_tv_loadMore).setOnClickListener(this);
        //发送评论
        rootView.findViewById(R.id.fddt_commentNone).setOnClickListener(this);
        rootView.findViewById(R.id.ffdt_ll_sendComment).setOnClickListener(this);
        //评论的顺序
        rootView.findViewById(R.id.fddt_viewComment_order).setOnClickListener(this);
        rootView.findViewById(R.id.fddt_viewComment_hot).setOnClickListener(this);
        //刷新
        refreshLayout.setOnRefreshListener(this);
        //设置软键盘状态监听
        keyboardLayout.setOnkbdStateListener(onKybdsChangeListener);
        //设置评论内容的各个监听器
        commentListAdapter.setOnLikeClickListener(onLikeClickListener);
        commentListAdapter.setOnHeadImgClickListener(onHeadImgClickListener);
        commentListAdapter.setOnReplyUserClickListener(onReplyUserClickListener);
        commentListAdapter.setOnTargetReplyUserClickListener(onTargetReplyUserClickListener);
        commentListAdapter.setOnViewAllCommentClickListener(onViewAllCommentClickListener);
        commentListAdapter.setOnCommentValueClickListener(onCommentValueClickListener);
        commentListAdapter.setOnReplyReplyCommentClickListener(onReplyReplyCommentClickListener);
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
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //隐藏软键盘的显示
//        etCommentInput.clearFocus();
        dynamicDetailActivity.hideSoftKeyboard(etCommentInput);
        if (savedInstanceState == null) {

        } else {
            dynamic_id = savedInstanceState.getInt(SAVED_DYNAMIC_ID);//恢复数据
        }
        commentListAdapter = new CommentListAdapter(mActivity, null);
        commentRecyclerView.setAdapter(commentListAdapter);
        comment$ReplyStatusController = new Comment$ReplyStatusController();
    }

    @Override
    protected void initView() {
        dynamicDetailActivity.hideSoftInputView();//隐藏软键盘的显示

        keyboardLayout = (KeyboardLayout) rootView.findViewById(R.id.fddt_kbl);
        //初始化输入
        etCommentInput = (EditText) rootView.findViewById(R.id.fddt_et_commentInput);
        //初始化其他
        tvCommentCount = (TextView) rootView.findViewById(R.id.fddt_tv_inputCount);
        tvCommentRemainderCount = (TextView) rootView.findViewById(R.id.fddt_tv_inputRemainderCount);
        ivSendComment = (ImageView) rootView.findViewById(R.id.fddt_iv_sendComment);

        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fddt_srl);
        //初始化评论列表
        commentRecyclerView = (RecyclerView) rootView.findViewById(R.id.fddt_rv_comment);
        linearLayoutManager = new LinearLayoutManager(mActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentRecyclerView.setLayoutManager(linearLayoutManager);
        commentRecyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_DYNAMIC_ID, dynamic_id);
    }

    @Override
    public int bindDynamicId() {
        return dynamic_id;
    }

    @Override
    public void setContentImages(String imageUrl) {
        ImageView ivContent = (ImageView) rootView.findViewById(R.id.fddt_iv_contentImg);
        if (StringUtils.isEmpty(imageUrl)) {
            ivContent.setVisibility(View.GONE);
        } else {
            ImageLoaderFactory.getInstance().displayImage(ivContent, imageUrl);
        }
    }

    @Override
    public void hideTopicContentView() {
        rootView.findViewById(R.id.fddt_contentTopic).setVisibility(View.INVISIBLE);
    }

    @Override
    public void showTopicContentView() {
        rootView.findViewById(R.id.fddt_contentTopic).setVisibility(View.VISIBLE);
    }

    @Override
    public void setContentValue(String contentValue) {
        TextView tvContentValue = (TextView) rootView.findViewById(R.id.fddt_tv_contentValue);
        tvContentValue.setText(contentValue);
    }

    @Override
    public void setUserHeadImg(String userHeadImg) {
        ImageView ivUser = (ImageView) rootView.findViewById(R.id.fddt_iv_headImg);
        if (StringUtils.isEmpty(userHeadImg)) {
            ivUser.setImageResource(R.drawable.ic_user_head);
        } else {
            ImageLoaderFactory.getInstance().displayImage(ivUser, userHeadImg);
        }
    }

    @Override
    public void setUserNickName(String userNickName) {
        TextView tvNickName = (TextView) rootView.findViewById(R.id.fddt_tv_name);
        tvNickName.setText(userNickName);
    }

    @Override
    public void setTopicTitle(String topicTitle) {
        TextView tvTopicTitle = (TextView) rootView.findViewById(R.id.fddt_tv_title);
        tvTopicTitle.setText(topicTitle);
    }

    @Override
    public void setPostTime(String postTime) {
        TextView tvTime = (TextView) rootView.findViewById(R.id.fddt_tv_postTime);
        tvTime.setText(TimeUtils.changeTimeToDay(postTime));
    }

    @Override
    public void setCommentCount(int commentCount) {
        TextView tvTitleCommentCount = (TextView) rootView.findViewById(R.id.ts6_tv_right_1);
        tvTitleCommentCount.setText(commentCount + "");
    }

    @Override
    public void setFavoriteCount(int favoriteCount) {
        TextView tvTitleFavoriteCount = (TextView) rootView.findViewById(R.id.ts6_tv_right_0);
        if (favoriteCount > 0) {
            tvTitleFavoriteCount.setText(favoriteCount + "");
        } else {
            tvTitleFavoriteCount.setText("");
        }
    }

    @Override
    public void setPartInCount(int partInCount) {
        TextView tvPartInCount = (TextView) rootView.findViewById(R.id.fddt_tv_partInNum);
        tvPartInCount.setText(partInCount + "");
    }

    @Override
    public void setHasFavorite(boolean hasFavorite) {
        ImageView favorite = (ImageView) rootView.findViewById(R.id.ts6_ib_right_0);
        if (hasFavorite) {
            favorite.setImageResource(R.drawable.ic_heart_red);
        } else {
            favorite.setImageResource(R.drawable.ic_heart_black);
        }
    }

    @Override
    public void setTag(String tag) {
        TextView tvTag = (TextView) rootView.findViewById(R.id.fddt_tv_tag);
        tvTag.setText(tag);
    }

    @Override
    public void setTagCount(int tagCount) {

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
    public void showNoneCommentView() {
        Log.d(TAG, "showNoneCommentView");
        rootView.findViewById(R.id.fddt_commentNone).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fddt_contentComment).setVisibility(View.GONE);
//        commentRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void setCommentData(List<DynamicDetailCommentData> commentsData) {
        if (commentsData == null || commentsData.size() == 0) {
            showNoneCommentView();
            return;
        }
        Log.d(TAG, "setCommentData");
        rootView.findViewById(R.id.fddt_commentNone).setVisibility(View.GONE);
        rootView.findViewById(R.id.fddt_contentComment).setVisibility(View.VISIBLE);
//        commentRecyclerView.setVisibility(View.VISIBLE);
        commentListAdapter.commentDataList = commentsData;
        commentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMore() {
        mPresenter.onLoadMore();
    }

    @Override
    public void showLoadMoreView() {
        rootView.findViewById(R.id.fddt_tv_loadMore).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fddt_contentNonLoadMore).setVisibility(View.GONE);
    }

    @Override
    public void hideLoadMoreView() {
        rootView.findViewById(R.id.fddt_tv_loadMore).setVisibility(View.GONE);
        rootView.findViewById(R.id.fddt_contentNonLoadMore).setVisibility(View.VISIBLE);
    }

    @Override
    public void loadCompleted() {
        hideLoadMoreView();
        rootView.findViewById(R.id.fddt_contentNonLoadMore).setVisibility(View.VISIBLE);
    }

    @Override
    public void setOrderByHotView() {
        //通过热门排序评论
        //设置UI
        clearOrderUi();
        rootView.findViewById(R.id.fddt_viewHot_select_0).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fddt_viewHot_select_1).setVisibility(View.VISIBLE);
        ((TextView) rootView.findViewById(R.id.fddt_tv_viewHot)).setTextColor(COLOR_MAIN_THEME_GREEN);
    }

    @Override
    public void setOrderByAsc() {
        clearOrderUi();
        rootView.findViewById(R.id.fddt_viewOrder_select_0).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fddt_viewOrder_select_1).setVisibility(View.VISIBLE);
        ((TextView) rootView.findViewById(R.id.fddt_tv_viewOrder)).setTextColor(COLOR_MAIN_THEME_GREEN);
        ((TextView) rootView.findViewById(R.id.fddt_tv_viewOrder)).setText(R.string.view_order_asc);
        ((ImageView) rootView.findViewById(R.id.fddt_iv_viewOrder)).
                setImageResource(R.drawable.ic_asc_green);
    }

    @Override
    public void setOrderByDesc() {
        clearOrderUi();
        rootView.findViewById(R.id.fddt_viewOrder_select_0).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fddt_viewOrder_select_1).setVisibility(View.VISIBLE);
        ((TextView) rootView.findViewById(R.id.fddt_tv_viewOrder)).setTextColor(COLOR_MAIN_THEME_GREEN);
        ((TextView) rootView.findViewById(R.id.fddt_tv_viewOrder)).setText(R.string.view_order_desc);
        ((ImageView) rootView.findViewById(R.id.fddt_iv_viewOrder)).
                setImageResource(R.drawable.ic_desc_green);
    }

    /**
     * 清楚排序的UI状态
     */
    private void clearOrderUi() {
        rootView.findViewById(R.id.fddt_viewHot_select_0).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.fddt_viewHot_select_1).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.fddt_viewOrder_select_0).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.fddt_viewOrder_select_1).setVisibility(View.INVISIBLE);
        ((TextView) rootView.findViewById(R.id.fddt_tv_viewHot)).setTextColor(Color.BLACK);
        ((TextView) rootView.findViewById(R.id.fddt_tv_viewOrder)).setTextColor(Color.BLACK);
        ((ImageView) rootView.findViewById(R.id.fddt_iv_viewOrder)).
                setImageResource(R.drawable.ic_desc_gray);
    }

    @Override
    public void commitComment() {
        String comment = etCommentInput.getText().toString();
        //判断是发布评论还是回复
        int submitType = comment$ReplyStatusController.getSubmitType();
        switch (submitType) {
            case Comment$ReplyStatusController.SUBMIT_TYPE_COMMENT://评论
                mPresenter.sendComment(comment);
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
        dynamicDetailActivity.hideSoftKeyboard(etCommentInput);//设置软键盘不可见
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
        dynamicDetailActivity.showSoftKeyboard(etCommentInput);
        //设置内容
        mPresenter.onReplyContentClick(commentPosition);
    }

    @Override
    public void setReplyReplyStatus(int commentPosition, int replyPosition) {
        comment$ReplyStatusController.changeStatusToReplyReply(commentPosition, replyPosition);
        //弹起软键盘
        etCommentInput.requestFocus();
        dynamicDetailActivity.showSoftInputView();
        dynamicDetailActivity.showSoftKeyboard(etCommentInput);
        //设置内容
        mPresenter.onReplyReplyContentClick(commentPosition, replyPosition);
    }

    @Override
    public void startPersonCenterView(int user_id) {
        dynamicDetailActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void onUserHeadImgClick() {
        mPresenter.onUserHeadImgClick();
    }

    @Override
    public void startPersonCenterActivity(int user_id) {
        dynamicDetailActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void showViewAllComment$ReplyView(int comment_id) {
        //显示所有的评论和回复的页面
        dynamicDetailActivity.startCommentSingleAllActivity(comment_id, dynamic_id);
    }

    @Override
    public void setPresenter(DynamicDetailTopicContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        dynamicDetailActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        dynamicDetailActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        dynamicDetailActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts6_ib_left://返回
                dynamicDetailActivity.onBackPressed();
                break;
            case R.id.fddt_iv_headImg://头像
                onUserHeadImgClick();
                break;
            case R.id.ts6_ib_right_0://喜欢
                mPresenter.onLikeDynamicClick();
                break;
            case R.id.ts6_ib_right_1://评论
            case R.id.fddt_commentNone://抢占沙发
                clearReplyStatus();
                dynamicDetailActivity.showSoftKeyboard(etCommentInput);
                break;
            case R.id.ts6_ib_right_2://更多
                dynamicDetailActivity.showShareDynamicView(onShareItemClickListener);
                break;
            case R.id.ffdt_ll_sendComment://提交
                commitComment();
                break;
            case R.id.fddt_tv_loadMore://加载更多
                onLoadMore();
                break;
            case R.id.fddt_viewComment_order://顺序排序
                mPresenter.onOrderByDesc$Asc();
                break;
            case R.id.fddt_viewComment_hot://热门排序
                mPresenter.onOrderByHot();
                break;
        }
    }

    /**
     * 分享的点击监听事件
     */
    private ShareDynamicDialogFragment.OnShareItemClickListener onShareItemClickListener = new
            ShareDynamicDialogFragment.OnShareItemClickListener() {
                @Override
                public void onWechatClick() {

                }

                @Override
                public void onWechatCircleClick() {

                }

                @Override
                public void onQQClick() {

                }

                @Override
                public void onQQZoneClick() {

                }

                @Override
                public void onSinaClick() {

                }

                @Override
                public void onCircleClick() {

                }
            };

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

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
                    dynamicDetailActivity.showSoftInputView();
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
                    dynamicDetailActivity.showSoftInputView();
                    setReplyReplyStatus(parentPosition, position);
                }
            };
}
