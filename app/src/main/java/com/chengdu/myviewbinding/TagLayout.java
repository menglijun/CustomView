package com.chengdu.myviewbinding;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Tag流式布局
 */
public class TagLayout extends ViewGroup {

    private final static int CHILD_HORIZONTAL_MARGIN = (int) Utils.dp2px(5);
    private final static int CHILD_TOP_MARGIN = (int) Utils.dp2px(5);

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

    public void setData(List<String> list) {
        if (list != null && list.size() > 0) {
            for (String item : list) {
                TextView textView = new TextView(getContext());
                textView.setText(item);
                textView.setPadding((int) Utils.dp2px(5), (int) Utils.dp2px(5), (int) Utils.dp2px(5), (int) Utils.dp2px(5));
                textView.setBackgroundResource(R.drawable.bg_green_c5);
                textView.setTextColor(getResources().getColor(R.color.white));
                MarginLayoutParams marginLayoutParams = new MarginLayoutParams(generateDefaultLayoutParams());
                textView.setLayoutParams(marginLayoutParams);
                addView(textView);
            }
        }
    }

    /**
     * 递归方法
     * @param widthMeasureSpec 父view对当前view的要求
     * @param heightMeasureSpec
     * 测量时，先考虑开发者的要求，再结合父view的要求进行计算
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = getPaddingLeft();
        int heightUsed = getPaddingTop();
        int width = 0;
        int height = 0;
        int itemHeightMax = 0;
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            if ((widthUsed + child.getMeasuredWidth()) > (widthSpecSize - getPaddingRight()) ) {
                widthUsed = getPaddingLeft();
                heightUsed += itemHeightMax + CHILD_TOP_MARGIN;
            }
            Rect rect;
            if (childRectList.size() > i) {
                rect = childRectList.get(i);
            } else {
                rect = new Rect();
                childRectList.add(rect);
            }
            rect.set(widthUsed, heightUsed, widthUsed + child.getMeasuredWidth(), heightUsed + child.getMeasuredHeight());
            widthUsed += child.getMeasuredWidth() + CHILD_HORIZONTAL_MARGIN * 2;
            itemHeightMax = (int) Math.max(itemHeightMax, child.getMeasuredHeight());
//            heightUsed = Math.max(heightUsed, child.getMeasuredHeight());
        }
        width = Math.max(width, widthUsed);
        height = Math.max(height, itemHeightMax + heightUsed) + getPaddingBottom();
        setMeasuredDimension(widthMeasureSpec, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.layout(childRectList.get(i).left, childRectList.get(i).top, childRectList.get(i).right, childRectList.get(i).bottom);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
