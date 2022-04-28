package com.android.wifitrackerlib;

import android.net.wifi.WifiConfiguration;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda20 implements Predicate {
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda20 INSTANCE = new WifiPickerTracker$$ExternalSyntheticLambda20();

    public final boolean test(Object obj) {
        return !((WifiConfiguration) obj).isEphemeral();
    }
}
