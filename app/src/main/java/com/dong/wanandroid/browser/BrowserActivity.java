package com.dong.wanandroid.ui.activity.browser;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.constant.ApiConstant;
import com.dong.wanandroid.model.home.HomeArticleBean;
import com.dong.wanandroid.presenter.collect.CollectIpresenterCompl;
import com.dong.wanandroid.ui.activity.collect.CollectIView;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.just.agentweb.AgentWeb;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Cookie;


public class BrowserActivity extends BaseActivity implements CollectIView {
    private static final String TAG = "BrowserActivity";
    public final static String PARAM_URL = "param_url";
    public final static String PARAM_TITLE = "param_title";
    public final static String PARAM_ID = "param_id";
    public final static String PARAM_AUTHOR = "param_author";

    @BindView(R.id.linearlayout)
    LinearLayout mLinearlayout;

    private String mUrl;
    private int mId;
    private String mTitle;
    private String mAuthor;
    private CollectIpresenterCompl mCollectIpresenterCompl;


    @Override
    public void showCollectResult(int resultCode, String errorMsg, int total, HomeArticleBean data) {
        if (resultCode == 0) {
            // 成功
            Toast.makeText(BrowserActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
        } else {
            // 失败
            Toast.makeText(BrowserActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showGuideViewResult() {
        Toast.makeText(this, "你很棒棒哟~", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int intiLayout() {
        return R.layout.activity_browser;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(PARAM_URL);
        mTitle = intent.getStringExtra(PARAM_TITLE);
        mId = intent.getIntExtra(PARAM_ID, 0);
        mAuthor = intent.getStringExtra(PARAM_AUTHOR);

        if (TextUtils.isEmpty(mUrl)) {
            finish();
            return;
        }

        mCollectIpresenterCompl = new CollectIpresenterCompl(this);

        AgentWeb.with(this)//传入Activity
                .setAgentWebParent(mLinearlayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .createAgentWeb()//
                .ready()
                .go(mUrl);

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        // TODO: 2018/3/18 判断cookie是否过期
        SharedPrefsCookiePersistor cookiePersistor = new SharedPrefsCookiePersistor(BrowserActivity.this);
        List<Cookie> cookies = cookiePersistor.loadAll();

        if (cookies.size() > 0) {
            // 存在cookie
            if (mUrl.contains(ApiConstant.BASE_URL_WAN_ANDROID)) {
                Log.e(TAG, "onClick: 站内文章");
                mCollectIpresenterCompl.collectPostInSite(BrowserActivity.this, mId, mTitle, mAuthor, mUrl);
            } else {
                Log.e(TAG, "onClick: 站外文章");
                mCollectIpresenterCompl.collectPostOutSite(BrowserActivity.this, mTitle, mAuthor, mUrl);
            }
        } else {
            Toast.makeText(BrowserActivity.this, "你还未登录哟~", Toast.LENGTH_SHORT).show();
        }
    }
}
