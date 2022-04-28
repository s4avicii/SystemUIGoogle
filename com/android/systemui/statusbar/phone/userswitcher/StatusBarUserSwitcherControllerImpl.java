package com.android.systemui.statusbar.phone.userswitcher;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.p006qs.user.UserSwitchDialogController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.util.ViewController;
import java.util.Objects;

/* compiled from: StatusBarUserSwitcherController.kt */
public final class StatusBarUserSwitcherControllerImpl extends ViewController<StatusBarUserSwitcherContainer> implements StatusBarUserSwitcherController {
    public final ActivityStarter activityStarter;
    public final StatusBarUserSwitcherFeatureController featureController;
    public final StatusBarUserSwitcherControllerImpl$featureFlagListener$1 featureFlagListener = new StatusBarUserSwitcherControllerImpl$featureFlagListener$1();
    public final FeatureFlags featureFlags;
    public final StatusBarUserSwitcherControllerImpl$listener$1 listener = new StatusBarUserSwitcherControllerImpl$listener$1(this);
    public final StatusBarUserInfoTracker tracker;
    public final UserSwitchDialogController userSwitcherDialogController;

    public final void onViewAttached() {
        this.tracker.addCallback((CurrentUserChipInfoUpdatedListener) this.listener);
        this.featureController.addCallback((OnUserSwitcherPreferenceChangeListener) this.featureFlagListener);
        ((StatusBarUserSwitcherContainer) this.mView).setOnClickListener(new StatusBarUserSwitcherControllerImpl$onViewAttached$1(this));
        updateEnabled();
    }

    public final void onViewDetached() {
        StatusBarUserInfoTracker statusBarUserInfoTracker = this.tracker;
        StatusBarUserSwitcherControllerImpl$listener$1 statusBarUserSwitcherControllerImpl$listener$1 = this.listener;
        Objects.requireNonNull(statusBarUserInfoTracker);
        statusBarUserInfoTracker.listeners.remove(statusBarUserSwitcherControllerImpl$listener$1);
        if (statusBarUserInfoTracker.listeners.isEmpty()) {
            statusBarUserInfoTracker.listening = false;
            statusBarUserInfoTracker.userInfoController.removeCallback(statusBarUserInfoTracker.userInfoChangedListener);
        }
        StatusBarUserSwitcherFeatureController statusBarUserSwitcherFeatureController = this.featureController;
        StatusBarUserSwitcherControllerImpl$featureFlagListener$1 statusBarUserSwitcherControllerImpl$featureFlagListener$1 = this.featureFlagListener;
        Objects.requireNonNull(statusBarUserSwitcherFeatureController);
        statusBarUserSwitcherFeatureController.listeners.remove(statusBarUserSwitcherControllerImpl$featureFlagListener$1);
        ((StatusBarUserSwitcherContainer) this.mView).setOnClickListener((View.OnClickListener) null);
    }

    public final void updateChip() {
        StatusBarUserSwitcherContainer statusBarUserSwitcherContainer = (StatusBarUserSwitcherContainer) this.mView;
        Objects.requireNonNull(statusBarUserSwitcherContainer);
        TextView textView = statusBarUserSwitcherContainer.text;
        ImageView imageView = null;
        if (textView == null) {
            textView = null;
        }
        StatusBarUserInfoTracker statusBarUserInfoTracker = this.tracker;
        Objects.requireNonNull(statusBarUserInfoTracker);
        textView.setText(statusBarUserInfoTracker.currentUserName);
        StatusBarUserSwitcherContainer statusBarUserSwitcherContainer2 = (StatusBarUserSwitcherContainer) this.mView;
        Objects.requireNonNull(statusBarUserSwitcherContainer2);
        ImageView imageView2 = statusBarUserSwitcherContainer2.avatar;
        if (imageView2 != null) {
            imageView = imageView2;
        }
        StatusBarUserInfoTracker statusBarUserInfoTracker2 = this.tracker;
        Objects.requireNonNull(statusBarUserInfoTracker2);
        imageView.setImageDrawable(statusBarUserInfoTracker2.currentUserAvatar);
    }

    public final void updateEnabled() {
        StatusBarUserSwitcherFeatureController statusBarUserSwitcherFeatureController = this.featureController;
        Objects.requireNonNull(statusBarUserSwitcherFeatureController);
        if (statusBarUserSwitcherFeatureController.flags.isEnabled(Flags.STATUS_BAR_USER_SWITCHER)) {
            StatusBarUserInfoTracker statusBarUserInfoTracker = this.tracker;
            Objects.requireNonNull(statusBarUserInfoTracker);
            if (statusBarUserInfoTracker.userSwitcherEnabled) {
                ((StatusBarUserSwitcherContainer) this.mView).setVisibility(0);
                updateChip();
                return;
            }
        }
        ((StatusBarUserSwitcherContainer) this.mView).setVisibility(8);
    }

    public StatusBarUserSwitcherControllerImpl(StatusBarUserSwitcherContainer statusBarUserSwitcherContainer, StatusBarUserInfoTracker statusBarUserInfoTracker, StatusBarUserSwitcherFeatureController statusBarUserSwitcherFeatureController, UserSwitchDialogController userSwitchDialogController, FeatureFlags featureFlags2, ActivityStarter activityStarter2) {
        super(statusBarUserSwitcherContainer);
        this.tracker = statusBarUserInfoTracker;
        this.featureController = statusBarUserSwitcherFeatureController;
        this.userSwitcherDialogController = userSwitchDialogController;
        this.featureFlags = featureFlags2;
        this.activityStarter = activityStarter2;
    }
}
