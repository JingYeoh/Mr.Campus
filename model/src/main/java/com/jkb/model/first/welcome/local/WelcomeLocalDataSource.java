package com.jkb.model.first.welcome.local;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;

import com.jkb.model.R;
import com.jkb.model.first.welcome.WelcomeData;
import com.jkb.model.first.welcome.WelcomeDataSource;

import java.io.InputStream;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Welcome页面用到的数据本地来源
 * Created by JustKiddingBaby on 2016/7/21.
 */
public class WelcomeLocalDataSource implements WelcomeDataSource {


    private static WelcomeLocalDataSource INSTANCE;

    private Context context;


    /**
     * 获取WelcomeRemoteDataSource的单例类对象
     */
    public static WelcomeLocalDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new WelcomeLocalDataSource(context);
        }
        return INSTANCE;
    }

    private WelcomeLocalDataSource(Context context) {
        this.context = checkNotNull(context);
    }

    @Override
    public void getWelcomeBackground(@NonNull final GetBitmapCallBack callBack) {
        Observable.just(getResourceId())
                .map(new Func1<Integer, Bitmap>() {
                    @Override
                    public Bitmap call(Integer integer) {
                        return getBitmapByResourceId(integer);
                    }
                })
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        packageBitmap(bitmap, callBack);
                    }
                });
    }

    /**
     * 封装并且回调
     */
    private void packageBitmap(Bitmap bitmap, GetBitmapCallBack callBack) {
        if (null == bitmap) {
            callBack.onDataNotAvailable();
        } else {
            WelcomeData image = new WelcomeData();
            image.setBitmap(bitmap);
            callBack.onBitmapLoaded(image);
        }
    }


    /**
     * 通过资源Id得到Bitmap对象
     */
    private Bitmap getBitmapByResourceId(int resourceId) {
        if (resourceId == 0) {
            return null;
        }
        Resources resources = context.getResources();
        InputStream is = resources.openRawResource(resourceId);
        checkNotNull(is, "没有该资源图片！");
        BitmapDrawable bmpDraw = new BitmapDrawable(is);
        Bitmap bitmap = bmpDraw.getBitmap();
        return bitmap;
    }

    /**
     * 得到图片资源的ID
     */
    public int getResourceId() {
        return chooseResourceId();
    }

    /**
     * 选择一个图片资源Id
     */
    private int chooseResourceId() {
        //选择过程
        return 0;
    }
}
