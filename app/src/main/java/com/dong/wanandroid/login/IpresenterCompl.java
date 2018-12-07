package com.dong.wanandroid.login;

import android.content.Context;

import com.dong.wanandroid.constant.ApiConstant;
import com.dong.wanandroid.http.ApiService;
import com.dong.wanandroid.http.RetrofitHelper;
import com.dong.wanandroid.model.user.UserListModel;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by macmini002 on 18/2/28.
 */

public class IpresenterCompl implements IPresenter {
    private static final String TAG = "IpresenterCompl";
    private ILoginView iLoginView;
    private UserListModel iUser;
    private Context context ;
    public IpresenterCompl(Context context ,ILoginView iLoginView ) {
        this.iLoginView = iLoginView;
        this.context = context ;
        iUser = new UserListModel() ;
    }


    //初始化数据
    private void initUser(String username, String password) {
        iLoginView.showLoadingView();
        RetrofitHelper.getInstance(context).create(ApiService.class, ApiConstant.BASE_URL_WAN_ANDROID)
                .userLogin(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserListModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) { }

                    @Override
                    public void onNext(@NonNull UserListModel userModle) {
                        iUser = userModle;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) { }

                    @Override
                    public void onComplete() {
                        if (iUser != null){
                            //初始化数据
                            iLoginView.hideLoadingView();
                            iLoginView.loginResult(iUser.getErrorCode(),iUser.getErrorMsg(), iUser.getData());
                        }
                    }
                });


    }

    @Override
    public void login(String user, String pwd) {
        initUser(user,pwd) ;
    }
}
