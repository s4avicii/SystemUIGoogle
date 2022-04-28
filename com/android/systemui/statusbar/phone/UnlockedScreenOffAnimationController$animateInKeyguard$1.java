package com.android.systemui.statusbar.phone;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.View;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.KeyguardViewMediator;
import java.util.Objects;

/* compiled from: UnlockedScreenOffAnimationController.kt */
public final class UnlockedScreenOffAnimationController$animateInKeyguard$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ Runnable $after;
    public final /* synthetic */ View $keyguardView;
    public final /* synthetic */ UnlockedScreenOffAnimationController this$0;

    public UnlockedScreenOffAnimationController$animateInKeyguard$1(UnlockedScreenOffAnimationController unlockedScreenOffAnimationController, KeyguardVisibilityHelper$$ExternalSyntheticLambda0 keyguardVisibilityHelper$$ExternalSyntheticLambda0, View view) {
        this.this$0 = unlockedScreenOffAnimationController;
        this.$after = keyguardVisibilityHelper$$ExternalSyntheticLambda0;
        this.$keyguardView = view;
    }

    public final void onAnimationCancel(Animator animator) {
        this.this$0.interactionJankMonitor.cancel(41);
    }

    public final void onAnimationEnd(Animator animator) {
        UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
        unlockedScreenOffAnimationController.aodUiAnimationPlaying = false;
        KeyguardViewMediator keyguardViewMediator = unlockedScreenOffAnimationController.keyguardViewMediatorLazy.get();
        Objects.requireNonNull(keyguardViewMediator);
        if (keyguardViewMediator.mPendingLock && !keyguardViewMediator.mScreenOffAnimationController.isKeyguardShowDelayed()) {
            keyguardViewMediator.doKeyguardLocked((Bundle) null);
            keyguardViewMediator.mPendingLock = false;
        }
        StatusBar statusBar = this.this$0.statusBar;
        if (statusBar == null) {
            statusBar = null;
        }
        statusBar.updateIsKeyguard();
        this.$after.run();
        this.this$0.decidedToAnimateGoingToSleep = null;
        this.$keyguardView.animate().setListener((Animator.AnimatorListener) null);
        this.this$0.interactionJankMonitor.end(41);
    }

    public final void onAnimationStart(Animator animator) {
        UnlockedScreenOffAnimationController unlockedScreenOffAnimationController = this.this$0;
        InteractionJankMonitor interactionJankMonitor = unlockedScreenOffAnimationController.interactionJankMonitor;
        StatusBar statusBar = unlockedScreenOffAnimationController.statusBar;
        if (statusBar == null) {
            statusBar = null;
        }
        Objects.requireNonNull(statusBar);
        interactionJankMonitor.begin(statusBar.mNotificationShadeWindowView, 41);
    }
}
