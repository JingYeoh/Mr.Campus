package com.jkb.core.contract.entering;

import com.jkb.core.base.BasePresenter;
import com.jkb.core.base.BaseView;

/**
 * 重置密码的页面协议类
 * Created by JustKiddingBaby on 2016/8/5.
 */

public interface ResetpasswordContract {

    interface View extends BaseView<Presenter> {

        /**
         * 清除输入信息
         */
        void clearInputMessage();

        /**
         * 重置密码成功
         */
        void resetPasswordSuccess();

        /**
         * 重置密码
         */
        void resetPassword();

    }

    interface Presenter extends BasePresenter {

        /**
         * 重置密码
         *
         * @param identifyCode 验证码
         * @param value        帐号
         * @param password     密码
         */
        void resetPassword(String identifyCode, String value, String password);
    }
}
