package com.android.systemui.screenshot;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public final class SwipeDismissHandler implements View.OnTouchListener {
    public final SwipeDismissCallbacks mCallbacks;
    public int mDirectionX;
    public ValueAnimator mDismissAnimation;
    public DisplayMetrics mDisplayMetrics = new DisplayMetrics();
    public final GestureDetector mGestureDetector;
    public float mPreviousX;
    public float mStartX;
    public final View mView;

    public interface SwipeDismissCallbacks {
        void onDismiss();

        void onInteraction();
    }

    public class SwipeDismissGestureListener extends GestureDetector.SimpleOnGestureListener {
        public SwipeDismissGestureListener() {
        }

        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (SwipeDismissHandler.this.mView.getTranslationX() * f <= 0.0f) {
                return false;
            }
            ValueAnimator valueAnimator = SwipeDismissHandler.this.mDismissAnimation;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                return false;
            }
            SwipeDismissHandler.this.dismiss(f / 1000.0f);
            return true;
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            int i;
            SwipeDismissHandler.this.mView.setTranslationX(motionEvent2.getRawX() - SwipeDismissHandler.this.mStartX);
            SwipeDismissHandler swipeDismissHandler = SwipeDismissHandler.this;
            float rawX = motionEvent2.getRawX();
            SwipeDismissHandler swipeDismissHandler2 = SwipeDismissHandler.this;
            if (rawX < swipeDismissHandler2.mPreviousX) {
                i = -1;
            } else {
                i = 1;
            }
            swipeDismissHandler.mDirectionX = i;
            swipeDismissHandler2.mPreviousX = motionEvent2.getRawX();
            return true;
        }
    }

    public final void dismiss(float f) {
        int i;
        float min = Math.min(3.0f, Math.max(1.0f, f));
        ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
        float translationX = this.mView.getTranslationX();
        int layoutDirection = this.mView.getContext().getResources().getConfiguration().getLayoutDirection();
        int i2 = (translationX > 0.0f ? 1 : (translationX == 0.0f ? 0 : -1));
        if (i2 > 0 || (i2 == 0 && layoutDirection == 1)) {
            i = this.mDisplayMetrics.widthPixels;
        } else {
            i = this.mView.getRight() * -1;
        }
        float f2 = (float) i;
        float abs = Math.abs(f2 - translationX);
        ofFloat.addUpdateListener(new SwipeDismissHandler$$ExternalSyntheticLambda1(this, translationX, f2));
        ofFloat.setDuration((long) (abs / Math.abs(min)));
        this.mDismissAnimation = ofFloat;
        ofFloat.addListener(new AnimatorListenerAdapter() {
            public boolean mCancelled;

            public final void onAnimationCancel(Animator animator) {
                super.onAnimationCancel(animator);
                this.mCancelled = true;
            }

            public final void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (!this.mCancelled) {
                    SwipeDismissHandler.this.mCallbacks.onDismiss();
                }
            }
        });
        this.mDismissAnimation.start();
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        boolean onTouchEvent = this.mGestureDetector.onTouchEvent(motionEvent);
        this.mCallbacks.onInteraction();
        if (motionEvent.getActionMasked() == 0) {
            float rawX = motionEvent.getRawX();
            this.mStartX = rawX;
            this.mPreviousX = rawX;
            return true;
        } else if (motionEvent.getActionMasked() != 1) {
            return onTouchEvent;
        } else {
            ValueAnimator valueAnimator = this.mDismissAnimation;
            if (valueAnimator != null && valueAnimator.isRunning()) {
                return true;
            }
            float translationX = this.mView.getTranslationX();
            boolean z = false;
            if (((float) this.mDirectionX) * translationX > 0.0f && Math.abs(translationX) >= FloatingWindowUtil.dpToPx(this.mDisplayMetrics, 20.0f)) {
                z = true;
            }
            if (z) {
                dismiss(1.0f);
            } else {
                ValueAnimator ofFloat = ValueAnimator.ofFloat(new float[]{0.0f, 1.0f});
                ofFloat.addUpdateListener(new SwipeDismissHandler$$ExternalSyntheticLambda0(this, this.mView.getTranslationX()));
                ofFloat.start();
            }
            return true;
        }
    }

    public SwipeDismissHandler(Context context, View view, SwipeDismissCallbacks swipeDismissCallbacks) {
        this.mView = view;
        this.mCallbacks = swipeDismissCallbacks;
        this.mGestureDetector = new GestureDetector(context, new SwipeDismissGestureListener());
        context.getDisplay().getRealMetrics(this.mDisplayMetrics);
    }
}
