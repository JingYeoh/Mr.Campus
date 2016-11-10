package com.jkb.mrcampus.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jkb.core.control.userstate.LoginContext;
import com.jkb.model.info.UserInfoSingleton;
import com.jkb.model.utils.LogUtils;
import com.jkb.mrcampus.Mr_Campus;
import com.jkb.mrcampus.helper.map.MyLocationListener;
import com.jkb.mrcampus.manager.MapManagerSingleton;

import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

/**
 * 菌菌的服务
 * 方式：不与Activity绑定，直接进行定位
 * 地图：定位、周边雷达
 * 功能：连接融云服务器等
 * Created by JustKiddingBaby on 2016/8/12.
 */
public class MrCampusService extends Service implements MrCampusServiceAction, Observer {

    private static final String TAG = "MrCampusService";
    private ServiceInformBinder binder = new ServiceInformBinder();

    /**
     * 得到service的一个类，用于交互的时候使用
     */
    public class ServiceInformBinder extends Binder {
        public MrCampusService getService() {
            return MrCampusService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    //百度地图相关
    private MapManagerSingleton mapManager;

    private LocationClient mLocationClient = null;
    private BDLocationListener myListener = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mapManager = MapManagerSingleton.getInstance();
        //设置为登录的观察者
        LoginContext.getInstance().addObserver(this);
        initBaiduLocation();
        startBaiDuLocation();//开始定位
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient = null;
        LoginContext.getInstance().deleteObserver(this);
        myListener = null;
    }

    /**
     * 初始化百度地图进行定位
     */
    private void initBaiduLocation() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
        }
        if (myListener == null) {
            myListener = new MyLocationListener();
            mLocationClient.registerLocationListener(myListener); // 注册监听函数
            initLocation();
        }
    }

    /**
     * 初始化定位相关
     */
    private void initLocation() {
        Log.d(TAG, "initLocation");
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(MapManagerSingleton.TIME_UPLOAD_LOCATION);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setNeedDeviceDirect(true);//网络定位时候是否需要方向
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(false);// 可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        mLocationClient.setLocOption(option);
    }

    /**
     * 开始百度地图定位
     */
    private void startBaiDuLocation() {
        Log.d(TAG, "startBaiDuLocation");
        if (mapManager.isAbleLocation()) {
            // 开始定位
            mLocationClient.start();
        } else {
            stopBaiDuLocation();// 停止百度地图定位
        }
    }

    /**
     * 停止百度地图定位
     */
    private void stopBaiDuLocation() {
        Log.d(TAG, "stopBaiDuLocation");
        // 停止定位
        if (mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if (LoginContext.getInstance().isLogined()) {
            onLogin();
        } else {
            onLogout();
        }
    }

    @Override
    public void onLogin() {
        connectIM();
        bindJPushAlias();
    }

    @Override
    public void onLogout() {
        breakIMConnect();
        clearJPushAlias();
    }

    @Override
    public void connectIM() {
        String rong_im_token = UserInfoSingleton.getInstance().getUserAuths().getRong_im_token();
        if (getApplicationInfo().packageName.
                equals(Mr_Campus.getCurProcessName(getApplicationContext()))) {
            RongIMClient.ConnectionStatusListener.ConnectionStatus connectionStatus =
                    RongIM.getInstance().getCurrentConnectionStatus();
            if (connectionStatus == RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) {
                return;
            }
            RongIM.connect(rong_im_token, new RongIMClient.ConnectCallback() {
                @Override
                public void onTokenIncorrect() {
                    LogUtils.i(TAG, "onTokenIncorrect");
                }

                @Override
                public void onSuccess(String s) {
                    //反馈给LoginContext，表示连接成功让其重置登录
                    LogUtils.i(TAG, "connectIM success");
                    LoginContext.getInstance().connectIMSuccess();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    LogUtils.i(TAG, "连接IM失败：errorCode=" + errorCode.getValue());
                }
            });
        }
    }

    @Override
    public void breakIMConnect() {
        RongIM.getInstance().logout();
    }

    @Override
    public void bindJPushAlias() {
        Integer user_id = UserInfoSingleton.getInstance().getUserAuths().getUser_id();
        JPushInterface.setAlias(getApplicationContext(), user_id + "", tagAliasCallback);
    }

    @Override
    public void clearJPushAlias() {
        JPushInterface.setAlias(getApplicationContext(), "", tagAliasCallback);
    }

    /**
     * JPush的设置别名回调
     */
    private TagAliasCallback tagAliasCallback = new TagAliasCallback() {
        @Override
        public void gotResult(int i, String s, Set<String> set) {
            if (i == 0) {
                LogUtils.d(TAG, "-----设置的别名成功");
            } else {
                LogUtils.w(TAG, "-----设置别名失败，错误Code是" + i);
            }
        }
    };

}
