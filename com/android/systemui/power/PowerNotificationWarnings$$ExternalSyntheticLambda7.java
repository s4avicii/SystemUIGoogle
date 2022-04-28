package com.android.systemui.power;

import android.content.DialogInterface;
import com.android.systemui.volume.Events;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda7 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ PowerNotificationWarnings f$0;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda7(PowerNotificationWarnings powerNotificationWarnings) {
        this.f$0 = powerNotificationWarnings;
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        PowerNotificationWarnings powerNotificationWarnings = this.f$0;
        Objects.requireNonNull(powerNotificationWarnings);
        powerNotificationWarnings.mUsbHighTempDialog = null;
        Events.writeEvent(20, 9, Boolean.valueOf(powerNotificationWarnings.mKeyguard.isKeyguardLocked()));
    }
}
