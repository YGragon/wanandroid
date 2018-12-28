package com.dong.wanandroid.project;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseFragment;
import com.dong.wanandroid.project.custom_view.CustomView01Activity;
import com.dong.wanandroid.widget.LinearGradientView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends BaseFragment {


    @BindView(R.id.my_collect_layout)
    LinearLayout myCollectLayout;
    @BindView(R.id.my_read_record_layout)
    LinearLayout myReadRecordLayout;
    @BindView(R.id.gank_layout)
    LinearLayout gankLayout;
    @BindView(R.id.tv_project_toolbar)
    TextView tvProjectToolbar;
    @BindView(R.id.tv_pie)
    TextView tvPie;
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.layout_toolbar)
    LinearLayout layoutToolbar;
    @BindView(R.id.direction1)
    LinearGradientView linearGradientView1;
    @BindView(R.id.direction2)
    LinearGradientView linearGradientView2;

    private boolean isShow = true;

    public ProjectFragment() {
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        linearGradientView1.setDirection(0);
        linearGradientView2.setDirection(1);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //上滑 并且 正在显示底部栏
                if (scrollY - oldScrollY > 0 && isShow) {
                    isShow = false;
                    // 隐藏
                    layoutToolbar.animate().translationY(-layoutToolbar.getMeasuredHeight());
                } else if (scrollY - oldScrollY < 0 && !isShow) {
                    isShow = true;
                    // 显示
                    layoutToolbar.animate().translationY(0);
                }
            }
        });
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {
    }

    @Override
    protected void onPreDestroyView() {
    }


    @OnClick(R.id.tv_pie)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(),CustomView01Activity.class));
    }
}
