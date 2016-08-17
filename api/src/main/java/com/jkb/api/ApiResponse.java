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
    /**
     * 当前页
     */
    private int currentPage;
    /**
     * 当前页最多对象数量
     */
    private int pageSize;
    /**
     * 对象数据总量
     */
    private int maxCount;
    /**
     * 总共有多少页
     */
    private int maxPage;

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

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }
}
