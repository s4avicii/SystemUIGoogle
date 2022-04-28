package com.android.systemui.statusbar.events;

import android.animation.ValueAnimator;
import java.util.Objects;

/* compiled from: SystemStatusAnimationScheduler.kt */
public final class SystemStatusAnimationScheduler$systemUpdateListener$1 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ SystemStatusAnimationScheduler this$0;

    public SystemStatusAnimationScheduler$systemUpdateListener$1(SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        this.this$0 = systemStatusAnimationScheduler;
    }

    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        SystemStatusAnimationScheduler systemStatusAnimationScheduler = this.this$0;
        Objects.requireNonNull(systemStatusAnimationScheduler);
        for (SystemStatusAnimationCallback onSystemChromeAnimationUpdate : systemStatusAnimationScheduler.listeners) {
            onSystemChromeAnimationUpdate.onSystemChromeAnimationUpdate(valueAnimator);
        }
    }
}
