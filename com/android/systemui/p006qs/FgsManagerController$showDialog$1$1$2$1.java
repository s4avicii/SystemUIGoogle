package com.android.systemui.p006qs;

import com.android.systemui.p006qs.FgsManagerController;

/* renamed from: com.android.systemui.qs.FgsManagerController$showDialog$1$1$2$1 */
/* compiled from: FgsManagerController.kt */
public /* synthetic */ class FgsManagerController$showDialog$1$1$2$1 implements Runnable {
    public final /* synthetic */ FgsManagerController.OnDialogDismissedListener $tmp0;

    public FgsManagerController$showDialog$1$1$2$1(FgsManagerController.OnDialogDismissedListener onDialogDismissedListener) {
        this.$tmp0 = onDialogDismissedListener;
    }

    public final void run() {
        this.$tmp0.onDialogDismissed();
    }
}
