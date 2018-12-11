package com.dong.wanandroid.data.home;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/12/11.
 */

public interface HomeDataSource {

    interface LoadBannersCallback {

        void onBannerLoaded(ArrayList<String> images,ArrayList<String> urls);

        void onBannerNotAvailable();
    }

    interface GetHomeArticleCallback {

        void onHomeArticleLoaded(ArrayList<HomeArticleModel> homeArticleModels, int totalCount);

        void onHomeArticleNotAvailable();
    }



    ArrayList<String> getFuncData();
    void getBannerData(Context context,LoadBannersCallback loadBannersCallback);
    void getHomeArticleList(Context context, int page,GetHomeArticleCallback getHomeArticleCallback);

}
