package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import java.util.Objects;

/* compiled from: KeyguardUnlockAnimationController.kt */
public final class KeyguardUnlockAnimationController$2$2 extends AnimatorListenerAdapter {
    public final /* synthetic */ KeyguardUnlockAnimationController this$0;

    public KeyguardUnlockAnimationController$2$2(KeyguardUnlockAnimationController keyguardUnlockAnimationController) {
        this.this$0 = keyguardUnlockAnimationController;
    }

    public final void onAnimationEnd(Animator animator) {
        KeyguardUnlockAnimationController keyguardUnlockAnimationController = this.this$0;
        Objects.requireNonNull(keyguardUnlockAnimationController);
        keyguardUnlockAnimationController.playingCannedUnlockAnimation = false;
        this.this$0.keyguardViewMediator.get().onKeyguardExitRemoteAnimationFinished(false);
    }
}
