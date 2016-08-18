package com.jkb.api;

/**
 * 响应的数据实体的基类
 * 响应的数据分为如下三种类型：
 * {"event": "0", "msg": "success"}
 * {"event": "0", "msg": "success", "obj":{...}}
 * {"event": "0", "msg": "success", "objList":[{...}, {...}], "currentPage": 1, "pageSize": 20, "maxCount": 2, "maxPage": 1}
 * Created by JustKiddingBaby on 2016/7/20.
 */
public class ApiResponse<T> {
    /**
     * 返回码
     */
    private boolean success;
    /**
     * 返回信息实体
     */
    private T msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }
}
