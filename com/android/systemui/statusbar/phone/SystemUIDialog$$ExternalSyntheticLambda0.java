package com.android.systemui.statusbar.phone;

import android.content.DialogInterface;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SystemUIDialog$$ExternalSyntheticLambda0 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ SystemUIDialog.DismissReceiver f$0;
    public final /* synthetic */ Runnable f$1 = null;

    public /* synthetic */ SystemUIDialog$$ExternalSyntheticLambda0(SystemUIDialog.DismissReceiver dismissReceiver) {
        this.f$0 = dismissReceiver;
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        SystemUIDialog.DismissReceiver dismissReceiver = this.f$0;
        Runnable runnable = this.f$1;
        Objects.requireNonNull(dismissReceiver);
        if (dismissReceiver.mRegistered) {
            dismissReceiver.mBroadcastDispatcher.unregisterReceiver(dismissReceiver);
            dismissReceiver.mRegistered = false;
        }
        if (runnable != null) {
            runnable.run();
        }
    }
}
