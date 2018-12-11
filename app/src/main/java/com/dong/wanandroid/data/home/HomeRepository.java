package com.dong.wanandroid.data.home;

import android.content.Context;

import com.dong.wanandroid.data.banner.Banner;
import com.dong.wanandroid.data.banner.BannerList;
import com.dong.wanandroid.data.db.DBHelper;
import com.dong.wanandroid.http.ApiConstant;
import com.dong.wanandroid.http.ApiService;
import com.dong.wanandroid.http.RetrofitHelper;
import com.dong.wanandroid.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/12/11.
 */
public class HomeRepository implements HomeDataSource {

    private ArrayList<String> funcTitles = new ArrayList<>() ;
    private List<Banner> bannerLists = new ArrayList<>() ;
    private ArrayList<String> images = new ArrayList<>() ;
    private ArrayList<String> url = new ArrayList<>() ;
    private ArrayList<HomeArticleModel> mHomeArticleModels = new ArrayList<>() ;
    private int mTotal;


    @Inject
    public HomeRepository() {}

    @Override
    public ArrayList<String> getFuncData() {
        funcTitles.clear();
        funcTitles.add("常用网址");
        funcTitles.add("体系");
        funcTitles.add("导航");
        funcTitles.add("项目");
        return funcTitles;
    }

    @Override
    public void getBannerData(Context context, final LoadBannersCallback loadBannersCallback) {
        if (NetworkUtils.isConnected()){
            bannerLists.clear();
            RetrofitHelper.getInstance(context).create(ApiService.class, ApiConstant.BASE_URL_WAN_ANDROID)
                    .getBanner()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BannerList>() {
                        @Override
                        public void onSubscribe(Disposable d) {}

                        @Override
                        public void onNext(BannerList bannerList) {
                            bannerLists = bannerList.getData();
                        }

                        @Override
                        public void onError(Throwable e) {}

                        @Override
                        public void onComplete() {
                            DBHelper.setBannerToDb(bannerLists);
                            images.clear();
                            url.clear();
                            for (int i = 0; i < bannerLists.size(); i++) {
                                images.add(bannerLists.get(i).getImagePath());
                                url.add(bannerLists.get(i).getUrl());
                            }
                            loadBannersCallback.onBannerLoaded(images,url);
                        }


                    });
        }else {
            bannerLists.clear();
            images.clear();
            url.clear();
            List<Banner> bannerFromDb = DBHelper.getBannerFromDb();
            bannerLists.addAll(bannerFromDb) ;
            if (bannerLists.size() > 0){
                for (int i = 0; i < bannerLists.size(); i++) {
                    images.add(bannerLists.get(i).getImagePath());
                    url.add(bannerLists.get(i).getUrl());
                }
                loadBannersCallback.onBannerLoaded(images,url);
            }
        }
    }

    @Override
    public void getHomeArticleList(Context context, int page, final GetHomeArticleCallback getHomeArticleCallback) {
        if (NetworkUtils.isConnected()){
            RetrofitHelper.getInstance(context).create(ApiService.class, ApiConstant.BASE_URL_WAN_ANDROID)
                    .getHomeArticleList(page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<HomeArticleList>() {
                        @Override
                        public void onSubscribe(Disposable d) { }

                        @Override
                        public void onNext(HomeArticleList homeArticleList) {
                            mTotal = homeArticleList.getData().getTotal();
                            mHomeArticleModels.addAll(homeArticleList.getData().getDatas()) ;
                            DBHelper.setArticleToDb(mHomeArticleModels);
                            getHomeArticleCallback.onHomeArticleLoaded(mHomeArticleModels,mTotal);
                        }

                        @Override
                        public void onError(Throwable e) {}

                        @Override
                        public void onComplete() {}

                    });
        }else {
            mHomeArticleModels.clear();
            List<HomeArticleModel> articleFromDb = DBHelper.getArticleFromDb();
            mHomeArticleModels.addAll(articleFromDb) ;
            getHomeArticleCallback.onHomeArticleLoaded(mHomeArticleModels,mHomeArticleModels.size());
        }
    }
}
