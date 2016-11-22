package com.jkb.mrcampus.adapter.recycler.personCenter.original.mySubject;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jkb.api.config.Config;
import com.jkb.core.data.special.SpecialData;
import com.jkb.model.net.ImageLoaderFactory;
import com.jkb.mrcampus.R;
import com.jkb.mrcampus.utils.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的原创专题的页面适配器
 * Created by JustKiddingBaby on 2016/11/22.
 */

public class MyOriginalSubjectAdapter extends
        RecyclerView.Adapter<MyOriginalSubjectAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    public List<SpecialData> mSpecialData;

    //常量
    private static final int SUBJECY_TYPE_CONFESSION = 1001;
    private static final int SUBJECT_TYPE_TAUNTED = 1002;
    private static final int SUBJECT_TYPE_LOSTANDFOUND = 1003;
    private static final int SUBJECT_TYPE_FLEAMARKET = 1004;
    private static final int SUBJECT_TYPE_WANTED_SAVANT = 1005;
    private static final int SUBJECT_TYPE_WANTED_PARTNER = 1006;

    //监听器
    private OnOriginalSubjectItemClickListener onOriginalSubjectItemClickListener;

    public MyOriginalSubjectAdapter(Context context, List<SpecialData> specialData) {
        this.context = context;
        if (specialData == null) {
            specialData = new ArrayList<>();
        }
        this.mSpecialData = specialData;
    }

    @Override
    public int getItemViewType(int position) {
        int viewType;
        SpecialData specialData = mSpecialData.get(position);
        String dtype = specialData.getDtype();
        switch (dtype) {
            case Config.SUBJECT_TYPE_FLEAMARKET:
                viewType = SUBJECT_TYPE_FLEAMARKET;
                break;
            case Config.SUBJECT_TYPE_LOSTANDFOUND:
                viewType = SUBJECT_TYPE_LOSTANDFOUND;
                break;
            case Config.SUBJECT_TYPE_COMPLAINT:
                viewType = SUBJECT_TYPE_TAUNTED;
                break;
            case Config.SUBJECT_TYPE_PARTNER:
                viewType = SUBJECT_TYPE_WANTED_PARTNER;
                break;
            case Config.SUBJECT_TYPE_GRIND:
                viewType = SUBJECT_TYPE_WANTED_SAVANT;
                break;
            case Config.SUBJECT_TYPE_VINDICATOIN:
                viewType = SUBJECY_TYPE_CONFESSION;
                break;
            default:
                viewType = -1;
                break;
        }
        return viewType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;
        switch (viewType) {
            case SUBJECY_TYPE_CONFESSION:
                holder = initSubjectConfession(inflater, parent);
                break;
            case SUBJECT_TYPE_TAUNTED:
                holder = initSubjectTaunted(inflater, parent);
                break;
            case SUBJECT_TYPE_LOSTANDFOUND:
                holder = initSubjectLostAndFound(inflater, parent);
                break;
            case SUBJECT_TYPE_FLEAMARKET:
                holder = initSubjectFleaMarket(inflater, parent);
                break;
            case SUBJECT_TYPE_WANTED_SAVANT:
                holder = initSubjectWantedSavant(inflater, parent);
                break;
            case SUBJECT_TYPE_WANTED_PARTNER:
                holder = initSubjectWantedPartner(inflater, parent);
                break;
            default:
                holder = initSubjectBlank(inflater, parent);
                break;
        }
        holder.viewType = viewType;
        return holder;
    }

    /**
     * 初始化专题：空白
     */
    private ViewHolder initSubjectBlank(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_original_subject_blank, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.subject_content);
        holder.contentView.setVisibility(View.GONE);
        return holder;
    }

    /**
     * 初始化专题：寻水友
     */
    private ViewHolder initSubjectWantedPartner(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_original_subject_wanted_partner, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.subject_content);
        //初始化id
        if (holder.wantedPartnerView == null) {
            holder.wantedPartnerView = new WantedPartnerView();
        }
        holder.wantedPartnerView.ivBgPic = (ImageView) view.findViewById(R.id.imoswp_iv_BgPic);
        holder.wantedPartnerView.ivPic = (ImageView) view.findViewById(R.id.imoswp_iv_pic);
        holder.wantedPartnerView.ivTag = (ImageView) view.findViewById(R.id.imoswp_iv_tag);
        holder.wantedPartnerView.tvTitle = (TextView) view.findViewById(R.id.imoswp_tv_title);
        holder.wantedPartnerView.tvText = (TextView) view.findViewById(R.id.imoswp_tv_content);
        //底部功能栏
        holder.wantedPartnerView.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.wantedPartnerView.ivCommit = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.wantedPartnerView.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.wantedPartnerView.tvCommit = (TextView) view.findViewById(R.id.iidbf_tv_commentNum);
        holder.wantedPartnerView.tvHeart = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);
        holder.wantedPartnerView.tvDelete = (TextView) view.findViewById(R.id.iidbf_tv_delete);
        holder.wantedPartnerView.tvMark = (TextView) view.findViewById(R.id.iidbf_tv_mark);
        //初始化点击事件
        holder.wantedPartnerView.ivShare.setOnClickListener(this);
        holder.wantedPartnerView.ivCommit.setOnClickListener(this);
        holder.wantedPartnerView.ivHeart.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        holder.wantedPartnerView.tvMark.setOnClickListener(this);
        holder.wantedPartnerView.tvDelete.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化专题：求学霸
     */
    private ViewHolder initSubjectWantedSavant(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_original_subject_wanted_savant, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.subject_content);
        //初始化id
        if (holder.wantedSavantView == null) {
            holder.wantedSavantView = new WantedSavantView();
        }
        holder.wantedSavantView.ivBgPic = (ImageView) view.findViewById(R.id.imosws_iv_BgPic);
        holder.wantedSavantView.ivPic = (ImageView) view.findViewById(R.id.imosws_iv_pic);
        holder.wantedSavantView.ivTag = (ImageView) view.findViewById(R.id.imosws_iv_tag);
        holder.wantedSavantView.tvTitle = (TextView) view.findViewById(R.id.imosws_tv_title);
        holder.wantedSavantView.tvText = (TextView) view.findViewById(R.id.imosws_tv_content);
        //底部功能栏
        holder.wantedSavantView.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.wantedSavantView.ivCommit = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.wantedSavantView.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.wantedSavantView.tvCommit = (TextView) view.findViewById(R.id.iidbf_tv_commentNum);
        holder.wantedSavantView.tvHeart = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);
        holder.wantedSavantView.tvDelete = (TextView) view.findViewById(R.id.iidbf_tv_delete);
        holder.wantedSavantView.tvMark = (TextView) view.findViewById(R.id.iidbf_tv_mark);
        //初始化点击事件
        holder.wantedSavantView.ivShare.setOnClickListener(this);
        holder.wantedSavantView.ivCommit.setOnClickListener(this);
        holder.wantedSavantView.ivHeart.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        holder.wantedSavantView.tvMark.setOnClickListener(this);
        holder.wantedSavantView.tvDelete.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化专题：跳蚤市场
     */
    private ViewHolder initSubjectFleaMarket(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_original_subject_fleamarket, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.subject_content);
        //初始化id
        if (holder.fleaMarketView == null) {
            holder.fleaMarketView = new FleaMarketView();
        }
        holder.fleaMarketView.ivBgPic = (ImageView) view.findViewById(R.id.imosf_iv_BgPic);
        holder.fleaMarketView.ivPic = (ImageView) view.findViewById(R.id.imosf_iv_pic);
        holder.fleaMarketView.ivTag = (ImageView) view.findViewById(R.id.imosf_iv_tag);
        holder.fleaMarketView.tvTitle = (TextView) view.findViewById(R.id.imosf_tv_title);
        holder.fleaMarketView.tvText = (TextView) view.findViewById(R.id.imosf_tv_content);
        //底部功能栏
        holder.fleaMarketView.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.fleaMarketView.ivCommit = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.fleaMarketView.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.fleaMarketView.tvCommit = (TextView) view.findViewById(R.id.iidbf_tv_commentNum);
        holder.fleaMarketView.tvHeart = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);
        holder.fleaMarketView.tvDelete = (TextView) view.findViewById(R.id.iidbf_tv_delete);
        holder.fleaMarketView.tvMark = (TextView) view.findViewById(R.id.iidbf_tv_mark);
        //初始化点击事件
        holder.fleaMarketView.ivShare.setOnClickListener(this);
        holder.fleaMarketView.ivCommit.setOnClickListener(this);
        holder.fleaMarketView.ivHeart.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        holder.fleaMarketView.tvMark.setOnClickListener(this);
        holder.fleaMarketView.tvDelete.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化专题：失物招领
     */
    private ViewHolder initSubjectLostAndFound(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_original_subject_lostandfound, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.subject_content);
        //初始化id
        if (holder.lostAndFoundView == null) {
            holder.lostAndFoundView = new LostAndFoundView();
        }
        holder.lostAndFoundView.ivBgPic = (ImageView) view.findViewById(R.id.imosl_iv_BgPic);
        holder.lostAndFoundView.ivPic = (ImageView) view.findViewById(R.id.imosl_iv_pic);
        holder.lostAndFoundView.ivTag = (ImageView) view.findViewById(R.id.imosl_iv_tag);
        holder.lostAndFoundView.tvTitle = (TextView) view.findViewById(R.id.imosl_tv_title);
        holder.lostAndFoundView.tvText = (TextView) view.findViewById(R.id.imosl_tv_content);
        //底部功能栏
        holder.lostAndFoundView.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.lostAndFoundView.ivCommit = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.lostAndFoundView.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.lostAndFoundView.tvCommit = (TextView) view.findViewById(R.id.iidbf_tv_commentNum);
        holder.lostAndFoundView.tvHeart = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);
        holder.lostAndFoundView.tvDelete = (TextView) view.findViewById(R.id.iidbf_tv_delete);
        holder.lostAndFoundView.tvMark = (TextView) view.findViewById(R.id.iidbf_tv_mark);
        //初始化点击事件
        holder.lostAndFoundView.ivShare.setOnClickListener(this);
        holder.lostAndFoundView.ivCommit.setOnClickListener(this);
        holder.lostAndFoundView.ivHeart.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        holder.lostAndFoundView.tvMark.setOnClickListener(this);
        holder.lostAndFoundView.tvDelete.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化专题：吐槽墙
     */
    private ViewHolder initSubjectTaunted(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_original_subject_taunted, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.subject_content);
        //初始化id
        if (holder.tauntedView == null) {
            holder.tauntedView = new TauntedView();
        }
        holder.tauntedView.ivBgPic = (ImageView) view.findViewById(R.id.imost_iv_BgPic);
        holder.tauntedView.ivPic = (ImageView) view.findViewById(R.id.imost_iv_pic);
        holder.tauntedView.ivTag = (ImageView) view.findViewById(R.id.imost_iv_tag);
        holder.tauntedView.tvTitle = (TextView) view.findViewById(R.id.imost_tv_title);
        holder.tauntedView.tvText = (TextView) view.findViewById(R.id.imost_tv_content);
        //底部功能栏
        holder.tauntedView.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.tauntedView.ivCommit = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.tauntedView.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.tauntedView.tvCommit = (TextView) view.findViewById(R.id.iidbf_tv_commentNum);
        holder.tauntedView.tvHeart = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);
        holder.tauntedView.tvDelete = (TextView) view.findViewById(R.id.iidbf_tv_delete);
        holder.tauntedView.tvMark = (TextView) view.findViewById(R.id.iidbf_tv_mark);
        //初始化点击事件
        holder.tauntedView.ivShare.setOnClickListener(this);
        holder.tauntedView.ivCommit.setOnClickListener(this);
        holder.tauntedView.ivHeart.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        holder.tauntedView.tvMark.setOnClickListener(this);
        holder.tauntedView.tvDelete.setOnClickListener(this);
        return holder;
    }

    /**
     * 初始化专题：表白墙
     */
    private ViewHolder initSubjectConfession(LayoutInflater inflater, ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_my_original_subject_confession, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.contentView = view.findViewById(R.id.subject_content);
        //初始化id
        if (holder.confessionView == null) {
            holder.confessionView = new ConfessionView();
        }
        holder.confessionView.ivBgPic = (ImageView) view.findViewById(R.id.imosc_iv_BgPic);
        holder.confessionView.ivPic = (ImageView) view.findViewById(R.id.imosc_iv_pic);
        holder.confessionView.ivTag = (ImageView) view.findViewById(R.id.imosc_iv_tag);
        holder.confessionView.tvTitle = (TextView) view.findViewById(R.id.imosc_tv_title);
        holder.confessionView.tvText = (TextView) view.findViewById(R.id.imosc_tv_content);
        //底部功能栏
        holder.confessionView.ivShare = (ImageView) view.findViewById(R.id.iidbf_iv_share);
        holder.confessionView.ivCommit = (ImageView) view.findViewById(R.id.iidbf_iv_comment);
        holder.confessionView.ivHeart = (ImageView) view.findViewById(R.id.iidbf_iv_heart);
        holder.confessionView.tvCommit = (TextView) view.findViewById(R.id.iidbf_tv_commentNum);
        holder.confessionView.tvHeart = (TextView) view.findViewById(R.id.iidbf_tv_likeNum);
        holder.confessionView.tvDelete = (TextView) view.findViewById(R.id.iidbf_tv_delete);
        holder.confessionView.tvMark = (TextView) view.findViewById(R.id.iidbf_tv_mark);
        //初始化点击事件
        holder.confessionView.ivShare.setOnClickListener(this);
        holder.confessionView.ivCommit.setOnClickListener(this);
        holder.confessionView.ivHeart.setOnClickListener(this);
        holder.contentView.setOnClickListener(this);
        holder.confessionView.tvMark.setOnClickListener(this);
        holder.confessionView.tvDelete.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = holder.viewType;
        switch (viewType) {
            case SUBJECY_TYPE_CONFESSION:
                bindSubjectConfession(position, holder);
                break;
            case SUBJECT_TYPE_TAUNTED:
                bindSubjectTaunted(position, holder);
                break;
            case SUBJECT_TYPE_LOSTANDFOUND:
                bindSubjectLostAndFound(position, holder);
                break;
            case SUBJECT_TYPE_FLEAMARKET:
                bindSubjectFleaMarket(position, holder);
                break;
            case SUBJECT_TYPE_WANTED_SAVANT:
                bindSubjectWantedSavant(position, holder);
                break;
            case SUBJECT_TYPE_WANTED_PARTNER:
                bindSubjectWantedPartner(position, holder);
                break;
            default:
                bindSubjectBlank(position, holder);
                break;
        }
    }

    /**
     * 绑定专题数据：空白
     */
    private void bindSubjectBlank(int position, ViewHolder holder) {
    }

    /**
     * 绑定专题数据：寻水友
     */
    private void bindSubjectWantedPartner(int position, ViewHolder holder) {
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.wantedPartnerView.ivShare,
                holder.wantedPartnerView.ivCommit,
                holder.wantedPartnerView.ivHeart,
                holder.wantedPartnerView.tvMark,
                holder.wantedPartnerView.tvDelete);
        SpecialData specialData = mSpecialData.get(position);
        holder.wantedPartnerView.tvTitle.setText(specialData.getTitle());
        holder.wantedPartnerView.tvText.setText(specialData.getDoc());
        //设置图片
        List<String> img = specialData.getImg();
        if (img == null || img.size() == 0) {
            holder.wantedPartnerView.ivPic.setImageResource(R.color.default_picture);
            holder.wantedPartnerView.ivBgPic.setImageResource(R.color.default_picture);
        } else {
            String picUrl = img.get(0);
            String picBg = picUrl;
            if (img.size() > 1) {
                picBg = img.get(1);
            }
            loadImageByUrl(holder.wantedPartnerView.ivPic, picUrl);
            loadBlurImage(holder.wantedPartnerView.ivBgPic, picBg);
        }
        if (specialData.getHasFavorite()) {
            holder.wantedPartnerView.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.wantedPartnerView.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
        //其他
        holder.wantedPartnerView.tvCommit.setText(specialData.getCount_of_comment() + "");
        holder.wantedPartnerView.tvHeart.setText(specialData.getCount_of_favorite() + "");
        //是否标识
        if (specialData.getTag()) {
            holder.wantedPartnerView.ivTag.setVisibility(View.VISIBLE);
            holder.wantedPartnerView.tvMark.setText("取消标记");
        } else {
            holder.wantedPartnerView.ivTag.setVisibility(View.GONE);
            holder.wantedPartnerView.tvMark.setText("标记");
        }
        holder.wantedPartnerView.tvDelete.setVisibility(View.VISIBLE);
        holder.wantedPartnerView.tvMark.setVisibility(View.VISIBLE);
    }

    /**
     * 绑定专题数据：求学霸
     */
    private void bindSubjectWantedSavant(int position, ViewHolder holder) {
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.wantedSavantView.ivShare,
                holder.wantedSavantView.ivCommit,
                holder.wantedSavantView.ivHeart,
                holder.wantedSavantView.tvMark,
                holder.wantedSavantView.tvDelete);
        SpecialData specialData = mSpecialData.get(position);
        holder.wantedSavantView.tvTitle.setText(specialData.getTitle());
        holder.wantedSavantView.tvText.setText(specialData.getDoc());
        //设置图片
        List<String> img = specialData.getImg();
        if (img == null || img.size() == 0) {
            holder.wantedSavantView.ivPic.setImageResource(R.color.default_picture);
            holder.wantedSavantView.ivBgPic.setImageResource(R.color.default_picture);
        } else {
            String picUrl = img.get(0);
            String picBg = picUrl;
            if (img.size() > 1) {
                picBg = img.get(1);
            }
            loadImageByUrl(holder.wantedSavantView.ivPic, picUrl);
            loadBlurImage(holder.wantedSavantView.ivBgPic, picBg);
        }
        if (specialData.getHasFavorite()) {
            holder.wantedSavantView.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.wantedSavantView.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
        //其他
        holder.wantedSavantView.tvCommit.setText(specialData.getCount_of_comment() + "");
        holder.wantedSavantView.tvHeart.setText(specialData.getCount_of_favorite() + "");
        //是否标识
        if (specialData.getTag()) {
            holder.wantedSavantView.ivTag.setVisibility(View.VISIBLE);
            holder.wantedSavantView.tvMark.setText("取消标记");
        } else {
            holder.wantedSavantView.ivTag.setVisibility(View.GONE);
            holder.wantedSavantView.tvMark.setText("标记");
        }
        holder.wantedSavantView.tvDelete.setVisibility(View.VISIBLE);
        holder.wantedSavantView.tvMark.setVisibility(View.VISIBLE);
    }

    /**
     * 绑定专题数据：跳蚤市场
     */
    private void bindSubjectFleaMarket(int position, ViewHolder holder) {
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.fleaMarketView.ivShare,
                holder.fleaMarketView.ivCommit,
                holder.fleaMarketView.ivHeart,
                holder.fleaMarketView.tvMark,
                holder.fleaMarketView.tvDelete);
        SpecialData specialData = mSpecialData.get(position);
        holder.fleaMarketView.tvTitle.setText(specialData.getTitle());
        holder.fleaMarketView.tvText.setText(specialData.getDoc());
        //设置图片
        List<String> img = specialData.getImg();
        if (img == null || img.size() == 0) {
            holder.fleaMarketView.ivPic.setImageResource(R.color.default_picture);
            holder.fleaMarketView.ivBgPic.setImageResource(R.color.default_picture);
        } else {
            String picUrl = img.get(0);
            String picBg = picUrl;
            if (img.size() > 1) {
                picBg = img.get(1);
            }
            loadImageByUrl(holder.fleaMarketView.ivPic, picUrl);
            loadBlurImage(holder.fleaMarketView.ivBgPic, picBg);
        }
        if (specialData.getHasFavorite()) {
            holder.fleaMarketView.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.fleaMarketView.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
        //其他
        holder.fleaMarketView.tvCommit.setText(specialData.getCount_of_comment() + "");
        holder.fleaMarketView.tvHeart.setText(specialData.getCount_of_favorite() + "");
        //是否标识
        if (specialData.getTag()) {
            holder.fleaMarketView.ivTag.setVisibility(View.VISIBLE);
            holder.fleaMarketView.tvMark.setText("取消标记");
        } else {
            holder.fleaMarketView.ivTag.setVisibility(View.GONE);
            holder.fleaMarketView.tvMark.setText("标记");
        }
        holder.fleaMarketView.tvDelete.setVisibility(View.VISIBLE);
        holder.fleaMarketView.tvMark.setVisibility(View.VISIBLE);
    }

    /**
     * 绑定专题数据：失物招领
     */
    private void bindSubjectLostAndFound(int position, ViewHolder holder) {
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.lostAndFoundView.ivShare,
                holder.lostAndFoundView.ivCommit,
                holder.lostAndFoundView.ivHeart,
                holder.lostAndFoundView.tvMark,
                holder.lostAndFoundView.tvDelete);
        SpecialData specialData = mSpecialData.get(position);
        holder.lostAndFoundView.tvTitle.setText(specialData.getTitle());
        holder.lostAndFoundView.tvText.setText(specialData.getDoc());
        //设置图片
        List<String> img = specialData.getImg();
        if (img == null || img.size() == 0) {
            holder.lostAndFoundView.ivPic.setImageResource(R.color.default_picture);
            holder.lostAndFoundView.ivBgPic.setImageResource(R.color.default_picture);
        } else {
            String picUrl = img.get(0);
            String picBg = picUrl;
            if (img.size() > 1) {
                picBg = img.get(1);
            }
            loadImageByUrl(holder.lostAndFoundView.ivPic, picUrl);
            loadBlurImage(holder.lostAndFoundView.ivBgPic, picBg);
        }
        if (specialData.getHasFavorite()) {
            holder.lostAndFoundView.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.lostAndFoundView.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
        //其他
        holder.lostAndFoundView.tvCommit.setText(specialData.getCount_of_comment() + "");
        holder.lostAndFoundView.tvHeart.setText(specialData.getCount_of_favorite() + "");
        //是否标识
        if (specialData.getTag()) {
            holder.lostAndFoundView.ivTag.setVisibility(View.VISIBLE);
            holder.lostAndFoundView.tvMark.setText("取消标记");
        } else {
            holder.lostAndFoundView.ivTag.setVisibility(View.GONE);
            holder.lostAndFoundView.tvMark.setText("标记");
        }
        holder.lostAndFoundView.tvDelete.setVisibility(View.VISIBLE);
        holder.lostAndFoundView.tvMark.setVisibility(View.VISIBLE);
    }

    /**
     * 绑定专题数据：吐槽墙
     */
    private void bindSubjectTaunted(int position, ViewHolder holder) {
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.tauntedView.ivShare,
                holder.tauntedView.ivCommit,
                holder.tauntedView.ivHeart,
                holder.tauntedView.tvMark,
                holder.tauntedView.tvDelete);
        SpecialData specialData = mSpecialData.get(position);
        holder.tauntedView.tvTitle.setText(specialData.getTitle());
        holder.tauntedView.tvText.setText(specialData.getDoc());
        //设置图片
        List<String> img = specialData.getImg();
        if (img == null || img.size() == 0) {
            holder.tauntedView.ivPic.setImageResource(R.color.default_picture);
            holder.tauntedView.ivBgPic.setImageResource(R.color.default_picture);
        } else {
            String picUrl = img.get(0);
            String picBg = picUrl;
            if (img.size() > 1) {
                picBg = img.get(1);
            }
            loadImageByUrl(holder.tauntedView.ivPic, picUrl);
            loadBlurImage(holder.tauntedView.ivBgPic, picBg);
        }
        if (specialData.getHasFavorite()) {
            holder.tauntedView.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.tauntedView.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
        //其他
        holder.tauntedView.tvCommit.setText(specialData.getCount_of_comment() + "");
        holder.tauntedView.tvHeart.setText(specialData.getCount_of_favorite() + "");
        //是否标识
        if (specialData.getTag()) {
            holder.tauntedView.ivTag.setVisibility(View.VISIBLE);
            holder.tauntedView.tvMark.setText("取消标记");
        } else {
            holder.tauntedView.ivTag.setVisibility(View.GONE);
            holder.tauntedView.tvMark.setText("标记");
        }
        holder.tauntedView.tvDelete.setVisibility(View.VISIBLE);
        holder.tauntedView.tvMark.setVisibility(View.VISIBLE);
    }

    /**
     * 绑定专题数据：表白墙
     */
    private void bindSubjectConfession(int position, ViewHolder holder) {
        //设置TAG
        ClassUtils.bindViewsTag(position,
                holder.contentView,
                holder.confessionView.ivShare,
                holder.confessionView.ivCommit,
                holder.confessionView.ivHeart,
                holder.confessionView.tvMark,
                holder.confessionView.tvDelete);
        SpecialData specialData = mSpecialData.get(position);
        holder.confessionView.tvTitle.setText(specialData.getTitle());
        holder.confessionView.tvText.setText(specialData.getDoc());
        //设置图片
        List<String> img = specialData.getImg();
        if (img == null || img.size() == 0) {
            holder.confessionView.ivPic.setImageResource(R.color.default_picture);
            holder.confessionView.ivBgPic.setImageResource(R.color.default_picture);
        } else {
            String picUrl = img.get(0);
            String picBg = picUrl;
            if (img.size() > 1) {
                picBg = img.get(1);
            }
            loadImageByUrl(holder.confessionView.ivPic, picUrl);
            loadBlurImage(holder.confessionView.ivBgPic, picBg);
        }
        if (specialData.getHasFavorite()) {
            holder.confessionView.ivHeart.setImageResource(R.drawable.ic_heart_red);
        } else {
            holder.confessionView.ivHeart.setImageResource(R.drawable.ic_heart_black);
        }
        //其他
        holder.confessionView.tvCommit.setText(specialData.getCount_of_comment() + "");
        holder.confessionView.tvHeart.setText(specialData.getCount_of_favorite() + "");
        //是否标识
        if (specialData.getTag()) {
            holder.confessionView.ivTag.setVisibility(View.VISIBLE);
            holder.confessionView.tvMark.setText("取消标记");
        } else {
            holder.confessionView.ivTag.setVisibility(View.GONE);
            holder.confessionView.tvMark.setText("标记");
        }
        holder.confessionView.tvDelete.setVisibility(View.VISIBLE);
        holder.confessionView.tvMark.setVisibility(View.VISIBLE);
    }

    private void loadBlurImage(ImageView ivBgPic, String picUrl) {
        ImageLoaderFactory.getInstance().displayBlurImage(ivBgPic, picUrl, 7, 2);
    }

    private void loadImageByUrl(ImageView ivPic, String picUrl) {
        ImageLoaderFactory.getInstance().displayImage(ivPic, picUrl);
    }

    @Override
    public int getItemCount() {
        return mSpecialData.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        int viewType;
        View contentView;
        ConfessionView confessionView;
        TauntedView tauntedView;
        FleaMarketView fleaMarketView;
        LostAndFoundView lostAndFoundView;
        WantedSavantView wantedSavantView;
        WantedPartnerView wantedPartnerView;
    }

    private class ConfessionView {
        ImageView ivBgPic;
        ImageView ivPic;
        TextView tvTitle;
        TextView tvText;
        ImageView ivTag;
        //功能栏
        ImageView ivShare;
        ImageView ivCommit;
        ImageView ivHeart;
        TextView tvCommit;
        TextView tvHeart;
        TextView tvDelete;
        TextView tvMark;
    }

    private class TauntedView {
        ImageView ivBgPic;
        ImageView ivPic;
        TextView tvTitle;
        TextView tvText;
        ImageView ivTag;
        //功能栏
        ImageView ivShare;
        ImageView ivCommit;
        ImageView ivHeart;
        TextView tvCommit;
        TextView tvHeart;
        TextView tvDelete;
        TextView tvMark;
    }

    private class FleaMarketView {
        ImageView ivBgPic;
        ImageView ivPic;
        TextView tvTitle;
        TextView tvText;
        ImageView ivTag;
        //功能栏
        ImageView ivShare;
        ImageView ivCommit;
        ImageView ivHeart;
        TextView tvCommit;
        TextView tvHeart;
        TextView tvDelete;
        TextView tvMark;
    }

    private class LostAndFoundView {
        ImageView ivBgPic;
        ImageView ivPic;
        TextView tvTitle;
        TextView tvText;
        ImageView ivTag;
        //功能栏
        ImageView ivShare;
        ImageView ivCommit;
        ImageView ivHeart;
        TextView tvCommit;
        TextView tvHeart;
        TextView tvDelete;
        TextView tvMark;
    }

    private class WantedSavantView {
        ImageView ivBgPic;
        ImageView ivPic;
        TextView tvTitle;
        TextView tvText;
        ImageView ivTag;
        //功能栏
        ImageView ivShare;
        ImageView ivCommit;
        ImageView ivHeart;
        TextView tvCommit;
        TextView tvHeart;
        TextView tvDelete;
        TextView tvMark;
    }

    private class WantedPartnerView {
        ImageView ivBgPic;
        ImageView ivPic;
        TextView tvTitle;
        TextView tvText;
        ImageView ivTag;
        //功能栏
        ImageView ivShare;
        ImageView ivCommit;
        ImageView ivHeart;
        TextView tvCommit;
        TextView tvHeart;
        TextView tvDelete;
        TextView tvMark;
    }

    public interface OnOriginalSubjectItemClickListener {

        /**
         * 整体条目被点击
         */
        void onItemClick(int position);

        /**
         * 分享条目被点击
         */
        void onShareItemClick(int position);

        /**
         * 评论条目被电击
         */
        void onCommentItemClick(int position);

        /**
         * 喜欢条目被点击
         */
        void onLikeItemClick(int position);

        /**
         * 删除条目被点击
         */
        void onDeleteItemClick(int position);

        /**
         * 标记条目被点击
         */
        void onMarkItemClick(int position);
    }

    public void setOnOriginalSubjectItemClickListener(
            OnOriginalSubjectItemClickListener onOriginalSubjectItemClickListener) {
        this.onOriginalSubjectItemClickListener = onOriginalSubjectItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (onOriginalSubjectItemClickListener == null) {
            return;
        }
        Bundle bundle = (Bundle) v.getTag();
        if (bundle == null) {
            return;
        }
        int viewId = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_ID);
        int position = bundle.getInt(com.jkb.mrcampus.Config.BUNDLE_KEY_VIEW_POSITION);
        switch (viewId) {
            case R.id.subject_content://主体
                onOriginalSubjectItemClickListener.onItemClick(position);
                break;
            case R.id.iidbf_iv_share://分享
                onOriginalSubjectItemClickListener.onShareItemClick(position);
                break;
            case R.id.iidbf_iv_comment://分享
                onOriginalSubjectItemClickListener.onCommentItemClick(position);
                break;
            case R.id.iidbf_iv_heart://喜欢
                onOriginalSubjectItemClickListener.onLikeItemClick(position);
                break;
            case R.id.iidbf_tv_delete://删除
                onOriginalSubjectItemClickListener.onDeleteItemClick(position);
                break;
            case R.id.iidbf_tv_mark://标记
                onOriginalSubjectItemClickListener.onMarkItemClick(position);
                break;
        }
    }
}
