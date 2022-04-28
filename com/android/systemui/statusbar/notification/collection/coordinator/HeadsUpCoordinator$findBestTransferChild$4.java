package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpCoordinator.kt */
final class HeadsUpCoordinator$findBestTransferChild$4 extends Lambda implements Function1<NotificationEntry, Comparable<?>> {
    public static final HeadsUpCoordinator$findBestTransferChild$4 INSTANCE = new HeadsUpCoordinator$findBestTransferChild$4();

    public HeadsUpCoordinator$findBestTransferChild$4() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Long.valueOf(-((NotificationEntry) obj).mSbn.getNotification().when);
    }
}
