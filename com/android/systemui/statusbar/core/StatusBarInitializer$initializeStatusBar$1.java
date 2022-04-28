package com.android.systemui.statusbar.core;

import android.app.Fragment;
import com.android.systemui.fragments.FragmentHostManager;
import com.android.systemui.statusbar.core.StatusBarInitializer;
import com.android.systemui.statusbar.phone.NotificationShadeWindowViewController;
import com.android.systemui.statusbar.phone.PhoneStatusBarTransitions;
import com.android.systemui.statusbar.phone.PhoneStatusBarViewController;
import com.android.systemui.statusbar.phone.StatusBar;
import com.android.systemui.statusbar.phone.StatusBar$$ExternalSyntheticLambda7;
import com.android.systemui.statusbar.phone.fragment.CollapsedStatusBarFragment;
import com.android.systemui.statusbar.phone.fragment.dagger.StatusBarFragmentComponent;
import java.util.Objects;

/* compiled from: StatusBarInitializer.kt */
public final class StatusBarInitializer$initializeStatusBar$1 implements FragmentHostManager.FragmentListener {
    public final /* synthetic */ StatusBarInitializer this$0;

    public final void onFragmentViewDestroyed(Fragment fragment) {
    }

    public StatusBarInitializer$initializeStatusBar$1(StatusBarInitializer statusBarInitializer) {
        this.this$0 = statusBarInitializer;
    }

    public final void onFragmentViewCreated(Fragment fragment) {
        StatusBarFragmentComponent statusBarFragmentComponent = ((CollapsedStatusBarFragment) fragment).mStatusBarFragmentComponent;
        if (statusBarFragmentComponent != null) {
            StatusBarInitializer statusBarInitializer = this.this$0;
            Objects.requireNonNull(statusBarInitializer);
            StatusBarInitializer.OnStatusBarViewUpdatedListener onStatusBarViewUpdatedListener = statusBarInitializer.statusBarViewUpdatedListener;
            if (onStatusBarViewUpdatedListener != null) {
                statusBarFragmentComponent.getPhoneStatusBarView();
                PhoneStatusBarViewController phoneStatusBarViewController = statusBarFragmentComponent.getPhoneStatusBarViewController();
                PhoneStatusBarTransitions phoneStatusBarTransitions = statusBarFragmentComponent.getPhoneStatusBarTransitions();
                StatusBar statusBar = ((StatusBar$$ExternalSyntheticLambda7) onStatusBarViewUpdatedListener).f$0;
                long[] jArr = StatusBar.CAMERA_LAUNCH_GESTURE_VIBRATION_TIMINGS;
                Objects.requireNonNull(statusBar);
                statusBar.mPhoneStatusBarViewController = phoneStatusBarViewController;
                statusBar.mStatusBarTransitions = phoneStatusBarTransitions;
                NotificationShadeWindowViewController notificationShadeWindowViewController = statusBar.mNotificationShadeWindowViewController;
                Objects.requireNonNull(notificationShadeWindowViewController);
                notificationShadeWindowViewController.mStatusBarViewController = phoneStatusBarViewController;
                statusBar.mNotificationPanelViewController.updatePanelExpansionAndVisibility();
                statusBar.setBouncerShowingForStatusBarComponents(statusBar.mBouncerShowing);
                statusBar.checkBarModes();
                return;
            }
            return;
        }
        throw new IllegalStateException();
    }
}
