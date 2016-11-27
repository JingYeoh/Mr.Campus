package com.jkb.core.presenter.function.special.detail;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.comment.CommentListEntity;
import com.jkb.api.entity.comment.CommentReplyEntity;
import com.jkb.api.entity.comment.CommentSendEntity;
import com.jkb.api.entity.operation.OperationActionEntity;
import com.jkb.api.entity.subject.SubjectEntity;
import com.jkb.core.contract.dynamicDetail.data.DynamicDetailUserData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentData;
import com.jkb.core.contract.dynamicDetail.data.comment.DynamicDetailCommentReplyData;
import com.jkb.core.contract.function.special.detail.data.SubjectDetailData;
import com.jkb.core.contract.function.special.detail.general.SubjectDetailContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.data.PageControlEntity;
import com.jkb.model.dataSource.function.special.detail.SubjectDetailRepertory;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

/**
 * 专题详情的P层
 * Created by JustKiddingBaby on 2016/11/22.
 */

public class SubjectDetailPresenter implements SubjectDetailContract.Presenter {

    private SubjectDetailContract.View view;
    private SubjectDetailRepertory repertory;

    //data
    private int dynamicId = 0;
    private SubjectDetailData mConfessionData;
    private boolean isCached = false;
    private boolean isRefreshing = false;
    private PageControlEntity pageControl;
    private List<DynamicDetailCommentData> mCommentData;

    //排序
    private static final String ORDER_HOT_DESC = "hot|desc";
    private static final String ORDER_CREATE_ASC = "create|asc";
    private static final String ORDER_CREATE_DESC = "create|desc";
    private String orderType = ORDER_CREATE_DESC;

    public SubjectDetailPresenter(
            SubjectDetailContract.View view, SubjectDetailRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        mConfessionData = new SubjectDetailData();
        pageControl = new PageControlEntity();
        mCommentData = new ArrayList<>();

        this.view.setPresenter(this);
    }

    @Override
    public void bindDataToView() {
        isRefreshing = false;
        //绑定假数据
        if (!view.isActive()) {
            return;
        }
        if (mConfessionData == null) {
            view.hideContentView();
            isCached = false;
            return;
        }
        isCached = true;
        view.showContentView();
        view.dismissLoading();
        view.setSubjectTitle(mConfessionData.getSubjectTitle());
        view.setSubjectContent(mConfessionData.getSubjectText());
        view.setSubjectImageUrls(mConfessionData.getImageUlrs());
        //设置个人数据
        view.setUserNickName(mConfessionData.getUserInfo().getNickName());
        view.setUserAvatar(mConfessionData.getUserInfo().getAvatar());
        //设置栏
        view.setCommentCount(mConfessionData.getCount_of_comment());
        view.setFavoriteCount(mConfessionData.getCount_of_favorite());
        view.setFavoriteStatus(mConfessionData.isHasFavorite());

        ////////////////////////////////////////////设置评论的数据
        //筛选回复的个数至只有三条
        filterCommentReplyData();
        //绑定评论的数据
        if (mCommentData.size() == 0) {
            view.showNoneCommentView();
        } else {
            view.setCommentData(mCommentData);
        }
        if (pageControl.getCurrent_page() < pageControl.getLast_page()) {
            view.showLoadMoreView();
        } else {
            view.hideLoadMoreView();
            view.loadCompleted();
        }
    }

    /**
     * 筛选回复个数
     */
    private void filterCommentReplyData() {
        if (mCommentData == null || mCommentData.size() == 0) {
            return;
        }
        for (int p = 0; p < mCommentData.size(); p++) {
            DynamicDetailCommentData comment = mCommentData.get(p);
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
            mCommentData.set(p, comment);
        }
    }

    @Override
    public void initSubjectData() {
        if (isCached) {
            bindDataToView();
        } else {
            onRefresh();
        }
    }

    @Override
    public void onRefresh() {
        isCached = false;
        if (isRefreshing) {
            return;
        }
        mConfessionData = new SubjectDetailData();
        isRefreshing = true;
        int currentUserId = getCurrentUserId();
        repertory.getSubjectDynamic(currentUserId, dynamicId, subjectDetailApiCallback);
    }

    @Override
    public void onRefreshComment() {
        isCached = false;
        //加载更多的评论
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        //设置当前页数+1
        pageControl.setCurrent_page(1);
        mCommentData.clear();
        //请求评论接口
        reqCommentData();
    }

    @Override
    public void loadMoreComment() {
        //加载更多的评论
        if (isRefreshing) {
            return;
        }
        isRefreshing = true;
        isCached = false;
        if (pageControl.getCurrent_page() >= pageControl.getLast_page()) {
            return;
        }
        //设置当前页数+1
        pageControl.setCurrent_page(pageControl.getCurrent_page() + 1);
        //请求评论接口
        reqCommentData();
    }

    @Override
    public void sendComment(String comment) {
        reqSendComment(-1, comment);
    }

    @Override
    public void sendCommitReply(int commentPosition, String comment) {
        DynamicDetailCommentData commentData = mCommentData.get(commentPosition);
        int id = commentData.getComment_id();
        reqSendComment(id, comment);
    }

    @Override
    public void sendCommitReply(int commentPosition, int replyPosition, String comment) {
        DynamicDetailCommentData commentData = mCommentData.get(commentPosition);
        int id = commentData.getComment_id();
        reqSendComment(id, comment);
    }

    @Override
    public void onSubjectPicturesItemClick(int position) {
        position = position < 0 ? 0 : position;
        view.showPictureBrowserView((ArrayList<String>) mConfessionData.getImageUlrs(), position);
    }

    @Override
    public void onUserHeadImgClick() {
        int id = mConfessionData.getUserInfo().getId();
        view.startPersonCenter(id);
    }

    @Override
    public void onFavoriteClick() {
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        int dynamicId;
        if (mConfessionData == null) {
            view.showReqResult("得到动态数据失败");
            return;
        }
        dynamicId = mConfessionData.getId();
        repertory.favorite(authorization, getCurrentUserId(), dynamicId,
                new ApiCallback<ApiResponse<OperationActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("操作成功");
                            boolean hasFavorite = mConfessionData.isHasFavorite();
                            hasFavorite = !hasFavorite;
                            mConfessionData.setHasFavorite(hasFavorite);
                            int count_of_favorite = mConfessionData.getCount_of_favorite();
                            if (hasFavorite) {
                                count_of_favorite++;
                            } else {
                                count_of_favorite--;
                            }
                            mConfessionData.setCount_of_favorite(count_of_favorite);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onError(
                            Response<ApiResponse<OperationActionEntity>> response,
                            String error, ApiResponse<OperationActionEntity> apiResponse) {
                        if (view.isActive()) {
                            view.showReqResult(error);
                            bindDataToView();
                        }
                    }

                    @Override
                    public void onFail() {
                        if (view.isActive()) {
                            view.showReqResult("请求失败");
                            bindDataToView();
                        }
                    }
                });
    }

    @Override
    public void onOrderByHot() {
        orderType = ORDER_HOT_DESC;
        view.showOrderByHotView();
        //请求数据
        onRefreshComment();
    }

    @Override
    public void onOrderByDesc$Asc() {
        switch (orderType) {
            case ORDER_CREATE_ASC:
                orderType = ORDER_CREATE_DESC;
                view.showOrderByDesc();
                break;
            case ORDER_CREATE_DESC:
                orderType = ORDER_CREATE_ASC;
                view.showOrderByAsc();
                break;
            default:
                orderType = ORDER_CREATE_DESC;
                view.showOrderByDesc();
                break;
        }
        //请求数据
        onRefreshComment();
    }

    @Override
    public void onReplyContentClick(int commentPosition) {
        DynamicDetailCommentData commentData = mCommentData.get(commentPosition);
        String nickName = commentData.getComment_user().getNickName();
        view.setTargetName(nickName);
    }

    @Override
    public void onReplyReplyContentClick(int commentPosition, int replyPosition) {
        DynamicDetailCommentData commentData = mCommentData.get(commentPosition);
        List<DynamicDetailCommentReplyData> replyDatas = commentData.getReplyDatas();
        DynamicDetailCommentReplyData replyData = replyDatas.get(replyPosition);
        String nickName = replyData.getReply_user().getNickName();
        view.setTargetName(nickName);
    }

    @Override
    public void onConfessionCommentItemClick(final int position) {
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        DynamicDetailCommentData commentData = mCommentData.get(position);
        int comment_id = commentData.getComment_id();
        view.showLoading("");
        repertory.likeComment(authorization, getCurrentUserId(), comment_id,
                new ApiCallback<ApiResponse<OperationActionEntity>>() {
                    @Override
                    public void onSuccess(Response<ApiResponse<OperationActionEntity>> response) {
                        if (view.isActive()) {
                            view.showReqResult("操作成功");
                            handleCommentData();
                            bindDataToView();
                        }
                    }

                    /**
                     * 处理数据
                     */
                    private void handleCommentData() {
                        DynamicDetailCommentData comment = mCommentData.get(position);
                        boolean has_like = comment.isHas_like();
                        int likeCount = comment.getLikeCount();
                        has_like = !has_like;
                        if (has_like) {
                            likeCount++;
                        } else {
                            likeCount--;
                        }
                        comment.setHas_like(has_like);
                        comment.setLikeCount(likeCount);
                        mCommentData.set(position, comment);
                    }

                    @Override
                    public void onError(
                            Response<ApiResponse<OperationActionEntity>> response,
                            String error, ApiResponse<OperationActionEntity> apiResponse) {

                    }

                    @Override
                    public void onFail() {

                    }
                });
    }

    @Override
    public void onCommentUserItemClick(int position) {
        DynamicDetailCommentData commentData = mCommentData.get(position);
        int id = commentData.getComment_user().getId();
        view.startPersonCenter(id);
    }

    @Override
    public void onReplySenderUserItemClick(int commentPosition, int replyPosition) {
        DynamicDetailCommentData commentData = mCommentData.get(commentPosition);
        List<DynamicDetailCommentReplyData> replyDatas = commentData.getReplyDatas();
        DynamicDetailCommentReplyData replyData = replyDatas.get(replyPosition);
        int id = replyData.getReply_user().getId();
        view.startPersonCenter(id);
    }

    @Override
    public void onReplyTargetUserItemClick(int commentPosition, int replyPosition) {
        DynamicDetailCommentData commentData = mCommentData.get(commentPosition);
        List<DynamicDetailCommentReplyData> replyDatas = commentData.getReplyDatas();
        DynamicDetailCommentReplyData replyData = replyDatas.get(replyPosition);
        int id = replyData.getReply_target_user().getId();
        view.startPersonCenter(id);
    }

    @Override
    public void onReplyLoadMoreItemClick(int commentPosition) {
        DynamicDetailCommentData commentData = mCommentData.get(commentPosition);
        int comment_id = commentData.getComment_id();
        view.startCommentDetail(dynamicId, comment_id);
    }

    @Override
    public void start() {
        view.hideContentView();
        dynamicId = view.getDynamicId();
        initSubjectData();
    }

    /**
     * 得到当前用户的id
     */
    private int getCurrentUserId() {
        int user_id = 0;
        if (LoginContext.getInstance().isLogined()) {
            user_id = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
        }
        return user_id;
    }

    /**
     * 得到头
     */
    private String getAuthorization() {
        String authorization = null;
        if (LoginContext.getInstance().isLogined()) {
            authorization = Config.HEADER_BEARER
                    + UserInfoSingleton.getInstance().getUserAuths().getToken();
        }
        return authorization;
    }

    /**
     * 请求评论的接口
     */
    private void reqCommentData() {
        view.showLoading("");
        String authorization = getAuthorization();
        repertory.getComment$Apply(authorization, dynamicId, pageControl.getCurrent_page(),
                orderType, commentApiCallback);
    }

    /**
     * 请求发送评论
     */
    private void reqSendComment(int commentId, String comment) {
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        if (StringUtils.isEmpty(comment)) {
            view.showReqResult("评论内容不能为空");
            return;
        }
        view.showLoading("");
        if (commentId > 0) {
            repertory.sendReply(authorization, getCurrentUserId(), commentId, dynamicId, comment,
                    sendReplyApiCallback);
        } else {
            repertory.sendComment(authorization, getCurrentUserId(),
                    dynamicId, comment, sendCommentApiCallback);
        }
    }

    /**
     * 专题详情数据回调
     */
    private ApiCallback<ApiResponse<SubjectEntity>> subjectDetailApiCallback =
            new ApiCallback<ApiResponse<SubjectEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<SubjectEntity>> response) {
                    if (view.isActive()) {
                        SubjectEntity msg = response.body().getMsg();
                        isRefreshing = false;
                        handleData(msg);
                        //请求评论数据
                        onRefreshComment();
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(SubjectEntity msg) {
                    if (msg == null) {
                        mConfessionData = null;
                        bindDataToView();
                        return;
                    }
                    SubjectEntity.DynamicBean dynamic = msg.getDynamic();
                    SubjectEntity.DynamicBean.ContentBean content = dynamic.getContent();
                    if (content == null) {
                        mConfessionData = null;
                        bindDataToView();
                        return;
                    }
                    //设置数据
                    mConfessionData.setId(dynamic.getId());
                    mConfessionData.setSubjectTitle(dynamic.getTitle());
                    mConfessionData.setSubjectText(content.getDoc());
                    mConfessionData.setCount_of_comment(dynamic.getCount_of_comment());
                    mConfessionData.setCount_of_favorite(dynamic.getCount_of_favorite());
                    mConfessionData.setCount_of_participation(dynamic.getCount_of_participation());
                    mConfessionData.setHasFavorite(dynamic.getHasFavorite() != 0);
                    mConfessionData.setUpdated_at(dynamic.getUpdated_at());
                    mConfessionData.setCreated_at(dynamic.getCreated_at());
                    List<String> img = content.getImg();
                    mConfessionData.setImageUlrs(img);
                    //用户数据
                    SubjectEntity.DynamicBean.UserBean user = dynamic.getUser();
                    if (user == null) {
                        mConfessionData = null;
                        bindDataToView();
                        return;
                    }
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(user.getId());
                    userInfo.setAvatar(user.getAvatar());
                    userInfo.setNickName(user.getNickname());
                    mConfessionData.setUserInfo(userInfo);
                    //其他
                    bindDataToView();
                }

                @Override
                public void onError(Response<ApiResponse<SubjectEntity>> response, String error,
                                    ApiResponse<SubjectEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                        bindDataToView();
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
                        mCommentData.add(comment);
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
     * 发送评论的回调
     */
    private ApiCallback<ApiResponse<CommentSendEntity>> sendCommentApiCallback =
            new ApiCallback<ApiResponse<CommentSendEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CommentSendEntity>> response) {
                    if (view.isActive()) {
                        view.showReqResult("评论成功");
                        view.dismissLoading();
                        view.onCommentSuccess();
                        onRefresh();
                    }
                }

                @Override
                public void onError(Response<ApiResponse<CommentSendEntity>> response, String error,
                                    ApiResponse<CommentSendEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                        bindDataToView();
                    }
                }
            };
    /**
     * 发送评论的回调
     */
    private ApiCallback<ApiResponse<CommentReplyEntity>> sendReplyApiCallback =
            new ApiCallback<ApiResponse<CommentReplyEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CommentReplyEntity>> response) {
                    if (view.isActive()) {
                        view.showReqResult("回复成功");
                        view.dismissLoading();
                        view.onCommentSuccess();
                        onRefresh();
                    }
                }

                @Override
                public void onError(Response<ApiResponse<CommentReplyEntity>> response, String error,
                                    ApiResponse<CommentReplyEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult(error);
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                        bindDataToView();
                    }
                }
            };
}
