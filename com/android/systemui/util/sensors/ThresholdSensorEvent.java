package com.android.systemui.util.sensors;

import java.util.Locale;

public final class ThresholdSensorEvent {
    public final boolean mBelow;
    public final long mTimestampNs;

    public final String toString() {
        return String.format((Locale) null, "{near=%s, timestamp_ns=%d}", new Object[]{Boolean.valueOf(this.mBelow), Long.valueOf(this.mTimestampNs)});
    }

    public ThresholdSensorEvent(boolean z, long j) {
        this.mBelow = z;
        this.mTimestampNs = j;
    }
}
