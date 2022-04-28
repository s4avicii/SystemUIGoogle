package com.android.systemui.biometrics;

import android.view.View;
import com.android.systemui.recents.OverviewProxyService;

/* compiled from: SidefpsController.kt */
public final class SidefpsController$overviewProxyListener$1 implements OverviewProxyService.OverviewProxyListener {
    public final /* synthetic */ SidefpsController this$0;

    public SidefpsController$overviewProxyListener$1(SidefpsController sidefpsController) {
        this.this$0 = sidefpsController;
    }

    public final void onTaskbarStatusUpdated(boolean z, boolean z2) {
        SidefpsController sidefpsController = this.this$0;
        View view = sidefpsController.overlayView;
        if (view != null) {
            sidefpsController.handler.postDelayed(new C0698x184619d8(sidefpsController, view), 500);
        }
    }
}
