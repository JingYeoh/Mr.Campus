package com.jkb.mrcampus.fragment.entering;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jkb.core.contract.entering.LoginContract;
import com.jkb.core.presenter.entering.LoginPresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.EnteringActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.utils.ClassUtils;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 登录页面View视图
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class LoginFragment extends BaseFragment implements LoginContract.View, View.OnClickListener {

    private LoginPresenter mPresenter;

    private EnteringActivity enteringActivity;


    public LoginFragment() {
    }

    /**
     * 获得一个实例化的HomePageFragment对象
     *
     * @return
     */
    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_entering_login);
        //初始化页面控制器
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
        rootView.findViewById(R.id.fel_bt_login).setOnClickListener(this);
        rootView.findViewById(R.id.fel_bt_register).setOnClickListener(this);
        rootView.findViewById(R.id.fel_loginByQQ).setOnClickListener(this);
        rootView.findViewById(R.id.fel_loginByWeChat).setOnClickListener(this);
        rootView.findViewById(R.id.fel_loginByWeiBo).setOnClickListener(this);
        rootView.findViewById(R.id.fel_loginByRenRen).setOnClickListener(this);
        rootView.findViewById(R.id.fel_loginByDouBan).setOnClickListener(this);
        rootView.findViewById(R.id.fel_tv_resetPassword).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts1_ib_back:
                enteringActivity.backToLastView();
                break;
            case R.id.fel_bt_login:
                loginByInput();
                break;
            case R.id.fel_bt_register:
                showRegister();
                break;
            case R.id.fel_loginByQQ:
                loginByQQ();
                break;
            case R.id.fel_loginByWeChat:
                loginByWeChat();
                break;
            case R.id.fel_loginByWeiBo:
                loginByWeiBo();
                break;
            case R.id.fel_loginByDouBan:
                loginByDouBan();
                break;
            case R.id.fel_loginByRenRen:
                loginByRenRen();
                break;
            case R.id.fel_tv_resetPassword:
                showResetPassWordView();
                break;
        }
    }

    @Override
    public void setUserName(String userName) {
        ((EditText) rootView.findViewById(R.id.fel_et_userName)).setText(userName);
    }

    @Override
    public void setPassWord(String passWord) {
        ((EditText) rootView.findViewById(R.id.fel_et_passWord)).setText(passWord);
    }

    @Override
    public void showLoginResult(String result) {
        showShortToash(result);
    }

    @Override
    public void loginByQQ() {
        mPresenter.loginByQQ();
    }

    @Override
    public void loginByWeChat() {
        mPresenter.loginByWeChat();
    }

    @Override
    public void loginByWeiBo() {
        mPresenter.loginByWeiBo();
    }

    @Override
    public void loginByRenRen() {
        mPresenter.loginByRenRen();
    }

    @Override
    public void loginByDouBan() {
        mPresenter.loginByDouBan();
    }

    @Override
    public void loginByInput() {
        EditText etUserName = ((EditText) rootView.findViewById(R.id.fel_et_userName));
        EditText etPassWord = ((EditText) rootView.findViewById(R.id.fel_et_passWord));
        String userName = etUserName.getText().toString();
        String passWord = etPassWord.getText().toString();
        //请求登录
        mPresenter.loginByInput(userName, passWord);
    }

    @Override
    public void showRegister() {
        enteringActivity.showRegisterView();
    }

    @Override
    public void showMrCampusAgreement() {
        enteringActivity.showFragment(
                ClassUtils.getClassName(MrCampusAgreementFragment.class));
    }

    @Override
    public void showResetPassWordView() {
        enteringActivity.showResetPasswordView();
//        showShare();
    }

    @Override
    public void loginSuccess() {
        //登录成功
        enteringActivity.loginSystem();
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
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = (LoginPresenter) presenter;
    }

    //用于测试的方法
    private void showShare() {
        ShareSDK.initSDK(mActivity);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

        // 启动分享GUI
        oks.show(mActivity);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.start();//设置加载
        }
    }
}
