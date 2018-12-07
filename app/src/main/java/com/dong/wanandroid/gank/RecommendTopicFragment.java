package com.dong.wanandroid.gank;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dong.wanandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendTopicFragment extends Fragment {


    public RecommendTopicFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommend_topic, container, false);
    }

}
