package com.voisd.vanimator.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import com.voisd.vanimator.R;
import com.voisd.vanimator.cartanim.BezierEvaluator;


/**
 * Created by jm on 2016/6/28.
 */
public class CarAddView extends FrameLayout {

    Context context;
    View mView;
    PointF startPointF,overPointF;
    int[] location = new int[2];

    AnimatorSet animatorSet;

    public CarAddView(Context context) {
        super(context);
        init(context,null,0);
    }

    public CarAddView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public CarAddView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    public void init(Context context, AttributeSet attrs, int defStyleAttr){
        this.context = context;

    }


    private void setAnimView(int rId){
        if(rId==-1){
            mView = LayoutInflater.from(context).inflate(R.layout.cart_anim,this, false);
        }else{
            mView = LayoutInflater.from(context).inflate(rId,this, false);
        }
    }

    public void showAnim(){
        if(animatorSet!=null){
            animatorSet.start();
        }
    }

    public void disAnim(){
        if(animatorSet!=null){
            animatorSet.end();
        }

    }


    /**
     * @param startView start location
     * @param overView over location
     * @param rId  layout ID  default -1
     * @param animTime  time
     * */
    public CarAddView setAnim(View startView,View overView,int rId,int animTime){

        animatorSet  = new AnimatorSet();

        setAnimView(rId);

        startView.getLocationInWindow(location);
        startPointF = new PointF(location[0],location[1]);

        overView.getLocationInWindow(location);
        overPointF = new PointF(location[0],location[1]);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(), startPointF, overPointF);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF pointF = (PointF) animation.getAnimatedValue();
                mView.setX(pointF.x);
                mView.setY(pointF.y);
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener()
        {
            public void onAnimationStart(Animator animation)
            {
                addView(mView);
            }
            public void onAnimationEnd(Animator animation)
            {
                if(mView!=null) {
                    removeView(mView);
                    mView.destroyDrawingCache();
                }
            }
            public void onAnimationCancel(Animator animation) { }
            public void onAnimationRepeat(Animator animation) { }
        });

        animatorSet.playTogether(
                ObjectAnimator.ofFloat(mView, "scaleX", 0.3f, 1f),
                ObjectAnimator.ofFloat(mView, "scaleY", 0.3f, 1f),
                valueAnimator
        );
        animatorSet.setDuration(animTime);
        return this;
    }

}
