package com.google.android.material.progressindicator;

import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.util.Property;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;
import com.google.android.material.progressindicator.BaseProgressIndicator;
import java.util.ArrayList;
import java.util.Objects;

public abstract class DrawableWithAnimatedVisibilityChange extends Drawable implements Animatable2Compat {
    public static final C20703 GROW_FRACTION = new Property<DrawableWithAnimatedVisibilityChange, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            return Float.valueOf(((DrawableWithAnimatedVisibilityChange) obj).getGrowFraction());
        }

        public final void set(Object obj, Object obj2) {
            DrawableWithAnimatedVisibilityChange drawableWithAnimatedVisibilityChange = (DrawableWithAnimatedVisibilityChange) obj;
            float floatValue = ((Float) obj2).floatValue();
            Objects.requireNonNull(drawableWithAnimatedVisibilityChange);
            if (drawableWithAnimatedVisibilityChange.growFraction != floatValue) {
                drawableWithAnimatedVisibilityChange.growFraction = floatValue;
                drawableWithAnimatedVisibilityChange.invalidateSelf();
            }
        }
    };
    public ArrayList animationCallbacks;
    public AnimatorDurationScaleProvider animatorDurationScaleProvider;
    public final BaseProgressIndicatorSpec baseSpec;
    public final Context context;
    public float growFraction;
    public ValueAnimator hideAnimator;
    public boolean ignoreCallbacks;
    public float mockGrowFraction;
    public boolean mockHideAnimationRunning;
    public boolean mockShowAnimationRunning;
    public final Paint paint = new Paint();
    public ValueAnimator showAnimator;
    public int totalAlpha;

    public final int getOpacity() {
        return -3;
    }

    public final boolean setVisible(boolean z, boolean z2) {
        return setVisible(z, z2, true);
    }

    public final void start() {
        setVisibleInternal(true, true, false);
    }

    public final void stop() {
        setVisibleInternal(false, true, false);
    }

    public final void clearAnimationCallbacks() {
        this.animationCallbacks.clear();
        this.animationCallbacks = null;
    }

    public final float getGrowFraction() {
        boolean z;
        BaseProgressIndicatorSpec baseProgressIndicatorSpec = this.baseSpec;
        Objects.requireNonNull(baseProgressIndicatorSpec);
        boolean z2 = true;
        if (baseProgressIndicatorSpec.showAnimationBehavior != 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            BaseProgressIndicatorSpec baseProgressIndicatorSpec2 = this.baseSpec;
            Objects.requireNonNull(baseProgressIndicatorSpec2);
            if (baseProgressIndicatorSpec2.hideAnimationBehavior == 0) {
                z2 = false;
            }
            if (!z2) {
                return 1.0f;
            }
        }
        if (this.mockHideAnimationRunning || this.mockShowAnimationRunning) {
            return this.mockGrowFraction;
        }
        return this.growFraction;
    }

    public final boolean isHiding() {
        ValueAnimator valueAnimator = this.hideAnimator;
        if ((valueAnimator == null || !valueAnimator.isRunning()) && !this.mockHideAnimationRunning) {
            return false;
        }
        return true;
    }

    public final boolean isShowing() {
        ValueAnimator valueAnimator = this.showAnimator;
        if ((valueAnimator == null || !valueAnimator.isRunning()) && !this.mockShowAnimationRunning) {
            return false;
        }
        return true;
    }

    public final void registerAnimationCallback(Animatable2Compat.AnimationCallback animationCallback) {
        if (this.animationCallbacks == null) {
            this.animationCallbacks = new ArrayList();
        }
        if (!this.animationCallbacks.contains(animationCallback)) {
            this.animationCallbacks.add(animationCallback);
        }
    }

    public final void setAlpha(int i) {
        this.totalAlpha = i;
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.paint.setColorFilter(colorFilter);
        invalidateSelf();
    }

    public void setMockHideAnimationRunning(boolean z, float f) {
        this.mockHideAnimationRunning = z;
        this.mockGrowFraction = f;
    }

    public void setMockShowAnimationRunning(boolean z, float f) {
        this.mockShowAnimationRunning = z;
        this.mockGrowFraction = f;
    }

    public final boolean setVisible(boolean z, boolean z2, boolean z3) {
        AnimatorDurationScaleProvider animatorDurationScaleProvider2 = this.animatorDurationScaleProvider;
        ContentResolver contentResolver = this.context.getContentResolver();
        Objects.requireNonNull(animatorDurationScaleProvider2);
        return setVisibleInternal(z, z2, z3 && Settings.Global.getFloat(contentResolver, "animator_duration_scale", 1.0f) > 0.0f);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:47:0x00c9, code lost:
        if (r6.showAnimationBehavior != 0) goto L_0x00cb;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x00cd, code lost:
        r6 = false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x00d6, code lost:
        if (r6.hideAnimationBehavior != 0) goto L_0x00cb;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean setVisibleInternal(boolean r6, boolean r7, boolean r8) {
        /*
            r5 = this;
            android.animation.ValueAnimator r0 = r5.showAnimator
            r1 = 500(0x1f4, double:2.47E-321)
            r3 = 2
            if (r0 != 0) goto L_0x003b
            com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange$3 r0 = GROW_FRACTION
            float[] r4 = new float[r3]
            r4 = {0, 1065353216} // fill-array
            android.animation.ObjectAnimator r0 = android.animation.ObjectAnimator.ofFloat(r5, r0, r4)
            r5.showAnimator = r0
            r0.setDuration(r1)
            android.animation.ValueAnimator r0 = r5.showAnimator
            androidx.interpolator.view.animation.FastOutSlowInInterpolator r4 = com.google.android.material.animation.AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR
            r0.setInterpolator(r4)
            android.animation.ValueAnimator r0 = r5.showAnimator
            if (r0 == 0) goto L_0x0031
            boolean r4 = r0.isRunning()
            if (r4 != 0) goto L_0x0029
            goto L_0x0031
        L_0x0029:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Cannot set showAnimator while the current showAnimator is running."
            r5.<init>(r6)
            throw r5
        L_0x0031:
            r5.showAnimator = r0
            com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange$1 r4 = new com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange$1
            r4.<init>()
            r0.addListener(r4)
        L_0x003b:
            android.animation.ValueAnimator r0 = r5.hideAnimator
            if (r0 != 0) goto L_0x0073
            com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange$3 r0 = GROW_FRACTION
            float[] r3 = new float[r3]
            r3 = {1065353216, 0} // fill-array
            android.animation.ObjectAnimator r0 = android.animation.ObjectAnimator.ofFloat(r5, r0, r3)
            r5.hideAnimator = r0
            r0.setDuration(r1)
            android.animation.ValueAnimator r0 = r5.hideAnimator
            androidx.interpolator.view.animation.FastOutSlowInInterpolator r1 = com.google.android.material.animation.AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR
            r0.setInterpolator(r1)
            android.animation.ValueAnimator r0 = r5.hideAnimator
            if (r0 == 0) goto L_0x0069
            boolean r1 = r0.isRunning()
            if (r1 != 0) goto L_0x0061
            goto L_0x0069
        L_0x0061:
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException
            java.lang.String r6 = "Cannot set hideAnimator while the current hideAnimator is running."
            r5.<init>(r6)
            throw r5
        L_0x0069:
            r5.hideAnimator = r0
            com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange$2 r1 = new com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange$2
            r1.<init>()
            r0.addListener(r1)
        L_0x0073:
            boolean r0 = r5.isVisible()
            r1 = 0
            if (r0 != 0) goto L_0x007d
            if (r6 != 0) goto L_0x007d
            return r1
        L_0x007d:
            if (r6 == 0) goto L_0x0082
            android.animation.ValueAnimator r0 = r5.showAnimator
            goto L_0x0084
        L_0x0082:
            android.animation.ValueAnimator r0 = r5.hideAnimator
        L_0x0084:
            r2 = 1
            if (r8 != 0) goto L_0x00ab
            boolean r7 = r0.isRunning()
            if (r7 == 0) goto L_0x0091
            r0.end()
            goto L_0x00a6
        L_0x0091:
            android.animation.ValueAnimator[] r7 = new android.animation.ValueAnimator[r2]
            r7[r1] = r0
            boolean r8 = r5.ignoreCallbacks
            r5.ignoreCallbacks = r2
            r0 = r1
        L_0x009a:
            if (r0 >= r2) goto L_0x00a4
            r3 = r7[r0]
            r3.end()
            int r0 = r0 + 1
            goto L_0x009a
        L_0x00a4:
            r5.ignoreCallbacks = r8
        L_0x00a6:
            boolean r5 = super.setVisible(r6, r1)
            return r5
        L_0x00ab:
            if (r8 == 0) goto L_0x00b4
            boolean r8 = r0.isRunning()
            if (r8 == 0) goto L_0x00b4
            return r1
        L_0x00b4:
            if (r6 == 0) goto L_0x00bf
            boolean r8 = super.setVisible(r6, r1)
            if (r8 == 0) goto L_0x00bd
            goto L_0x00bf
        L_0x00bd:
            r8 = r1
            goto L_0x00c0
        L_0x00bf:
            r8 = r2
        L_0x00c0:
            if (r6 == 0) goto L_0x00cf
            com.google.android.material.progressindicator.BaseProgressIndicatorSpec r6 = r5.baseSpec
            java.util.Objects.requireNonNull(r6)
            int r6 = r6.showAnimationBehavior
            if (r6 == 0) goto L_0x00cd
        L_0x00cb:
            r6 = r2
            goto L_0x00d9
        L_0x00cd:
            r6 = r1
            goto L_0x00d9
        L_0x00cf:
            com.google.android.material.progressindicator.BaseProgressIndicatorSpec r6 = r5.baseSpec
            java.util.Objects.requireNonNull(r6)
            int r6 = r6.hideAnimationBehavior
            if (r6 == 0) goto L_0x00cd
            goto L_0x00cb
        L_0x00d9:
            if (r6 != 0) goto L_0x00f0
            android.animation.ValueAnimator[] r6 = new android.animation.ValueAnimator[r2]
            r6[r1] = r0
            boolean r7 = r5.ignoreCallbacks
            r5.ignoreCallbacks = r2
        L_0x00e3:
            if (r1 >= r2) goto L_0x00ed
            r0 = r6[r1]
            r0.end()
            int r1 = r1 + 1
            goto L_0x00e3
        L_0x00ed:
            r5.ignoreCallbacks = r7
            return r8
        L_0x00f0:
            if (r7 != 0) goto L_0x00fd
            boolean r5 = r0.isPaused()
            if (r5 != 0) goto L_0x00f9
            goto L_0x00fd
        L_0x00f9:
            r0.resume()
            goto L_0x0100
        L_0x00fd:
            r0.start()
        L_0x0100:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.progressindicator.DrawableWithAnimatedVisibilityChange.setVisibleInternal(boolean, boolean, boolean):boolean");
    }

    public final boolean unregisterAnimationCallback(BaseProgressIndicator.C20624 r2) {
        ArrayList arrayList = this.animationCallbacks;
        if (arrayList == null || !arrayList.contains(r2)) {
            return false;
        }
        this.animationCallbacks.remove(r2);
        if (!this.animationCallbacks.isEmpty()) {
            return true;
        }
        this.animationCallbacks = null;
        return true;
    }

    public DrawableWithAnimatedVisibilityChange(Context context2, BaseProgressIndicatorSpec baseProgressIndicatorSpec) {
        this.context = context2;
        this.baseSpec = baseProgressIndicatorSpec;
        this.animatorDurationScaleProvider = new AnimatorDurationScaleProvider();
        setAlpha(255);
    }

    public final boolean isRunning() {
        if (isShowing() || isHiding()) {
            return true;
        }
        return false;
    }

    public final int getAlpha() {
        return this.totalAlpha;
    }
}
