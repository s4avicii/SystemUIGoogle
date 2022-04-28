package com.android.wifitrackerlib;

import android.net.wifi.hotspot2.PasspointConfiguration;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda15 implements Predicate {
    public final /* synthetic */ WifiPickerTracker f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda15(WifiPickerTracker wifiPickerTracker) {
        this.f$0 = wifiPickerTracker;
    }

    public final boolean test(Object obj) {
        WifiPickerTracker wifiPickerTracker = this.f$0;
        Objects.requireNonNull(wifiPickerTracker);
        PasspointWifiEntry passpointWifiEntry = (PasspointWifiEntry) ((Map.Entry) obj).getValue();
        Objects.requireNonNull(passpointWifiEntry);
        passpointWifiEntry.updatePasspointConfig((PasspointConfiguration) wifiPickerTracker.mPasspointConfigCache.get(passpointWifiEntry.mKey));
        if (passpointWifiEntry.isSubscription() || passpointWifiEntry.isSuggestion()) {
            return false;
        }
        return true;
    }
}
