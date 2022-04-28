package com.android.systemui.statusbar.notification.collection.coordinator;

import androidx.fragment.R$styleable;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import java.util.Comparator;
import java.util.Objects;

/* compiled from: Comparisons.kt */
public final class HeadsUpCoordinator$findAlertOverride$$inlined$sortedBy$1<T> implements Comparator {
    public final int compare(T t, T t2) {
        HeadsUpCoordinator.PostedEntry postedEntry = (HeadsUpCoordinator.PostedEntry) t;
        Objects.requireNonNull(postedEntry);
        NotificationEntry notificationEntry = postedEntry.entry;
        Objects.requireNonNull(notificationEntry);
        Long valueOf = Long.valueOf(-notificationEntry.mSbn.getNotification().when);
        HeadsUpCoordinator.PostedEntry postedEntry2 = (HeadsUpCoordinator.PostedEntry) t2;
        Objects.requireNonNull(postedEntry2);
        NotificationEntry notificationEntry2 = postedEntry2.entry;
        Objects.requireNonNull(notificationEntry2);
        return R$styleable.compareValues(valueOf, Long.valueOf(-notificationEntry2.mSbn.getNotification().when));
    }
}
