package com.android.systemui.statusbar.phone;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda26 implements Runnable {
    public final /* synthetic */ StatusBar f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda26(StatusBar statusBar, int i, int i2) {
        this.f$0 = statusBar;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void run() {
        StatusBar statusBar = this.f$0;
        int i = this.f$1;
        int i2 = this.f$2;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        statusBar.mCommandQueue.disable(statusBar.mDisplayId, i, i2, false);
    }
}
