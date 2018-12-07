package com.dong.wanandroid.weiget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Scroller;

public class DragViewGroup extends LinearLayout {

    private Scroller mScroller;
    private int lastX;
    private int lastY;
    private int scrollY ;

    public DragViewGroup(Context context) {
        this(context,null);
    }

    public DragViewGroup(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DragViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
//                Log.e("222", "onTouchEvent: x---->"+offsetX );
//                Log.e("222", "onTouchEvent: y---->"+offsetY );
                if (offsetY > 0){
                    ((View) getParent()).scrollBy(0, -offsetY);
                }
                scrollY = 0 ;


                break;
            case MotionEvent.ACTION_UP:

                // 手指离开时，执行滑动过程
                View viewGroup = ((View) getParent());
                //第三部 启动scroller
                // 只有 Y 轴能够滑动
                mScroller.startScroll(
                        0,
                        viewGroup.getScrollY(),
                        0,
                        -viewGroup.getScrollY());//滑动的distanceXY
                Log.e("222", "onTouchEvent:11 "+(-viewGroup.getScrollY()));
                Log.e("222", "onTouchEvent:22 "+(viewGroup.getScrollY()));
                scrollY = (-viewGroup.getScrollY()) ;
//                mScroller.startScroll(
//                        viewGroup.getScrollX(),//当前view的左上角相对于母视图的左上角的X轴偏移量
//                        viewGroup.getScrollY(),//开始的x y
//                        -viewGroup.getScrollX(),
//                        -viewGroup.getScrollY());//滑动的distanceXY
                invalidate();
                break;
        }
        return true;
    }

    private void initView() {
        //初始化mScroller
        mScroller = new Scroller(getContext());
    }

    //第二步 此方法为模板代码
    @Override
    public void computeScroll() {
        super.computeScroll();
        // 判断Scroller是否执行完毕
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(
                    mScroller.getCurrX(),
                    mScroller.getCurrY());
            // 通过重绘来不断调用computeScroll
            invalidate();
        }
    }

    public int getScrollerY(){
        return scrollY;
    }

}
