package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.KeyboardShortcuts;
import com.android.systemui.util.concurrency.MessageRouter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda16 implements MessageRouter.SimpleMessageListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda16(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    public final void onMessage() {
        StatusBar statusBar = this.f$0;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        KeyboardShortcuts.dismiss();
    }
}
