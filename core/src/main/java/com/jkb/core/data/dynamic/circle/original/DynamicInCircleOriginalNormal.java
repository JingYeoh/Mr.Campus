package com.jkb.core.data.dynamic.circle.original;

import com.jkb.api.entity.circle.DynamicInCircleListEntity;
import com.jkb.core.data.dynamic.circle.DynamicInCircle;
import com.jkb.core.data.user.UserInfo;

import java.util.List;

/**
 * 圈子内原创动态：普通
 * Created by JustKiddingBaby on 2016/10/6.
 */

public class DynamicInCircleOriginalNormal extends DynamicInCircle {

    private boolean isDataEffective;

    private NormalContent normalContent;

    public DynamicInCircleOriginalNormal(DynamicInCircleListEntity.DataBean dataBean) {
        handleDynamicNormal(dataBean);
    }

    @Override
    public DynamicInCircle getDynamic() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理普通动态数据
     */
    private void handleDynamicNormal(DynamicInCircleListEntity.DataBean dynamic) {
        if (dynamic == null) {
            isDataEffective = false;
            return;
        }
        isDataEffective = true;
        setDynamic_id(dynamic.getDynamic_id());
        setDtype(dynamic.getDtype());
        setTitle(dynamic.getTitle());
        //设置话题内容数据
        DynamicInCircleListEntity.DataBean.ContentBean content = dynamic.getContent();
        DynamicInCircleListEntity.DataBean.ContentBean.NormalBean normal = content.getNormal();
        if (normal == null) {
            setNormalContent(null);
        } else {
            NormalContent normalContent = new NormalContent();
            normalContent.setImg(normal.getImg());
            normalContent.setDoc(normal.getDoc());
            setNormalContent(normalContent);
        }
        setTag(dynamic.getTag());
        setCount_of_comment(dynamic.getCount_of_comment());
        setCount_of_favorite(dynamic.getCount_of_favorite());
        setDynamic_created_at(dynamic.getCreated_at());
        if (dynamic.getHas_favorite() == 0) {
            setHas_favorite(false);
        } else {
            setHas_favorite(true);
        }
        //设置用户数据
        UserInfo userInfo = new UserInfo();
        userInfo.setId(dynamic.getAuthor_id());
        userInfo.setAvatar(dynamic.getAuthor_avatar());
        userInfo.setNickName(dynamic.getAuthor_nickname());
        setUser(userInfo);
    }

    public static class NormalContent {
        String doc;
        List<String> img;

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
    }

    public NormalContent getNormalContent() {
        return normalContent;
    }

    public void setNormalContent(NormalContent normalContent) {
        this.normalContent = normalContent;
    }
}
