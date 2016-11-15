package com.jkb.mrcampus.fragment.entering;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jkb.core.contract.entering.IdentifyContract;
import com.jkb.core.presenter.entering.IdentifyPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.EnteringActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 获取验证码页面View视图
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class IdentifyFragment extends BaseFragment implements IdentifyContract.View, View.OnClickListener {

    private IdentifyPresenter mPresenter;
    private EnteringActivity enteringActivity;

    private TextView tvCount;
    private View countView;

    //发送验证码的逻辑数据
    private static final int TIME_COUNT = 60;
    private static final int WHAT_TIME_COUNT = 1001;
    private int countTime = 0;

    public IdentifyFragment() {
    }

    /**
     * 获得一个实例化的HomePageFragment对象
     */
    public static IdentifyFragment newInstance() {
        return new IdentifyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_entering_identifycode);
        enteringActivity = (EnteringActivity) mActivity;
        super.onCreateView(inflater, container, savedInstanceState);
        init(savedInstanceState);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.fei_bt_send).setOnClickListener(this);
        rootView.findViewById(R.id.fei_bt_MrcampusAgreement).setOnClickListener(this);
        rootView.findViewById(R.id.ts1_ib_back).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        tvCount = (TextView) rootView.findViewById(R.id.fei_tv_sendCodeCount);
        countView = rootView.findViewById(R.id.fei_ll_countCode);
        ((TextView) rootView.findViewById(R.id.ts1_tv_name)).setText(R.string.getIdentifyCode);
    }

    @Override
    public void showLoading(String value) {
        enteringActivity.showLoading(value, this);
    }

    @Override
    public void dismissLoading() {
        Log.d(TAG, "dismissLoading");
        enteringActivity.dismissLoading();
    }

    @Override
    public void sendCode() {
        if (countTime == 0) {
            String value = ((EditText) rootView.findViewById(R.id.fei_et_input)).getText().toString();
            mPresenter.sendIdentifyCode(value);
        } else {
            notAllowToSendCode();
        }
    }

    @Override
    public void sendCodeSuccess() {
        countView.setVisibility(View.VISIBLE);
        countTime = TIME_COUNT;
        handler.sendEmptyMessageDelayed(WHAT_TIME_COUNT, 1000);

        //设置发送的帐号到控制器
        enteringActivity.setIdentifier(
                ((EditText) rootView.findViewById(R.id.fei_et_input)).getText().toString());
    }

    @Override
    public void notAllowToSendCode() {
        showShortToash("宝宝别急，待会再试");
    }

    @Override
    public void showNextView() {
//        enteringActivity.showFragment(ClassUtils.getClassName(EnteringPersonMessageFragment.class));
        enteringActivity.showIdentifyNextView();
    }

    @Override
    public void showMrCampusAgreement() {
        enteringActivity.showFragment(ClassUtils.getClassName(MrCampusAgreementFragment.class));
    }

    @Override
    public void showReqResult(String msg) {
        showShortToash(msg);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        enteringActivity = null;
        tvCount = null;
        countView = null;
    }

    @Override
    public void setPresenter(IdentifyContract.Presenter presenter) {
        mPresenter = (IdentifyPresenter) presenter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fei_bt_send:
                sendCode();
                break;
            case R.id.fei_bt_MrcampusAgreement:
                showMrCampusAgreement();
                break;
            case R.id.ts1_ib_back:
                enteringActivity.backToLastView();//返回上级页面
                break;
        }
    }

    /**
     * 用于倒计时的Handler
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_TIME_COUNT:
                    if (countTime > 0) {
                        countTime--;
                        handler.sendEmptyMessageDelayed(WHAT_TIME_COUNT, 1000);
                        tvCount.setText("" + countTime);
                    } else {
                        countView.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    };
}
