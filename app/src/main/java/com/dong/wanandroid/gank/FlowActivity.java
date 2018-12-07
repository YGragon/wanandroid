package com.dong.wanandroid.gank;

import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseActivity;

import java.util.ArrayList;

import butterknife.BindView;

public class FlowActivity extends BaseActivity {

    @BindView(R.id.circle_home_bg_iv)
    ImageView circleHomeBgIv;
    @BindView(R.id.newest_tv)
    TextView newestTv;
    @BindView(R.id.newest_layout)
    LinearLayout newestLayout;
    @BindView(R.id.recommend_tv)
    TextView recommendTv;
    @BindView(R.id.recommend_layout)
    LinearLayout recommendLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private TabFragmentPagerAdapter tabFragmentPagerAdapter;

    private NewestTopicFragment newestTopicFragment;
    private RecommendTopicFragment recommendTopicFragment;

    @Override
    public int intiLayout() {
        return R.layout.activity_flow;
    }

    @Override
    public void initView() {
        fragmentArrayList.clear();
        newestTopicFragment = new NewestTopicFragment();
        recommendTopicFragment = new RecommendTopicFragment();
        fragmentArrayList.add(newestTopicFragment);
        fragmentArrayList.add(recommendTopicFragment);


    }

    @Override
    public void initData() {
        tabFragmentPagerAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), fragmentArrayList);
        viewPager.setAdapter(tabFragmentPagerAdapter);
        viewPager.setCurrentItem(0);
    }
}
