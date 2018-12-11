package com.dong.wanandroid.home;

import android.content.Context;

import com.dong.wanandroid.BasePresenter;
import com.dong.wanandroid.base.BaseView;
import com.dong.wanandroid.data.home.HomeArticleModel;
import com.dong.wanandroid.data.read_record.ReadRecordModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/12/10.
 */

public interface HomeContract {
    interface View extends BaseView<Presenter> {

        void showLoadingView();
        void hideLoadingView();
        void showBanner(ArrayList<String> images, ArrayList<String> urls) ;
        void showFuncation(ArrayList<String> funcTitles) ;
        void showHomeArticleList(ArrayList<HomeArticleModel> mHomeArticleModels, int totalCount) ;

    }

    interface Presenter extends BasePresenter {

        ArrayList<String> getFuncData() ;
        void getBannerData(Context context) ;
        void getHomeArticleList(Context context, int page) ;
        void toSearchAc(Context context) ;
        void toBrowserAc(Context context, String url, int id, String title, String author) ;
        void saveReadRecordToDb(HomeArticleModel homeArticleModel) ;
        List<ReadRecordModel> getReadRecordFromDb() ;

    }
}
