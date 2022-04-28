package com.android.systemui.statusbar.events;

import android.animation.ValueAnimator;

/* compiled from: SystemStatusAnimationScheduler.kt */
public interface SystemStatusAnimationCallback {
    void onHidePersistentDot() {
    }

    void onSystemChromeAnimationEnd() {
    }

    void onSystemChromeAnimationStart() {
    }

    void onSystemChromeAnimationUpdate(ValueAnimator valueAnimator) {
    }

    void onSystemStatusAnimationTransitionToPersistentDot(String str) {
    }
}
