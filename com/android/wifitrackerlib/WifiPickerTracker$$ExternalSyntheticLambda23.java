package com.android.wifitrackerlib;

import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda23 implements Predicate {
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda23 INSTANCE = new WifiPickerTracker$$ExternalSyntheticLambda23();

    public final boolean test(Object obj) {
        int connectedState = ((StandardWifiEntry) obj).getConnectedState();
        if (connectedState == 2 || connectedState == 1) {
            return true;
        }
        return false;
    }
}
