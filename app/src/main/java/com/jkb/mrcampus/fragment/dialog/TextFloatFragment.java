package com.jkb.mrcampus.fragment.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jkb.mrcampus.R;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * 悬浮的文字显示的弹出视图
 * Created by JustKiddingBaby on 2016/8/16.
 */
@SuppressLint("ValidFragment")
public class TextFloatFragment extends BlurDialogFragment {

    private View rootView;
    private Dialog dialog;
    private String contentValue;

    private TextView evContent;


    public TextFloatFragment(String contentValue) {
        super();
        this.contentValue = contentValue;
    }

    public TextFloatFragment() {
        super();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialog = new Dialog(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_textfloat, null);
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
        evContent = (TextView) rootView.findViewById(R.id.fdtf_tv_content);
        evContent.setText(contentValue);
    }
}