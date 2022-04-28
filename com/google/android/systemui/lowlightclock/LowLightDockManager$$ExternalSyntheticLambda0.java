package com.google.android.systemui.lowlightclock;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.PreparationCoordinator;
import com.android.systemui.statusbar.notification.collection.inflation.NotifInflater;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.google.android.systemui.lowlightclock.AmbientLightModeMonitor;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LowLightDockManager$$ExternalSyntheticLambda0 implements NotifInflater.InflationCallback, AmbientLightModeMonitor.Callback {
    public final /* synthetic */ Object f$0;

    public final void onInflationFinished(NotificationEntry notificationEntry, ExpandableNotificationRowController expandableNotificationRowController) {
        PreparationCoordinator.$r8$lambda$OBZgOZcphwYSTtPwW4dGUoKs3OA((PreparationCoordinator) this.f$0, notificationEntry, expandableNotificationRowController);
    }

    public /* synthetic */ LowLightDockManager$$ExternalSyntheticLambda0(Object obj) {
        this.f$0 = obj;
    }
}
