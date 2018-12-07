package com.dong.wanandroid.home;

import android.content.Context;

import com.dong.wanandroid.data.home.HomeArticleModel;
import com.dong.wanandroid.data.read_record.ReadRecordModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macmini002 on 18/3/1.
 */

public interface HomeIPresenter {
    ArrayList<String> getFuncData() ;
    void getBannerData(Context context) ;
    void getHomeArticleList(Context context, int page) ;
    void toSearchAc(Context context) ;
    void toBrowserAc(Context context, String url, int id, String title, String author) ;
    void saveReadRecordToDb(HomeArticleModel homeArticleModel) ;
    List<ReadRecordModel> getReadRecordFromDb() ;

}
