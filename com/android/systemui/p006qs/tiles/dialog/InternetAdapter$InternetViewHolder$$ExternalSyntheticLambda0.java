package com.android.systemui.p006qs.tiles.dialog;

import android.view.View;
import com.android.systemui.p006qs.tiles.dialog.InternetAdapter;
import com.android.wifitrackerlib.WifiEntry;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ InternetAdapter.InternetViewHolder f$0;
    public final /* synthetic */ WifiEntry f$1;

    public /* synthetic */ InternetAdapter$InternetViewHolder$$ExternalSyntheticLambda0(InternetAdapter.InternetViewHolder internetViewHolder, WifiEntry wifiEntry) {
        this.f$0 = internetViewHolder;
        this.f$1 = wifiEntry;
    }

    public final void onClick(View view) {
        InternetAdapter.InternetViewHolder internetViewHolder = this.f$0;
        WifiEntry wifiEntry = this.f$1;
        Objects.requireNonNull(internetViewHolder);
        internetViewHolder.mInternetDialogController.launchWifiNetworkDetailsSetting(wifiEntry.getKey(), view);
    }
}
