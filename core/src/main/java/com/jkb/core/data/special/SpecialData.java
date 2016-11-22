package com.jkb.core.data.special;

import com.jkb.api.config.Config;
import com.jkb.api.entity.subject.SpecialListEntity;
import com.jkb.core.data.info.school.SchoolInfo;
import com.jkb.core.data.info.user.UserInfo;

import java.util.List;

/**
 * 专题数据
 * Created by JustKiddingBaby on 2016/11/19.
 */

public class SpecialData {


    private boolean isDataEffectived = true;

    public SpecialData() {
    }

    public SpecialData(SpecialListEntity.DataBean dataBean) {
        handleData(dataBean);
    }


    public SpecialData getSpecial() {
       /* if (INSTANCE == null) {
            return null;
        }
        return this.getSpecial();*/
        if (!isDataEffectived) {
            return null;
        }
        return this;
    }

    /**
     * 处理数据
     */
    private void handleData(SpecialListEntity.DataBean dataBean) {
        if (dataBean == null) {
            isDataEffectived = false;
            return;
        }
        String dtype = dataBean.getDtype();
        switch (dtype) {
            case Config.SUBJECT_ACTION_VINDICATION:
                break;
            case Config.SUBJECT_ACTION_COMPLAINT:
                break;
            case Config.SUBJECT_ACTION_LOSTANDFOUND:
                break;
            case Config.SUBJECT_ACTION_GRIND:
                break;
            case Config.SUBJECT_ACTION_PARTNER:
                break;
            case Config.SUBJECT_ACTION_FLEAMARKET:
                break;
            default:
                isDataEffectived = false;
                break;
        }
        bindCommonData(dataBean);
    }

    /**
     * 绑定公共数据
     */
    private void bindCommonData(SpecialListEntity.DataBean dataBean) {
        if (dataBean == null) {
            isDataEffectived = false;
            return;
        }
        this.setId(dataBean.getId());
        this.setDtype(dataBean.getDtype());
        this.setTitle(dataBean.getTitle());
        SpecialListEntity.DataBean.ContentBean content = dataBean.getContent();
        if (content == null) {
            isDataEffectived = false;
            return;
        }
        if (content.getDoc() == null && content.getImg() == null) {
            isDataEffectived = false;
            return;
        }
        this.setDoc(content.getDoc());
        this.setImg(content.getImg());
        //设置是否标注
        this.setTag(dataBean.getTag() != 0);
        this.setCount_of_favorite(dataBean.getCount_of_favorite());
        this.setCount_of_participation(dataBean.getCount_of_participation());
        this.setCount_of_comment(dataBean.getCount_of_comment());
        //设置时间等
        this.setCreated_at(dataBean.getCreated_at());
        this.setUpdated_at(dataBean.getUpdated_at());
        this.setHasFavorite(dataBean.getHasFavorite() != 0);
        //用户
        SpecialListEntity.DataBean.UserBean user = dataBean.getUser();
        if (user == null) {
            isDataEffectived = false;
            return;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setNickName(user.getNickname());
        userInfo.setAvatar(user.getAvatar());
        this.setUserInfo(userInfo);
        SchoolInfo schoolInfo = new SchoolInfo();
        schoolInfo.setId(user.getSchool_id());
        this.setSchoolInfo(schoolInfo);
    }

    private int id;
    private String dtype;
    private String title;

    private boolean tag;
    private int count_of_comment;
    private int count_of_favorite;
    private int count_of_participation;
    private String created_at;
    private String updated_at;
    private boolean hasFavorite;
    private UserInfo userInfo;
    private SchoolInfo schoolInfo;
    private String doc;
    private List<String> img;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean getTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public int getCount_of_comment() {
        return count_of_comment;
    }

    public void setCount_of_comment(int count_of_comment) {
        this.count_of_comment = count_of_comment;
    }

    public int getCount_of_favorite() {
        return count_of_favorite;
    }

    public void setCount_of_favorite(int count_of_favorite) {
        this.count_of_favorite = count_of_favorite;
    }

    public int getCount_of_participation() {
        return count_of_participation;
    }

    public void setCount_of_participation(int count_of_participation) {
        this.count_of_participation = count_of_participation;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public boolean getHasFavorite() {
        return hasFavorite;
    }

    public void setHasFavorite(boolean hasFavorite) {
        this.hasFavorite = hasFavorite;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public SchoolInfo getSchoolInfo() {
        return schoolInfo;
    }

    public void setSchoolInfo(SchoolInfo schoolInfo) {
        this.schoolInfo = schoolInfo;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }
///////////////////////////////公共数据

}
