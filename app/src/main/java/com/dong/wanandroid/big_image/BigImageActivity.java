package com.dong.wanandroid.ui.activity.big_image;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.model.welfare.WelfareModel;
import com.dong.wanandroid.presenter.bigimage.BigImageIPresent;
import com.dong.wanandroid.presenter.bigimage.BigImageIpresentCompl;
import com.dong.wanandroid.ui.adapter.BigImageViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BigImageActivity extends BaseActivity implements BigImageIView{
    private static final String TAG = "BigImageActivity";
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private BigImageIPresent mBigImageIPresent;
    private List<String> mImagesList= new ArrayList<>();
    private List<WelfareModel> mWelfareModels= new ArrayList<>();
    private BigImageViewPagerAdapter mAdapter;

    private int curPage ;
    private int preState ;
    private int mPage;
    private int mSize;


    @Override
    public void showWelfareResult(ArrayList<WelfareModel> welfareModels, int size, int page) {
        mWelfareModels.addAll(welfareModels) ;
        mPage = page ;
        mSize = size ;
    }

    @Override
    public void showWelfareImage(ArrayList<String> images) {
        mImagesList.addAll(images) ;
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public int intiLayout() {
        return R.layout.activity_big_image;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mImagesList = intent.getStringArrayListExtra("images");
        int position = intent.getIntExtra("position",0);
        mPage = intent.getIntExtra("page",0);
        mSize = intent.getIntExtra("size",0);

        mBigImageIPresent = new BigImageIpresentCompl(this);

        mAdapter = new BigImageViewPagerAdapter(this,mImagesList);
        mViewPager.setAdapter(mAdapter);

        mViewPager.setCurrentItem(position);

        //监听ViewPager的跳转状态，当跳转到最后一页时，执行jumpToNext()方法
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * 在屏幕滚动过程中不断被调用
             * @param position
             * @param positionOffset   是当前页面滑动比例，如果页面向右翻动，这个值不断变大，最后在趋近1的情况后突变为0。如果页面向左翻动，这个值不断变小，最后变为0
             * @param positionOffsetPixels   是当前页面滑动像素，变化情况和positionOffset一致
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                curPage = position;
            }

            /**
             * 这个方法有一个参数position，代表哪个页面被选中
             * @param position    当前页的索引
             */
            @Override
            public void onPageSelected(int position) {

            }

            /**
             * 在手指操作屏幕的时候发生变化
             * @param state   有三个值：0（END）,1(PRESS) , 2(UP) 。
             */
            @Override
            public void onPageScrollStateChanged(int state) {
//              如果要想在最后一页往右滑动时响应动作
                if (preState == 1 && state == 0 && curPage == mImagesList.size()-1) {
                    mPage++ ;
                    mBigImageIPresent.getBigImageUrl(BigImageActivity.this,"福利",mSize,mPage);
                    Toast.makeText(BigImageActivity.this, "请求下一页数据", Toast.LENGTH_SHORT).show();
                }
//              如果要在首页往左滑动时响应动作可以如下写
                if (preState == 1 && state == 0 && curPage == 0) {
                    Toast.makeText(BigImageActivity.this, "请求上一页数据", Toast.LENGTH_SHORT).show();
                }
                preState = state;
            }
        });
    }
}
