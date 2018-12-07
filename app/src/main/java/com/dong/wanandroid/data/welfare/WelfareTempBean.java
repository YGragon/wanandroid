package com.dong.wanandroid.model.welfare;

import java.util.List;

/**
 * Created by Administrator on 2018/3/4.
 */

public class WelfareTempBean {
    public int page ;
    public int size ;
    public int position ;
    public List<WelfareModel> mWelfareModels ;

    public WelfareTempBean(int page, int size, int position, List<WelfareModel> welfareModels) {
        this.page = page;
        this.size = size;
        this.position = position;
        mWelfareModels = welfareModels;
    }
}
