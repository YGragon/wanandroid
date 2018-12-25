package com.dong.wanandroid.home;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseFragment;
import com.dong.wanandroid.data.home.HomeArticleModel;
import com.dong.wanandroid.data.home.HomeRepository;
import com.dong.wanandroid.util.NotNullUtil;
import com.dong.wanandroid.util.ReplaceClickUtils;
import com.dong.wanandroid.util.tool.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeContract.View {
    private static final String TAG = "HomeFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_progress)
    ProgressBar loadingView;
    @BindView(R.id.search_layout)
    TextView searchLayout;
    @BindView(R.id.banner)
    Banner mBanner;
    Unbinder unbinder;
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;

    private HomeContract.Presenter mPresenter;

    private ArrayList<HomeArticleModel> homeArticleModels = new ArrayList<>();


    private int page = 0;
    private HomeArticleAdapter mHomeArticleAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private int mCurrentCounter;
    private int totalCount = 0;

    public HomeFragment() { }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViewsAndEvents(View view) {

        // 透明顶部
        setTvTitleBackgroundColor(Color.TRANSPARENT);
        // ItemView
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mHomeArticleAdapter = new HomeArticleAdapter(R.layout.home_article_item, homeArticleModels);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mHomeArticleAdapter);

        // 设置给 presenter
        mPresenter = new HomeIPresenterCompl(new HomeRepository(), this);
    }

    @Override
    protected void initData() {
        mPresenter.getBannerData(getActivity());
        mPresenter.getHomeArticleList(getActivity(), page);

        // 加载更多
        mHomeArticleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (mCurrentCounter >= totalCount) {
                            //数据全部加载完毕
                            mHomeArticleAdapter.loadMoreEnd();
                        } else {
                            //成功获取更多数据
                            Log.e(TAG, "run: 加载更多");
                            page++;
                            mPresenter.getHomeArticleList(getActivity(), page);
                            mHomeArticleAdapter.loadMoreComplete();
                        }
                    }

                }, 1000);
            }
        }, recyclerView);

        /**
         * 点击事件
         */
        mHomeArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (!ReplaceClickUtils.isFastClick()) {
                    mPresenter.saveReadRecordToDb(homeArticleModels.get(position));
                    mPresenter.toBrowserAc(getActivity(),
                            homeArticleModels.get(position).getLink(),
                            homeArticleModels.get(position).getId(),
                            homeArticleModels.get(position).getTitle(),
                            homeArticleModels.get(position).getAuthor());
                }
            }
        });
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

    @Override
    public void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showBanner(ArrayList<String> images, final ArrayList<String> url) {
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(images);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                // banner 默认不传
                mPresenter.toBrowserAc(getActivity(), url.get(position), 0, "", "");
            }
        });

    }

    public void setTvTitleBackgroundColor(@ColorInt int color) {
        fakeStatusBar.setBackgroundColor(color);
    }

    @Override
    public void showHomeArticleList(final ArrayList<HomeArticleModel> mHomeArticleModels, final int totalCount) {
        homeArticleModels.clear();
        homeArticleModels.addAll(mHomeArticleModels);
        this.totalCount = totalCount;
        mCurrentCounter = mHomeArticleAdapter.getData().size();
        mHomeArticleAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPresenter(HomeContract.Presenter presenter) {
        mPresenter = NotNullUtil.checkNotNull(presenter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
