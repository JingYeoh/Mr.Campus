package com.jkb.mrcampus.adapter.recycler.dynamic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.api.config.Config;
import com.jkb.core.contract.function.data.dynamic.CircleData;
import com.jkb.core.contract.function.data.dynamic.DynamicBaseData;
import com.jkb.core.contract.function.data.dynamic.DynamicData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.model.utils.StringUtils;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.BitmapUtil;

import java.util.ArrayList;
import java.util.List;
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

    public List<DynamicBaseData> dynamicBaseDatas;

    //用到的常量
    private static final int ORIGINAL_TYPE_NORMAL = 1001;
    private static final int ORIGINAL_TYPE_TOPIC = 1002;
    private static final int ORIGINAL_TYPE_ARTICLE = 1003;
    private static final int UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE = 2001;


    public DynamicAdapter(Context context, List<DynamicBaseData> dynamicBaseDatas) {
        this.context = context;
        if (dynamicBaseDatas == null) {
            dynamicBaseDatas = new ArrayList<>();
        }
        this.dynamicBaseDatas = dynamicBaseDatas;

        colorWhite = context.getResources().getColor(R.color.white);
        colorGravy = context.getResources().getColor(R.color.background_general);

        random = new Random();
    }

    @Override
    public int getItemViewType(int position) {
        DynamicBaseData dynamicBaseData = dynamicBaseDatas.get(position);
        int itemType = 0;
        String target_type = dynamicBaseData.getTarget_type();
        if (target_type.equals(Config.TARGET_TYPE_CIRCLE)) {
            return UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE;
        } else if (target_type.equals(Config.TARGET_TYPE_DYNAMIC)) {
            if (dynamicBaseData instanceof DynamicData) {
                //设置动态数据
                DynamicData dynamicData = (DynamicData) dynamicBaseData;
                String dType = dynamicData.getDtype();
                if (dType.equals(Config.D_TYPE_ARTICLE)) {
                    itemType = ORIGINAL_TYPE_ARTICLE;
                } else if (dType.equals(Config.D_TYPE_TOPIC)) {
                    itemType = ORIGINAL_TYPE_TOPIC;
                } else {
                    itemType = ORIGINAL_TYPE_NORMAL;
                }
            } else {
                dynamicBaseDatas.remove(position);
            }
        }
//        int type = random.nextInt(4);
//        if (type == 0) {
//            return ORIGINAL_TYPE_TOPIC;
//        } else if (type == 1) {
//            return ORIGINAL_TYPE_ARTICLE;
//        } else if (type == 2) {
//            return ORIGINAL_TYPE_NORMAL;
//        } else {
//            return UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE;
//        }
        return itemType;
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
        holder.viewType = UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE;
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
        //设置作者信息
        handleDynamicData(holder, position);
    }

    /**
     * 处理用户数据
     */
    private void handleDynamicData(ViewHolder holder, int position) {
        DynamicBaseData dynamicBaseData = dynamicBaseDatas.get(position);
        if (dynamicBaseData == null) {
            dynamicBaseDatas.remove(position);
            return;
        }
        //设置作者信息
        switch (holder.viewType) {
            case ORIGINAL_TYPE_NORMAL:
                break;
            case ORIGINAL_TYPE_ARTICLE:
                handleDynamicArticleData(holder, position);//绑定文章数据
                break;
            case ORIGINAL_TYPE_TOPIC:
                handleDynamicTopicData(holder, position);//绑定话题数据
                break;
            case UNORIGINAL_TYPE_SUBSCRIBE_CIRCLE:
                handleCircleSubscribe(holder, position);//绑定圈子数据
                break;
        }
    }

    /**
     * 解析话题数据
     */
    private void handleDynamicTopicData(ViewHolder holder, int position) {
        Log.d(TAG, "handleDynamicTopicData");
        DynamicBaseData dynamicBaseData = dynamicBaseDatas.get(position);
        if (!(dynamicBaseData instanceof DynamicData)) {
            dynamicBaseDatas.remove(position);
            return;
        }
        //设置用户数据
        holder.originalTopic.tvName.setText(dynamicBaseData.getCreator_nickname());
        holder.originalTopic.tvTime.setText(dynamicBaseData.getCreated_at());
        //设置头像
        String headImgUrl = dynamicBaseData.getCreator_avatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.originalTopic.iv_headImg, headImgUrl);
        }
        //解析话题的数据
        holder.originalTopic.tvTitle.setText(((DynamicData) dynamicBaseData).getTitle());
        holder.originalTopic.tvPartNum.setText(((DynamicData) dynamicBaseData).getParticipation() + "");
        //设置图片和内容
        DynamicData.Topic topic = ((DynamicData) dynamicBaseData).getTopic();
        if (topic != null) {
            DynamicData.Topic.TopicBean bean = topic.getTopic();
            if (bean != null) {
                holder.originalTopic.tvContent.setText(bean.getDoc());
                bindImageViewAndUrl(holder.originalTopic.iv_picture, bean.getImg());
            }
        }
    }

    /**
     * 解析订阅圈子操作的数据
     */
    private void handleCircleSubscribe(ViewHolder holder, int position) {
        Log.d(TAG, "handleCircleSubscribe");
        DynamicBaseData dynamicBaseData = dynamicBaseDatas.get(position);
        if (!(dynamicBaseData instanceof CircleData)) {
            dynamicBaseDatas.remove(position);
            return;
        }
        //设置用户数据
        holder.unOriginalSubscribeCircle.tvName.setText(dynamicBaseData.getCreator_nickname());
        holder.unOriginalSubscribeCircle.tvTime.setText(dynamicBaseData.getCreated_at());
        holder.unOriginalSubscribeCircle.tvAction.setText(dynamicBaseData.getActionTitle());
        //设置头像
        String headImgUrl = dynamicBaseData.getCreator_avatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.unOriginalSubscribeCircle.ivHeadImg, headImgUrl);
        }
        //设置圈子数据
        CircleData circleData = (CircleData) dynamicBaseData;
        holder.unOriginalSubscribeCircle.tvCircleName.setText(circleData.getName());
        holder.unOriginalSubscribeCircle.tvCircleContent.setText(circleData.getIntroduction());
        holder.unOriginalSubscribeCircle.tvCircleOperationNum.setText(circleData.getOperation_count() + "");
        holder.unOriginalSubscribeCircle.tvCircleSubscribeNum.setText(circleData.getDynamics_count() + "");
        holder.unOriginalSubscribeCircle.tvCircleType.setText(circleData.getType());
        //设置图片
        bindImageViewAndUrl(holder.unOriginalSubscribeCircle.ivCirclePicture,
                circleData.getPicture());
        bindBlurImageViewAndUrl(holder.unOriginalSubscribeCircle.ivCirclePicBg,
                circleData.getPicture(), 25, 5);
    }

    /**
     * 解析文章数据
     */
    private void handleDynamicArticleData(ViewHolder holder, int position) {
        DynamicBaseData dynamicBaseData = dynamicBaseDatas.get(position);
        if (!(dynamicBaseData instanceof DynamicData)) {
            dynamicBaseDatas.remove(position);
            return;
        }
        //设置用户数据
        holder.originalArticle.tvName.setText(dynamicBaseData.getCreator_nickname());
        holder.originalArticle.tvTime.setText(dynamicBaseData.getCreated_at());
        //设置头像
        String headImgUrl = dynamicBaseData.getCreator_avatar();
        if (!StringUtils.isEmpty(headImgUrl)) {
            bindImageViewAndUrl(holder.originalArticle.iv_headImg, headImgUrl);
        }
        //设置文章数据
        DynamicData.Article article = ((DynamicData) dynamicBaseData).getArticle();
        if (article != null) {
            List<DynamicData.Article.ArticleBean> articleBeen = article.getArticle();
            if (articleBeen != null && articleBeen.size() > 0) {
                DynamicData.Article.ArticleBean bean = articleBeen.get(0);
                holder.originalArticle.tvContent.setText(bean.getDoc());
                String picUrl = bean.getImg();
                bindImageViewAndUrl(holder.originalArticle.iv_picture, picUrl);
            }
        }
        //设置文章标题
        holder.originalArticle.tvTitle.setText(((DynamicData) dynamicBaseData).getTitle());
        holder.originalArticle.tvCommentNum.setText(
                ((DynamicData) dynamicBaseData).getComments_count() + "");
        holder.originalArticle.tvLikeNum.setText(
                ((DynamicData) dynamicBaseData).getOperation_count() + "");
    }


    /**
     * 绑定图片到网址上
     */
    private void bindImageViewAndUrl(ImageView iv_headImg, String headImgUrl) {
        ImageLoaderFactory.getInstance().displayImage(iv_headImg, headImgUrl);
    }

    /**
     * 绑定图片到网址上并设置高斯模糊效果
     */
    private void bindBlurImageViewAndUrl(
            ImageView iv_headImg, String headImgUrl, int radius, int downSampling) {
        ImageLoaderFactory.getInstance().displayBlurImage(iv_headImg, headImgUrl, radius, downSampling);
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
        return dynamicBaseDatas.size();
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
