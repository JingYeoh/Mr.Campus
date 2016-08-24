package com.jkb.api.entity.user;

import java.util.List;

/**
 * 修改用户数据的数据实体类
 * Created by JustKiddingBaby on 2016/8/24.
 */

public class UserUpdateEntity {

    private List<String> id;
    private List<String> identifier;
    private List<String> credential;

    public List<String> getId() {
        return id;
    }

    public void setId(List<String> id) {
        this.id = id;
    }

    public List<String> getIdentifier() {
        return identifier;
    }

    public void setIdentifier(List<String> identifier) {
        this.identifier = identifier;
    }

    public List<String> getCredential() {
        return credential;
    }

    public void setCredential(List<String> credential) {
        this.credential = credential;
    }
}
