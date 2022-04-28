package com.android.systemui.power;

import com.android.systemui.plugins.ActivityStarter;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda8 implements ActivityStarter.Callback {
    public final /* synthetic */ PowerNotificationWarnings f$0;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda8(PowerNotificationWarnings powerNotificationWarnings) {
        this.f$0 = powerNotificationWarnings;
    }

    public final void onActivityStarted(int i) {
        PowerNotificationWarnings powerNotificationWarnings = this.f$0;
        Objects.requireNonNull(powerNotificationWarnings);
        powerNotificationWarnings.mUsbHighTempDialog = null;
    }
}
