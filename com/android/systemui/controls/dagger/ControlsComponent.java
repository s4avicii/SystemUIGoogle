package com.android.systemui.controls.dagger;

import android.provider.Settings;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsTileResourceConfiguration;
import com.android.systemui.controls.controller.ControlsTileResourceConfigurationImpl;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.controls.p004ui.ControlsUiController;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.settings.SecureSettings;
import dagger.Lazy;
import java.util.Optional;

/* compiled from: ControlsComponent.kt */
public final class ControlsComponent {
    public boolean canShowWhileLockedSetting;
    public final ControlsTileResourceConfiguration controlsTileResourceConfiguration;
    public final boolean featureEnabled;
    public final KeyguardStateController keyguardStateController;
    public final Lazy<ControlsController> lazyControlsController;
    public final Lazy<ControlsListingController> lazyControlsListingController;
    public final Lazy<ControlsUiController> lazyControlsUiController;
    public final LockPatternUtils lockPatternUtils;
    public final SecureSettings secureSettings;
    public final ControlsComponent$showWhileLockedObserver$1 showWhileLockedObserver;
    public final UserTracker userTracker;

    /* compiled from: ControlsComponent.kt */
    public enum Visibility {
        AVAILABLE,
        AVAILABLE_AFTER_UNLOCK,
        UNAVAILABLE
    }

    public final Optional<ControlsController> getControlsController() {
        if (this.featureEnabled) {
            return Optional.of(this.lazyControlsController.get());
        }
        return Optional.empty();
    }

    public final Optional<ControlsListingController> getControlsListingController() {
        if (this.featureEnabled) {
            return Optional.of(this.lazyControlsListingController.get());
        }
        return Optional.empty();
    }

    public final Visibility getVisibility() {
        Visibility visibility = Visibility.AVAILABLE_AFTER_UNLOCK;
        if (!this.featureEnabled) {
            return Visibility.UNAVAILABLE;
        }
        if (this.lockPatternUtils.getStrongAuthForUser(this.userTracker.getUserHandle().getIdentifier()) == 1) {
            return visibility;
        }
        if (this.canShowWhileLockedSetting || this.keyguardStateController.isUnlocked()) {
            return Visibility.AVAILABLE;
        }
        return visibility;
    }

    public ControlsComponent(boolean z, Lazy lazy, Lazy lazy2, Lazy lazy3, LockPatternUtils lockPatternUtils2, KeyguardStateController keyguardStateController2, UserTracker userTracker2, SecureSettings secureSettings2, Optional optional) {
        this.featureEnabled = z;
        this.lazyControlsController = lazy;
        this.lazyControlsUiController = lazy2;
        this.lazyControlsListingController = lazy3;
        this.lockPatternUtils = lockPatternUtils2;
        this.keyguardStateController = keyguardStateController2;
        this.userTracker = userTracker2;
        this.secureSettings = secureSettings2;
        this.controlsTileResourceConfiguration = (ControlsTileResourceConfiguration) optional.orElse(new ControlsTileResourceConfigurationImpl());
        ControlsComponent$showWhileLockedObserver$1 controlsComponent$showWhileLockedObserver$1 = new ControlsComponent$showWhileLockedObserver$1(this);
        this.showWhileLockedObserver = controlsComponent$showWhileLockedObserver$1;
        if (z) {
            boolean z2 = false;
            secureSettings2.registerContentObserver(Settings.Secure.getUriFor("lockscreen_show_controls"), false, controlsComponent$showWhileLockedObserver$1);
            this.canShowWhileLockedSetting = secureSettings2.getInt("lockscreen_show_controls", 0) != 0 ? true : z2;
        }
    }
}
