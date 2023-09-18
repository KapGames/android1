package com.example.mathchampionsleague;

import android.animation.ValueAnimator;
import android.view.View;

public class MyAnimations {
    public static void pop(View v){
        ValueAnimator popanimation;
        //animation
        int intensity = 10;
        popanimation = ValueAnimator.ofInt(100 , 120,100,120,100);
        popanimation.setDuration(2000);
        popanimation.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        v.setScaleY(value / 100f);
                        v.setScaleX(value / 100f);

                    }
                }
        );
        popanimation.start();

    }
    public static void shake(View v) {
        ValueAnimator startButtonAnimator;

        int animationIntensity = 10;
        startButtonAnimator = ValueAnimator.ofInt(-1 * animationIntensity, 1 * animationIntensity, -1 * animationIntensity, 1 * animationIntensity, 0);
        startButtonAnimator.setDuration(2000);
        startButtonAnimator.addUpdateListener(
                new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int value = (int) animation.getAnimatedValue();
                        v.setRotation(value);
                    }
                }
        );
        startButtonAnimator.start();
    }
}
