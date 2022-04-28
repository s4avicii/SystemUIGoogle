package com.google.android.systemui.statusbar;

import android.provider.DeviceConfig;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardIndicationControllerGoogle$$ExternalSyntheticLambda0 implements DeviceConfig.OnPropertiesChangedListener {
    public final /* synthetic */ KeyguardIndicationControllerGoogle f$0;

    public /* synthetic */ KeyguardIndicationControllerGoogle$$ExternalSyntheticLambda0(KeyguardIndicationControllerGoogle keyguardIndicationControllerGoogle) {
        this.f$0 = keyguardIndicationControllerGoogle;
    }

    public final void onPropertiesChanged(DeviceConfig.Properties properties) {
        KeyguardIndicationControllerGoogle keyguardIndicationControllerGoogle = this.f$0;
        Objects.requireNonNull(keyguardIndicationControllerGoogle);
        if (properties.getKeyset().contains("adaptive_charging_enabled")) {
            keyguardIndicationControllerGoogle.triggerAdaptiveChargingStatusUpdate();
        }
    }
}
