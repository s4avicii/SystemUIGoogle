package com.android.systemui.statusbar.charging;

import com.android.systemui.statusbar.policy.BatteryController;
import java.util.Objects;

/* renamed from: com.android.systemui.statusbar.charging.WiredChargingRippleController$registerCallbacks$batteryStateChangeCallback$1 */
/* compiled from: WiredChargingRippleController.kt */
public final class C1193x82710e47 implements BatteryController.BatteryStateChangeCallback {
    public final /* synthetic */ WiredChargingRippleController this$0;

    public C1193x82710e47(WiredChargingRippleController wiredChargingRippleController) {
        this.this$0 = wiredChargingRippleController;
    }

    public final void onBatteryLevelChanged(int i, boolean z, boolean z2) {
        if (!this.this$0.batteryController.isPluggedInWireless()) {
            WiredChargingRippleController wiredChargingRippleController = this.this$0;
            Boolean bool = wiredChargingRippleController.pluggedIn;
            wiredChargingRippleController.pluggedIn = Boolean.valueOf(z);
            if ((bool == null || !bool.booleanValue()) && z) {
                WiredChargingRippleController wiredChargingRippleController2 = this.this$0;
                Objects.requireNonNull(wiredChargingRippleController2);
                long elapsedRealtime = wiredChargingRippleController2.systemClock.elapsedRealtime();
                Long l = wiredChargingRippleController2.lastTriggerTime;
                if (l != null) {
                    if (((double) (elapsedRealtime - l.longValue())) <= Math.pow(2.0d, (double) wiredChargingRippleController2.debounceLevel) * ((double) 2000)) {
                        wiredChargingRippleController2.debounceLevel = Math.min(3, wiredChargingRippleController2.debounceLevel + 1);
                        wiredChargingRippleController2.lastTriggerTime = Long.valueOf(elapsedRealtime);
                    }
                }
                wiredChargingRippleController2.startRipple();
                wiredChargingRippleController2.debounceLevel = 0;
                wiredChargingRippleController2.lastTriggerTime = Long.valueOf(elapsedRealtime);
            }
        }
    }
}
