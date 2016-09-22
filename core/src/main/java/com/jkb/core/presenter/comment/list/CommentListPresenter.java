package com.jkb.core.presenter.comment.list;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentReplyEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.comment.list.CommentListContract;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailUserData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentReplyData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.comment.list.CommentListRepository;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 评论列表的P层
 * Created by JustKiddingBaby on 2016/9/20.
 */

public class CommentListPresenter implements CommentListContract.Presenter {

    private static final String TAG = "CommentListPresenter";
    private CommentListContract.View view;
    private CommentListRepository repository;

    //其他
    private int dynamic_id;
    private List<DynamicDetailCommentData> commentData;//评论内容
    private boolean isCached = false;//是否有缓存
    private boolean isLoading = false;//正在加载
    private PageControlEntity pageControl;
    private int action = ACTION_REFRESH;
    private static final int ACTION_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;

    public CommentListPresenter(@NonNull CommentListContract.View view,
                                @NonNull CommentListRepository repository) {
        this.view = view;
        this.repository = repository;

        commentData = new ArrayList<>();
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void initCommentData() {
        initDynamic_id();
        if (isCached) {
            bindDataToView();
            return;
        }
        //请求数据
        onRefresh();
    }

    @Override
    public void initDynamic_id() {
        dynamic_id = view.bindDynamicId();
    }

    @Override
    public void bindDataToView() {
        isCached = true;
        //绑定数据
        if (!view.isActive()) {
            return;
        }
        //筛选数据，回复最多三条
        filterCommentReplyData();
        //绑定评论的数据
        view.setCommentData(commentData);
    }

    /**
     * 筛选回复个数
     */
    private void filterCommentReplyData() {
        if (commentData == null || commentData.size() == 0) {
            return;
        }
        for (int p = 0; p < commentData.size(); p++) {
            DynamicDetailCommentData comment = commentData.get(p);
            List<DynamicDetailCommentReplyData> replyDatas = comment.getReplyDatas();
            if (replyDatas == null || replyDatas.size() == 0) {
                continue;
            }
            //筛选为只有三条数据
            if (replyDatas.size() <= 3) {
                continue;
            }
            //筛减数据，最多只要三条
            List<DynamicDetailCommentReplyData> filterReplydatas = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                filterReplydatas.add(replyDatas.get(i));
            }
            replyDatas.clear();
            comment.setReplyDatas(filterReplydatas);
            commentData.set(p, comment);
        }
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        //请求数据
        view.showRefreshView();
        isCached = false;
        action = ACTION_REFRESH;
        pageControl.setCurrent_page(1);
        reqCommentList();
    }

    @Override
    public void onLoadMore() {
        if (isLoading) {
            return;
        }
        action = ACTION_LOADMORE;
        if (pageControl.getCurrent_page() >= pageControl.getLast_page()) {
            return;
        }
        //设置当前页数+1
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        reqCommentList();
    }

    @Override
    public void onLikeCommentClick(final int position) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        //调用喜欢评论的方法
        UserAuths userAuths = getUserAuths();
        if (userAuths == null) {
            view.showReqResult("登录过期，请重新登录");
            return;
        }
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        int user_id = userAuths.getUser_id();
        int target_id = commentData.get(position).getComment_id();
        repository.likeComment(Authorization, user_id, target_id, new
                ApiCallback<ApiResponse<OperationActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("操作成功");
                            //设置局部刷新
                            DynamicDetailCommentData comment =
                                    commentData.get(position);
                            boolean isFavorite = !comment.isHas_like();
                            comment.setHas_like(isFavorite);
                            if (isFavorite) {
                                comment.setLikeCount(comment.getLikeCount() + 1);
                            } else {
                                comment.setLikeCount(comment.getLikeCount() - 1);
                            }
                            commentData.set(position, comment);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                        String error, ApiResponse<OperationActionEntity> apiResponse) {
                        if (view.isActive()) {
                            view.showReqResult("操作失败");
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            view.showReqResult("操作错误");
                        }
                    }
                });
    }

    @Override
    public void commentComment(String comment) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        if (StringUtils.isEmpty(comment)) {
            view.showReqResult("内容不能为空");
            return;
        }
        //请求发送评论接口
        //调用喜欢评论的方法
        UserAuths userAuths = getUserAuths();
        if (userAuths == null) {
            view.showReqResult("登录过期，请重新登录");
            return;
        }
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        int user_id = userAuths.getUser_id();
        view.showLoading("");
        repository.sendComment(Authorization, user_id, dynamic_id, comment, sendCommentApiCallback);
    }

    @Override
    public void commentReply(int commentPosition, String comment) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        if (StringUtils.isEmpty(comment)) {
            view.showReqResult("内容不能为空");
            return;
        }
        //请求发送评论接口
        //调用喜欢评论的方法
        UserAuths userAuths = getUserAuths();
        if (userAuths == null) {
            view.showReqResult("登录过期，请重新登录");
            return;
        }
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        int user_id = commentData.get(commentPosition).getComment_user().getId();
        int comment_id = commentData.get(commentPosition).getComment_id();
        view.showLoading("");
        reqSubmitReply(Authorization, user_id, comment_id, comment);
    }

    @Override
    public void commentReply(int commentPosition, int replyPosition, String comment) {
        if (!LoginContext.getInstance().isLogined()) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        if (StringUtils.isEmpty(comment)) {
            view.showReqResult("内容不能为空");
            return;
        }
        //请求发送评论接口
        //调用喜欢评论的方法
        UserAuths userAuths = getUserAuths();
        if (userAuths == null) {
            view.showReqResult("登录过期，请重新登录");
            return;
        }
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        int comment_id = commentData.get(commentPosition). getComment_id();
        int user_id = commentData.get(commentPosition).getReplyDatas().get(replyPosition)
                .getReply_user().getId();
        view.showLoading("");
        reqSubmitReply(Authorization, user_id, comment_id, comment);
    }

    /**
     * 提交回复的数据
     *
     * @param authorization 头
     * @param user_id       用户id
     * @param comment_id    评论id
     * @param comment       评论
     */
    private void reqSubmitReply(String authorization, int user_id, int comment_id, String comment) {
        repository.sendReply(authorization, user_id, comment_id, dynamic_id, comment, replyApiCallback);
    }

    @Override
    public int getUser_id(int position) {
        int user_id;
        DynamicDetailCommentData commentData = this.commentData.get(position);
        DynamicDetailUserData comment_user = commentData.getComment_user();
        user_id = comment_user.getId();
        return user_id;
    }

    @Override
    public void onCommentUserClick(int commentPosition) {
        Log.d(TAG, "commentPosition=" + commentPosition);
        int user_id = getUser_id(commentPosition);
        view.startPersonCenterView(user_id);
    }

    @Override
    public void onReplyUserClick(int commentPosition, int replyPosition) {
        DynamicDetailCommentData commentData = this.commentData.get(commentPosition);
        DynamicDetailCommentReplyData reply = commentData.getReplyDatas().get(replyPosition);
        DynamicDetailUserData reply_user = reply.getReply_user();
        view.startPersonCenterView(reply_user.getId());
    }

    @Override
    public void onTargetReplyUserClick(int commentPosition, int replyPosition) {
        DynamicDetailCommentData commentData = this.commentData.get(commentPosition);
        DynamicDetailCommentReplyData reply = commentData.getReplyDatas().get(replyPosition);
        DynamicDetailUserData reply_target_user = reply.getReply_target_user();
        view.startPersonCenterView(reply_target_user.getId());
    }

    @Override
    public void onViewAllComment$ReplyClick(int commentPosition) {
        DynamicDetailCommentData commentData = this.commentData.get(commentPosition);
        int comment_id = commentData.getComment_id();
        view.showViewAllComment$ReplyView(comment_id);
    }

    @Override
    public void onReplyContentClick(int commentPosition) {
        DynamicDetailCommentData commentData = this.commentData.get(commentPosition);
        DynamicDetailUserData comment_user = commentData.getComment_user();
        view.setReplyTargetNickName(comment_user.getNickName());
    }

    @Override
    public void onReplyReplyContentClick(int commentPosition, int replyPosition) {
        DynamicDetailCommentData commentData = this.commentData.get(commentPosition);
        DynamicDetailCommentReplyData reply = commentData.getReplyDatas().get(replyPosition);
        DynamicDetailUserData reply_user = reply.getReply_user();
        view.setReplyTargetNickName(reply_user.getNickName());
    }

    @Override
    public void start() {
        initCommentData();
    }

    /**
     * 得到用户数据
     */
    private UserAuths getUserAuths() {
        UserAuths userAuths;
        userAuths = UserInfoSingleton.getInstance().getUserAuths();
        if (userAuths == null) {
            LoginContext.getInstance().setUserState(new LogoutState());
        }
        return userAuths;
    }

    /**
     * 请求评论列表数据
     */
    private void reqCommentList() {
        UserAuths userAuths = getUserAuths();
        if (userAuths == null) {
            view.showReqResult("登录过期，请重新登录");
            return;
        }
        isLoading = true;
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        repository.getComment$Apply(Authorization, dynamic_id, pageControl.getCurrent_page(),
                commentApiCallback);
    }

    /**
     * 评论的回调接口
     */
    private ApiCallback<ApiResponse<CommentListEntity>> commentApiCallback = new
            ApiCallback<ApiResponse<CommentListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CommentListEntity>> response) {
                    isLoading = false;
                    if (!view.isActive()) {
                        return;
                    }
                    view.hideRefreshView();
                    handleData(response.body());
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<CommentListEntity> body) {
                    if (body == null) {
                        return;
                    }
                    handleCommentData(body.getMsg());
                }

                /**
                 * 处理评论数据
                 */
                private void handleCommentData(CommentListEntity msg) {
                    if (msg == null) {
                        return;
                    }

                    //设置页码控制器
                    pageControl.setTotal(msg.getTotal());
                    pageControl.setPer_page(msg.getPer_page());
                    pageControl.setCurrent_page(msg.getCurrent_page());
                    pageControl.setLast_page(msg.getLast_page());
                    pageControl.setNext_page_url(msg.getNext_page_url());
                    pageControl.setPrev_page_url(msg.getPrev_page_url());
                    pageControl.setFrom(msg.getFrom());
                    pageControl.setTo(msg.getTo());

                    List<CommentListEntity.DataBean> data = msg.getData();
                    if (data == null) {
                        return;
                    }

                    switch (action) {
                        case ACTION_REFRESH:
                            commentData.clear();
                            break;
                        case ACTION_LOADMORE:
                            break;
                    }
                    for (CommentListEntity.DataBean dataBean : data) {
                        DynamicDetailCommentData comment = new DynamicDetailCommentData();
                        comment.setComment(dataBean.getComment());
                        comment.setComment_id(dataBean.getComment_id());
                        comment.setComment_time(dataBean.getComment_create_time());
                        comment.setLikeCount(dataBean.getCount_of_like());
                        //设置用户数据
                        DynamicDetailUserData userData = new DynamicDetailUserData();
                        userData.setId(dataBean.getComment_user_id());
                        userData.setNickName(dataBean.getComment_user_nickname());
                        userData.setAvatar(dataBean.getComment_user_avatar());
                        comment.setComment_user(userData);
                        //设置是否点赞
                        if (dataBean.getHas_like() == 0) {
                            comment.setHas_like(false);
                        } else {
                            comment.setHas_like(true);
                        }
                        //设置回复评论数据
                        List<CommentListEntity.DataBean.ReplyBean> replyDatas = dataBean.getReply();
                        if (replyDatas == null || replyDatas.size() <= 0) {
                            comment.setReplyDatas(null);
                            comment.setReplyCount(0);
                        } else {
                            List<DynamicDetailCommentReplyData> replys = new ArrayList<>();
                            for (CommentListEntity.DataBean.ReplyBean applyData :
                                    replyDatas) {
                                DynamicDetailCommentReplyData apply =
                                        new DynamicDetailCommentReplyData();
                                apply.setReply_id(applyData.getReply_id());
                                apply.setComment(applyData.getComment());
                                apply.setReply_time(applyData.getReply_create_time());
                                //设置发送者
                                DynamicDetailUserData reply_user = new DynamicDetailUserData();
                                reply_user.setId(applyData.getReply_user_id());
                                reply_user.setAvatar(applyData.getReply_user_avatar());
                                reply_user.setNickName(applyData.getReply_user_nickname());
                                apply.setReply_user(reply_user);
                                //设置目标用户
                                DynamicDetailUserData target_user = new DynamicDetailUserData();
                                target_user.setId(applyData.getTarget_user_id());
                                target_user.setAvatar(applyData.getTarget_user_avatar());
                                target_user.setNickName(applyData.getTarget_user_nickname());
                                apply.setReply_target_user(target_user);

                                //放入总的集合中
                                replys.add(apply);
                            }
                            comment.setReplyCount(replyDatas.size());
                            comment.setReplyDatas(replys);
                        }
                        commentData.add(comment);
                    }
                    //绑定数据
                    bindDataToView();
                }

                @Override
                public void onError(Response<ApiResponse<CommentListEntity>> response,
                                    String error, ApiResponse<CommentListEntity> apiResponse) {
                    isLoading = false;
                    if (view.isActive()) {
                        view.showReqResult("请求错误");
                        view.hideRefreshView();
                    }
                }

                @Override
                public void onFail() {
                    isLoading = false;
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                        view.hideRefreshView();
                    }
                }
            };

    /**
     * 发送评论的回调接口
     */
    private ApiCallback<ApiResponse<CommentSendEntity>> sendCommentApiCallback = new
            ApiCallback<ApiResponse<CommentSendEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CommentSendEntity>> response) {
                    if (view.isActive()) {
                        view.showReqResult("评论成功");
                        view.dismissLoading();
                        view.clearComment$HideSoftInputView();
                        onRefresh();
                    }
                }

                @Override
                public void onError(Response<ApiResponse<CommentSendEntity>> response,
                                    String error, ApiResponse<CommentSendEntity> apiResponse) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("发送失败");
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("发送失败");
                    }
                }
            };
    /**
     * 回复评论的回调接口
     */
    private ApiCallback<ApiResponse<CommentReplyEntity>> replyApiCallback =
            new ApiCallback<ApiResponse<CommentReplyEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CommentReplyEntity>> response) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.clearComment$HideSoftInputView();
                        view.showReqResult("回复成功");
                        onRefresh();
                    }
                }

                @Override
                public void onError(Response<ApiResponse<CommentReplyEntity>> response, String error,
                                    ApiResponse<CommentReplyEntity> apiResponse) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("回复错误");
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("回复失败");
                    }
                }
            };
}
