package com.google.android.systemui.assist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.animation.Interpolators;
import com.google.android.systemui.elmyra.feedback.FeedbackEffect;
import com.google.android.systemui.elmyra.sensors.GestureSensor;
import java.util.ArrayList;
import java.util.Objects;

public class LockscreenOpaLayout extends FrameLayout implements FeedbackEffect {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final PathInterpolator INTERPOLATOR_5_100 = new PathInterpolator(1.0f, 0.0f, 0.95f, 1.0f);
    public final ArrayList<View> mAnimatedViews = new ArrayList<>();
    public View mBlue;
    public AnimatorSet mCannedAnimatorSet;
    public final ArraySet<Animator> mCurrentAnimators = new ArraySet<>();
    public AnimatorSet mGestureAnimatorSet;
    public int mGestureState = 0;
    public View mGreen;
    public AnimatorSet mLineAnimatorSet;
    public View mRed;
    public Resources mResources;
    public View mYellow;

    public LockscreenOpaLayout(Context context) {
        super(context);
    }

    public final void onProgress(float f, int i) {
        AnimatorSet animatorSet;
        int i2 = this.mGestureState;
        if (i2 != 2) {
            if (i2 == 4) {
                if (!this.mCurrentAnimators.isEmpty()) {
                    int size = this.mCurrentAnimators.size();
                    while (true) {
                        size--;
                        if (size < 0) {
                            break;
                        }
                        Animator valueAt = this.mCurrentAnimators.valueAt(size);
                        valueAt.removeAllListeners();
                        valueAt.end();
                    }
                    this.mCurrentAnimators.clear();
                }
                this.mGestureState = 0;
            }
            if (f == 0.0f) {
                this.mGestureState = 0;
                return;
            }
            long max = Math.max(0, ((long) (f * 533.0f)) - 167);
            int i3 = this.mGestureState;
            if (i3 != 0) {
                if (i3 == 1) {
                    this.mGestureAnimatorSet.setCurrentPlayTime(max);
                } else if (i3 == 3 && max >= 167) {
                    this.mGestureAnimatorSet.end();
                    if (this.mGestureState == 1) {
                        this.mGestureAnimatorSet.setCurrentPlayTime(max);
                    }
                }
            } else if (isAttachedToWindow()) {
                skipToStartingValue();
                this.mGestureState = 3;
                AnimatorSet animatorSet2 = this.mCannedAnimatorSet;
                if (animatorSet2 != null) {
                    animatorSet2.removeAllListeners();
                    this.mCannedAnimatorSet.cancel();
                    animatorSet = this.mCannedAnimatorSet;
                } else {
                    this.mCannedAnimatorSet = new AnimatorSet();
                    View view = this.mRed;
                    PathInterpolator pathInterpolator = OpaUtils.INTERPOLATOR_40_40;
                    ObjectAnimator translationObjectAnimatorX = OpaUtils.getTranslationObjectAnimatorX(view, pathInterpolator, -((float) this.mResources.getDimensionPixelOffset(C1777R.dimen.opa_lockscreen_canned_ry)), this.mRed.getX(), 83);
                    translationObjectAnimatorX.setStartDelay(17);
                    ObjectAnimator translationObjectAnimatorX2 = OpaUtils.getTranslationObjectAnimatorX(this.mYellow, pathInterpolator, (float) this.mResources.getDimensionPixelOffset(C1777R.dimen.opa_lockscreen_canned_ry), this.mYellow.getX(), 83);
                    translationObjectAnimatorX2.setStartDelay(17);
                    AnimatorSet.Builder with = this.mCannedAnimatorSet.play(translationObjectAnimatorX).with(translationObjectAnimatorX2).with(OpaUtils.getTranslationObjectAnimatorX(this.mBlue, pathInterpolator, -((float) this.mResources.getDimensionPixelOffset(C1777R.dimen.opa_lockscreen_canned_bg)), this.mBlue.getX(), 167)).with(OpaUtils.getTranslationObjectAnimatorX(this.mGreen, pathInterpolator, (float) this.mResources.getDimensionPixelOffset(C1777R.dimen.opa_lockscreen_canned_bg), this.mGreen.getX(), 167));
                    View view2 = this.mRed;
                    LinearInterpolator linearInterpolator = Interpolators.LINEAR;
                    with.with(OpaUtils.getAlphaObjectAnimator(view2, 130, linearInterpolator)).with(OpaUtils.getAlphaObjectAnimator(this.mYellow, 130, linearInterpolator)).with(OpaUtils.getAlphaObjectAnimator(this.mBlue, 113, linearInterpolator)).with(OpaUtils.getAlphaObjectAnimator(this.mGreen, 113, linearInterpolator));
                    animatorSet = this.mCannedAnimatorSet;
                }
                this.mGestureAnimatorSet = animatorSet;
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        LockscreenOpaLayout lockscreenOpaLayout = LockscreenOpaLayout.this;
                        lockscreenOpaLayout.mGestureState = 1;
                        lockscreenOpaLayout.mGestureAnimatorSet = LockscreenOpaLayout.m303$$Nest$mgetLineAnimatorSet(lockscreenOpaLayout);
                        LockscreenOpaLayout.this.mGestureAnimatorSet.setCurrentPlayTime(0);
                    }
                });
                this.mGestureAnimatorSet.start();
            } else {
                skipToStartingValue();
            }
        }
    }

    public final void onRelease() {
        int i = this.mGestureState;
        if (i != 2 && i != 4) {
            if (i == 3) {
                if (this.mGestureAnimatorSet.isRunning()) {
                    this.mGestureAnimatorSet.removeAllListeners();
                    this.mGestureAnimatorSet.addListener(new AnimatorListenerAdapter() {
                        public final void onAnimationEnd(Animator animator) {
                            LockscreenOpaLayout lockscreenOpaLayout = LockscreenOpaLayout.this;
                            int i = LockscreenOpaLayout.$r8$clinit;
                            lockscreenOpaLayout.startRetractAnimation();
                        }
                    });
                    return;
                }
                this.mGestureState = 4;
                startRetractAnimation();
            } else if (i == 1) {
                startRetractAnimation();
            }
        }
    }

    public final void onResolve(GestureSensor.DetectionProperties detectionProperties) {
        int i = this.mGestureState;
        if (i != 4 && i != 2) {
            if (i == 3) {
                this.mGestureState = 2;
                this.mGestureAnimatorSet.removeAllListeners();
                this.mGestureAnimatorSet.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        LockscreenOpaLayout lockscreenOpaLayout = LockscreenOpaLayout.this;
                        lockscreenOpaLayout.mGestureAnimatorSet = LockscreenOpaLayout.m303$$Nest$mgetLineAnimatorSet(lockscreenOpaLayout);
                        LockscreenOpaLayout.this.mGestureAnimatorSet.removeAllListeners();
                        LockscreenOpaLayout.this.mGestureAnimatorSet.addListener(new AnimatorListenerAdapter() {
                            public final void onAnimationEnd(Animator animator) {
                                LockscreenOpaLayout.m304$$Nest$mstartCollapseAnimation(LockscreenOpaLayout.this);
                            }
                        });
                        LockscreenOpaLayout.this.mGestureAnimatorSet.end();
                    }
                });
                return;
            }
            AnimatorSet animatorSet = this.mGestureAnimatorSet;
            if (animatorSet != null) {
                this.mGestureState = 2;
                animatorSet.removeAllListeners();
                this.mGestureAnimatorSet.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        LockscreenOpaLayout.m304$$Nest$mstartCollapseAnimation(LockscreenOpaLayout.this);
                    }
                });
                if (!this.mGestureAnimatorSet.isStarted()) {
                    this.mGestureAnimatorSet.start();
                }
            }
        }
    }

    public final void skipToStartingValue() {
        int size = this.mAnimatedViews.size();
        for (int i = 0; i < size; i++) {
            View view = this.mAnimatedViews.get(i);
            view.setAlpha(0.0f);
            view.setTranslationX(0.0f);
        }
    }

    /* renamed from: -$$Nest$mgetLineAnimatorSet  reason: not valid java name */
    public static AnimatorSet m303$$Nest$mgetLineAnimatorSet(LockscreenOpaLayout lockscreenOpaLayout) {
        Objects.requireNonNull(lockscreenOpaLayout);
        AnimatorSet animatorSet = lockscreenOpaLayout.mLineAnimatorSet;
        if (animatorSet != null) {
            animatorSet.removeAllListeners();
            lockscreenOpaLayout.mLineAnimatorSet.cancel();
            return lockscreenOpaLayout.mLineAnimatorSet;
        }
        AnimatorSet animatorSet2 = new AnimatorSet();
        lockscreenOpaLayout.mLineAnimatorSet = animatorSet2;
        View view = lockscreenOpaLayout.mRed;
        PathInterpolator pathInterpolator = lockscreenOpaLayout.INTERPOLATOR_5_100;
        Resources resources = lockscreenOpaLayout.mResources;
        PathInterpolator pathInterpolator2 = OpaUtils.INTERPOLATOR_40_40;
        animatorSet2.play(OpaUtils.getTranslationObjectAnimatorX(view, pathInterpolator, -((float) resources.getDimensionPixelOffset(C1777R.dimen.opa_lockscreen_translation_ry)), lockscreenOpaLayout.mRed.getX(), 366)).with(OpaUtils.getTranslationObjectAnimatorX(lockscreenOpaLayout.mYellow, lockscreenOpaLayout.INTERPOLATOR_5_100, (float) lockscreenOpaLayout.mResources.getDimensionPixelOffset(C1777R.dimen.opa_lockscreen_translation_ry), lockscreenOpaLayout.mYellow.getX(), 366)).with(OpaUtils.getTranslationObjectAnimatorX(lockscreenOpaLayout.mGreen, lockscreenOpaLayout.INTERPOLATOR_5_100, (float) lockscreenOpaLayout.mResources.getDimensionPixelOffset(C1777R.dimen.opa_lockscreen_translation_bg), lockscreenOpaLayout.mGreen.getX(), 366)).with(OpaUtils.getTranslationObjectAnimatorX(lockscreenOpaLayout.mBlue, lockscreenOpaLayout.INTERPOLATOR_5_100, -((float) lockscreenOpaLayout.mResources.getDimensionPixelOffset(C1777R.dimen.opa_lockscreen_translation_bg)), lockscreenOpaLayout.mBlue.getX(), 366));
        return lockscreenOpaLayout.mLineAnimatorSet;
    }

    /* renamed from: -$$Nest$mstartCollapseAnimation  reason: not valid java name */
    public static void m304$$Nest$mstartCollapseAnimation(LockscreenOpaLayout lockscreenOpaLayout) {
        Objects.requireNonNull(lockscreenOpaLayout);
        if (lockscreenOpaLayout.isAttachedToWindow()) {
            lockscreenOpaLayout.mCurrentAnimators.clear();
            ArraySet<Animator> arraySet = lockscreenOpaLayout.mCurrentAnimators;
            ArraySet arraySet2 = new ArraySet();
            View view = lockscreenOpaLayout.mRed;
            PathInterpolator pathInterpolator = OpaUtils.INTERPOLATOR_40_OUT;
            arraySet2.add(OpaUtils.getTranslationAnimatorX(view, pathInterpolator, 133));
            arraySet2.add(OpaUtils.getTranslationAnimatorX(lockscreenOpaLayout.mBlue, pathInterpolator, 150));
            arraySet2.add(OpaUtils.getTranslationAnimatorX(lockscreenOpaLayout.mYellow, pathInterpolator, 133));
            arraySet2.add(OpaUtils.getTranslationAnimatorX(lockscreenOpaLayout.mGreen, pathInterpolator, 150));
            OpaUtils.getLongestAnim(arraySet2).addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    LockscreenOpaLayout.this.mCurrentAnimators.clear();
                    LockscreenOpaLayout lockscreenOpaLayout = LockscreenOpaLayout.this;
                    lockscreenOpaLayout.mGestureAnimatorSet = null;
                    lockscreenOpaLayout.mGestureState = 0;
                    lockscreenOpaLayout.skipToStartingValue();
                }
            });
            arraySet.addAll(arraySet2);
            ArraySet<Animator> arraySet3 = lockscreenOpaLayout.mCurrentAnimators;
            int size = arraySet3.size();
            while (true) {
                size--;
                if (size >= 0) {
                    arraySet3.valueAt(size).start();
                } else {
                    lockscreenOpaLayout.mGestureState = 2;
                    return;
                }
            }
        } else {
            lockscreenOpaLayout.skipToStartingValue();
        }
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mResources = getResources();
        this.mBlue = findViewById(C1777R.C1779id.blue);
        this.mRed = findViewById(C1777R.C1779id.red);
        this.mYellow = findViewById(C1777R.C1779id.yellow);
        this.mGreen = findViewById(C1777R.C1779id.green);
        this.mAnimatedViews.add(this.mBlue);
        this.mAnimatedViews.add(this.mRed);
        this.mAnimatedViews.add(this.mYellow);
        this.mAnimatedViews.add(this.mGreen);
    }

    public final void startRetractAnimation() {
        if (isAttachedToWindow()) {
            AnimatorSet animatorSet = this.mGestureAnimatorSet;
            if (animatorSet != null) {
                animatorSet.removeAllListeners();
                this.mGestureAnimatorSet.cancel();
            }
            this.mCurrentAnimators.clear();
            ArraySet<Animator> arraySet = this.mCurrentAnimators;
            ArraySet arraySet2 = new ArraySet();
            View view = this.mRed;
            PathInterpolator pathInterpolator = OpaUtils.INTERPOLATOR_40_OUT;
            arraySet2.add(OpaUtils.getTranslationAnimatorX(view, pathInterpolator, 190));
            arraySet2.add(OpaUtils.getTranslationAnimatorX(this.mBlue, pathInterpolator, 190));
            arraySet2.add(OpaUtils.getTranslationAnimatorX(this.mGreen, pathInterpolator, 190));
            arraySet2.add(OpaUtils.getTranslationAnimatorX(this.mYellow, pathInterpolator, 190));
            OpaUtils.getLongestAnim(arraySet2).addListener(new AnimatorListenerAdapter() {
                public final void onAnimationEnd(Animator animator) {
                    LockscreenOpaLayout.this.mCurrentAnimators.clear();
                    LockscreenOpaLayout.this.skipToStartingValue();
                    LockscreenOpaLayout lockscreenOpaLayout = LockscreenOpaLayout.this;
                    lockscreenOpaLayout.mGestureState = 0;
                    lockscreenOpaLayout.mGestureAnimatorSet = null;
                }
            });
            arraySet.addAll(arraySet2);
            ArraySet<Animator> arraySet3 = this.mCurrentAnimators;
            int size = arraySet3.size();
            while (true) {
                size--;
                if (size >= 0) {
                    arraySet3.valueAt(size).start();
                } else {
                    this.mGestureState = 4;
                    return;
                }
            }
        } else {
            skipToStartingValue();
        }
    }

    public LockscreenOpaLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public LockscreenOpaLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public LockscreenOpaLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
