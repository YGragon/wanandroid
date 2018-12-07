package com.dong.wanandroid.gank;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.util.MultiTypeDelegate;
import com.dong.wanandroid.R;
import com.dong.wanandroid.data.flow.FlowGankModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by macmini002 on 18/5/29.
 */

public class FlowAdapter extends BaseQuickAdapter<FlowGankModel.ResultsEntity,BaseViewHolder> {

    private static final int TEXT = 0 ;
    private static final int IMG = 1 ;

    public FlowAdapter(List data) {
        super(data);
        //Step.1
        setMultiTypeDelegate(new MultiTypeDelegate<FlowGankModel.ResultsEntity>() {
            @Override
            protected int getItemType(FlowGankModel.ResultsEntity entity) {
                //根据实体类来判断布局类型
                if (entity.getImages() != null){
                    return IMG;
                }else {
                    return TEXT;
                }
//                if (entity.getImages().size() > 0){
//
//                }else {
//
//                }
            }
        });

        //Step.2
        getMultiTypeDelegate()
                .registerItemType(TEXT, R.layout.flow_text_item)
                .registerItemType(IMG, R.layout.flow_image_item);

    }

    @Override
    protected void convert(BaseViewHolder helper, FlowGankModel.ResultsEntity item) {

        // Step3
        TextView userName = helper.getView(R.id.card_item_user_name);
        CircleImageView userHead = helper.getView(R.id.card_item_user_head);
        TextView time = helper.getView(R.id.card_item_time);
        TextView topicTitle = helper.getView(R.id.card_item_title);

        LinearLayout textLayout = helper.getView(R.id.circle_home_text_layout);
        CardView itemLayout = helper.getView(R.id.item_layout) ;


        userName.setText(item.getWho());
        time.setText(item.getCreatedAt());
        Glide.with(mContext).load(R.mipmap.ic_launcher).into(userHead);

        // 有标题显示标题，没有标题显示内容，两个都没有显示图片
        if (item.getDesc().length() > 0) {
            textLayout.setVisibility(View.VISIBLE);
            topicTitle.setText(item.getDesc());
        } else {
            // 只有图片的情况
            textLayout.setVisibility(View.GONE);
        }


        switch (helper.getItemViewType()) {
            case TEXT:
                userName.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent));
                time.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
                break;
            case IMG:


                userName.setTextColor(ContextCompat.getColor(mContext,R.color.white));
                time.setTextColor(ContextCompat.getColor(mContext,R.color.colorPrimary));
                final ImageView circleTopicIv = helper.getView(R.id.circle_topic_iv);

                Glide.with(mContext).load(item.getImages().get(0)).placeholder(R.mipmap.ic_launcher).into(circleTopicIv);

                break;
        }
    }
}
