package com.android.systemui.p006qs.tiles.dialog;

import android.content.Intent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import android.view.View;
import com.android.keyguard.KeyguardUpdateMonitor$$ExternalSyntheticOutline3;
import com.android.systemui.p006qs.tiles.dialog.InternetAdapter;
import com.android.systemui.p006qs.tiles.dialog.InternetDialogController;
import com.android.wifitrackerlib.WifiEntry;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ InternetAdapter.InternetViewHolder f$0;
    public final /* synthetic */ WifiEntry f$1;

    public /* synthetic */ InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda1(InternetAdapter.InternetViewHolder internetViewHolder, WifiEntry wifiEntry) {
        this.f$0 = internetViewHolder;
        this.f$1 = wifiEntry;
    }

    public final void onClick(View view) {
        InternetAdapter.InternetViewHolder internetViewHolder = this.f$0;
        WifiEntry wifiEntry = this.f$1;
        Objects.requireNonNull(internetViewHolder);
        if (wifiEntry.shouldEditBeforeConnect()) {
            Intent intent = new Intent("com.android.settings.WIFI_DIALOG");
            intent.addFlags(268435456);
            intent.addFlags(131072);
            intent.putExtra("key_chosen_wifientry_key", wifiEntry.getKey());
            intent.putExtra("connect_for_caller", false);
            internetViewHolder.mContext.startActivity(intent);
        }
        InternetDialogController internetDialogController = internetViewHolder.mInternetDialogController;
        Objects.requireNonNull(internetDialogController);
        if (wifiEntry.getWifiConfiguration() != null) {
            if (InternetDialogController.DEBUG) {
                KeyguardUpdateMonitor$$ExternalSyntheticOutline3.m28m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("connect networkId="), wifiEntry.getWifiConfiguration().networkId, "InternetDialogController");
            }
        } else if (InternetDialogController.DEBUG) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("connect to unsaved network ");
            m.append(wifiEntry.getTitle());
            Log.d("InternetDialogController", m.toString());
        }
        wifiEntry.connect(new InternetDialogController.WifiEntryConnectCallback(internetDialogController.mActivityStarter, wifiEntry, internetDialogController));
    }
}
