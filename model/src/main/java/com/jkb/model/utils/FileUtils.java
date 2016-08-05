package com.jkb.model.utils;

import android.util.Log;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * 文件的工具类
 * Created by JustKiddingBaby on 2016/8/2.
 */
public class FileUtils {

    private static final String TAG = "FileUtils";

    /**
     * 通过文件路径得到RequestBody对象
     *
     * @param path
     * @return
     */
    public static RequestBody getRequestBodyFromFile(String path) {
        if (path == null || path.isEmpty()) {
            return null;
        }
        RequestBody requestBody = null;
        File file = new File(path);
        if (file == null) {
            Log.e(TAG, "文件不存在！");
            return null;
        }
        requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        return requestBody;
    }

    /**
     * 转换文件为 MultipartBody.Part对象
     * @param path 文件路徑
     * @param name 文件名稱
     * @return
     */
    public static MultipartBody.Part getPartFromFile(String path, String name) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        name = (StringUtils.isEmpty(name) ? "image" : name);
        File file = new File(path);
        RequestBody photoRequestBody = getRequestBodyFromFile(path);
        MultipartBody.Part photo = MultipartBody.Part.createFormData(name, file.getName(), photoRequestBody);
        return photo;
    }
}
