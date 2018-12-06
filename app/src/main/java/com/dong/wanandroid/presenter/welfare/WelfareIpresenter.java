package com.dong.wanandroid.presenter.welfare;

import android.content.Context;

import com.dong.wanandroid.model.welfare.WelfareModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface WelfareIpresenter {
    void getWelfareData(Context context, String type, int size, int page) ;
    void toBigImageAc(Context context, int position, ArrayList<WelfareModel> mWelfareModels) ;
}
