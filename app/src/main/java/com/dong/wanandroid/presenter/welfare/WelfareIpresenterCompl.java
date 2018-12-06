package com.dong.wanandroid.presenter.welfare;

import android.content.Context;
import android.content.Intent;

import com.dong.wanandroid.base.BasePresenter;
import com.dong.wanandroid.constant.ApiConstant;
import com.dong.wanandroid.http.ApiService;
import com.dong.wanandroid.http.RetrofitHelper;
import com.dong.wanandroid.model.welfare.WelfareList;
import com.dong.wanandroid.model.welfare.WelfareModel;
import com.dong.wanandroid.ui.fragment.welfare.WelfareIView;
import com.dong.wanandroid.ui.activity.big_image.BigImageActivity;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/1.
 */

public class WelfareIpresenterCompl extends BasePresenter<WelfareIView> implements WelfareIpresenter {
    private static final String TAG = "WelfareIpresenterCompl";
    private WelfareIView mWelfareIView ;
    private ArrayList<WelfareModel> mWelfareModels = new ArrayList<>() ;
    private ArrayList<String> images = new ArrayList<>() ;

    private int mPage ;
    private int mSize ;

    public WelfareIpresenterCompl(WelfareIView welfareIView) {
        this.mWelfareIView = welfareIView ;
    }

    @Override
    public void getWelfareData(Context context, String type, int size, int page) {
        mWelfareIView.showLoadingView();
        mPage = page ;
        mSize = size ;
        RetrofitHelper.getInstance(context).create(ApiService.class, ApiConstant.BASE_URL_GANK)
                .getWelfareList(type, size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WelfareList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onNext(@NonNull WelfareList welfareList) {
                        mWelfareModels.clear();
                        images.clear();
                        mWelfareModels.addAll(welfareList.getResults()) ;
                        mWelfareIView.showWelfareResult(mWelfareModels);
                        for (int i=0; i < mWelfareModels.size(); i++){
                            images.add(mWelfareModels.get(i).getUrl()) ;
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) { }

                    @Override
                    public void onComplete() {
                        mWelfareIView.hideLoadingView();
                    }
                });
    }

    @Override
    public void toBigImageAc(Context context, int position, ArrayList<WelfareModel> welfareModelArrayList) {
        Intent intent = new Intent(context, BigImageActivity.class);
        ArrayList<String> images = new ArrayList<>();
        for (int j = 0; j < welfareModelArrayList.size(); j++) {
            images.add(welfareModelArrayList.get(j).getUrl());
        }
        intent.putExtra("images", images);
        intent.putExtra("position", position) ;
        intent.putExtra("page", mPage) ;
        intent.putExtra("size", mSize) ;
        context.startActivity(intent);
    }
}
