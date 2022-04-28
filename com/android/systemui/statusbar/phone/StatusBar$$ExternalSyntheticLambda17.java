package com.android.systemui.statusbar.phone;

import android.util.Log;
import com.android.systemui.util.concurrency.MessageRouter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda17 implements MessageRouter.SimpleMessageListener {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda17(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    public final void onMessage() {
        StatusBar statusBar = this.f$0;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        Log.w("StatusBar", "Launch transition: Timeout!");
        NotificationPanelViewController notificationPanelViewController = statusBar.mNotificationPanelViewController;
        Objects.requireNonNull(notificationPanelViewController);
        notificationPanelViewController.setLaunchingAffordance(false);
        statusBar.releaseGestureWakeLock();
        statusBar.mNotificationPanelViewController.resetViews(false);
    }
}
