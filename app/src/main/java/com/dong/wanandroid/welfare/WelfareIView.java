package com.dong.wanandroid.welfare;

import com.dong.wanandroid.model.welfare.WelfareModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface WelfareIView {
    void showLoadingView();
    void hideLoadingView();
    void showWelfareResult(ArrayList<WelfareModel> welfareModels) ;
}
