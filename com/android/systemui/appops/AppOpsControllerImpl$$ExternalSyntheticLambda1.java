package com.android.systemui.appops;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class AppOpsControllerImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ AppOpsControllerImpl f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ boolean f$2;

    public /* synthetic */ AppOpsControllerImpl$$ExternalSyntheticLambda1(AppOpsControllerImpl appOpsControllerImpl, int i, boolean z) {
        this.f$0 = appOpsControllerImpl;
        this.f$1 = i;
        this.f$2 = z;
    }

    public final void run() {
        AppOpsControllerImpl appOpsControllerImpl = this.f$0;
        int i = this.f$1;
        boolean z = this.f$2;
        Objects.requireNonNull(appOpsControllerImpl);
        if (i == 2) {
            appOpsControllerImpl.mCameraDisabled = z;
        } else {
            boolean z2 = true;
            if (i == 1) {
                if (!appOpsControllerImpl.mAudioManager.isMicrophoneMute() && !z) {
                    z2 = false;
                }
                appOpsControllerImpl.mMicMuted = z2;
            }
        }
        appOpsControllerImpl.updateSensorDisabledStatus();
    }
}
