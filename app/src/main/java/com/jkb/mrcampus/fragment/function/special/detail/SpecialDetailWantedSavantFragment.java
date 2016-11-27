package com.jkb.mrcampus.fragment.function.special.detail;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.core.contract.function.special.detail.general.SubjectDetailContract;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.SpecialDetailActivity;
import com.jkb.mrcampus.adapter.recycler.special.comment.CommentAdapter;
import com.jkb.mrcampus.adapter.recycler.special.detail.SubjectDetailTauntedPictureAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.helper.controller.comment.CommentController;
import com.jkb.mrcampus.helper.controller.comment.CommentControllerImpl;
import com.jkb.mrcampus.helper.controller.comment.senderView.CommentSenderViewController;
import com.jkb.mrcampus.helper.controller.comment.senderView.CommentSenderViewControllerImpl;
import com.jkb.mrcampus.view.KeyboardLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 专题详情：求学霸
 * Created by JustKiddingBaby on 2016/11/20.
 */

public class SpecialDetailWantedSavantFragment extends BaseFragment implements
        SubjectDetailContract.View, View.OnClickListener {

    public static SpecialDetailWantedSavantFragment newInstance(int dynamicId) {
        Bundle args = new Bundle();
        args.putInt(Config.INTENT_KEY_DYNAMIC_ID, dynamicId);
        SpecialDetailWantedSavantFragment fragment = new SpecialDetailWantedSavantFragment();
        fragment.setArguments(args);
        return fragment;
    }

    //data
    private int dynamicId = -1;
    private SpecialDetailActivity specialDetailActivity;
    private SubjectDetailContract.Presenter mPresenter;
    //评论控制器
    private CommentController commentController;
    private CommentSenderViewController commentSenderViewController;
    //列表
    private RecyclerView pictureRecyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private SubjectDetailTauntedPictureAdapter subjectDetailTauntedPictureAdapter;
    //评论
    private CommentAdapter commentAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        specialDetailActivity = (SpecialDetailActivity) mActivity;
        setRootView(R.layout.frg_special_detail_wanted_savant);
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
        //自身
        rootView.findViewById(R.id.ts6_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_0).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_1).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_2).setOnClickListener(this);
        rootView.findViewById(R.id.cclwo_tv_loadMore).setOnClickListener(this);
        rootView.findViewById(R.id.fsdws_iv_avatar).setOnClickListener(this);
        rootView.findViewById(R.id.ccr_contentSend).setOnClickListener(this);
        rootView.findViewById(R.id.cclwo_viewComment_hot).setOnClickListener(this);
        rootView.findViewById(R.id.cclwo_viewComment_order).setOnClickListener(this);
        //评论
        commentAdapter.setOnCommentItemClickListener(onCommentItemClickListener);
        subjectDetailTauntedPictureAdapter.setOnTauntedPicturesItemClickListener(
                picturesItemClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            dynamicId = args.getInt(Config.INTENT_KEY_DYNAMIC_ID);
        } else {
            dynamicId = savedInstanceState.getInt(Config.INTENT_KEY_DYNAMIC_ID);
        }
        //评论适配器
        initCommentController();
        subjectDetailTauntedPictureAdapter = new SubjectDetailTauntedPictureAdapter(context, null);
        pictureRecyclerView.setAdapter(subjectDetailTauntedPictureAdapter);
        commentAdapter = new CommentAdapter(context, null);
        recyclerView.setAdapter(commentAdapter);
    }

    /**
     * 初始化评论控制器
     */
    private void initCommentController() {
        commentController = new CommentControllerImpl();
        commentSenderViewController = new CommentSenderViewControllerImpl();
        //设置视图
        EditText etInput = (EditText) rootView.findViewById(R.id.ccr_et_commentInput);
        KeyboardLayout keyboardLayout = (KeyboardLayout) rootView.findViewById(R.id.fsdws_kbl);
        TextView tvCommentCount = (TextView) rootView.findViewById(R.id.ccr_tv_inputCount);
        TextView tvCommentRemainderCount = (TextView)
                rootView.findViewById(R.id.ccr_tv_inputRemainderCount);
        ImageView ivSendComment = (ImageView) rootView.findViewById(R.id.ccr_iv_sendComment);
        //配置
        commentSenderViewController.setControlView(
                keyboardLayout, etInput, tvCommentCount, tvCommentRemainderCount, ivSendComment);
        commentSenderViewController.setCommentController(commentController);
    }

    @Override
    protected void initView() {
        //列表
        pictureRecyclerView = (RecyclerView) rootView.findViewById(R.id.fsdws_rv);
        staggeredGridLayoutManager = new
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        pictureRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        pictureRecyclerView.setNestedScrollingEnabled(false);
        //评论
        recyclerView = (RecyclerView) rootView.findViewById(R.id.cclwo_rv_comment);
        recyclerView.setNestedScrollingEnabled(false);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public int getDynamicId() {
        return dynamicId;
    }

    @Override
    public void setSubjectTitle(String confessionTitle) {
        ((TextView) rootView.findViewById(R.id.fsdws_tv_titleLostAndFound)).
                setText(confessionTitle);
        ((TextView) rootView.findViewById(R.id.fsdws_tv_titleLostAndFoundPlace))
                .setText(confessionTitle);
    }

    @Override
    public void setSubjectContent(String confessionContent) {
        ((TextView) rootView.findViewById(R.id.fsdws_tv_lostAndFound)).setText(confessionContent);
    }

    @Override
    public void setSubjectImageUrls(List<String> confessionImageUrls) {
        if (confessionImageUrls == null || confessionImageUrls.size() == 0) {
            rootView.findViewById(R.id.fsdws_iv_pictureBg).setVisibility(View.GONE);
            rootView.findViewById(R.id.fsdws_iv_pictureBgPlace).setVisibility(View.GONE);
            return;
        }
        rootView.findViewById(R.id.fsdws_iv_pictureBg).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fsdws_iv_pictureBgPlace).setVisibility(View.INVISIBLE);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.fsdws_iv_pictureBg);
        ImageLoaderFactory.getInstance().displayImage(imageView, confessionImageUrls.get(0));
        subjectDetailTauntedPictureAdapter.picturesUlr = confessionImageUrls;
        subjectDetailTauntedPictureAdapter.notifyDataSetChanged();
    }

    @Override
    public void setUserNickName(String userNickName) {
        ((TextView) rootView.findViewById(R.id.fsdws_tv_nickName)).setText(userNickName);
    }

    @Override
    public void setUserAvatar(String userAvatar) {
        ImageView imageView = (ImageView) rootView.findViewById(R.id.fsdws_iv_avatar);
        ImageLoaderFactory.getInstance().displayImage(imageView, userAvatar);
    }

    @Override
    public void setCommentCount(int commentCount) {
        ((TextView) rootView.findViewById(R.id.ts6_tv_right_1)).setText(commentCount + "");
    }

    @Override
    public void setFavoriteCount(int favoriteCount) {
        ((TextView) rootView.findViewById(R.id.ts6_tv_right_0)).setText(favoriteCount + "");
    }

    @Override
    public void setFavoriteStatus(boolean favoriteStatus) {
        ImageButton imageView = (ImageButton) rootView.findViewById(R.id.ts6_ib_right_0);
        if (favoriteStatus) {
            imageView.setImageResource(R.drawable.ic_heart_red);
        } else {
            imageView.setImageResource(R.drawable.ic_heart_black);
        }
    }

    @Override
    public void showPictureBrowserView(ArrayList<String> urls, int position) {
        specialDetailActivity.showPictureBrowserView(urls, position);
    }

    @Override
    public void showContentView() {
        rootView.findViewById(R.id.fsdws_contentSubject).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fsdws_contentBg).setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContentView() {
        rootView.findViewById(R.id.fsdws_contentSubject).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.fsdws_contentBg).setVisibility(View.GONE);
    }

    @Override
    public void startPersonCenter(int user_id) {
        specialDetailActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void startCommentList(int dynamicId) {
        specialDetailActivity.startCommentListActivity(dynamicId);
    }

    @Override
    public void startCommentDetail(int dynamicId, int commentId) {
        specialDetailActivity.startCommentSingleAllActivity(commentId, dynamicId);
    }

    @Override
    public void setPresenter(SubjectDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        specialDetailActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        specialDetailActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        showShortToash(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setTargetName(String targetName) {
        commentSenderViewController.setTargetName(targetName);
    }

    @Override
    public void hideLoadMoreView() {
        rootView.findViewById(R.id.cclwo_tv_loadMore).setVisibility(View.GONE);
        rootView.findViewById(R.id.cclwo_contentNonLoadMore).setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoadMoreView() {
        rootView.findViewById(R.id.cclwo_tv_loadMore).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.cclwo_contentNonLoadMore).setVisibility(View.GONE);
    }

    @Override
    public void showNoneCommentView() {
        rootView.findViewById(R.id.cclwo_contentComment).setVisibility(View.GONE);
        rootView.findViewById(R.id.cclwo_commentNone).setVisibility(View.VISIBLE);
    }

    @Override
    public void loadCompleted() {
        rootView.findViewById(R.id.cclwo_contentNonLoadMore).setVisibility(View.VISIBLE);
    }

    @Override
    public void setCommentData(List<DynamicDetailCommentData> commentsData) {
        rootView.findViewById(R.id.cclwo_contentComment).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.cclwo_commentNone).setVisibility(View.GONE);
        commentAdapter.commentDataList = commentsData;
        commentAdapter.notifyDataSetChanged();
    }

    @Override
    public void showOrderByHotView() {
        clearOrderUi();
        commentSenderViewController.clearCommentStatus();
        rootView.findViewById(R.id.cclwo_viewHot_select_0).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.cclwo_viewHot_select_1).setVisibility(View.VISIBLE);
        ((TextView) rootView.findViewById(R.id.cclwo_tv_viewHot)).setTextColor(COLOR_MAIN_THEME_GREEN);
    }

    @Override
    public void showOrderByAsc() {
        clearOrderUi();
        commentSenderViewController.clearCommentStatus();
        rootView.findViewById(R.id.cclwo_viewOrder_select_0).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.cclwo_viewOrder_select_1).setVisibility(View.VISIBLE);
        ((TextView) rootView.findViewById(R.id.cclwo_tv_viewOrder)).setTextColor(COLOR_MAIN_THEME_GREEN);
        ((TextView) rootView.findViewById(R.id.cclwo_tv_viewOrder)).setText(R.string.view_order_asc);
        ((ImageView) rootView.findViewById(R.id.cclwo_iv_viewOrder)).
                setImageResource(R.drawable.ic_asc_green);
    }

    @Override
    public void showOrderByDesc() {
        clearOrderUi();
        commentSenderViewController.clearCommentStatus();
        rootView.findViewById(R.id.cclwo_viewOrder_select_0).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.cclwo_viewOrder_select_1).setVisibility(View.VISIBLE);
        ((TextView) rootView.findViewById(R.id.cclwo_tv_viewOrder)).setTextColor(COLOR_MAIN_THEME_GREEN);
        ((TextView) rootView.findViewById(R.id.cclwo_tv_viewOrder)).setText(R.string.view_order_desc);
        ((ImageView) rootView.findViewById(R.id.cclwo_iv_viewOrder)).
                setImageResource(R.drawable.ic_desc_green);
    }

    /**
     * 清楚排序的UI状态
     */
    private void clearOrderUi() {
        rootView.findViewById(R.id.cclwo_viewHot_select_0).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.cclwo_viewHot_select_1).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.cclwo_viewOrder_select_0).setVisibility(View.INVISIBLE);
        rootView.findViewById(R.id.cclwo_viewOrder_select_1).setVisibility(View.INVISIBLE);
        ((TextView) rootView.findViewById(R.id.cclwo_tv_viewHot)).setTextColor(Color.BLACK);
        ((TextView) rootView.findViewById(R.id.cclwo_tv_viewOrder)).setTextColor(Color.BLACK);
        ((ImageView) rootView.findViewById(R.id.cclwo_iv_viewOrder)).
                setImageResource(R.drawable.ic_desc_gray);
    }

    @Override
    public void commitComment() {
        commentController.sendComment(commentStatusSenderCallback);
    }

    @Override
    public void onCommentSuccess() {
        specialDetailActivity.hideSoftInputView();
        commentSenderViewController.clearCommentStatus();//清楚状态、隐藏软键盘
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        specialDetailActivity = null;
        subjectDetailTauntedPictureAdapter = null;
        commentAdapter = null;
        commentController = null;
        commentSenderViewController.destroyControl();
        commentSenderViewController = null;
        //view
        pictureRecyclerView = null;
        staggeredGridLayoutManager = null;
        recyclerView = null;
        linearLayoutManager = null;
        //监听器
        onCommentItemClickListener = null;
        commentStatusSenderCallback = null;
        picturesItemClickListener = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Config.INTENT_KEY_DYNAMIC_ID, dynamicId);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts6_ib_left:
                specialDetailActivity.onBackPressed();
                break;
            case R.id.ts6_ib_right_0:
                mPresenter.onFavoriteClick();
                break;
            case R.id.ts6_ib_right_1://评论列表
                startCommentList(dynamicId);
                break;
            case R.id.ts6_ib_right_2://更多视图
                break;
            case R.id.fsdws_iv_avatar:
                mPresenter.onUserHeadImgClick();
                break;
            case R.id.cclwo_tv_loadMore://加载更多评论
                mPresenter.loadMoreComment();
                break;
            case R.id.ccr_contentSend://发表评论
                commitComment();
                break;
            case R.id.cclwo_viewComment_hot://热门排序
                mPresenter.onOrderByHot();
                break;
            case R.id.cclwo_viewComment_order://排序
                mPresenter.onOrderByDesc$Asc();
                break;
        }
    }

    /**
     * 评论条目的点击适配器
     */
    private CommentAdapter.OnCommentItemClickListener onCommentItemClickListener =
            new CommentAdapter.OnCommentItemClickListener() {
                @Override
                public void onLikeItemClick(int commentPosition) {
                    mPresenter.onConfessionCommentItemClick(commentPosition);
                }

                @Override
                public void onCommentUserClick(int commentPosition) {
                    mPresenter.onCommentUserItemClick(commentPosition);
                }

                @Override
                public void onCommentContentItemClick(int commentPosition) {
                    specialDetailActivity.showSoftInputView();
                    commentController.changeStatusToReplyComment(commentPosition);
                    mPresenter.onReplyContentClick(commentPosition);
                }

                @Override
                public void onCommentViewAllItemClick(int commentPosition) {//查看所有的视图
                    mPresenter.onReplyLoadMoreItemClick(commentPosition);
                }

                @Override
                public void onReplyTargetUserItemClick(int commentItem, int replyPosition) {
                    mPresenter.onReplyTargetUserItemClick(commentItem, replyPosition);
                }

                @Override
                public void onReplyCommentItemClick(int commentItem, int replyPosition) {
                    specialDetailActivity.showSoftInputView();
                    commentController.changeStatusToReplyReply(commentItem, replyPosition);
                    mPresenter.onReplyReplyContentClick(commentItem, replyPosition);
                }

                @Override
                public void onReplySenderUserItemClick(int commentItem, int replyPosition) {
                    mPresenter.onReplySenderUserItemClick(commentItem, replyPosition);
                }
            };
    /**
     * 发送评论的监听器
     */
    private CommentController.CommentStatusSenderCallback commentStatusSenderCallback =
            new CommentController.CommentStatusSenderCallback() {
                @Override
                public void onCommentSend() {
                    String commentValue = commentSenderViewController.getCommentValue();
                    mPresenter.sendComment(commentValue);
                }

                @Override
                public void onReplyCommentSend(int commentPosition) {
                    String commentValue = commentSenderViewController.getCommentValue();
                    mPresenter.sendCommitReply(commentPosition, commentValue);
                }

                @Override
                public void onReplyReplySend(int commentPosition, int replyPosition) {
                    String commentValue = commentSenderViewController.getCommentValue();
                    mPresenter.sendCommitReply(commentPosition, replyPosition, commentValue);
                }
            };
    /**
     * 图片条目的点击监听事件
     */
    private SubjectDetailTauntedPictureAdapter.OnTauntedPicturesItemClickListener
            picturesItemClickListener =
            new SubjectDetailTauntedPictureAdapter.OnTauntedPicturesItemClickListener() {
                @Override
                public void onPictureItemClick(int position) {
                    mPresenter.onSubjectPicturesItemClick(position);
                }
            };
}
