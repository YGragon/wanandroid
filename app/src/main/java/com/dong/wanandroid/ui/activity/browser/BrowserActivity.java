package com.dong.wanandroid.ui.activity.browser;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dong.wanandroid.R;
import com.dong.wanandroid.base.BaseActivity;
import com.dong.wanandroid.presenter.collect.CollectIpresenterCompl;
import com.dong.wanandroid.ui.activity.collect.CollectIView;
import com.dong.wanandroid.constant.ApiConstant;
import com.dong.wanandroid.model.home.HomeArticleBean;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.just.agentweb.AgentWeb;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;

import okhttp3.Cookie;


public class BrowserActivity extends BaseActivity implements CollectIView {
    private static final String TAG = "BrowserActivity";
    public final static String PARAM_URL = "param_url";
    public final static String PARAM_TITLE = "param_title";
    public final static String PARAM_ID = "param_id";
    public final static String PARAM_AUTHOR = "param_author";
    private LinearLayout mLinearLayout;
    private TextView mShareTv;
    private FloatingActionButton mFab;
    private String mUrl;
    private int mId;
    private String mTitle;
    private String mAuthor;
    private CollectIpresenterCompl mCollectIpresenterCompl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mUrl = intent.getStringExtra(PARAM_URL);
        mTitle = intent.getStringExtra(PARAM_TITLE);
        mId = intent.getIntExtra(PARAM_ID,0);
        mAuthor = intent.getStringExtra(PARAM_AUTHOR);

        if (TextUtils.isEmpty(mUrl) ) {
            finish();
            return;
        }
        setContentView(R.layout.activity_browser);
        mLinearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        mShareTv = (TextView) findViewById(R.id.browser_share_tv);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        mCollectIpresenterCompl = new CollectIpresenterCompl(this);
        mCollectIpresenterCompl.showGuideView(this,mShareTv,mFab);

        AgentWeb.with(this)//传入Activity
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//传入AgentWeb 的父控件 ，如果父控件为 RelativeLayout ， 那么第二参数需要传入 RelativeLayout.LayoutParams
                .useDefaultIndicator()// 使用默认进度条
                .createAgentWeb()//
                .ready()
                .go(mUrl);

        mShareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT>=23){
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(BrowserActivity.this,mPermissionList,123);
                }else {
                    showShareContent() ;
                }
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 2018/3/18 判断cookie是否过期
                SharedPrefsCookiePersistor cookiePersistor = new SharedPrefsCookiePersistor(BrowserActivity.this);
                List<Cookie> cookies = cookiePersistor.loadAll();

                if (cookies.size() > 0){
                    // 存在cookie
                    if (mUrl.contains(ApiConstant.BASE_URL_WAN_ANDROID)){
                        Log.e(TAG, "onClick: 站内文章" );
                        mCollectIpresenterCompl.collectPostInSite(BrowserActivity.this,mId,mTitle,mAuthor,mUrl) ;
                    }else {
                        Log.e(TAG, "onClick: 站外文章" );
                        mCollectIpresenterCompl.collectPostOutSite(BrowserActivity.this,mTitle,mAuthor,mUrl) ;
                    }
                }else {
                    Toast.makeText(BrowserActivity.this, "你还未登录哟~", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(BrowserActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(BrowserActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(BrowserActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };
    private void showShareContent() {
        Log.e(TAG, "showShareContent: 打开分享面板");
        new ShareAction(BrowserActivity.this).withText("hello")
                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener).open();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        showShareContent() ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void showCollectResult(int resultCode, String errorMsg, int total, HomeArticleBean data) {
        if (resultCode == 0){
            // 成功
            Toast.makeText(BrowserActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
        }else {
            // 失败
            Toast.makeText(BrowserActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showGuideViewResult() {
        Toast.makeText(this, "你很棒棒哟~", Toast.LENGTH_SHORT).show();
    }
}
