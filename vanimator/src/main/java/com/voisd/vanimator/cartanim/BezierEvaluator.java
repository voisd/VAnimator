package com.voisd.vanimator.cartanim;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * evaluate
 */
public class BezierEvaluator implements TypeEvaluator<PointF>
{
    @Override
    public PointF evaluate(float fraction, PointF startValue,
                           PointF endValue)
    {
        PointF point1 = new PointF((startValue.x + endValue.x) / 2,0);
        return BezierCurve.bezier(fraction, startValue, point1, endValue);
    }
}

