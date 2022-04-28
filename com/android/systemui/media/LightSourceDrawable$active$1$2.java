package com.android.systemui.media;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import java.util.Objects;

/* compiled from: LightSourceDrawable.kt */
public final class LightSourceDrawable$active$1$2 extends AnimatorListenerAdapter {
    public boolean cancelled;
    public final /* synthetic */ LightSourceDrawable this$0;

    public final void onAnimationCancel(Animator animator) {
        this.cancelled = true;
    }

    public LightSourceDrawable$active$1$2(LightSourceDrawable lightSourceDrawable) {
        this.this$0 = lightSourceDrawable;
    }

    public final void onAnimationEnd(Animator animator) {
        if (!this.cancelled) {
            RippleData access$getRippleData$p = this.this$0.rippleData;
            Objects.requireNonNull(access$getRippleData$p);
            access$getRippleData$p.progress = 0.0f;
            RippleData access$getRippleData$p2 = this.this$0.rippleData;
            Objects.requireNonNull(access$getRippleData$p2);
            access$getRippleData$p2.alpha = 0.0f;
            this.this$0.rippleAnimation = null;
            this.this$0.invalidateSelf();
        }
    }
}
