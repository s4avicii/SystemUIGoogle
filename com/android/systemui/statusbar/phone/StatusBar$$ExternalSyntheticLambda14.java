package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.util.concurrency.MessageRouter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda14 implements MessageRouter.DataMessageListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda14(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    public final void onMessage(Object obj) {
        StatusBar statusBar = this.f$0;
        StatusBar.AnimateExpandSettingsPanelMessage animateExpandSettingsPanelMessage = (StatusBar.AnimateExpandSettingsPanelMessage) obj;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        statusBar.mCommandQueueCallbacks.animateExpandSettingsPanel((String) null);
    }
}
