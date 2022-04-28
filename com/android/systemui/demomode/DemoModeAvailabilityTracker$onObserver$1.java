package com.android.systemui.demomode;

import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import java.util.Objects;

/* compiled from: DemoModeAvailabilityTracker.kt */
public final class DemoModeAvailabilityTracker$onObserver$1 extends ContentObserver {
    public final /* synthetic */ DemoModeAvailabilityTracker this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DemoModeAvailabilityTracker$onObserver$1(DemoModeAvailabilityTracker demoModeAvailabilityTracker, Handler handler) {
        super(handler);
        this.this$0 = demoModeAvailabilityTracker;
    }

    public final void onChange(boolean z) {
        DemoModeAvailabilityTracker demoModeAvailabilityTracker = this.this$0;
        Objects.requireNonNull(demoModeAvailabilityTracker);
        boolean z2 = false;
        if (Settings.Global.getInt(demoModeAvailabilityTracker.context.getContentResolver(), "sysui_tuner_demo_on", 0) != 0) {
            z2 = true;
        }
        DemoModeAvailabilityTracker demoModeAvailabilityTracker2 = this.this$0;
        Objects.requireNonNull(demoModeAvailabilityTracker2);
        if (demoModeAvailabilityTracker2.isInDemoMode != z2) {
            DemoModeAvailabilityTracker demoModeAvailabilityTracker3 = this.this$0;
            Objects.requireNonNull(demoModeAvailabilityTracker3);
            demoModeAvailabilityTracker3.isInDemoMode = z2;
            if (z2) {
                this.this$0.onDemoModeStarted();
            } else {
                this.this$0.onDemoModeFinished();
            }
        }
    }
}
