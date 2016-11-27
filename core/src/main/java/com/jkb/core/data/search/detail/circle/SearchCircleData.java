package com.jkb.core.data.search.detail.circle;

import com.jkb.api.entity.search.SearchEntity;
import com.jkb.core.data.info.circle.CircleInfo;
import com.jkb.core.data.search.detail.SearchData;

/**
 * 圈子搜索详情的数据类
 * Created by JustKiddingBaby on 2016/11/26.
 */

public class SearchCircleData extends SearchData {

    //data
    private CircleInfo circle;

    public SearchCircleData() {
    }

    public SearchCircleData(SearchEntity.DataBean search) {
        handleSearchCircleData(search);
    }

    @Override
    public SearchData getSearchData() {
        if (isDataEffective) {
            return this;
        }
        return null;
    }

    /**
     * 处理搜索的圈子数据
     */
    private void handleSearchCircleData(SearchEntity.DataBean search) {
        SearchEntity.DataBean.CircleBean reqCircle = search.getCircle();
        if (reqCircle == null) {
            isDataEffective = false;
            return;
        }
        circle = new CircleInfo();
        circle.setCircleId(reqCircle.getId());
        circle.setCircleName(reqCircle.getName());
        circle.setCircleType(reqCircle.getType());
        circle.setPictureUrl(reqCircle.getPicture());
        circle.setIntroduction(reqCircle.getIntroduction());
        circle.setOperatorCount(reqCircle.getCount_of_subscription());
        setCircle(circle);
    }

    public CircleInfo getCircle() {
        return circle;
    }

    public void setCircle(CircleInfo circle) {
        this.circle = circle;
    }
}
