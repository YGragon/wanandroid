package com.dong.wanandroid.util.tool;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dong.wanandroid.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by macmini002 on 18/2/27.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context).load(path).error(R.mipmap.ic_launcher).into(imageView);

    }
}
