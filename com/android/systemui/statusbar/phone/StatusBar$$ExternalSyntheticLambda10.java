package com.android.systemui.statusbar.phone;

import com.android.systemui.plugins.ActivityStarter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda10 implements KeyguardDismissHandler {
    public final /* synthetic */ StatusBar f$0;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda10(StatusBar statusBar) {
        this.f$0 = statusBar;
    }

    public final void executeWhenUnlocked(ActivityStarter.OnDismissAction onDismissAction, boolean z, boolean z2) {
        StatusBar statusBar = this.f$0;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        StatusBarKeyguardViewManager statusBarKeyguardViewManager = statusBar.mStatusBarKeyguardViewManager;
        Objects.requireNonNull(statusBarKeyguardViewManager);
        if (statusBarKeyguardViewManager.mShowing && z) {
            statusBar.mStatusBarStateController.setLeaveOpenOnKeyguardHide(true);
        }
        statusBar.dismissKeyguardThenExecute(onDismissAction, (Runnable) null, z2);
    }
}
