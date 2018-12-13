package com.dong.wanandroid.home;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dong.wanandroid.R;
import com.dong.wanandroid.data.home.HomeArticleModel;

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
        helper.setText(R.id.tv_home_article_content, item.getTitle());
        helper.setText(R.id.tv_home_author_name, item.getAuthor());
        helper.setText(R.id.tv_home_publish_time, item.getNiceDate());
    }
}
