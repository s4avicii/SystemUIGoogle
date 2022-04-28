package com.android.systemui.util.time;

import android.os.SystemClock;

public final class SystemClockImpl implements SystemClock {
    public final long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public final long elapsedRealtime() {
        return SystemClock.elapsedRealtime();
    }

    public final long uptimeMillis() {
        return SystemClock.uptimeMillis();
    }
}
