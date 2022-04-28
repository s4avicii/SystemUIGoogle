package com.android.p012wm.shell.startingsurface;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.window.SplashScreenView;

/* renamed from: com.android.wm.shell.startingsurface.SplashscreenIconDrawableFactory$AnimatableIconAnimateListener */
public final class SplashscreenIconDrawableFactory$AnimatableIconAnimateListener extends SplashscreenIconDrawableFactory$AdaptiveForegroundDrawable implements SplashScreenView.IconAnimateListener {
    public Animatable mAnimatableIcon;
    public boolean mAnimationTriggered;
    public final C19312 mCallback;
    public ValueAnimator mIconAnimator;
    public AnimatorListenerAdapter mJankMonitoringListener;

    public final void draw(Canvas canvas) {
        if (!this.mAnimationTriggered) {
            ValueAnimator valueAnimator = this.mIconAnimator;
            if (valueAnimator != null && !valueAnimator.isRunning()) {
                this.mIconAnimator.start();
            }
            this.mAnimationTriggered = true;
        }
        super.draw(canvas);
    }

    public final boolean prepareAnimate(long j, final Runnable runnable) {
        this.mAnimatableIcon = (Animatable) this.mForegroundDrawable;
        ValueAnimator ofInt = ValueAnimator.ofInt(new int[]{0, 1});
        this.mIconAnimator = ofInt;
        ofInt.setDuration(j);
        this.mIconAnimator.addListener(new Animator.AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
                SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.mAnimatableIcon.stop();
                AnimatorListenerAdapter animatorListenerAdapter = SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.mJankMonitoringListener;
                if (animatorListenerAdapter != null) {
                    animatorListenerAdapter.onAnimationCancel(animator);
                }
            }

            public final void onAnimationEnd(Animator animator) {
                SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.mAnimatableIcon.stop();
                AnimatorListenerAdapter animatorListenerAdapter = SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.mJankMonitoringListener;
                if (animatorListenerAdapter != null) {
                    animatorListenerAdapter.onAnimationEnd(animator);
                }
            }

            public final void onAnimationRepeat(Animator animator) {
                SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.mAnimatableIcon.stop();
            }

            public final void onAnimationStart(Animator animator) {
                Runnable runnable = runnable;
                if (runnable != null) {
                    runnable.run();
                }
                try {
                    AnimatorListenerAdapter animatorListenerAdapter = SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.mJankMonitoringListener;
                    if (animatorListenerAdapter != null) {
                        animatorListenerAdapter.onAnimationStart(animator);
                    }
                    SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.mAnimatableIcon.start();
                } catch (Exception e) {
                    Log.e("ShellStartingWindow", "Error while running the splash screen animated icon", e);
                    animator.cancel();
                }
            }
        });
        return true;
    }

    public final void stopAnimation() {
        ValueAnimator valueAnimator = this.mIconAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mIconAnimator.end();
            this.mJankMonitoringListener = null;
        }
    }

    public SplashscreenIconDrawableFactory$AnimatableIconAnimateListener(Drawable drawable) {
        super(drawable);
        C19312 r1 = new Drawable.Callback() {
            public final void invalidateDrawable(Drawable drawable) {
                SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.invalidateSelf();
            }

            public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
                SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.scheduleSelf(runnable, j);
            }

            public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
                SplashscreenIconDrawableFactory$AnimatableIconAnimateListener.this.unscheduleSelf(runnable);
            }
        };
        this.mCallback = r1;
        this.mForegroundDrawable.setCallback(r1);
    }

    public final void setAnimationJankMonitoring(AnimatorListenerAdapter animatorListenerAdapter) {
        this.mJankMonitoringListener = animatorListenerAdapter;
    }
}
