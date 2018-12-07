package com.dong.wanandroid.gank;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dong.wanandroid.R;
import com.dong.wanandroid.model.flow.FlowGankModel;
import com.dong.wanandroid.constant.ApiConstant;
import com.dong.wanandroid.http.ApiService;
import com.dong.wanandroid.http.RetrofitHelper;
import com.dong.wanandroid.browser.BrowserActivity;
import com.dong.wanandroid.gank.FlowAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewestTopicFragment extends Fragment {

    private static final String TAG = "NewestTopicFragment";
    @BindView(R.id.flow_recycler_view)
    RecyclerView flowRecyclerView;
    Unbinder unbinder;

    private FlowAdapter flowAdapter;
    private StaggeredGridLayoutManager gridLayoutManager;
    private ArrayList<FlowGankModel.ResultsEntity> flowGankModels = new ArrayList<>();

    private int mPage = 1 ;
    private int mSize = 10;

    public NewestTopicFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_newest_topic, container, false);
        unbinder = ButterKnife.bind(this, view);

        getFlowGankData(10,1) ;
        // 圈子话题列表
        gridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        flowRecyclerView.setLayoutManager(gridLayoutManager);
        flowAdapter = new FlowAdapter(flowGankModels) ;
        flowRecyclerView.setAdapter(flowAdapter);
        flowAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                flowRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mPage+=10;
                        mSize+=1;
                        getFlowGankData( mPage , mSize) ;
                        flowAdapter.loadMoreComplete();
                    }
                },300);
            }
        });
        flowAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getContext(), BrowserActivity.class);
                intent.putExtra("param_url",flowGankModels.get(position).getUrl()) ;
                intent.putExtra("param_id",flowGankModels.get(position).get_id()) ;
                intent.putExtra("param_title",flowGankModels.get(position).getDesc()) ;
                intent.putExtra("param_author",flowGankModels.get(position).getWho()) ;
                getContext().startActivity(intent);
            }
        });

        return view;
    }


    /**
     * 获取网络数据
     * @param size
     * @param page
     */
    private void getFlowGankData(int size, int page) {
        RetrofitHelper.getInstance(getContext()).create(ApiService.class, ApiConstant.BASE_URL_GANK)
                .getFlowGankList("Android", page, size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FlowGankModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull FlowGankModel flowGankModel) {
                        flowGankModels.addAll(flowGankModel.getResults()) ;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                        flowAdapter.notifyItemInserted(flowGankModels.size()-1);
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
