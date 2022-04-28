package com.android.systemui.battery;

import android.provider.Settings;
import java.util.Objects;
import java.util.function.Supplier;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BatteryMeterView$$ExternalSyntheticLambda1 implements Supplier {
    public final /* synthetic */ BatteryMeterView f$0;

    public /* synthetic */ BatteryMeterView$$ExternalSyntheticLambda1(BatteryMeterView batteryMeterView) {
        this.f$0 = batteryMeterView;
    }

    public final Object get() {
        BatteryMeterView batteryMeterView = this.f$0;
        int i = BatteryMeterView.$r8$clinit;
        Objects.requireNonNull(batteryMeterView);
        return Integer.valueOf(Settings.System.getIntForUser(batteryMeterView.getContext().getContentResolver(), "status_bar_show_battery_percent", 0, -2));
    }
}
