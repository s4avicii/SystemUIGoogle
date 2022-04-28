package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$onBeforeFinalizeFilter$logicalMembersByGroup$1 */
/* compiled from: HeadsUpCoordinator.kt */
final class C1260xe7ca0177 extends Lambda implements Function1<NotificationEntry, Boolean> {
    public final /* synthetic */ Map<String, List<HeadsUpCoordinator.PostedEntry>> $postedEntriesByGroup;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1260xe7ca0177(LinkedHashMap linkedHashMap) {
        super(1);
        this.$postedEntriesByGroup = linkedHashMap;
    }

    public final Object invoke(Object obj) {
        return Boolean.valueOf(this.$postedEntriesByGroup.containsKey(((NotificationEntry) obj).mSbn.getGroupKey()));
    }
}
