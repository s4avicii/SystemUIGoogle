package com.android.systemui.navigationbar;

import android.provider.Settings;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationModeController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ NavigationModeController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ NavigationModeController$$ExternalSyntheticLambda0(NavigationModeController navigationModeController, int i) {
        this.f$0 = navigationModeController;
        this.f$1 = i;
    }

    public final void run() {
        NavigationModeController navigationModeController = this.f$0;
        int i = this.f$1;
        Objects.requireNonNull(navigationModeController);
        Settings.Secure.putString(navigationModeController.mCurrentUserContext.getContentResolver(), "navigation_mode", String.valueOf(i));
    }
}
