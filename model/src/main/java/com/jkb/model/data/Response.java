package com.jkb.model.data;

/**
 * 响应的数据实体的基类
 * 响应的数据分为如下三种类型：
 * {"event": "0", "msg": "success"}
 * {"event": "0", "msg": "success", "obj":{...}}
 * {"event": "0", "msg": "success", "objList":[{...}, {...}], "currentPage": 1, "pageSize": 20, "maxCount": 2, "maxPage": 1}
 * Created by JustKiddingBaby on 2016/7/20.
 */
public class Response<T> {
    /**
     * 返回码
     */
    private String event;
    /**
     * 返回信息
     */
    private String msg;
    /**
     * 返回的数据实体：单个数据对象，如：JsonObject
     */
    private T obj;
    /**
     * 返回的数据实体：数据对象数组，如：JsonArray
     */
    private T objList;
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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    public T getObjList() {
        return objList;
    }

    public void setObjList(T objList) {
        this.objList = objList;
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
