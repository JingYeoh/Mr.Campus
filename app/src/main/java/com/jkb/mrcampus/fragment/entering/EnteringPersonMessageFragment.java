package com.jkb.mrcampus.fragment.entering;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.jkb.core.contract.entering.EnterPersonMessageContract;
import com.jkb.core.presenter.entering.EnterPersonMessagePresenter;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.activity.EnteringActivity;
import com.jkb.mrcampus.base.BaseFragment;
import com.jkb.mrcampus.fragment.dialog.ChoosePictureFragment;
import com.jkb.mrcampus.helper.cropphoto.CropHandler;
import com.jkb.mrcampus.helper.cropphoto.CropHelper;
import com.jkb.mrcampus.helper.cropphoto.CropParams;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 录入个人信息的View视图
 * Created by JustKiddingBaby on 2016/7/30.
 */
public class EnteringPersonMessageFragment extends BaseFragment implements EnterPersonMessageContract.View,
        View.OnClickListener, ChoosePictureFragment.PictureChooseWayListener, CropHandler {

    private EnterPersonMessagePresenter mPresenter;
    private EnteringActivity enteringActivity;

    private CircleImageView ivHeadImg;//头像
    private EditText etIdentifyCode;
    private EditText etNickName;
    private EditText etPassWord;


    private CropParams mCropParams;


    public EnteringPersonMessageFragment() {
    }

    /**
     * 获得一个实例化的MrCampusAgreementFragment对象
     *
     * @return
     */
    public static EnteringPersonMessageFragment newInstance() {
        return new EnteringPersonMessageFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRootView(R.layout.frg_entering_personalmessage);
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
        ivHeadImg.setOnClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {
        ((TextView) rootView.findViewById(R.id.ts1_tv_name)).setText(R.string.register);
        ivHeadImg = (CircleImageView) rootView.findViewById(R.id.fep_bt_headImg);
        etIdentifyCode = (EditText) rootView.findViewById(R.id.fep_et_identifyCode);
        etNickName = (EditText) rootView.findViewById(R.id.fep_et_nickName);
        etPassWord = (EditText) rootView.findViewById(R.id.fep_et_passWord);
    }


    @Override
    public void setHeadImg(Bitmap bitmap) {
        if (bitmap != null)
            ivHeadImg.setImageBitmap(bitmap);
    }

    @Override
    public void showChoosePictureView() {
        mCropParams = new CropParams(mActivity);
        //显示选择图片的View
        enteringActivity.showChoosePictureDialog();
        enteringActivity.setChoosePictureWayListener(this);
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
    public void register() {
        String code = etIdentifyCode.getText().toString();
        String userName = etNickName.getText().toString();
        String passWord = etPassWord.getText().toString();
        String identifier = enteringActivity.getIdentifier();

        mPresenter.register(code, userName, passWord, identifier);
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
    public void loginSystem() {
        //登录进入系统
        enteringActivity.loginSystem();
    }

    @Override
    public void clearUserInput() {
        etPassWord.setText("");
        etIdentifyCode.setText("");
        etNickName.setText("");
    }

    @Override
    public void showLoading(String value) {
        enteringActivity.showLoading(value);
    }

    @Override
    public void dismissLoading() {
        enteringActivity.dismiss();
    }

    @Override
    public void setPresenter(EnterPersonMessageContract.Presenter presenter) {
        mPresenter = (EnterPersonMessagePresenter) presenter;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ts1_ib_back:
                enteringActivity.backToLastView();
                break;
            case R.id.fep_bt_headImg:
                showChoosePictureView();
                break;
            case R.id.fel_bt_login:
                register();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        CropHelper.handleResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onCameraSelected() {
        choosePictureFromCamera();
    }

    @Override
    public void onAlbusSelected() {
        choosePictureFromAlbum();
    }

    @Override
    public void onPhotoCropped(Uri uri) {
        Log.d(TAG, "Crop Uri in path: " + uri.getPath());
        mPresenter.setHeadImagePath(uri.getPath());//设置头像
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
}
