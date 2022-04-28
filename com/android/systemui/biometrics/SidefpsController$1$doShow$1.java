package com.android.systemui.biometrics;

import android.util.Log;

/* compiled from: SidefpsController.kt */
public final class SidefpsController$1$doShow$1 implements Runnable {
    public final /* synthetic */ SidefpsController this$0;

    public SidefpsController$1$doShow$1(SidefpsController sidefpsController) {
        this.this$0 = sidefpsController;
    }

    public final void run() {
        SidefpsController sidefpsController = this.this$0;
        if (sidefpsController.overlayView == null) {
            sidefpsController.setOverlayView(sidefpsController.createOverlayForDisplay());
        } else {
            Log.v("SidefpsController", "overlay already shown");
        }
    }
}
