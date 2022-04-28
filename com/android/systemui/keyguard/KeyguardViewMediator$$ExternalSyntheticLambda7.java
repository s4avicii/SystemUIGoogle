package com.android.systemui.keyguard;

import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.util.Log;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ boolean f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda7(boolean z, boolean z2) {
        this.f$0 = z;
        this.f$1 = z2;
    }

    public final void run() {
        boolean z = this.f$0;
        boolean z2 = this.f$1;
        if (KeyguardViewMediator.DEBUG) {
            Log.d("KeyguardViewMediator", "updateActivityLockScreenState(" + z + ", " + z2 + ")");
        }
        try {
            ActivityTaskManager.getService().setLockScreenShown(z, z2);
        } catch (RemoteException unused) {
        }
    }
}
