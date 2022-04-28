package com.android.keyguard;

import android.view.View;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class EmergencyButton$$ExternalSyntheticLambda0 implements View.OnLongClickListener {
    public final /* synthetic */ EmergencyButton f$0;

    public /* synthetic */ EmergencyButton$$ExternalSyntheticLambda0(EmergencyButton emergencyButton) {
        this.f$0 = emergencyButton;
    }

    public final boolean onLongClick(View view) {
        EmergencyButton emergencyButton = this.f$0;
        int i = EmergencyButton.$r8$clinit;
        Objects.requireNonNull(emergencyButton);
        if (emergencyButton.mLongPressWasDragged || !emergencyButton.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
            return false;
        }
        emergencyButton.mEmergencyAffordanceManager.performEmergencyCall();
        return true;
    }
}
