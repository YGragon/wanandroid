package com.dong.wanandroid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BottomAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    public BottomAdapter(FragmentManager fm, List<Fragment> fragmentLists) {
        super(fm);
        this.fragments = fragmentLists;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}