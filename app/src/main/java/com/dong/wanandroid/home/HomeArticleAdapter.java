package com.dong.wanandroid.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dong.wanandroid.R;
import com.dong.wanandroid.model.home.HomeArticleModel;

import java.util.List;

/**
 * Created by Administrator on 2018/2/28.
 */

public class HomeArticleAdapter extends BaseQuickAdapter<HomeArticleModel, BaseViewHolder> {
    public HomeArticleAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeArticleModel item) {
        helper.setText(R.id.home_article_content_tv, item.getTitle());
    }
}
