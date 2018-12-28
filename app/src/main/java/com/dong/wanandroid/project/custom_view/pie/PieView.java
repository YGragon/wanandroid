package com.dong.wanandroid.project.custom_view.pie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dong.wanandroid.util.LogUtils;

import java.util.ArrayList;

public class PieView extends View {
    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    // 饼状图初始角度值
    private float mStartAndle = 0;
    // 饼状图数据
    private ArrayList<PieData> mDatas ;
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();

    public PieView(Context context) {
        this(context,null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public PieView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
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
        if (null == mDatas){
            return;
        }else {
            drawPieArc(canvas);
        }
    }

    private void drawPieArc(Canvas canvas) {

        float mCurrentStartAngle = mStartAndle ;
        canvas.translate( mWidth / 2, mHeight / 2);
        float r = (Math.min(mWidth, mHeight) / 2 * 0.8f) ;
        RectF rectF = new RectF(-r, -r, r, r);

        for (int i = 0; i < mDatas.size(); i++) {
            PieData pieData = mDatas.get(i);
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectF,mCurrentStartAngle,pieData.getAngle(),true,mPaint);

            mCurrentStartAngle += pieData.getAngle();
        }
    }

    public void setStartAngle(int startAngle){
        this.mStartAndle = startAngle;
        invalidate();
    }

    public void setDatas(ArrayList<PieData> datas){
        this.mDatas = datas;
        initData(datas);
        invalidate();
    }

    private void initData(ArrayList<PieData> datas) {
        if (null == datas || datas.size() == 0)   // 数据有问题 直接返回
            return;

        float sumValue = 0;
        for (int i = 0; i < datas.size(); i++) {
            PieData pie = datas.get(i);

            sumValue += pie.getValue();       //计算数值和

            int j = i % mColors.length;       //设置颜色
            pie.setColor(mColors[j]);
        }

        float sumAngle = 0;
        for (int i = 0; i < datas.size(); i++) {
            PieData pie = datas.get(i);

            float percentage = pie.getValue() / sumValue;   // 百分比
            float angle = percentage * 360;                 // 对应的角度

            pie.setPercentage(percentage);                  // 记录百分比
            pie.setAngle(angle);                            // 记录角度大小
            sumAngle += angle;

            LogUtils.e( pie.getAngle());
        }
    }
}
