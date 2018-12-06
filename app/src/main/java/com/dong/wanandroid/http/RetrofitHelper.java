package com.dong.wanandroid.http;

import android.content.Context;

import com.dong.wanandroid.BuildConfig;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/9/15.
 */

public class RetrofitHelper {
    // baseUrl
    private static RetrofitHelper mRetrofitHelper = null;
    private static final int DEFAULT_TIMEOUT = 5 ;
    private Context mContext ;

    public RetrofitHelper(Context context) {
        this.mContext = context ;
    }

    /**
     * 单例
     * @return
     */
    public static RetrofitHelper getInstance(Context context) {
        synchronized (RetrofitHelper.class) {
            if (mRetrofitHelper == null) {
                mRetrofitHelper = new RetrofitHelper(context.getApplicationContext());
            }
        }
        return mRetrofitHelper;
    }

    /**
     * 动态添加 Base_Url
     * @param service
     * @param url
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service,String url) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder() ;
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        OkHttpClient httpClient = new OkHttpClient();
        PersistentCookieJar cookieJar = new PersistentCookieJar(
                new SetCookieCache(),
                new SharedPrefsCookiePersistor(mContext));
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            AddCookiesInterceptor addCookiesInterceptor = new AddCookiesInterceptor(mContext);
//            SaveCookiesInterceptor saveCookiesInterceptor = new SaveCookiesInterceptor(mContext);
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient = new OkHttpClient.Builder()
//                    .addInterceptor(saveCookiesInterceptor)
//                    .addInterceptor(addCookiesInterceptor)
                    .cookieJar(cookieJar)
                    .addInterceptor(logging)
                    .build();

        }
        return new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())// RxJava2 适配器
                .client(httpClient)
                .build()
                .create(service);
    }
}
