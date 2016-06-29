package com.voisd.vanimator.utils;

import android.content.Context;

/**
 * Created by voisd on 2016/6/29.
 */
public class UnitUtil {
    /**
    * dip转px像素
    * @param context
    * @param px
    * @return
     */
    public static int dip2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }

    /**
     * 获取屏幕密度
     * @param context
     * @return
     */
    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }
}
