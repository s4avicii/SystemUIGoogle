package com.android.wifitrackerlib;

import android.util.ArraySet;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ Set f$0;
    public final /* synthetic */ Map f$1;
    public final /* synthetic */ Set f$2;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda3(ArraySet arraySet, Map map, Set set) {
        this.f$0 = arraySet;
        this.f$1 = map;
        this.f$2 = set;
    }

    public final void accept(Object obj) {
        Set set = this.f$0;
        Map map = this.f$1;
        Set set2 = this.f$2;
        StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
        Objects.requireNonNull(standardWifiEntry);
        StandardWifiEntry.StandardWifiEntryKey standardWifiEntryKey = standardWifiEntry.mKey;
        set.add(standardWifiEntryKey);
        Objects.requireNonNull(standardWifiEntryKey);
        standardWifiEntry.updateScanResultInfo((List) map.get(standardWifiEntryKey.mScanResultKey));
        boolean contains = set2.contains(standardWifiEntryKey);
        synchronized (standardWifiEntry) {
            standardWifiEntry.mIsUserShareable = contains;
        }
    }
}
