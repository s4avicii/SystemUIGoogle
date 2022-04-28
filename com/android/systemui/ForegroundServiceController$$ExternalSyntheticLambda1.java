package com.android.systemui;

import android.os.UserHandle;
import android.util.ArraySet;
import com.android.systemui.util.Assert;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ForegroundServiceController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ ForegroundServiceController f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ boolean f$4;

    public /* synthetic */ ForegroundServiceController$$ExternalSyntheticLambda1(ForegroundServiceController foregroundServiceController, int i, int i2, String str, boolean z) {
        this.f$0 = foregroundServiceController;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = str;
        this.f$4 = z;
    }

    public final void run() {
        ForegroundServiceController foregroundServiceController = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        String str = this.f$3;
        boolean z = this.f$4;
        Objects.requireNonNull(foregroundServiceController);
        Assert.isMainThread();
        int userId = UserHandle.getUserId(i2);
        synchronized (foregroundServiceController.mMutex) {
            ForegroundServicesUserState foregroundServicesUserState = foregroundServiceController.mUserServices.get(userId);
            if (foregroundServicesUserState == null) {
                foregroundServicesUserState = new ForegroundServicesUserState();
                foregroundServiceController.mUserServices.put(userId, foregroundServicesUserState);
            }
            if (z) {
                if (foregroundServicesUserState.mAppOps.get(str) == null) {
                    foregroundServicesUserState.mAppOps.put(str, new ArraySet(3));
                }
                foregroundServicesUserState.mAppOps.get(str).add(Integer.valueOf(i));
            } else {
                ArraySet arraySet = foregroundServicesUserState.mAppOps.get(str);
                if (arraySet != null) {
                    arraySet.remove(Integer.valueOf(i));
                    if (arraySet.size() == 0) {
                        foregroundServicesUserState.mAppOps.remove(str);
                    }
                }
            }
        }
    }
}
