package com.jkb.core.presenter.function.special.create;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.config.Config;
import com.jkb.api.entity.dynamic.DynamicPostEntity;
import com.jkb.api.entity.image.ImageUploadEntity;
import com.jkb.core.contract.function.special.craete.SpecialCreateContract;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.dataSource.function.special.create.SpecialCreateRepertory;
import com.jkb.model.info.SchoolInfoSingleton;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.FileUtils;
import com.jkb.model.utils.StringUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Response;

/**
 * 创建专题的P层
 * Created by JustKiddingBaby on 2016/11/21.
 */

public class SpecialCreatePresenter implements SpecialCreateContract.Presenter {

    private SpecialCreateContract.View view;
    private SpecialCreateRepertory repertory;

    //data
    private List<String> localPictures;
    private List<String> uploadPicturesUrl;
    private String title = null;//标题
    private String content = null;//内容
    private String subjectType = null;

    public SpecialCreatePresenter(SpecialCreateContract.View view, SpecialCreateRepertory repertory) {
        this.view = view;
        this.repertory = repertory;

        localPictures = new ArrayList<>();
        uploadPicturesUrl = new ArrayList<>();

        this.view.setPresenter(this);
    }

    @Override
    public void bindDataToView() {
        if (!view.isActive()) {
            return;
        }
        view.dismissLoading();
        //设置图片
        view.setUploadPictures(localPictures);
    }

    @Override
    public void addPicture(String picture) {
        localPictures.add(picture);
        bindDataToView();
    }

    @Override
    public void deletePicture(int position) {
        localPictures.remove(position);
        bindDataToView();
    }

    @Override
    public void replacePicture(int position, String picture) {
        localPictures.set(position, picture);
        bindDataToView();
    }

    @Override
    public void postSubjectConfession(String title, String content) {
        postSubjectDynamic(title, content, Config.SUBJECT_TYPE_VINDICATOIN);
    }

    @Override
    public void postSubjectTaunted(String title, String content) {
        postSubjectDynamic(title, content, Config.SUBJECT_TYPE_COMPLAINT);
    }

    @Override
    public void postSubjectLostAndFound(String title, String content) {
        postSubjectDynamic(title, content, Config.SUBJECT_TYPE_LOSTANDFOUND);
    }

    @Override
    public void postSubjectFleaMarket(String title, String content) {
        postSubjectDynamic(title, content, Config.SUBJECT_TYPE_FLEAMARKET);
    }

    @Override
    public void postSubjectWantedSavant(String title, String content) {
        postSubjectDynamic(title, content, Config.SUBJECT_TYPE_GRIND);
    }

    @Override
    public void postSubjectWantedPartner(String title, String content) {
        postSubjectDynamic(title, content, Config.SUBJECT_TYPE_PARTNER);
    }

    @Override
    public void start() {
    }

    /**
     * 发布专题动态
     */
    private void postSubjectDynamic(String title, String content, String subjectType) {
        if (StringUtils.isEmpty(title, content)) {
            view.showReqResult("标题或内容不能为空");
            return;
        }
        this.title = title;
        this.content = content;
        this.subjectType = subjectType;
        String authorization = getAuthorization();
        if (StringUtils.isEmpty(authorization)) {
            view.showReqResult("请先登录再进行操作");
            return;
        }
        int schoolId = getCurrentSchoolId();
        if (schoolId <= 0) {
            view.showReqResult("请先选择学校再进行操作");
            return;
        }
        view.showLoading("");
        //上传图片
        uploadPicture();
    }

    /**
     * 上传图片
     */
    private void uploadPicture() {
        if (localPictures.size() == 0) {
            post();
        } else {
            //请求上传图片接口
            if (uploadPicturesUrl.size() != localPictures.size()) {
                //上传图片
                uploadPicture(localPictures.get(uploadPicturesUrl.size()));
            } else {
                post();
            }
        }
    }

    /**
     * 上傳圖片
     */
    private void uploadPicture(String localPath) {
        MultipartBody.Part part = FileUtils.getPartFromFile(localPath, "image");
        if (part == null) {
            return;
        }
        repertory.uploadImage(part, uploadImgApiCallback);
    }

    /**
     * 发布
     */
    private void post() {
        String authorization = getAuthorization();
        int schoolId = getCurrentSchoolId();
        int currentUserId = getCurrentUserId();
        //转换数据
        JSONObject object = new JSONObject();
        try {
            object.put("doc", content);
            JSONArray array = new JSONArray();
            for (int i = 0; i < uploadPicturesUrl.size(); i++) {
                array.put(uploadPicturesUrl.get(i));
            }
            object.put("img", array);
            repertory.postDynamic(
                    authorization, currentUserId, subjectType,
                    title, object.toString(), null,
                    schoolId, postSubjectApiCallback);
        } catch (JSONException e) {
            e.printStackTrace();
            view.showReqResult("数据转换错误");
        }
    }

    /**
     * 得到authorization
     */
    private String getAuthorization() {
        String authorization = null;
        if (LoginContext.getInstance().isLogined()) {
            authorization = Config.HEADER_BEARER +
                    UserInfoSingleton.getInstance().getUserAuths().getToken();
        }
        return authorization;
    }

    /**
     * 得到当前的用户id
     */
    private int getCurrentUserId() {
        int user_id = 0;
        if (LoginContext.getInstance().isLogined()) {
            user_id = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
        }
        return user_id;
    }

    /**
     * 得到当前学校id
     */
    private int getCurrentSchoolId() {
        int schoolId = -1;
        if (SchoolInfoSingleton.getInstance().isSelectedSchool()) {
            schoolId = SchoolInfoSingleton.getInstance().getSchool().getSchool_id();
        }
        return schoolId;
    }

    /**
     * 发布专题动态的回调接口
     */
    private ApiCallback<ApiResponse<DynamicPostEntity>> postSubjectApiCallback =
            new ApiCallback<ApiResponse<DynamicPostEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<DynamicPostEntity>> response) {
                    if (view.isActive()) {
                        view.dismissLoading();
                        view.postSuccess();
                    }
                }

                @Override
                public void onError(Response<ApiResponse<DynamicPostEntity>> response, String error,
                                    ApiResponse<DynamicPostEntity> apiResponse) {
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
     * 上传图片的回调接口
     */
    private ApiCallback<ApiResponse<ImageUploadEntity>> uploadImgApiCallback =
            new ApiCallback<ApiResponse<ImageUploadEntity>>() {
                @Override
                public void onSuccess(Response<ApiResponse<ImageUploadEntity>> response) {
                    if (view.isActive()) {
                        ImageUploadEntity msg = response.body().getMsg();
                        String url = msg.getUrl();
                        uploadPicturesUrl.add(url);
                        uploadPicture();
                    }
                }

                @Override
                public void onError(Response<ApiResponse<ImageUploadEntity>> response, String
                        error, ApiResponse<ImageUploadEntity> apiResponse) {
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
