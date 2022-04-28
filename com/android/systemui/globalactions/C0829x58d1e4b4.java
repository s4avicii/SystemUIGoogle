package com.android.systemui.globalactions;

import com.android.systemui.globalactions.GlobalActionsDialogLite;
import java.util.Objects;

/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda6 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class C0829x58d1e4b4 implements Runnable {
    public final /* synthetic */ GlobalActionsDialogLite.ActionsDialogLite f$0;

    public /* synthetic */ C0829x58d1e4b4(GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite) {
        this.f$0 = actionsDialogLite;
    }

    public final void run() {
        GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
        int i = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
        Objects.requireNonNull(actionsDialogLite);
        actionsDialogLite.setDismissOverride((Runnable) null);
        actionsDialogLite.hide();
        actionsDialogLite.dismiss();
    }
}
