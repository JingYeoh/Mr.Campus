package com.jkb.core.presenter.create$circle;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.entity.baidu.map.webService.ReverseGeocodingEntity;
import com.jkb.core.contract.create$circle.SelectCircleCoordinateContract;
import com.jkb.model.dataSource.baidu.map.webService.BaiduMapWebServiceResponsitory;
import com.jkb.model.info.LocationInfoSingleton;
import com.jkb.model.info.SingletonManager;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.Config;

import retrofit2.Response;

/**
 * 选择学校的坐标点的Presenter层
 * Created by JustKiddingBaby on 2016/8/11.
 */

public class SelectCircleCoordinatePresenter implements SelectCircleCoordinateContract.Presenter {

    private static final String TAG = "CoordinatePresenter";
    private SelectCircleCoordinateContract.View view;
    private LocationInfoSingleton locationInfoSingleton;
    private BaiduMapWebServiceResponsitory baiduMapWebServiceResponsitory;

    public SelectCircleCoordinatePresenter(@NonNull SelectCircleCoordinateContract.View view,
                                           @NonNull BaiduMapWebServiceResponsitory baiduMapWebServiceResponsitory) {
        this.view = view;
        this.baiduMapWebServiceResponsitory = baiduMapWebServiceResponsitory;

        locationInfoSingleton = (LocationInfoSingleton)
                SingletonManager.getService(Config.SINGLETON_KEY_LOCATION);//得到定位的单例类

        view.setPresenter(this);
    }

    @Override
    public void moveToLocation() {
        double latitude = locationInfoSingleton.latitude;
        double longitude = locationInfoSingleton.longitude;
        if (longitude == -9990 && latitude == -9990) {
            return;
        }
        view.moveToCoordinate(longitude, latitude);
    }

    @Override
    public void moveToSchoolCenterCoordinate() {
        moveToLocation();//移动到当前位置
    }

    @Override
    public void getGeocode(double lantitude, double longitude) {
        baiduMapWebServiceResponsitory.geocoder(
                com.jkb.api.config.Config.APP_BAIDU_MAP_AK,
                lantitude, longitude, 0, com.jkb.api.config.Config.APP_BAIDU_MAP_MCODE,
                new ApiCallback<ReverseGeocodingEntity>() {
                    @Override
                    public void onSuccess(Response<ReverseGeocodingEntity> response) {
                        ReverseGeocodingEntity entity = response.body();
                        Log.d(TAG, entity.toString());
                        ReverseGeocodingEntity.ResultBean resultBean = entity.getResult();
                        String address="地址查询失败...";
                        if (resultBean != null) {
                            address = resultBean.getSematic_description();
                        }
                        if(view.isActive()){
                            view.setAddressName(address);
                            view.showDetermineView();
                        }
                    }

                    @Override
                    public void onError(Response<ReverseGeocodingEntity> response, String error, ReverseGeocodingEntity apiResponse) {
                        if(view.isActive()){
                            view.setAddressName("地址查询错误...");
                        }
                    }

                    @Override
                    public void onFail() {
                        if(view.isActive()) {
                            view.setAddressName("地址查询失败...");
                        }
                    }
                });
    }

    @Override
    public void start() {
        view.setSchoolName("金陵科技学院");//设置学校名称
        //设置头像
        setImageHeadImg();
        //移动到学校中心点
        moveToSchoolCenterCoordinate();
    }

    /**
     * 设置显示的头像
     */
    private void setImageHeadImg() {
        UserInfoSingleton userInfoSingleton = UserInfoSingleton.getInstance();
        view.setLocationUserHeadImgBitmap(userInfoSingleton.getUsers().getAvatar());
    }
}
