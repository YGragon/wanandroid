package com.dong.wanandroid.ui.fragment.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dong.wanandroid.R;
import com.dong.wanandroid.model.home.HomeArticleModel;
import com.dong.wanandroid.presenter.home.HomeIPresenter;
import com.dong.wanandroid.presenter.home.HomeIPresenterCompl;
import com.dong.wanandroid.ui.adapter.HomeArticleAdapter;
import com.dong.wanandroid.ui.adapter.HomeFuncGridViewAdapter;
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
public class HomeFragment extends Fragment implements HomeIView{
    private static final String TAG = "HomeFragment";
    private static HomeFragment mHomeFragment = null;

    Unbinder unbinder;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.loading_progress)
    ProgressBar loadingView;

    private ArrayList<String> funcTitles = new ArrayList<>() ;
    private ArrayList<HomeArticleModel> homeArticleModels= new ArrayList<>();

    private GridView mGridView;

    private int page = 0 ;
    private HomeArticleAdapter mHomeArticleAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private View mHeadView;
    private int mCurrentCounter;

    private HomeIPresenter homeIPresenter;
    private HomeFuncGridViewAdapter homeFuncGridViewAdapter;
    private View mBannerHeadView;
    private Banner mBanner;
    private TextView searchLayout;
    private int totalCount = 0 ;

    public HomeFragment() { }

    public static HomeFragment getInstance() {
        synchronized (HomeFragment.class) {
            if (mHomeFragment == null) {
                mHomeFragment = new HomeFragment();
            }
        }
        return mHomeFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);


        // HeadView
        mBannerHeadView = LayoutInflater.from(getContext()).inflate(R.layout.home_head_banner_layout, null);
        mBanner = mBannerHeadView.findViewById(R.id.banner);
        searchLayout = mBannerHeadView.findViewById(R.id.search_layout);
        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homeIPresenter.toSearchAc(getActivity());
            }
        });

        // HeadView
        mHeadView = LayoutInflater.from(getContext()).inflate(R.layout.home_head_func_layout, null);
        mGridView = mHeadView.findViewById(R.id.grid_view);
        homeFuncGridViewAdapter = new HomeFuncGridViewAdapter(getContext(), funcTitles);
        mGridView.setAdapter(homeFuncGridViewAdapter);



        // ItemView
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mHomeArticleAdapter = new HomeArticleAdapter(R.layout.home_article_item, homeArticleModels);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(mHomeArticleAdapter);


        homeIPresenter = new HomeIPresenterCompl(this);
        homeIPresenter.getBannerData(getActivity());
        funcTitles = homeIPresenter.getFuncData();
        homeIPresenter.getHomeArticleList(getActivity(),page);

        // add HeadView
        mHomeArticleAdapter.addHeaderView(mBannerHeadView) ;
        mHomeArticleAdapter.addHeaderView(mHeadView) ;

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
                        } else{
                            //成功获取更多数据
                            Log.e(TAG, "run: 加载更多");
                            page++;
                            homeIPresenter.getHomeArticleList(getActivity(),page);
                            mHomeArticleAdapter.loadMoreComplete();
                        }
                    }

                }, 1000);
            }
        }, recyclerView);

        mHomeArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                if (!ReplaceClickUtils.isFastClick()){
                    homeIPresenter.saveReadRecordToDb(homeArticleModels.get(position));
                    homeIPresenter.toBrowserAc(getActivity(),
                            homeArticleModels.get(position).getLink(),
                            homeArticleModels.get(position).getId(),
                            homeArticleModels.get(position).getTitle(),
                            homeArticleModels.get(position).getAuthor());
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                homeIPresenter.toBrowserAc(getActivity(),url.get(position),0,"","");
            }
        });

    }

    @Override
    public void showFuncation(ArrayList<String> funcTitles) {
        this.funcTitles = funcTitles ;
        homeFuncGridViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showHomeArticleList(final ArrayList<HomeArticleModel> mHomeArticleModels, final int totalCount) {
        homeArticleModels.clear();
        homeArticleModels.addAll(mHomeArticleModels) ;
        this.totalCount = totalCount ;
        mCurrentCounter = mHomeArticleAdapter.getData().size();
        mHomeArticleAdapter.notifyDataSetChanged();
    }

}
