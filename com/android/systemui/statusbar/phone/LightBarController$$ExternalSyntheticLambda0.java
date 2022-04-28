package com.android.systemui.statusbar.phone;

import com.android.systemui.navigationbar.NavigationModeController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LightBarController$$ExternalSyntheticLambda0 implements NavigationModeController.ModeChangedListener {
    public final /* synthetic */ LightBarController f$0;

    public /* synthetic */ LightBarController$$ExternalSyntheticLambda0(LightBarController lightBarController) {
        this.f$0 = lightBarController;
    }

    public final void onNavigationModeChanged(int i) {
        LightBarController lightBarController = this.f$0;
        Objects.requireNonNull(lightBarController);
        lightBarController.mNavigationMode = i;
    }
}
