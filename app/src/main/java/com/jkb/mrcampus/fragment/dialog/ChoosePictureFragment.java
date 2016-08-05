package com.jkb.mrcampus.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.jkb.mrcampus.R;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * 选择图片的弹出视图
 * Created by JustKiddingBaby on 2016/8/4.
 */

public class ChoosePictureFragment extends BlurDialogFragment implements View.OnClickListener {

    private View rootView;
    private Dialog dialog;
    private PictureChooseWayListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialog = new Dialog(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_choose_picture, null);
        builder.setView(rootView);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);

        initListener();
        return builder.create();
    }

    /**
     * 初始化监听器
     */
    private void initListener() {
        rootView.findViewById(R.id.fdcp_tv_camera).setOnClickListener(this);
        rootView.findViewById(R.id.fdcp_tv_photo).setOnClickListener(this);
    }

    /**
     * 设置点击的监听器
     *
     * @param mListener
     */
    public void setPictureSelectedListener(PictureChooseWayListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fdcp_tv_camera:
                if (mListener != null) {
                    mListener.onCameraSelected();
                }
                dismiss();
                break;
            case R.id.fdcp_tv_photo:
                if (mListener != null) {
                    mListener.onAlbusSelected();
                }
                dismiss();
                break;
        }
    }

    /**
     * 选择图片的接口
     */
    public interface PictureChooseWayListener {
        /**
         * 选择照相机
         */
        void onCameraSelected();

        /**
         * 从相册选择
         */
        void onAlbusSelected();
    }
}
