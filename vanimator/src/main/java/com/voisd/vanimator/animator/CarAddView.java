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
import com.voisd.vanimator.utils.WindowsUtil;


/**
 * Created by voisd on 2016/6/28.
 */
public class CarAddView extends FrameLayout {

    Context context;
    View mView;
    PointF startPointF,overPointF;
    int[] location = new int[2];
    int statusHeight = 0;

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
        statusHeight = WindowsUtil.getStatusHeight(context);
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
    public CarAddView setAnimInWindow(View startView,View overView,int rId,int animTime){
        setAnimView(rId);

        animatorSet  = new AnimatorSet();

        startView.getLocationInWindow(location);
        startPointF = new PointF(location[0],location[1]);
        overView.getLocationInWindow(location);
        overPointF = new PointF(location[0],location[1]);
        setAnim(animTime,false);
        return this;
    }

    /**
     * @param startView start location
     * @param overView over location
     * @param rId  layout ID  default -1
     * @param animTime  time
     * */
    public CarAddView setAnimInWindowSystemBar(View startView,View overView,int rId,int animTime){
        setAnimView(rId);

        statusHeight = WindowsUtil.getStatusHeight(context);
        animatorSet  = new AnimatorSet();

        startView.getLocationInWindow(location);
        startPointF = new PointF(location[0],location[1]);
        overView.getLocationInWindow(location);
        overPointF = new PointF(location[0],location[1]);
        setAnim(animTime,true);
        return this;
    }

    /**
     * @param startView start location
     * @param overView over location
     * @param rId  layout ID  default -1
     * @param animTime  time
     * */

    public CarAddView setAnimScreenSystemBar(View startView,View overView,int rId,int animTime){

        setAnimView(rId);

        animatorSet  = new AnimatorSet();

        startView.getLocationOnScreen(location);
        startPointF = new PointF(location[0],location[1]);
        overView.getLocationOnScreen(location);
        overPointF = new PointF(location[0],location[1]);

        setAnim(animTime,true);
        return this;
    }

    /**
     * @param startView start location
     * @param overView over location
     * @param rId  layout ID  default -1
     * @param animTime  time
     * */

    public CarAddView setAnimScreen(View startView,View overView,int rId,int animTime){

        setAnimView(rId);

        animatorSet  = new AnimatorSet();

        startView.getLocationOnScreen(location);
        startPointF = new PointF(location[0],location[1]);
        overView.getLocationOnScreen(location);
        overPointF = new PointF(location[0],location[1]-statusHeight);

        setAnim(animTime,false);
        return this;
    }

    /**
     * @param startPointF start location
     * @param overPointF over location
     * @param rId  layout ID  default -1
     * @param animTime  time
     * */

    public CarAddView setAnimPointF(PointF startPointF,PointF overPointF,int rId,int animTime){

        setAnimView(rId);

        animatorSet  = new AnimatorSet();

        this.startPointF = startPointF;
        this.overPointF = overPointF;

        setAnim(animTime,false);
        return this;
    }

    /**
     * 设置动画
     * @param type
     * value true 计算状态栏高度 false 不计算
     * */
    private void setAnim(int animTime,final boolean type){
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(), startPointF, overPointF);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
        {
            @Override
            public void onAnimationUpdate(ValueAnimator animation)
            {
                PointF pointF = (PointF) animation.getAnimatedValue();
                mView.setX(pointF.x);
                if(type) {
                    mView.setY(pointF.y - statusHeight);
                }else{
                    mView.setY(pointF.y);
                }
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
    }

}
