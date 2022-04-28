package com.google.android.systemui.columbus;

import android.content.DialogInterface;
import android.view.View;
import com.google.android.systemui.columbus.C2193x675ce2c1;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ColumbusTargetRequestDialog$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ ColumbusTargetRequestDialog f$0;
    public final /* synthetic */ DialogInterface.OnClickListener f$1;

    public /* synthetic */ ColumbusTargetRequestDialog$$ExternalSyntheticLambda0(ColumbusTargetRequestDialog columbusTargetRequestDialog, C2193x675ce2c1.C21941 r2) {
        this.f$0 = columbusTargetRequestDialog;
        this.f$1 = r2;
    }

    public final void onClick(View view) {
        ColumbusTargetRequestDialog columbusTargetRequestDialog = this.f$0;
        DialogInterface.OnClickListener onClickListener = this.f$1;
        Objects.requireNonNull(columbusTargetRequestDialog);
        onClickListener.onClick(columbusTargetRequestDialog, -2);
        columbusTargetRequestDialog.dismiss();
    }
}
