package com.android.systemui.statusbar.charging;

import android.content.res.Configuration;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.policy.ConfigurationController;

/* renamed from: com.android.systemui.statusbar.charging.WiredChargingRippleController$registerCallbacks$configurationChangedListener$1 */
/* compiled from: WiredChargingRippleController.kt */
public final class C1194xe97593c0 implements ConfigurationController.ConfigurationListener {
    public final /* synthetic */ WiredChargingRippleController this$0;

    public C1194xe97593c0(WiredChargingRippleController wiredChargingRippleController) {
        this.this$0 = wiredChargingRippleController;
    }

    public final void onConfigChanged(Configuration configuration) {
        WiredChargingRippleController wiredChargingRippleController = this.this$0;
        wiredChargingRippleController.normalizedPortPosX = wiredChargingRippleController.context.getResources().getFloat(C1777R.dimen.physical_charger_port_location_normalized_x);
        WiredChargingRippleController wiredChargingRippleController2 = this.this$0;
        wiredChargingRippleController2.normalizedPortPosY = wiredChargingRippleController2.context.getResources().getFloat(C1777R.dimen.physical_charger_port_location_normalized_y);
    }

    public final void onThemeChanged() {
        this.this$0.updateRippleColor();
    }

    public final void onUiModeChanged() {
        this.this$0.updateRippleColor();
    }
}
