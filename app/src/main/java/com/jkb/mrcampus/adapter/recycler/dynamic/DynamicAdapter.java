package com.jkb.mrcampus.adapter.recycler.dynamic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.mrcampus.R;

import java.util.Random;

/**
 * 动态的适配器
 * Created by JustKiddingBaby on 2016/8/23.
 */

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.ViewHolder> {

    private static final String TAG = "DynamicAdapter";
    private Context context;
    private int colorWhite;
    private int colorGravy;
    private Random random;

    //用到的常量
    private static final int ORIGINAL_TYPE_NORMAL = 1001;
    private static final int ORIGINAL_TYPE_TOPIC = 1002;
    private static final int ORIGINAL_TYPE_ARTICLE = 1003;
    private static final int UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE = 2001;


    public DynamicAdapter(Context context) {
        this.context = context;

        colorWhite = context.getResources().getColor(R.color.white);
        colorGravy = context.getResources().getColor(R.color.background_general);

        random = new Random();
    }

    @Override
    public int getItemViewType(int position) {
        int type = random.nextInt(4);
        if (type == 0) {
            return ORIGINAL_TYPE_TOPIC;
        } else if (type == 1) {
            return ORIGINAL_TYPE_ARTICLE;
        } else if (type == 2) {
            return ORIGINAL_TYPE_NORMAL;
        } else {
            return UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        LayoutInflater inflater = LayoutInflater.from(context);
        DynamicAdapter.ViewHolder holder = null;
        switch (viewType) {
            case ORIGINAL_TYPE_NORMAL:
                view = inflater.inflate(R.layout.item_dynamic_orginal_normal,
                        parent, false);
                holder = new DynamicAdapter.ViewHolder(view);
                initOriginalNormal(view, holder);//初始化原创——文章组件
                break;
            case ORIGINAL_TYPE_ARTICLE:
                view = inflater.inflate(R.layout.item_dynamic_orginal_article,
                        parent, false);
                holder = new DynamicAdapter.ViewHolder(view);
                initOriginalArticle(view, holder);//初始化原创——文章组件
                break;
            case ORIGINAL_TYPE_TOPIC:
                view = inflater.inflate(R.layout.item_dynamic_original_topic,
                        parent, false);
                holder = new DynamicAdapter.ViewHolder(view);
                initOriginalTopic(view, holder);//初始化原创——文章组件
                break;
            case UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE:
                //随机方向
                int randomInt = random.nextInt(2);
                if (randomInt % 2 == 0) {
                    view = inflater.inflate(R.layout.item_dynamic_subscribe_circle_left,
                            parent, false);
                } else {
                    view = inflater.inflate(R.layout.item_dynamic_subscribe_circle_right,
                            parent, false);
                }
                holder = new DynamicAdapter.ViewHolder(view);
                initUnOriginalSubscribeCircle(view, holder);//初始化非原创——订阅圈子的相关组件
                break;
        }
        //初始化id
        holder.contentView = view.findViewById(R.id.dynamic_content);
        //初始化其他
        return holder;
    }

    /**
     * 初始化非原创——订阅圈子的相关组件
     */
    private void initUnOriginalSubscribeCircle(View view, ViewHolder holder) {
        if (holder.unOriginalSubscribeCircle == null) {
            holder.unOriginalSubscribeCircle = new UnOriginalSubscribeCircle();
        }
        //圈子相关
        holder.unOriginalSubscribeCircle.ivCirclePicBg =
                (ImageView) view.findViewById(R.id.idsc_iv_picBg);
        holder.unOriginalSubscribeCircle.ivCirclePicture =
                (ImageView) view.findViewById(R.id.idsc_iv_picture);
        holder.unOriginalSubscribeCircle.tvCircleName =
                (TextView) view.findViewById(R.id.idsc_tv_name);
        holder.unOriginalSubscribeCircle.tvCircleContent =
                (TextView) view.findViewById(R.id.idsc_tv_content);
        holder.unOriginalSubscribeCircle.tvCircleType =
                (TextView) view.findViewById(R.id.idsc_tv_circleType);
        holder.unOriginalSubscribeCircle.tvCircleOperationNum =
                (TextView) view.findViewById(R.id.idsc_tv_operationCount);
        holder.unOriginalSubscribeCircle.tvCircleSubscribeNum =
                (TextView) view.findViewById(R.id.idsc_tv_dynamicsCount);
        //作者相关
        holder.unOriginalSubscribeCircle.ivHeadImg = (ImageView) view.findViewById(R.id.ius_iv_headImg);
        holder.unOriginalSubscribeCircle.tvName = (TextView) view.findViewById(R.id.ius_tv_name);
        holder.unOriginalSubscribeCircle.tvTime = (TextView) view.findViewById(R.id.ius_tv_time);
        holder.unOriginalSubscribeCircle.tvAction = (TextView) view.findViewById(R.id.ius_tv_action);
    }

    /**
     * 初始化原创——文章的相关组件
     */
    private void initOriginalArticle(View view, ViewHolder holder) {
        if (holder.originalArticle == null) {
            holder.originalArticle = new OriginalArticle();
        }
        holder.viewType = ORIGINAL_TYPE_ARTICLE;
        holder.originalArticle.iv_headImg = (ImageView) view.findViewById(R.id.idoa_iv_headImg);
        holder.originalArticle.iv_picture = (ImageView) view.findViewById(R.id.idoa_iv_pic);
        holder.originalArticle.tvName = (TextView) view.findViewById(R.id.idoa_tv_name);
        holder.originalArticle.tvTime = (TextView) view.findViewById(R.id.idoa_tv_time);
        holder.originalArticle.tvTitle = (TextView) view.findViewById(R.id.idoa_tv_title);
        holder.originalArticle.tvContent = (TextView) view.findViewById(R.id.idoa_tv_content);
        holder.originalArticle.tvLikeNum = (TextView) view.findViewById(R.id.idoa_tv_likeNum);
        holder.originalArticle.tvCommentNum = (TextView) view.findViewById(R.id.idoa_tv_commentNum);
    }

    /**
     * 初始化原创——话题的相关组件
     */
    private void initOriginalTopic(View view, ViewHolder holder) {
        if (holder.originalTopic == null) {
            holder.originalTopic = new OriginalTopic();
        }
        holder.viewType = ORIGINAL_TYPE_TOPIC;
        holder.originalTopic.iv_headImg = (ImageView) view.findViewById(R.id.idot_iv_headImg);
        holder.originalTopic.iv_picture = (ImageView) view.findViewById(R.id.idot_iv_pic);
        holder.originalTopic.tvName = (TextView) view.findViewById(R.id.idot_tv_name);
        holder.originalTopic.tvTime = (TextView) view.findViewById(R.id.idot_tv_time);
        holder.originalTopic.tvTitle = (TextView) view.findViewById(R.id.idot_tv_title);
        holder.originalTopic.tvContent = (TextView) view.findViewById(R.id.idot_tv_content);
        holder.originalTopic.tvPartNum = (TextView) view.findViewById(R.id.idot_tv_partInNum);
        holder.originalTopic.tvLikeNum = (TextView) view.findViewById(R.id.idot_tv_likeNum);
    }

    /**
     * 初始化原创——普通的相关组件
     */
    private void initOriginalNormal(View view, ViewHolder holder) {
        if (holder.originalNormal == null) {
            holder.originalNormal = new OriginalNormal();
        }
        holder.viewType = ORIGINAL_TYPE_NORMAL;
        //初始化图片相关
        holder.originalNormal.pictures = view.findViewById(R.id.idon_ll_pictures);
        holder.originalNormal.pic1To3 = view.findViewById(R.id.idon_ll_pic1To3);
        holder.originalNormal.pic2To3 = view.findViewById(R.id.idon_ll_pic2To3);
        holder.originalNormal.pic4To6 = view.findViewById(R.id.idon_ll_pic4To6);
        holder.originalNormal.ivPic1 = (ImageView) view.findViewById(R.id.idon_iv_pic1);
        holder.originalNormal.ivPic2 = (ImageView) view.findViewById(R.id.idon_iv_pic2);
        holder.originalNormal.ivPic3 = (ImageView) view.findViewById(R.id.idon_iv_pic3);
        holder.originalNormal.ivPic4 = (ImageView) view.findViewById(R.id.idon_iv_pic4);
        holder.originalNormal.ivPic5 = (ImageView) view.findViewById(R.id.idon_iv_pic5);
        holder.originalNormal.ivPic6 = (ImageView) view.findViewById(R.id.idon_iv_pic6);
        //初始化其它组件
        holder.originalNormal.tvName = (TextView) view.findViewById(R.id.idon_tv_name);
        holder.originalNormal.tvTime = (TextView) view.findViewById(R.id.idon_tv_time);
        holder.originalNormal.ivHeadImg = (ImageView) view.findViewById(R.id.idon_iv_headImg);
        holder.originalNormal.tvContent = (TextView) view.findViewById(R.id.idon_tv_content);
        holder.originalNormal.tvCommentNum = (TextView) view.findViewById(R.id.idon_tv_commentNum);
        holder.originalNormal.tvLikeNum = (TextView) view.findViewById(R.id.idon_tv_likeNum);

        //设置监听事件

    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //绑定数据
        if (position % 2 == 0) {//偶数
            holder.contentView.setBackgroundColor(colorGravy);
        } else {//奇数
            holder.contentView.setBackgroundColor(colorWhite);
        }
        //判断类型
        switch (holder.viewType) {
            case ORIGINAL_TYPE_NORMAL:
                //初始化图片
                int picNum = random.nextInt(7);
                initOriginalPicturesView(picNum, holder);
                break;
            case ORIGINAL_TYPE_ARTICLE:
                break;
            case ORIGINAL_TYPE_TOPIC:
                break;
            case UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE:
                break;
        }
    }

    /**
     * 初始化图片控件
     *
     * @param picNum 图片数量
     * @param holder holder
     */
    private void initOriginalPicturesView(int picNum, ViewHolder holder) {
        Log.d(TAG, "picNum=" + picNum);
        holder.originalNormal.pictures.setVisibility(View.GONE);
        holder.originalNormal.pic1To3.setVisibility(View.GONE);
        holder.originalNormal.pic2To3.setVisibility(View.GONE);
        holder.originalNormal.pic4To6.setVisibility(View.GONE);
        //如果没图片不执行下面动作
        if (picNum <= 0) {
            return;
        }
        //有图片时肯定至少一张
        holder.originalNormal.pictures.setVisibility(View.VISIBLE);
        holder.originalNormal.pic1To3.setVisibility(View.VISIBLE);
        if (picNum >= 1) {
            holder.originalNormal.ivPic1.setVisibility(View.VISIBLE);
        }
        if (picNum >= 2) {
            holder.originalNormal.pic2To3.setVisibility(View.VISIBLE);
            holder.originalNormal.ivPic2.setVisibility(View.VISIBLE);
            holder.originalNormal.ivPic3.setVisibility(View.INVISIBLE);
        }
        if (picNum >= 3) {
            holder.originalNormal.ivPic3.setVisibility(View.VISIBLE);
        }
        if (picNum >= 4) {
            holder.originalNormal.pic4To6.setVisibility(View.VISIBLE);
            holder.originalNormal.ivPic4.setVisibility(View.VISIBLE);
            holder.originalNormal.ivPic5.setVisibility(View.INVISIBLE);
            holder.originalNormal.ivPic6.setVisibility(View.INVISIBLE);
        }
        if (picNum >= 5) {
            holder.originalNormal.ivPic5.setVisibility(View.VISIBLE);
        }
        if (picNum >= 6) {
            holder.originalNormal.ivPic6.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    /**
     * ViewHolder类
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(View itemView) {
            super(itemView);
        }

        View contentView;
        int viewType;
        //原创
        OriginalNormal originalNormal;//原创：普通
        OriginalTopic originalTopic;//原创：话题
        OriginalArticle originalArticle;//原创：文章
        //转载
        UnOriginalSubscribeCircle unOriginalSubscribeCircle;//订阅圈子
    }

    /**
     * 原创——话题
     */
    class OriginalNormal {
        //关于图片
        View pictures;
        View pic1To3;
        View pic2To3;
        View pic4To6;
        ImageView ivPic1, ivPic2, ivPic3, ivPic4, ivPic5, ivPic6;
        //其他
        TextView tvName;
        TextView tvTime;
        ImageView ivHeadImg;//头像
        TextView tvContent;//内容
        TextView tvCommentNum;//评论人数
        TextView tvLikeNum;//喜欢的人数
    }

    /**
     * 原创——话题
     */
    class OriginalTopic {
        ImageView iv_picture;
        ImageView iv_headImg;
        TextView tvName;
        TextView tvTime;
        TextView tvPartNum;//参与人数
        TextView tvTitle;//标题
        TextView tvContent;//内容
        TextView tvLikeNum;//喜欢的人数
    }

    /**
     * 原创——文章
     */
    class OriginalArticle {
        ImageView iv_picture;
        ImageView iv_headImg;
        TextView tvName;
        TextView tvTime;
        TextView tvTitle;//标题
        TextView tvContent;//内容
        TextView tvCommentNum;//评论人数
        TextView tvLikeNum;//喜欢的人数
    }

    /**
     * 非原创：订阅圈子
     */
    class UnOriginalSubscribeCircle {
        ImageView ivCirclePicBg;
        ImageView ivCirclePicture;
        TextView tvCircleName;
        TextView tvCircleContent;
        TextView tvCircleOperationNum;//动态数
        TextView tvCircleType;
        TextView tvCircleSubscribeNum;//订阅数
        ImageView ivHeadImg;
        TextView tvName;
        TextView tvTime;
        TextView tvAction;
    }
}
