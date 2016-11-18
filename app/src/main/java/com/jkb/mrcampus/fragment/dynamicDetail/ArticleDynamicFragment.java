package com.jkb.mrcampus.fragment.dynamicDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.dynamicDetail.article.DynamicDetailArticleContract;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailArticleData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.DynamicDetailActivity;
import com.jkb.mrcampus.adapter.recycler.comment.CommentListAdapter;
import com.jkb.mrcampus.adapter.recycler.comment.CommentReplyAdapter;
import com.jkb.mrcampus.adapter.recycler.dynamicDetail.article.ArticleContentShowAdapter;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ShareDynamicDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章的动态类型
 * Created by JustKiddingBaby on 2016/9/14.
 */

public class ArticleDynamicFragment extends BaseFragment implements
        DynamicDetailArticleContract.View,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    //data
    private int dynamic_id = -1;

    public static ArticleDynamicFragment newInstance(@NonNull int dynamic_id) {
        Bundle args = new Bundle();
        ArticleDynamicFragment INSTANCE = new ArticleDynamicFragment();
        args.putInt(SAVED_DYNAMIC_ID, dynamic_id);
        INSTANCE.setArguments(args);
        return INSTANCE;
    }

    //data
    private DynamicDetailActivity dynamicDetailActivity;
    private DynamicDetailArticleContract.Presenter mPresenter;

    //文章内容
    private ArticleContentShowAdapter articleContentShowAdapter;
    private RecyclerView articleContentRecyclerView;
    private LinearLayoutManager articleContentLinearLayoutManager;

    //评论
    private CommentListAdapter commentListAdapter;
    private RecyclerView commentRecyclerView;
    private LinearLayoutManager commentLinearLayoutManager;

    private static final String SAVED_DYNAMIC_ID = "saved_dynamic_id";

    //View
    private SwipeRefreshLayout refreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dynamicDetailActivity = (DynamicDetailActivity) mActivity;
        setRootView(R.layout.frg_dynamic_detail_article);
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
        //刷新
        refreshLayout.setOnRefreshListener(this);
        //其他
        rootView.findViewById(R.id.fdda_iv_headImg).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_1).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_left).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_0).setOnClickListener(this);
        rootView.findViewById(R.id.ts6_ib_right_2).setOnClickListener(this);
        rootView.findViewById(R.id.fdda_ll_allComment).setOnClickListener(this);
        rootView.findViewById(R.id.fdda_tv_loadMore).setOnClickListener(this);

        //文章内容监听器
        articleContentShowAdapter.setOnArticleContentItemClickListener(
                onArticleContentItemClickListener);
        //设置评论内容点击的监听器
        commentListAdapter.setOnLikeClickListener(onLikeClickListener);
        commentListAdapter.setOnHeadImgClickListener(onHeadImgClickListener);
        commentListAdapter.setOnReplyUserClickListener(onReplyUserClickListener);
        commentListAdapter.setOnTargetReplyUserClickListener(onTargetReplyUserClickListener);
        commentListAdapter.setOnViewAllCommentClickListener(onViewAllCommentClickListener);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle args = getArguments();
            if (args != null) {
                dynamic_id = args.getInt(SAVED_DYNAMIC_ID);
            }
        } else {
            dynamic_id = savedInstanceState.getInt(SAVED_DYNAMIC_ID);//恢复数据
        }

        articleContentShowAdapter = new ArticleContentShowAdapter(context, null);
        articleContentRecyclerView.setAdapter(articleContentShowAdapter);

        commentListAdapter = new CommentListAdapter(context, null);
        commentRecyclerView.setAdapter(commentListAdapter);
    }

    @Override
    protected void initView() {

        //刷新
        refreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fdda_srl);

        //初始化评论列表
        commentRecyclerView = (RecyclerView) rootView.findViewById(R.id.fdda_rv_comment);
        commentLinearLayoutManager = new LinearLayoutManager(context);
        commentLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        commentRecyclerView.setLayoutManager(commentLinearLayoutManager);
        commentRecyclerView.setNestedScrollingEnabled(false);

        //初始化文章数据
        articleContentRecyclerView = (RecyclerView) rootView.findViewById(R.id.fdda_rc_articleContent);
        articleContentLinearLayoutManager = new LinearLayoutManager(context);
        articleContentLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        articleContentRecyclerView.setLayoutManager(articleContentLinearLayoutManager);
        articleContentRecyclerView.setNestedScrollingEnabled(false);
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
    public void hideArticleContentView() {
        rootView.findViewById(R.id.fdda_content_article).setVisibility(View.INVISIBLE);
    }

    @Override
    public void showArticleContentView() {
        rootView.findViewById(R.id.fdda_content_article).setVisibility(View.VISIBLE);
    }

    @Override
    public void setUserHeadImg(String userHeadImg) {
        ImageView ivUser = (ImageView) rootView.findViewById(R.id.fdda_iv_headImg);
        if (StringUtils.isEmpty(userHeadImg)) {
            ivUser.setImageResource(R.drawable.ic_user_head);
        } else {
            ImageLoaderFactory.getInstance().displayImage(ivUser, userHeadImg);
        }
    }

    @Override
    public void setUserNickName(String userNickName) {
        TextView tvNickName = (TextView) rootView.findViewById(R.id.fdda_tv_name);
        TextView tvAuth = (TextView) rootView.findViewById(R.id.fdda_tv_authorName);
        tvNickName.setText(userNickName);
        tvAuth.setText(userNickName);
    }

    @Override
    public void setArticleTitle(String topicTitle) {
        TextView tvTopicArticle = (TextView) rootView.findViewById(R.id.fdda_tv_title);
        tvTopicArticle.setText(topicTitle);
    }

    @Override
    public void setPostTime(String postTime) {
//        TextView tvTime = (TextView) rootView.findViewById(R.id.fdda_tv_postTime);
//        tvTime.setText(TimeUtils.changeTimeToDay(postTime));
    }

    @Override
    public void setCommentCount(int commentCount) {
        TextView tvTitleCommentCount = (TextView) rootView.findViewById(R.id.ts6_tv_right_1);
        tvTitleCommentCount.setText(commentCount + "");
        TextView tvCommentCount = (TextView) rootView.findViewById(R.id.fdda_tv_commentCount);
        tvCommentCount.setText(commentCount + "");
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
        TextView tvTag = (TextView) rootView.findViewById(R.id.fdda_tv_tag);
        tvTag.setText(tag);
    }

    @Override
    public void setTagCount(int tagCount) {

    }

    @Override
    public void setArticleBgImg(String articleBgImg) {
        if (StringUtils.isEmpty(articleBgImg)) {
            rootView.findViewById(R.id.fdda_iv_articleBgContent).setVisibility(View.GONE);
            rootView.findViewById(R.id.fdda_iv_articleBg).setVisibility(View.GONE);
            return;
        }
        ImageView ivArticleBgImg = (ImageView) rootView.findViewById(R.id.fdda_iv_articleBg);
        rootView.findViewById(R.id.fdda_iv_articleBgContent).setVisibility(View.VISIBLE);
        ivArticleBgImg.setVisibility(View.VISIBLE);
        ImageLoaderFactory.getInstance().displayImage(ivArticleBgImg, articleBgImg);
    }

    @Override
    public void setArticleContent(List<DynamicDetailArticleData.ArticleContent> articleContent) {
        if (articleContent == null || articleContent.size() == 0) {
            articleContentRecyclerView.setVisibility(View.GONE);
            return;
        }
        articleContentShowAdapter.articleContents = articleContent;
        articleContentShowAdapter.notifyDataSetChanged();
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
        rootView.findViewById(R.id.fdda_commentNone).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.fdda_commentShow).setVisibility(View.GONE);
    }

    @Override
    public void setCommentData(List<DynamicDetailCommentData> commentsData) {
        if (commentsData == null || commentsData.size() == 0) {
            showNoneCommentView();
            return;
        }
        Log.d(TAG, "setCommentData");
        rootView.findViewById(R.id.fdda_commentNone).setVisibility(View.GONE);
        rootView.findViewById(R.id.fdda_commentShow).setVisibility(View.VISIBLE);
//        commentRecyclerView.setVisibility(View.VISIBLE);
        commentListAdapter.commentDataList = commentsData;
        commentListAdapter.notifyDataSetChanged();
    }

    @Override
    public void startPersonCenterActivity(int user_id) {
        dynamicDetailActivity.startPersonalCenterActivity(user_id);
    }

    @Override
    public void startCommentListActivity(int dynamic_id) {
        dynamicDetailActivity.startCommentListActivity(dynamic_id);
    }

    @Override
    public void showViewAllComment$ReplyView(int comment_id) {
        dynamicDetailActivity.startCommentSingleAllActivity(comment_id, dynamic_id);
    }

    @Override
    public void showPicturesBrowserView(ArrayList<String> pictures, int position) {
        dynamicDetailActivity.showPictureBrowserView(pictures, position);
    }

    @Override
    public void setPresenter(DynamicDetailArticleContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        dynamicDetailActivity.showLoading(value, this);
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
    public void onDestroy() {
        super.onDestroy();
        dynamicDetailActivity = null;
        articleContentShowAdapter = null;
        refreshLayout = null;
        articleContentRecyclerView = null;
        commentRecyclerView = null;
        articleContentLinearLayoutManager = null;
        commentLinearLayoutManager = null;
        commentListAdapter = null;
        //监听器
        onArticleContentItemClickListener = null;
        onHeadImgClickListener = null;
        onLikeClickListener = null;
        onReplyUserClickListener = null;
        onTargetReplyUserClickListener = null;
        onViewAllCommentClickListener = null;
        onShareItemClickListener = null;
    }

    @Override
    public void onRefresh() {
        mPresenter.onRefresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts6_ib_left:
                dynamicDetailActivity.onBackPressed();
                break;
            case R.id.fdda_iv_headImg:
                mPresenter.onAuthClick();
                break;
            case R.id.ts6_ib_right_1:
            case R.id.fdda_ll_allComment:
            case R.id.fdda_tv_loadMore:
                startCommentListActivity(dynamic_id);
                break;
            case R.id.ts6_ib_right_0://点击喜欢的时候
                mPresenter.onLikeDynamicClick();
                break;
            case R.id.ts6_ib_right_2://更多
                dynamicDetailActivity.showShareDynamicView(onShareItemClickListener);
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
     * 文章条目的监听器
     */
    private ArticleContentShowAdapter.OnArticleContentItemClickListener
            onArticleContentItemClickListener =
            new ArticleContentShowAdapter.OnArticleContentItemClickListener() {
                @Override
                public void onArticlePictureClick(int position) {
                    if (position > 0) {
                        position -= 1;
                    }
                    mPresenter.onArticlePictureClick(position);
                }
            };
}
