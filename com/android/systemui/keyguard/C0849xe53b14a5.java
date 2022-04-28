package com.android.systemui.keyguard;

/* renamed from: com.android.systemui.keyguard.KeyguardUnlockAnimationController$unlockToLauncherWithInWindowAnimations$2 */
/* compiled from: KeyguardUnlockAnimationController.kt */
public final class C0849xe53b14a5 implements Runnable {
    public final /* synthetic */ KeyguardUnlockAnimationController this$0;

    public C0849xe53b14a5(KeyguardUnlockAnimationController keyguardUnlockAnimationController) {
        this.this$0 = keyguardUnlockAnimationController;
    }

    public final void run() {
        this.this$0.keyguardViewMediator.get().onKeyguardExitRemoteAnimationFinished(false);
    }
}
