package com.jkb.mrcampus.view.zoomImageView;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

/**
 * 支持缩放的ImageView
 * Created by panl on 15/4/15.
 */
public class PictureView extends ImageView implements ViewTreeObserver.OnGlobalLayoutListener,
        ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener {

    //初始化一次
    private Boolean mOnce = false;

    //初始化时缩放的值
    private float mInitScale;
    //双击放大时缩放的值
    private float mMidScale;
    //放大的极限
    private float mMaxScale;

    private Matrix mScaleMatrix;
    /**
     * 捕获用户多点触控时缩放的比例
     */
    private ScaleGestureDetector mScaleGestureDetector;


    //---------自由移动

    /**
     * 记录上一次多点触控的数量
     */
    private int mLastPointerCount;

    private float mLastX;
    private float mLastY;

    private int mTouchSlop;
    private boolean isCanDrag;

    private Boolean isCheckLeftAndRight;
    private Boolean isCheckTopAndBottom;

    //--------------双击放大与缩小
    private GestureDetector mGestureDetector;

    private Boolean isAutoScale = false;

    public PictureView(Context context) {
        this(context, null);
    }

    public PictureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //init
        mScaleMatrix = new Matrix();
        setScaleType(ScaleType.MATRIX);
        mScaleGestureDetector = new ScaleGestureDetector(context, this);
        setOnTouchListener(this);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                if (isAutoScale)
                    return true;

                float x = e.getX();
                float y = e.getY();


                if (getScale() < mMidScale) {
//                    mScaleMatrix.postScale(mMidScale/getScale(),mMidScale/getScale(),
//                            getWidth()/2,getHeight()/2);
//                    setImageMatrix(mScaleMatrix);
                    isAutoScale = true;

                    postDelayed(new AutoScaleRunnable(mMidScale, x, y), 10);
                } else {
//                    mScaleMatrix.postScale(mInitScale/getScale(),mInitScale/getScale(),
//                            getWidth()/2,getHeight()/2);
//                    setImageMatrix(mScaleMatrix);
                    isAutoScale = true;

                    postDelayed(new AutoScaleRunnable(mInitScale, x, y), 10);
                }
                return true;
            }

        });
    }

    /**
     * 当view显示在窗口中时调用
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        //注册OnGlobalLayoutListener
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    /**
     * 当view从窗口中消失时调用
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        //移除OnGlobalLayoutListener
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
    }

    /**
     * 获取ImageView里面加载完成的图片
     */
    @Override
    public void onGlobalLayout() {

        if (!mOnce) {
            //得到图片父控件的宽和高
            int width = getWidth();
            int height = getHeight();
            //得到图片以及宽和高
            Drawable drawable = getDrawable();
            if (drawable == null)
                return;

            int dWidth = drawable.getIntrinsicWidth();
            int dHeight = drawable.getIntrinsicHeight();

            float scale = 1.0f;
            /**
             * 如果图片的宽度大于父控件的宽度，高度小于父控件的高度，将其缩小
             */
            if (dWidth > width && dHeight < height) {
                scale = width * 1.0f / dWidth;
            }
            /**
             * 如果图片的高度大于父控件的高度，宽度小于父控件的宽度，将其缩小
             */
            if (dHeight > height && dWidth < width) {
                scale = height * 1.0f / dHeight;
            }
            /**
             * 如果图片的宽高度都大于父控件的宽高度，或者相反，将其缩放到完全能够完全显示
             */
            if ((dWidth > width && dHeight > height) || (dWidth < width && dHeight < height)) {
                scale = Math.min(width * 1.0f / dWidth, height * 1.0f / dHeight);
            }

            //得到初始化时图片缩放的比例
            mInitScale = scale;
            mMaxScale = mInitScale * 10;
            mMidScale = mInitScale / 2;

            //将图片移动至父控件的中心
            int dx = getWidth() / 2 - dWidth / 2;
            int dy = getHeight() / 2 - dHeight / 2;

            mScaleMatrix.postTranslate(dx, dy);
            mScaleMatrix.postScale(mInitScale, mInitScale, width / 2, height / 2);
            setImageMatrix(mScaleMatrix);

            mOnce = true;
        }

    }

    /**
     * 获取当前图片的缩放值
     *
     * @return
     */
    public float getScale() {
        float[] values = new float[9];
        mScaleMatrix.getValues(values);
        return values[Matrix.MSCALE_X];
    }

    //缩放的区间mInitScale - mMaxScale
    @Override
    public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
        //获得当前图片的缩放值
        float scale = getScale();
        //获得手指的缩放值
        float scaleFactor = scaleGestureDetector.getScaleFactor();

        if (getDrawable() == null)
            return true;
        //缩放范围的控制
        if ((scale < mMaxScale && scaleFactor > 1.0f)
                || (scale > mInitScale && scaleFactor < 1.0f)) {

            if (scale * scaleFactor < mInitScale) {
                scaleFactor = mInitScale / scale;
            }
            if (scale * scaleFactor > mMaxScale) {
                scaleFactor = mMaxScale / scale;
            }
            mScaleMatrix.postScale(scaleFactor, scaleFactor,
                    scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

        }


        return true;
    }

    /**
     * 在缩放的时候进行边界控制以及位置的控制
     */
    private void checkBorderAndCenterWhenScale() {
        RectF rectF = getMatrixRectF();

        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();
        //缩放时进行边界控制
        if (rectF.width() >= width) {
            if (rectF.left > 0) {
                deltaX = -rectF.left;
            }
            if (rectF.right < width) {
                deltaX = width - rectF.right;
            }
        }

        if (rectF.height() >= height) {
            if (rectF.top > 0) {
                deltaY = -rectF.top;
            }
            if (rectF.bottom < height) {
                deltaY = height - rectF.bottom;
            }
        }

        //如果宽度和高度小于父控件的宽和高，让其居中
        if (rectF.width() < width) {
            deltaX = width / 2 - rectF.right + rectF.width() / 2;
        }
        if (rectF.height() < height) {
            deltaY = height / 2 - rectF.bottom + rectF.height() / 2;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        if (mGestureDetector.onTouchEvent(motionEvent))
            return true;
        //将触摸事件传递给OnScaleGestureListener
        mScaleGestureDetector.onTouchEvent(motionEvent);
        float x = 0;
        float y = 0;
        //拿到多点触控的数量
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            x += motionEvent.getX(i);
            y += motionEvent.getY(i);
        }

        x /= pointerCount;
        y /= pointerCount;

        if (mLastPointerCount != pointerCount) {
            isCanDrag = false;
            mLastX = x;
            mLastY = y;
        }

        mLastPointerCount = pointerCount;

        RectF rectF = getMatrixRectF();
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01) {
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (rectF.right == getWidth() || rectF.left == 0.0f) {
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (rectF.width() > getWidth() + 0.01 || rectF.height() > getHeight() + 0.01) {
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (rectF.right == getWidth() || rectF.left == 0.0f) {
                    if (getParent() instanceof ViewPager)
                        getParent().requestDisallowInterceptTouchEvent(false);
                }
                float dx = x - mLastX;
                float dy = y - mLastY;

                if (!isCanDrag) {
                    isCanDrag = isMoveAction(dx, dy);
                }

                if (isCanDrag) {
                    if (getDrawable() != null) {

                        isCheckLeftAndRight = isCheckTopAndBottom = true;
                        if (rectF.width() < getWidth()) {

                            isCheckLeftAndRight = false;
                            dx = 0;
                        }
                        if (rectF.height() < getHeight()) {
                            isCheckTopAndBottom = false;
                            dy = 0;
                        }
                        mScaleMatrix.postTranslate(dx, dy);
                        checkBorderWhenTranslate();
                        setImageMatrix(mScaleMatrix);
                    }

                }
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                mLastPointerCount = 0;
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 当移动时进行边界检查
     */
    private void checkBorderWhenTranslate() {

        RectF rectF = getMatrixRectF();
        float deltaX = 0;
        float deltaY = 0;

        int width = getWidth();
        int height = getHeight();

        if (rectF.top > 0 && isCheckTopAndBottom) {
            deltaY = -rectF.top;
        }
        if (rectF.bottom < height && isCheckTopAndBottom) {
            deltaY = height - rectF.bottom;
        }
        if (rectF.left > 0 && isCheckLeftAndRight) {
            deltaX = -rectF.left;
        }
        if (rectF.right < width && isCheckLeftAndRight) {
            deltaX = width - rectF.right;
        }
        mScaleMatrix.postTranslate(deltaX, deltaY);

    }

    /**
     * 获得图片缩放以后的宽和高，以及left，right，top，bottom
     */
    private RectF getMatrixRectF() {

        Matrix matrix = mScaleMatrix;
        RectF rectF = new RectF();

        Drawable d = getDrawable();
        if (d != null) {
            rectF.set(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
            matrix.mapRect(rectF);
        }

        return rectF;
    }

    /**
     * 判断是否足以触发移动动作
     *
     * @param dx
     * @param dy
     * @return
     */
    private boolean isMoveAction(float dx, float dy) {

        return Math.sqrt(dx * dx + dy * dy) > mTouchSlop;
    }

    /**
     * 自动放大与缩小
     */
    private class AutoScaleRunnable implements Runnable {
        private final float BIGGER = 1.07f;
        private final float SMALLER = 0.93f;
        /**
         * 缩放的目标值
         */
        private float mTargetScale;
        private float x;
        private float y;

        private float tmpScale;

        public AutoScaleRunnable(float mTargetScale, float x, float y) {
            this.mTargetScale = mTargetScale;
            this.x = x;
            this.y = y;

            if (getScale() < mTargetScale) {
                tmpScale = BIGGER;
            }
            if (getScale() > mTargetScale) {
                tmpScale = SMALLER;
            }

        }

        @Override
        public void run() {
            //进行缩放
            mScaleMatrix.postScale(tmpScale, tmpScale, x, y);
            checkBorderAndCenterWhenScale();
            setImageMatrix(mScaleMatrix);

            float currentScale = getScale();
            if ((tmpScale > 1.0f && currentScale < mTargetScale)
                    || (tmpScale < 1.0f && currentScale > mTargetScale)) {
                postDelayed(this, 10);

            } else {
                float scale = mTargetScale / currentScale;
                mScaleMatrix.postScale(scale, scale, x, y);
                checkBorderAndCenterWhenScale();
                setImageMatrix(mScaleMatrix);
                isAutoScale = false;
            }

        }
    }
}
