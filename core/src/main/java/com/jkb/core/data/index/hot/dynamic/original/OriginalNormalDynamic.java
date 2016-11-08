package com.jkb.core.data.index.hot.dynamic.original;

import com.jkb.api.entity.dynamic.DynamicPopularListEntity;
import com.jkb.core.data.index.hot.HotDynamic;

/**
 * 原创的普通热门动态数据
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class OriginalNormalDynamic extends OriginalDynamic {

    public OriginalNormalDynamic(DynamicPopularListEntity.DataBean.DynamicBean dynamic) {
    }

    @Override
    public HotDynamic getHotDynamic() {
        return this;
    }
}
