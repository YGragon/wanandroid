package com.dong.wanandroid.home;

import com.dong.wanandroid.model.home.HomeArticleModel;

import java.util.ArrayList;

/**
 * Created by macmini002 on 18/3/1.
 */

public interface HomeIView {
    void showLoadingView();
    void hideLoadingView();
    void showBanner(ArrayList<String> strings, ArrayList<String> images) ;
    void showFuncation(ArrayList<String> funcTitles) ;
    void showHomeArticleList(ArrayList<HomeArticleModel> mHomeArticleModels, int totalCount) ;
}
