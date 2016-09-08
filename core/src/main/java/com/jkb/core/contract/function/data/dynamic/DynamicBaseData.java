package com.jkb.core.contract.function.data.dynamic;

/**
 * 动态数据——父类:所有的动态都能用到的数据
 * Created by JustKiddingBaby on 2016/9/8.
 */

public class DynamicBaseData {

    private String action;//动作
    private String actionTitle;//标题
    private String target_type;//类型
    private int target_id;//目标id
    private String created_at;//创建时间
    private int creator_id;//作者id
    private String creator_nickname;//昵称
    private String creator_avatar;//头像

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getActionTitle() {
        return actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public int getTarget_id() {
        return target_id;
    }

    public void setTarget_id(int target_id) {
        this.target_id = target_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(int creator_id) {
        this.creator_id = creator_id;
    }

    public String getCreator_nickname() {
        return creator_nickname;
    }

    public void setCreator_nickname(String creator_nickname) {
        this.creator_nickname = creator_nickname;
    }

    public String getCreator_avatar() {
        return creator_avatar;
    }

    public void setCreator_avatar(String creator_avatar) {
        this.creator_avatar = creator_avatar;
    }


    /**
     * 包含的数据类
     */
    public static class Content {
    }
}
