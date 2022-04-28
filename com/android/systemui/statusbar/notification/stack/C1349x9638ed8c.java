package com.android.systemui.statusbar.notification.stack;

import androidx.fragment.R$styleable;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Comparator;
import java.util.Objects;

/* renamed from: com.android.systemui.statusbar.notification.stack.ForegroundServiceSectionController$update$lambda-2$$inlined$sortedBy$1 */
/* compiled from: Comparisons.kt */
public final class C1349x9638ed8c<T> implements Comparator {
    public final int compare(T t, T t2) {
        NotificationEntry notificationEntry = (NotificationEntry) t;
        Objects.requireNonNull(notificationEntry);
        Integer valueOf = Integer.valueOf(notificationEntry.mRanking.getRank());
        NotificationEntry notificationEntry2 = (NotificationEntry) t2;
        Objects.requireNonNull(notificationEntry2);
        return R$styleable.compareValues(valueOf, Integer.valueOf(notificationEntry2.mRanking.getRank()));
    }
}
