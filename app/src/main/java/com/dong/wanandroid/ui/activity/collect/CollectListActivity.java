package com.dong.wanandroid.ui.activity.collect;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.presenter.collect.CollectIpresenterCompl;
import com.dong.wanandroid.model.home.HomeArticleBean;
import com.dong.wanandroid.model.home.HomeArticleModel;
import com.dong.wanandroid.ui.adapter.HomeArticleAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollectListActivity extends BaseActivity implements CollectIView {
    private static final String TAG = "CollectListActivity";
    @BindView(R.id.title_tv)
    TextView mTitleTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private CollectIpresenterCompl mCollectIpresenterCompl;
    private ArrayList<HomeArticleModel> mHomeArticleModels = new ArrayList<>() ;
    private int page = 0;
    private int mCurrentCounter;
    private LinearLayoutManager mLinearLayoutManager;
    private HomeArticleAdapter mHomeArticleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_list);
        ButterKnife.bind(this);


        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mHomeArticleAdapter = new HomeArticleAdapter(R.layout.home_article_item, mHomeArticleModels);
        mRecyclerView.setAdapter(mHomeArticleAdapter);

        mCollectIpresenterCompl = new CollectIpresenterCompl(this);
        mCollectIpresenterCompl.getCollectList(this, page);


    }

    @Override
    public void showCollectResult(int resultCode, String errorMsg, final int totalCount,HomeArticleBean data) {
        if (resultCode == 0 ) {
            // 成功
            if (data.getDatas().size() >  0){
                mHomeArticleModels.clear();
                mHomeArticleModels.addAll(data.getDatas()) ;
                mHomeArticleAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                    @Override
                    public void onLoadMoreRequested() {

                        page++;
                        //成功获取更多数据
                        mRecyclerView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mCurrentCounter >= totalCount) {
                                    //数据全部加载完毕
                                    mHomeArticleAdapter.loadMoreEnd();
                                } else {
                                    //成功获取更多数据
                                    mCollectIpresenterCompl.getCollectList(CollectListActivity.this,page);
                                    mCurrentCounter = mHomeArticleAdapter.getData().size();
                                    mHomeArticleAdapter.loadMoreComplete();

                                }
                            }

                        }, 1000);

                    }
                }, mRecyclerView);
            }else {
                Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
            }

            mHomeArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    mCollectIpresenterCompl.toBrowserAc(CollectListActivity.this,
                            mHomeArticleModels.get(position).getLink(),
                            mHomeArticleModels.get(position).getId(),
                            mHomeArticleModels.get(position).getTitle(),
                            mHomeArticleModels.get(position).getAuthor());
                }
            });
            mHomeArticleAdapter.notifyDataSetChanged() ;
        } else {
            // 失败
            Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showGuideViewResult() {

    }
}