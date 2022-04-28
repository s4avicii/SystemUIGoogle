package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpCoordinator.kt */
final class HeadsUpCoordinator$onBeforeFinalizeFilter$groupLocationsByKey$2 extends Lambda implements Function0<Map<String, ? extends GroupLocation>> {
    public final /* synthetic */ List<ListEntry> $list;
    public final /* synthetic */ HeadsUpCoordinator this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public HeadsUpCoordinator$onBeforeFinalizeFilter$groupLocationsByKey$2(HeadsUpCoordinator headsUpCoordinator, List<? extends ListEntry> list) {
        super(0);
        this.this$0 = headsUpCoordinator;
        this.$list = list;
    }

    public final Object invoke() {
        HeadsUpCoordinator headsUpCoordinator = this.this$0;
        List<ListEntry> list = this.$list;
        Objects.requireNonNull(headsUpCoordinator);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (ListEntry listEntry : list) {
            if (listEntry instanceof NotificationEntry) {
                NotificationEntry notificationEntry = (NotificationEntry) listEntry;
                Objects.requireNonNull(notificationEntry);
                linkedHashMap.put(notificationEntry.mKey, GroupLocation.Isolated);
            } else if (listEntry instanceof GroupEntry) {
                GroupEntry groupEntry = (GroupEntry) listEntry;
                Objects.requireNonNull(groupEntry);
                NotificationEntry notificationEntry2 = groupEntry.mSummary;
                if (notificationEntry2 != null) {
                    linkedHashMap.put(notificationEntry2.mKey, GroupLocation.Summary);
                }
                for (NotificationEntry notificationEntry3 : groupEntry.mUnmodifiableChildren) {
                    Objects.requireNonNull(notificationEntry3);
                    linkedHashMap.put(notificationEntry3.mKey, GroupLocation.Child);
                }
            } else {
                throw new IllegalStateException(Intrinsics.stringPlus("unhandled type ", listEntry).toString());
            }
        }
        return linkedHashMap;
    }
}
