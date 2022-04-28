package com.android.systemui.navigationbar;

import android.view.accessibility.AccessibilityManager;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavBarHelper$$ExternalSyntheticLambda0 implements AccessibilityManager.AccessibilityServicesStateChangeListener {
    public final /* synthetic */ NavBarHelper f$0;

    public /* synthetic */ NavBarHelper$$ExternalSyntheticLambda0(NavBarHelper navBarHelper) {
        this.f$0 = navBarHelper;
    }

    public final void onAccessibilityServicesStateChanged(AccessibilityManager accessibilityManager) {
        NavBarHelper navBarHelper = this.f$0;
        Objects.requireNonNull(navBarHelper);
        navBarHelper.dispatchA11yEventUpdate();
    }
}
