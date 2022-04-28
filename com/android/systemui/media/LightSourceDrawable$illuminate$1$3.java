package com.android.systemui.media;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import java.util.Objects;

/* compiled from: LightSourceDrawable.kt */
public final class LightSourceDrawable$illuminate$1$3 extends AnimatorListenerAdapter {
    public final /* synthetic */ LightSourceDrawable this$0;

    public LightSourceDrawable$illuminate$1$3(LightSourceDrawable lightSourceDrawable) {
        this.this$0 = lightSourceDrawable;
    }

    public final void onAnimationEnd(Animator animator) {
        RippleData access$getRippleData$p = this.this$0.rippleData;
        Objects.requireNonNull(access$getRippleData$p);
        access$getRippleData$p.progress = 0.0f;
        this.this$0.rippleAnimation = null;
        this.this$0.invalidateSelf();
    }
}
