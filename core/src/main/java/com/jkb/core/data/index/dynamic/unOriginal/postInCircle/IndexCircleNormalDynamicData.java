package com.jkb.core.data.index.dynamic.unOriginal.postInCircle;

import com.jkb.api.entity.dynamic.DynamicListEntity;
import com.jkb.core.data.index.dynamic.IndexDynamicData;
import com.jkb.core.data.index.dynamic.unOriginal.favorite.IndexFavoriteDynamicData;
import com.jkb.core.data.info.dynamic.content.DynamicContentNormalInfo;

/**
 * 首页-动态的原创普通动态实体类
 * Created by JustKiddingBaby on 2016/11/7.
 */

public class IndexCircleNormalDynamicData extends IndexCircleDynamicData {

    private boolean isDataEffective = true;

    private DynamicContentNormalInfo normal;

    public IndexCircleNormalDynamicData() {
    }

    public IndexCircleNormalDynamicData(
            DynamicListEntity.DataBean.DynamicBean.ContentBean.NormalBean normalBean) {
        handleNormalData(normalBean);
    }

    @Override
    public IndexDynamicData getIndexDynamic() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理普通动态数据
     */
    private void handleNormalData(
            DynamicListEntity.DataBean.DynamicBean.ContentBean.NormalBean normalBean) {
        if (normalBean == null) {
            isDataEffective = false;
            return;
        }
        normal = new DynamicContentNormalInfo();
        normal.setImg(normalBean.getImg());
        normal.setDoc(normalBean.getDoc());
        setNormal(normal);
    }

    public DynamicContentNormalInfo getNormal() {
        return normal;
    }

    public void setNormal(DynamicContentNormalInfo normal) {
        this.normal = normal;
    }
}
