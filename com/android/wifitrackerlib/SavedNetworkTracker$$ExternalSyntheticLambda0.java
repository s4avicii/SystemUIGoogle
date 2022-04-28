package com.android.wifitrackerlib;

import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.statusbar.phone.KeyguardBottomAreaView;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SavedNetworkTracker$$ExternalSyntheticLambda0 implements Function {
    public static final /* synthetic */ SavedNetworkTracker$$ExternalSyntheticLambda0 INSTANCE = new SavedNetworkTracker$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ SavedNetworkTracker$$ExternalSyntheticLambda0 INSTANCE$1 = new SavedNetworkTracker$$ExternalSyntheticLambda0(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ SavedNetworkTracker$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        boolean z = false;
        switch (this.$r8$classId) {
            case 0:
                return new StandardWifiEntry.StandardWifiEntryKey((WifiConfiguration) obj, false);
            default:
                Intent intent = KeyguardBottomAreaView.PHONE_INTENT;
                if (((ControlsController) obj).getFavorites().size() > 0) {
                    z = true;
                }
                return Boolean.valueOf(z);
        }
    }
}
