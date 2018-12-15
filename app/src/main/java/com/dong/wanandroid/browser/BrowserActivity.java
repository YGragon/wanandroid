package com.dong.wanandroid.browser;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.LinearLayout;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.util.ShareUtil;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;
import butterknife.OnClick;


public class BrowserActivity extends BaseActivity{
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

    @Override
    public int intiLayout() {
        return R.layout.activity_browser;
    }

    @Override
    public void initView() {}

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


        AgentWeb.with(this)//传入Activity
                .setAgentWebParent(mLinearlayout, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()// 使用默认进度条
                .createAgentWeb()//
                .ready()
                .go(mUrl);

    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        ShareUtil.getInstance(this).shareUrl(null,null,mUrl,mTitle,mAuthor);
    }
}
