package com.dong.wanandroid.util;

/**
 * Created by Administrator on 2018/12/9.
 */

public class NotNullUtil {
    /**
     * 判空
     * @param reference
     * @param <T>
     * @return
     */
    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }
}
