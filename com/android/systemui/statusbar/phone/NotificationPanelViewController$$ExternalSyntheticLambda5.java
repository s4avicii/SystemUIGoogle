package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.window.StatusBarWindowStateListener;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda5 implements StatusBarWindowStateListener {
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda5(NotificationPanelViewController notificationPanelViewController) {
        this.f$0 = notificationPanelViewController;
    }

    public final void onStatusBarWindowStateChanged(int i) {
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        if (i == 0) {
            Objects.requireNonNull(notificationPanelViewController);
        } else if (notificationPanelViewController.mStatusBarStateController.getState() == 0) {
            notificationPanelViewController.collapsePanel(false, false, 1.0f);
        }
    }
}
