package com.jkb.mrcampus.fragment.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jkb.mrcampus.R;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * 浮动的输入信息的DialogFragment
 * Created by JustKiddingBaby on 2016/8/24.
 */
@SuppressLint("ValidFragment")
public class InputTextFloatFragment extends BlurDialogFragment implements View.OnClickListener {

    private View rootView;
    private Dialog dialog;
    private String contentValue;

    private EditText etInput;
    private Button btSubmit;
    private String inputValue = "";
    private OnSubmitClickListener onSubmitClickListener;


    public InputTextFloatFragment(OnSubmitClickListener onSubmitClickListener, String inputValue) {
        this.onSubmitClickListener = onSubmitClickListener;
        this.inputValue = inputValue;
    }

    public InputTextFloatFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialog = new Dialog(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_input_text, null);
        builder.setView(rootView);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);

        //初始控件
        etInput = (EditText) rootView.findViewById(R.id.fdit_etInput);
        btSubmit = (Button) rootView.findViewById(R.id.fdit_bt_submit);
        btSubmit.setOnClickListener(this);
        etInput.setText(inputValue);

        return builder.create();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fdit_bt_submit:
                submitBtCallback();
                break;
        }
    }

    /**
     * 回调方法
     */
    private void submitBtCallback() {
        if (onSubmitClickListener == null) {
            return;
        }
        String value = etInput.getText().toString();
        this.contentValue = value;
        onSubmitClickListener.onSure(value);
        dismiss();
    }


    /**
     * 提交按钮点击事件
     */
    public interface OnSubmitClickListener {
        /**
         * 点击确定后
         */
        void onSure(String value);
    }
}
