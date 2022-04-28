package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;
import com.android.systemui.battery.BatteryMeterView$$ExternalSyntheticLambda0;
import com.android.systemui.demomode.DemoMode;

public interface BatteryController extends DemoMode, Dumpable, CallbackController<BatteryStateChangeCallback> {

    public interface BatteryStateChangeCallback {
        void onBatteryLevelChanged(int i, boolean z, boolean z2) {
        }

        void onBatteryUnknownStateChanged(boolean z) {
        }

        void onExtremeBatterySaverChanged(boolean z) {
        }

        void onPowerSaveChanged(boolean z) {
        }

        void onReverseChanged(boolean z, int i, String str) {
        }

        void onWirelessChargingChanged(boolean z) {
        }
    }

    public interface EstimateFetchCompletion {
        void onBatteryRemainingEstimateRetrieved(String str);
    }

    void getEstimatedTimeRemainingString(BatteryMeterView$$ExternalSyntheticLambda0 batteryMeterView$$ExternalSyntheticLambda0) {
    }

    boolean isAodPowerSave();

    boolean isPluggedIn();

    boolean isPluggedInWireless() {
        return false;
    }

    boolean isPowerSave();

    boolean isReverseOn() {
        return false;
    }

    boolean isReverseSupported() {
        return false;
    }

    boolean isWirelessCharging() {
        return false;
    }

    void setPowerSaveMode(boolean z);

    void setReverseState(boolean z) {
    }
}
