package com.android.systemui.statusbar.phone;

import com.android.internal.graphics.ColorUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StatusBar$$ExternalSyntheticLambda25 implements Runnable {
    public final /* synthetic */ StatusBar f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ StatusBar$$ExternalSyntheticLambda25(StatusBar statusBar, float f) {
        this.f$0 = statusBar;
        this.f$1 = f;
    }

    public final void run() {
        StatusBar statusBar = this.f$0;
        float f = this.f$1;
        long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
        Objects.requireNonNull(statusBar);
        ScrimController scrimController = statusBar.mScrimController;
        Objects.requireNonNull(scrimController);
        scrimController.mAdditionalScrimBehindAlphaKeyguard = f;
        ScrimController scrimController2 = statusBar.mScrimController;
        Objects.requireNonNull(scrimController2);
        float compositeAlpha = ((float) ColorUtils.compositeAlpha((int) (scrimController2.mAdditionalScrimBehindAlphaKeyguard * 255.0f), 51)) / 255.0f;
        scrimController2.mScrimBehindAlphaKeyguard = compositeAlpha;
        ScrimState[] values = ScrimState.values();
        for (ScrimState scrimState : values) {
            Objects.requireNonNull(scrimState);
            scrimState.mScrimBehindAlphaKeyguard = compositeAlpha;
        }
        scrimController2.scheduleUpdate();
    }
}
