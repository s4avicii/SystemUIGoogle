package com.android.systemui.keyguard;

import android.os.RemoteException;
import android.view.IRemoteAnimationFinishedCallback;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ KeyguardViewMediator f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda6(KeyguardViewMediator keyguardViewMediator, boolean z, boolean z2) {
        this.f$0 = keyguardViewMediator;
        this.f$1 = z;
        this.f$2 = z2;
    }

    public final void run() {
        KeyguardViewMediator keyguardViewMediator = this.f$0;
        boolean z = this.f$1;
        boolean z2 = KeyguardViewMediator.DEBUG;
        Objects.requireNonNull(keyguardViewMediator);
        keyguardViewMediator.onKeyguardExitFinished();
        if (keyguardViewMediator.mKeyguardStateController.isDismissingFromSwipe() || z) {
            KeyguardUnlockAnimationController keyguardUnlockAnimationController = keyguardViewMediator.mKeyguardUnlockAnimationControllerLazy.get();
            Objects.requireNonNull(keyguardUnlockAnimationController);
            keyguardUnlockAnimationController.keyguardViewController.hide(keyguardUnlockAnimationController.surfaceBehindRemoteAnimationStartTime, 0);
        }
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
        keyguardViewMediator.mSurfaceBehindRemoteAnimationRequested = false;
    }
}
