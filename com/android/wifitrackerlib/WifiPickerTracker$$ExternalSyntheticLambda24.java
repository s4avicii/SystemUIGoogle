package com.android.wifitrackerlib;

import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda24 implements Predicate {
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda24 INSTANCE = new WifiPickerTracker$$ExternalSyntheticLambda24();

    public final boolean test(Object obj) {
        int connectedState = ((PasspointWifiEntry) obj).getConnectedState();
        if (connectedState == 2 || connectedState == 1) {
            return true;
        }
        return false;
    }
}
