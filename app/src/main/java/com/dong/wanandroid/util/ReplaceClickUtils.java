package com.dong.wanandroid.util;

/**
 * 防止重复点击
 *      最好别设置在 BaseActivity，
 *      因为有些页面也是需要快速点击的，比如快速点击多个表情
 */
public class ReplaceClickUtils {
    private static final int MIN_DELAY_TIME= 1000;  // 两次点击间隔不能少于1000ms
    private static long lastClickTime;

    /**
     * 是否是快速点击
     * @return true 是快速点击，false 不是快速点击
     */
    public static boolean isFastClick() {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClickTime) >= MIN_DELAY_TIME) {
            flag = false;
        }
        lastClickTime = currentClickTime;
        return flag;
    }
}
