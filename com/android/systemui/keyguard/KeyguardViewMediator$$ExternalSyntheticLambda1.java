package com.android.systemui.keyguard;

import androidx.leanback.R$color;
import com.android.systemui.navigationbar.NavigationModeController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardViewMediator$$ExternalSyntheticLambda1 implements NavigationModeController.ModeChangedListener {
    public final /* synthetic */ KeyguardViewMediator f$0;

    public /* synthetic */ KeyguardViewMediator$$ExternalSyntheticLambda1(KeyguardViewMediator keyguardViewMediator) {
        this.f$0 = keyguardViewMediator;
    }

    public final void onNavigationModeChanged(int i) {
        KeyguardViewMediator keyguardViewMediator = this.f$0;
        boolean z = KeyguardViewMediator.DEBUG;
        Objects.requireNonNull(keyguardViewMediator);
        keyguardViewMediator.mInGestureNavigationMode = R$color.isGesturalMode(i);
    }
}
