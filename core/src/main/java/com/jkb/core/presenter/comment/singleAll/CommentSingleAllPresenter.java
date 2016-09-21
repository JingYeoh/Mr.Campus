package com.jkb.core.presenter.comment.singleAll;

import com.jkb.core.contract.comment.singleAll.CommentSingleAllContract;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.comment.singleAll.CommentSingleAllRepository;

/**
 * 单个所有评论和回复的评论页面P层
 * Created by JustKiddingBaby on 2016/9/21.
 */

public class CommentSingleAllPresenter implements CommentSingleAllContract.Presenter {

    private CommentSingleAllContract.View view;
    private CommentSingleAllRepository repository;

    //其他
    private int comment_id;
    private DynamicDetailCommentData commentData;//评论内容
    private boolean isCached = false;//是否有缓存
    private boolean isLoading = false;//正在加载
    private PageControlEntity pageControl;
    private int action = ACTION_REFRESH;
    private static final int ACTION_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;

    public CommentSingleAllPresenter(CommentSingleAllContract.View view,
                                     CommentSingleAllRepository repository) {
        this.view = view;
        this.repository = repository;

        this.view.setPresenter(this);
    }

    @Override

    public void initComment$ApplyData() {
        initComment_id();

    }

    @Override
    public void initComment_id() {
        comment_id = view.bindCommentId();
    }

    @Override
    public void bindDataToView() {
        isCached = true;
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void start() {
        initComment$ApplyData();
    }
}
