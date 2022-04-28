package com.android.systemui.statusbar.phone;

import android.content.Intent;
import com.android.systemui.animation.ActivityLaunchAnimator;
import com.android.systemui.plugins.ActivityStarter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda28 implements Runnable {
    public final /* synthetic */ StatusBar f$0;
    public final /* synthetic */ Intent f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ ActivityLaunchAnimator.Controller f$3;
    public final /* synthetic */ boolean f$4;
    public final /* synthetic */ boolean f$5;
    public final /* synthetic */ ActivityStarter.Callback f$6;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda28(StatusBar statusBar, Intent intent, int i, ActivityLaunchAnimator.Controller controller, boolean z, boolean z2, ActivityStarter.Callback callback) {
        this.f$0 = statusBar;
        this.f$1 = intent;
        this.f$2 = i;
        this.f$3 = controller;
        this.f$4 = z;
        this.f$5 = z2;
        this.f$6 = callback;
    }

    public final void run() {
        StatusBar statusBar = this.f$0;
        Intent intent = this.f$1;
        int i = this.f$2;
        ActivityLaunchAnimator.Controller controller = this.f$3;
        boolean z = this.f$4;
        boolean z2 = this.f$5;
        ActivityStarter.Callback callback = this.f$6;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        statusBar.mAssistManagerLazy.get().hideAssist();
        intent.setFlags(335544320);
        intent.addFlags(i);
        int[] iArr = {-96};
        ActivityLaunchAnimator activityLaunchAnimator = statusBar.mActivityLaunchAnimator;
        String str = intent.getPackage();
        StatusBar$$ExternalSyntheticLambda37 statusBar$$ExternalSyntheticLambda37 = new StatusBar$$ExternalSyntheticLambda37(statusBar, z2, intent, iArr);
        Objects.requireNonNull(activityLaunchAnimator);
        activityLaunchAnimator.startIntentWithAnimation(controller, z, str, false, statusBar$$ExternalSyntheticLambda37);
        if (callback != null) {
            callback.onActivityStarted(iArr[0]);
        }
    }
}
