package com.dong.wanandroid.ui.weiget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.dong.wanandroid.R;
import com.sina.weibo.sdk.utils.LogUtil;

/**
 * Created by macmini002 on 18/6/7.
 */

public class DropZoomRecyclerView extends RecyclerView {
    private ImageView imageView;
    private int mMeasureWeight;
    public DropZoomRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMeasureWeight = context.getResources().getDimensionPixelSize(R.dimen.dp200);
    }
    public DropZoomRecyclerView(Context context) {
        super(context);
    }
    public void setZoomImageView(ImageView iView ){
        imageView = iView;

    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if(deltaY < 0){//下拉过度  对图片进行放大
            imageView.getLayoutParams().height = imageView.getHeight() - deltaY;
            imageView.requestLayout();
        }else{//上拉过度时
            imageView.getLayoutParams().height = imageView.getHeight() - deltaY;
            imageView.requestLayout();
        }
        LogUtil.i("yuyahao","deltaY:  "+deltaY);
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //只有当imageView过度放大时，这里才会去执行缩小
        View view = (View) imageView.getParent();
        int detalY = view.getTop();//此时 detay 为负值
        if(imageView.getHeight() > mMeasureWeight){//如果当前图片的高度 > 初始高度
            imageView.getLayoutParams().height = imageView.getHeight() + detalY;
            imageView.requestLayout();
        }
        LogUtil.i("yuyahao","----   deltaY:  "+detalY);
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_UP://松手释放时
                MyCustomserAnimation myCustomserAnimation = new MyCustomserAnimation(mMeasureWeight);
                myCustomserAnimation.setDuration(300);
                myCustomserAnimation.setInterpolator(new BounceInterpolator());
                imageView.startAnimation(myCustomserAnimation);
                break;
        }
        return super.onTouchEvent(ev);
    }
    public class MyCustomserAnimation extends Animation {
        private int delay ;//高度差
        private int currentHeight ; //当前的高度
        public MyCustomserAnimation( int targetHeight){
            //this.delay = delay;
            delay = imageView.getHeight()  - targetHeight;
            currentHeight =  imageView.getHeight();
        }
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            imageView.getLayoutParams().height = (int) (currentHeight  - delay * interpolatedTime);
            imageView.requestLayout();
            super.applyTransformation(interpolatedTime, t);
        }
    }
}
