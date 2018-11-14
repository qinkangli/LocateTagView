package com.example.administrator.locatetagdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class LocateTagView extends View {
    private static final float TEMP = 2.5f;
    private static final float PADDING = 50;
    private float TEXT_BOUND_WIDTH;
    private float TEXT_BOUND_HEIGHT;
    private float centerX;
    private float centerY;
    private String text;
    private Paint mBoundPaint;
    private Paint mTextPaint;
    private Paint mDotPaint;
    private Paint mLinePaint;
    private Rect rect;

    public LocateTagView(Context context) {
        this(context,null);
    }

    public LocateTagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LocateTagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.tag);
        centerX = array.getFloat(R.styleable.tag_centerX,0);
        centerY = array.getFloat(R.styleable.tag_centerY,0);
        text = array.getString(R.styleable.tag_text);

        mBoundPaint = new Paint();
        mBoundPaint.setColor(getResources().getColor(android.R.color.white));
        mBoundPaint.setStyle(Paint.Style.STROKE);
        mBoundPaint.setAntiAlias(true);
        mBoundPaint.setStrokeWidth(1);
        mBoundPaint.setDither(true);

        mTextPaint = new Paint();
        mTextPaint.setTextSize(28);
        mTextPaint.setColor(getResources().getColor(android.R.color.white));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);

        mDotPaint = new Paint();
        mDotPaint.setColor(Color.parseColor("#20000000"));
        mDotPaint.setStyle(Paint.Style.FILL);
        mDotPaint.setAntiAlias(true);
        mDotPaint.setDither(true);

        mLinePaint = new Paint();
        mLinePaint.setColor(getResources().getColor(android.R.color.white));
        mLinePaint.setAntiAlias(true);
        mLinePaint.setDither(true);

        rect = new Rect();
        mTextPaint.getTextBounds(text,0,text.length(),rect);
        TEXT_BOUND_WIDTH = rect.width() + PADDING - TEMP;
        TEXT_BOUND_HEIGHT = rect.height() + PADDING - TEMP;

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(TEMP, TEMP,TEXT_BOUND_WIDTH,TEXT_BOUND_HEIGHT,50,50,mBoundPaint);//画边框
        canvas.drawText(text,PADDING / 2 - TEMP,TEXT_BOUND_HEIGHT / 2 + rect.height() / 2 - TEMP,mTextPaint);//写文字
        mDotPaint.setColor(Color.parseColor("#20000000"));
        canvas.drawCircle(centerX,centerY,20,mDotPaint);//画大圆点
        mDotPaint.setColor(getResources().getColor(android.R.color.white));
        canvas.drawCircle(centerX,centerY,8,mDotPaint);//画小圆点
        canvas.drawLine(TEXT_BOUND_WIDTH,TEXT_BOUND_HEIGHT / 2,centerX,centerY,mLinePaint);//划线
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        int width = measureWidth(minimumWidth, widthMeasureSpec);
        int height = measureHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width,height);
    }


    private int measureWidth(int defaultWidth, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = (int) Math.max(TEXT_BOUND_WIDTH,centerX) + 20;
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }


    private int measureHeight(int defaultHeight, int measureSpec) {

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = (int) Math.max(TEXT_BOUND_HEIGHT,centerY) + 20;
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                break;
        }
        return defaultHeight;


    }

}
