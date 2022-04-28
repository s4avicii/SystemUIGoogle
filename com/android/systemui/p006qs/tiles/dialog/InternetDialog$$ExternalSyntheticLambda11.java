package com.android.systemui.p006qs.tiles.dialog;

import com.android.wifitrackerlib.WifiEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda11 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialog$$ExternalSyntheticLambda11 implements Runnable {
    public final /* synthetic */ InternetDialog f$0;
    public final /* synthetic */ WifiEntry f$1;
    public final /* synthetic */ List f$2;
    public final /* synthetic */ boolean f$3;
    public final /* synthetic */ boolean f$4;

    public /* synthetic */ InternetDialog$$ExternalSyntheticLambda11(InternetDialog internetDialog, WifiEntry wifiEntry, ArrayList arrayList, boolean z, boolean z2) {
        this.f$0 = internetDialog;
        this.f$1 = wifiEntry;
        this.f$2 = arrayList;
        this.f$3 = z;
        this.f$4 = z2;
    }

    public final void run() {
        int i;
        InternetDialog internetDialog = this.f$0;
        WifiEntry wifiEntry = this.f$1;
        List<WifiEntry> list = this.f$2;
        boolean z = this.f$3;
        boolean z2 = this.f$4;
        Objects.requireNonNull(internetDialog);
        internetDialog.mConnectedWifiEntry = wifiEntry;
        if (list == null) {
            i = 0;
        } else {
            i = list.size();
        }
        internetDialog.mWifiEntriesCount = i;
        internetDialog.mHasMoreWifiEntries = z;
        internetDialog.updateDialog(z2);
        InternetAdapter internetAdapter = internetDialog.mAdapter;
        int i2 = internetDialog.mWifiEntriesCount;
        Objects.requireNonNull(internetAdapter);
        internetAdapter.mWifiEntries = list;
        int i3 = internetAdapter.mMaxEntriesCount;
        if (i2 >= i3) {
            i2 = i3;
        }
        internetAdapter.mWifiEntriesCount = i2;
        internetDialog.mAdapter.notifyDataSetChanged();
    }
}
