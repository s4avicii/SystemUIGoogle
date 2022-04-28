package com.android.systemui.navigationbar;

import com.android.systemui.statusbar.phone.NotificationPanelViewController;
import com.android.systemui.statusbar.phone.StatusBar;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NavigationBar$$ExternalSyntheticLambda15 implements Consumer {
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ NavigationBar$$ExternalSyntheticLambda15(boolean z) {
        this.f$0 = z;
    }

    public final void accept(Object obj) {
        StatusBar statusBar = (StatusBar) obj;
        boolean z = true;
        boolean z2 = !this.f$0;
        Objects.requireNonNull(statusBar);
        NotificationPanelViewController notificationPanelViewController = statusBar.mNotificationPanelViewController;
        Objects.requireNonNull(notificationPanelViewController);
        if (notificationPanelViewController.mQsScrimEnabled == z2) {
            z = false;
        }
        notificationPanelViewController.mQsScrimEnabled = z2;
        if (z) {
            notificationPanelViewController.updateQsState();
        }
    }
}
