package com.android.systemui.statusbar.phone;

import android.content.DialogInterface;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SystemUIDialog$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ SystemUIDialog f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ DialogInterface.OnClickListener f$2;

    public /* synthetic */ SystemUIDialog$$ExternalSyntheticLambda2(SystemUIDialog systemUIDialog, int i, DialogInterface.OnClickListener onClickListener) {
        this.f$0 = systemUIDialog;
        this.f$1 = i;
        this.f$2 = onClickListener;
    }

    public final void run() {
        SystemUIDialog systemUIDialog = this.f$0;
        int i = this.f$1;
        DialogInterface.OnClickListener onClickListener = this.f$2;
        Objects.requireNonNull(systemUIDialog);
        systemUIDialog.getButton(i).setOnClickListener(new SystemUIDialog$$ExternalSyntheticLambda1(systemUIDialog, onClickListener, i));
    }
}
