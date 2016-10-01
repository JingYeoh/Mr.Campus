package com.jkb.core.contract.function.data.hot.dynamic;

import com.jkb.core.contract.function.data.hot.HotDynamic;

/**
 * 原创的普通热门动态数据
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class OriginalNormalDynamic extends OriginalDynamic {

    public OriginalNormalDynamic(String hotDynamicData) {
        super(hotDynamicData);
    }

    @Override
    public HotDynamic getHotDynamic() {
        return this;
    }
}
