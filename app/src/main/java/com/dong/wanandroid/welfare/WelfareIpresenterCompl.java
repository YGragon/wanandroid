package com.dong.wanandroid.welfare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BasePresenter;
import com.dong.wanandroid.big_image.BigImageActivity;
import com.dong.wanandroid.data.AppConfig;
import com.dong.wanandroid.data.welfare.WelfareList;
import com.dong.wanandroid.data.welfare.WelfareModel;
import com.dong.wanandroid.http.ApiConstant;
import com.dong.wanandroid.http.ApiService;
import com.dong.wanandroid.http.RetrofitHelper;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/1.
 */

public class WelfareIpresenterCompl extends BasePresenter<WelfareIView> implements WelfareIpresenter {
    private static final String TAG = "WelfareIpresenterCompl";
    private WelfareIView mWelfareIView ;
    private ArrayList<WelfareModel> mWelfareModels = new ArrayList<>() ;
    private ArrayList<String> images = new ArrayList<>() ;


    public WelfareIpresenterCompl(WelfareIView welfareIView) {
        this.mWelfareIView = welfareIView ;
    }

    @Override
    public void getWelfareData(Context context, String type, int size, int page) {
        mWelfareIView.showLoadingView();
        RetrofitHelper.getInstance(context).create(ApiService.class, ApiConstant.BASE_URL_GANK)
                .getWelfareList(type, size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WelfareList>() {
                    @Override
                    public void accept(WelfareList welfareList) throws Exception {
                        mWelfareModels.clear();
                        images.clear();
                        mWelfareModels.addAll(welfareList.getResults()) ;
                        mWelfareIView.showWelfareResult(mWelfareModels);
                        for (int i=0; i < mWelfareModels.size(); i++){
                            images.add(mWelfareModels.get(i).getUrl()) ;
                        }
                        mWelfareIView.hideLoadingView();
                    }
                });
    }

    @Override
    public void toBigImageAc(Context context, ImageView shareImage, String imageUrl) {
        Intent intent = new Intent(context, BigImageActivity.class);
        intent.putExtra(AppConfig.IMAGE_URL,imageUrl);
        context.startActivity(intent,
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        (Activity) context,
                        shareImage,
                        context.getString(R.string.share_pic_str))
                .toBundle());

    }
}
