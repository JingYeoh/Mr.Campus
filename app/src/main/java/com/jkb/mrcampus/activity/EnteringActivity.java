package com.jkb.mrcampus.activity;

import android.os.Bundle;
import android.util.Log;

import com.jkb.core.Injection;
import com.jkb.core.presenter.entering.EnterPersonMessagePresenter;
import com.jkb.core.presenter.entering.IdentifyPresenter;
import com.jkb.core.presenter.entering.LoginPresenter;
import com.jkb.core.presenter.entering.ResetpasswordPresenter;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseActivity;
import com.jkb.mrcampus.fragment.entering.EnteringPersonMessageFragment;
import com.jkb.mrcampus.fragment.entering.IdentifyFragment;
import com.jkb.mrcampus.fragment.entering.LoginFragment;
import com.jkb.mrcampus.fragment.entering.MrCampusAgreementFragment;
import com.jkb.mrcampus.fragment.entering.ResetPasswordFragment;
import com.jkb.mrcampus.helper.ActivityUtils;
import com.jkb.mrcampus.helper.FragmentStack;
import com.jkb.mrcampus.utils.ClassUtils;

/**
 * 登录、注册、找回密码相关的逻辑控制器
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class EnteringActivity extends BaseActivity {

    //保存入栈的Fragment顺序
    private FragmentStack fragmentStack;

    //登录
    private LoginFragment loginFragment;
    private LoginPresenter loginPresenter;

    //获取验证码
    private IdentifyFragment identifyFragment;
    private IdentifyPresenter identifyPresenter;
    private String identifier;
    private String nextView = ENTERING_PSERSONMESSAGE;
    private static final String ENTERING_PSERSONMESSAGE = "enteringPersonMessageView";
    private static final String RESETPASSWORD = "resetPasswordView";

    //菌菌协议
    private MrCampusAgreementFragment agreementFragment;

    //输入个人信息
    private EnteringPersonMessageFragment personMessageFragment;
    private EnterPersonMessagePresenter personMessagePresenter;

    //重置密码
    private ResetpasswordPresenter resetpasswordPresenter;
    private ResetPasswordFragment resetPasswordFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView(R.layout.aty_entering);
        init(savedInstanceState);
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        fm = getSupportFragmentManager();
        fragmentStack = new FragmentStack();
        //第一次进入时调用显示首页视图
        if (!savedInstanceStateValued) {
            showFragment(LoginFragment.class.getName());
        } else {
            restoreFragments();
            //恢复保存的栈数据
            fragmentStack.setFragmetStackNames(
                    savedInstanceState.getStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK));
            nextView = savedInstanceState.getString("nextView");
        }
    }

    @Override
    protected void initView() {

    }

    @Override
    public void showFragment(String fragmentName) {
        Log.d(TAG, "showFragment------->" + fragmentName);
        try {
            Class<?> clzFragment = Class.forName(fragmentName);
            //初始化Fragment
            initFragmentStep1(clzFragment);
            //隐藏掉所有的视图
            ActivityUtils.hideAllFragments(fm);
            //添加到回退栈中
            fragmentStack.addFragmentToStack(fragmentName);

            if (ClassUtils.isNameEquals(fragmentName, LoginFragment.class)) {
                showLogin();
            } else if (ClassUtils.isNameEquals(fragmentName, IdentifyFragment.class)) {
                showIdentifyCode();
            } else if (ClassUtils.isNameEquals(fragmentName, EnteringPersonMessageFragment.class)) {
                showEnteringPersonMessage();
            } else if (ClassUtils.isNameEquals(fragmentName, ResetPasswordFragment.class)) {
                showResetPassword();
            } else if (ClassUtils.isNameEquals(fragmentName, MrCampusAgreementFragment.class)) {
                showMrCampusAgreement();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void restoreFragments(String fragmentTAG) {
        if (ClassUtils.isNameEquals(fragmentTAG, LoginFragment.class)) {
            loginFragment = (LoginFragment) fm.findFragmentByTag(fragmentTAG);
            loginPresenter = new LoginPresenter(loginFragment,
                    Injection.provideLoginResponsitory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, IdentifyFragment.class)) {
            identifyFragment = (IdentifyFragment) fm.findFragmentByTag(fragmentTAG);
            identifyPresenter = new IdentifyPresenter(identifyFragment,
                    Injection.provideIdentifyCodeResponsitory());
        } else if (ClassUtils.isNameEquals(fragmentTAG, MrCampusAgreementFragment.class)) {
            agreementFragment = MrCampusAgreementFragment.newInstance();
        } else if (ClassUtils.isNameEquals(fragmentTAG, EnteringPersonMessageFragment.class)) {
            personMessageFragment = (EnteringPersonMessageFragment) fm.findFragmentByTag(fragmentTAG);
            personMessagePresenter = new EnterPersonMessagePresenter(personMessageFragment,
                    Injection.providePersonMessageResponsotory(getApplicationContext()));
        } else if (ClassUtils.isNameEquals(fragmentTAG, ResetPasswordFragment.class)) {
            resetPasswordFragment = (ResetPasswordFragment) fm.findFragmentByTag(fragmentTAG);
            resetpasswordPresenter = new ResetpasswordPresenter(resetPasswordFragment,
                    Injection.provideResetPasswordResponsitory(getApplicationContext()));
        }
    }

    @Override
    protected void initFragmentStep2(Class<?> fragmentClass) {
        String fragmentTAG = fragmentClass.getName();

        if (ClassUtils.isNameEquals(fragmentTAG, LoginFragment.class)) {
            initLoginFragment();
        } else if (ClassUtils.isNameEquals(fragmentTAG, IdentifyFragment.class)) {
            initIdentifyCodeFragment();
        } else if (ClassUtils.isNameEquals(fragmentTAG, MrCampusAgreementFragment.class)) {
            initMrCampusAgreementFragment();
        } else if (ClassUtils.isNameEquals(fragmentTAG, EnteringPersonMessageFragment.class)) {
            initEnterPersonMessageFragment();
        } else if (ClassUtils.isNameEquals(fragmentTAG, ResetPasswordFragment.class)) {
            initResetPassWordFragment();
        }
    }

    /**
     * 初始化重置密码页面
     */
    private void initResetPassWordFragment() {
        Log.i(TAG, "initResetPassWordFragment");
        if (resetPasswordFragment == null) {
            resetPasswordFragment = ResetPasswordFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, resetPasswordFragment, R.id.enteringContent);
        }
        if (resetpasswordPresenter == null) {
            resetpasswordPresenter = new ResetpasswordPresenter(resetPasswordFragment,
                    Injection.provideResetPasswordResponsitory(getApplicationContext()));
        }
    }

    /**
     * 初始化录入个人信息页面
     */
    private void initEnterPersonMessageFragment() {
        Log.i(TAG, "initEnterPersonMessageFragment");
        if (personMessageFragment == null) {
            personMessageFragment = EnteringPersonMessageFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, personMessageFragment, R.id.enteringContent);
        }
        if (personMessagePresenter == null) {
            personMessagePresenter = new EnterPersonMessagePresenter(personMessageFragment,
                    Injection.providePersonMessageResponsotory(getApplicationContext()));
        }
    }

    /**
     * 初始化菌菌协议页面
     */
    private void initMrCampusAgreementFragment() {
        Log.i(TAG, "initMrCampusAgreementFragment");
        if (agreementFragment == null) {
            agreementFragment = MrCampusAgreementFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, agreementFragment, R.id.enteringContent);
        }
    }

    /**
     * 初始化发送验证码视图
     */
    private void initIdentifyCodeFragment() {
        Log.i(TAG, "initIdentifyCodeFragment");
        if (identifyFragment == null) {
            identifyFragment = IdentifyFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, identifyFragment, R.id.enteringContent);
        }
        if (identifyPresenter == null) {
            identifyPresenter = new IdentifyPresenter(identifyFragment, Injection.provideIdentifyCodeResponsitory());
        }
    }

    /**
     * 初始化登录的Fragment视图
     */
    private void initLoginFragment() {
        Log.i(TAG, "initLoginFragment");
        if (loginFragment == null) {
            loginFragment = LoginFragment.newInstance();
            ActivityUtils.addFragmentToActivity(fm, loginFragment, R.id.enteringContent);
        }
        if (loginPresenter == null) {
            loginPresenter = new LoginPresenter(loginFragment,
                    Injection.provideLoginResponsitory(getApplicationContext()));
        }
    }

    /**
     * 显示Login页面
     */
    public void showLogin() {
        Log.d(TAG, "showLogin");
//        initFragmentStep1(LoginFragment.class);
        ActivityUtils.showFragment(fm, loginFragment);
    }


    /**
     * 显示填写个人信息页面
     */
    public void showEnteringPersonMessage() {
        Log.d(TAG, "showNextView");
//        initFragmentStep1(IdentifyFragment.class);
        ActivityUtils.showFragment(fm, personMessageFragment);
    }

    /**
     * 显示获取验证码页面
     */
    public void showIdentifyCode() {
        Log.d(TAG, "showRegisterView");
//        initFragmentStep1(IdentifyFragment.class);
        ActivityUtils.showFragment(fm, identifyFragment);
    }

    /**
     * 显示菌菌协议页面
     */
    public void showMrCampusAgreement() {
        Log.d(TAG, "showMrCampusAgreement");
//        initFragmentStep1(MrCampusAgreementFragment.class);
        ActivityUtils.showFragment(fm, agreementFragment);
    }

    /**
     * 显示reSetPassWord页面
     */
    public void showResetPassword() {
        Log.d(TAG, "showResetPassword");
        ActivityUtils.showFragment(fm, resetPasswordFragment);
    }

    /**
     * 设置要发送的帐号
     *
     * @param identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
        if (personMessageFragment != null) {
            personMessageFragment.clearUserInput();
        }
    }

    /**
     * 得到验证的帐号
     *
     * @return
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * 显示发送验证码后的下一个页面
     */
    public void showIdentifyNextView() {
        if (nextView.equals(ENTERING_PSERSONMESSAGE)) {//显示的视图是录入个人信息
            showFragment(ClassUtils.getClassName(EnteringPersonMessageFragment.class));
        } else if (nextView.equals(RESETPASSWORD)) {//显示的视图是重置密码
            showFragment(ClassUtils.getClassName(ResetPasswordFragment.class));
        }
    }

    /**
     * 显示注册页面的流程
     */
    public void showRegisterView() {
        Log.d(TAG, "showRegisterView");
        nextView = ENTERING_PSERSONMESSAGE;
        showFragment(ClassUtils.getClassName(IdentifyFragment.class));
    }

    /**
     * 显示注册页面的流程
     */
    public void showResetPasswordView() {
        Log.d(TAG, "showResetPasswordView");
        nextView = RESETPASSWORD;
        showFragment(ClassUtils.getClassName(IdentifyFragment.class));
    }

    /**
     * 返回到上级页面
     */
    public void backToLastView() {
        //如果当前页面就是登录页面的时候
        if (ClassUtils.isNameEquals(fragmentStack.getCurrentFragmentName(), LoginFragment.class)) {
            super.onBackPressed();
            return;
        }
        //先销毁使用过后的Fragment
        removeCurrentFragment();

        String fragmentName = fragmentStack.getCurrentFragmentName();
        //如果到最顶层栈或者到了登录页面的时候，再次回退就直接销毁当前页面
        if (fragmentName == null) {
            //返回上级页面
            super.onBackPressed();
        } else {
            //先回退一级栈保存的类信息，避免重复添加
            fragmentStack.removePopFragmentStack();
            //根据类名显示Fragment视图
            showFragment(fragmentName);
        }
    }

    /**
     * 销毁使用过后的Fragment
     */
    private void removeCurrentFragment() {
//        String fragmentName = fragmentStack.getCurrentFragmentName();
//        ActivityUtils.removeFragment(fm, fragmentName);
//        ActivityUtils.popBackFragmentStack(fm);

        //销毁的Fragment出栈
        fragmentStack.removePopFragmentStack();
    }

    /**
     * 登录进入系统
     */
    public void loginSystem() {
        super.onBackPressed();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArrayList(FragmentStack.SAVED_FRAGMENT_STACK, fragmentStack.getFragmetStackNames());
        outState.putString("nextView", nextView);
    }

    @Override
    public void onBackPressed() {
        backToLastView();
    }

}
