package com.dong.wanandroid.presenter.bigimage;

import android.content.Context;

import com.dong.wanandroid.base.BasePresenter;
import com.dong.wanandroid.constant.ApiConstant;
import com.dong.wanandroid.http.ApiService;
import com.dong.wanandroid.http.RetrofitHelper;
import com.dong.wanandroid.model.welfare.WelfareList;
import com.dong.wanandroid.model.welfare.WelfareModel;
import com.dong.wanandroid.ui.activity.big_image.BigImageIView;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/1.
 */

public class BigImageIpresentCompl extends BasePresenter<BigImageIView> implements BigImageIPresent {

    private BigImageIView mBigImageIView ;
    private ArrayList<WelfareModel> mWelfareModels = new ArrayList<>() ;
    private ArrayList<String> images = new ArrayList<>() ;

    private int mPage ;
    private int mSize ;

    public BigImageIpresentCompl(BigImageIView bigImageIView) {
        this.mBigImageIView = bigImageIView ;
    }

    @Override
    public void getBigImageUrl(Context context, String type, int size, int page) {
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
                        mBigImageIView.showWelfareResult(mWelfareModels, mSize, mPage);
                        for (int i=0; i < mWelfareModels.size(); i++){
                            images.add(mWelfareModels.get(i).getUrl()) ;
                        }
                        mBigImageIView.showWelfareImage(images);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {}

                    @Override
                    public void onComplete() {}
                });
    }
}
