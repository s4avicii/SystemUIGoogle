package com.google.android.material.floatingactionbutton;

import android.animation.Animator;
import android.animation.AnimatorSet;

public interface MotionStrategy {
    AnimatorSet createAnimator();

    int getDefaultMotionSpecResource();

    void onAnimationCancel();

    void onAnimationEnd();

    void onAnimationStart(Animator animator);

    void onChange();

    void performNow();

    boolean shouldCancel();
}
