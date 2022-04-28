package com.android.systemui.demomode;

import android.content.Context;
import java.util.Objects;

/* compiled from: DemoModeController.kt */
public final class DemoModeController$tracker$1 extends DemoModeAvailabilityTracker {
    public final /* synthetic */ DemoModeController this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DemoModeController$tracker$1(DemoModeController demoModeController, Context context) {
        super(context);
        this.this$0 = demoModeController;
    }

    public final void onDemoModeAvailabilityChanged() {
        DemoModeController demoModeController = this.this$0;
        boolean z = this.isDemoModeAvailable;
        Objects.requireNonNull(demoModeController);
        if (demoModeController.isInDemoMode && !z) {
            demoModeController.globalSettings.putInt("sysui_tuner_demo_on", 0);
        }
    }

    public final void onDemoModeFinished() {
        DemoModeController demoModeController = this.this$0;
        Objects.requireNonNull(demoModeController);
        if (demoModeController.isInDemoMode != this.isInDemoMode) {
            this.this$0.exitDemoMode();
        }
    }

    public final void onDemoModeStarted() {
        DemoModeController demoModeController = this.this$0;
        Objects.requireNonNull(demoModeController);
        if (demoModeController.isInDemoMode != this.isInDemoMode) {
            this.this$0.enterDemoMode();
        }
    }
}
