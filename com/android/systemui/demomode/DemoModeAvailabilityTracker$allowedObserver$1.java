package com.android.systemui.demomode;

import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import java.util.Objects;

/* compiled from: DemoModeAvailabilityTracker.kt */
public final class DemoModeAvailabilityTracker$allowedObserver$1 extends ContentObserver {
    public final /* synthetic */ DemoModeAvailabilityTracker this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DemoModeAvailabilityTracker$allowedObserver$1(DemoModeAvailabilityTracker demoModeAvailabilityTracker, Handler handler) {
        super(handler);
        this.this$0 = demoModeAvailabilityTracker;
    }

    public final void onChange(boolean z) {
        DemoModeAvailabilityTracker demoModeAvailabilityTracker = this.this$0;
        Objects.requireNonNull(demoModeAvailabilityTracker);
        boolean z2 = false;
        if (Settings.Global.getInt(demoModeAvailabilityTracker.context.getContentResolver(), "sysui_demo_allowed", 0) != 0) {
            z2 = true;
        }
        DemoModeAvailabilityTracker demoModeAvailabilityTracker2 = this.this$0;
        Objects.requireNonNull(demoModeAvailabilityTracker2);
        if (demoModeAvailabilityTracker2.isDemoModeAvailable != z2) {
            DemoModeAvailabilityTracker demoModeAvailabilityTracker3 = this.this$0;
            Objects.requireNonNull(demoModeAvailabilityTracker3);
            demoModeAvailabilityTracker3.isDemoModeAvailable = z2;
            this.this$0.onDemoModeAvailabilityChanged();
        }
    }
}
