package com.chengdu.myviewbinding;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Tag流式布局
 */
public class TagLayout extends ViewGroup {

    private ArrayList<Rect> childRectList = new ArrayList<>();

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * 递归方法
     * @param widthMeasureSpec 父view对当前view的要求
     * @param heightMeasureSpec
     * 测量时，先考虑开发者的要求，再结合父view的要求进行计算
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = 0;
        int heightUsed = 0;
        int width = 0;
        int height = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, widthUsed, heightMeasureSpec, heightUsed);
            Rect rect;
            if (childRectList.size() > i) {
                rect = childRectList.get(i);
            } else {
                rect = new Rect();
            }
            if ((widthUsed + child.getMeasuredWidth()) > widthMeasureSpec ) {
                widthUsed = 0;
            }
            rect.set(widthUsed, heightUsed, widthUsed + child.getMeasuredWidth(), heightUsed + child.getMeasuredHeight());
            childRectList.set(i, rect);
            widthUsed += child.getMeasuredWidth();
            width = Math.max(width, widthUsed);
            height = Math.max(height, heightUsed);
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(childRectList.get(i).left, childRectList.get(i).top, childRectList.get(i).right, childRectList.get(i).bottom);
        }
    }
}
