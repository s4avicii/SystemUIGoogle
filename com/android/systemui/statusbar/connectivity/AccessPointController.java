package com.android.systemui.statusbar.connectivity;

import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.wifitrackerlib.MergedCarrierEntry;
import com.android.wifitrackerlib.WifiEntry;
import java.util.List;

/* compiled from: AccessPointController.kt */
public interface AccessPointController {

    /* compiled from: AccessPointController.kt */
    public interface AccessPointCallback {
        void onAccessPointsChanged(List<WifiEntry> list);
    }

    void addAccessPointCallback(InternetDialogController internetDialogController);

    boolean canConfigMobileData();

    boolean canConfigWifi();

    MergedCarrierEntry getMergedCarrierEntry();

    void removeAccessPointCallback(InternetDialogController internetDialogController);

    void scanForAccessPoints();
}
