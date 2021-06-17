package com.chengdu.myviewbinding;

import android.app.Application;
import android.content.Context;

/**
 * @Author LY
 * @Date 2021/6/16 9:32
 */
public class Utils {
    /**
     * 将dp转换为实际的像素值px
     *
     * @param dpValue
     * @return
     */
    public static float dp2px(float dpValue) {
        float scale = HostApplication.mContext.getResources().getDisplayMetrics().density;
        return (dip2px(dpValue, scale) + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param scale    （DisplayMetrics类中属性density）
     * @return
     */
    private static float dip2px(float dipValue, float scale) {
        return dipValue * scale;
    }
}
