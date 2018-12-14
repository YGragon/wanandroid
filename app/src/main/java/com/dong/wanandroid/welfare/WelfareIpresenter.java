package com.dong.wanandroid.welfare;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface WelfareIpresenter {
    void getWelfareData(Context context, String type, int size, int page) ;
    void toBigImageAc(Context context, ImageView shareImage, String imageUrl) ;
}
