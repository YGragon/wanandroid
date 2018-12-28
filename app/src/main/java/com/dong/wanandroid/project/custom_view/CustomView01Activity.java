package com.dong.wanandroid.project.custom_view;

import android.os.Bundle;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.project.custom_view.pie.PieData;
import com.dong.wanandroid.project.custom_view.pie.PieView;

import java.util.ArrayList;

import butterknife.BindView;

public class CustomView01Activity extends BaseActivity {


    @BindView(R.id.pie_view)
    PieView pieView;

    @Override
    public int intiLayout() {
        return R.layout.activity_custom_view01;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ArrayList<PieData> pieDatas = new ArrayList<>();
        pieDatas.add(new PieData("大豆",20,20,R.color.colorPrimary,20)) ;
        pieDatas.add(new PieData("x小米",10,10,R.color.colorAccent,10)) ;
        pieDatas.add(new PieData("大米",30,30,R.color.colorPrimary,30)) ;
        pieDatas.add(new PieData("小广告",40,40,R.color.colorPrimary,40)) ;
        pieView.setDatas(pieDatas);
    }

}
