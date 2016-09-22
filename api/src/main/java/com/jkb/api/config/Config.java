package com.jkb.api.config;

/**
 * 用于保存要用到的键值对的常量类
 * Created by JustKiddingBaby on 2016/7/31.
 */
public class Config {

    public static final String PATH_ROOT_IMAGE = "/sdcard/JustKiddingBaby/MrCampus/images/";

    public static final String APP_BAIDU_MAP_AK = "MQBdW7A7mBr9NWp8IIjckVBMGqFjSiG4";
    public static final String APP_BAIDU_MAP_MCODE = "06:01:F7:13:E8:17:0F:6A:C9:EF:C7:B3:37:00:53:1F:D1:93:2C:9F;com.jkb.mrcampus";

    public static final String API_HOST = "http://bsapi.lyfsmile.cn/api/v1/";
    //百度地图的host
    public static final String API_HOST_BAIDU_MAP = "http://api.map.baidu.com/";

    //auth的接口地址
    public static final String URL_SEND_EMAIL = "auth/sendEmail";//发送邮箱验证码
    public static final String URL_SEND_PHONE = "auth/sendSms";//发送邮箱验证码
    public static final String URL_REGISTER_WITH_EMAIL = "auth/registerWithEmail";//邮箱号注册用户
    public static final String URL_REGISTER_WITH_PHONE = "auth/registerWithPhone";//手机号注册用户
    public static final String URL_LOGIN_WITH_EMAIL = "auth/loginWithEmail"; //邮箱号登录
    public static final String URL_LOGIN_WITH_PHONE = "auth/loginWithPhone";//手机号登录
    public static final String URL_LOGIN_WITH_THIRDPLATFORM = "auth/loginWithThirdPlatform";//第三方登录
    public static final String URL_RESET_PASSWORD_WITH_EMAIL = "auth/resetPasswordWithEmail";//邮箱号修改密码
    public static final String URL_RESET_PASSWORD_WITH_PHONE = "auth/resetPasswordWithPhone";//手机号修改密码
    //circle的接口地址
    public static final String URL_CIRCLE_CREATE = "circle";
    public static final String URL_CIRCLE_INFO = "circle/{userId}/{id}";
    //user接口地址
    public static final String URL_USER_ACTION = "getOperation";
    public static final String URL_USER_INFO = "user/{id}";
    public static final String URL_USER_UPDATE_IMAGE = "user/image/{id}";
    public static final String URL_USER_UPDATE_INFO = "user/{id}";
    //operation的接口地址
    public static final String URL_OPERATION = "operation";//关注订阅等操作
    public static final String URL_OPERATION_USER = "operation/getUser";//获取涉及操作的用户
    public static final String URL_OPERATION_VERIFYIFPAYATTENTION = "verifyIfPayAttention";//是否被关注
    public static final String URL_OPERATION_VISITME = "getVisitMe";//访问我的人
    public static final String URL_OPERATION_VISITOTHER = "getVisitOther";//我访问的人
    //dynamic接口地址
    public static final String URL_DYNAMIC_GET_ALL = "dynamic/getAllDynamic";
    public static final String URL_DYNAMIC_GET_SINGLE = "dynamic/{userId}/{id}";
    //Contacts接口
    public static final String URL_CONTACTS_FRIENDS = "contacts";//相互关注的好友
    //Comment的接口u
    public static final String URL_COMMENT_LIST = "comment/{dynamicId}";
    public static final String URL_COMMENT_SINGLE = "getReply/{commentId}";
    public static final String URL_COMMENT_SEND = "comment";
    public static final String URL_COMMENT_REPLY_SEND = "reply";
    //百度地图的地址
    public static final String URL_GEOCODING = "geocoder/v2/";

    //需要添加的头部
    public static final String HEADER_KEY_AUTHORIZATION = "Authorization";
    //需要添加的header内容
    public static final String HEADER_BEARER = "Bearer ";


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

    public static final String KEY_COORDTYPE = "coordtype";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_POIS = "pois";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CITY = "city";
    public static final String KEY_OUTPUT = "output";
    public static final String KEY_AK = "ak";
    public static final String KEY_MCODE = "mcode";
    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_TARGET_USER_ID = "target_user_id";
    public static final String KEY_SCHOOL_ID = "school_id";
    public static final String KEY_INTRODUCTION = "introduction";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_NAME = "name";
    public static final String KEY_ACTION = "action";
    public static final String KEY_USERID = "userId";
    public static final String KEY_ID = "id";
    public static final String KEY_PAGE = "page";
    public static final String KEY_TARGET_ID = "target_id";
    public static final String KEY_TARGETID = "targetId";
    public static final String KEY_VISITOR_ID = "visitor_id";
    public static final String KEY_COLUMN = "column";
    public static final String KEY_VALUE = "value";
    public static final String KEY_DYNAMICID = "dynamicId";
    public static final String KEY_DYNAMIC_ID = "dynamic_id";
    public static final String KEY_COMMENT = "comment";
    public static final String KEY_COMMENTID = "commentId";
    public static final String KEY_COMMENT_ID = "comment_id";


    public static final String GENDER_M = "m";
    public static final String GENDER_F = "f";
    public static final String SEX_MAN = "男";
    public static final String SEX_FEMAN = "女";

    public static final String API_OUTPUT = "json";
    public static final String COORDTYPE_BAIDU = "bd09ll";

    public static final String FLAG_AVATAR = "avatar";
    public static final String FLAG_CIRCLE = "circle";
    public static final String FLAG_BACKGROUND = "background";

    public static final String IDENTITY_TYPE_QQ = "qq";
    public static final String IDENTITY_TYPE_WECHAT = "wechat";
    public static final String IDENTITY_TYPE_WEIBO = "weibo";
    public static final String IDENTITY_TYPE_RENREN = "renren";
    public static final String IDENTITY_TYPE_DOUBAN = "douban";
    public static final String IDENTITY_TYPE_EMAIL = "email";
    public static final String IDENTITY_TYPE_PHONE = "phone";

    public static final String ACTION_SUBSCRIBE = "subscribe";
    public static final String ACTION_FAVORITE = "favorite";
    public static final String ACTION_PAYATTENTION = "payAttention";
    public static final String ACTION_INCOMMONUSE = "inCommonUse";
    public static final String ACTION_VISITOR = "visitor";
    public static final String ACTION_VISIT = "visit";
    public static final String ACTION_LIKE = "like";

    public static final String COLUMN_NICKNAME = "nickname";
    public static final String COLUMN_SEX = "sex";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_BREF_INTRODUCTION = "bref_introduction";

    public static final String TARGET_TYPE_CIRCLE = "circle";
    public static final String TARGET_TYPE_DYNAMIC = "dynamic";
    public static final String TARGET_TYPE_CIRCLEINCOMMONUSE = "circleInCommonUse";

    public static final String D_TYPE_TOPIC = "topic";
    public static final String D_TYPE_ARTICLE = "article";
    public static final String D_TYPE_NORMAL = "normal";

    public static final String ACTION_TYPE_POST = "post";
    public static final String ACTION_TYPE_FAVORITE = "favorite";
    public static final String ACTION_TYPE_SUBSCRIBE = "subscribe";
    public static final String ACTION_TYPE_POSTINCIRCLE = "postInCircle";
}
