package com.dong.wanandroid.big_image;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dong.wanandroid.R;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class BigImageViewPagerAdapter extends PagerAdapter {
    private Context mContext ;
    private List<String> mImages ;
    public BigImageViewPagerAdapter(Context context,  List<String> images) {
        this.mContext = context ;
        this.mImages = images ;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    @Override
    public int getCount() {
        return mImages.size() > 0 ? mImages.size() : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final View view = View.inflate(mContext, R.layout.big_image_item, null);
        final ImageView bigImage = view.findViewById(R.id.big_image_item_iv);
        Glide.with(mContext).load(mImages.get(position)).into(bigImage) ;

        container.addView(view);
        return view ;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
