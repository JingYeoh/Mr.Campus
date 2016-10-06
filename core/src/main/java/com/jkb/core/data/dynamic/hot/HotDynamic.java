package com.jkb.core.data.dynamic.hot;

import android.support.annotation.NonNull;

import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.core.data.dynamic.hot.circle.CircleDynamic;
import com.jkb.core.data.dynamic.hot.dynamic.circle.DynamicInCircleDynamic;
import com.jkb.core.data.dynamic.hot.dynamic.original.OriginalDynamic;
import com.jkb.core.data.dynamic.hot.user.UserDynamic;

/**
 * 热门动态的数据类
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class HotDynamic {

    protected DynamicPopularListEntity.DataBean hotDynamicData;
    private String hotDynamicType;

    private HotDynamic INSTANCE;

    public HotDynamic(@NonNull DynamicPopularListEntity.DataBean hotDynamicData) {
        this.hotDynamicData = hotDynamicData;
        handleHotDynamicData();
    }

    public HotDynamic() {
    }

    /**
     * 得到热门动态
     */
    public HotDynamic getHotDynamic() {
        if (INSTANCE == null) {
            return null;
        }
        return INSTANCE.getHotDynamic();
    }

    /**
     * 解析热门动态的数据
     */
    private void handleHotDynamicData() {
        //判断是哪种类型
        hotDynamicType = hotDynamicData.getPopular_type();
        switch (hotDynamicType) {
            case "user":
                INSTANCE = new UserDynamic(hotDynamicData);
                break;
            case "dynamic":
                INSTANCE = new OriginalDynamic(hotDynamicData);
                break;
            case "circle":
                INSTANCE = new CircleDynamic(hotDynamicData);
                break;
            case "dynamicInCircle":
                INSTANCE = new DynamicInCircleDynamic(hotDynamicData);
                break;
            default:
                INSTANCE = null;
                return;
        }
    }
}
