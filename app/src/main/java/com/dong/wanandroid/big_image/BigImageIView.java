package com.dong.wanandroid.big_image;

import com.dong.wanandroid.data.welfare.WelfareModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/3/1.
 */

public interface BigImageIView {

    void showWelfareResult(ArrayList<WelfareModel> welfareModels, int size, int page);

    void showWelfareImage(ArrayList<String> images);
}
