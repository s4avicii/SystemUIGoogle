package com.android.systemui.p006qs;

import android.content.DialogInterface;
import com.android.systemui.p006qs.FgsManagerController;
import java.util.LinkedHashSet;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.FgsManagerController$showDialog$1$1 */
/* compiled from: FgsManagerController.kt */
public final class FgsManagerController$showDialog$1$1 implements DialogInterface.OnDismissListener {
    public final /* synthetic */ FgsManagerController this$0;

    public FgsManagerController$showDialog$1$1(FgsManagerController fgsManagerController) {
        this.this$0 = fgsManagerController;
    }

    public final void onDismiss(DialogInterface dialogInterface) {
        FgsManagerController fgsManagerController = this.this$0;
        fgsManagerController.changesSinceDialog = false;
        synchronized (fgsManagerController.lock) {
            fgsManagerController.dialog = null;
            fgsManagerController.updateAppItemsLocked();
        }
        FgsManagerController fgsManagerController2 = this.this$0;
        Objects.requireNonNull(fgsManagerController2);
        LinkedHashSet<FgsManagerController.OnDialogDismissedListener> linkedHashSet = fgsManagerController2.onDialogDismissedListeners;
        FgsManagerController fgsManagerController3 = this.this$0;
        for (FgsManagerController.OnDialogDismissedListener fgsManagerController$showDialog$1$1$2$1 : linkedHashSet) {
            fgsManagerController3.mainExecutor.execute(new FgsManagerController$showDialog$1$1$2$1(fgsManagerController$showDialog$1$1$2$1));
        }
    }
}
