package com.dong.wanandroid.ui.fragment.project;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dong.wanandroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectFragment extends Fragment {


    public ProjectFragment() {
    }

    private static ProjectFragment mProjectFragment = null;
    public static ProjectFragment getInstance() {
        synchronized (ProjectFragment.class) {
            if (mProjectFragment == null) {
                mProjectFragment = new ProjectFragment();
            }
        }
        return mProjectFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project, container, false);
    }

}
