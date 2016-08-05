package com.jkb.api.entity.auth;

import java.util.List;

/**
 * 发送验证码返回信息的实体类
 * Created by JustKiddingBaby on 2016/7/31.
 */
public class IdentifyCodeEntity {

    private List<String> email;
    private List<String> phone;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }


    public List<String> getPhone() {
        return phone;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }
}
