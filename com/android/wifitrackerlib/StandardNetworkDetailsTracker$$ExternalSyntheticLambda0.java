package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class StandardNetworkDetailsTracker$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ StandardNetworkDetailsTracker f$0;

    public /* synthetic */ StandardNetworkDetailsTracker$$ExternalSyntheticLambda0(StandardNetworkDetailsTracker standardNetworkDetailsTracker) {
        this.f$0 = standardNetworkDetailsTracker;
    }

    public final boolean test(Object obj) {
        StandardNetworkDetailsTracker standardNetworkDetailsTracker = this.f$0;
        Objects.requireNonNull(standardNetworkDetailsTracker);
        StandardWifiEntry.ScanResultKey scanResultKey = new StandardWifiEntry.ScanResultKey((ScanResult) obj);
        StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = standardNetworkDetailsTracker.mKey;
        Objects.requireNonNull(standardWifiEntryKey);
        return scanResultKey.equals(standardWifiEntryKey.mScanResultKey);
    }
}
