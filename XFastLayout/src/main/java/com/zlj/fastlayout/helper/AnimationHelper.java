package com.zlj.fastlayout.helper;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * Created by zlj on 2021/4/25.
 * @Word：Thought is the foundation of understanding
 * @since 1.0.0
 * 动画辅助类
 */
public final class AnimationHelper {
    /**
     * 收起动画
     * @param view
     * @param height
     */
    public static void collapse(final View view, final int height){
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(1,0);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp=view.getLayoutParams();
                lp.height= (int) (height*value);
                view.setLayoutParams(lp);
            }
        });
        valueAnimator.start();
    }

    /**
     * 展开动画
     * @param view
     * @param height
     */
    public static void expand(final View view, final int height){
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(0,1);
        valueAnimator.setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value= (float) animation.getAnimatedValue();
                ViewGroup.LayoutParams lp=view.getLayoutParams();
                lp.height= (int) (height*value);
                view.setLayoutParams(lp);
            }
        });
        valueAnimator.start();
    }

    /**
     * getBottomSlideAnimatorIn
     * @param targetView
     * @return
     */
    public static Animator getBottomSlideAnimatorIn(View targetView){
        return ObjectAnimator.ofFloat(targetView,"translationY",targetView.getHeight(),0);
    }

    /**
     * getBottomSlideAnimatorOut
     * @param targetView
     * @return
     */
    public static Animator getBottomSlideAnimatorOut(View targetView){
        return ObjectAnimator.ofFloat(targetView,"translationY",0,targetView.getHeight());
    }

    /**
     * getDropDownSlideAnimatorIn
     * @param targetView
     * @return
     */
    public static Animator getDropDownSlideAnimatorIn(View targetView){
        return ObjectAnimator.ofFloat(targetView,"translationY",-targetView.getHeight(),0);
    }

    /**
     * getDropDownSlideAnimatorOut
     * @param targetView
     * @return
     */
    public static Animator getDropDownSlideAnimatorOut(View targetView){
        return  ObjectAnimator.ofFloat(targetView,"translationY",0,-targetView.getHeight());
    }

    /**
     * getLeftSlideAnimatorIn
     * @param targetView
     * @return
     */
    public static Animator getLeftSlideAnimatorIn(View targetView){
        return  ObjectAnimator.ofFloat(targetView,"translationX",-targetView.getWidth(),0);
    }

    /**
     * getLeftSlideAnimatorOut
     * @param targetView
     * @return
     */
    public static Animator getLeftSlideAnimatorOut(View targetView){
        return  ObjectAnimator.ofFloat(targetView,"translationX",0,-targetView.getWidth());
    }

    /**
     * getRightSlideAnimatorIn
     * @param targetView
     * @return
     */
    public static Animator getRightSlideAnimatorIn(View targetView){
        return ObjectAnimator.ofFloat(targetView,"translationX",targetView.getWidth(),0);
    }

    /**
     * getRightSlideAnimatorOut
     * @param targetView
     * @return
     */
    public static Animator getRightSlideAnimatorOut(View targetView){
        return  ObjectAnimator.ofFloat(targetView,"translationX",0,targetView.getWidth());
    }

    /**
     * getAlphaAnimatorIn
     * @param targetView
     * @return
     */
    public static Animator getAlphaAnimatorIn(View targetView){
        return ObjectAnimator.ofFloat(targetView,"alpha",0f,1f);
    }

    /**
     * getAlphaAnimatorOut
     * @param targetView
     * @return
     */
    public static Animator getAlphaAnimatorOut(View targetView){
        return ObjectAnimator.ofFloat(targetView,"alpha",1f,0f);
    }

    /**
     * getScaleAnimatorIn
     * @param targetView
     * @return
     */
    public static Animator getScaleAnimatorIn(View targetView){
        AnimatorSet animatorSet=new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(targetView, "scaleX", 0.9f, 1f);
        ObjectAnimator scaleY= ObjectAnimator.ofFloat(targetView, "scaleY", 0.9f, 1f);
        animatorSet.playTogether(scaleX,scaleY,getAlphaAnimatorIn(targetView));
        return animatorSet;
    }

    /**
     * getScaleAnimatorOut
     * @param targetView
     * @return
     */
    public static Animator getScaleAnimatorOut(View targetView){
        AnimatorSet animatorSet=new AnimatorSet();
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(targetView, "scaleX",  1f,0.9f);
        ObjectAnimator scaleY= ObjectAnimator.ofFloat(targetView, "scaleY",  1f,0.9f);
        animatorSet.playTogether(scaleX,scaleY,getAlphaAnimatorOut(targetView));
        return animatorSet;
    }

    /**
     * getRotateAnimatorIn
     * @param targetView
     * @return
     */
    public static Animator getRotateAnimatorIn(View targetView){
        return ObjectAnimator.ofFloat(targetView,"rotation",0,360);
    }

    /**
     * getRotateAnimatorOut
     * @param targetView
     * @return
     */
    public static Animator getRotateAnimatorOut(View targetView){
        return ObjectAnimator.ofFloat(targetView,"rotation",360,0);
    }

    /**
     * getBottomSlideAnimationIn
     * @return
     */
    public static Animation getBottomSlideAnimationIn(){
        return new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,
                1f,Animation.RELATIVE_TO_SELF,0f);
    }

    /**
     * getBottomSlideAnimationOut
     * @return
     */
    public static Animation getBottomSlideAnimationOut(){
        return new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,1f);
    }

    /**
     * getDropDownSlideAnimationIn
     * @return
     */
    public static Animation getDropDownSlideAnimationIn(){
        return new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,
                -1f,Animation.RELATIVE_TO_SELF,0f);
    }

    /**
     * getDropDownSlideAnimationOut
     * @return
     */
    public static Animation getDropDownSlideAnimationOut(){
        return  new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,-1f);
    }

    /**
     * getLeftSlideAnimationIn
     * @return
     */
    public static Animation getLeftSlideAnimationIn(){
        return  new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                -1f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,0f);
    }

    /**
     * getLeftSlideAnimationOut
     * @return
     */
    public static Animation getLeftSlideAnimationOut(){
        return  new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,-1f,Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,0f);
    }

    /**
     * getRightSlideAnimationIn
     * @return
     */
    public static Animation getRightSlideAnimationIn(){
        return new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                1f,Animation.RELATIVE_TO_SELF,0f,Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,0f);
    }

    /**
     * getRightSlideAnimationOut
     * @return
     */
    public static Animation getRightSlideAnimationOut(){
        return  new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,1f,Animation.RELATIVE_TO_SELF,
                0f,Animation.RELATIVE_TO_SELF,0f);
    }

    /**
     * 执行摇晃动画
     * @param shakeView
     */
    public static void shake(View shakeView){
        if(shakeView==null)return;
        shakeView.clearAnimation();
        TranslateAnimation animation=new TranslateAnimation(0,0,0,15.0f);
        animation.setInterpolator(new CycleInterpolator(4.0f));
        animation.setDuration(900);
        animation.setRepeatMode(Animation.INFINITE);
        shakeView.startAnimation(animation);
    }
}
