package com.dong.wanandroid.search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.data.home.HomeArticleModel;
import com.dong.wanandroid.data.search.SearchModel;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import me.next.tagview.TagCloudView;

public class SearchActivity extends BaseActivity implements SearchIView, TagCloudView.OnTagClickListener {
    private static final String TAG = "SearchActivity";
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.tag_cloud_view)
    TagCloudView tagCloudView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.search_hot_key_layout)
    LinearLayout searchHotKeyLayout;
    @BindView(R.id.search_hot_key_back_iv)
    ImageView searchHotKeyBackIv;
    @BindView(R.id.search_hot_key_commit_tv)
    TextView searchHotKeyCommitTv;
    private ArrayList<HomeArticleModel> searchModels= new ArrayList<>();
    private SearchIPresenter searchIPresenter;
    private int page = 0;
    private int mCurrentCounter;
    private SearchAdapter searchAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<String> mHotKeyTags;


    @Override
    public void showHotKey(List<SearchModel> searchHotLists) {
        mHotKeyTags = new ArrayList<>();
        for (int i = 0; i < searchHotLists.size(); i++) {
            mHotKeyTags.add(searchHotLists.get(i).getName());
        }
        tagCloudView.setTags(mHotKeyTags);
        tagCloudView.setOnTagClickListener(this);
    }

    @Override
    public void showSearchResult(ArrayList<HomeArticleModel> mSearchResultModels, final int total, final String keyWold) {
        if (mSearchResultModels.size() > 0){
            Log.e(TAG, "showSearchResult: size------>"+mSearchResultModels.size());
            searchModels.clear();
            searchModels.addAll(mSearchResultModels) ;
            searchHotKeyLayout.setVisibility(View.GONE);

            searchAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    page++;
                    recyclerView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (mCurrentCounter >= total) {
                                //数据全部加载完毕
                                searchAdapter.loadMoreEnd();
                            } else {
                                //成功获取更多数据
                                searchIPresenter.getSearchResultList(SearchActivity.this,page,keyWold);
                                mCurrentCounter = searchAdapter.getData().size();
                                searchAdapter.loadMoreComplete();

                            }
                        }

                    }, 3000);
                }
            }, recyclerView);

            searchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    searchIPresenter.toBrowserAc(SearchActivity.this,searchModels.get(position).getLink());
                }
            });
            searchAdapter.notifyDataSetChanged();

        }else {
            searchHotKeyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTagClick(int position) {
        if (position == -1) {
            Log.e(TAG, "onTagClick: 点击了");
        } else {
            searchEt.setText(mHotKeyTags.get(position));
            searchIPresenter.getSearchResultList(SearchActivity.this, page, mHotKeyTags.get(position));
        }
    }

    @OnClick({R.id.search_hot_key_back_iv, R.id.search_hot_key_commit_tv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.search_hot_key_back_iv:
                finish();
                break;
            case R.id.search_hot_key_commit_tv:
                // TODO: 18/3/1 搜索的词
                searchIPresenter.getSearchResultList(this,page,searchEt.getText().toString().trim());
                break;
        }
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void initView() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        searchAdapter = new SearchAdapter(R.layout.home_article_item, searchModels);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void initData() {
        searchIPresenter = new SearchPresenterCompl(this);
        // 获取热搜的关键词
        searchIPresenter.getHotKeyList(this);

        // 利用RxBinding进行联想搜索
        RxTextView.textChanges(searchEt)
                //当你敲完字之后停下来的半秒就会执行下面语句
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(new Predicate<CharSequence>() {
                    @Override
                    public boolean test(@NonNull CharSequence charSequence) throws Exception {
                        // 过滤掉输入内容为空的情况，输入为空不请求
                        return charSequence.length() > 0;
                    }
                })
                //下面这两个都是数据转换
                //flatMap：当同时多个网络请求访问的时候，前面的网络数据会覆盖后面的网络数据
                //switchMap：当同时多个网络请求访问的时候，会以最后一个发送请求为准，前面网路数据会被最后一个覆盖
                .switchMap(new Function<CharSequence, Observable<ArrayList<HomeArticleModel>>>() {
                    @Override
                    public Observable<ArrayList<HomeArticleModel>> apply(@NonNull CharSequence charSequence) throws Exception {
                        //网络操作，获取我们需要的数据
                        Log.e(TAG, "apply: 发送数据  " );
                        searchIPresenter.getSearchResultList(SearchActivity.this, page, searchEt.getText().toString().trim());

                        return Observable.just(searchModels);
                    }
                })
                //网络请求是在子线程的
                .subscribeOn(Schedulers.io())
                //界面更新在主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<HomeArticleModel>>() {
                    @Override
                    public void accept(@NonNull List<HomeArticleModel> models) throws Exception {
                        //界面更新，这里用打印替代
                        Log.e(TAG, "apply: 接收数据  " );
                        Log.e(TAG, "accept: data----->"+models.toString() );
                    }
                });
    }
}
