package com.jkb.core.presenter.first;

import android.support.annotation.NonNull;

import com.jkb.core.contract.first.FirstContract;
import com.jkb.model.first.firstlogic.FirstData;
import com.jkb.model.first.firstlogic.FirstDataResponsitory;
import com.jkb.model.first.firstlogic.FirstDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * FirstActivity控制器的逻辑Presenter类
 * Created by JustKiddingBaby on 2016/7/22.
 */
public class FirstPresenter implements FirstContract.Presenter {


    private FirstDataResponsitory firstDataResponsitory;
    private FirstContract.View firstView;

    /**
     * 初始化Presenter并且绑定View
     *
     * @param firstDataResponsitory
     * @param firstView
     */
    public FirstPresenter(@NonNull FirstDataResponsitory firstDataResponsitory, @NonNull FirstContract.View firstView) {
        this.firstDataResponsitory = checkNotNull(firstDataResponsitory, "firstDataResponsitory不能为空");
        this.firstView = checkNotNull(firstView, "firstView不能为空");

        this.firstView.setPresenter(this);
    }

    @Override
    public void start() {
        chooseFragment();
    }

    @Override
    public void chooseFragment() {
        firstDataResponsitory.getFirstData(callBack);
    }

    FirstDataSource.FirstDataCallBack callBack = new FirstDataSource.FirstDataCallBack() {
        @Override
        public void onFirstDataLoaded(FirstData firstData) {
            handleData(firstData);
        }

        @Override
        public void onDataNotAvailable() {
            firstView.showFragment(0);
        }
    };

    /**
     * 处理数据
     *
     * @param firstData
     */
    private void handleData(FirstData firstData) {
        if (firstData.getShowPosition() == FirstData.ShowPosition.WELCOME) {
            firstView.showFragment(0);
        } else if (firstData.getShowPosition() == FirstData.ShowPosition.GUIDE) {
            firstView.showFragment(1);
        } else if (firstData.getShowPosition() == FirstData.ShowPosition.ADVENT) {
            firstView.showFragment(2);
        }
    }
}
