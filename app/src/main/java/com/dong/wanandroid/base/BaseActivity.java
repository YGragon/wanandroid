package com.dong.wanandroid.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;

import com.umeng.message.PushAgent;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static anet.channel.util.Utils.context;

/**
 * Created by Administrator on 2018/3/10.
 */

public class BaseActivity extends SwipeBackActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        PushAgent.getInstance(context).onAppStart();
//        custom you swipeBackLayout
//        SwipeBackLayout swipeBackLayout = getSwipeBackLayout();
//        swipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_RIGHT);
    }
}
