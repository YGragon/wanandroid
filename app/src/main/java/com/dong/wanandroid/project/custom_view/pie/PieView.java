package com.dong.wanandroid.project.custom_view.pie;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.dong.wanandroid.util.LogUtils;

import java.util.ArrayList;

public class PieView extends View {
    private final String TAG = PieView.class.getSimpleName();
    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080, 0xFFE6B800, 0xFF7CFC00};
    // 饼状图初始角度值
    private float mStartAndle = 0;
    // 饼状图数据
    private ArrayList<PieData> mDatas ;
    // 宽高
    private int mWidth, mHeight;
    // 扇形画笔
    private Paint mPaint = new Paint();
    // 文本画笔
    private Paint mTextPaint = new Paint();
    // 半径
    private float r;
    // 圆心坐标
    private int centerx, centery;

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

        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(38);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        centerx = mWidth / 2;
        centery = mHeight / 2;
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
        r = (Math.min(mWidth, mHeight) / 2 * 0.8f);
        RectF rectF = new RectF(-r, -r, r, r);

        for (int i = 0; i < mDatas.size(); i++) {
            PieData pieData = mDatas.get(i);
            mPaint.setColor(pieData.getColor());
            canvas.drawArc(rectF,mCurrentStartAngle,pieData.getAngle(),true,mPaint);

            //计算当前角度的一半
            float textAngle = mCurrentStartAngle + pieData.getAngle() / 2;

            // 扇形的中心坐标
            float x = (float) (r / 2 * Math.cos(textAngle * Math.PI / 180));
            float y = (float) (r / 2 * Math.sin(textAngle * Math.PI / 180));

            String name = pieData.getName();
            String percentage = (int)(pieData.getPercentage()*100) + "%";

            // 名称居中
            canvas.drawText(name, (x - mTextPaint.measureText(name)/2), y ,mTextPaint);

            // 名称 文本的高度
            Rect rect = new Rect();
            mTextPaint.getTextBounds(name, 0, name.length(), rect);
            int height = rect.height();

            // 绘制百分比
            canvas.drawText(percentage, (x - mTextPaint.measureText(percentage)/2), (y + height*2) ,mTextPaint);

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
            float mCurrentStartAngle = percentage * 360;                 // 对应的角度

            pie.setPercentage(percentage);                  // 记录百分比
            pie.setAngle(mCurrentStartAngle);                            // 记录角度大小
            sumAngle += mCurrentStartAngle;

            LogUtils.e( pie.getAngle());
        }
    }
}
