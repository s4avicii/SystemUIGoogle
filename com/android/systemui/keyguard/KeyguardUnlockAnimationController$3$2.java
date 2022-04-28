package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import com.android.systemui.shared.system.smartspace.ILauncherUnlockAnimationController;

/* compiled from: KeyguardUnlockAnimationController.kt */
public final class KeyguardUnlockAnimationController$3$2 extends AnimatorListenerAdapter {
    public final /* synthetic */ KeyguardUnlockAnimationController this$0;

    public KeyguardUnlockAnimationController$3$2(KeyguardUnlockAnimationController keyguardUnlockAnimationController) {
        this.this$0 = keyguardUnlockAnimationController;
    }

    public final void onAnimationEnd(Animator animator) {
        ILauncherUnlockAnimationController iLauncherUnlockAnimationController = this.this$0.launcherUnlockController;
        if (iLauncherUnlockAnimationController != null) {
            iLauncherUnlockAnimationController.setSmartspaceVisibility(0);
        }
        this.this$0.keyguardViewMediator.get().onKeyguardExitRemoteAnimationFinished(false);
    }
}
