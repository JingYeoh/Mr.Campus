package com.jkb.model.entering.personmessage.local;

import android.graphics.Bitmap;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.RegisterEntity;
import com.jkb.api.config.Config;
import com.jkb.model.entering.personmessage.PersonMessageDataSource;
import com.jkb.model.utils.BitmapUtils;

import okhttp3.MultipartBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 获取个人数据的本地数据获取类
 * Created by JustKiddingBaby on 2016/8/1.
 */
public class PersonMessageLocalDataSource implements PersonMessageDataSource {


    private static PersonMessageLocalDataSource INSTANCE;

    public static PersonMessageLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonMessageLocalDataSource();
        }
        return INSTANCE;
    }

    private PersonMessageLocalDataSource() {
    }

    @Override
    public void registerWithEmail(String nickName, String code, String credential, String identity_type, String identifier, MultipartBody.Part image, ApiCallback<ApiResponse<RegisterEntity>> apiCallback) {

    }

    @Override
    public void registerWithPhone(String nickName, String code, String credential, String identity_type, String identifier, MultipartBody.Part image, ApiCallback<ApiResponse<RegisterEntity>> apiCallback) {

    }

    @Override
    public String saveBitmapToFile(Bitmap bitmap, String path, String fileName) {
        final String[] url = {null};
        if (fileName == null) {
            fileName = BitmapUtils.getSystemTime();
        }
        if (path == null) {
            path = Config.PATH_ROOT_IMAGE;
        }
        final String finalPath = path;
        final String finalFileName = fileName;
        //保存图片
        Observable.just(bitmap)
                .map(new Func1<Bitmap, String>() {
                    @Override
                    public String call(Bitmap bitmap) {
                        String url = BitmapUtils.saveBitmapWithName(finalPath, finalFileName, bitmap);
                        return url;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        url[0] = s;
                    }
                });
        return url[0];
    }

    @Override
    public Bitmap getBitmapFromFile(final String urlPath) {

        if (urlPath == null) {
            return null;
        }

        final Bitmap[] mBitmap = {null};

        Observable.just(urlPath)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        Bitmap bitmap = BitmapUtils.getCompressedImage(urlPath);
                        return bitmap;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        mBitmap[0] = bitmap;
                    }
                });

        return mBitmap[0];
    }
}
