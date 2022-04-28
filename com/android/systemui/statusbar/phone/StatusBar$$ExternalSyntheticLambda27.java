package com.android.systemui.statusbar.phone;

import android.app.PendingIntent;
import com.android.systemui.animation.ActivityLaunchAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda27 implements Runnable {
    public final /* synthetic */ StatusBar f$0;
    public final /* synthetic */ PendingIntent f$1;
    public final /* synthetic */ ActivityLaunchAnimator.Controller f$2;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda27(StatusBar statusBar, PendingIntent pendingIntent, ActivityLaunchAnimator.Controller controller) {
        this.f$0 = statusBar;
        this.f$1 = pendingIntent;
        this.f$2 = controller;
    }

    public final void run() {
        StatusBar statusBar = this.f$0;
        PendingIntent pendingIntent = this.f$1;
        ActivityLaunchAnimator.Controller controller = this.f$2;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        statusBar.startPendingIntentDismissingKeyguard(pendingIntent, (Runnable) null, controller);
    }
}
