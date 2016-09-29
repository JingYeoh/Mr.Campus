package com.jkb.mrcampus.adapter.baseAdapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.core.contract.school.data.SchoolData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.Config;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 初始化选择学校的适配器
 * Created by JustKiddingBaby on 2016/9/29.
 */

public class SelectSchoolAdapter extends BaseAdapter {

    private Context context;
    public List<SchoolData> schoolDatas;
    private OnItemClickListener onItemClickListener;

    public SelectSchoolAdapter(Context context, List<SchoolData> schoolDatas) {
        this.context = context;
        if (schoolDatas == null) {
            schoolDatas = new ArrayList<>();
        }
        this.schoolDatas = schoolDatas;
    }

    @Override
    public int getCount() {
        return schoolDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return schoolDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_school,
                    parent, false);
            holder = new ViewHolder();
            initView(convertView, holder);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        bindData(holder, position);
        return convertView;
    }

    /**
     * 绑定数据
     */
    private void bindData(ViewHolder holder, int position) {
        SchoolData schoolData = schoolDatas.get(position);
        ClassUtils.bindViewsTag(position, holder.contentView);
        holder.tvSchoolName.setText(schoolData.getSchoolName());
        String schoolBadge = schoolData.getSchoolBadge();
        if (StringUtils.isEmpty(schoolBadge)) {

        } else {
            loadImg(holder.ivSchoolBadge, schoolBadge);
        }
    }

    /**
     * 加载图片
     */
    private void loadImg(ImageView ivSchoolBadge, String schoolBadge) {
        ImageLoaderFactory.getInstance().displayImage(ivSchoolBadge, schoolBadge);
    }

    /**
     * 初始化视图
     */
    private void initView(View convertView, ViewHolder holder) {
        holder.ivSchoolBadge = (ImageView) convertView.findViewById(R.id.iss_iv_schoolBadge);
        holder.tvSchoolName = (TextView) convertView.findViewById(R.id.iss_tv_schoolName);
        holder.contentView = convertView.findViewById(R.id.iss_content);
        holder.contentView.setOnClickListener(clickSchoolItem);
    }

    private class ViewHolder {
        TextView tvSchoolName;
        ImageView ivSchoolBadge;
        View contentView;
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private View.OnClickListener clickSchoolItem = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                Bundle bundle = (Bundle) v.getTag();
                if (bundle == null) {
                    return;
                }
                int viewId = bundle.getInt(Config.BUNDLE_KEY_VIEW_ID);
                int position = bundle.getInt(Config.BUNDLE_KEY_VIEW_POSITION);
                switch (viewId) {
                    case R.id.iss_content:
                        onItemClickListener.onItemCLick(position);
                        break;
                }
            }
        }
    };

    /**
     * 条目点击的监听器
     */
    public interface OnItemClickListener {
        void onItemCLick(int position);
    }
}
