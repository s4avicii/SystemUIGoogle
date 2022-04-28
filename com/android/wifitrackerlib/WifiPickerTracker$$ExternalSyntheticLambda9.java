package com.android.wifitrackerlib;

import android.net.wifi.ScanResult;
import com.android.systemui.statusbar.notification.row.ExpandableOutlineView;
import com.android.wifitrackerlib.StandardWifiEntry;
import java.util.Objects;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda9 implements Function {
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda9 INSTANCE = new WifiPickerTracker$$ExternalSyntheticLambda9(0);
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda9 INSTANCE$1 = new WifiPickerTracker$$ExternalSyntheticLambda9(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda9(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return new StandardWifiEntry.ScanResultKey((ScanResult) obj);
            default:
                ExpandableOutlineView expandableOutlineView = (ExpandableOutlineView) obj;
                Objects.requireNonNull(expandableOutlineView);
                return Float.valueOf(expandableOutlineView.mCurrentBottomRoundness);
        }
    }
}
