package com.dong.wanandroid.project;


import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseFragment;

import butterknife.BindView;


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
    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.layout_toolbar)
    LinearLayout layoutToolbar;

    private boolean isShow = true;

    public ProjectFragment() { }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        setTvTitleBackgroundColor(Color.TRANSPARENT);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //上滑 并且 正在显示底部栏
                if (scrollY - oldScrollY > 0 && isShow) {
                    isShow = false;
                    // 隐藏
                    layoutToolbar.animate().translationY(-layoutToolbar.getMeasuredHeight());
                    setTvTitleBackgroundColor(Color.TRANSPARENT);
                } else if (scrollY - oldScrollY < 0 && !isShow) {
                    isShow = true;
                    // 显示
                    layoutToolbar.animate().translationY(0);
                    setTvTitleBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
            }
        });
    }
    public void setTvTitleBackgroundColor(@ColorInt int color) {
        fakeStatusBar.setBackgroundColor(color);
    }
    @Override
    protected void initData() { }

    @Override
    protected void onUserVisible() { }

    @Override
    protected void onUserInvisible() { }

    @Override
    protected void onPreDestroyView() { }
}
