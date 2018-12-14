package com.dong.wanandroid.big_image;

import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.data.AppConfig;

import butterknife.BindView;
import butterknife.OnClick;

public class BigImageActivity extends BaseActivity {
    private static final String TAG = "BigImageActivity";
    @BindView(R.id.iv_big_image)
    ImageView mPhotoView;

    @Override
    public int intiLayout() {
        return R.layout.activity_big_image;
    }

    @Override
    public void initView() {}

    @Override
    public void initData() {
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(AppConfig.IMAGE_URL);
        Glide.with(this).load(imageUrl).into(mPhotoView);
    }


    @OnClick(R.id.iv_big_image)
    public void onViewClicked() {
        finishAfterTransition();

    }
}
