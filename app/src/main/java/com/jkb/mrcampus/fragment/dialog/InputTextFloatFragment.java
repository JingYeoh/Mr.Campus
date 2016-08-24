package com.jkb.mrcampus.fragment.dialog;

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
 * 浮动的输入信息的DialogFragment
 * Created by JustKiddingBaby on 2016/8/24.
 */

public class InputTextFloatFragment extends BlurDialogFragment {

    private View rootView;
    private Dialog dialog;
    private String contentValue;

    private TextView evContent;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialog = new Dialog(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_input_text, null);
        builder.setView(rootView);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);

        return builder.create();
    }
}
