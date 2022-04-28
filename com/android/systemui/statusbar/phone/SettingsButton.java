package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import com.android.keyguard.AlphaOptimizedImageButton;
import com.android.systemui.animation.Interpolators;
import java.util.Objects;

public class SettingsButton extends AlphaOptimizedImageButton {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ObjectAnimator mAnimator;
    public final C15303 mLongPressCallback = new Runnable() {
        public final void run() {
            SettingsButton.this.startAccelSpin();
        }
    };
    public float mSlop = ((float) ViewConfiguration.get(getContext()).getScaledTouchSlop());
    public boolean mUpToSpeed;

    public final void startAccelSpin() {
        ObjectAnimator objectAnimator = this.mAnimator;
        if (objectAnimator != null) {
            objectAnimator.removeAllListeners();
            this.mAnimator.cancel();
            this.mAnimator = null;
        }
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this, View.ROTATION, new float[]{0.0f, 360.0f});
        this.mAnimator = ofFloat;
        ofFloat.setInterpolator(AnimationUtils.loadInterpolator(this.mContext, 17563648));
        this.mAnimator.setDuration(750);
        this.mAnimator.addListener(new Animator.AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
            }

            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
            }

            public final void onAnimationEnd(Animator animator) {
                SettingsButton settingsButton = SettingsButton.this;
                Objects.requireNonNull(settingsButton);
                ObjectAnimator objectAnimator = settingsButton.mAnimator;
                if (objectAnimator != null) {
                    objectAnimator.removeAllListeners();
                    settingsButton.mAnimator.cancel();
                    settingsButton.mAnimator = null;
                }
                settingsButton.performHapticFeedback(0);
                settingsButton.mUpToSpeed = true;
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(settingsButton, View.ROTATION, new float[]{0.0f, 360.0f});
                settingsButton.mAnimator = ofFloat;
                ofFloat.setInterpolator(Interpolators.LINEAR);
                settingsButton.mAnimator.setDuration(375);
                settingsButton.mAnimator.setRepeatCount(-1);
                settingsButton.mAnimator.start();
            }
        });
        this.mAnimator.start();
    }

    public SettingsButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked != 1) {
            if (actionMasked == 2) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                float f = -this.mSlop;
                if (x < f || y < f || x > ((float) getWidth()) + this.mSlop || y > ((float) getHeight()) + this.mSlop) {
                    ObjectAnimator objectAnimator = this.mAnimator;
                    if (objectAnimator != null) {
                        objectAnimator.removeAllListeners();
                        this.mAnimator.cancel();
                        this.mAnimator = null;
                    }
                    this.mUpToSpeed = false;
                    removeCallbacks(this.mLongPressCallback);
                }
            } else if (actionMasked == 3) {
                ObjectAnimator objectAnimator2 = this.mAnimator;
                if (objectAnimator2 != null) {
                    objectAnimator2.removeAllListeners();
                    this.mAnimator.cancel();
                    this.mAnimator = null;
                }
                this.mUpToSpeed = false;
                removeCallbacks(this.mLongPressCallback);
            }
        } else if (this.mUpToSpeed) {
            animate().translationX(((float) ((View) getParent().getParent()).getWidth()) - getX()).alpha(0.0f).setDuration(350).setInterpolator(AnimationUtils.loadInterpolator(this.mContext, 17563650)).setListener(new Animator.AnimatorListener() {
                public final void onAnimationCancel(Animator animator) {
                }

                public final void onAnimationRepeat(Animator animator) {
                }

                public final void onAnimationStart(Animator animator) {
                }

                public final void onAnimationEnd(Animator animator) {
                    SettingsButton.this.setAlpha(1.0f);
                    SettingsButton.this.setTranslationX(0.0f);
                    SettingsButton settingsButton = SettingsButton.this;
                    int i = SettingsButton.$r8$clinit;
                    Objects.requireNonNull(settingsButton);
                    ObjectAnimator objectAnimator = settingsButton.mAnimator;
                    if (objectAnimator != null) {
                        objectAnimator.removeAllListeners();
                        settingsButton.mAnimator.cancel();
                        settingsButton.mAnimator = null;
                    }
                    settingsButton.mUpToSpeed = false;
                    settingsButton.removeCallbacks(settingsButton.mLongPressCallback);
                    SettingsButton.this.animate().setListener((Animator.AnimatorListener) null);
                }
            }).start();
        } else {
            ObjectAnimator objectAnimator3 = this.mAnimator;
            if (objectAnimator3 != null) {
                objectAnimator3.removeAllListeners();
                this.mAnimator.cancel();
                this.mAnimator = null;
            }
            this.mUpToSpeed = false;
            removeCallbacks(this.mLongPressCallback);
        }
        return super.onTouchEvent(motionEvent);
    }
}
