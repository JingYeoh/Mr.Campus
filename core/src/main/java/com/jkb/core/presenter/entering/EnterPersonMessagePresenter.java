package com.jkb.core.presenter.entering;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.RegisterEntity;
import com.jkb.api.config.Config;
import com.jkb.core.contract.entering.EnterPersonMessageContract;
import com.jkb.model.entering.personmessage.PersonMessageResponsitory;
import com.jkb.model.utils.BitmapUtils;
import com.jkb.model.utils.FileUtils;
import com.jkb.model.utils.FormatUtils;
import com.jkb.model.utils.StringUtils;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Response;

/**
 * 设置个人信息的Presenter层
 * Created by JustKiddingBaby on 2016/8/1.
 */
public class EnterPersonMessagePresenter implements EnterPersonMessageContract.Presenter, ApiCallback<ApiResponse<RegisterEntity>> {

    private static final String TAG = "personPresenter";

    private EnterPersonMessageContract.View personView;
    private PersonMessageResponsitory personMessageResponsitory;

    private Bitmap cachedHeadImage = null;
    private String headImagePath = null;

    public EnterPersonMessagePresenter(
            @NonNull EnterPersonMessageContract.View personView,
            @NonNull PersonMessageResponsitory personMessageResponsitory) {
        this.personView = personView;
        this.personMessageResponsitory = personMessageResponsitory;

        this.personView.setPresenter(this);
    }

    @Override
    public void register(String identifyCode, String nickName, String passWord, String identifier) {

        Log.d(TAG, "register我要在此处判断帐号类型");
        //判断输入是否正确
        if (StringUtils.isEmpty(identifyCode, nickName, passWord)) {
            personView.showReqResult("宝宝一个信息都不能空哦");
            return;
        }
        if (passWord.trim().length() < 6) {
            personView.showReqResult("宝宝密码不能少于6位哦");
            return;
        }

        String identity_type = null;
        //判断是否为邮箱
        if (FormatUtils.judgeEmailFormat(identifier)) {
            identity_type = Config.KEY_EMAIL;
        } else if (FormatUtils.judgePhoneFormat(identifier)) {//判断是否为手机号
            identity_type = Config.KEY_PHONE;
        } else {
            personView.showReqResult("请输入正确的手机号或邮箱");
            return;
        }

        if (personView.isActive()) {
            register(nickName, identifyCode, passWord, identity_type, identifier);
        }
    }

    @Override
    public void sendReqToUpLoadHeadImg(String headImgPath) {

    }

    /**
     * 请求注册
     *
     * @param nickName      昵称
     * @param code          验证码
     * @param credential    密码
     * @param identity_type 帐号类型
     * @param identifier    认证帐号
     */
    private void register(String nickName, String code, String credential,
                          String identity_type, String identifier) {
        personView.showLoading("加载中...");

        MultipartBody.Part part = FileUtils.getPartFromFile(headImagePath, "image");
        if (identity_type.equals(Config.KEY_EMAIL)) {
            //邮箱注册
            personMessageResponsitory.registerWithEmail(nickName, code, credential, identity_type
                    , identifier, part, this);
        } else if (identity_type.equals(Config.KEY_PHONE)) {
            //手机号注册
            personMessageResponsitory.registerWithPhone(nickName, code, credential, identity_type
                    , identifier, part, this);
        }
    }

    @Override
    public void saveHeadImgToFile(Bitmap bitmap, String name) {
        cachedHeadImage = bitmap;
        //保存头像得到路径
        headImagePath = personMessageResponsitory.saveBitmapToFile(bitmap, null, name);

        //设置头像显示
        personView.setHeadImg(personMessageResponsitory.getBitmapFromFile(headImagePath));
    }

    @Override
    public void setHeadImagePath(String imagePath) {
        this.headImagePath = imagePath;
        Bitmap bitmap = BitmapUtils.getCompressedImage(imagePath);
        personView.setHeadImg(bitmap);//设置头像上去
    }

    @Override
    public void start() {
//        personView.clearUserInput();//清楚之前保存的输入信息
    }

    @Override
    public void onSuccess(Response<ApiResponse<RegisterEntity>> response) {
        personView.dismissLoading();
        personView.showReqResult("注册成功，宝宝真棒");
        //保存信息
    }

    @Override
    public void onError(Response<ApiResponse<RegisterEntity>> response, String error,
                        ApiResponse<RegisterEntity> apiResponse) {
        personView.dismissLoading();
        //显示错误信息
        RegisterEntity registerEntity = apiResponse.getMsg();
        if (registerEntity != null) {
            List<String> errors = registerEntity.getIdentifier();
            if (errors != null && errors.size() > 0) {
                String errMsg = errors.get(0);
                personView.showReqResult(errMsg);
            }
        } else {
            personView.showReqResult("失败了，宝宝好烦");
        }
    }

    @Override
    public void onFail() {
        personView.dismissLoading();
        personView.showReqResult("请求失败");
    }
}
