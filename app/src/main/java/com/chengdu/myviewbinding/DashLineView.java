package com.chengdu.myviewbinding;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 虚线
 */
public class DashLineView extends View {

    private float mDashWidth = Utils.dp2px(6);
    private float mDashDistance = Utils.dp2px(3);
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Path dash = new Path();
    private PathDashPathEffect mEffect;
    private int mColor = getResources().getColor(R.color.color_EDEDED);
    


    public DashLineView(Context context) {
        super(context);
        init(context, null);
    }

    public DashLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public DashLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public DashLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DashLineView);
            if (typedArray != null) {
                mColor = typedArray.getColor(R.styleable.DashLineView_paintColor, getResources().getColor(R.color.color_EDEDED));
                mDashWidth = typedArray.getDimension(R.styleable.DashLineView_dashWidth, Utils.dp2px(6));
                mDashDistance = typedArray.getDimension(R.styleable.DashLineView_dashDistance, Utils.dp2px(3));
                mPaint.setColor(mColor);
            }
        }

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(Utils.dp2px(2));
        dash.addRect(0,0, mDashWidth, Utils.dp2px(2), Path.Direction.CW);
        /**
         * shape:填充图形
         * advance :两个dash之间的距离  周长/个数
         * phase：shape的偏移量、
         * PathDashPathEffect.Style.ROTATE：根据path的旋转而旋转
         * PathDashPathEffect.Style.TRANSLATE 以平移方式填充
         */
        mEffect = new PathDashPathEffect(dash, mDashWidth + mDashDistance, 0, PathDashPathEffect.Style.TRANSLATE);
        mPaint.setPathEffect(mEffect);
//        mPaint.setPathEffect(new DashPathEffect(new float[]{10, 10},0));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(0, getHeight()/2, getWidth(), getHeight()/2, mPaint);
    }
}
