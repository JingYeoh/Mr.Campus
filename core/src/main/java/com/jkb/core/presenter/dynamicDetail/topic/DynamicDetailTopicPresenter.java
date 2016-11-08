package com.jkb.core.presenter.dynamicDetail.topic;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentReplyEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.dynamic.DynamicTopicEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailTopicData;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailUserData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentReplyData;
import com.jkb.core.contract.dynamicDetail.topic.DynamicDetailTopicContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.dynamicDetail.topic.DynamicDetailTopicRepository;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 动态详情：话题的P层
 * Created by JustKiddingBaby on 2016/9/23.
 */

public class DynamicDetailTopicPresenter implements DynamicDetailTopicContract.Presenter {

    //data
    private static final String TAG = "DynamicDetailTopicP";
    private DynamicDetailTopicContract.View view;
    private DynamicDetailTopicRepository repository;

    //其他
    private int dynamic_id;
    private DynamicDetailTopicData dynamicDetailTopicData;
    private List<DynamicDetailCommentData> commentData;//评论内容
    private boolean isCached = false;//是否有缓存
    private boolean isLoading = false;//是否正在刷新
    //分页
    private PageControlEntity pageControl;
    private int action = ACTION_REFRESH;
    private static final int ACTION_REFRESH = 0;
    private static final int ACTION_LOADMORE = 1;
    //排序
    private static final String ORDER_HOT_DESC = "hot|desc";
    private static final String ORDER_CREATE_ASC = "create|asc";
    private static final String ORDER_CREATE_DESC = "create|desc";
    private String orderType = ORDER_CREATE_DESC;


    public DynamicDetailTopicPresenter(
            @NonNull DynamicDetailTopicContract.View view,
            @NonNull DynamicDetailTopicRepository repository) {
        this.view = view;
        this.repository = repository;

        dynamicDetailTopicData = new DynamicDetailTopicData();
        commentData = new ArrayList<>();
        pageControl = new PageControlEntity();

        this.view.setPresenter(this);
    }

    @Override
    public void initTopicData() {
        initDynamic_id();
        view.hideTopicContentView();
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
        isLoading = false;
        //绑定数据
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        view.showTopicContentView();
        //设置用户数据
        DynamicDetailUserData userData =
                dynamicDetailTopicData.getDynamicDetailUserData();
        if (userData != null) {
            view.setUserNickName(userData.getNickName());
            view.setUserHeadImg(userData.getAvatar());
        }
        //设置其他数据
        view.setTopicTitle(dynamicDetailTopicData.getTitle());
        view.setContentImages(dynamicDetailTopicData.getImg());
        view.setContentValue(dynamicDetailTopicData.getDoc());
        view.setPostTime(dynamicDetailTopicData.getCreated_at());
        view.setHasFavorite(dynamicDetailTopicData.isHasFavorite());
        view.setCommentCount(dynamicDetailTopicData.getComments_count());
        view.setFavoriteCount(dynamicDetailTopicData.getOperation_count());
        view.setPartInCount(dynamicDetailTopicData.getPartIn_count());
        view.setTag(dynamicDetailTopicData.getTag());
        view.setTagCount(0);//设置标签数目

        //筛选回复的个数至只有三条
        filterCommentReplyData();
        //绑定评论的数据
        view.setCommentData(commentData);

        if (pageControl.getCurrent_page() < pageControl.getLast_page()) {
            view.showLoadMoreView();
        } else {
            view.loadCompleted();
        }
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
        action = ACTION_REFRESH;
        pageControl.setCurrent_page(1);
        //请求数据
        view.showRefreshView();
        isCached = false;
        int user_id = 0;
        if (!LoginContext.getInstance().isLogined()) {
            user_id = 0;
        } else {
            UserAuths userAuths = getUserAuths();
            if (userAuths == null) {
                user_id = 0;
            } else {
                user_id = userAuths.getUser_id();
            }
        }
        repository.getTopicDynamic(user_id, dynamic_id, dynamicTopicApiCallback);
    }

    @Override
    public void onLoadMore() {
        //加载更多的评论
        if (isLoading) {
            return;
        }
        action = ACTION_LOADMORE;
        if (pageControl.getCurrent_page() >= pageControl.getLast_page()) {
            return;
        }
        //设置当前页数+1
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        //请求评论接口
        reqCommentData();
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
                            view.showReqResult(error);
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
    public void onLikeDynamicClick() {
        //点赞
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
        repository.favorite(Authorization, user_id, dynamic_id, favoriteApiCallback);
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
        repository.sendComment(Authorization, user_id, dynamic_id, comment, sendCommentApiCallback);
    }

    @Override
    public void commitReply(int commentPosition, String comment) {
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
    public void commitReply(int commentPosition, int replyPosition, String comment) {
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
        int comment_id = commentData.get(commentPosition).getComment_id();
        int user_id = commentData.get(commentPosition).getReplyDatas().get(replyPosition)
                .getReply_user().getId();
        view.showLoading("");
        reqSubmitReply(Authorization, user_id, comment_id, comment);
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
    public void onUserHeadImgClick() {
        DynamicDetailUserData userData = dynamicDetailTopicData.getDynamicDetailUserData();
        if (userData != null) {
            view.startPersonCenterActivity(userData.getId());
        }
    }

    @Override
    public void onOrderByHot() {
        isLoading = true;
        pageControl.setCurrent_page(1);
        action = ACTION_REFRESH;
        orderType = ORDER_HOT_DESC;
        view.showLoading("");
        view.setOrderByHotView();
        //请求数据
        reqCommentData();
    }

    @Override
    public void onOrderByDesc$Asc() {
        isLoading = true;
        pageControl.setCurrent_page(1);
        action = ACTION_REFRESH;
        view.showLoading("");
        switch (orderType) {
            case ORDER_CREATE_ASC:
                orderType = ORDER_CREATE_DESC;
                view.setOrderByDesc();
                break;
            case ORDER_CREATE_DESC:
                orderType = ORDER_CREATE_ASC;
                view.setOrderByAsc();
                break;
            default:
                orderType = ORDER_CREATE_DESC;
                view.setOrderByDesc();
                break;
        }
        //请求数据
        reqCommentData();
    }

    @Override
    public void start() {
        initTopicData();
    }


    /**
     * 请求评论的接口
     */
    private void reqCommentData() {
        if (!LoginContext.getInstance().isLogined()) {
            repository.getComment$Apply(null, dynamic_id, pageControl.getCurrent_page(), orderType,
                    commentApiCallback);
            return;
        }
        UserAuths userAuths = getUserAuths();
        if (userAuths == null) {
            repository.getComment$Apply(null, dynamic_id, pageControl.getCurrent_page(), orderType,
                    commentApiCallback);
            return;
        }
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        repository.getComment$Apply(Authorization, dynamic_id, pageControl.getCurrent_page(),
                orderType, commentApiCallback);
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
     * 获取普通动态的回调接口
     */
    private ApiCallback<ApiResponse<DynamicTopicEntity>> dynamicTopicApiCallback =
            new ApiCallback<ApiResponse<DynamicTopicEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicTopicEntity>> response) {
                    if (view.isActive()) {
                        view.hideRefreshView();
                        handleData(response.body());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<DynamicTopicEntity> body) {
                    if (body == null) {
                        return;
                    }
                    handleDynamicData(body.getMsg());
                }

                /**
                 * 处理动态数据
                 */
                private void handleDynamicData(DynamicTopicEntity entity) {
                    if (entity == null) {
                        return;
                    }
                    handleNormalData(entity.getDynamic());
                }

                /**
                 * 处理普通动态数据
                 */
                private void handleNormalData(DynamicTopicEntity.DynamicBean dynamic) {
                    if (dynamic == null) {
                        return;
                    }
                    changeReqDataToCacheData(dynamic);
                    //请求comment评论接口
                    reqCommentData();
                    bindDataToView();
                }

                /**
                 * 转换为缓存的数据
                 */
                private void changeReqDataToCacheData(DynamicTopicEntity.DynamicBean dynamic) {
                    dynamicDetailTopicData.setId(dynamic.getId());
                    dynamicDetailTopicData.setTitle(dynamic.getTitle());
                    dynamicDetailTopicData.setTag(dynamic.getTag());
                    dynamicDetailTopicData.setComments_count(dynamic.getComments_count());
                    dynamicDetailTopicData.setCreated_at(dynamic.getCreated_at());
                    dynamicDetailTopicData.setOperation_count(dynamic.getOperation_count());
                    dynamicDetailTopicData.setPartIn_count(dynamic.getParticipation());
                    //设置是否喜欢
                    int hasFavorite = dynamic.getHasFavorite();
                    if (hasFavorite == 0) {
                        dynamicDetailTopicData.setHasFavorite(false);
                    } else {
                        dynamicDetailTopicData.setHasFavorite(true);
                    }
                    //设置内容
                    DynamicTopicEntity.DynamicBean.ContentBean content = dynamic.getContent();
                    if (content != null) {
                        DynamicTopicEntity.DynamicBean.ContentBean.TopicBean normal =
                                content.getTopic();
                        if (normal != null) {
                            dynamicDetailTopicData.setDoc(normal.getDoc());
                            if (normal.getImg() != null) {
                                dynamicDetailTopicData.setImg(
                                        normal.getImg());
                            }
                        }
                    }
                    //设置用户数据
                    DynamicTopicEntity.DynamicBean.UserBean user = dynamic.getUser();
                    if (user != null) {
                        DynamicDetailUserData userData = new DynamicDetailUserData();
                        userData.setId(user.getId());
                        userData.setAvatar(user.getAvatar());
                        userData.setNickName(user.getNickname());
                        dynamicDetailTopicData.setDynamicDetailUserData(userData);
                    }
                }

                @Override
                public void onError(
                        Response<ApiResponse<DynamicTopicEntity>> response, String error,
                        ApiResponse<DynamicTopicEntity> apiResponse) {
                    if (view.isActive()) {
                        view.hideRefreshView();
                        view.showReqResult(error);
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.hideRefreshView();
                        view.showReqResult("请求失败");
                    }
                }
            };
    /**
     * 评论的回调接口
     */
    private ApiCallback<ApiResponse<CommentListEntity>> commentApiCallback = new
            ApiCallback<ApiResponse<CommentListEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CommentListEntity>> response) {
                    if (!view.isActive()) {
                        return;
                    }
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
                    List<CommentListEntity.DataBean> data = msg.getData();
                    if (data == null) {
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

                    Log.i(TAG, "pageControl=" + pageControl.toString());

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
                    view.showReqResult(error);
                }

                @Override
                public void onFail() {

                }
            };
    /**
     * 喜欢动态的接口
     */
    private ApiCallback<ApiResponse<OperationActionEntity>> favoriteApiCallback = new
            ApiCallback<ApiResponse<OperationActionEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                    if (view.isActive()) {
                        view.showReqResult("操作成功");
                        //设置局部刷新
                        dynamicDetailTopicData.setHasFavorite(
                                !dynamicDetailTopicData.isHasFavorite());
                        if (dynamicDetailTopicData.isHasFavorite()) {
                            dynamicDetailTopicData.setOperation_count(
                                    dynamicDetailTopicData.getOperation_count() + 1);
                        } else {
                            dynamicDetailTopicData.setOperation_count(
                                    dynamicDetailTopicData.getOperation_count() - 1);
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
                        view.showReqResult("操作失败");
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
                        reqCommentData();//请求评论内容
                    }
                }

                @Override
                public void onError(Response<ApiResponse<CommentSendEntity>> response,
                                    String error, ApiResponse<CommentSendEntity> apiResponse) {
                    if (view.isActive()) {
                        view.dismissLoading();
//                        view.showReqResult("发送失败");
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
//                        view.showReqResult("回复错误");
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
