package com.android.wifitrackerlib;

import android.net.wifi.hotspot2.PasspointConfiguration;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda6 implements Function {
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda6 INSTANCE = new WifiPickerTracker$$ExternalSyntheticLambda6(0);
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda6 INSTANCE$1 = new WifiPickerTracker$$ExternalSyntheticLambda6(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda6(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return PasspointWifiEntry.uniqueIdToPasspointWifiEntryKey(((PasspointConfiguration) obj).getUniqueId());
            default:
                return Boolean.valueOf(((StatusBar) obj).isKeyguardShowing());
        }
    }
}
