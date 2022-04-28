package com.android.systemui.statusbar.phone;

import androidx.leanback.R$color;
import com.android.systemui.navigationbar.NavigationModeController;

/* compiled from: NotificationsQSContainerController.kt */
public final class NotificationsQSContainerController$onInit$currentMode$1 implements NavigationModeController.ModeChangedListener {
    public final /* synthetic */ NotificationsQSContainerController this$0;

    public NotificationsQSContainerController$onInit$currentMode$1(NotificationsQSContainerController notificationsQSContainerController) {
        this.this$0 = notificationsQSContainerController;
    }

    public final void onNavigationModeChanged(int i) {
        this.this$0.isGestureNavigation = R$color.isGesturalMode(i);
    }
}
