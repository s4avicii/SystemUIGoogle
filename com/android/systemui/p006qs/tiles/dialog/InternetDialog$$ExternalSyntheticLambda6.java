package com.android.systemui.p006qs.tiles.dialog;

import android.view.View;
import com.android.wifitrackerlib.WifiEntry;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialog$$ExternalSyntheticLambda6 implements View.OnClickListener {
    public final /* synthetic */ InternetDialog f$0;

    public /* synthetic */ InternetDialog$$ExternalSyntheticLambda6(InternetDialog internetDialog) {
        this.f$0 = internetDialog;
    }

    public final void onClick(View view) {
        InternetDialog internetDialog = this.f$0;
        Objects.requireNonNull(internetDialog);
        WifiEntry wifiEntry = internetDialog.mConnectedWifiEntry;
        if (wifiEntry != null) {
            internetDialog.mInternetDialogController.launchWifiNetworkDetailsSetting(wifiEntry.getKey(), view);
        }
    }
}
