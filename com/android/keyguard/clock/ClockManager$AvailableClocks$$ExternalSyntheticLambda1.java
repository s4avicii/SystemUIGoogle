package com.android.keyguard.clock;

import com.android.keyguard.clock.ClockManager;
import com.android.systemui.plugins.ClockPlugin;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ClockManager$AvailableClocks$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ ClockManager.AvailableClocks f$0;
    public final /* synthetic */ ClockPlugin f$1;

    public /* synthetic */ ClockManager$AvailableClocks$$ExternalSyntheticLambda1(ClockManager.AvailableClocks availableClocks, ClockPlugin clockPlugin) {
        this.f$0 = availableClocks;
        this.f$1 = clockPlugin;
    }

    public final Object get() {
        ClockManager.AvailableClocks availableClocks = this.f$0;
        ClockPlugin clockPlugin = this.f$1;
        Objects.requireNonNull(availableClocks);
        ClockManager clockManager = ClockManager.this;
        return clockPlugin.getPreview(clockManager.mWidth, clockManager.mHeight);
    }
}
