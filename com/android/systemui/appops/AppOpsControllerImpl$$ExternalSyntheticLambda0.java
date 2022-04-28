package com.android.systemui.appops;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AppOpsControllerImpl$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ AppOpsControllerImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ boolean f$4;

    public /* synthetic */ AppOpsControllerImpl$$ExternalSyntheticLambda0(AppOpsControllerImpl appOpsControllerImpl, int i, int i2, String str, boolean z) {
        this.f$0 = appOpsControllerImpl;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = str;
        this.f$4 = z;
    }

    public final void run() {
        AppOpsControllerImpl appOpsControllerImpl = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        String str = this.f$3;
        boolean z = this.f$4;
        Objects.requireNonNull(appOpsControllerImpl);
        appOpsControllerImpl.notifySuscribersWorker(i, i2, str, z);
    }
}
