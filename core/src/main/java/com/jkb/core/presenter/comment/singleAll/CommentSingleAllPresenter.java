package com.jkb.core.presenter.comment.singleAll;

import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.comment.Comment$ReplyEntity;
import com.jkb.api.entity.comment.CommentReplyEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.comment.singleAll.CommentSingleAllContract;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailUserData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentReplyData;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.comment.singleAll.CommentSingleAllRepository;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 单个所有评论和回复的评论页面P层
 * Created by JustKiddingBaby on 2016/9/21.
 */

public class CommentSingleAllPresenter implements CommentSingleAllContract.Presenter {

    private static final String TAG = "SingleAllP";
    private CommentSingleAllContract.View view;
    private CommentSingleAllRepository repository;

    //其他
    private int comment_id;
    private int dynamic_id;
    private DynamicDetailCommentData commentData;//评论内容
    private boolean isCached = false;//是否有缓存
    private boolean isLoading = false;//正在加载

    public CommentSingleAllPresenter(CommentSingleAllContract.View view,
                                     CommentSingleAllRepository repository) {
        this.view = view;
        this.repository = repository;

        commentData = new DynamicDetailCommentData();
        List<DynamicDetailCommentReplyData> replyDatas = new ArrayList<>();
        commentData.setReplyDatas(replyDatas);

        this.view.setPresenter(this);
    }

    @Override
    public void initComment$ApplyData() {
        initComment_id();
        initDynamic_id();
        if (isCached) {//又缓存的数据
            //设置数据
            bindDataToView();
        } else {//否则缓存过期
            onRefresh();//刷新数据
        }
    }

    @Override
    public void initComment_id() {
        comment_id = view.bindCommentId();
    }

    @Override
    public void initDynamic_id() {
        Log.d(TAG, "dynamic_id=" + dynamic_id);
        dynamic_id = view.bindDynamicId();
    }

    @Override
    public void bindDataToView() {
        if (!view.isActive()) {
            return;
        }
        isCached = true;
        //设置评论数据
        view.setCommentContent(commentData.getComment());
        view.setCommentCreate_Time(commentData.getComment_time());
        DynamicDetailUserData comment_user = commentData.getComment_user();
        view.setCommentUserAvatar(comment_user.getAvatar());
        view.setCommentUserName(comment_user.getNickName());
        view.setHasFavorite(commentData.isHas_like());
        view.setLikeCount(commentData.getLikeCount());

        view.setReplyData(commentData.getReplyDatas());
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        view.showRefreshView();//设置刷新动画
        reqComment$ReplyData();
    }

    @Override
    public void sendComment(String comment) {
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
        Log.d(TAG, "dynamic_id=" + dynamic_id);
        repository.sendComment(Authorization, user_id, dynamic_id, comment, sendCommentApiCallback);
    }

    @Override
    public void commentReply(String comment) {
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
        int user_id = commentData.getComment_user().getId();
        int comment_id = commentData.getComment_id();
        view.showLoading("");
        reqSubmitReply(Authorization, user_id, comment_id, comment);
    }

    @Override
    public void commentReply(int replyPosition, String comment) {
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
        int comment_id = commentData.getComment_id();
        int user_id = commentData.getReplyDatas().get(replyPosition)
                .getReply_user().getId();
        view.showLoading("");
        reqSubmitReply(Authorization, user_id, comment_id, comment);
    }

    @Override
    public void onLikeClick() {
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
        int target_id = commentData.getComment_id();
        repository.likeComment(Authorization, user_id, target_id, likeApiCallback);
    }

    @Override
    public void onCommentUserClick() {
        int user_id = commentData.getComment_user().getId();
        view.startPersonCenterView(user_id);
    }

    @Override
    public void onReplyUserClick(int position) {
        List<DynamicDetailCommentReplyData> replyDatas = commentData.getReplyDatas();
        DynamicDetailCommentReplyData replyData = replyDatas.get(position);
        DynamicDetailUserData reply_user = replyData.getReply_user();
        view.startPersonCenterView(reply_user.getId());
    }

    @Override
    public void onTargetReplyUserClick(int position) {
        List<DynamicDetailCommentReplyData> replyDatas = commentData.getReplyDatas();
        DynamicDetailCommentReplyData replyData = replyDatas.get(position);
        DynamicDetailUserData reply_target_user = replyData.getReply_target_user();
        view.startPersonCenterView(reply_target_user.getId());
    }

    @Override
    public void onReplyContentClick() {
        DynamicDetailUserData comment_user = commentData.getComment_user();
        view.setReplyTargetNickName(comment_user.getNickName());
    }

    @Override
    public void onReplyReplyContentClick(int replyPosition) {
        DynamicDetailCommentReplyData replyData = commentData.getReplyDatas().get(replyPosition);
        DynamicDetailUserData comment_user = replyData.getReply_user();
        view.setReplyTargetNickName(comment_user.getNickName());
    }

    @Override
    public void start() {
        initComment$ApplyData();
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
     * 请求评论和回复的单个数据
     */
    private void reqComment$ReplyData() {
        UserAuths userAuths = getUserAuths();
        if (userAuths == null) {
            view.showReqResult("登录过期，请重新登录");
            return;
        }
        isLoading = true;
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        repository.getSingleComment$Apply(Authorization, comment_id,
                comment$ReplyApiCallback);
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

    /**
     * 请求评论和回复的接口
     */
    private ApiCallback<ApiResponse<Comment$ReplyEntity>> comment$ReplyApiCallback =
            new ApiCallback<ApiResponse<Comment$ReplyEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<Comment$ReplyEntity>> response) {
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
                private void handleData(ApiResponse<Comment$ReplyEntity> body) {
                    if (body == null) {
                        return;
                    }
                    handleComment$ReplyData(body.getMsg());
                }

                /**
                 * 处理评论和回复数据
                 */
                private void handleComment$ReplyData(Comment$ReplyEntity msg) {
                    if (msg == null) {
                        return;
                    }
                    Comment$ReplyEntity.CommentBean comment = msg.getComment();
                    handleCommentData(comment);
                    List<Comment$ReplyEntity.ReplyBean> reply = msg.getReply();
                    handleReplyData(reply);
                    bindDataToView();
                }

                /**
                 * 处理回复数据
                 */
                private void handleReplyData(List<Comment$ReplyEntity.ReplyBean> replys) {
                    if (replys == null) {
                        return;
                    }

                    List<DynamicDetailCommentReplyData> replyDatas = commentData.getReplyDatas();
                    replyDatas.clear();

                    //设置其他数据
                    if (replys == null || replys.size() <= 0) {
                        replyDatas.clear();
                        commentData.setReplyCount(0);
                        commentData.setReplyDatas(replyDatas);
                        return;
                    }
                    commentData.setReplyCount(replys.size());
                    for (int i = 0; i < replys.size(); i++) {
                        Comment$ReplyEntity.ReplyBean dataBean = replys.get(i);
                        DynamicDetailCommentReplyData replyData = new DynamicDetailCommentReplyData();

                        replyData.setReply_id(dataBean.getReply_id());
                        replyData.setComment(dataBean.getReply());
                        replyData.setReply_time(dataBean.getCreated_at());
                        //设置发送者
                        DynamicDetailUserData reply_user = new DynamicDetailUserData();
                        reply_user.setId(dataBean.getReply_user_id());
                        reply_user.setAvatar(dataBean.getReply_user_avatar());
                        reply_user.setNickName(dataBean.getReply_user_nickname());
                        replyData.setReply_user(reply_user);
                        //设置目标用户
                        DynamicDetailUserData target_user = new DynamicDetailUserData();
                        target_user.setId(dataBean.getTarget_user_id());
                        target_user.setAvatar(dataBean.getTarget_user_avatar());
                        target_user.setNickName(dataBean.getTarget_user_nickname());
                        replyData.setReply_target_user(target_user);

                        //放入总的集合中
                        replyDatas.add(replyData);
                    }
                    commentData.setReplyDatas(replyDatas);
                }

                /**
                 * 处理评论数据
                 */
                private void handleCommentData(Comment$ReplyEntity.CommentBean comment) {
                    if (comment == null) {
                        return;
                    }
                    commentData.setComment_id(comment_id);
                    commentData.setComment(comment.getComment());
                    commentData.setLikeCount(comment.getCount_of_like());
                    commentData.setComment_time(comment.getCreated_at());
                    int has_like = comment.getHas_like();
                    if (has_like == 0) {
                        commentData.setHas_like(false);
                    } else {
                        commentData.setHas_like(true);
                    }
                    //设置用户数据
                    DynamicDetailUserData comment_user = new DynamicDetailUserData();
                    comment_user.setId(comment.getComment_user_id());
                    comment_user.setNickName(comment.getComment_user_nickname());
                    comment_user.setAvatar(comment.getComment_user_avatar());
                    commentData.setComment_user(comment_user);
                }

                @Override
                public void onError(Response<ApiResponse<Comment$ReplyEntity>> response,
                                    String error, ApiResponse<Comment$ReplyEntity> apiResponse) {
                    isLoading = false;
                    if (view.isActive()) {
                        view.showReqResult(error);
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
     * 点赞/取消点赞评论的接口
     */
    private ApiCallback<ApiResponse<OperationActionEntity>> likeApiCallback = new
            ApiCallback<ApiResponse<OperationActionEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                    if (view.isActive()) {
                        view.showReqResult("操作成功");
                        //设置局部刷新
                        boolean isFavorite = !commentData.isHas_like();
                        commentData.setHas_like(isFavorite);
                        if (isFavorite) {
                            commentData.setLikeCount(commentData.getLikeCount() + 1);
                        } else {
                            commentData.setLikeCount(commentData.getLikeCount() - 1);
                        }
                        bindDataToView();
                    }
                }

                @Override
                public void onError(Response<ApiResponse<OperationActionEntity>> response,
                                    String error, ApiResponse<OperationActionEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("操作错误");
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
                        view.showReqResult(error);
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
                        view.showReqResult(error);
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
