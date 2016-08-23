package com.jkb.mrcampus.fragment.personCenter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jkb.core.contract.personCenter.PersonSettingContract;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.base.BaseFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人设置页面View层
 * Created by JustKiddingBaby on 2016/8/23.
 */

public class PersonSettingFragment extends BaseFragment implements PersonSettingContract.View,
        View.OnClickListener {

    private static PersonSettingFragment INSTANCE = null;

    public PersonSettingFragment() {
    }

    public static PersonSettingFragment newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonSettingFragment();
        }
        return INSTANCE;
    }

    //data
    private PersonSettingContract.Presenter mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_person_setting);
        init(savedInstanceState);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mPresenter.start();
        }
    }

    @Override
    protected void initListener() {
        rootView.findViewById(R.id.fps_iv_headImg).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fps_iv_headImg://头像
                break;
        }
    }

    @Override
    public void setHeadImg(Bitmap bitmap) {

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setSex(String sex) {

    }

    @Override
    public void setNickName(String nickName) {

    }

    @Override
    public void setBref_introduction(String bref_introduction) {

    }

    @Override
    public void setBackGround(Bitmap bitmap) {

    }

    @Override
    public void setPhone(String phone) {

    }

    @Override
    public void setEmail(String email) {

    }

    @Override
    public void setID(String ID) {

    }

    @Override
    public void setRegisterTime(String registerTime) {

    }

    @Override
    public void showChoosePictureView() {

    }

    @Override
    public void choosePictureFromCamera() {

    }

    @Override
    public void choosePictureFromAlbum() {

    }

    @Override
    public void showInputTextView() {

    }

    @Override
    public void setPresenter(PersonSettingContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showLoading(String value) {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showReqResult(String value) {

    }

    @Override
    public boolean isActive() {
        return isAdded();
    }
}
