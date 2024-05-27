package com.chengdu.myviewbinding;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

/**
 * 带label的输入框
 * @Author LY
 * @Date 2021/6/5 11:20
 */
public class MaterialEditText extends androidx.appcompat.widget.AppCompatEditText {
    private final static float TEXT_SIZE = Utils.dp2px(12);
    private final static float TEXT_VERTICAL_OFFSET = Utils.dp2px(18);//输入框垂直padding
    private final static float TEXT_HORIZONTAL_OFFSET = Utils.dp2px(15);//水平padding
    private final static float TEXT_VERTICAL_SPACE = Utils.dp2px(4);//label和输入框字体间距
    private final static float TEXT_ANIMATOR_OFFSET = Utils.dp2px(8);//动画移动距离


    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private ObjectAnimator animator;
    private boolean floatingLabelShown;
    private float floatingLabelFraction;
    private boolean userFloatingLabel;

    public float getFloatingLabelFraction() {
        return floatingLabelFraction;
    }

    public void setUserFloatingLabel(boolean userFloatingLabel) {
        this.userFloatingLabel = userFloatingLabel;
        setPadding();
    }

    public void setFloatingLabelFraction(float floatingLabelFraction) {
        this.floatingLabelFraction = floatingLabelFraction;
        invalidate();
    }

    public MaterialEditText(Context context) {
        super(context);
        init(context, null);
    }

    public MaterialEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MaterialEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    {
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(TEXT_SIZE);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText);
            if (attributes != null) {
                userFloatingLabel = attributes.getBoolean(R.styleable.MaterialEditText_userFloatingLabel, false);
            }
        }
        setPadding();
        if (userFloatingLabel) {
            addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!floatingLabelShown && !TextUtils.isEmpty(s)) {
                        floatingLabelShown = true;
                        getAnimator().start();
                        setPadding();
                    } else if (floatingLabelShown && TextUtils.isEmpty(s)){
                        floatingLabelShown = false;
                        getAnimator().reverse();
                        setPadding();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    private void setPadding() {
        if (userFloatingLabel && !TextUtils.isEmpty(getText().toString())) setPadding((int) (TEXT_HORIZONTAL_OFFSET), (int) (TEXT_VERTICAL_OFFSET + TEXT_SIZE + TEXT_VERTICAL_SPACE), (int) TEXT_HORIZONTAL_OFFSET, (int) TEXT_VERTICAL_OFFSET);
        else setPadding((int) (TEXT_HORIZONTAL_OFFSET), (int) (TEXT_VERTICAL_OFFSET), (int) TEXT_HORIZONTAL_OFFSET, (int) TEXT_VERTICAL_OFFSET);
    }

    private ObjectAnimator getAnimator() {
        if (animator == null) {
            animator = ObjectAnimator.ofFloat(MaterialEditText.this, "floatingLabelFraction", 0, 1);
        }
        animator.setDuration(100);
        return animator;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAlpha((int) (0xff * floatingLabelFraction));
        float extraOffset = TEXT_ANIMATOR_OFFSET * (1 - floatingLabelFraction);
        canvas.drawText(getHint().toString(), TEXT_HORIZONTAL_OFFSET, TEXT_VERTICAL_OFFSET + TEXT_SIZE + extraOffset, mPaint);
    }
}
