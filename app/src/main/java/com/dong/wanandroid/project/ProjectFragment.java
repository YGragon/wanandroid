package com.dong.wanandroid.project;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.LinearLayout;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseFragment;
import com.dong.wanandroid.project.custom_view.CustomView01Activity;
import com.dong.wanandroid.project.custom_view.canvas_operation.CanvasOperationActivity;
import com.dong.wanandroid.widget.LinearGradientView;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends BaseFragment {


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


    @OnClick({R.id.tv_pie,R.id.tv_canvas})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_pie:
                startActivity(new Intent(getActivity(),CustomView01Activity.class));
                break;
            case R.id.tv_canvas:
                startActivity(new Intent(getActivity(),CanvasOperationActivity.class));
                break;
            default:
                break;
        }

    }
}
