package com.jkb.mrcampus.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

/**
 * 支持伸缩的ImageView
 * <p>
 * 目前默认拉伸的图片为拉伸为全屏
 * <p>
 * 最大缩放比例为1.5f
 *
 * @author JustKiddingBaby
 */
public class DrawnImageView extends ImageView {

    private Context context;// 上下文对象
    private static final String TAG = "DrawnImageView";

    /**
     * 初始的矩阵
     **/
    private Matrix matrix = new Matrix();

    /**
     * 屏幕的分辨率
     */
    private DisplayMetrics dm;
    /**
     * 位图对象
     **/
    private Bitmap bitmap = null;
    /**
     * 图片移动的速度
     **/
    private int speed = 1000;
    /**
     * 每次移动的像素距离
     **/
    private static final int moveDx = 1;
    private static final int moveDy = 1;
    /**
     * 最大缩放比例
     **/
    private static final float diskMax = 1.5f;
    /**
     * 原始缩放比例
     **/
    private static final float diskIitial = 1.0f;
    /**
     * 保存初始矩阵位置的两坐标 (居中的)
     **/
    private float delta[];
    /**
     * 是否停止拉伸的标识
     **/
    private boolean flagRunningDrawn = false;
    // 用于发送消息的
    private static final int WHAT_RUNNING_DRAWN = 10000;

    // 每移动一个周期变化的像素点所花的时间
    private long timeEachDrawn = 1;

    public DrawnImageView(Context context) {
        super(context);
        initView();
    }

    public DrawnImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        Log.d(TAG, "setImageBitmap---->bitmap=" + bm);
        bitmap = bm;
//        this.setImageBitmap(bitmap);
        if (bitmap != null) {
            center(true, true);
        }
    }

    /**
     * 初始化方法
     */
    private void initView() {
        Log.d(TAG, "initView");
        context = getContext();
        // 初始化矩阵的初始坐标
        delta = new float[2];
        // 获取屏幕分辨率,需要根据分辨率来使用图片居中
        dm = context.getResources().getDisplayMetrics();
        // 获取bitmap对象
        BitmapDrawable bd = (BitmapDrawable) this.getDrawable();
        if (bd != null) {
            bitmap = bd.getBitmap();
        }

        // 设置ScaleType为ScaleType.MATRIX，这一步很重要
        // ScaleType.MATRIX表示用矩阵来绘制,矩阵可以动态放大缩小图片
//        this.setScaleType(ScaleType.FIT_XY);
        this.setScaleType(ScaleType.MATRIX);
        this.setImageBitmap(bitmap);

        // bitmap为空就不调用center函数
        if (bitmap != null) {
            center(true, true);
        }
        this.setImageMatrix(matrix);// 设置到图像中显示
    }

    /**
     * 开始拉伸
     * 调用完毕记得调用stop方法，不然会持续消耗内存
     */
    public void startDraw() {
        flagRunningDrawn = true;
        this.setScaleType(ScaleType.MATRIX);
        int imageWidth = bitmap.getWidth();
        timeEachDrawn = Math.abs((moveDx * speed) / (2 * imageWidth));
        drawnHandler.sendEmptyMessageDelayed(WHAT_RUNNING_DRAWN, timeEachDrawn);
    }

    /**
     * 停止拉伸
     * 停止图片拉伸
     */
    public void stopDraw() {
        flagRunningDrawn = false;
    }

    /**
     * 设定缩放的速度
     * 建议放在start方法之前调用
     *
     * @param speed 缩放速度，单位；秒，默认1000
     */
    public void setDrawnSpeed(int speed) {
        this.speed = speed;
    }

    public void setImageFullScreen3() {
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float height = rect.height();// 得到图片的高度
        float width = rect.width();// 得到图片的宽度

        // 父容器的宽高
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        // 得到缩放的宽高比例
        float sx = screenWidth / width;
        float sy = screenHeight / height;

        Log.d(TAG, "sx=" + sx);
        Log.d(TAG, "sy=" + sy);

        // 缩放
        matrix.postScale(sx, sy, screenWidth / 2, screenHeight / 2);
        matrix.set(matrix);// 设置为全屏这么大
        this.setImageMatrix(matrix);
    }

    /**
     * 设置图片全屏
     */
    public void setImageFullScreen() {
        // this.setScaleType(ScaleType.FIT_XY);
        Matrix m = new Matrix();
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);


        float height = rect.height();// 得到图片的高度
        float width = rect.width();// 得到图片的宽度

        // 父容器的宽高
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        //放大縮小的比例
        float deltaX = 0, deltaY = 0;

        //判断时做放大还是缩小的操作
        if (height < screenHeight) {
            deltaY = (screenHeight - height) / 2 - rect.top;
        } else {
            deltaY = screenHeight / height;
        }

        if (width < screenWidth) {
            deltaX = (screenWidth - width) / 2 - rect.left;
        } else {
            deltaX = screenWidth / width;
        }

        Log.d(TAG, "deltaX=" + deltaX);
        Log.d(TAG, "deltaY=" + deltaY);

        if (deltaX == 0 && deltaY == 0) {
            return;
        }

        // 缩放
        matrix.postScale(deltaX, deltaY, screenWidth / 2, screenHeight / 2);
        matrix.set(matrix);// 设置为缩放后的大小
        this.setImageMatrix(matrix);
    }

    /**
     * 横向、纵向居中
     */
    private void center(boolean horizontal, boolean vertical) {
        Matrix m = new Matrix();
        m.set(matrix);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);

        float height = rect.height();
        float width = rect.width();

        float deltaX = 0, deltaY = 0;

        if (vertical) {
            // 图片小于屏幕大小，则居中显示。大于屏幕，上方留空则往上移，下方留空则往下移
            int screenHeight = dm.heightPixels;
            if (height < screenHeight) {
                deltaY = (screenHeight - height) / 2 - rect.top;
            } else if (rect.top > 0) {
                deltaY = -rect.top;
            } else if (rect.bottom < screenHeight) {
                deltaY = this.getHeight() - rect.bottom;
            }
        }

        if (horizontal) {
            int screenWidth = dm.widthPixels;
            if (width < screenWidth) {
                deltaX = (screenWidth - width) / 2 - rect.left;
            } else if (rect.left > 0) {
                deltaX = -rect.left;
            } else if (rect.right < screenWidth) {
                deltaX = screenWidth - rect.right;
            }
        }

        System.out.println(deltaX + ":" + deltaY);
        delta[0] = deltaX;
        delta[1] = deltaY;
        matrix.postTranslate(deltaX, deltaY);
        this.setImageMatrix(matrix);
    }

    /**
     * 拉伸图片
     */
    private void drawnImageView() {
        Matrix m = new Matrix();
        m.set(matrix);
        RectF rect = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        m.mapRect(rect);

        // 父容器的宽高
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        // 得到缩放的宽高比例
        float sx = (diskIitial + ((diskMax - diskIitial) / speed));
        float sy = (diskIitial + ((diskMax - diskIitial) / speed));

//        Log.d(TAG, "sx=" + sx);
//        Log.d(TAG, "sy=" + sy);

        // 缩放
        matrix.postScale(sx, sy, screenWidth / 2, screenHeight / 2);
        matrix.set(matrix);
        this.setImageMatrix(matrix);
    }

    // 用于拉伸的handler
    private Handler drawnHandler = new Handler() {

        // 在此处处理消息
        public void handleMessage(android.os.Message msg) {
            if (flagRunningDrawn) {
                drawnImageView();
                drawnHandler.sendEmptyMessageDelayed(WHAT_RUNNING_DRAWN,
                        timeEachDrawn);
            } else {

            }
        }
    };
}
