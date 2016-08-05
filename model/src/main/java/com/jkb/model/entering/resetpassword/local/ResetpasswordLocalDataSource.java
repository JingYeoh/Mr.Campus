package com.jkb.model.entering.resetpassword.local;

import android.content.Context;
import android.util.Log;

import com.jkb.api.ApiCallback;
import com.jkb.api.ApiResponse;
import com.jkb.api.entity.auth.ResetPasswordEntity;
import com.jkb.model.entering.resetpassword.ResetPasswordDataSource;

/**
 * 重置密码的本地数据来源类
 * Created by JustKiddingBaby on 2016/8/5.
 */

public class ResetpasswordLocalDataSource implements ResetPasswordDataSource {

    private static final String TAG = "重置密码本地仓库";

    private static ResetpasswordLocalDataSource INSTANCE = null;
    private Context context;

    private ResetpasswordLocalDataSource(Context applicationContext) {
        context = applicationContext;
    }

    public static ResetpasswordLocalDataSource getInstance(
            Context applicationContext) {
        if (INSTANCE == null) {
            INSTANCE = new ResetpasswordLocalDataSource(applicationContext);
        }
        return INSTANCE;
    }

    @Override
    public void resetPasswordWithEmail(String email, String credential, String code, ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback) {

    }

    @Override
    public void resetPasswordWithPhone(String phone, String credential, String code, ApiCallback<ApiResponse<ResetPasswordEntity>> apiCallback) {

    }

    @Override
    public void saveEntityToDb(ResetPasswordEntity resetPasswordEntity) {
        Log.d(TAG, "saveEntityToDb");
    }
}
