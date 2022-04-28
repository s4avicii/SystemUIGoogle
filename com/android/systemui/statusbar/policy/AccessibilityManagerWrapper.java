package com.android.systemui.statusbar.policy;

import android.view.accessibility.AccessibilityManager;

public final class AccessibilityManagerWrapper implements CallbackController<AccessibilityManager.AccessibilityServicesStateChangeListener> {
    public final AccessibilityManager mAccessibilityManager;

    public final void addCallback(Object obj) {
        this.mAccessibilityManager.addAccessibilityServicesStateChangeListener((AccessibilityManager.AccessibilityServicesStateChangeListener) obj);
    }

    public final void removeCallback(Object obj) {
        this.mAccessibilityManager.removeAccessibilityServicesStateChangeListener((AccessibilityManager.AccessibilityServicesStateChangeListener) obj);
    }

    public AccessibilityManagerWrapper(AccessibilityManager accessibilityManager) {
        this.mAccessibilityManager = accessibilityManager;
    }
}
