package com.android.settingslib.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.view.RenderNodeAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import com.android.keyguard.KeyguardInputView;
import com.android.p012wm.shell.C1777R;

public class AppearAnimationUtils implements AppearAnimationCreator<View> {
    public boolean mAppearing;
    public final float mDelayScale;
    public final long mDuration;
    public final Interpolator mInterpolator;
    public final AppearAnimationProperties mProperties = new AppearAnimationProperties();
    public RowTranslationScaler mRowTranslationScaler;
    public final float mStartTranslation;

    public class AppearAnimationProperties {
        public long[][] delays;
        public int maxDelayColIndex;
        public int maxDelayRowIndex;
    }

    public interface RowTranslationScaler {
    }

    public final /* bridge */ /* synthetic */ void createAnimation(Object obj, long j, long j2, float f, boolean z, Interpolator interpolator, Runnable runnable) {
        createAnimation((View) obj, j, j2, f, z, interpolator, runnable);
    }

    public static void createAnimation(final View view, long j, long j2, float f, boolean z, Interpolator interpolator, Runnable runnable) {
        RenderNodeAnimator renderNodeAnimator;
        final Runnable runnable2 = runnable;
        if (view != null) {
            float f2 = 1.0f;
            view.setAlpha(z ? 0.0f : 1.0f);
            view.setTranslationY(z ? f : 0.0f);
            if (!z) {
                f2 = 0.0f;
            }
            if (view.isHardwareAccelerated()) {
                renderNodeAnimator = new RenderNodeAnimator(11, f2);
                renderNodeAnimator.setTarget(view);
            } else {
                renderNodeAnimator = ObjectAnimator.ofFloat(view, View.ALPHA, new float[]{view.getAlpha(), f2});
            }
            renderNodeAnimator.setInterpolator(interpolator);
            renderNodeAnimator.setDuration(j2);
            long j3 = j;
            renderNodeAnimator.setStartDelay(j);
            if (view.hasOverlappingRendering()) {
                view.setLayerType(2, (Paint) null);
                renderNodeAnimator.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        view.setLayerType(0, (Paint) null);
                    }
                });
            }
            if (runnable2 != null) {
                renderNodeAnimator.addListener(new AnimatorListenerAdapter() {
                    public final void onAnimationEnd(Animator animator) {
                        runnable2.run();
                    }
                });
            }
            renderNodeAnimator.start();
            startTranslationYAnimation(view, j, j2, z ? 0.0f : f, interpolator, (KeyguardInputView.C05011) null);
        }
    }

    public long calculateDelay(int i, int i2) {
        return (long) ((((Math.pow((double) i, 0.4d) + 0.4d) * ((double) i2) * 20.0d) + ((double) (i * 40))) * ((double) this.mDelayScale));
    }

    public final <T> void startAnimation2d(T[][] tArr, Runnable runnable, AppearAnimationCreator<T> appearAnimationCreator) {
        float f;
        Runnable runnable2;
        float f2;
        T[][] tArr2 = tArr;
        AppearAnimationProperties appearAnimationProperties = this.mProperties;
        appearAnimationProperties.maxDelayColIndex = -1;
        appearAnimationProperties.maxDelayRowIndex = -1;
        appearAnimationProperties.delays = new long[tArr2.length][];
        long j = -1;
        for (int i = 0; i < tArr2.length; i++) {
            T[] tArr3 = tArr2[i];
            this.mProperties.delays[i] = new long[tArr3.length];
            for (int i2 = 0; i2 < tArr3.length; i2++) {
                long calculateDelay = calculateDelay(i, i2);
                AppearAnimationProperties appearAnimationProperties2 = this.mProperties;
                appearAnimationProperties2.delays[i][i2] = calculateDelay;
                if (tArr2[i][i2] != null && calculateDelay > j) {
                    appearAnimationProperties2.maxDelayColIndex = i2;
                    appearAnimationProperties2.maxDelayRowIndex = i;
                    j = calculateDelay;
                }
            }
        }
        AppearAnimationProperties appearAnimationProperties3 = this.mProperties;
        if (appearAnimationProperties3.maxDelayRowIndex == -1 || appearAnimationProperties3.maxDelayColIndex == -1) {
            runnable.run();
            return;
        }
        int i3 = 0;
        while (true) {
            long[][] jArr = appearAnimationProperties3.delays;
            if (i3 < jArr.length) {
                long[] jArr2 = jArr[i3];
                if (this.mRowTranslationScaler != null) {
                    int length = jArr.length;
                    f = (float) (Math.pow((double) (length - i3), 2.0d) / ((double) length));
                } else {
                    f = 1.0f;
                }
                float f3 = f * this.mStartTranslation;
                for (int i4 = 0; i4 < jArr2.length; i4++) {
                    long j2 = jArr2[i4];
                    if (appearAnimationProperties3.maxDelayRowIndex == i3 && appearAnimationProperties3.maxDelayColIndex == i4) {
                        runnable2 = runnable;
                    } else {
                        runnable2 = null;
                    }
                    T t = tArr2[i3][i4];
                    long j3 = this.mDuration;
                    boolean z = this.mAppearing;
                    if (z) {
                        f2 = f3;
                    } else {
                        f2 = -f3;
                    }
                    appearAnimationCreator.createAnimation(t, j2, j3, f2, z, this.mInterpolator, runnable2);
                }
                i3++;
            } else {
                return;
            }
        }
    }

    public AppearAnimationUtils(Context context, long j, float f, float f2, Interpolator interpolator) {
        this.mInterpolator = interpolator;
        this.mStartTranslation = ((float) context.getResources().getDimensionPixelOffset(C1777R.dimen.appear_y_translation_start)) * f;
        this.mDelayScale = f2;
        this.mDuration = j;
        this.mAppearing = true;
    }

    public static void startTranslationYAnimation(View view, long j, long j2, float f, Interpolator interpolator, KeyguardInputView.C05011 r12) {
        RenderNodeAnimator renderNodeAnimator;
        if (view.isHardwareAccelerated()) {
            renderNodeAnimator = new RenderNodeAnimator(1, f);
            renderNodeAnimator.setTarget(view);
        } else {
            renderNodeAnimator = ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, new float[]{view.getTranslationY(), f});
        }
        renderNodeAnimator.setInterpolator(interpolator);
        renderNodeAnimator.setDuration(j2);
        renderNodeAnimator.setStartDelay(j);
        if (r12 != null) {
            renderNodeAnimator.addListener(r12);
        }
        renderNodeAnimator.start();
    }
}
