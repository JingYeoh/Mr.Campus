package com.jkb.api.config;

/**
 * 用于保存要用到的键值对的常量类
 * Created by JustKiddingBaby on 2016/7/31.
 */
public class Config {

    public static final String PATH_ROOT_IMAGE = "/sdcard/JustKiddingBaby/MrCampus/images/";

    public static final String API_HOST = "http://bsapi.lyfsmile.cn/api/v1/";

    public static final String URL_SEND_EMAIL = "auth/sendEmail";//发送邮箱验证码
    public static final String URL_SEND_PHONE = "auth/sendSms";//发送邮箱验证码
    public static final String URL_REGISTER_WITH_EMAIL = "auth/registerWithEmail";//邮箱号注册用户
    public static final String URL_REGISTER_WITH_PHONE = "auth/registerWithPhone";//手机号注册用户
    public static final String URL_LOGIN_WITH_EMAIL = "auth/loginWithEmail"; //邮箱号登录
    public static final String URL_LOGIN_WITH_PHONE = "auth/loginWithPhone";//手机号登录
    public static final String URL_LOGIN_WITH_THIRDPLATFORM = "auth/loginWithThirdPlatform";//第三方登录
    public static final String URL_RESET_PASSWORD_WITH_EMAIL = "auth/resetPasswordWithEmail";//邮箱号修改密码
    public static final String URL_RESET_PASSWORD_WITH_PHONE = "auth/resetPasswordWithPhone";//手机号修改密码

    public static final String KEY_EMAIL = "email";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_NICK_NAME = "nickname";
    public static final String KEY_CODE = "code";
    public static final String KEY_CREDENTIAL = "credential";
    public static final String KEY_IDENTITY_TYPE = "identity_type";
    public static final String KEY_IDENTIFIER = "identifier";
    public static final String KEY_AVATAR = "avatar";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_FLAG = "flag";
    public static final String KEY_SEX = "sex";
    public static final String KEY_BACKGROUND = "background";

    public static final String GENDER_M = "m";
    public static final String GENDER_F = "f";
    public static final String SEX_MAN = "男";
    public static final String SEX_FEMAN = "女";
}
