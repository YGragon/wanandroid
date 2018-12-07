package com.dong.wanandroid.home;

import android.content.Context;
import android.content.Intent;

import com.dong.wanandroid.model.banner.Banner;
import com.dong.wanandroid.model.banner.BannerList;
import com.dong.wanandroid.base.BasePresenter;
import com.dong.wanandroid.constant.ApiConstant;
import com.dong.wanandroid.model.db.DBHelper;
import com.dong.wanandroid.http.ApiService;
import com.dong.wanandroid.http.RetrofitHelper;
import com.dong.wanandroid.model.home.HomeArticleList;
import com.dong.wanandroid.model.home.HomeArticleModel;
import com.dong.wanandroid.model.read_record.ReadRecordModel;
import com.dong.wanandroid.search.SearchActivity;
import com.dong.wanandroid.browser.BrowserActivity;
import com.dong.wanandroid.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by macmini002 on 18/3/1.
 */

public class HomeIPresenterCompl extends BasePresenter<HomeIView> implements HomeIPresenter {
    private static final String TAG = "HomeIPresenterCompl";
    private HomeIView homeIView;

    private List<Banner> bannerLists = new ArrayList<>() ;
    private ArrayList<String> funcTitles = new ArrayList<>() ;
    private ArrayList<String> images = new ArrayList<>() ;
    private ArrayList<String> url = new ArrayList<>() ;
    private ArrayList<HomeArticleModel> mHomeArticleModels = new ArrayList<>() ;
    private int mTotal;

    public HomeIPresenterCompl(HomeIView homeIView) {
        this.homeIView = homeIView ;
    }

    @Override
    public ArrayList<String> getFuncData() {
        funcTitles.clear();
        funcTitles.add("常用网址");
        funcTitles.add("体系");
        funcTitles.add("导航");
        funcTitles.add("项目");
        return funcTitles ;
    }

    @Override
    public void getBannerData(Context context) {
        if (NetworkUtils.isConnected()){
            bannerLists.clear();
            RetrofitHelper.getInstance(context).create(ApiService.class, ApiConstant.BASE_URL_WAN_ANDROID)
                    .getBanner()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BannerList>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }

                        @Override
                        public void onNext(BannerList bannerList) {
                            bannerLists = bannerList.getData();
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                            DBHelper.setBannerToDb(bannerLists);
                            images.clear();
                            url.clear();
                            for (int i = 0; i < bannerLists.size(); i++) {
                                images.add(bannerLists.get(i).getImagePath());
                                url.add(bannerLists.get(i).getUrl());
                            }
                            homeIView.showBanner(images,url);
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
                homeIView.showBanner(images,url);
            }
        }

    }

    @Override
    public void getHomeArticleList(Context context, int page) {
        if (NetworkUtils.isConnected()){
            homeIView.showLoadingView();
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
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                            DBHelper.setArticleToDb(mHomeArticleModels);
                            homeIView.showHomeArticleList(mHomeArticleModels,mTotal);
                            homeIView.hideLoadingView();
                        }

                    });
        }else {
            homeIView.showLoadingView();
            mHomeArticleModels.clear();
            List<HomeArticleModel> articleFromDb = DBHelper.getArticleFromDb();
            mHomeArticleModels.addAll(articleFromDb) ;
            homeIView.showHomeArticleList(mHomeArticleModels,mHomeArticleModels.size());
            homeIView.hideLoadingView();
        }
    }

    @Override
    public void toSearchAc(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void toBrowserAc(Context context,String param_url,int id, String title,String author) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra("param_url",param_url) ;
        intent.putExtra("param_id",id) ;
        intent.putExtra("param_title",title) ;
        intent.putExtra("param_author",author) ;
        context.startActivity(intent);
    }

    @Override
    public void saveReadRecordToDb(HomeArticleModel homeArticleModel) {
        ReadRecordModel readRecordModel = new ReadRecordModel();
        readRecordModel.setApkLink(homeArticleModel.getApkLink());
        readRecordModel.setAuthor(homeArticleModel.getAuthor());
        readRecordModel.setChapterId(homeArticleModel.getChapterId());
        readRecordModel.setChapterName(homeArticleModel.getChapterName());
        readRecordModel.setCollect(homeArticleModel.getCollect());
        readRecordModel.setCourseId(homeArticleModel.getCourseId());
        readRecordModel.setDesc(homeArticleModel.getDesc());
        readRecordModel.setEnvelopePic(homeArticleModel.getEnvelopePic());
        readRecordModel.setId(homeArticleModel.getId());
        readRecordModel.setNiceDate(homeArticleModel.getNiceDate());
        readRecordModel.setOrigin(homeArticleModel.getOrigin());
        readRecordModel.setProjectLink(homeArticleModel.getProjectLink());
        readRecordModel.setPublishTime(homeArticleModel.getPublishTime());
        readRecordModel.setVisible(homeArticleModel.getVisible());
        readRecordModel.setZan(homeArticleModel.getZan());
        readRecordModel.setTitle(homeArticleModel.getTitle());
        readRecordModel.setLink(homeArticleModel.getLink());
        DBHelper.setReadRecordToDb(readRecordModel);
    }

    @Override
    public List<ReadRecordModel> getReadRecordFromDb() {
        List<ReadRecordModel> readRecordFromDb = DBHelper.getReadRecordFromDb();
        return readRecordFromDb ;
    }

}
