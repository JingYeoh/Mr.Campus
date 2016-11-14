package com.jkb.model.data;

/**
 * 分页控制的实体类
 * Created by JustKiddingBaby on 2016/8/18.
 */

public class PageControlEntity {

    private int total = 0;//动态的总条数.
    private int per_page = 0;//每页的动态条数.
    private int current_page = 1;//当前页数.
    private int last_page = 0;//    最后一页所对应的页数.
    private String next_page_url = "";//下一页的地址.
    private String prev_page_url = "";//上一页的地址.
    private int from = 0;//每页第一条动态对应的位置序列.
    private int to = 0;//每页最后一条动态对应的位置序列.

    public PageControlEntity() {
        total = 0;
        per_page = 0;
        current_page = 1;
        last_page = 0;
        from = 0;
        to = 0;
        next_page_url = "";
        prev_page_url = "";
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPer_page() {
        return per_page;
    }

    public void setPer_page(int per_page) {
        this.per_page = per_page;
    }

    public int getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(int current_page) {
        this.current_page = current_page;
    }

    public int getLast_page() {
        return last_page;
    }

    public void setLast_page(int last_page) {
        this.last_page = last_page;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "PageControlEntity{" + "\n" +
                "total=" + total + "\n" +
                ", per_page=" + per_page + "\n" +
                ", current_page=" + current_page + "\n" +
                ", last_page=" + last_page + "\n" +
                ", next_page_url='" + next_page_url + '\'' + "\n" +
                ", prev_page_url='" + prev_page_url + '\'' + "\n" +
                ", from=" + from + "\n" +
                ", to=" + to + "\n" +
                "}" + "\n";
    }
}
