package com.android.systemui.statusbar.events;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import java.util.Objects;

/* compiled from: SystemStatusAnimationScheduler.kt */
public final class SystemStatusAnimationScheduler$systemAnimatorAdapter$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ SystemStatusAnimationScheduler this$0;

    public SystemStatusAnimationScheduler$systemAnimatorAdapter$1(SystemStatusAnimationScheduler systemStatusAnimationScheduler) {
        this.this$0 = systemStatusAnimationScheduler;
    }

    public final void onAnimationEnd(Animator animator) {
        SystemStatusAnimationScheduler systemStatusAnimationScheduler = this.this$0;
        Objects.requireNonNull(systemStatusAnimationScheduler);
        for (SystemStatusAnimationCallback onSystemChromeAnimationEnd : systemStatusAnimationScheduler.listeners) {
            onSystemChromeAnimationEnd.onSystemChromeAnimationEnd();
        }
    }

    public final void onAnimationStart(Animator animator) {
        SystemStatusAnimationScheduler systemStatusAnimationScheduler = this.this$0;
        Objects.requireNonNull(systemStatusAnimationScheduler);
        for (SystemStatusAnimationCallback onSystemChromeAnimationStart : systemStatusAnimationScheduler.listeners) {
            onSystemChromeAnimationStart.onSystemChromeAnimationStart();
        }
    }
}
