package com.jkb.mrcampus.fragment.entering;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jkb.core.contract.entering.ResetpasswordContract;
import com.jkb.core.presenter.entering.ResetpasswordPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.EnteringActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 重置密码View视图
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class ResetPasswordFragment extends BaseFragment implements
        ResetpasswordContract.View,
        View.OnClickListener {

    private EnteringActivity enteringActivity;

    private ResetpasswordContract.Presenter mPresenter;

    private EditText etIdentifyCode;
    private EditText etPassword;

    public ResetPasswordFragment() {
    }

    /**
     * 获得一个实例化的ResetPasswordFragment对象
     */
    public static ResetPasswordFragment newInstance() {
        return new ResetPasswordFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_entering_resetpassword);
        enteringActivity = (EnteringActivity) mActivity;
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ts1_ib_back).setOnClickListener(this);
        rootView.findViewById(R.id.fer_bt_commit).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts1_tv_name)).setText(R.string.resetPassword);
        etIdentifyCode = (EditText) rootView.findViewById(R.id.fer_et_identifyCode);
        etPassword = (EditText) rootView.findViewById(R.id.fer_et_passWord);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ts1_ib_back:
                enteringActivity.backToLastView();
                break;
            case R.id.fer_bt_commit:
                resetPassword();
                break;
        }
    }

    @Override
    public void clearInputMessage() {
        etIdentifyCode.setText("");
        etPassword.setText("");
    }

    @Override
    public void resetPasswordSuccess() {
        //跳转到登录页面
        enteringActivity.showFragment(ClassUtils.getClassName(LoginFragment.class));
    }

    @Override
    public void resetPassword() {
        String code = etIdentifyCode.getText().toString();
        String password = etPassword.getText().toString();
        String value = enteringActivity.getIdentifier();
        mPresenter.resetPassword(code, value, password);
    }

    @Override
    public void setPresenter(ResetpasswordContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        enteringActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        enteringActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        showShortToash(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        enteringActivity = null;
    }
}
