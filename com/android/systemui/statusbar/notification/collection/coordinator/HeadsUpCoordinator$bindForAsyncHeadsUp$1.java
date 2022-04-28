package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import java.util.Objects;

/* compiled from: HeadsUpCoordinator.kt */
public /* synthetic */ class HeadsUpCoordinator$bindForAsyncHeadsUp$1 implements NotifBindPipeline.BindCallback {
    public final /* synthetic */ HeadsUpCoordinator $tmp0;

    public HeadsUpCoordinator$bindForAsyncHeadsUp$1(HeadsUpCoordinator headsUpCoordinator) {
        this.$tmp0 = headsUpCoordinator;
    }

    public final void onBindFinished(NotificationEntry notificationEntry) {
        HeadsUpCoordinator headsUpCoordinator = this.$tmp0;
        Objects.requireNonNull(headsUpCoordinator);
        headsUpCoordinator.mHeadsUpManager.showNotification(notificationEntry);
        headsUpCoordinator.mEntriesBindingUntil.remove(notificationEntry.mKey);
    }
}
