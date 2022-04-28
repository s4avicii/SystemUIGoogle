package com.android.systemui.statusbar.charging;

import android.view.WindowManager;
import java.util.Objects;

/* renamed from: com.android.systemui.statusbar.charging.WiredChargingRippleController$startRipple$1$onViewAttachedToWindow$1 */
/* compiled from: WiredChargingRippleController.kt */
public final class C1195xfe18c88d implements Runnable {
    public final /* synthetic */ WiredChargingRippleController this$0;

    public C1195xfe18c88d(WiredChargingRippleController wiredChargingRippleController) {
        this.this$0 = wiredChargingRippleController;
    }

    public final void run() {
        WiredChargingRippleController wiredChargingRippleController = this.this$0;
        WindowManager windowManager = wiredChargingRippleController.windowManager;
        Objects.requireNonNull(wiredChargingRippleController);
        windowManager.removeView(wiredChargingRippleController.rippleView);
    }
}
