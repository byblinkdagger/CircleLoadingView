package com.lucky.circleloading;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

import com.lucky.circleloading.util.DensityUtil;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by lucky on 16-8-18.
 */
public class CircleLoadingView extends View {
    private Paint mPaint;
    private int[] mColors= new int[]{0xB07ECBDA, 0xB0E6A92C, 0xB0D6014D, 0xB05ABA94};;
    private int mWidth;
    private int mHeight;
    private final int MIN_RADIU = 8;
    private int rotateAngle;
    //动画时长
    private int duration=5000;
    //圆半径
    private int radiu;
    private Context context;
    public CircleLoadingView(Context context) {
        this(context, null);
    }

    public CircleLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setStrokeWidth(5);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColors[0]);
        radiu=MIN_RADIU;

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth=w;
        mHeight=h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.rotate(-rotateAngle,mWidth/2,mHeight/2);
        //canvas.save();
        canvas.drawCircle(mWidth/2- DensityUtil.dip2px(context,100),mHeight/2-DensityUtil.dip2px(context,100),radiu,mPaint);
        canvas.rotate(rotateAngle,mWidth/2,mHeight/2);
        //canvas.restore();
    }

    protected void customAnimation() {

        Collection<Animator> animList = new ArrayList<>();
        ValueAnimator rotateAnimator=ValueAnimator.ofInt(0,360);
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rotateAngle= (int) valueAnimator.getAnimatedValue();
                //invalidate();
            }
        });
        animList.add(rotateAnimator);
        ValueAnimator radiuAnimator=ValueAnimator.ofInt(radiu,radiu*4);
        radiuAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                radiu= (int) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        animList.add(radiuAnimator);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(duration);
        animatorSet.playTogether(animList);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

}
