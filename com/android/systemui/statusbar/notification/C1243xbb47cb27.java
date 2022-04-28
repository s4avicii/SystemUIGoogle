package com.android.systemui.statusbar.notification;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;

/* renamed from: com.android.systemui.statusbar.notification.ViewGroupFadeHelper$Companion$fadeOutAllChildrenExcept$animator$1$2 */
/* compiled from: ViewGroupFadeHelper.kt */
public final class C1243xbb47cb27 extends AnimatorListenerAdapter {
    public final /* synthetic */ Runnable $endRunnable;

    public C1243xbb47cb27(Runnable runnable) {
        this.$endRunnable = runnable;
    }

    public final void onAnimationEnd(Animator animator) {
        Runnable runnable = this.$endRunnable;
        if (runnable != null) {
            runnable.run();
        }
    }
}
