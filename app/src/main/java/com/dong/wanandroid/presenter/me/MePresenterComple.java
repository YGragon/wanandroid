package com.dong.wanandroid.presenter.me;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.dong.wanandroid.db.DBHelper;
import com.dong.wanandroid.event_bus_model.UserEvent;
import com.dong.wanandroid.ui.activity.login.LoginActivity;
import com.dong.wanandroid.model.user.UserModel;
import com.dong.wanandroid.ui.fragment.me.MeIView;
import com.dong.wanandroid.ui.activity.collect.CollectListActivity;
import com.dong.wanandroid.ui.activity.FlowActivity;
import com.dong.wanandroid.util.LogUtils;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import okhttp3.Cookie;

public class MePresenterComple implements MeIPresenter {

    private MeIView mMeIView ;
    private List<Cookie> cookies;
    private SharedPrefsCookiePersistor mCookiePersistor;

    public MePresenterComple(MeIView meIView){
        this.mMeIView = meIView ;
        meIView.showImage();
    }


    @Override
    public void judgeShowName(Context context) {
        mCookiePersistor = new SharedPrefsCookiePersistor(context);
        cookies = mCookiePersistor.loadAll();
        if (cookies.size() > 0) {
            List<UserModel> userFromDb = DBHelper.getUserFromDb();
            if (userFromDb.size() > 0){
                mMeIView.showUserLogin(userFromDb.get(0).getUsername());
            }
        } else {
            LogUtils.eTag("222","清空数据");
            mMeIView.showUserUnLogin();
            // 清空数据
            DBHelper.clearReadRecordFromDb();
        }
    }

    @Override
    public void judgeToCollectAc(Context context) {
        LogUtils.eTag("222","size---->"+cookies.size());
        if (cookies.size() > 0) {
            context.startActivity(new Intent(context, CollectListActivity.class));
        } else {
            Toast.makeText(context, "你还未登录哟~", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void judgeToLoginAc(Context context) {
        if (cookies.size() > 0) {
            // TODO: 2018/6/3 查看用户信息、退出登录
            Toast.makeText(context, "准备退出登录", Toast.LENGTH_SHORT).show();
            // 清空缓存 cookie
            cookies.clear();
            mCookiePersistor.clear();
            UserModel userModel = new UserModel();
            userModel.setUsername("你还未登录哟");
            EventBus.getDefault().postSticky(new UserEvent(userModel));
        } else {
            context.startActivity(new Intent(context, LoginActivity.class));
        }
    }

    @Override
    public void judgeToFlowAc(Context context) {
        context.startActivity(new Intent(context, FlowActivity.class));
    }
}
