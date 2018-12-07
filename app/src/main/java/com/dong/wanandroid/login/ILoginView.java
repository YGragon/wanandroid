package com.dong.wanandroid.login;

import com.dong.wanandroid.data.user.UserModel;

/**
 * Created by macmini002 on 18/2/28.
 */

public interface ILoginView {
    void showLoadingView();
    void hideLoadingView();
    void loginResult(int resultCode, String msg, UserModel userModle);
}
