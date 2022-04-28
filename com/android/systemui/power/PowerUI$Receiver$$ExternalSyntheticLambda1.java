package com.android.systemui.power;

import android.util.Slog;
import com.android.settingslib.fuelgauge.Estimate;
import com.android.systemui.power.PowerUI;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class PowerUI$Receiver$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ PowerUI.Receiver f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ PowerUI$Receiver$$ExternalSyntheticLambda1(PowerUI.Receiver receiver, boolean z, int i) {
        this.f$0 = receiver;
        this.f$1 = z;
        this.f$2 = i;
    }

    public final void run() {
        boolean z;
        PowerUI.Receiver receiver = this.f$0;
        boolean z2 = this.f$1;
        int i = this.f$2;
        int i2 = PowerUI.Receiver.$r8$clinit;
        Objects.requireNonNull(receiver);
        PowerUI powerUI = PowerUI.this;
        Objects.requireNonNull(powerUI);
        boolean isHybridNotificationEnabled = powerUI.mEnhancedEstimates.isHybridNotificationEnabled();
        boolean isPowerSaveMode = powerUI.mPowerManager.isPowerSaveMode();
        boolean z3 = PowerUI.DEBUG;
        if (z3) {
            Slog.d("PowerUI", "evaluating which notification to show");
        }
        if (isHybridNotificationEnabled) {
            if (z3) {
                Slog.d("PowerUI", "using hybrid");
            }
            Estimate refreshEstimateIfNeeded = powerUI.refreshEstimateIfNeeded();
            int i3 = powerUI.mBatteryLevel;
            int i4 = powerUI.mBatteryStatus;
            int[] iArr = powerUI.mLowBatteryReminderLevels;
            int i5 = iArr[1];
            int i6 = iArr[0];
            Objects.requireNonNull(refreshEstimateIfNeeded);
            BatteryStateSnapshot batteryStateSnapshot = r2;
            BatteryStateSnapshot batteryStateSnapshot2 = new BatteryStateSnapshot(i3, isPowerSaveMode, z2, i, i4, i5, i6, refreshEstimateIfNeeded.estimateMillis, refreshEstimateIfNeeded.averageDischargeTime, powerUI.mEnhancedEstimates.getSevereWarningThreshold(), powerUI.mEnhancedEstimates.getLowWarningThreshold(), refreshEstimateIfNeeded.isBasedOnUsage, powerUI.mEnhancedEstimates.getLowWarningEnabled());
            powerUI.mCurrentBatteryStateSnapshot = batteryStateSnapshot;
            z = false;
        } else {
            if (z3) {
                Slog.d("PowerUI", "using standard");
            }
            int i7 = powerUI.mBatteryLevel;
            int i8 = powerUI.mBatteryStatus;
            int[] iArr2 = powerUI.mLowBatteryReminderLevels;
            BatteryStateSnapshot batteryStateSnapshot3 = new BatteryStateSnapshot(i7, isPowerSaveMode, z2, i, i8, iArr2[1], iArr2[0], -1, -1, -1, -1, false, true);
            z = false;
            batteryStateSnapshot3.isHybrid = false;
            powerUI.mCurrentBatteryStateSnapshot = batteryStateSnapshot3;
        }
        powerUI.mWarnings.updateSnapshot(powerUI.mCurrentBatteryStateSnapshot);
        BatteryStateSnapshot batteryStateSnapshot4 = powerUI.mCurrentBatteryStateSnapshot;
        Objects.requireNonNull(batteryStateSnapshot4);
        if (batteryStateSnapshot4.isHybrid) {
            powerUI.maybeShowHybridWarning(powerUI.mCurrentBatteryStateSnapshot, powerUI.mLastBatteryStateSnapshot);
            return;
        }
        BatteryStateSnapshot batteryStateSnapshot5 = powerUI.mCurrentBatteryStateSnapshot;
        BatteryStateSnapshot batteryStateSnapshot6 = powerUI.mLastBatteryStateSnapshot;
        Objects.requireNonNull(batteryStateSnapshot5);
        int i9 = batteryStateSnapshot5.bucket;
        Objects.requireNonNull(batteryStateSnapshot6);
        if (i9 != batteryStateSnapshot6.bucket || batteryStateSnapshot6.plugged) {
            z = true;
        }
        if (powerUI.shouldShowLowBatteryWarning(batteryStateSnapshot5, batteryStateSnapshot6)) {
            powerUI.mWarnings.showLowBatteryWarning(z);
        } else if (powerUI.shouldDismissLowBatteryWarning(batteryStateSnapshot5, batteryStateSnapshot6)) {
            powerUI.mWarnings.dismissLowBatteryWarning();
        } else {
            powerUI.mWarnings.updateLowBatteryWarning();
        }
    }
}
