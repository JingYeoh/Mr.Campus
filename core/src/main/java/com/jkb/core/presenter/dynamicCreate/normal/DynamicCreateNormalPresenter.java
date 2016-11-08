package com.jkb.core.presenter.dynamicCreate.normal;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;
import com.jkb.core.contract.dynamicCreate.normal.DynamicCreateNormalContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.core.control.userstate.LogoutState;
import com.jkb.model.dataSource.dynamicCreate.normal.DynamicCreateNormalDataRepertory;
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
 * 创建普通动态的P层
 * Created by JustKiddingBaby on 2016/9/27.
 */

public class DynamicCreateNormalPresenter implements DynamicCreateNormalContract.Presenter {

    private static final String TAG = "DynamicCreateNormalP";
    private DynamicCreateNormalContract.View view;
    private DynamicCreateNormalDataRepertory repertory;

    //data
    private List<String> imgUrls;
    private int replaceImgPosition = -1;
    private int circle_id = 0;

    public DynamicCreateNormalPresenter(
            @NonNull DynamicCreateNormalContract.View view,
            @NonNull DynamicCreateNormalDataRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        imgUrls = new ArrayList<>();

        this.view.setPresenter(this);
    }

    /**
     * 绑定图片网址到视图中
     */
    private void bindImgsToView() {
        String[] imgs = new String[imgUrls.size()];
        for (int i = 0; i < imgUrls.size(); i++) {
            imgs[i] = imgUrls.get(i);
        }
        if (view.isActive()) {
            view.setContentImgs(imgs);
        }
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
    public void postNormalDynamic(String content) {
        if (StringUtils.isEmpty(content) && imgUrls.size() == 0) {
            view.showReqResult("请输入内容或者图片");
            return;
        }
        UserAuths auths = getUserAuths();
        if (auths == null) {
            return;
        }
        try {
            JSONObject obj = handleNormalContent(content);
            String Authorization = Config.HEADER_BEARER + auths.getToken();
            int user_id = auths.getUser_id();

            Log.i(TAG, "发送的内容" + obj);
            //处理数据
            if (circle_id > 0) {
                repertory.postDynamic(Authorization, user_id,
                        Config.DYNAMIC_TYPE_NORMAL, null, obj.toString(), null, circle_id,
                        postDynamicApiCallback);
            } else {
                repertory.postDynamic(Authorization, user_id,
                        Config.DYNAMIC_TYPE_NORMAL, null, obj.toString(), null, postDynamicApiCallback);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理话题数据
     */
    private JSONObject handleNormalContent(String content) throws JSONException {
        JSONObject object = new JSONObject();
        JSONObject normalObj = new JSONObject();
        JSONArray imgArray = new JSONArray();

        for (int i = 0; i < imgUrls.size(); i++) {
            imgArray.put(imgUrls.get(i));
        }
        normalObj.put(Config.KEY_IMG, imgArray);
        normalObj.put(Config.KEY_DOC, content);

        object.put(Config.DYNAMIC_TYPE_NORMAL, normalObj);
        return object;
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

    @Override
    public boolean isAllowAddPicture() {
        return imgUrls.size() <= 6;
    }


    @Override
    public void start() {
        circle_id = view.getCircleId();
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
                    if (replaceImgPosition != -1 && imgUrls.size() > 0) {
                        imgUrls.set(replaceImgPosition, msg.getUrl());
                    } else {
                        imgUrls.add(msg.getUrl());
                    }
                    replaceImgPosition = -1;
                    bindImgsToView();
                }

                @Override
                public void onError(Response<ApiResponse<ImageUploadEntity>> response,
                                    String error, ApiResponse<ImageUploadEntity> apiResponse) {
                    if (view.isActive()) {
                        view.dismissLoading();
//                        view.showReqResult("图片上传失败");
                        view.showReqResult(error);
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
//                        view.showReqResult("发布失败");
                        view.showReqResult(error);
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
}
