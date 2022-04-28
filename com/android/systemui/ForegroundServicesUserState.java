package com.android.systemui;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.ArrayMap;
import android.util.ArraySet;
import java.util.Arrays;

public final class ForegroundServicesUserState {
    public ArrayMap<String, ArraySet<Integer>> mAppOps = new ArrayMap<>(1);
    public ArrayMap<String, ArraySet<String>> mImportantNotifications = new ArrayMap<>(1);
    public String[] mRunning = null;
    public long mServiceStartTime = 0;
    public ArrayMap<String, ArraySet<String>> mStandardLayoutNotifications = new ArrayMap<>(1);

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("UserServices{mRunning=");
        m.append(Arrays.toString(this.mRunning));
        m.append(", mServiceStartTime=");
        m.append(this.mServiceStartTime);
        m.append(", mImportantNotifications=");
        m.append(this.mImportantNotifications);
        m.append(", mStandardLayoutNotifications=");
        m.append(this.mStandardLayoutNotifications);
        m.append('}');
        return m.toString();
    }
}
