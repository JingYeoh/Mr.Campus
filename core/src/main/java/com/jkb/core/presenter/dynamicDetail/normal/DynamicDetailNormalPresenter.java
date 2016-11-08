package com.jkb.core.presenter.dynamicDetail.normal;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.dynamic.DynamicNormalEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailNormalData;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailUserData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.core.contract.dynamicDetail.normal.DynamicDetailNormalContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.dynamicDetail.normal.DynamicDetailNormalRepository;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 动态详情——普通：P层
 * Created by JustKiddingBaby on 2016/9/17.
 */

public class DynamicDetailNormalPresenter implements DynamicDetailNormalContract.Presenter {

    //data
    private DynamicDetailNormalContract.View view;
    private DynamicDetailNormalRepository repository;

    //其他
    private int dynamic_id;
    private DynamicDetailNormalData dynamicDetailNormalData;
    private List<DynamicDetailCommentData> commentData;//评论内容
    private boolean isCached = false;//是否有缓存

    public DynamicDetailNormalPresenter(
            DynamicDetailNormalContract.View view,
            DynamicDetailNormalRepository repository) {
        this.view = view;
        this.repository = repository;

        dynamicDetailNormalData = new DynamicDetailNormalData();
        commentData = new ArrayList<>();

        this.view.setPresenter(this);
    }

    @Override
    public void initNormalData() {
        initDynamic_id();
        view.hideNormalContentView();
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
        view.showNormalContentView();
        //设置用户数据
        DynamicDetailUserData userData =
                dynamicDetailNormalData.getDynamicDetailUserData();
        if (userData != null) {
            view.setUserNickName(userData.getNickName());
            view.setUserHeadImg(userData.getAvatar());
        }
        //设置其他数据
        view.setContentImages(dynamicDetailNormalData.getImgs());
        view.setContentValue(dynamicDetailNormalData.getDoc());
        view.setPostTime(dynamicDetailNormalData.getCreated_at());
        view.setHasFavorite(dynamicDetailNormalData.isHasFavorite());
        view.setCommentCount(dynamicDetailNormalData.getComments_count());
        view.setFavoriteCount(dynamicDetailNormalData.getOperation_count());
        view.setTag(dynamicDetailNormalData.getTag());
        view.setTagCount(0);//设置标签数目

        //绑定评论的数据
        view.setCommentData(commentData);
    }

    @Override
    public void onRefresh() {
        //请求数据
        view.showRefreshView();
        isCached = false;
        UserAuths userAuths = getUserAuths();
        if (userAuths == null) {
            view.showReqResult("登录过期，请重新登录");
            return;
        }
        repository.getNormalDynamic(userAuths.getUser_id(), dynamic_id, dynamicNormalApiCallback);
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
    public int getUser_id(int position) {
        int user_id;
        DynamicDetailCommentData commentData = this.commentData.get(position);
        DynamicDetailUserData comment_user = commentData.getComment_user();
        user_id = comment_user.getId();
        return user_id;
    }

    @Override
    public void start() {
        initNormalData();//初始化普通动态
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
    private ApiCallback<ApiResponse<DynamicNormalEntity>> dynamicNormalApiCallback =
            new ApiCallback<ApiResponse<DynamicNormalEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicNormalEntity>> response) {
                    if (view.isActive()) {
                        view.hideRefreshView();
                        handleData(response.body());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<DynamicNormalEntity> body) {
                    if (body == null) {
                        return;
                    }
                    handleDynamicData(body.getMsg());
                }

                /**
                 * 处理动态数据
                 */
                private void handleDynamicData(DynamicNormalEntity entity) {
                    if (entity == null) {
                        return;
                    }
                    handleNormalData(entity.getDynamic());
                }

                /**
                 * 处理普通动态数据
                 */
                private void handleNormalData(DynamicNormalEntity.DynamicBean dynamic) {
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
                private void changeReqDataToCacheData(DynamicNormalEntity.DynamicBean dynamic) {
                    dynamicDetailNormalData.setId(dynamic.getId());
                    dynamicDetailNormalData.setTitle(dynamic.getTitle());
                    dynamicDetailNormalData.setTag(dynamic.getTag());
                    dynamicDetailNormalData.setComments_count(dynamic.getComments_count());
                    dynamicDetailNormalData.setCreated_at(dynamic.getCreated_at());
                    dynamicDetailNormalData.setOperation_count(dynamic.getOperation_count());
                    //设置是否喜欢
                    int hasFavorite = dynamic.getHasFavorite();
                    if (hasFavorite == 0) {
                        dynamicDetailNormalData.setHasFavorite(false);
                    } else {
                        dynamicDetailNormalData.setHasFavorite(true);
                    }
                    //设置内容
                    DynamicNormalEntity.DynamicBean.ContentBean content = dynamic.getContent();
                    if (content != null) {
                        DynamicNormalEntity.DynamicBean.ContentBean.NormalBean normal =
                                content.getNormal();
                        if (normal != null) {
                            dynamicDetailNormalData.setDoc(normal.getDoc());
                            if (normal.getImg() != null) {
                                dynamicDetailNormalData.setImgs(
                                        normal.getImg().toArray(new String[normal.getImg().size()]));
                            }
                        }
                    }
                    //设置用户数据
                    DynamicNormalEntity.DynamicBean.UserBean user = dynamic.getUser();
                    if (user != null) {
                        DynamicDetailUserData userData = new DynamicDetailUserData();
                        userData.setId(user.getId());
                        userData.setAvatar(user.getAvatar());
                        userData.setNickName(user.getNickname());
                        dynamicDetailNormalData.setDynamicDetailUserData(userData);
                    }
                }

                @Override
                public void onError(
                        Response<ApiResponse<DynamicNormalEntity>> response, String error,
                        ApiResponse<DynamicNormalEntity> apiResponse) {
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
     * 请求评论的接口
     */
    private void reqCommentData() {
        UserAuths userAuths = getUserAuths();
        if (userAuths == null) {
            return;
        }
        String Authorization = Config.HEADER_BEARER + userAuths.getToken();
        repository.getComment$Apply(Authorization, dynamic_id, 1, commentApiCallback);
    }

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
                        dynamicDetailNormalData.setHasFavorite(
                                !dynamicDetailNormalData.isHasFavorite());
                        if (dynamicDetailNormalData.isHasFavorite()) {
                            dynamicDetailNormalData.setOperation_count(
                                    dynamicDetailNormalData.getOperation_count() + 1);
                        } else {
                            dynamicDetailNormalData.setOperation_count(
                                    dynamicDetailNormalData.getOperation_count() - 1);
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
}
