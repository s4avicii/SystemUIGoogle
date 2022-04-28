package com.android.systemui.statusbar.phone;

import android.app.PendingIntent;
import android.util.Log;
import com.android.systemui.animation.ActivityLaunchAnimator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda29 implements Runnable {
    public final /* synthetic */ StatusBar f$0;
    public final /* synthetic */ ActivityLaunchAnimator.Controller f$1;
    public final /* synthetic */ PendingIntent f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ boolean f$4;
    public final /* synthetic */ Runnable f$5;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda29(StatusBar statusBar, ActivityLaunchAnimator.Controller controller, PendingIntent pendingIntent, boolean z, boolean z2, Runnable runnable) {
        this.f$0 = statusBar;
        this.f$1 = controller;
        this.f$2 = pendingIntent;
        this.f$3 = z;
        this.f$4 = z2;
        this.f$5 = runnable;
    }

    public final void run() {
        StatusBarLaunchAnimatorController statusBarLaunchAnimatorController;
        StatusBar statusBar = this.f$0;
        ActivityLaunchAnimator.Controller controller = this.f$1;
        PendingIntent pendingIntent = this.f$2;
        boolean z = this.f$3;
        boolean z2 = this.f$4;
        Runnable runnable = this.f$5;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        if (controller != null) {
            try {
                statusBarLaunchAnimatorController = new StatusBarLaunchAnimatorController(controller, statusBar, pendingIntent.isActivity());
            } catch (PendingIntent.CanceledException e) {
                Log.w("StatusBar", "Sending intent failed: " + e);
                if (!z2) {
                    statusBar.collapsePanelOnMainThread();
                }
            }
        } else {
            statusBarLaunchAnimatorController = null;
        }
        statusBar.mActivityLaunchAnimator.startPendingIntentWithAnimation(statusBarLaunchAnimatorController, z, pendingIntent.getCreatorPackage(), new StatusBar$$ExternalSyntheticLambda9(statusBar, pendingIntent));
        if (pendingIntent.isActivity()) {
            statusBar.mAssistManagerLazy.get().hideAssist();
        }
        if (runnable != null) {
            statusBar.mMainExecutor.execute(runnable);
        }
    }
}
