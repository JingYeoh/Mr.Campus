package com.jkb.core.presenter.im.conversation;

import android.support.annotation.NonNull;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.circle.CircleInfoEntity;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.core.contract.im.conversation.ConversationContract;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.info.user.UserInfo;
import com.jkb.model.dataSource.im.conversation.ConversationRepertory;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import retrofit2.Response;

/**
 * 会话页面的P层
 * Created by JustKiddingBaby on 2016/10/20.
 */

public class ConversationPresenter implements ConversationContract.Presenter {

    private static final String TAG = "ConversationP";
    //data
    private ConversationContract.View view;
    private ConversationRepertory repertory;

    //数据
    private boolean isCached = false;
    private List<UserInfo> userInfos;
    private int privateConversationUserId = -1;
    private int chatRoomConversationCircleId = -1;
    private CircleInfo circleInfo;

    public ConversationPresenter(
            @NonNull ConversationContract.View view, @NonNull ConversationRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        userInfos = new ArrayList<>();

        this.view.setPresenter(this);
    }

    @Override
    public void bindDataToView() {
        if (!view.isActive()) {
            return;
        }
        isCached = true;
        view.setUserInfo(userInfos);
        //私聊
        if (privateConversationUserId != -1) {
            UserInfo userInfo = getUserInfo(privateConversationUserId);
            if (userInfo != null) {
                view.setTitleName(userInfo.getNickName());
            }
        }
        //聊天室
        if (chatRoomConversationCircleId != -1) {
            if (circleInfo == null) {
                view.setTitleName("聊天室" + chatRoomConversationCircleId);
            } else {
                view.setTitleName(circleInfo.getCircleName());
            }
        }
    }

    /**
     * 得到緩存的用戶數據
     */
    private UserInfo getUserInfo(int userId) {
        if (userInfos.size() == 0) {
            return null;
        }
        for (UserInfo userInfo :
                userInfos) {
            if (userId == userInfo.getId()) {
                return userInfo;
            }
        }
        return null;
    }

    @Override
    public void refreshUserData() {
        if (isCached) {
            bindDataToView();
        } else {
            //请求用户数据
            reqUserInfo(getUserSelfId());
        }
    }

    @Override
    public void reqUserInfoData(int user_id) {
        reqUserInfo(user_id);
    }

    @Override
    public void setPrivateConversationUserId(int userId) {
        LogUtils.d(TAG, "对方私聊的id是" + userId);
        privateConversationUserId = userId;
        reqUserInfo(userId);
    }

    @Override
    public void setChatRootConversationCircleId(int circleId) {
        LogUtils.d(ConversationPresenter.class, "聊天室的圈子id是" + circleId);
        chatRoomConversationCircleId = circleId;
        reqCircleInfo(circleId);
    }

    /**
     * 请求圈子数据
     */
    private void reqCircleInfo(int circleId) {
        if (circleId < 0) {
            return;
        }
        if (circleInfo != null) {
            bindDataToView();
        } else {
            int userSelfId = getUserSelfId();
            repertory.getCircleInfo(userSelfId, circleId, circleApiCallback);
        }
    }

    /**
     * 请求用户数据
     */
    private void reqUserInfo(int user_id) {
        if (user_id < 0) {
            return;
        }
        //判断是否有该数据缓存
        if (userInfos.size() > 0) {
            for (UserInfo userInfo : userInfos) {
                if (userInfo.getId() == user_id) {
                    bindDataToView();
                    return;
                }
            }
        }
        repertory.getUserInfo(user_id, userInfoApiCallback);
    }

    /**
     * 获取用户自己的id
     */
    private int getUserSelfId() {
        UserAuths userAuths = UserInfoSingleton.getInstance().getUserAuths();
        return userAuths.getUser_id();
    }

    @Override
    public void start() {
        refreshUserData();
    }

    /**
     * 请求用户数据的回调接口
     */
    private ApiCallback<ApiResponse<UserInfoEntity>> userInfoApiCallback =
            new ApiCallback<ApiResponse<UserInfoEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<UserInfoEntity>> response) {
                    if (view.isActive()) {
                        handleData(response);
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(Response<ApiResponse<UserInfoEntity>> response) {
                    if (response == null) {
                        bindDataToView();
                        return;
                    }
                    handleUserData(response.body());
                }

                /**
                 * 处理用户数据
                 */
                private void handleUserData(ApiResponse<UserInfoEntity> body) {
                    if (body == null) {
                        bindDataToView();
                        return;
                    }
                    UserInfoEntity msg = body.getMsg();
                    if (msg == null) {
                        bindDataToView();
                        return;
                    }
                    UserInfoEntity.UserInfoBean userInfo = msg.getUserInfo();
                    if (userInfo == null) {
                        bindDataToView();
                        return;
                    }

                    UserInfo info = new UserInfo();
                    info.setId(userInfo.getId());
                    info.setNickName(userInfo.getNickname());
                    info.setAvatar(userInfo.getAvatar());
                    info.setBackground(userInfo.getBackground());
                    info.setBref_introduction(userInfo.getBref_introduction());
                    info.setCount_of_fan(userInfo.getAttentionCount());
                    info.setUID(userInfo.getUID());
                    info.setName(userInfo.getName());
                    info.setCount_of_fan(userInfo.getFansCount());
                    info.setCount_of_visitor(userInfo.getVisitorCount());

                    filterUserInfo(info);

                    bindDataToView();
                }

                /**
                 * 筛选用户数据
                 */
                private void filterUserInfo(UserInfo info) {
                    if (userInfos.size() == 0) {
                        userInfos.add(info);
                        return;
                    }
                    for (UserInfo userInfo :
                            userInfos) {
                        if (userInfo.getId() == info.getId()) {
                            return;
                        }
                    }
                    userInfos.add(info);
                }

                @Override
                public void onError(Response<ApiResponse<UserInfoEntity>> response, String error,
                                    ApiResponse<UserInfoEntity> apiResponse) {
                    if (view.isActive()) {
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        bindDataToView();
                    }
                }
            };
    /**
     * 请求圈子数据的回调接口
     */
    private ApiCallback<ApiResponse<CircleInfoEntity>> circleApiCallback =
            new ApiCallback<ApiResponse<CircleInfoEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CircleInfoEntity>> response) {
                    if (view.isActive()) {
                        handleData(response);
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(Response<ApiResponse<CircleInfoEntity>> response) {
                    if (response == null) {
                        bindDataToView();
                    } else {
                        handleCircleData(response.body());
                    }
                }

                /**
                 * 处理圈子数据
                 */
                private void handleCircleData(ApiResponse<CircleInfoEntity> body) {
                    if (body == null) {
                        bindDataToView();
                    } else {
                        CircleInfoEntity msg = body.getMsg();
                        if (msg == null) {
                            bindDataToView();
                        } else {
                            CircleInfoEntity.CircleBean circle = msg.getCircle();
                            if (circle == null) {
                                bindDataToView();
                            } else {
                                handleCircleInfo(circle);
                                bindDataToView();
                            }
                        }
                    }
                }

                /**
                 * 处理圈子信息
                 */
                private void handleCircleInfo(CircleInfoEntity.CircleBean circle) {
                    circleInfo = new CircleInfo();
                    circleInfo.setCircleId(circle.getId());
                    circleInfo.setCircleName(circle.getName());
                    circleInfo.setCircleType(circle.getType());
                    circleInfo.setIntroduction(circle.getIntroduction());
                    circleInfo.setDynamicsCount(circle.getDynamics_count());
                    circleInfo.setOperatorCount(circle.getSubscribe_count());
                    circleInfo.setPictureUrl(circle.getPicture());
                }

                @Override
                public void onError(Response<ApiResponse<CircleInfoEntity>> response,
                                    String error, ApiResponse<CircleInfoEntity> apiResponse) {
                    if (view.isActive()) {
                        bindDataToView();
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        bindDataToView();
                    }
                }
            };
}
