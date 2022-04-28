package com.android.wifitrackerlib;

import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda19 implements Predicate {
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda19 INSTANCE = new WifiPickerTracker$$ExternalSyntheticLambda19();

    public final boolean test(Object obj) {
        OsuWifiEntry osuWifiEntry = (OsuWifiEntry) ((Map.Entry) obj).getValue();
        Objects.requireNonNull(osuWifiEntry);
        if (osuWifiEntry.mLevel == -1) {
            return true;
        }
        return false;
    }
}
