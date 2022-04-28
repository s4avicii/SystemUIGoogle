package com.android.systemui.biometrics;

import android.view.View;

/* renamed from: com.android.systemui.biometrics.SidefpsController$overviewProxyListener$1$onTaskbarStatusUpdated$1$1 */
/* compiled from: SidefpsController.kt */
public final class C0698x184619d8 implements Runnable {
    public final /* synthetic */ View $view;
    public final /* synthetic */ SidefpsController this$0;

    public C0698x184619d8(SidefpsController sidefpsController, View view) {
        this.this$0 = sidefpsController;
        this.$view = view;
    }

    public final void run() {
        this.this$0.updateOverlayVisibility(this.$view);
    }
}
