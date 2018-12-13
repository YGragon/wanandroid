package com.dong.wanandroid.search;

import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dong.wanandroid.R;
import com.dong.wanandroid.data.home.HomeArticleModel;

import java.util.List;

/**
 * Created by macmini002 on 18/3/1.
 */

public class SearchAdapter extends BaseQuickAdapter<HomeArticleModel, BaseViewHolder> {
    public SearchAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeArticleModel item) {
        helper.setText(R.id.tv_home_article_content, Html.fromHtml(item.getTitle()));
    }
}
