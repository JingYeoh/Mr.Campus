package com.jkb.core.data.circle.dynamicInBlackList;

import com.jkb.api.entity.circle.CircleDynamicInBlackListEntity;
import com.jkb.core.data.info.dynamic.content.DynamicContentNormalInfo;

/**
 * 普通的动态数据
 * Created by JustKiddingBaby on 2016/11/5.
 */

public class NormalDynamicInBlackList extends DynamicInBlackList {

    private boolean isDataEffective = true;

    private DynamicContentNormalInfo normalInfo;

    public NormalDynamicInBlackList(
            CircleDynamicInBlackListEntity.DataBean.ContentBean.NormalBean normalBean) {
        handleNormalData(normalBean);
    }

    @Override
    public DynamicInBlackList getDynamicInBlackList() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理普通数据
     */
    private void handleNormalData(
            CircleDynamicInBlackListEntity.DataBean.ContentBean.NormalBean normalBean) {
        if (normalBean == null) {
            isDataEffective = false;
            return;
        }
        normalInfo = new DynamicContentNormalInfo();
        normalInfo.setDoc(normalBean.getDoc());
        normalInfo.setImg(normalBean.getImg());
        setNormalInfo(normalInfo);
    }

    public DynamicContentNormalInfo getNormalInfo() {
        return normalInfo;
    }

    public void setNormalInfo(DynamicContentNormalInfo normalInfo) {
        this.normalInfo = normalInfo;
    }
}
