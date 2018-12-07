package com.dong.wanandroid.collect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnGuideChangedListener;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.listener.OnPageChangedListener;
import com.app.hubert.guide.model.GuidePage;
import com.app.hubert.guide.model.HighLight;
import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BasePresenter;
import com.dong.wanandroid.data.home.HomeArticleBean;
import com.dong.wanandroid.http.ApiConstant;
import com.dong.wanandroid.http.ApiService;
import com.dong.wanandroid.http.RetrofitHelper;
import com.dong.wanandroid.data.home.HomeArticleList;
import com.dong.wanandroid.browser.BrowserActivity;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/18.
 */

public class CollectIpresenterCompl extends BasePresenter<CollectIView> implements CollectIPresenter {
    private static final String TAG = "CollectIpresenterCompl";
    private CollectIView mCollectIView;

    public CollectIpresenterCompl(CollectIView collectIView) {
        this.mCollectIView = collectIView ;
    }

    @Override
    public void collectPostOutSite(Context context, String title, String author, String url) {
        RetrofitHelper.getInstance(context).create(ApiService.class, ApiConstant.BASE_URL_WAN_ANDROID)
                .collectPost("add",title,author,url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeArticleList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HomeArticleList homeArticleList) {
                        Log.e(TAG, "onNext: code--->"+homeArticleList.getErrorCode());
                        Log.e(TAG, "onNext: code--->"+homeArticleList.getErrorMsg());
                        mCollectIView.showCollectResult(
                                homeArticleList.getErrorCode(),
                                homeArticleList.getErrorMsg(),
                                homeArticleList.getData().getTotal(),
                                homeArticleList.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.toString() );
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: " );
                    }
                });
    }

    @Override
    public void collectPostInSite(final Context context, int id, String title, String author, String url) {
        RetrofitHelper.getInstance(context).create(ApiService.class,ApiConstant.BASE_URL_WAN_ANDROID)
                .collectPost(id+"",title,author,url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeArticleList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HomeArticleList homeArticleList) {
                        Log.e(TAG, "onNext: code--->"+homeArticleList.getErrorCode());
                        Log.e(TAG, "onNext: code--->"+homeArticleList.getErrorMsg());
                        HomeArticleBean homeArticleBean = homeArticleList.getData();
                        int totalCount = 0 ;
                        if (homeArticleBean != null){
                            totalCount = homeArticleList.getData().getTotal() ;
                        }else {
                            totalCount = 0 ;
                        }
                        mCollectIView.showCollectResult(
                                homeArticleList.getErrorCode(),
                                homeArticleList.getErrorMsg(),
                                totalCount,
                                homeArticleBean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.toString() );
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: " );
                    }
                });
    }

    @Override
    public void getCollectList(Context context, int page) {
        Log.e(TAG, "getCollectList: page--->"+page );
        RetrofitHelper.getInstance(context).create(ApiService.class,ApiConstant.BASE_URL_WAN_ANDROID)
                .getCollectList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeArticleList>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull HomeArticleList homeArticleList) {
                        Log.e(TAG, "onNext: code--->"+homeArticleList.getErrorCode());
                        Log.e(TAG, "onNext: code--->"+homeArticleList.getErrorMsg());
                        mCollectIView.showCollectResult(
                                homeArticleList.getErrorCode(),
                                homeArticleList.getErrorMsg(),
                                homeArticleList.getData().getTotal(),
                                homeArticleList.getData());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.toString() );
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: " );
                    }
                });
    }

    @Override
    public void showGuideView(Context context, TextView shareTv, FloatingActionButton bottomTv) {

        Animation enterAnimation = new AlphaAnimation(0f, 1f);
        enterAnimation.setDuration(600);
        enterAnimation.setFillAfter(true);

        Animation exitAnimation = new AlphaAnimation(1f, 0f);
        exitAnimation.setDuration(600);
        exitAnimation.setFillAfter(true);

        NewbieGuide.with((Activity) context)
                .setLabel("page")//设置引导层标示区分不同引导层，必传！否则报错
                .setOnGuideChangedListener(new OnGuideChangedListener() {
                    @Override
                    public void onShowed(Controller controller) {
                        Log.e(TAG, "NewbieGuide onShowed: ");
                        //引导层显示
                    }

                    @Override
                    public void onRemoved(Controller controller) {
                        Log.e(TAG, "NewbieGuide  onRemoved: ");
                        //引导层消失（多页切换不会触发）
                        mCollectIView.showGuideViewResult();
                    }
                })
                .setOnPageChangedListener(new OnPageChangedListener() {
                    @Override
                    public void onPageChanged(int page) {
                        Log.e(TAG, "NewbieGuide  onPageChanged: " + page);
                        //引导页切换，page为当前页位置，从0开始
                    }
                })
                .alwaysShow(true)//是否每次都显示引导层，默认false，只显示一次
                .addGuidePage(//添加一页引导页
                        GuidePage.newInstance()//创建一个实例
                                .addHighLight(shareTv,HighLight.Shape.ROUND_RECTANGLE, 20)//添加高亮的view
                                .addHighLight(bottomTv, HighLight.Shape.ROUND_RECTANGLE, 10) //添加高亮的view
                                .setLayoutRes(R.layout.guide_browser_share_layout)//设置引导页布局
                                .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                                    @Override
                                    public void onLayoutInflated(View view) {
                                        //引导页布局填充后回调，用于初始化
                                        TextView tv = view.findViewById(R.id.textView2);
                                        tv.setText("点击可以分享哟~");
                                    }
                                })
                                .setEnterAnimation(enterAnimation)//进入动画
                                .setExitAnimation(exitAnimation)//退出动画
                )
                .addGuidePage(//添加第二页页引导页
                        GuidePage.newInstance()
                                .addHighLight(bottomTv, HighLight.Shape.RECTANGLE,20)
                                .setLayoutRes(R.layout.guide2_browser_share_layout, R.id.iv)//引导页布局，点击跳转下一页或者消失引导层的控件id
                                .setEverywhereCancelable(false)//是否点击任意地方跳转下一页或者消失引导层，默认true
                                .setBackgroundColor(context.getResources().getColor(R.color.guide_bg_color_9e4891ff))//设置背景色，建议使用有透明度的颜色
                                .setEnterAnimation(enterAnimation)//进入动画
                                .setExitAnimation(exitAnimation)//退出动画
                )
                .show();//显示引导层(至少需要一页引导页才能显示)
    }

    @Override
    public void toBrowserAc(Context context, String url, int id, String title, String author) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra("param_url",url) ;
        intent.putExtra("param_id",id) ;
        intent.putExtra("param_title",title) ;
        intent.putExtra("param_author",author) ;
        context.startActivity(intent);
    }
}
