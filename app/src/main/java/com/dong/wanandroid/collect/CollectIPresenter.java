package com.dong.wanandroid.collect;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.widget.TextView;

/**
 * Created by Administrator on 2018/3/18.
 */

public interface CollectIPresenter {
    void collectPostOutSite(Context context, String title, String author, String url) ;
    void collectPostInSite(Context context,int id, String title, String author, String url) ;
    void getCollectList(Context context,int page) ;
    void showGuideView(Context context,TextView shareTv,FloatingActionButton bottomTv) ;
    void toBrowserAc(Context context, String url, int id, String title, String author) ;
}
