package com.android.systemui.keyguard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;
import java.util.Objects;

/* compiled from: KeyguardUnlockAnimationController.kt */
public final class KeyguardUnlockAnimationController$1$2 extends AnimatorListenerAdapter {
    public final /* synthetic */ KeyguardUnlockAnimationController this$0;

    public KeyguardUnlockAnimationController$1$2(KeyguardUnlockAnimationController keyguardUnlockAnimationController) {
        this.this$0 = keyguardUnlockAnimationController;
    }

    public final void onAnimationEnd(Animator animator) {
        boolean z;
        KeyguardUnlockAnimationController keyguardUnlockAnimationController = this.this$0;
        if (keyguardUnlockAnimationController.surfaceBehindAlpha == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            KeyguardViewMediator keyguardViewMediator = keyguardUnlockAnimationController.keyguardViewMediator.get();
            Objects.requireNonNull(keyguardViewMediator);
            if (keyguardViewMediator.mSurfaceBehindRemoteAnimationRunning) {
                keyguardViewMediator.mSurfaceBehindRemoteAnimationRunning = false;
                IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback = keyguardViewMediator.mSurfaceBehindRemoteAnimationFinishedCallback;
                if (iRemoteAnimationFinishedCallback != null) {
                    try {
                        iRemoteAnimationFinishedCallback.onAnimationFinished();
                        keyguardViewMediator.mSurfaceBehindRemoteAnimationFinishedCallback = null;
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
