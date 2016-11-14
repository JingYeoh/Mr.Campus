package com.jkb.core.presenter.map;

import android.support.annotation.NonNull;

import com.jkb.core.contract.map.MapAtyContract;

/**
 * MapAty的Presenter层
 * Created by JustKiddingBaby on 2016/7/28.
 */
public class MapAtyPresenter implements MapAtyContract.Presenter {

    private MapAtyContract.View mapView;

    public MapAtyPresenter(@NonNull MapAtyContract.View mapView) {
        this.mapView = mapView;

        this.mapView.setPresenter(this);
    }

    @Override
    public void chooseFragment(int position) {
        mapView.showFragment(position);

        switchShowView(position);
    }

    /**
     * 选择合适的控制器
     */
    private void switchShowView(int position) {
        if (position == 0) {
            mapView.showMapView();
        } else if (position == 1) {
            mapView.showListView();
        }
    }

    @Override
    public void start() {
        chooseFragment(0);
    }
}
