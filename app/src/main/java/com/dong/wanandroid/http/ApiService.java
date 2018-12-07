package com.dong.wanandroid.http;

import com.dong.wanandroid.data.banner.BannerList;
import com.dong.wanandroid.data.flow.FlowGankModel;
import com.dong.wanandroid.data.home.HomeArticleList;
import com.dong.wanandroid.data.user.UserListModel;
import com.dong.wanandroid.data.welfare.WelfareList;
import com.dong.wanandroid.data.search.SearchHotList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/9/17.
 */

public interface ApiService {

    @GET("banner/json")
    Observable<BannerList> getBanner() ;
    @POST("user/login")
    Observable<UserListModel> userLogin(@Query("username") String username,
                                        @Query("password") String password) ;
    @POST("user/register")
    Observable<UserListModel> userRegister(@Query("username") String username,
                                       @Query("password") String password, @Query("repassword") String repassword) ;

    @GET("article/list/{page}/json")
    Observable<HomeArticleList> getHomeArticleList(@Path("page") int page) ;
    @GET("hotkey/json")
    Observable<SearchHotList> getHotKeyList() ;
    @POST("article/query/{page}/json")
    Observable<HomeArticleList> getSearchResultList(@Path("page") int page,
                                                    @Query("k") String keyWord) ;
    @GET("api/data/{type}/{size}/{page}")
    Observable<WelfareList> getWelfareList(@Path("type") String welfare,
                                           @Path("size") int size, @Path("page") int page) ;
    @GET("api/data/{type}/{size}/{page}")
    Observable<FlowGankModel> getFlowGankList(@Path("type") String welfare,
                                             @Path("size") int size, @Path("page") int page) ;
    // 收藏，站内收藏type==1165,type为文章id，站外收藏type==add，需要剩下的三个参数
    @POST("lg/collect/{type}/json")
    Observable<HomeArticleList> collectPost(@Path("type") String type,
                                             @Query("title") String title, @Query("author") String author, @Query("link") String link ) ;
    // 收藏列表
    @GET("lg/collect/list/{page}/json")
    Observable<HomeArticleList> getCollectList(@Path("page") int page) ;

//    // open_eye
//    @GET("api/v4/tabs/selected")
//    Observable<OpenEyeBean> getOpenEyeByRx() ;
//
//    // one
//    @GET("api/{category}/{id}/0")
//    Observable<OneBean> getOneList(@Path("category") String category, @Path("id") String id) ;
//
//    @GET("api/onelist/idlist")
//    Observable<OneIdListBean> getOneIdList() ;
//
//    //  Joker
//    @GET("neihan/service/tabs/")
//    Observable<JokerTabBean> getJokerTab() ;
//
//
//    @GET("neihan/stream/mix/v1/")
//    Observable<JokerBean> getJoker(@Query("content_type") int id) ;
//
//
//    // 头条新闻
//    @GET("toutiao/index")
//    Observable<NewsResult> getResult(@QueryMap HashMap<String, Object> map);
//
//    // 图片下载
//    @GET
//    Observable<ResponseBody> downloadPicFromNet(@Url String url);
}
