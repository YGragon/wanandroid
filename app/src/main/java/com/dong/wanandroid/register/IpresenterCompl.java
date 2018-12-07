package com.dong.wanandroid.register;

import android.content.Context;
import android.util.Log;

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
    private IRegister iRegister;
    private UserListModel iUser = new UserListModel();
    private Context context ;
    public IpresenterCompl(Context context, IRegister iRegisterView) {
        this.context = context ;
        this.iRegister = iRegisterView;
    }
    //初始化数据
    private void initUser(String username, String password , String repassword) {
        RetrofitHelper.getInstance(context).create(ApiService.class, ApiConstant.BASE_URL_WAN_ANDROID)
                .userRegister(username,password,repassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserListModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {}

                    @Override
                    public void onNext(@NonNull UserListModel userModle) {
                        Log.e(TAG, "onNext: " +userModle);
                        iUser = userModle;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(TAG, "onError: "+e.toString() );
                    }

                    @Override
                    public void onComplete() {
                        //初始化数据
                        if (iUser != null){
                            iRegister.registerResult(iUser.getErrorCode(),iUser.getErrorMsg(), iUser.getData());
                        }
                        Log.e(TAG, "onComplete: " );
                    }
                });


    }

    @Override
    public void register(String user, String pwd, String repwd) {
        initUser(user, pwd , repwd) ;
    }
}
