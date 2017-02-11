package com.reliance.text1;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

/**
 * Created by sunzhishuai on 17/2/9.
 * E-mail itzhishuaisun@sina.com
 */

public class VerifyView extends View {
    private static final String TAG = "MyTextView";
    private MyAttrs attrs1;
    private Paint painter1;
    private Rect mBound;
    private Paint paint;

    public VerifyView(Context context) {
        this(context, null);
    }

    public VerifyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性
        attrs1 = getAttrs(context, attrs, defStyleAttr);
        //初始化画笔
        initPaint();

    }

    public MyAttrs getAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        //获取自定义属性
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.VerifyView, defStyleAttr, 0);
        int indexCount11 = a.getIndexCount();
        MyAttrs myAttrs = new MyAttrs();
        for (int i = 0; i < indexCount11; i++) {
            int index = a.getIndex(i);
            switch (index) {
                case R.styleable.VerifyView_verify_bg_color:
                    int[] defaultBgColor = getTextColor(1);
                    myAttrs.color = a.getColor(index, defaultBgColor[0]);
                    break;
                case R.styleable.VerifyView_verify_text_content:
                    myAttrs.content = a.getString(index);
                    break;
                case R.styleable.VerifyView_verify_text_size:
                    myAttrs.textSize = a.getDimensionPixelSize(index, 10);
                    break;
            }
        }
        return myAttrs;
    }

    public void initPaint() {
        painter1 = new Paint();
        painter1.setTextSize(attrs1.textSize);
        painter1.setColor(attrs1.color);
        //获取写字区域的
        mBound = new Rect();
        painter1.getTextBounds(attrs1.content, 0, attrs1.content.length(), mBound);

        paint = new Paint();
        paint.setStrokeWidth(15);
    }

    /**
     * 获取颜色
     */
    public int[] getTextColor(int count) {
        int[] ints = new int[count];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = Color.rgb(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255));
        }
        return ints;
    }

    private class MyAttrs {
        public int color = getTextColor(1)[0];
        public String content;
        public int textSize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //让view 支持warpcontent 属性
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int WidthDize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            WidthDize = mBound.width() + getPaddingRight() + getPaddingLeft();
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = mBound.height() + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(WidthDize, heightSize);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawTexts(canvas);
        drawLines(canvas);






    }

    private void drawTexts(Canvas canvas) {
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int[] textColors = getTextColor(attrs1.content.length());
        char[] texts = attrs1.content.toCharArray();
        for (int i = 0; i < attrs1.content.length(); i++) {
            int drawTextY = new Random().nextInt(measuredHeight - mBound.height() + 1) + mBound.height();
            int drawTextX = new Random().nextInt(measuredWidth - (mBound.width() / attrs1.content.length()));
            painter1.setColor(textColors[i]);
            canvas.drawText(texts, i, 1, drawTextX, drawTextY, painter1);
        }
    }

    private void drawLines(Canvas canvas) {

        Random random = new Random();
        int measuredHeight = getMeasuredHeight();
        int measuredWidth = getMeasuredWidth();
        paint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < random.nextInt(2)+2; i++) {
            paint.setAntiAlias(true);
            //绘制干扰线
            Path path = new Path();
            path.moveTo(0, random.nextInt(measuredHeight));//设置Path的起点
            path.cubicTo(random.nextInt(measuredHeight), random.nextInt(measuredWidth),random.nextInt(measuredHeight), random.nextInt(measuredWidth),measuredWidth,
                    random.nextInt(measuredHeight));

            paint.setColor(getTextColor(1)[0]);
            paint.setStrokeWidth(random.nextInt(mBound.height()/3));
            canvas.drawPath(path,paint);

        }

    }

    public void setVerifyTextContent(String content) {
        attrs1.content = content;
        postInvalidate();
        setBackgroundColor(getTextColor(1)[0]);
    }

}
