package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.ongoingcall.OngoingCallListener;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda11 implements OngoingCallListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda11(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    public final void onOngoingCallStateChanged() {
        StatusBar statusBar = this.f$0;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        statusBar.maybeUpdateBarMode();
    }
}
