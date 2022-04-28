package com.android.systemui.statusbar.phone;

import android.animation.ValueAnimator;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: UnlockedScreenOffAnimationController.kt */
public final class UnlockedScreenOffAnimationController$lightRevealAnimator$1$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ UnlockedScreenOffAnimationController this$0;

    public UnlockedScreenOffAnimationController$lightRevealAnimator$1$1(UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        this.this$0 = unlockedScreenOffAnimationController;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        boolean z;
        LightRevealScrim lightRevealScrim = this.this$0.lightRevealScrim;
        LightRevealScrim lightRevealScrim2 = null;
        if (lightRevealScrim == null) {
            lightRevealScrim = null;
        }
        Object animatedValue = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        lightRevealScrim.setRevealAmount(((Float) animatedValue).floatValue());
        UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
        float animatedFraction = 1.0f - valueAnimator.getAnimatedFraction();
        Object animatedValue2 = valueAnimator.getAnimatedValue();
        Objects.requireNonNull(animatedValue2, "null cannot be cast to non-null type kotlin.Float");
        float floatValue = 1.0f - ((Float) animatedValue2).floatValue();
        Objects.requireNonNull(unlockedScreenOffAnimationController);
        Iterator<UnlockedScreenOffAnimationController.Callback> it = unlockedScreenOffAnimationController.callbacks.iterator();
        while (it.hasNext()) {
            it.next().onUnlockedScreenOffProgressUpdate(animatedFraction, floatValue);
        }
        LightRevealScrim lightRevealScrim3 = this.this$0.lightRevealScrim;
        if (lightRevealScrim3 != null) {
            lightRevealScrim2 = lightRevealScrim3;
        }
        Objects.requireNonNull(lightRevealScrim2);
        if (lightRevealScrim2.interpolatedRevealAmount < 0.1f) {
            z = true;
        } else {
            z = false;
        }
        if (z && this.this$0.interactionJankMonitor.isInstrumenting(40)) {
            this.this$0.interactionJankMonitor.end(40);
        }
    }
}
