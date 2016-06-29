package com.voisd.vanimator.cartanim;

import android.graphics.PointF;

/**
 * add car acimator tanim
 */
public class BezierCurve
{
    public static PointF bezier(float t, PointF point0, PointF point1, PointF point2)
    {
        if (t < 0 || t > 1) {
            throw new IllegalArgumentException("t must between 0 and 1");
        }

        float  oneMinusT = 1.0f - t;
        PointF point     = new PointF();
        point.x = oneMinusT * oneMinusT * point0.x
                + 2 * t * oneMinusT * point1.x
                + t * t * point2.x;
        point.y = oneMinusT * oneMinusT * point0.y
                + 2 * t * oneMinusT * point1.y
                + t * t * point2.y;
        return point;
    }

}
