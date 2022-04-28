package com.android.systemui.statusbar.phone;

import androidx.leanback.R$color;
import com.android.systemui.navigationbar.NavigationModeController;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda3 implements NavigationModeController.ModeChangedListener {
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda3(NotificationPanelViewController notificationPanelViewController) {
        this.f$0 = notificationPanelViewController;
    }

    public final void onNavigationModeChanged(int i) {
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        Objects.requireNonNull(notificationPanelViewController);
        notificationPanelViewController.mIsGestureNavigation = R$color.isGesturalMode(i);
    }
}
