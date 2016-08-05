package com.jkb.core.presenter.map;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.jkb.core.contract.map.MapAtyContract;
import com.jkb.core.contract.map.MapContract;

/**
 * Map的Presenter层
 * Created by JustKiddingBaby on 2016/7/26.
 */
public class MapPresenter implements MapContract.Presenter {

    private MapContract.View mapView;
    private MapAtyContract.View mapAtyView;

    private Bitmap cacheMapSnapShort = null;

    public MapPresenter(@NonNull MapContract.View mapView) {
        this.mapView = mapView;

        this.mapView.setPresenter(this);
    }


    @Override
    public void setMapSnapShort(Bitmap bitmap) {
        cacheMapSnapShort = bitmap;
    }

    @Override
    public void recycleSnapShort() {
        if (cacheMapSnapShort != null && !cacheMapSnapShort.isRecycled()) {
            cacheMapSnapShort.recycle();
            cacheMapSnapShort = null;
        }
    }

    @Override
    public Bitmap getMapSnapShort() {
        return cacheMapSnapShort;
    }

    @Override
    public void start() {

    }
}
