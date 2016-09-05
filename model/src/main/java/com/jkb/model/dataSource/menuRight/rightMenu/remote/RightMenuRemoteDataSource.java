package com.jkb.model.dataSource.menuRight.rightMenu.remote;

import com.jkb.model.dataSource.menuRight.rightMenu.RightMenuDataSource;

/**
 * 右滑菜单的本地数据来源类
 * Created by JustKiddingBaby on 2016/9/5.
 */

public class RightMenuRemoteDataSource implements RightMenuDataSource {


    private RightMenuRemoteDataSource() {
    }

    private static RightMenuRemoteDataSource INSTANCE = null;

    public static RightMenuRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RightMenuRemoteDataSource();
        }
        return INSTANCE;
    }
}
