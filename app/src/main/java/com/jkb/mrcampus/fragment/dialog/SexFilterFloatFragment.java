package com.jkb.mrcampus.fragment.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.jkb.mrcampus.R;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * 筛选性别的浮动fragment
 * Created by JustKiddingBaby on 2016/8/28.
 */
@SuppressLint("ValidFragment")
public class SexFilterFloatFragment extends BlurDialogFragment implements View.OnClickListener {

    private View rootView;
    private Dialog dialog;

    private ImageView ivMan;
    private ImageView ivFemale;

    private SexFilterListener sexFilterListener;

    private int sexType = SEX_TYPE_MAN;//性别

    public static final int SEX_TYPE_MAN = 1000;//男
    public static final int SEX_TYPE_FEMALE = 1001;//女


    public SexFilterFloatFragment(int sexType, SexFilterListener sexFilterListener) {
        this.sexType = sexType;
        this.sexFilterListener = sexFilterListener;
    }

    public SexFilterFloatFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialog = new Dialog(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_sexfilter, null);
        builder.setView(rootView);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);

        //初始控件
        ivMan = (ImageView) rootView.findViewById(R.id.fds_iv_man);
        ivFemale = (ImageView) rootView.findViewById(R.id.fds_iv_female);
        rootView.findViewById(R.id.fds_ll_man).setOnClickListener(this);
        rootView.findViewById(R.id.fds_ll_female).setOnClickListener(this);

        //设置类型
        setSexData();

        return builder.create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fds_ll_man:
                sexType = SEX_TYPE_MAN;
                break;
            case R.id.fds_ll_female:
                sexType = SEX_TYPE_FEMALE;
                break;
        }
        sexFilterCallback();
    }

    /**
     * 筛选性别回调方法
     */
    private void sexFilterCallback() {
        setSexData();
        if (sexFilterListener == null) {
            return;
        }
        switch (sexType) {
            case SEX_TYPE_MAN:
                sexFilterListener.onManSelected();
                break;
            case SEX_TYPE_FEMALE:
                sexFilterListener.onFemaleSelected();
                break;
        }
        dismiss();
    }

    /**
     * 设置性别数据
     */
    private void setSexData() {
        switch (sexType) {
            case SEX_TYPE_MAN:
                ivMan.setVisibility(View.VISIBLE);
                ivFemale.setVisibility(View.GONE);
                break;
            case SEX_TYPE_FEMALE:
                ivMan.setVisibility(View.GONE);
                ivFemale.setVisibility(View.VISIBLE);
                break;
            default:
                ivMan.setVisibility(View.GONE);
                ivFemale.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * 性别筛选的回调方法
     */
    public interface SexFilterListener {
        /**
         * 选择男
         */
        void onManSelected();

        /**
         * 选择女
         */
        void onFemaleSelected();
    }
}
