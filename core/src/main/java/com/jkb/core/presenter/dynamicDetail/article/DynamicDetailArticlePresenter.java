package com.jkb.core.presenter.dynamicDetail.article;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.dynamic.DynamicArticleEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.dynamicDetail.article.DynamicDetailArticleContract;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailArticleData;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailUserData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentReplyData;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.dynamicDetail.article.DynamicDetailArticleRepository;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.LogUtils;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 动态详情：文章的P层
 * Created by JustKiddingBaby on 2016/9/25.
 */

public class DynamicDetailArticlePresenter implements DynamicDetailArticleContract.Presenter {

    private static final String TAG = "DynamicDetailArticleP";
    private DynamicDetailArticleContract.View view;
    private DynamicDetailArticleRepository repository;

    //其他
    private int dynamic_id = 0;
    private DynamicDetailArticleData dynamicDetailArticleData;
    private List<DynamicDetailCommentData> commentData;//评论内容
    private boolean isCached = false;//是否有缓存
    private boolean isLoading = false;//是否正在刷新

    public DynamicDetailArticlePresenter(@NonNull DynamicDetailArticleContract.View view,
                                         @NonNull DynamicDetailArticleRepository repository) {
        this.view = view;
        this.repository = repository;

        dynamicDetailArticleData = new DynamicDetailArticleData();
        commentData = new ArrayList<>();

        this.view.setPresenter(this);
    }

    @Override
    public void initArticleData() {
        initDynamic_id();
    }

    @Override
    public void initDynamic_id() {
        dynamic_id = view.bindDynamicId();
        //绑定评论的数据
        view.setCommentData(commentData);
        view.hideArticleContentView();
        if (isCached) {
            bindDataToView();
            return;
        }
        //请求数据
        onRefresh();
    }

    @Override
    public void bindDataToView() {
        isCached = true;
        isLoading = false;
        //绑定数据
        if (!view.isActive()) {
            return;
        }
        view.showArticleContentView();
        //设置用户数据
        DynamicDetailUserData userData =
                dynamicDetailArticleData.getDynamicDetailUserData();
        if (userData != null) {
            view.setUserNickName(userData.getNickName());
            view.setUserHeadImg(userData.getAvatar());
        }
        //处理数据
        //设置文章内容的第一张图片为背景图片
        filterArticleData();
        //设置文章数据
        view.setArticleContent(dynamicDetailArticleData.getArticles());
        //设置其他数据
        view.setArticleTitle(dynamicDetailArticleData.getTitle());
        view.setPostTime(dynamicDetailArticleData.getCreated_at());
        view.setHasFavorite(dynamicDetailArticleData.isHasFavorite());
        view.setCommentCount(dynamicDetailArticleData.getComments_count());
        view.setFavoriteCount(dynamicDetailArticleData.getOperation_count());
        view.setTag(dynamicDetailArticleData.getTag());
        view.setTagCount(0);//设置标签数目
        view.setArticleBgImg(dynamicDetailArticleData.getArticleBgImg());
        //筛选评论数据
        filterCommentData();
        //设置评论数据
        view.setCommentData(commentData);
    }

    /**
     * 筛选评论数据
     */
    private void filterCommentData() {
        if (commentData == null || commentData.size() == 0) {
            return;
        }
        if (commentData.size() <= 3) {
            return;
        }
        List<DynamicDetailCommentData> comments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            comments.add(commentData.get(i));
        }
        commentData = comments;
        //筛选回复数据
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

    /**
     * 筛选文章第一章背景图片
     */
    private void filterArticleData() {
        if (dynamicDetailArticleData == null) {
            return;
        }
        //已经有背景说明该数据已经做过处理
        if (!StringUtils.isEmpty(dynamicDetailArticleData.getArticleBgImg())) {
            return;
        }
        List<DynamicDetailArticleData.ArticleContent> articles = dynamicDetailArticleData.getArticles();
        if (articles == null || articles.size() == 0) {
            return;
        }
        for (int i = 0; i < articles.size(); i++) {
            DynamicDetailArticleData.ArticleContent content = articles.get(i);
            if (StringUtils.isEmpty(content.getImg())) {
                continue;
            } else {
                dynamicDetailArticleData.setArticleBgImg(content.getImg());
                dynamicDetailArticleData.getArticles().get(i).setImg(null);
            }
            break;
        }
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            return;
        }
        isCached = false;
        //清空数据
        dynamicDetailArticleData = new DynamicDetailArticleData();
        int user_id = 0;
        if (LoginContext.getInstance().isLogined()) {
            UserAuths userAuths = getUserAuths();
            if (userAuths == null) {
                view.showReqResult("登录过期，请重新登录");
                return;
            } else {
                user_id = userAuths.getUser_id();
            }
        }
        view.showRefreshView();
        repository.getArticleDynamic(user_id, dynamic_id, dynamicArticleApiCallback);
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onAuthClick() {
        //点击作者的时候
        DynamicDetailUserData userData = dynamicDetailArticleData.getDynamicDetailUserData();
        view.startPersonCenterActivity(userData.getId());
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
    public void onCommentUserClick(int commentPosition) {
        DynamicDetailCommentData commentData = this.commentData.get(commentPosition);
        DynamicDetailUserData comment_user = commentData.getComment_user();
        int user_id = comment_user.getId();
        view.startPersonCenterActivity(user_id);
    }

    @Override
    public void onReplyUserClick(int commentPosition, int replyPosition) {
        DynamicDetailCommentData commentData = this.commentData.get(commentPosition);
        DynamicDetailCommentReplyData reply = commentData.getReplyDatas().get(replyPosition);
        DynamicDetailUserData reply_user = reply.getReply_user();
        view.startPersonCenterActivity(reply_user.getId());
    }

    @Override
    public void onTargetReplyUserClick(int commentPosition, int replyPosition) {
        DynamicDetailCommentData commentData = this.commentData.get(commentPosition);
        DynamicDetailCommentReplyData reply = commentData.getReplyDatas().get(replyPosition);
        DynamicDetailUserData reply_target_user = reply.getReply_target_user();
        view.startPersonCenterActivity(reply_target_user.getId());
    }

    @Override
    public void onViewAllComment$ReplyClick(int commentPosition) {
        DynamicDetailCommentData commentData = this.commentData.get(commentPosition);
        int comment_id = commentData.getComment_id();
        view.showViewAllComment$ReplyView(comment_id);
    }

    @Override
    public void onArticlePictureClick(int position) {
        LogUtils.d(DynamicDetailArticlePresenter.class, "position=" + position);
        List<DynamicDetailArticleData.ArticleContent>
                articles = dynamicDetailArticleData.getArticles();
        if (articles == null || articles.size() == 0) {
            return;
        }
        ArrayList<String> pictures = new ArrayList<>();
        for (DynamicDetailArticleData.ArticleContent content :
                articles) {
            if (!StringUtils.isEmpty(content.getImg())) {
                pictures.add(content.getImg());
            }
        }
        view.showPicturesBrowserView(pictures, position);
    }

    @Override
    public void start() {
        initArticleData();
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
     * 请求评论的接口
     */
    private void reqCommentData() {
        UserAuths userAuths = getUserAuths();
        if (userAuths == null) {
            return;
        }
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        repository.getComment$Apply(Authorization, dynamic_id, 1, "create|desc", commentApiCallback);
    }

    /**
     * 获取文章动态的回调接口
     */
    private ApiCallback<ApiResponse<DynamicArticleEntity>> dynamicArticleApiCallback =
            new ApiCallback<ApiResponse<DynamicArticleEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicArticleEntity>> response) {
                    if (view.isActive()) {
                        view.hideRefreshView();
                        handleData(response.body());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<DynamicArticleEntity> body) {
                    if (body == null) {
                        return;
                    }
                    handleDynamicData(body.getMsg());
                }

                /**
                 * 处理动态数据
                 */
                private void handleDynamicData(DynamicArticleEntity entity) {
                    if (entity == null) {
                        return;
                    }
                    handleNormalData(entity.getDynamic());
                }

                /**
                 * 处理普通动态数据
                 */
                private void handleNormalData(DynamicArticleEntity.DynamicBean dynamic) {
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
                private void changeReqDataToCacheData(DynamicArticleEntity.DynamicBean dynamic) {
                    dynamicDetailArticleData.setId(dynamic.getId());
                    dynamicDetailArticleData.setTitle(dynamic.getTitle());
                    dynamicDetailArticleData.setTag(dynamic.getTag());
                    dynamicDetailArticleData.setComments_count(dynamic.getComments_count());
                    dynamicDetailArticleData.setCreated_at(dynamic.getCreated_at());
                    dynamicDetailArticleData.setOperation_count(dynamic.getOperation_count());
                    //设置是否喜欢
                    int hasFavorite = dynamic.getHasFavorite();
                    if (hasFavorite == 0) {
                        dynamicDetailArticleData.setHasFavorite(false);
                    } else {
                        dynamicDetailArticleData.setHasFavorite(true);
                    }
                    //设置文章内容
                    DynamicArticleEntity.DynamicBean.ContentBean content = dynamic.getContent();
                    if (content != null) {
                        List<DynamicArticleEntity.DynamicBean.ContentBean.ArticleBean> reqArticles =
                                content.getArticle();
                        if (reqArticles != null) {
                            List<DynamicDetailArticleData.ArticleContent> articles = new ArrayList<>();
                            for (DynamicArticleEntity.DynamicBean.ContentBean.ArticleBean reqArticle :
                                    reqArticles) {
                                DynamicDetailArticleData.ArticleContent article =
                                        new DynamicDetailArticleData.ArticleContent();
                                article.setDoc(reqArticle.getDoc());
                                article.setImg(reqArticle.getImg());
                                articles.add(article);
                            }
                            dynamicDetailArticleData.setArticles(articles);
                        }
                    } else {
                        dynamicDetailArticleData.setArticles(null);
                    }
                    //设置用户数据
                    DynamicArticleEntity.DynamicBean.UserBean user = dynamic.getUser();
                    if (user != null) {
                        DynamicDetailUserData userData = new DynamicDetailUserData();
                        userData.setId(user.getId());
                        userData.setAvatar(user.getAvatar());
                        userData.setNickName(user.getNickname());
                        dynamicDetailArticleData.setDynamicDetailUserData(userData);
                    }
                }

                @Override
                public void onError(
                        Response<ApiResponse<DynamicArticleEntity>> response, String error,
                        ApiResponse<DynamicArticleEntity> apiResponse) {
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


                    commentData.clear();
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
                        dynamicDetailArticleData.setHasFavorite(
                                !dynamicDetailArticleData.isHasFavorite());
                        if (dynamicDetailArticleData.isHasFavorite()) {
                            dynamicDetailArticleData.setOperation_count(
                                    dynamicDetailArticleData.getOperation_count() + 1);
                        } else {
                            dynamicDetailArticleData.setOperation_count(
                                    dynamicDetailArticleData.getOperation_count() - 1);
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
}
