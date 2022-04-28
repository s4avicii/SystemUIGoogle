package com.android.wifitrackerlib;

import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda18 implements Predicate {
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda18 INSTANCE = new WifiPickerTracker$$ExternalSyntheticLambda18();

    public final boolean test(Object obj) {
        StandardWifiEntry standardWifiEntry = (StandardWifiEntry) obj;
        Objects.requireNonNull(standardWifiEntry);
        if (standardWifiEntry.mLevel == -1) {
            return true;
        }
        return false;
    }
}
