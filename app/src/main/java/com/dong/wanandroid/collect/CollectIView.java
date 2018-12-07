package com.dong.wanandroid.collect;

import com.dong.wanandroid.model.home.HomeArticleBean;

/**
 * Created by Administrator on 2018/3/18.
 */

public interface CollectIView {
    void showCollectResult(int resultCode, String errorMsg, final int totalCount, HomeArticleBean data) ;
    void showGuideViewResult() ;
}
