package com.android.systemui.p006qs.tiles.dialog;

import android.content.DialogInterface;
import com.android.systemui.Prefs;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialog$$ExternalSyntheticLambda2 implements DialogInterface.OnClickListener {
    public final /* synthetic */ InternetDialog f$0;

    public /* synthetic */ InternetDialog$$ExternalSyntheticLambda2(InternetDialog internetDialog) {
        this.f$0 = internetDialog;
    }

    public final void onClick(DialogInterface dialogInterface, int i) {
        InternetDialog internetDialog = this.f$0;
        Objects.requireNonNull(internetDialog);
        internetDialog.mInternetDialogController.setMobileDataEnabled(internetDialog.mContext, internetDialog.mDefaultDataSubId, false);
        internetDialog.mMobileDataToggle.setChecked(false);
        Prefs.putBoolean(internetDialog.mContext, "QsHasTurnedOffMobileData", true);
    }
}
