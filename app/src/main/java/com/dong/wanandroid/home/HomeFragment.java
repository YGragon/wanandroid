package com.dong.wanandroid.home;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseFragment;
import com.dong.wanandroid.data.home.HomeArticleModel;
import com.dong.wanandroid.util.ReplaceClickUtils;
import com.dong.wanandroid.util.tool.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment implements HomeIView{
    private static final String TAG = "HomeFragment";
    private static HomeFragment mHomeFragment = null;


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

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViewsAndEvents(View view) {
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
        // add HeadView
        mHomeArticleAdapter.addHeaderView(mBannerHeadView) ;
        mHomeArticleAdapter.addHeaderView(mHeadView) ;

    }

    @Override
    protected void initData() {
        homeIPresenter = new HomeIPresenterCompl(this);
        homeIPresenter.getBannerData(getActivity());
        funcTitles = homeIPresenter.getFuncData();
        homeIPresenter.getHomeArticleList(getActivity(),page);

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

        /**
         * 点击事件
         */
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
    }

    @Override
    protected void onUserVisible() {}

    @Override
    protected void onUserInvisible() {}

    @Override
    protected void onPreDestroyView() {}

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
