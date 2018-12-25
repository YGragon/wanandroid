package com.dong.wanandroid.weiget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollHorizontally extends ViewPager {

    private boolean isCanScroll = true;

    public NoScrollHorizontally(Context context) {
        super(context);
    }

    public NoScrollHorizontally(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return  false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }


    public void setScanScroll(boolean isCanScroll){

        this.isCanScroll = isCanScroll;

    }

}
