package com.jkb.core.contract.function.data.hot;

import com.jkb.core.contract.function.data.hot.circle.CircleDynamic;
import com.jkb.core.contract.function.data.hot.dynamic.OriginalDynamic;
import com.jkb.core.contract.function.data.hot.user.UserDynamic;

/**
 * 热门动态的数据类
 * Created by JustKiddingBaby on 2016/9/30.
 */

public abstract class HotDynamic {

    private String hotDynamicData;
    private String hotDynamicType;

    private HotDynamic INSTANCE;

    public HotDynamic(String hotDynamicData) {
        this.hotDynamicData = hotDynamicData;
        handleHotDynamicData();
    }

    /**
     * 得到热门动态
     */
    public HotDynamic getHotDynamic() {
        return INSTANCE.getHotDynamic();
    }

    /**
     * 解析热门动态的数据
     */
    private void handleHotDynamicData() {
        //判断是哪种类型
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
        }
    }
}
