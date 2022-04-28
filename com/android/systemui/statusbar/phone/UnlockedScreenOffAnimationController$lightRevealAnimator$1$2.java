package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.statusbar.LightRevealScrim;
import com.android.systemui.statusbar.phone.UnlockedScreenOffAnimationController;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: UnlockedScreenOffAnimationController.kt */
public final class UnlockedScreenOffAnimationController$lightRevealAnimator$1$2 extends AnimatorListenerAdapter {
    public final /* synthetic */ UnlockedScreenOffAnimationController this$0;

    public UnlockedScreenOffAnimationController$lightRevealAnimator$1$2(UnlockedScreenOffAnimationController unlockedScreenOffAnimationController) {
        this.this$0 = unlockedScreenOffAnimationController;
    }

    public final void onAnimationCancel(Animator animator) {
        LightRevealScrim lightRevealScrim = this.this$0.lightRevealScrim;
        if (lightRevealScrim == null) {
            lightRevealScrim = null;
        }
        lightRevealScrim.setRevealAmount(1.0f);
        UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
        unlockedScreenOffAnimationController.lightRevealAnimationPlaying = false;
        Iterator<UnlockedScreenOffAnimationController.Callback> it = unlockedScreenOffAnimationController.callbacks.iterator();
        while (it.hasNext()) {
            it.next().onUnlockedScreenOffProgressUpdate(0.0f, 0.0f);
        }
        this.this$0.interactionJankMonitor.cancel(40);
    }

    public final void onAnimationEnd(Animator animator) {
        UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
        unlockedScreenOffAnimationController.lightRevealAnimationPlaying = false;
        unlockedScreenOffAnimationController.interactionJankMonitor.end(40);
    }

    public final void onAnimationStart(Animator animator) {
        UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
        InteractionJankMonitor interactionJankMonitor = unlockedScreenOffAnimationController.interactionJankMonitor;
        StatusBar statusBar = unlockedScreenOffAnimationController.statusBar;
        if (statusBar == null) {
            statusBar = null;
        }
        Objects.requireNonNull(statusBar);
        interactionJankMonitor.begin(statusBar.mNotificationShadeWindowView, 40);
    }
}
