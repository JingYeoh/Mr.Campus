package com.jkb.core.contract.function.data.hot.dynamic;

import com.jkb.core.contract.function.data.hot.HotDynamic;

/**
 * 原创的热门动态
 * Created by JustKiddingBaby on 2016/9/30.
 */

public class OriginalDynamic extends HotDynamic {

    private String hotDynamicData;
    private String originalDynamicType;//原创的热门动态的类型
    private OriginalDynamic INSTANCE;

    public OriginalDynamic(String hotDynamicData) {
        super(hotDynamicData);
        this.hotDynamicData = hotDynamicData;
        handleOriginalDynamicData();
    }

    @Override
    public HotDynamic getHotDynamic() {
        return INSTANCE.getHotDynamic();
    }

    /**
     * 处理原创的热门动态数据
     */
    private void handleOriginalDynamicData() {
        originalDynamicType = "";
        //设置动态分类
        switch (originalDynamicType) {
            case "article":
                INSTANCE = new OriginalArticleDynamic(hotDynamicData);
                break;
            case "normal":
                INSTANCE = new OriginalNormalDynamic(hotDynamicData);
                break;
            case "topic":
                INSTANCE = new OriginalTopicDynamic(hotDynamicData);
                break;
        }
    }
}
