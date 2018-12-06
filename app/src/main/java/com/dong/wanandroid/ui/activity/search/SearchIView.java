package com.dong.wanandroid.ui.activity.search;

import com.dong.wanandroid.model.home.HomeArticleModel;
import com.dong.wanandroid.model.search.SearchModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macmini002 on 18/3/1.
 */

public interface SearchIView {
    void showHotKey(List<SearchModel> searchHotLists) ;
    void showSearchResult(ArrayList<HomeArticleModel> mSearchResultModels, int total, String keyWold) ;
}
