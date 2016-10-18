package com.jkb.core.data.dynamic.myOriginal.myCircle;

import com.jkb.api.entity.dynamic.DynamicCircleListEntity;
import com.jkb.core.data.info.circle.CircleInfo;

/**
 * 圈子内动态
 * Created by JustKiddingBaby on 2016/10/17.
 */

public class DynamicInCircleDynamic {


    public DynamicInCircleDynamic(DynamicCircleListEntity.DataBean dataBean) {
        handleDynamicInCircleData(dataBean);
    }

    public DynamicInCircleDynamic() {
    }

    private static DynamicInCircleDynamic INSTANCE = null;

    //data
    private int dynamic_id;
    private String dynamic_updated_at;
    private boolean has_favorite;
    private int count_of_comment;
    private int count_of_favorite;
    private String title;
    private String tag;
    private CircleInfo circle;//圈子

    /**
     * 得到具体的圈子内动态对象
     */
    public DynamicInCircleDynamic getDynamicInCircle() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE.getDynamicInCircle();
    }

    /**
     * 处理圈内动态数据
     */
    private void handleDynamicInCircleData(DynamicCircleListEntity.DataBean dataBean) {
        String dynamic_type = dataBean.getDynamic_type();
        switch (dynamic_type) {
            case "article":
                INSTANCE = new CircleArticleDynamic(dataBean);
                break;
            case "topic":
                INSTANCE = new CircleTopicDynamic(dataBean);
                break;
            case "normal":
                INSTANCE = new CircleNormalDynamic(dataBean);
                break;
            default:
                INSTANCE = null;
                break;
        }
    }

    public int getDynamic_id() {
        return dynamic_id;
    }

    public void setDynamic_id(int dynamic_id) {
        this.dynamic_id = dynamic_id;
    }


    public String getDynamic_updated_at() {
        return dynamic_updated_at;
    }

    public void setDynamic_updated_at(String dynamic_updated_at) {
        this.dynamic_updated_at = dynamic_updated_at;
    }

    public boolean isHas_favorite() {
        return has_favorite;
    }

    public void setHas_favorite(boolean has_favorite) {
        this.has_favorite = has_favorite;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public CircleInfo getCircle() {
        return circle;
    }

    public void setCircle(CircleInfo circle) {
        this.circle = circle;
    }
}
