package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeTransformGroupsListener;
import java.util.Objects;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* compiled from: HeadsUpCoordinator.kt */
public /* synthetic */ class HeadsUpCoordinator$attach$1 implements OnBeforeTransformGroupsListener {
    public final /* synthetic */ HeadsUpCoordinator $tmp0;

    public HeadsUpCoordinator$attach$1(HeadsUpCoordinator headsUpCoordinator) {
        this.$tmp0 = headsUpCoordinator;
    }

    public final void onBeforeTransformGroups() {
        HeadsUpCoordinator headsUpCoordinator = this.$tmp0;
        Objects.requireNonNull(headsUpCoordinator);
        headsUpCoordinator.mNow = headsUpCoordinator.mSystemClock.currentTimeMillis();
        if (!headsUpCoordinator.mPostedEntries.isEmpty()) {
            for (T t : CollectionsKt___CollectionsKt.toList(headsUpCoordinator.mPostedEntries.values())) {
                Objects.requireNonNull(t);
                NotificationEntry notificationEntry = t.entry;
                Objects.requireNonNull(notificationEntry);
                if (!notificationEntry.mSbn.isGroup()) {
                    headsUpCoordinator.handlePostedEntry(t, "non-group");
                    headsUpCoordinator.mPostedEntries.remove(t.key);
                }
            }
        }
    }
}
