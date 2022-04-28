package com.google.android.systemui.reversecharging;

import android.util.Log;
import com.android.systemui.BootCompleteCache;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ReverseChargingController$$ExternalSyntheticLambda5 implements BootCompleteCache.BootCompleteListener {
    public final /* synthetic */ ReverseChargingController f$0;

    public /* synthetic */ ReverseChargingController$$ExternalSyntheticLambda5(ReverseChargingController reverseChargingController) {
        this.f$0 = reverseChargingController;
    }

    public final void onBootComplete() {
        ReverseChargingController reverseChargingController = this.f$0;
        if (ReverseChargingController.DEBUG) {
            Objects.requireNonNull(reverseChargingController);
            Log.d("ReverseChargingControl", "onBootComplete(): ACTION_BOOT_COMPLETED");
        }
        reverseChargingController.mBootCompleted = true;
        reverseChargingController.setRtxTimer(2, ReverseChargingController.DURATION_WAIT_NFC_SERVICE);
    }
}
