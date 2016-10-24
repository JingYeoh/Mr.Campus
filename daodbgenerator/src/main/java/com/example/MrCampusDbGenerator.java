package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

/**
 * 数据库的配置类
 * 使用框架：GreenDAO
 * 用于生成bean层与dao层
 */
public class MrCampusDbGenerator {

    public static void main(String[] args) {
        //dao的自动生成器
        try {
            initSchema();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化dao的自动生成器
     */
    private static void initSchema() throws Exception {
        System.out.println("initSchema");
        Schema schema = new Schema(DBConfig.VERSION_NUM, DBConfig.ENTITY_PACKAGE_NAME);
        schema.setDefaultJavaPackageDao(DBConfig.DAO_PACKAGE_NAME);//如果不指定默认为entityPackageName
        initUserAuthsEntity(schema);
        initUsersEntity(schema);
        initStatusEntity(schema);
        initSchoolsEntity(schema);
        initMessageEntity(schema);
        new DaoGenerator().generateAll(schema, DBConfig.AUTO_GENERATE_JAVA_PATH);
    }

    /**
     * 初始化消息的实体表
     *
     * @param schema dao的自动生成器对象
     */
    private static void initMessageEntity(Schema schema) {
        System.out.println("initMessageEntity");
        Entity messages = schema.addEntity(DBConfig.ENTITY_MESSAGES);
        messages.setTableName(DBConfig.TABLE_MESSAGES);
        messages.addIdProperty();//建立自增的主键
        messages.addStringProperty(DBConfig.MSG_CONTENT).notNull();//消息内容，相当于标题
        messages.addStringProperty(DBConfig.ACTION).notNull();//消息的action
        messages.addIntProperty(DBConfig.SENDER_ID).notNull();//发送者的id
        messages.addIntProperty(DBConfig.TARGET_ID).notNull();//目标id
        messages.addStringProperty(DBConfig.TARGET_TYPE).notNull();//目标类型
        messages.addStringProperty(DBConfig.TARGET_NAME).notNull();//目标名称
        messages.addStringProperty(DBConfig.TARGET_PICTURE);//目标的图片
        messages.addStringProperty(DBConfig.SENDER_TYPE).notNull();//发送者的类型
        messages.addStringProperty(DBConfig.SENDER_NAME).notNull();//发送者的名称
        messages.addStringProperty(DBConfig.SENDER_PICTURE);//发送者的图片
        messages.addBooleanProperty(DBConfig.IS_READED);//是否读取
        messages.addDateProperty(DBConfig.UPDATED_AT);//最后更新的时间
    }

    /**
     * 初始化学校的实体表
     *
     * @param schema dao的自动生成器对象
     */
    private static void initSchoolsEntity(Schema schema) {
        System.out.println("initSchoolsEntity");
        Entity schools = schema.addEntity(DBConfig.ENTITY_SCHOOLS);
        schools.setTableName(DBConfig.TABLE_SCHOOLS);
        schools.addIdProperty();
        schools.addIntProperty(DBConfig.SCHOOL_ID).unique();
        schools.addStringProperty(DBConfig.BADGE);
        schools.addStringProperty(DBConfig.SCHOOL_NAME);
        schools.addStringProperty(DBConfig.SUMMARY);
        schools.addDoubleProperty(DBConfig.LATITUDE);
        schools.addDoubleProperty(DBConfig.LONGITUDE);
        schools.addDateProperty(DBConfig.UPDATED_AT);
    }

    /**
     * 初始化系统状态表
     *
     * @param schema dao的自动生成器对象
     */
    private static void initStatusEntity(Schema schema) {
        System.out.println("initStatusEntity");
        Entity status = schema.addEntity(DBConfig.ENTITY_STATUS);
        status.setTableName(DBConfig.TABLE_STATUS);
        status.addIdProperty();//建立自增的主键
        status.addStringProperty(DBConfig.VERSION).notNull();//缓存的版本号
        status.addBooleanProperty(DBConfig.FLAG_LOGIN).notNull();//是否登录
        status.addBooleanProperty(DBConfig.FLAG_SELECT_SEHOOL).notNull();//是否选择学校
        status.addIntProperty(DBConfig.SCHOOL_ID).notNull();//选择的学校的id
        status.addIntProperty(DBConfig.USER_ID).notNull();//用户的id
        status.addDateProperty(DBConfig.CREATED_AT);//创建时间
    }

    /**
     * 初始化用户数据表
     *
     * @param schema dao的自动生成器对象
     */
    private static void initUserAuthsEntity(Schema schema) {
        System.out.println("initUserAuthsEntity");
        Entity userAuth = schema.addEntity(DBConfig.ENTITY_USERAUTHS);
        userAuth.setTableName(DBConfig.TABLE_USERAUTHS);
        userAuth.addIdProperty();//建立自增的主键
        userAuth.addIntProperty(DBConfig.USER_ID).unique();//设置用户的id
        userAuth.addStringProperty(DBConfig.IDENTITY_TYPE);//登录方式
        userAuth.addStringProperty(DBConfig.IDENTIFIER);//帐号
        userAuth.addStringProperty(DBConfig.CREDENTIAL);//密码
        userAuth.addStringProperty(DBConfig.TOKEN);//token
        userAuth.addStringProperty(DBConfig.RONG_IM_TOKEN);//融云聊天的token
        userAuth.addDateProperty(DBConfig.UPDATED_AT);//更新时间
    }

    /**
     * 初始化用户表
     *
     * @param schema dao的自动生成器对象
     */
    private static void initUsersEntity(Schema schema) {
        System.out.println("initUsersEntity");
        Entity user = schema.addEntity(DBConfig.ENTITY_USERS);
        user.setTableName(DBConfig.TABLE_USERS);
        user.addIdProperty();//建立自增的主键
        user.addIntProperty(DBConfig.USER_ID).unique();//设置用户的id
        user.addStringProperty(DBConfig.UID).unique();
        user.addStringProperty(DBConfig.NICKNAME);//昵称
        user.addStringProperty(DBConfig.AVATAR);//头像
        user.addStringProperty(DBConfig.AVATAR_LOCAL_PATH);//本地头像
        user.addStringProperty(DBConfig.SEX);//性别
        user.addStringProperty(DBConfig.NAME);//应用名称
        user.addStringProperty(DBConfig.BREF_INTRODUCTION);//简介
        user.addStringProperty(DBConfig.BACKGROUND);//背景
        user.addIntProperty(DBConfig.SCHOOL_ID);//学校id
        user.addIntProperty(DBConfig.ATTENTIONCOUNT);//关注圈子总数目
        user.addIntProperty(DBConfig.ATTENTIONUSERCOUNT);//关注用户总数目
        user.addIntProperty(DBConfig.FANSCOUNT);//粉丝总数
        user.addIntProperty(DBConfig.VISITORCOUNT);//访客总数
        user.addDateProperty(DBConfig.UPDATED_AT);//更新时间
    }
}
