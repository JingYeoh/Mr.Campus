package com.jkb.mrcampus.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jkb.core.contract.dynamicCreate.data.CategoryTypeData;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.adapter.baseAdapter.DialogShowTagAdapter;

import java.util.List;

import fr.tvbarthel.lib.blurdialogfragment.BlurDialogFragment;

/**
 * 展示TAG的浮动View
 * Created by JustKiddingBaby on 2016/9/27.
 */

public class TagFloatFragment extends BlurDialogFragment implements
        AdapterView.OnItemClickListener {

    private View rootView;
    private Dialog dialog;

    private ListView listView;

    //data
    private DialogShowTagAdapter dialogShowTagAdapter;
    private List<CategoryTypeData> categoryTypeDatas;
    private OnTagItemClickListener onTagItemClickListener;

    public TagFloatFragment(List<CategoryTypeData> categoryTypeDatas,
                            OnTagItemClickListener onTagItemClickListener) {
        this.categoryTypeDatas = categoryTypeDatas;
        this.onTagItemClickListener = onTagItemClickListener;
    }

    public TagFloatFragment() {
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        dialog = new Dialog(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.frg_dialog_show_tag, null);
        builder.setView(rootView);

        dialog.setCanceledOnTouchOutside(true);
        dialog.getWindow().setGravity(Gravity.CENTER);

        //初始控件
        listView = (ListView) rootView.findViewById(R.id.fdst_lv);
        listView.setOnItemClickListener(this);

        dialogShowTagAdapter = new DialogShowTagAdapter(getActivity(), categoryTypeDatas);
        listView.setAdapter(dialogShowTagAdapter);

        return builder.create();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CategoryTypeData categoryTypeData = categoryTypeDatas.get(position);
        String category = categoryTypeData.getCategory();
        if (onTagItemClickListener != null) {
            onTagItemClickListener.onTagItemClick(category);
            dismiss();
        }
    }

    /**
     * TAG条目的监听器
     */
    public interface OnTagItemClickListener {

        /**
         * 当TAG条目点击的时候
         *
         * @param tag TAG内容
         */
        void onTagItemClick(String tag);
    }
}
