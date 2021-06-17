package com.chengdu.myviewbinding;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class AvatarView extends View {

    private static final float WIDTH = Utils.dp2px(300);
    private static final float PADDING = Utils.dp2px(50);
    private static final float EDGE_WIDTH = Utils.dp2px(35);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    //圆落在目标图片之上，显示圆的形状，目标图片的内容
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    Bitmap bitmap;
    RectF savedArea = new RectF();

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    {
        bitmap = getAvatar((int) WIDTH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        savedArea.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //oval 椭圆
        canvas.drawOval(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH, paint);
        //离屏缓冲
        int saved = canvas.saveLayer(savedArea, paint);
        canvas.drawOval(PADDING + EDGE_WIDTH, PADDING + EDGE_WIDTH,
                PADDING +WIDTH - EDGE_WIDTH, PADDING + WIDTH - EDGE_WIDTH, paint);
        //PorterDuffXfermode 图形混合模式(也就是图形叠加的时候，设置叠加效果)
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, PADDING, PADDING, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
    }

    Bitmap getAvatar(int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //此属性设为true时，解码bitmap时可以只返回其高，宽和Mime类型，而不必为其申请内存，从而节省了内存空间
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher, options);
    }
}
