package com.android.systemui.power;

import android.content.DialogInterface;
import com.android.settingslib.fuelgauge.BatterySaverUtils;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda1 implements DialogInterface.OnClickListener {
    public final /* synthetic */ PowerNotificationWarnings f$0;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda1(PowerNotificationWarnings powerNotificationWarnings) {
        this.f$0 = powerNotificationWarnings;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        PowerNotificationWarnings powerNotificationWarnings = this.f$0;
        Objects.requireNonNull(powerNotificationWarnings);
        BatterySaverUtils.setPowerSaveMode(powerNotificationWarnings.mContext, true, false);
    }
}
