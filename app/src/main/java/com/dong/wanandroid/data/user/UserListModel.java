package com.dong.wanandroid.data.user;

/**
 * Created by macmini002 on 18/6/4.
 */

public class UserListModel {
    private UserModel data;
    private int errorCode;
    private String errorMsg;

    public void setData(UserModel data) {
        this.data = data;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public UserModel getData() {
        return data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
