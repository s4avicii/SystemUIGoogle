package com.android.systemui.power;

import com.android.systemui.power.PowerUI;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PowerUI$Receiver$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ PowerUI.Receiver f$0;

    public /* synthetic */ PowerUI$Receiver$$ExternalSyntheticLambda0(PowerUI.Receiver receiver) {
        this.f$0 = receiver;
    }

    public final void run() {
        PowerUI.Receiver receiver = this.f$0;
        int i = PowerUI.Receiver.$r8$clinit;
        Objects.requireNonNull(receiver);
        if (PowerUI.this.mPowerManager.isPowerSaveMode()) {
            PowerUI.this.mWarnings.dismissLowBatteryWarning();
        }
    }
}
