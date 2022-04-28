package com.android.systemui.p006qs.tiles.dialog;

import android.widget.CompoundButton;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda8 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialog$$ExternalSyntheticLambda8 implements CompoundButton.OnCheckedChangeListener {
    public final /* synthetic */ InternetDialog f$0;

    public /* synthetic */ InternetDialog$$ExternalSyntheticLambda8(InternetDialog internetDialog) {
        this.f$0 = internetDialog;
    }

    public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        InternetDialog internetDialog = this.f$0;
        Objects.requireNonNull(internetDialog);
        if (internetDialog.mWifiManager != null) {
            compoundButton.setChecked(z);
            internetDialog.mWifiManager.setWifiEnabled(z);
        }
    }
}
