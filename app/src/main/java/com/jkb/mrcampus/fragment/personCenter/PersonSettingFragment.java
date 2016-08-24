package com.jkb.mrcampus.fragment.personCenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.personCenter.PersonSettingContract;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.PersonCenterActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ChoosePictureFragment;
import com.jkb.mrcampus.helper.cropphoto.CropHandler;
import com.jkb.mrcampus.helper.cropphoto.CropHelper;
import com.jkb.mrcampus.helper.cropphoto.CropParams;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 个人设置页面View层
 * Created by JustKiddingBaby on 2016/8/23.
 */

public class PersonSettingFragment extends BaseFragment implements PersonSettingContract.View,
        View.OnClickListener, CropHandler, ChoosePictureFragment.PictureChooseWayListener {

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
    private PersonCenterActivity personCenterActivity;
    private PersonSettingContract.Presenter mPresenter;

    private CropParams mCropParams;//头像裁剪用到的
    private int photoCropType = -1;
    private static final int CROP_TYPE_HEADIMG = 0;
    private static final int CROP_TYPE_BG = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        personCenterActivity = (PersonCenterActivity) mActivity;
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
        rootView.findViewById(R.id.ts4_ib_left).setOnClickListener(this);
        //选择图片
        rootView.findViewById(R.id.fps_iv_headImg).setOnClickListener(this);
        rootView.findViewById(R.id.fps_iv_backGround).setOnClickListener(this);
        //输入信息
        rootView.findViewById(R.id.fps_ll_approve).setOnClickListener(this);
        rootView.findViewById(R.id.fps_ll_bref).setOnClickListener(this);
        rootView.findViewById(R.id.fps_ll_email).setOnClickListener(this);
        rootView.findViewById(R.id.fps_ll_name).setOnClickListener(this);
        rootView.findViewById(R.id.fps_ll_nickName).setOnClickListener(this);
        rootView.findViewById(R.id.fps_ll_phone).setOnClickListener(this);
        rootView.findViewById(R.id.fps_ll_sex).setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mCropParams = new CropParams(mActivity);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ts4_ib_left://返回按钮
                personCenterActivity.backToLastView();
                break;
            case R.id.fps_iv_headImg://头像
                onHeadImgClick();
                break;
            case R.id.fps_iv_backGround://背景
                onBackGroundClick();
                break;
            case R.id.fps_ll_nickName://昵称
                showInputTextView();
                break;
            case R.id.fps_ll_approve://认证
                break;
            case R.id.fps_ll_sex://性别
                break;
            case R.id.fps_ll_name://名称
                break;
            case R.id.fps_ll_bref://简介
                break;
            case R.id.fps_ll_phone://手机
                break;
            case R.id.fps_ll_email://邮箱
                break;
        }
    }

    @Override
    public void setHeadImg(Bitmap bitmap) {
        ((CircleImageView) rootView.findViewById(R.id.fps_iv_headImg)).setImageBitmap(bitmap);
    }

    @Override
    public void setName(String name) {
        ((TextView) rootView.findViewById(R.id.fps_tv_name)).setText(name);
        ((TextView) rootView.findViewById(R.id.ts4_tv_name)).setText(name);
    }

    @Override
    public void setSex(String sex) {
        ((TextView) rootView.findViewById(R.id.fps_tv_sex)).setText(sex);
    }

    @Override
    public void setNickName(String nickName) {
        ((TextView) rootView.findViewById(R.id.fps_tv_nickName)).setText(nickName);
    }

    @Override
    public void setBref_introduction(String bref_introduction) {
        ((TextView) rootView.findViewById(R.id.fps_tv_bref_introduction)).setText(bref_introduction);
    }

    @Override
    public void setBackGround(Bitmap bitmap) {
        ((ImageView) rootView.findViewById(R.id.fps_iv_backGround)).setImageBitmap(bitmap);
    }

    @Override
    public void setPhone(String phone) {
        ((TextView) rootView.findViewById(R.id.fps_tv_phone)).setText(phone);
    }

    @Override
    public void setEmail(String email) {
        ((TextView) rootView.findViewById(R.id.fps_tv_email)).setText(email);
    }

    @Override
    public void setID(String ID) {
        ((TextView) rootView.findViewById(R.id.fps_tv_ID)).setText(ID);
    }

    @Override
    public void setRegisterTime(String registerTime) {
        ((TextView) rootView.findViewById(R.id.fps_tv_registerTime)).setText(registerTime);
    }

    @Override
    public void onHeadImgClick() {
        photoCropType = CROP_TYPE_HEADIMG;
        showChoosePictureView();
    }

    @Override
    public void onBackGroundClick() {
        photoCropType = CROP_TYPE_BG;
        showChoosePictureView();
    }

    @Override
    public void showChoosePictureView() {
        personCenterActivity.showChoosePictureDialog();
        personCenterActivity.setChoosePictureWayListener(this);
    }

    @Override
    public void choosePictureFromCamera() {
        mCropParams.enable = true;
        mCropParams.compress = false;
        Intent intent = CropHelper
                .buildCameraIntent(mCropParams);
        startActivityForResult(intent,
                CropHelper.REQUEST_CAMERA);
    }

    @Override
    public void choosePictureFromAlbum() {
        mCropParams.enable = true;
        mCropParams.compress = false;
        Intent intent = CropHelper
                .buildGalleryIntent(mCropParams);
        startActivityForResult(intent, CropHelper.REQUEST_CROP);
    }

    @Override
    public void showInputTextView() {
        personCenterActivity.showInputTextFloatView("");
    }

    @Override
    public void notifyDataChanged() {
        Log.d(TAG, "notifyDataChanged");
        personCenterActivity.notifyDataChanged();//设置数据过期通知
    }

    @Override
    public void setPresenter(PersonSettingContract.Presenter presenter) {
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
        personCenterActivity.showShortToast(value);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    ///////////////////////////////头像裁剪处理回调区////////////////////////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        Log.d(TAG, "Crop Uri in path: " + uri.getPath());
        String path = uri.getPath();
        switch (photoCropType) {
            case CROP_TYPE_BG://背景
                mPresenter.changeBackGround(path);
                break;
            case CROP_TYPE_HEADIMG://头像
                mPresenter.changeHeadImg(path);
                break;
        }
    }

    @Override
    public void onCompressed(Uri uri) {
        Log.d(TAG, "onCompressed: " + uri.getPath());
    }

    @Override
    public void onCancel() {
        //裁剪取消
    }

    @Override
    public void onFailed(String message) {
        showShortToash("裁剪失败");
    }

    @Override
    public void handleIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
    }

    @Override
    public CropParams getCropParams() {
        return mCropParams;
    }

    @Override
    public void onCameraSelected() {
        choosePictureFromCamera();
    }

    @Override
    public void onAlbusSelected() {
        choosePictureFromAlbum();
    }
    ///////////////////////////////头像裁剪处理回调区////////////////////////////
}
