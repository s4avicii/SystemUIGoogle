package com.android.systemui.power;

import android.content.DialogInterface;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda6 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ PowerNotificationWarnings f$0;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda6(PowerNotificationWarnings powerNotificationWarnings) {
        this.f$0 = powerNotificationWarnings;
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        PowerNotificationWarnings powerNotificationWarnings = this.f$0;
        Objects.requireNonNull(powerNotificationWarnings);
        powerNotificationWarnings.mSaverConfirmation = null;
    }
}
