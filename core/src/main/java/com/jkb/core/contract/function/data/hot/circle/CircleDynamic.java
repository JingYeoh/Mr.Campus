package com.jkb.core.contract.function.data.hot.circle;

import com.jkb.core.contract.function.data.hot.HotDynamic;

/**
 * 推荐的圈子热门动态
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class CircleDynamic extends HotDynamic {

    public CircleDynamic(String hotDynamicData) {
        super(hotDynamicData);
    }

    @Override
    public HotDynamic getHotDynamic() {
        return this;
    }
}
