package com.jkb.core.data.dynamic.myUnOriginal.myFavorite;

import com.jkb.api.entity.dynamic.DynamicMyFavoriteEntity;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.circle.DynamicInCircleDynamic;
import com.jkb.core.data.dynamic.myUnOriginal.myFavorite.original.OriginalDynamic;

/**
 * 我喜欢的动态数据
 * Created by JustKiddingBaby on 2016/10/16.
 */

public class MyFavoriteDynamicData {

    protected DynamicMyFavoriteEntity.DataBean dataBean;
    private MyFavoriteDynamicData INSTANCE = null;

    //data
    private int dynamic_id;
    private String dtype;
    private String dynamic_created_at;
    private boolean has_favorite;
    private int count_of_comment;
    private int count_of_favorite;

    public MyFavoriteDynamicData() {
    }

    public MyFavoriteDynamicData(DynamicMyFavoriteEntity.DataBean dataBean) {
        this.dataBean = dataBean;
        handleMyFavoriteDynamic();
    }


    /**
     * 返回我喜歡的動態數據
     */
    public MyFavoriteDynamicData getMyFavoriteDynamic() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE.getMyFavoriteDynamic();
    }

    /**
     * 处理我喜欢的动态
     */
    private void handleMyFavoriteDynamic() {
        String dataType = dataBean.getDataType();
        switch (dataType) {
            case "dynamicInCircle"://圈子内动态
                INSTANCE = new DynamicInCircleDynamic(dataBean.getDynamicInCircle());
                break;
            case "dynamic"://动态
                INSTANCE = new OriginalDynamic(dataBean.getDynamic());
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

    public String getDtype() {
        return dtype;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }

    public String getDynamic_created_at() {
        return dynamic_created_at;
    }

    public void setDynamic_created_at(String dynamic_created_at) {
        this.dynamic_created_at = dynamic_created_at;
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
}
