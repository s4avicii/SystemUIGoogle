package com.android.systemui.statusbar.policy;

import android.content.Context;
import android.view.accessibility.AccessibilityManager;
import java.util.ArrayList;

public final class AccessibilityController implements AccessibilityManager.AccessibilityStateChangeListener, AccessibilityManager.TouchExplorationStateChangeListener {
    public boolean mAccessibilityEnabled;
    public final ArrayList<AccessibilityStateChangedCallback> mChangeCallbacks = new ArrayList<>();
    public boolean mTouchExplorationEnabled;

    public interface AccessibilityStateChangedCallback {
        void onStateChanged(boolean z, boolean z2);
    }

    public final void onAccessibilityStateChanged(boolean z) {
        this.mAccessibilityEnabled = z;
        int size = this.mChangeCallbacks.size();
        for (int i = 0; i < size; i++) {
            this.mChangeCallbacks.get(i).onStateChanged(this.mAccessibilityEnabled, this.mTouchExplorationEnabled);
        }
    }

    public final void onTouchExplorationStateChanged(boolean z) {
        this.mTouchExplorationEnabled = z;
        int size = this.mChangeCallbacks.size();
        for (int i = 0; i < size; i++) {
            this.mChangeCallbacks.get(i).onStateChanged(this.mAccessibilityEnabled, this.mTouchExplorationEnabled);
        }
    }

    public AccessibilityController(Context context) {
        AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService("accessibility");
        accessibilityManager.addTouchExplorationStateChangeListener(this);
        accessibilityManager.addAccessibilityStateChangeListener(this);
        this.mAccessibilityEnabled = accessibilityManager.isEnabled();
        this.mTouchExplorationEnabled = accessibilityManager.isTouchExplorationEnabled();
    }
}
