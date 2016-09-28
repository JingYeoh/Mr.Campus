package com.jkb.core.presenter.dynamicCreate.topic;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.category.CategoryTypeEntity;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;
import com.jkb.core.contract.dynamicCreate.data.CategoryTypeData;
import com.jkb.core.contract.dynamicCreate.topic.DynamicCreateTopicContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.dynamicCreate.topic.DynamicCreateTopicDataRepertory;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.FileUtils;
import com.jkb.model.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jkb.mrcampus.db.entity.UserAuths;
import okhttp3.MultipartBody;
import retrofit2.Response;

/**
 * 动态创建：话题页面P层
 * Created by JustKiddingBaby on 2016/9/26.
 */

public class DynamicCreateTopicPresenter implements DynamicCreateTopicContract.Presenter {

    private static final String TAG = "DynamicCreateTopicP";
    private DynamicCreateTopicContract.View view;
    private DynamicCreateTopicDataRepertory repertory;

    //data
    private List<CategoryTypeData> categoryTypeDatas;

    //图片
    private String picturePath;
    private String pictureUrl;

    public DynamicCreateTopicPresenter(
            @NonNull DynamicCreateTopicContract.View view,
            @NonNull DynamicCreateTopicDataRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        this.view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void uploadImage(String imgPath) {
        if (StringUtils.isEmpty(imgPath)) {
            view.showReqResult("路径选择失败,请重试");
            return;
        }
        picturePath = imgPath;
        MultipartBody.Part part = FileUtils.getPartFromFile(picturePath, "image");
        view.showLoading("");
        repertory.uploadImage(part, uploadImgApiCallback);
    }

    @Override
    public void postTopicDynamic(String title, String content, String tag) {
        if (StringUtils.isEmpty(title)) {
            view.showReqResult("话题标题不能为空");
            return;
        }
        if (StringUtils.isEmpty(content)) {
            view.showReqResult("话题内容不能为空");
            return;
        }
        if (StringUtils.isEmpty(tag)) {
            view.showReqResult("话题标签不能为空");
            return;
        }
        UserAuths auths = getUserAuths();
        if (auths == null) {
            return;
        }
        try {
            JSONObject obj = handleTopicContent(content);
            String Authorization = Config.HEADER_BEARER + auths.getToken();
            int user_id = auths.getUser_id();

            Log.i(TAG, "发送的内容" + obj);
            //处理数据
            repertory.postDynamic(Authorization, user_id,
                    Config.DYNAMIC_TYPE_TOPIC, title, obj.toString(), tag, postDynamicApiCallback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理话题数据
     */
    private JSONObject handleTopicContent(String content) throws JSONException {
        JSONObject object = new JSONObject();
        JSONObject topicObj = new JSONObject();

        topicObj.put(Config.KEY_IMG, pictureUrl);
        topicObj.put(Config.KEY_DOC, content);

        object.put(Config.DYNAMIC_TYPE_TOPIC, topicObj);
        return object;
    }

    @Override
    public void getAllTag() {
        if (categoryTypeDatas == null) {
            repertory.getAllTag(Config.DYNAMIC_TYPE_TOPIC, categoryTypeApiCallback);
        } else {
            view.showCategoryTypeView(categoryTypeDatas);
        }
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
     * 上传图片的Api回调接口
     */
    private ApiCallback<ApiResponse<ImageUploadEntity>> uploadImgApiCallback = new
            ApiCallback<ApiResponse<ImageUploadEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<ImageUploadEntity>> response) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        handleData(response.body());
                    }
                }

                /**
                 * 处理数据
                 */
                private void handleData(ApiResponse<ImageUploadEntity> body) {
                    if (body == null) {
                        return;
                    }
                    ImageUploadEntity msg = body.getMsg();
                    if (msg == null) {
                        return;
                    }
                    pictureUrl = msg.getUrl();
                    view.setPicture(pictureUrl);
                }

                @Override
                public void onError(Response<ApiResponse<ImageUploadEntity>> response,
                                    String error, ApiResponse<ImageUploadEntity> apiResponse) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("图片上传失败");
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("图片上传失败");
                    }
                }
            };
    /**
     * 发布动态的Api回调接口
     */
    private ApiCallback<ApiResponse<DynamicPostEntity>> postDynamicApiCallback = new
            ApiCallback<ApiResponse<DynamicPostEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicPostEntity>> response) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("发布成功");
                        view.postSuccess();
                    }
                }

                @Override
                public void onError(Response<ApiResponse<DynamicPostEntity>> response, String error,
                                    ApiResponse<DynamicPostEntity> apiResponse) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("发布失败");
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.showReqResult("发布成失败");
                    }
                }
            };
    /**
     * 请求分类信息的回调接口
     */
    private ApiCallback<ApiResponse<CategoryTypeEntity>> categoryTypeApiCallback = new
            ApiCallback<ApiResponse<CategoryTypeEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<CategoryTypeEntity>> response) {
                    if (view.isActive()) {
                        handleData(response.body());
                    }
                }

                /**
                 * 解析数据
                 */
                private void handleData(ApiResponse<CategoryTypeEntity> body) {
                    if (body == null) {
                        return;
                    }
                    handleCategoryData(body.getMsg());
                }

                /**
                 * 解析标签数据
                 */
                private void handleCategoryData(CategoryTypeEntity msg) {
                    if (msg == null) {
                        return;
                    }
                    List<String> category = msg.getCategory();
                    if (category == null) {
                        return;
                    }
                    categoryTypeDatas = new ArrayList<>();
                    for (int i = 0; i < category.size(); i++) {
                        String s = category.get(i);
                        CategoryTypeData categoryTypeData = new CategoryTypeData();
                        categoryTypeData.setCategory(s);

                        categoryTypeDatas.add(categoryTypeData);
                    }
                    //设置数据
                    view.showCategoryTypeView(categoryTypeDatas);
                }

                @Override
                public void onError(Response<ApiResponse<CategoryTypeEntity>> response,
                                    String error, ApiResponse<CategoryTypeEntity> apiResponse) {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                    }
                }

                @Override
                public void onFail() {
                    if (view.isActive()) {
                        view.showReqResult("请求失败");
                    }
                }
            };
}
