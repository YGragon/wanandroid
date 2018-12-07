package com.dong.wanandroid.data.search;

import java.util.List;

/**
 * Created by macmini002 on 18/3/1.
 */

public class SearchHotList {

    private int errorCode;
    private String errorMsg;
    private List<SearchModel> data;

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setData(List<SearchModel> data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public List<SearchModel> getData() {
        return data;
    }

}
