package com.hc.hencoder.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hc.hencoder.R;

/**
 * Created by liu_lei on 2018/1/23.
 *
 */

public class PaintView extends View {

    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    Shader linearShaderClamp, linearShaderMirror, linearShaderRepeat;
    Shader radialShader;
    Shader sweepShader;
    Shader bitmapShader;

    Bitmap mBitmapDeathNote;

    public PaintView(Context context) {
        this(context, null);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        linearShaderClamp = new LinearGradient(30, 210, 230, 360,
                Color.parseColor("#ff0000"), Color.parseColor("#0000ff"),
                Shader.TileMode.CLAMP);

        linearShaderMirror = new LinearGradient(260, 210, 460, 360,
                Color.parseColor("#ff0000"), Color.parseColor("#0000ff"),
                Shader.TileMode.MIRROR);

        linearShaderRepeat = new LinearGradient(500, 210, 700, 360,
                Color.parseColor("#ff0000"), Color.parseColor("#0000ff"),
                Shader.TileMode.REPEAT);

        radialShader = new RadialGradient(900, 130, 100, Color.parseColor("#ff0000"),
                Color.parseColor("#0000ff"), Shader.TileMode.CLAMP);

        sweepShader = new SweepGradient(900, 360, Color.parseColor("#ff0000"),
                Color.parseColor("#0000ff"));

        mBitmapDeathNote = BitmapFactory.decodeResource(getResources(), R.drawable.l);
        bitmapShader = new BitmapShader(mBitmapDeathNote, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制背影
        canvas.drawColor(Color.parseColor("#f2f2f2"));

        // 绘制矩形
        mPaint.setColor(Color.parseColor("#009688"));
        canvas.drawRect(30, 30, 230, 180, mPaint);

        // 绘制斜线
        mPaint.setColor(Color.parseColor("#ff9800"));
        mPaint.setStrokeWidth(10);
        canvas.drawLine(300, 30, 450, 180, mPaint);

        // 绘制文字
        mPaint.setColor(Color.parseColor("#ff0000"));
        mPaint.setTextSize(40);
        canvas.drawText("henCoder", 500, 130, mPaint);

        // 线性渐变 - clamp
        mPaint.setShader(linearShaderClamp);
        canvas.drawRect(30, 210, 230, 360, mPaint);

        // 线性渐变 - mirror
        mPaint.setShader(linearShaderMirror);
        canvas.drawRect(260, 210, 460, 360, mPaint);

        // 线性渐变 - repeat
        mPaint.setShader(linearShaderRepeat);
        canvas.drawRect(500, 210, 700, 360, mPaint);

        // 辐射渐变 - clamp
        mPaint.setShader(radialShader);
        canvas.drawCircle(900, 130, 100, mPaint);

        // 扫描渐变
        mPaint.setShader(sweepShader);
        canvas.drawCircle(900, 360, 100, mPaint);

        // 图片作填充色
        mPaint.setShader(bitmapShader);
        canvas.drawCircle(900, 590, 100, mPaint);
    }

}
