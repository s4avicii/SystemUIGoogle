package com.android.systemui.keyguard;

import android.os.IBinder;
import java.util.Objects;

/* compiled from: KeyguardUnlockAnimationController.kt */
public final class KeyguardUnlockAnimationController$setLauncherUnlockController$1 implements IBinder.DeathRecipient {
    public final /* synthetic */ KeyguardUnlockAnimationController this$0;

    public KeyguardUnlockAnimationController$setLauncherUnlockController$1(KeyguardUnlockAnimationController keyguardUnlockAnimationController) {
        this.this$0 = keyguardUnlockAnimationController;
    }

    public final void binderDied() {
        KeyguardUnlockAnimationController keyguardUnlockAnimationController = this.this$0;
        keyguardUnlockAnimationController.launcherUnlockController = null;
        Objects.requireNonNull(keyguardUnlockAnimationController);
        keyguardUnlockAnimationController.launcherSmartspaceState = null;
    }
}
