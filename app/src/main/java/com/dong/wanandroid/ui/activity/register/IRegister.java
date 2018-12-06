package com.dong.wanandroid.ui.activity.register;

import com.dong.wanandroid.model.user.UserModel;

/**
 * Created by macmini002 on 18/2/28.
 */

public interface IRegister {
    void registerResult(int resultCode, String msg, UserModel userModle);
}
