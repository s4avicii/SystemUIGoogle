package com.android.systemui.recents;

import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.util.Log;
import com.android.systemui.recents.OverviewProxyService;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda12 implements Runnable {
    public static final /* synthetic */ OverviewProxyService$1$$ExternalSyntheticLambda12 INSTANCE = new OverviewProxyService$1$$ExternalSyntheticLambda12();

    public final void run() {
        int i = OverviewProxyService.C10571.$r8$clinit;
        try {
            ActivityTaskManager.getService().stopSystemLockTaskMode();
        } catch (RemoteException unused) {
            Log.e("OverviewProxyService", "Failed to stop screen pinning");
        }
    }
}
