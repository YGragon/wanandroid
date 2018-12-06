package com.dong.wanandroid.base;

/**
 * Created by macmini002 on 18/3/1.
 * 解决 presenter 中空指针异常
 */

public abstract class BasePresenter <T> {
    /**
     * 绑定的view
     */
    public T mView;
    public void attach(T mView) {
        this.mView = mView;
    }

    public void dettach() {
        mView = null;
    }
    /**
     * 是否与View建立连接
     * 每次调用业务请求的时候都要出先调用方法检查是否与View建立连接
     */
    public boolean isViewAttached(){
        return mView != null;
    }
    /**
     * 获取连接的view
     */
    public T getView(){
        return mView;
    }
}
