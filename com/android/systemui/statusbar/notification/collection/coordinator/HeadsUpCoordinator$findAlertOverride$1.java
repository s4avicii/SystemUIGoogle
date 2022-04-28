package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import java.util.Objects;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpCoordinator.kt */
final class HeadsUpCoordinator$findAlertOverride$1 extends Lambda implements Function1<HeadsUpCoordinator.PostedEntry, Boolean> {
    public static final HeadsUpCoordinator$findAlertOverride$1 INSTANCE = new HeadsUpCoordinator$findAlertOverride$1();

    public HeadsUpCoordinator$findAlertOverride$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        NotificationEntry notificationEntry = ((HeadsUpCoordinator.PostedEntry) obj).entry;
        Objects.requireNonNull(notificationEntry);
        return Boolean.valueOf(!notificationEntry.mSbn.getNotification().isGroupSummary());
    }
}
