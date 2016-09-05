package com.jkb.core.contract.function.index.data.dynamic;

import com.jkb.core.contract.function.index.data.dynamic.entity.DynamicUserData;

/**
 * 动态的基础数据类
 * Created by JustKiddingBaby on 2016/9/3.
 */

public abstract class DynamicBaseData<T> {

    /**
     * 动态类型：圈子动态
     */
    public static final int TARGET_TYPE_CIRCLE_IN_COMMONUSE = 1000;
    /**
     * 动态类型：动态
     */
    public static final int TARGET_TYPE_DYNAMIC = 2000;

    //好友动态的类型（动态或者圈子）.
    private int target_type;
    //动态发布操作类型（订阅圈子;喜欢动态;发表文章、话题贴或者动态）.
    private String action;
    //动态的标题（例如：喜欢了一个动态）.
    private String title;
    //具体的动态数据类
    private T dynamic;
    //用户的数据类
    private DynamicUserData dynamicUserData;

    public int getTarget_type() {
        return target_type;
    }

    public void setTarget_type(int target_type) {
        this.target_type = target_type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public T getDynamic() {
        return dynamic;
    }

    public abstract void setDynamic(T dynamic);

    public DynamicUserData getDynamicUserData() {
        return dynamicUserData;
    }

    public void setDynamicUserData(DynamicUserData dynamicUserData) {
        this.dynamicUserData = dynamicUserData;
    }
}
