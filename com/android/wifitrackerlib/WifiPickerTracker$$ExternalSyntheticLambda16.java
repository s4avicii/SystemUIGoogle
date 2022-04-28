package com.android.wifitrackerlib;

import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda16 implements Predicate {
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda16 INSTANCE = new WifiPickerTracker$$ExternalSyntheticLambda16();

    public final boolean test(Object obj) {
        if (((PasspointWifiEntry) obj).getConnectedState() == 0) {
            return true;
        }
        return false;
    }
}
