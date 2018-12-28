package com.dong.wanandroid.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dong.wanandroid.R;

public class LinearGradientView extends View {

    private int mDiraction = 0;
    private Paint paint;

    public LinearGradientView(Context context) {
        this(context,null);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //获取View的宽高
        int width = getWidth();
        int height = getHeight();

        int colorStart = getResources().getColor(R.color.colorPrimary);
        int colorEnd = getResources().getColor(R.color.colorAccent);


        LinearGradient backGradient = null;
        if (mDiraction == 0){
            backGradient = new LinearGradient(0, 0, 0, height, new int[]{colorStart ,colorEnd}, null, Shader.TileMode.REPEAT);
        }else {
            backGradient =new LinearGradient(0, 0, width, height, colorStart ,colorEnd, Shader.TileMode.CLAMP);
        }

        paint.setShader(backGradient);
        canvas.drawRect(0, 0, width, height, paint);
    }

    /**
     * 设置渐变方向
     * @param direction 0 表示 上下渐变，1 表示 45°渐变
     */
    public void setDirection(int direction){
        mDiraction = direction;
    }
}
