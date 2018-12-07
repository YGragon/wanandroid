package com.dong.wanandroid.search;

import com.dong.wanandroid.data.home.HomeArticleModel;
import com.dong.wanandroid.data.search.SearchModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macmini002 on 18/3/1.
 */

public interface SearchIView {
    void showHotKey(List<SearchModel> searchHotLists) ;
    void showSearchResult(ArrayList<HomeArticleModel> mSearchResultModels, int total, String keyWold) ;
}
