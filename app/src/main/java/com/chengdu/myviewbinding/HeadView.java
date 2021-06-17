package com.chengdu.myviewbinding;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @Author LY
 * @Date 2021/6/5 15:25
 * 自定义view之自定义组合控件
 */
public class HeadView extends RelativeLayout {
    private TextView mTvBack;
    private TextView mTvTitle;
    private TextView mTvRight;

    public HeadView(Context context) {
        super(context);
    }

    public HeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
        RelativeLayout.inflate(context, R.layout.layout_head_view, this);
        mTvBack = findViewById(R.id.tv_back);
        mTvTitle = findViewById(R.id.tv_title);
        mTvRight = findViewById(R.id.tv_right);

        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);
        if (attributes != null) {
            //处理titleBar背景色
            int titleBarBackGround = attributes.getResourceId(R.styleable.CustomTitleBar_tlb_title_background_color, R.color.teal_200);
            setBackgroundResource(titleBarBackGround);
            //先处理左边按钮
            //获取是否要显示左边按钮
            boolean leftButtonVisible = attributes.getBoolean(R.styleable.CustomTitleBar_tlb_left_button_visible, true);
            if (leftButtonVisible) {
                mTvBack.setVisibility(View.VISIBLE);
            } else {
                mTvBack.setVisibility(View.INVISIBLE);
            }
            //设置左边按钮的文字
            String leftButtonText = attributes.getString(R.styleable.CustomTitleBar_tlb_left_button_text);
            if (!TextUtils.isEmpty(leftButtonText)) {
                mTvBack.setText(leftButtonText);
                //设置左边按钮文字颜色
                int leftButtonTextColor = attributes.getColor(R.styleable.CustomTitleBar_tlb_left_button_text_color, Color.WHITE);
                mTvBack.setTextColor(leftButtonTextColor);
            }
            //设置左边图片icon 这里是二选一 要么只能是文字 要么只能是图片
            int leftButtonDrawable = attributes.getResourceId(R.styleable.CustomTitleBar_tlb_left_button_drawable, R.mipmap.ic_back);
            if (leftButtonDrawable != -1) {
                mTvBack.setCompoundDrawablesWithIntrinsicBounds(leftButtonDrawable, 0, 0, 0);  //设置到哪个控件的位置（）
            }


            //处理标题
            //先获取标题是否要显示图片icon
            int titleTextDrawable = attributes.getResourceId(R.styleable.CustomTitleBar_tlb_title_text_drawable, -1);
            if (titleTextDrawable != -1) {
                mTvTitle.setBackgroundResource(titleTextDrawable);
            } else {
                //如果不是图片标题 则获取文字标题
                String titleText = attributes.getString(R.styleable.CustomTitleBar_tlb_title_text);
                if (!TextUtils.isEmpty(titleText)) {
                    mTvTitle.setText(titleText);
                }
                //获取标题显示颜色
                int titleTextColor = attributes.getColor(R.styleable.CustomTitleBar_tlb_title_text_color, Color.WHITE);
                mTvTitle.setTextColor(titleTextColor);
            }

            //先处理右边按钮
            //获取是否要显示右边按钮
            boolean rightButtonVisible = attributes.getBoolean(R.styleable.CustomTitleBar_tlb_right_button_visible, true);
            if (rightButtonVisible) {
                mTvRight.setVisibility(View.VISIBLE);
            } else {
                mTvRight.setVisibility(View.INVISIBLE);
            }
            //设置右边按钮的文字
            String rightButtonText = attributes.getString(R.styleable.CustomTitleBar_tlb_right_button_text);
            if (!TextUtils.isEmpty(rightButtonText)) {
                mTvRight.setText(rightButtonText);
                //设置右边按钮文字颜色
                int rightButtonTextColor = attributes.getColor(R.styleable.CustomTitleBar_tlb_right_button_text_color, Color.WHITE);
                mTvRight.setTextColor(rightButtonTextColor);
            }
            //设置右边图片icon 这里是二选一 要么只能是文字 要么只能是图片
            int rightButtonDrawable = attributes.getResourceId(R.styleable.CustomTitleBar_tlb_right_button_drawable, -1);
            if (rightButtonDrawable != -1) {
                mTvRight.setCompoundDrawablesWithIntrinsicBounds(0, 0, rightButtonDrawable, 0);  //设置到哪个控件的位置（）
            }
            attributes.recycle();
        }
    }

    public void setTitleClickListener(OnClickListener onClickListener) {
        if (onClickListener != null) {
            mTvBack.setOnClickListener(onClickListener);
            mTvRight.setOnClickListener(onClickListener);
        }
    }

    public TextView getmTvBack() {
        return mTvBack;
    }

    public TextView getmTvRight() {
        return mTvRight;
    }

    public TextView getmTvTitle() {
        return mTvTitle;
    }

}
