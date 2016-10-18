package com.jkb.core.data.dynamic.myOriginal.myCircle;

import com.jkb.api.entity.dynamic.DynamicCircleListEntity;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentArticleInfo;
import com.jkb.core.data.info.dynamic.content.DynamicContentNormalInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 圈子内动态：普通
 * Created by JustKiddingBaby on 2016/10/17.
 */

public class CircleNormalDynamic extends DynamicInCircleDynamic {

    private boolean isData_effective = true;
    private DynamicContentNormalInfo normalInfo;

    public CircleNormalDynamic(DynamicCircleListEntity.DataBean dataBean) {
        handleArticleDynamicData(dataBean);
    }

    @Override
    public DynamicInCircleDynamic getDynamicInCircle() {
        if (!isData_effective) {
            return null;
        }
        return this;
    }

    /**
     * 处理圈子内的动态数据
     */
    private void handleArticleDynamicData(DynamicCircleListEntity.DataBean dynamic) {
        if (dynamic == null) {
            isData_effective = false;
            return;
        }
        isData_effective = true;
        setDynamic_id(dynamic.getDynamic_id());
        setTitle(dynamic.getTitle());
        //设置话题内容数据
        DynamicCircleListEntity.DataBean.ContentBean
                content = dynamic.getContent();
        if (content == null) {
            isData_effective = false;
            return;
        }
        DynamicCircleListEntity.DataBean.ContentBean.NormalBean reqNormal = content.getNormal();
        if (reqNormal == null) {
            isData_effective = false;
            return;
        }
        normalInfo = new DynamicContentNormalInfo();
        normalInfo.setDoc(reqNormal.getDoc());
        normalInfo.setImg(reqNormal.getImg());
        setNormalInfo(normalInfo);
        setTag(dynamic.getTag());
        setCount_of_comment(dynamic.getCount_of_comment());
        setCount_of_favorite(dynamic.getCount_of_favorite());
        setDynamic_updated_at(dynamic.getDynamic_update_time());
        if (dynamic.getHas_favorite() == 0) {
            setHas_favorite(false);
        } else {
            setHas_favorite(true);
        }
        //设置圈子数据
        CircleInfo circleInfo = new CircleInfo();
        circleInfo.setCircleId(dynamic.getCircle_id());
        circleInfo.setCircleName(dynamic.getCircle_name());
        circleInfo.setCircleType(dynamic.getCircle_type());
        circleInfo.setPictureUrl(dynamic.getCircle_picture());
        circleInfo.setIntroduction(dynamic.getCircle_introduction());
        setCircle(circleInfo);
    }

    public DynamicContentNormalInfo getNormalInfo() {
        return normalInfo;
    }

    public void setNormalInfo(DynamicContentNormalInfo normalInfo) {
        this.normalInfo = normalInfo;
    }
}
