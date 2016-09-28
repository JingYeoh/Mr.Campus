package com.jkb.core.presenter.dynamicCreate.article;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.category.CategoryTypeEntity;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;
import com.jkb.core.contract.dynamicCreate.article.DynamicCreateArticleContract;
import com.jkb.core.contract.dynamicCreate.data.CategoryTypeData;
import com.jkb.core.contract.dynamicCreate.data.DynamicCreateArticleData;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.dynamicCreate.article.DynamicCreateArticleDataRepertory;
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
 * 创建文章动态的P层
 * Created by JustKiddingBaby on 2016/9/27.
 */

public class DynamicCreateArticlePresenter implements DynamicCreateArticleContract.Presenter {

    private static final String TAG = "DynamicCreateArticleP";
    private DynamicCreateArticleContract.View view;
    private DynamicCreateArticleDataRepertory repertory;
    //data
    private List<CategoryTypeData> categoryTypeDatas;
    private List<DynamicCreateArticleData> dynamicCreateArticleDatas;

    private int replaceImgPosition = -1;


    public DynamicCreateArticlePresenter(
            @NonNull DynamicCreateArticleContract.View view,
            @NonNull DynamicCreateArticleDataRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        dynamicCreateArticleDatas = new ArrayList<>();
        DynamicCreateArticleData data = new DynamicCreateArticleData();
        dynamicCreateArticleDatas.add(data);

        this.view.setPresenter(this);
    }

    @Override
    public void start() {
        bindDataToView();
    }

    @Override
    public void bindDataToView() {
        if (!view.isActive()) {
            return;
        }
        view.setArticleData(dynamicCreateArticleDatas);
    }

    @Override
    public void replaceImage(int position, String imagePath) {
        replaceImgPosition = position;
        uploadImage(imagePath);
    }

    @Override
    public void uploadImage(String imgPath) {
        if (StringUtils.isEmpty(imgPath)) {
            view.showReqResult("路径选择失败,请重试");
            return;
        }
        MultipartBody.Part part = FileUtils.getPartFromFile(imgPath, "image");
        view.showLoading("");
        repertory.uploadImage(part, uploadImgApiCallback);
    }

    @Override
    public void postArticleDynamic(
            String title, List<DynamicCreateArticleData> articleDatas,
            String tag) {
        if (StringUtils.isEmpty(title)) {
            view.showReqResult("文章标题不能为空");
            return;
        }
        if (StringUtils.isEmpty(tag)) {
            view.showReqResult("文章标签不能为空");
            return;
        }
        if (articleDatas == null || articleDatas.size() == 0) {
            view.showReqResult("文章内容不能为空");
            return;
        }
        DynamicCreateArticleData data = articleDatas.get(0);
        Log.i(TAG, "doc=" + data.getArticleContent());
        if (StringUtils.isEmpty(data.getArticleContent())
                && StringUtils.isEmpty(data.getArticleImgUrl())) {
            view.showReqResult("请保持至少插入文字或者图片");
            return;
        }
        this.dynamicCreateArticleDatas = articleDatas;
        UserAuths auths = getUserAuths();
        if (auths == null) {
            return;
        }
        try {
            JSONObject obj = handleArticleContent();
            String Authorization = Config.HEADER_BEARER + auths.getToken();
            int user_id = auths.getUser_id();

            Log.i(TAG, "发送的内容" + obj);
            //处理数据
            repertory.postDynamic(Authorization, user_id,
                    Config.DYNAMIC_TYPE_ARTICLE, title, obj.toString(), tag, postDynamicApiCallback);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理话题数据
     */
    private JSONObject handleArticleContent() throws JSONException {
        JSONObject object = new JSONObject();
        JSONArray articleArray = new JSONArray();

        //去掉最后一行空数据
        DynamicCreateArticleData data = dynamicCreateArticleDatas.
                get(dynamicCreateArticleDatas.size() - 1);
        if (StringUtils.isEmpty(data.getArticleImgUrl())
                && StringUtils.isEmpty(data.getArticleContent())) {
            dynamicCreateArticleDatas.remove(dynamicCreateArticleDatas.size() - 1);
        }

        for (int i = 0; i < dynamicCreateArticleDatas.size(); i++) {
            DynamicCreateArticleData dynamicCreateArticleData = dynamicCreateArticleDatas.get(i);
            JSONObject obj = new JSONObject();
            String articleContent = dynamicCreateArticleData.getArticleContent();
            String articleImgUrl = dynamicCreateArticleData.getArticleImgUrl();
            if (StringUtils.isEmpty(articleContent)) {
                articleContent = "";
            }
            if (StringUtils.isEmpty(articleImgUrl)) {
                articleImgUrl = "";
            }
            obj.put(Config.KEY_IMG, articleImgUrl);
            obj.put(Config.KEY_DOC, articleContent);
            articleArray.put(obj);
        }
        object.put(Config.DYNAMIC_TYPE_ARTICLE, articleArray);
        return object;
    }

    @Override
    public void getAllTag() {
        if (categoryTypeDatas == null) {
            repertory.getAllTag(Config.DYNAMIC_TYPE_ARTICLE, categoryTypeApiCallback);
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
                    if (replaceImgPosition != -1) {
                        DynamicCreateArticleData dynamicCreateArticleData
                                = dynamicCreateArticleDatas.get(replaceImgPosition);
                        dynamicCreateArticleData.setArticleImgUrl(msg.getUrl());
                        dynamicCreateArticleDatas.set(replaceImgPosition, dynamicCreateArticleData);
                    } else {
                        DynamicCreateArticleData dynamicCreateArticleData;
                        dynamicCreateArticleData = new DynamicCreateArticleData();
                        dynamicCreateArticleData.setArticleImgUrl(msg.getUrl());
                        if (dynamicCreateArticleDatas.size() > 0) {
                            dynamicCreateArticleDatas.set(dynamicCreateArticleDatas.size() - 1,
                                    dynamicCreateArticleData);
                        } else {
                            dynamicCreateArticleDatas.add(dynamicCreateArticleData);
                        }
                        //再次添加空条目
                        DynamicCreateArticleData data = new DynamicCreateArticleData();
                        dynamicCreateArticleDatas.add(data);
                    }
                    bindDataToView();
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
