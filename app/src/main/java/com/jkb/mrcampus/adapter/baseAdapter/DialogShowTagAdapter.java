package com.jkb.mrcampus.adapter.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jkb.core.contract.dynamicCreate.data.CategoryTypeData;
import com.jkb.mrcampus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 显示TAG标签的适配器
 * Created by JustKiddingBaby on 2016/9/27.
 */

public class DialogShowTagAdapter extends BaseAdapter {

    private Context context;
    public List<CategoryTypeData> categoryTypeDatas;

    public DialogShowTagAdapter(Context context, List<CategoryTypeData> categoryTypeDatas) {
        this.context = context;
        if (categoryTypeDatas == null) {
            categoryTypeDatas = new ArrayList<>();
        }
        this.categoryTypeDatas = categoryTypeDatas;
    }

    @Override
    public int getCount() {
        return categoryTypeDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryTypeDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_dialog_show_tag,
                    parent, false);
            holder = new ViewHolder();
            initViewId(convertView, holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        initViewData(holder, position);
        return convertView;
    }

    /**
     * 初始化数据
     */
    private void initViewData(ViewHolder holder, int position) {
        CategoryTypeData categoryTypeData = categoryTypeDatas.get(position);
        String category = categoryTypeData.getCategory();
        holder.tvTag.setText(category);
    }

    /**
     * 初始化View的id等
     */
    private void initViewId(View convertView, ViewHolder holder) {
        holder.tvTag = (TextView) convertView.findViewById(R.id.idst_tv_tag);
    }

    public class ViewHolder {
        TextView tvTag;
    }
}
