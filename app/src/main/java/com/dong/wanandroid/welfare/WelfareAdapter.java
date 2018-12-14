package com.dong.wanandroid.welfare;

import android.support.annotation.LayoutRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dong.wanandroid.R;
import com.dong.wanandroid.data.welfare.WelfareModel;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class WelfareAdapter extends BaseQuickAdapter<WelfareModel,BaseViewHolder> {
    public WelfareAdapter(@LayoutRes int layoutResId, List data) {
        super(layoutResId,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WelfareModel item) {
        Glide.with(mContext).load(item.getUrl()).crossFade().into((ImageView) helper.getView(R.id.iv_grid_welfare));
    }
}
