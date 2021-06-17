package com.chengdu.myviewbinding;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 仪表盘
 */
public class DashBoard extends View {
    private static final int ANGLE = 120;
    private static final float RADIUS = Utils.dp2px(150);
    private static final float LENGTH = Utils.dp2px(100);
    Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Path dash = new Path();
    PathDashPathEffect mEffect;

    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    //大括号表示在构造方法super之后执行
    {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Utils.dp2px(2));
        dash.addRect(0,0,Utils.dp2px(2),Utils.dp2px(10), Path.Direction.CW);
        Path arc = new Path();
        arc.addArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS,
                getHeight() /2 + RADIUS,90 + ANGLE/2, 360 - ANGLE);
        PathMeasure pathMeasure = new PathMeasure(arc,false);
        /**
         * shape:填充图形
         * advance :两个dash之间的距离  周长/个数
         * phase：shape的偏移量、
         * PathDashPathEffect.Style.ROTATE：根据path的旋转而旋转
         * PathDashPathEffect.Style.TRANSLATE 以平移方式填充
         */
        mEffect = new PathDashPathEffect(dash, (pathMeasure.getLength() - Utils.dp2px(2) * 20) / 20,
                0, PathDashPathEffect.Style.ROTATE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画一个弧
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS,
                getHeight() /2 + RADIUS,90 + ANGLE/2, 360 - ANGLE, false,
                mPaint);
        //画刻度
        mPaint.setPathEffect(mEffect);
        canvas.drawArc(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS,
                getHeight() /2 + RADIUS,90 + ANGLE/2, 360 - ANGLE, false,
                mPaint);
        mPaint.setPathEffect(null);
        //画一个指针
        canvas.drawLine(getWidth() / 2, getHeight() / 2,
                (float) Math.cos(Math.toRadians(getAngleFromMark(5))) * LENGTH + getWidth() / 2,
                (float) Math.sin(Math.toRadians(getAngleFromMark(5)))* LENGTH + getHeight() / 2,
                mPaint);
    }
    int getAngleFromMark(int mark){
        return (int) (90 + (float)ANGLE / 2 + (360 - (float) ANGLE) / 20 * mark);
    }
}
