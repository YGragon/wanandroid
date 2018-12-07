package com.dong.wanandroid.ui.fragment.welfare;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dong.wanandroid.R;
import com.dong.wanandroid.constant.ApiParamConstant;
import com.dong.wanandroid.model.welfare.WelfareModel;
import com.dong.wanandroid.presenter.welfare.WelfareIpresenter;
import com.dong.wanandroid.presenter.welfare.WelfareIpresenterCompl;
import com.dong.wanandroid.util.tool.GridSpacingItemDecoration;
import com.dong.wanandroid.ui.adapter.WelfareAdapter;
import com.dong.wanandroid.util.ReplaceClickUtils;
import com.scwang.smartrefresh.layout.util.DensityUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class WelfareFragment extends Fragment implements WelfareIView {

    private static final String TAG = "WelfareFragment";
    private static WelfareFragment sMWelfareFragment = null;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.loading_progress)
    ProgressBar loadingView;
    Unbinder unbinder;

    private WelfareAdapter mWelfareAdapter;
    private GridLayoutManager mGridLayoutManager;
    private int page = 1 ;
    private int size = 10 ;
    private ArrayList<WelfareModel> mWelfareModels= new ArrayList<>();
    private WelfareIpresenter mWelfareIpresenter;

    public WelfareFragment() {
    }

    public static WelfareFragment getInstance() {
        synchronized (WelfareFragment.class) {
            if (sMWelfareFragment == null) {
                sMWelfareFragment = new WelfareFragment();
            }
        }
        return sMWelfareFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_welfare, container, false);
        unbinder = ButterKnife.bind(this, view);

        mWelfareIpresenter = new WelfareIpresenterCompl(this);
        mWelfareIpresenter.getWelfareData(getActivity(), ApiParamConstant.WELFARE,size,page);

        // ItemView
        mGridLayoutManager = new GridLayoutManager(getContext(),2);
        mWelfareAdapter = new WelfareAdapter(R.layout.welfare_item, mWelfareModels);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, DensityUtil.dp2px(9),true));
        mRecyclerView.setAdapter(mWelfareAdapter);

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
        loadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showWelfareResult(ArrayList<WelfareModel> welfareModelArrayList) {
        mWelfareModels.addAll(welfareModelArrayList) ;
        mWelfareAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                //成功获取更多数据
                mWelfareIpresenter.getWelfareData(getActivity(), ApiParamConstant.WELFARE ,size,page);
                mWelfareAdapter.loadMoreComplete();

            }
        }, mRecyclerView);

        mWelfareAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (!ReplaceClickUtils.isFastClick()){
                    mWelfareIpresenter.toBigImageAc(getActivity(),position,mWelfareModels);
                }
            }
        });
        mWelfareAdapter.notifyDataSetChanged();
    }

}
