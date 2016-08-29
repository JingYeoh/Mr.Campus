package com.jkb.core.presenter.personCenter;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.user.UserInfoEntity;
import com.jkb.api.entity.user.UserUpdateEntity;
import com.jkb.core.contract.personCenter.PersonSettingContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LoginState;
import com.jkb.core.presenter.personCenter.data.UserSettingData;
import com.jkb.model.dataSource.personSetting.PersonSettingDataResponsitory;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.intfc.BitmapLoadedCallback;
import com.jkb.model.utils.FileUtils;
import com.jkb.model.utils.StringUtils;

import jkb.mrcampus.db.entity.UserAuths;
import jkb.mrcampus.db.entity.Users;
import okhttp3.MultipartBody;
import retrofit2.Response;

/**
 * 个人设置的P层
 * Created by JustKiddingBaby on 2016/8/23.
 */

public class PersonSettingPresenter implements PersonSettingContract.Presenter {

    private PersonSettingContract.View view;
    private PersonSettingDataResponsitory responsitory;


    //data
    private UserSettingData userSettingData;//缓存的信息
    private boolean isCacheOverDatad = false;//缓存是否过期
    private static final String TAG = "PersonSettingPresenter";
    private String headImgLocalPath = null;

    public PersonSettingPresenter(
            @NonNull PersonSettingContract.View view,
            @NonNull PersonSettingDataResponsitory responsitory) {
        this.view = view;
        this.responsitory = responsitory;

        this.view.setPresenter(this);
    }

    @Override
    public void bindInformation() {
        if (!view.isActive()) {
            return;
        }
        if (userSettingData.getHeadImg() != null) {//设置头像
            view.setHeadImg(userSettingData.getHeadImg());
        }
        if (userSettingData.getBackGround() != null) {
            view.setBackGround(userSettingData.getBackGround());//设置背景
        }
        view.setName(userSettingData.getName());
        view.setNickName(userSettingData.getNickName());
        view.setSex(userSettingData.getSex());
        view.setBref_introduction(userSettingData.getBref_introduction());
        view.setID(userSettingData.getUID());
        view.setPhone(userSettingData.getPhone());
        view.setEmail(userSettingData.getEmail());
        view.setRegisterTime(userSettingData.getCreateTime());
    }

    @Override
    public void getUserInfo() {
        //有缓存的数据时
        if (userSettingData != null && !isCacheOverDatad) {
            bindInformation();
            return;
        }
        //得到用户id
        Users users = UserInfoSingleton.getInstance().getUsers();
        if (users == null) {
            return;
        }
        int user_id = users.getUser_id();
        //从网络获取数据
        view.showLoading("");
        responsitory.getUserInfo(user_id, userInfoApiCallback);
    }

    @Override
    public void updateHeadImg(String headImgPath) {
        headImgLocalPath = headImgPath;
        uploadImage(headImgPath, 0);
    }

    @Override
    public void updateBackGround(String bgPath) {
        uploadImage(bgPath, 1);
    }

    @Override
    public void updateNickName(String nickName) {
        updateUserInfo(Config.COLUMN_NICKNAME, nickName);
    }

    @Override
    public void updateName(String name) {
        updateUserInfo(Config.COLUMN_NAME, name);
    }

    @Override
    public void updateSex(String sex) {
        updateUserInfo(Config.COLUMN_SEX, sex);
    }

    @Override
    public void updateBref_introduction(String bref_introfuction) {
        updateUserInfo(Config.COLUMN_BREF_INTRODUCTION, bref_introfuction);
    }

    /**
     * 更新用户数据
     *
     * @param column 修改用户信息的类型
     * @param value  要修改的用户信息
     */
    private void updateUserInfo(String column, String value) {
        Users users = getUsers();
        UserAuths auths = getUserAuths();
        int user_id = users.getUser_id();
        String Authorization = Config.HEADER_BEARER + auths.getToken();
        responsitory.updateUserInfo(Authorization, user_id, column, value, updateUserInfo);
    }

    /**
     * 上传图片
     *
     * @param path 路径
     * @param type 类型0：头像、1：背景
     */
    private void uploadImage(String path, int type) {
        if (StringUtils.isEmpty(path)) {
            return;
        }
        MultipartBody.Part part = FileUtils.getPartFromFile(path, "image");
        if (part == null) {
            return;
        }
        //得到用户id
        Users users = getUsers();
        UserAuths auths = getUserAuths();
        int user_id = users.getUser_id();
        String Authorization = Config.HEADER_BEARER + auths.getToken();
        view.showLoading("");
        if (type == 0) {
            responsitory.uploadHeadImg(Authorization, user_id, part, uploadHeadImgApiCallback);
        } else if (type == 1) {
            responsitory.uploadBackGround(Authorization, user_id, part, uploadBgApiCallback);
        }
    }

    @Override
    public void notifyDataChanged() {
        if (view.isActive()) {
            Log.d(TAG, "notifyDataChanged");
            view.notifyDataChanged();//设置数据过期通知
        }
    }

    /**
     * 获取用户数据的回调接口
     */
    private ApiCallback<ApiResponse<UserInfoEntity>> userInfoApiCallback
            = new ApiCallback<ApiResponse<UserInfoEntity>>() {
        @Override
        public void onSuccess(Response<ApiResponse<UserInfoEntity>> response) {
            if (view.isActive()) {
                view.dismissLoading();
                isCacheOverDatad = false;//设置缓存没有过期
                handleUserInfo(response.body().getMsg());
            }
        }

        /**
         * 处理用户数据
         */
        private void handleUserInfo(UserInfoEntity entity) {
            if (entity == null) {
                return;
            }
            UserInfoEntity.UserInfoBean userInfoBean = entity.getUserInfo();
            if (userInfoBean == null) {
                return;
            }
            //设置数据到缓存类中
            if (userSettingData == null) {
                userSettingData = new UserSettingData();
            }
            userSettingData.setSex(userInfoBean.getSex());
            userSettingData.setName(userInfoBean.getName());
            userSettingData.setNickName(userInfoBean.getNickname());
            userSettingData.setBref_introduction(userInfoBean.getBref_introduction());
            String phone = userInfoBean.getPhone();
            if (StringUtils.isEmpty(phone)) {
                phone = "none";
            }
            userSettingData.setPhone(phone);
            String email = userInfoBean.getEmail();
            if (StringUtils.isEmpty(email)) {
                email = "none";
            }
            userSettingData.setEmail(email);
            userSettingData.setUID(userInfoBean.getUID());
            userSettingData.setCreateTime(userInfoBean.getCreated_at());
            //得到頭像和背景
            getHeadImgBitmapByUrl(userInfoBean.getAvatar());
            getBackGroundBitmapByUrl(userInfoBean.getBackground());
            //存到数据库中
            saveToDb(userInfoBean);
            //绑定数据
            bindInformation();
        }

        /**
         * 存储数据到数据库中
         */
        private void saveToDb(UserInfoEntity.UserInfoBean userInfoBean) {
            saveUsersToDb(userInfoBean);
            saveUserAuthsToDb(userInfoBean);
        }

        /**
         * 从网络获取背景
         */
        private void getBackGroundBitmapByUrl(String background) {
            if (StringUtils.isEmpty(background)) {
                return;
            }
            //加载背景
            responsitory.loadBitmapByUrl(background, new BitmapLoadedCallback() {
                @Override
                public void onBitmapDataLoaded(Bitmap bitmap) {
                    userSettingData.setBackGround(bitmap);
                    bindInformation(); //绑定数据
                }

                @Override
                public void onDataNotAvailable(String url) {
                    userSettingData.setBackGround(null);
                    bindInformation(); //绑定数据
                }
            });
        }

        /**
         * 从网络获取头像
         */
        private void getHeadImgBitmapByUrl(String avatar) {
            if (StringUtils.isEmpty(avatar)) {
                return;
            }
            //加载头像
            responsitory.loadBitmapByUrl(avatar, new BitmapLoadedCallback() {
                @Override
                public void onBitmapDataLoaded(Bitmap bitmap) {
                    userSettingData.setHeadImg(bitmap);
                    bindInformation(); //绑定数据
                    UserInfoSingleton.getInstance().setUserAvatar(bitmap);
                    //更新数据
                    LoginContext.getInstance().setUserState(new LoginState());
                }

                @Override
                public void onDataNotAvailable(String url) {
                    userSettingData.setHeadImg(null);
                    bindInformation(); //绑定数据
                }
            });
        }

        @Override
        public void onError(Response<ApiResponse<UserInfoEntity>> response,
                            String error, ApiResponse<UserInfoEntity> apiResponse) {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("请求错误");
            }
        }

        @Override
        public void onFail() {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("请求失败");
            }
        }
    };


    /**
     * 上传头像的数据回调
     */
    private ApiCallback<ApiResponse<UserUpdateEntity>> uploadHeadImgApiCallback
            = new ApiCallback<ApiResponse<UserUpdateEntity>>() {
        @Override
        public void onSuccess(Response<ApiResponse<UserUpdateEntity>> response) {
            if (view.isActive()) {
                view.dismissLoading();
                isCacheOverDatad = true;//设置数据过期
                notifyDataChanged();//通知数据过期
                getUserInfo();
            }
        }

        @Override
        public void onError(Response<ApiResponse<UserUpdateEntity>> response,
                            String error, ApiResponse<UserUpdateEntity> apiResponse) {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("上传失败");
            }
        }

        @Override
        public void onFail() {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("请求失败，请检查您的网络");
            }
        }
    };
    /**
     * 上传背景的数据回调
     */
    private ApiCallback<ApiResponse<UserUpdateEntity>> uploadBgApiCallback
            = new ApiCallback<ApiResponse<UserUpdateEntity>>() {
        @Override
        public void onSuccess(Response<ApiResponse<UserUpdateEntity>> response) {
            if (view.isActive()) {
                view.dismissLoading();
                isCacheOverDatad = true;//设置数据过期
                notifyDataChanged();//通知数据过期
                getUserInfo();
            }
        }

        @Override
        public void onError(Response<ApiResponse<UserUpdateEntity>> response,
                            String error, ApiResponse<UserUpdateEntity> apiResponse) {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("上传失败");
            }
        }

        @Override
        public void onFail() {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("请求失败，请检查您的网络");
            }
        }
    };

    /**
     * 更新用户信息
     */
    private ApiCallback<ApiResponse<UserUpdateEntity>> updateUserInfo
            = new ApiCallback<ApiResponse<UserUpdateEntity>>() {
        @Override
        public void onSuccess(Response<ApiResponse<UserUpdateEntity>> response) {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("修改成功");
                isCacheOverDatad = true;//设置数据过期
                notifyDataChanged();//通知数据过期
                getUserInfo();
            }
        }

        @Override
        public void onError(Response<ApiResponse<UserUpdateEntity>> response,
                            String error, ApiResponse<UserUpdateEntity> apiResponse) {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("修改失败");
            }
        }

        @Override
        public void onFail() {
            if (view.isActive()) {
                view.dismissLoading();
                view.showReqResult("请求失败");
            }
        }
    };

    /**
     * 存储数据到UserAuths表中
     */
    private void saveUserAuthsToDb(UserInfoEntity.UserInfoBean userInfoBean) {
        UserAuths auths = getUserAuths();
        auths.setUpdated_at(StringUtils.getSystemCurrentTime());
        responsitory.saveUserAuthsToDb(auths);
        //设置到当前单例类中
        UserInfoSingleton.getInstance().setUserAuths(auths);
        LoginContext.getInstance().setUserState(new LoginState());//为了更新数据
    }

    /**
     * 存储数据到Users表中
     */
    private void saveUsersToDb(UserInfoEntity.UserInfoBean userInfoBean) {
        Users users = getUsers();
        users.setName(userInfoBean.getName());
        users.setNickname(userInfoBean.getNickname());
        users.setAvatar(userInfoBean.getAvatar());
        users.setSex(userInfoBean.getSex());
        users.setBref_introduction(userInfoBean.getBref_introduction());
        users.setBackground(userInfoBean.getBackground());
        users.setUpdated_at(StringUtils.getSystemCurrentTime());
        //设置本地头像
        if (!StringUtils.isEmpty(headImgLocalPath)) {
            users.setAvatarLocalPath(headImgLocalPath);
        }
        //设置数据到当前单例类中
        responsitory.saveUsersToDb(users);
        UserInfoSingleton.getInstance().setUsers(users);
        LoginContext.getInstance().setUserState(new LoginState());//为了更新数据
    }

    @Override
    public void start() {
        getUserInfo();//初始化个人信息
    }

    private Users getUsers() {
        Users users = UserInfoSingleton.getInstance().getUsers();
        return users;
    }

    private UserAuths getUserAuths() {
        UserAuths auths = UserInfoSingleton.getInstance().getUserAuths();
        return auths;
    }
}
