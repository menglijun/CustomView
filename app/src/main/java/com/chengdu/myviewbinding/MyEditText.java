package com.chengdu.myviewbinding;

import android.content.Context;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * @Author LY
 * @Date 2021/6/5 11:20
 */
public class MyEditText extends AppCompatEditText {
    public MyEditText(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
