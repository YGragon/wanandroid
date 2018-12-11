package com.dong.wanandroid.home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.dong.wanandroid.browser.BrowserActivity;
import com.dong.wanandroid.data.db.DBHelper;
import com.dong.wanandroid.data.home.HomeArticleModel;
import com.dong.wanandroid.data.home.HomeDataSource;
import com.dong.wanandroid.data.home.HomeRepository;
import com.dong.wanandroid.data.read_record.ReadRecordModel;
import com.dong.wanandroid.search.SearchActivity;
import com.dong.wanandroid.util.NotNullUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by macmini002 on 18/3/1.
 */

public class HomeIPresenterCompl implements HomeContract.Presenter {
    private static final String TAG = "HomeIPresenterCompl";
    private final HomeRepository mHomeRepository;

    private final HomeContract.View mHomeView;

    public HomeIPresenterCompl(@NonNull HomeRepository homeRepository, @NonNull HomeContract.View homeView) {
        mHomeRepository = NotNullUtil.checkNotNull(homeRepository);
        mHomeView = NotNullUtil.checkNotNull(homeView);

        mHomeView.setPresenter(this);
    }

    @Override
    public ArrayList<String> getFuncData() {

        return mHomeRepository.getFuncData();
    }

    @Override
    public void getBannerData(Context context) {
        mHomeRepository.getBannerData(context, new HomeDataSource.LoadBannersCallback() {
            @Override
            public void onBannerLoaded(ArrayList<String> images, ArrayList<String> urls) {
                mHomeView.showBanner(images,urls);
            }

            @Override
            public void onBannerNotAvailable() {}
        });
    }

    @Override
    public void getHomeArticleList(Context context, int page) {
        mHomeView.showLoadingView();
        mHomeRepository.getHomeArticleList(context, page, new HomeDataSource.GetHomeArticleCallback() {
            @Override
            public void onHomeArticleLoaded(ArrayList<HomeArticleModel> homeArticleModels, int totalCount) {
                mHomeView.showHomeArticleList(homeArticleModels,totalCount);
                mHomeView.hideLoadingView();
            }
            @Override
            public void onHomeArticleNotAvailable() {
             }
        });
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

    @Override
    public void start() {

    }
}
