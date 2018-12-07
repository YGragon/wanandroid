package com.dong.wanandroid.register;

import com.dong.wanandroid.data.user.UserModel;

/**
 * Created by macmini002 on 18/2/28.
 */

public interface IRegister {
    void registerResult(int resultCode, String msg, UserModel userModle);
}
