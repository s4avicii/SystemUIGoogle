package com.android.systemui.scrim;

import android.animation.ValueAnimator;
import com.android.internal.graphics.ColorUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ScrimDrawable$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ ScrimDrawable f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ ScrimDrawable$$ExternalSyntheticLambda0(ScrimDrawable scrimDrawable, int i, int i2) {
        this.f$0 = scrimDrawable;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        ScrimDrawable scrimDrawable = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        Objects.requireNonNull(scrimDrawable);
        scrimDrawable.mMainColor = ColorUtils.blendARGB(i, i2, ((Float) valueAnimator.getAnimatedValue()).floatValue());
        scrimDrawable.invalidateSelf();
    }
}
