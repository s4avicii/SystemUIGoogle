package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpCoordinator.kt */
final class HeadsUpCoordinator$findBestTransferChild$1 extends Lambda implements Function1<NotificationEntry, Boolean> {
    public static final HeadsUpCoordinator$findBestTransferChild$1 INSTANCE = new HeadsUpCoordinator$findBestTransferChild$1();

    public HeadsUpCoordinator$findBestTransferChild$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Boolean.valueOf(!((NotificationEntry) obj).mSbn.getNotification().isGroupSummary());
    }
}
