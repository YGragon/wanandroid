package com.dong.wanandroid.search;

import android.content.Context;

/**
 * Created by macmini002 on 18/3/1.
 */

public interface SearchIPresenter {
    void getHotKeyList(Context context) ;
    void getSearchResultList(Context context,int page, String keyWold) ;

    void toBrowserAc(Context context, String link);
}
