package com.android.systemui.p006qs.tiles.dialog;

import android.content.DialogInterface;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.tiles.dialog.InternetDialog$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class InternetDialog$$ExternalSyntheticLambda0 implements DialogInterface.OnCancelListener {
    public final /* synthetic */ InternetDialog f$0;

    public /* synthetic */ InternetDialog$$ExternalSyntheticLambda0(InternetDialog internetDialog) {
        this.f$0 = internetDialog;
    }

    public final void onCancel(DialogInterface dialogInterface) {
        InternetDialog internetDialog = this.f$0;
        Objects.requireNonNull(internetDialog);
        internetDialog.mMobileDataToggle.setChecked(true);
    }
}
