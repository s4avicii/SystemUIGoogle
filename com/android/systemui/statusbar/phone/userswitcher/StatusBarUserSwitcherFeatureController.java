package com.android.systemui.statusbar.phone.userswitcher;

import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.statusbar.policy.CallbackController;
import java.util.ArrayList;

/* compiled from: StatusBarUserSwitcherFeatureController.kt */
public final class StatusBarUserSwitcherFeatureController implements CallbackController<OnUserSwitcherPreferenceChangeListener> {
    public final FeatureFlags flags;
    public final ArrayList listeners = new ArrayList();

    public final void addCallback(OnUserSwitcherPreferenceChangeListener onUserSwitcherPreferenceChangeListener) {
        if (!this.listeners.contains(onUserSwitcherPreferenceChangeListener)) {
            this.listeners.add(onUserSwitcherPreferenceChangeListener);
        }
    }

    public final void removeCallback(Object obj) {
        this.listeners.remove((OnUserSwitcherPreferenceChangeListener) obj);
    }

    public StatusBarUserSwitcherFeatureController(FeatureFlags featureFlags) {
        this.flags = featureFlags;
        featureFlags.addListener();
    }
}
