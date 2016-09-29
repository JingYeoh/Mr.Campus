package com.jkb.model.dataSource.school.remote;

import android.support.annotation.NonNull;

import com.google.gson.reflect.TypeToken;
import com.jkb.api.ApiCallback;
import com.jkb.api.ApiEngine;
import com.jkb.api.ApiFactoryImpl;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.school.SchoolListEntity;
import com.jkb.api.net.school.SchoolApi;
import com.jkb.model.dataSource.school.SelectSchoolDataSource;

import java.lang.reflect.Type;

import jkb.mrcampus.db.entity.Schools;
import jkb.mrcampus.db.entity.Status;
import retrofit2.Call;

/**
 * 选择学校的远程数据来源类
 * Created by JustKiddingBaby on 2016/9/29.
 */

public class SelectSchoolRemoteDataSource implements SelectSchoolDataSource {

    private SelectSchoolRemoteDataSource() {
    }

    private static SelectSchoolRemoteDataSource INSTANCE = null;

    public static SelectSchoolRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SelectSchoolRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getAllSchool(@NonNull int page, ApiCallback<ApiResponse<SchoolListEntity>> apiCallback) {
        //获取网络数据
        ApiFactoryImpl factory = ApiFactoryImpl.newInstance();
        factory.setHttpClient(factory.genericClient());
        factory.initRetrofit();
        SchoolApi schoolApi = factory.createApi(SchoolApi.class);
        Call<ApiResponse<SchoolListEntity>> call;
        call = schoolApi.getAllSchool(page);
        Type type = new TypeToken<ApiResponse<SchoolListEntity>>() {
        }.getType();
        new ApiEngine<ApiResponse<SchoolListEntity>>(apiCallback, call, type);
    }

    @Override
    public void saveSchoolToDb(Schools schools) {

    }

    @Override
    public void saveStatusToDb(Status status) {

    }

    @Override
    public String getCurrentVersion() {
        return null;
    }
}
