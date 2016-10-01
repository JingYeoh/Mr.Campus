package com.jkb.core.contract.function.data.hot.user;

import com.jkb.core.contract.function.data.hot.HotDynamic;

/**
 * 推荐用户的热门动态
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class UserDynamic extends HotDynamic {

    public UserDynamic(String hotDynamicData) {
        super(hotDynamicData);
        //解析数据
    }

    @Override
    public HotDynamic getHotDynamic() {
        return this;
    }
}
