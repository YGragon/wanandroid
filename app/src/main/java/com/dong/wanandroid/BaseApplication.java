package com.dong.wanandroid;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dong.wanandroid.greendao.gen.DaoMaster;
import com.dong.wanandroid.greendao.gen.DaoSession;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * Created by macmini002 on 18/2/27.
 */

public class BaseApplication extends Application {
    private static final String TAG = "BaseApplication";
    private static DaoSession daoSession;
    private static Context mContext ;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext() ;
        //配置数据库
        setupDatabase();
        // 侧滑关闭初始化
        BGASwipeBackHelper.init(this, null);

    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "androidwan.db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    /**
     * 获取全局的Context
     * @return
     */

    public static Context getAppContext() {
        return mContext;
    }
}
