package com.android.systemui.p006qs;

/* renamed from: com.android.systemui.qs.FgsManagerController$showDialog$1$3 */
/* compiled from: FgsManagerController.kt */
public final class FgsManagerController$showDialog$1$3 implements Runnable {
    public final /* synthetic */ FgsManagerController this$0;

    public FgsManagerController$showDialog$1$3(FgsManagerController fgsManagerController) {
        this.this$0 = fgsManagerController;
    }

    public final void run() {
        FgsManagerController fgsManagerController = this.this$0;
        synchronized (fgsManagerController.lock) {
            fgsManagerController.updateAppItemsLocked();
        }
    }
}
