package com.dong.wanandroid;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dong.wanandroid.greendao.gen.DaoMaster;
import com.dong.wanandroid.greendao.gen.DaoSession;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

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
        // 工具包
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "3d63a6dc93ca8f71fa31dfc6c219243d");
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                Log.e(TAG, "onSuccess: "+deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "onFailure: s---->"+s);
                Log.e(TAG, "onFailure: s1---->"+s1);
            }
        });

        // 分享
        UMShareAPI.get(this);
        {

            PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
            PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
            PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        }
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
