package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import java.util.function.BiFunction;

/* compiled from: HeadsUpCoordinator.kt */
public final class HeadsUpCoordinator$mNotifCollectionListener$1$onEntryUpdated$1<T, U, R> implements BiFunction {
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ boolean $isAlerting;
    public final /* synthetic */ boolean $isBinding;
    public final /* synthetic */ boolean $shouldHeadsUpAgain;
    public final /* synthetic */ boolean $shouldHeadsUpEver;

    public HeadsUpCoordinator$mNotifCollectionListener$1$onEntryUpdated$1(NotificationEntry notificationEntry, boolean z, boolean z2, boolean z3, boolean z4) {
        this.$entry = notificationEntry;
        this.$shouldHeadsUpEver = z;
        this.$shouldHeadsUpAgain = z2;
        this.$isAlerting = z3;
        this.$isBinding = z4;
    }

    public final Object apply(Object obj, Object obj2) {
        boolean z;
        String str = (String) obj;
        HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) obj2;
        if (postedEntry == null) {
            postedEntry = null;
        } else {
            boolean z2 = this.$shouldHeadsUpEver;
            boolean z3 = this.$shouldHeadsUpAgain;
            boolean z4 = this.$isAlerting;
            boolean z5 = this.$isBinding;
            boolean z6 = true;
            postedEntry.wasUpdated = true;
            if (postedEntry.shouldHeadsUpEver || z2) {
                z = true;
            } else {
                z = false;
            }
            postedEntry.shouldHeadsUpEver = z;
            if (!postedEntry.shouldHeadsUpAgain && !z3) {
                z6 = false;
            }
            postedEntry.shouldHeadsUpAgain = z6;
            postedEntry.isAlerting = z4;
            postedEntry.isBinding = z5;
        }
        if (postedEntry == null) {
            return new HeadsUpCoordinator.PostedEntry(this.$entry, false, true, this.$shouldHeadsUpEver, this.$shouldHeadsUpAgain, this.$isAlerting, this.$isBinding);
        }
        return postedEntry;
    }
}
