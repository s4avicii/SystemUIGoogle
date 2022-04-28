package com.android.systemui.statusbar.phone;

import android.content.DialogInterface;
import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SystemUIDialog$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ SystemUIDialog f$0;
    public final /* synthetic */ DialogInterface.OnClickListener f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ SystemUIDialog$$ExternalSyntheticLambda1(SystemUIDialog systemUIDialog, DialogInterface.OnClickListener onClickListener, int i) {
        this.f$0 = systemUIDialog;
        this.f$1 = onClickListener;
        this.f$2 = i;
    }

    public final void onClick(View view) {
        SystemUIDialog systemUIDialog = this.f$0;
        DialogInterface.OnClickListener onClickListener = this.f$1;
        int i = this.f$2;
        Objects.requireNonNull(systemUIDialog);
        onClickListener.onClick(systemUIDialog, i);
    }
}
