package com.dong.wanandroid.project.custom_view.canvas_operation;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasOperationView extends View {

    private Paint mPaint;
    private Paint mCirclePaint;
    private Paint mRectFPaint;
    private int mWidth;
    private int mHeight;

    public CanvasOperationView(Context context) {
        this(context,null);
    }

    public CanvasOperationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CanvasOperationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mCirclePaint = new Paint();
        mRectFPaint = new Paint();
        // 在坐标原点绘制一个黑色圆形
        mPaint.setColor(Color.BLACK);
        mCirclePaint.setColor(Color.BLACK);
        mRectFPaint.setColor(Color.BLACK);

        mPaint.setAntiAlias(true);
        mCirclePaint.setAntiAlias(true);
        mRectFPaint.setAntiAlias(true);

        mCirclePaint.setStyle(Paint.Style.STROKE);
        mRectFPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        // translate
        mPaint.setColor(Color.BLACK);
        canvas.translate(100, 100);
        canvas.drawCircle(0,0,50,mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.translate(100,100);
        canvas.drawCircle(0,0,50,mPaint);
        canvas.restore();

        canvas.save();
        // scale
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth/2,mHeight/2);
        RectF rect = new RectF(-400,-400,400,400);   // 矩形区域
        for (int i=0; i<=20; i++)
        {
            canvas.scale(0.9f,0.9f);
            canvas.drawRect(rect,mRectFPaint);
        }

        canvas.restore();

        canvas.save();
        // rotate
        // 将坐标系原点移动到画布正中心
        canvas.translate(mWidth/2,mHeight/2);
        canvas.drawCircle(0,0,400,mCirclePaint);          // 绘制两个圆形
        canvas.drawCircle(0,0,380,mCirclePaint);

        for (int i=0; i<=360; i+=10){               // 绘制圆形之间的连接线
            canvas.drawLine(0,380,0,400,mCirclePaint);
            canvas.rotate(10);
        }
        canvas.restore();



    }
}
