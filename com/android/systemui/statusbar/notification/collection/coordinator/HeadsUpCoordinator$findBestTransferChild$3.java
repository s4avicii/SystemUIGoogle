package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpCoordinator.kt */
final class HeadsUpCoordinator$findBestTransferChild$3 extends Lambda implements Function1<NotificationEntry, Comparable<?>> {
    public final /* synthetic */ HeadsUpCoordinator this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public HeadsUpCoordinator$findBestTransferChild$3(HeadsUpCoordinator headsUpCoordinator) {
        super(1);
        this.this$0 = headsUpCoordinator;
    }

    public final Object invoke(Object obj) {
        return Boolean.valueOf(!this.this$0.mPostedEntries.containsKey(((NotificationEntry) obj).mKey));
    }
}
