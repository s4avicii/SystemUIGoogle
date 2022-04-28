package com.android.systemui;

import com.android.systemui.appops.AppOpsController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ForegroundServiceController$$ExternalSyntheticLambda0 implements AppOpsController.Callback {
    public final /* synthetic */ ForegroundServiceController f$0;

    public /* synthetic */ ForegroundServiceController$$ExternalSyntheticLambda0(ForegroundServiceController foregroundServiceController) {
        this.f$0 = foregroundServiceController;
    }

    public final void onActiveStateChanged(int i, int i2, String str, boolean z) {
        ForegroundServiceController foregroundServiceController = this.f$0;
        Objects.requireNonNull(foregroundServiceController);
        foregroundServiceController.mMainHandler.post(new ForegroundServiceController$$ExternalSyntheticLambda1(foregroundServiceController, i, i2, str, z));
    }
}
