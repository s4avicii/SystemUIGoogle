package com.android.systemui.keyguard;

import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.util.Log;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardViewMediator$10$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int f$0;

    public /* synthetic */ KeyguardViewMediator$10$$ExternalSyntheticLambda0(int i) {
        this.f$0 = i;
    }

    public final void run() {
        try {
            ActivityTaskManager.getService().keyguardGoingAway(this.f$0);
        } catch (RemoteException e) {
            Log.e("KeyguardViewMediator", "Error while calling WindowManager", e);
        }
    }
}
