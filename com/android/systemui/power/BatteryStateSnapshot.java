package com.android.systemui.power;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.recyclerview.widget.LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0;
import com.android.keyguard.FontInterpolator$VarFontKey$$ExternalSyntheticOutline0;

/* compiled from: BatteryStateSnapshot.kt */
public final class BatteryStateSnapshot {
    public final long averageTimeToDischargeMillis;
    public final int batteryLevel;
    public final int batteryStatus;
    public final int bucket;
    public final boolean isBasedOnUsage;
    public boolean isHybrid = true;
    public final boolean isLowWarningEnabled;
    public final boolean isPowerSaver;
    public final int lowLevelThreshold;
    public final long lowThresholdMillis;
    public final boolean plugged;
    public final int severeLevelThreshold;
    public final long severeThresholdMillis;
    public final long timeRemainingMillis;

    public BatteryStateSnapshot(int i, boolean z, boolean z2, int i2, int i3, int i4, int i5, long j, long j2, long j3, long j4, boolean z3, boolean z4) {
        this.batteryLevel = i;
        this.isPowerSaver = z;
        this.plugged = z2;
        this.bucket = i2;
        this.batteryStatus = i3;
        this.severeLevelThreshold = i4;
        this.lowLevelThreshold = i5;
        this.timeRemainingMillis = j;
        this.averageTimeToDischargeMillis = j2;
        this.severeThresholdMillis = j3;
        this.lowThresholdMillis = j4;
        this.isBasedOnUsage = z3;
        this.isLowWarningEnabled = z4;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof BatteryStateSnapshot)) {
            return false;
        }
        BatteryStateSnapshot batteryStateSnapshot = (BatteryStateSnapshot) obj;
        return this.batteryLevel == batteryStateSnapshot.batteryLevel && this.isPowerSaver == batteryStateSnapshot.isPowerSaver && this.plugged == batteryStateSnapshot.plugged && this.bucket == batteryStateSnapshot.bucket && this.batteryStatus == batteryStateSnapshot.batteryStatus && this.severeLevelThreshold == batteryStateSnapshot.severeLevelThreshold && this.lowLevelThreshold == batteryStateSnapshot.lowLevelThreshold && this.timeRemainingMillis == batteryStateSnapshot.timeRemainingMillis && this.averageTimeToDischargeMillis == batteryStateSnapshot.averageTimeToDischargeMillis && this.severeThresholdMillis == batteryStateSnapshot.severeThresholdMillis && this.lowThresholdMillis == batteryStateSnapshot.lowThresholdMillis && this.isBasedOnUsage == batteryStateSnapshot.isBasedOnUsage && this.isLowWarningEnabled == batteryStateSnapshot.isLowWarningEnabled;
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.batteryLevel) * 31;
        boolean z = this.isPowerSaver;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int i = (hashCode + (z ? 1 : 0)) * 31;
        boolean z3 = this.plugged;
        if (z3) {
            z3 = true;
        }
        int m = FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.lowLevelThreshold, FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.severeLevelThreshold, FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.batteryStatus, FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.bucket, (i + (z3 ? 1 : 0)) * 31, 31), 31), 31), 31);
        int hashCode2 = Long.hashCode(this.averageTimeToDischargeMillis);
        int hashCode3 = (Long.hashCode(this.lowThresholdMillis) + ((Long.hashCode(this.severeThresholdMillis) + ((hashCode2 + ((Long.hashCode(this.timeRemainingMillis) + m) * 31)) * 31)) * 31)) * 31;
        boolean z4 = this.isBasedOnUsage;
        if (z4) {
            z4 = true;
        }
        int i2 = (hashCode3 + (z4 ? 1 : 0)) * 31;
        boolean z5 = this.isLowWarningEnabled;
        if (!z5) {
            z2 = z5;
        }
        return i2 + (z2 ? 1 : 0);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("BatteryStateSnapshot(batteryLevel=");
        m.append(this.batteryLevel);
        m.append(", isPowerSaver=");
        m.append(this.isPowerSaver);
        m.append(", plugged=");
        m.append(this.plugged);
        m.append(", bucket=");
        m.append(this.bucket);
        m.append(", batteryStatus=");
        m.append(this.batteryStatus);
        m.append(", severeLevelThreshold=");
        m.append(this.severeLevelThreshold);
        m.append(", lowLevelThreshold=");
        m.append(this.lowLevelThreshold);
        m.append(", timeRemainingMillis=");
        m.append(this.timeRemainingMillis);
        m.append(", averageTimeToDischargeMillis=");
        m.append(this.averageTimeToDischargeMillis);
        m.append(", severeThresholdMillis=");
        m.append(this.severeThresholdMillis);
        m.append(", lowThresholdMillis=");
        m.append(this.lowThresholdMillis);
        m.append(", isBasedOnUsage=");
        m.append(this.isBasedOnUsage);
        m.append(", isLowWarningEnabled=");
        return LinearLayoutManager$AnchorInfo$$ExternalSyntheticOutline0.m21m(m, this.isLowWarningEnabled, ')');
    }
}
