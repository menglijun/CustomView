package com.chengdu.myviewbinding;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class GradientTextView extends AppCompatTextView {
    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;
    private Rect mTextBound = new Rect();

    public GradientTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                new int[]{0xFF0000FF, 0xFF9400D3},
                null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
    }

}
