package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpCoordinator.kt */
final class HeadsUpCoordinator$findBestTransferChild$2 extends Lambda implements Function1<NotificationEntry, Boolean> {
    public final /* synthetic */ Function1<String, GroupLocation> $locationLookupByKey;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public HeadsUpCoordinator$findBestTransferChild$2(Function1<? super String, ? extends GroupLocation> function1) {
        super(1);
        this.$locationLookupByKey = function1;
    }

    public final Object invoke(Object obj) {
        boolean z;
        if (this.$locationLookupByKey.invoke(((NotificationEntry) obj).mKey) != GroupLocation.Detached) {
            z = true;
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
