package com.dong.wanandroid;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.ui.fragment.home.HomeFragment;
import com.dong.wanandroid.ui.fragment.me.MeFragment;
import com.dong.wanandroid.ui.fragment.project.ProjectFragment;
import com.dong.wanandroid.ui.fragment.welfare.WelfareFragment;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @BindView(R.id.contentContainer)
    FrameLayout contentContainer;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;

    //之前显示的fragment
    private HomeFragment homeFragment;
    private WelfareFragment mWelfareFragment;
    private ProjectFragment projectFragment;
    private MeFragment meFragment;
    private Fragment mContent;


    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        homeFragment = HomeFragment.getInstance();
        mWelfareFragment = WelfareFragment.getInstance();
        projectFragment = ProjectFragment.getInstance();
        meFragment = MeFragment.getInstance();
        setDefaultFragment(homeFragment);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        switchContent(homeFragment);
                        break;
                    case R.id.tab_welfare:
                        switchContent(mWelfareFragment);
                        break;
                    case R.id.tab_gank:
                        switchContent(projectFragment);
                        break;
                    case R.id.tab_me:
                        switchContent(meFragment);
                        break;
                    default:
                        break;
                }
            }
        });
    }
    public void setDefaultFragment(Fragment fragment) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.add(R.id.contentContainer, fragment).commit();
        mContent = fragment;
    }
    //切换fragment的显示隐藏
    public void switchContent(Fragment to) {
        if (mContent != to) {
            fm = getSupportFragmentManager();
            ft = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                ft.hide(mContent).add(R.id.contentContainer, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
            } else {
                ft.hide(mContent).show(to).commit(); // 隐藏当前的fragment，显示下一个
            }
            mContent = to;
        }
    }
}
