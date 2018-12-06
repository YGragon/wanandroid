package com.dong.wanandroid.ui.activity.login;

import com.dong.wanandroid.model.user.UserModel;

/**
 * Created by macmini002 on 18/2/28.
 */

public interface ILoginView {
    void showLoadingView();
    void hideLoadingView();
    void loginResult(int resultCode, String msg, UserModel userModle);
}
