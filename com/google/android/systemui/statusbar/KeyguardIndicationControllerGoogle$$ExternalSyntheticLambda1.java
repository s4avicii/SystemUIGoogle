package com.google.android.systemui.statusbar;

import com.android.systemui.tuner.TunerService;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardIndicationControllerGoogle$$ExternalSyntheticLambda1 implements TunerService.Tunable {
    public final /* synthetic */ KeyguardIndicationControllerGoogle f$0;

    public /* synthetic */ KeyguardIndicationControllerGoogle$$ExternalSyntheticLambda1(KeyguardIndicationControllerGoogle keyguardIndicationControllerGoogle) {
        this.f$0 = keyguardIndicationControllerGoogle;
    }

    public final void onTuningChanged(String str, String str2) {
        KeyguardIndicationControllerGoogle keyguardIndicationControllerGoogle = this.f$0;
        Objects.requireNonNull(keyguardIndicationControllerGoogle);
        keyguardIndicationControllerGoogle.refreshAdaptiveChargingEnabled();
    }
}
