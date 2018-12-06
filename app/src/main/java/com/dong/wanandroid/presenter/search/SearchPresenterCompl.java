package com.dong.wanandroid.presenter.search;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dong.wanandroid.base.BasePresenter;
import com.dong.wanandroid.constant.ApiConstant;
import com.dong.wanandroid.http.ApiService;
import com.dong.wanandroid.http.RetrofitHelper;
import com.dong.wanandroid.model.home.HomeArticleList;
import com.dong.wanandroid.model.home.HomeArticleModel;
import com.dong.wanandroid.model.search.SearchHotList;
import com.dong.wanandroid.model.search.SearchModel;
import com.dong.wanandroid.ui.activity.search.SearchIView;
import com.dong.wanandroid.ui.activity.browser.BrowserActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by macmini002 on 18/3/1.
 */

public class SearchPresenterCompl extends BasePresenter<SearchIView> implements SearchIPresenter {
    private static final String TAG = "SearchPresenterCompl";
    private List<SearchModel> searchHotLists = new ArrayList<>();
    private ArrayList<HomeArticleModel> mSearchResultModels = new ArrayList<>() ;
    private int mTotal;



    private SearchIView searchIView ;
    public SearchPresenterCompl(SearchIView searchIView) {
        this.searchIView = searchIView ;
    }

    @Override
    public void getHotKeyList(Context context) {
        RetrofitHelper.getInstance(context).create(ApiService.class, ApiConstant.BASE_URL_WAN_ANDROID)
                .getHotKeyList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SearchHotList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(SearchHotList searchHotList) {
                        searchHotLists = searchHotList.getData();
                        Log.e(TAG, "onNext: ------------>" + searchHotLists.get(0).getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ------------>" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        searchIView.showHotKey(searchHotLists);
                        Log.e(TAG, "onComplete: ------完成------>" );
                    }


                });

    }

    @Override
    public void getSearchResultList(Context context, int page , final String keyWold) {
        RetrofitHelper.getInstance(context).create(ApiService.class, ApiConstant.BASE_URL_WAN_ANDROID)
                .getSearchResultList(page,keyWold)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeArticleList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(HomeArticleList homeArticleList) {
                        mTotal = homeArticleList.getData().getTotal();
                        Log.e(TAG, "onNext: size------>"+homeArticleList.getData().getDatas().size() );
                        mSearchResultModels.addAll(homeArticleList.getData().getDatas()) ;
                        Log.e(TAG, "onNext: size------>"+mSearchResultModels.size() );
                        searchIView.showSearchResult(mSearchResultModels,mTotal,keyWold);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ------------>" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        searchIView.showHotKey(searchHotLists);
                        Log.e(TAG, "onComplete: ------完成------>" );
                    }


                });
    }

    @Override
    public void toBrowserAc(Context context, String link) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra("param_url",link) ;
        context.startActivity(intent);
    }
}
