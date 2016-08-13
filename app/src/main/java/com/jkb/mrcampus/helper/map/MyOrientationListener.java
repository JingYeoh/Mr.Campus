package com.jkb.mrcampus.helper.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * 用于百度地图封装的方向传感器的类
 * Created by JustKiddingBaby on 2016/8/12.
 */

public class MyOrientationListener implements SensorEventListener {

    private static final String TAG = "MyOrientationListener";

    private Context context;
    private SensorManager sensorManager;
    private Sensor sensor;

    private float lastX;
    private OnOrientationListener onOrientationListener;

    public MyOrientationListener(Context context) {
        this.context = context;
        Log.d(TAG, "MyOrientationListener");
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
//        Log.d(TAG, "getType=" + event.sensor.getType());
        // 接受方向感应器的类型
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            // 这里我们可以得到数据，然后根据需要来处理
            float x = event.values[SensorManager.DATA_X];

            if (Math.abs(x - lastX) > 1.0) {
                onOrientationListener.onOrientationChanged(x);
            }
//            Log.e("DATA_X", x + "");
            lastX = x;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Log.d(TAG, "onAccuracyChanged");
    }

    // 开始
    public void start() {
        // 获得传感器管理器
        sensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            // 获得方向传感器
            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        // 注册
        if (sensor != null) {//SensorManager.SENSOR_DELAY_UI
            sensorManager.registerListener(this, sensor,
                    SensorManager.SENSOR_DELAY_UI);
        }
    }

    // 停止检测
    public void stop() {
        sensorManager.unregisterListener(this);
    }


    public void setOnOrientationListener(OnOrientationListener onOrientationListener) {
        this.onOrientationListener = onOrientationListener;
    }


    public interface OnOrientationListener {
        void onOrientationChanged(float x);
    }
}
