package com.jkb.mrcampus.fragment.personCenter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.personCenter.PersonCenterContract;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.PersonCenterActivity;
import com.jkb.mrcampus.base.BaseFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人中心的显示层
 * Created by JustKiddingBaby on 2016/8/14.
 */

public class PersonCenterFragment extends BaseFragment implements PersonCenterContract.View, View.OnClickListener {

    private static PersonCenterFragment INSTANCE = null;

    public PersonCenterFragment() {
    }

    public static PersonCenterFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonCenterFragment();
        }
        return INSTANCE;
    }

    private PersonCenterActivity personCenterActivity;
    private PersonCenterContract.Presenter mPresenter;

    //View层数据
    private CircleImageView ivHeadImg;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setRootView(R.layout.frg_peronal_center);
        personCenterActivity = (PersonCenterActivity) mActivity;
        init(savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.ts3_ib_left).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        ivHeadImg = (CircleImageView) rootView.findViewById(R.id.fpc_iv_headImg);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ts3_ib_left:
                personCenterActivity.backToLastView();
                break;
        }
    }

    @Override
    public void setHeadImg(Bitmap headImg) {
        ivHeadImg.setImageBitmap(headImg);
    }

    @Override
    public void setUserName(String userName) {
        ((TextView) rootView.findViewById(R.id.fpc_tv_userName)).setText(userName);
    }

    @Override
    public void setName(String name) {
        ((TextView) rootView.findViewById(R.id.ts3_tv_name)).setText(name);
    }

    @Override
    public void setUserSign(String userSign) {
        ((TextView) rootView.findViewById(R.id.fpc_tv_sign)).setText(userSign);
    }

    @Override
    public void setWatchedNum(int watched) {

    }

    @Override
    public void setFansNum(int fans) {

    }

    @Override
    public void setVistiorsNum(int visitors) {

    }

    @Override
    public void setPresenter(PersonCenterContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {
        personCenterActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        personCenterActivity.dismissLoading();
    }

    @Override
    public void showReqResult(String value) {
        showShortToash(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
