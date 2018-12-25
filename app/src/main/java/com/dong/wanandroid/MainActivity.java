package com.dong.wanandroid;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.home.HomeFragment;
import com.dong.wanandroid.me.MeFragment;
import com.dong.wanandroid.project.ProjectFragment;
import com.dong.wanandroid.weiget.NoScrollHorizontally;
import com.dong.wanandroid.welfare.WelfareFragment;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.view_pager)
    NoScrollHorizontally mViewPager;
    @BindView(R.id.bottomBar)
    BottomNavigationView bottomBar;


    private List<Fragment> fragmentList = new ArrayList<>();


    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        fragmentList.add(new HomeFragment());
        fragmentList.add(new WelfareFragment());
        fragmentList.add(new ProjectFragment());
        fragmentList.add(new MeFragment());

        BottomAdapter bottomAdapter = new BottomAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(bottomAdapter);
        mViewPager.setOffscreenPageLimit(fragmentList.size());
    }

    @Override
    public void initData() {
        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.tab_home:
                        mViewPager.setCurrentItem(0);
                        return true;
                    case R.id.tab_welfare:
                        mViewPager.setCurrentItem(1);
                        return true;
                    case R.id.tab_gank:
                        mViewPager.setCurrentItem(2);
                        return true;
                    case R.id.tab_me:
                        mViewPager.setCurrentItem(3);
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });

        /**
         * 控制不让左右滑动
         */
        mViewPager.setScanScroll(false);
    }


    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }
}
