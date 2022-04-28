package com.android.wifitrackerlib;

import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiConfiguration;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda8 implements Function {
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda8 INSTANCE = new WifiPickerTracker$$ExternalSyntheticLambda8(0);
    public static final /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda8 INSTANCE$1 = new WifiPickerTracker$$ExternalSyntheticLambda8(1);
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda8(int i) {
        this.$r8$classId = i;
    }

    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return Integer.valueOf(((WifiConfiguration) obj).networkId);
            default:
                ColorDrawable colorDrawable = InternetDialogController.EMPTY_DRAWABLE;
                return ((InternetDialogController.AnonymousClass1DisplayInfo) obj).originalName;
        }
    }
}
