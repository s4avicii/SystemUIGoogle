package com.android.systemui.power;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.provider.Settings;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PowerNotificationWarnings$$ExternalSyntheticLambda3 implements DialogInterface.OnClickListener {
    public final /* synthetic */ PowerNotificationWarnings f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ PowerNotificationWarnings$$ExternalSyntheticLambda3(PowerNotificationWarnings powerNotificationWarnings, int i, int i2) {
        this.f$0 = powerNotificationWarnings;
        this.f$1 = i;
        this.f$2 = i2;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        PowerNotificationWarnings powerNotificationWarnings = this.f$0;
        int i2 = this.f$1;
        int i3 = this.f$2;
        Objects.requireNonNull(powerNotificationWarnings);
        ContentResolver contentResolver = powerNotificationWarnings.mContext.getContentResolver();
        Settings.Global.putInt(contentResolver, "automatic_power_save_mode", i2);
        Settings.Global.putInt(contentResolver, "low_power_trigger_level", i3);
        Settings.Secure.putIntForUser(contentResolver, "low_power_warning_acknowledged", 1, -2);
    }
}
