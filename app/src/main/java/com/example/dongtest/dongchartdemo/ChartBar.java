package com.example.dongtest.dongchartdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Swift_Dong on 2017/4/27.
 */

public class ChartBar extends View {

    //画线的画笔
    private Paint mLinePaint;
    //画柱状图的画笔
    private Paint mBarPaint;
    //写字的画笔
    private Paint mTextPaint1;

    private Paint mTextPaintnumber;


    //柱状图
    private Paint mTextPaint;

    //开始X坐标
    private int startX;
    //开始Y坐标
    private int startY;
    //结束X坐标
    private int stopX;
    //结束Y坐标
    private int stopY;

    //测量值 宽度
    private int measuredWidth;
    //测量值 高度
    private int measuredHeight;
    //每条柱状图的高度
    private int barHeight;
    //设置最大值，用于计算比例
    private float max;
    //方便计算左边字的坐标
    private String maxlength;
    //设置每条柱状图的目标值，除以max即为比例
    private ArrayList<Integer> respTarget;

    //每条刻度线的值
    private ArrayList<Integer> tarList = new ArrayList<Integer>();
    //设置一共有几条柱状图
    private int totalBarNum;
    //设置每条柱状图的当前比例
    private Float[] currentBarProgress;
    //每条竖线的当前比例
    private int currentVerticalLineProgress;

    //每条柱状图的名字
    private ArrayList<String> respName;
    //每条横线之间的间距
    private int deltaX;
    //每条柱状图之间的间距
    private int deltaY;
    //一共有几条横线
    private int horizentalLineNum;

    public ChartBar(Context context) {
        super(context);
        init(context);
    }

    public ChartBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChartBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        //start X = 0
        startX = 108;

        startY = 0;

        //setting paint for chart
        mBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBarPaint.setColor(Color.GREEN);
        mBarPaint.setStyle(Paint.Style.FILL);

        //setting paint for line
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(0xff3ea8f9);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setStrokeWidth(4);

        //setting paint for line
        mTextPaint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint1.setColor(0xff3ea8f9);
        mTextPaint1.setStyle(Paint.Style.FILL);
        mTextPaint1.setTextSize(20);

        //setting paint for 数值
        mTextPaintnumber = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaintnumber.setColor(0xffff5959);
        mTextPaintnumber.setStyle(Paint.Style.FILL);
        mTextPaintnumber.setTextSize(20);
        mTextPaintnumber.setTextAlign(Paint.Align.CENTER);

    }

    /**
     * 测量方法，主要考虑宽和高设置为wrap_content的时候，我们的view的宽高设置为多少
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
//        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
//        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
//        //如果宽和高都为wrap_content的时候，我们将宽设置为我们输入的max值，也就是柱状图的最大值
//        //高度为每条柱状图的宽度加上间距再乘以柱状图条数再加上开始Y值后得到的值
//        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
//            setMeasuredDimension((int) max, startY+10+totalBarNum*(barHeight+2*10));
//            //如果宽度为wrap_content  高度为match_parent或者精确数值的时候
//        }else if (widthSpecMode == MeasureSpec.AT_MOST) {
//            //宽度设置为max，高度为父容器高度
//            setMeasuredDimension((int) max, heightSpecSize);
//            //如果宽度为match_parent或者精确数值的时候，高度为wrap_content
//        }else if (heightSpecMode == MeasureSpec.AT_MOST) {
//            //宽度设置为父容器的宽度，高度为每条柱状图的宽度加上间距再乘以柱状图条数再加上开始Y值后得到的值
////            setMeasuredDimension(widthSpecSize, startY+10+totalBarNum*(barWidth+2*10));
//            setMeasuredDimension(startX + 10+totalBarNum*(barHeight+2*10), widthSpecSize);
//        }

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        //获得测量后的宽度
        measuredWidth = getMeasuredWidth();
        //获得测量后的高度
        measuredHeight = getMeasuredHeight();
        //计算结束Y的值
        stopY = measuredHeight - barHeight;
        //计算每条横线的间距
        deltaY = (stopY - (startY + (7 * barHeight / 5))) / horizentalLineNum;
        //计算每条柱状图之间间距
        deltaX = (measuredWidth-40 - barHeight * totalBarNum - startX) / (1+totalBarNum);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);
        rectPaint.setStyle(Paint.Style.FILL);

        Paint textPaint = new Paint();
        textPaint.setColor(0xff3ea8f9);
        textPaint.setTextSize(20);
        textPaint.setStyle(Paint.Style.FILL);
        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        textPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom

        Paint.FontMetrics fontMetricsnum = mTextPaintnumber.getFontMetrics();
        float topnum = fontMetricsnum.top;//为基线到字体上边框的距离,即上图中的top
        float bottomnum = fontMetricsnum.bottom;//为基线到字体下边框的距离,即上图中的bottom
        /**
         * 画柱状图
         */

        for (int i = 0; i < totalBarNum; i++) {
            if (currentBarProgress[i] < (respTarget.get(i) / max) * (measuredHeight - 40 - deltaY)) {
                currentBarProgress[i] += 10;
                postInvalidateDelayed(10);
            }
            /**
             *  drawRect(float left, float top, float right, float bottom, Paint paint)
             */
            //下文字
            Rect rect = new Rect(startX + deltaX + i * (deltaX + barHeight) + barHeight / 2 - (respName.get(i).length() * 20) / 2, measuredHeight - 30, startX + deltaX + i * (deltaX + barHeight) + barHeight / 2 + (respName.get(i).length() * 20) / 2, measuredHeight - 10);//画一个矩形
            canvas.drawRect(rect, rectPaint);
            int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
            canvas.drawText(respName.get(i), rect.centerX(), baseLineY, textPaint);
            canvas.drawRect(startX + deltaX + i * (deltaX + barHeight), measuredHeight - 40 - currentBarProgress[i], startX + deltaX + i * (deltaX + barHeight) + barHeight, measuredHeight - 40, mBarPaint);

            //图表值
            int startx = (int) (measuredHeight - 50 - currentBarProgress[i]);
            Rect rect1 = new Rect(startX + deltaX + i * (deltaX + barHeight) + barHeight / 2 - (respName.get(i).length() * 20) / 2, startx-20, startX + deltaX + i * (deltaX + barHeight) + barHeight / 2 + (respName.get(i).length() * 20) / 2, startx);//画一个矩形
            canvas.drawRect(rect1, rectPaint);
            int baseLineY1 = (int) (rect1.centerY() - topnum / 2 - bottomnum / 2);//基线中间点的y轴计算公式
            canvas.drawText(respTarget.get(i) + "", rect1.centerX(), baseLineY1, mTextPaintnumber);
        }

        /**
         * 画横线
         */


        for (int i = 0; i < horizentalLineNum; i++) {
            tarList.add(i,(int)(max/horizentalLineNum)*(i+1));
        }

        for (int i = 0; i < horizentalLineNum; i++) {
            if (currentVerticalLineProgress < measuredHeight) {
                currentVerticalLineProgress += 3;
                postInvalidateDelayed(10);
            }
            canvas.drawText(tarList.get(i)+"",startX - (maxlength.length() * 20),(measuredHeight - 22) - (i + 1) * deltaY,mTextPaint1);
            //标线
            canvas.drawLine(startX-8, (measuredHeight - 30) - (i + 1) * deltaY, startX, (measuredHeight - 30) - (i + 1) * deltaY, mLinePaint);
            //竖线
            canvas.drawLine(startX, 30, startX, measuredHeight - 10, mLinePaint);
            //地横线
            canvas.drawLine(startX-24,measuredHeight -40, measuredWidth - 40, measuredHeight - 40, mLinePaint);
//            canvas.drawText(numPerUnit*(i+1)+unit, (startX+7*barHeight/5)+(i+1)*deltaX-barHeight, startY-barHeight/5, mTextPaint);
        }
    }

    /**
     * 分别设置每个柱状图的目标值
     *
     * @param respTarget
     */
    public void setRespectTargetNum(ArrayList<Integer> respTarget) {
        this.respTarget = respTarget;

    }

    /**
     * 分别设置每个柱状图的名字
     *
     * @param respName
     */
    public void setRespectName(ArrayList<String> respName) {
        this.respName = respName;
    }

    /**
     * 设置每个柱状图的宽度
     *
     * @param width
     */
    public void setBarWidth(int width) {
        this.barHeight = width;
        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(3 * barHeight / 5);
        mTextPaint.setStrokeWidth(1);
        mTextPaint.setColor(0xffababab);

    }

    /**
     * 设置一共有几个柱状图
     *
     * @param totalNum
     */
    public void setTotalBarNum(int totalNum) {
        this.totalBarNum = totalNum;
        currentBarProgress = new Float[totalNum];
        for (int i = 0; i < totalNum; i++) {
            currentBarProgress[i] = 0.0f;
        }
    }

    /**
     * 设置有几条竖线
     *
     * @param num
     */
    public void setHorizentalLineNum(int num) {
        this.horizentalLineNum = num;
    }

    /**
     * 设置最大值
     *
     * @param max
     */
    public void setMax(float max) {
        this.max = max;
    }

    public void setMaxLength(String max) {
        this.maxlength = max;
    }

}
