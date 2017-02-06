package com.zf.common.widget;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import com.zf.common.R;
import com.zf.common.Utils;


/**
 * Created by 郭攀峰 on 2015/8/19.
 */
public class RadarScanView extends View {
    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 300;

    private int centerX;
    private int centerY;
    private int radarRadius;
    private int circleColor = Color.parseColor("#a2a2a2");
    private int radarColor = Color.parseColor("#99a2a2a2");
    private int tailColor = Color.parseColor("#50aaaaaa");

    private Paint paintCircle;
    private Paint paintRadar;
    private Matrix matrix = new Matrix();

    public RadarScanView(Context context) {
        super(context);
        init(null, context);
    }

    public RadarScanView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, context);
    }

    public RadarScanView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, context);
    }

    @TargetApi(21)
    public RadarScanView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, context);
    }

    private void init(AttributeSet attrs, Context context) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadarScanView);
        circleColor = ta.getColor(R.styleable.RadarScanView_circleColor, circleColor);
        radarColor = ta.getColor(R.styleable.RadarScanView_radarColor, radarColor);
        tailColor = ta.getColor(R.styleable.RadarScanView_tailColor, tailColor);
        ta.recycle();

        initPaint();
        loop();
    }


    private void initPaint() {
        paintCircle = new Paint();
        paintCircle.setColor(circleColor);
        paintCircle.setAntiAlias(true);//抗锯齿
        paintCircle.setStyle(Paint.Style.STROKE);//设置实心
        paintCircle.setStrokeWidth(1);//画笔宽度

        paintRadar = new Paint();
        paintRadar.setColor(radarColor);
        paintRadar.setAntiAlias(true);
    }

    private void loop() {
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                matrix.postRotate(2, centerX, centerY);
                postInvalidate();
                sendEmptyMessageDelayed(0, 10);
            }
        };
        handler.sendEmptyMessageDelayed(0, 10);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
        radarRadius = Math.min(w, h);
        Shader shader = new SweepGradient(centerX, centerY, Color.TRANSPARENT, tailColor);
        paintRadar.setShader(shader);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int resultWidth = 0;
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);

        if (modeWidth == MeasureSpec.EXACTLY) {
            resultWidth = sizeWidth;
        } else {
            resultWidth = Utils.dip2px(getContext(), DEFAULT_WIDTH);
            if (modeWidth == MeasureSpec.AT_MOST) {
                resultWidth = Math.min(resultWidth, sizeWidth);
            }
        }

        int resultHeight = 0;
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        if (modeHeight == MeasureSpec.EXACTLY) {
            resultHeight = sizeHeight;
        } else {
            resultHeight = Utils.dip2px(getContext(), DEFAULT_HEIGHT);
            if (modeHeight == MeasureSpec.AT_MOST) {
                resultHeight = Math.min(resultHeight, sizeHeight);
            }
        }

        setMeasuredDimension(resultWidth, resultHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(centerX, centerY, radarRadius / 7, paintCircle);
        canvas.drawCircle(centerX, centerY, 2 * radarRadius / 7, paintCircle);
        canvas.drawCircle(centerX, centerY, 3 * radarRadius / 7, paintCircle);

        canvas.concat(matrix);
        canvas.drawCircle(centerX, centerY, 3 * radarRadius / 7, paintRadar);
    }
}
