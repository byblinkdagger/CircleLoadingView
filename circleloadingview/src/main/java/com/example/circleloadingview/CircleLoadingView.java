package com.example.circleloadingview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.CycleInterpolator;
import android.view.animation.LinearInterpolator;

import com.example.circleloadingview.util.DensityUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by luck on 2016/8/20.
 */
public class CircleLoadingView extends View {
    private Paint mPaint;
    private int[] mColors= new int[]{0xB07ECBDA, 0xB0E6A92C, 0xB0D6014D, 0xB05ABA94,0XB0E040FB,0XB0FF5722};
    private int mWidth;
    private int mHeight;
    private final int MIN_RADIU = 8;
    private int rotateAngle;
    //动画时长
    private int duration=2000;
    //圆半径
    private int radiu;
    //圆半径与Canvas中心坐标差值
    private int gapCircle;
    //圆数量
    private int circleCount=3;
    private List<Animator> allAnim = new ArrayList<>();
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
        for (int i = 0; i < circleCount; i++) {
            canvas.rotate(-rotateAngle-i*(360/circleCount),mWidth/2,mHeight/2);
            mPaint.setColor(mColors[i]);
            canvas.drawCircle(mWidth/2- gapCircle,mHeight/2-gapCircle,radiu,mPaint);
            canvas.rotate(rotateAngle+i*(360/circleCount),mWidth/2,mHeight/2);
        }
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
        ValueAnimator gapAnimator=ValueAnimator.ofInt(DensityUtil.dip2px(context,60),0);
        gapAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                gapCircle= (int) valueAnimator.getAnimatedValue();
            }
        });
        animList.add(gapAnimator);
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
                oppositeAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        allAnim.add(animatorSet);
        animatorSet.start();
    }
    protected void oppositeAnimation() {
        Collection<Animator> animList = new ArrayList<>();
        ValueAnimator rotateAnimator=ValueAnimator.ofInt(360,0);
        rotateAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                rotateAngle= (int) valueAnimator.getAnimatedValue();
                //invalidate();
            }
        });
        animList.add(rotateAnimator);
        ValueAnimator gapAnimator=ValueAnimator.ofInt(0,DensityUtil.dip2px(context,60));
        gapAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                gapCircle= (int) valueAnimator.getAnimatedValue();
            }
        });
        animList.add(gapAnimator);
        ValueAnimator radiuAnimator=ValueAnimator.ofInt(radiu,radiu/4);
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
                //customAnimation();

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        allAnim.add(animatorSet);
        animatorSet.start();
    }

    protected void stopAll(){
        for (Animator anim : allAnim) {
           anim.cancel();
        }
        init();

    }
    protected void setCircleCount(int count) {
        stopAll();
        circleCount=count;
        customAnimation();
    }
    protected void setDuration(int duration) {
        stopAll();
        this.duration=duration;
        customAnimation();
    }

}
