package com.android.systemui.statusbar.notification.collection;

import java.util.Objects;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: NotificationRankingManager.kt */
public /* synthetic */ class NotificationRankingManager$filterAndSortLocked$filtered$1 extends FunctionReferenceImpl implements Function1<NotificationEntry, Boolean> {
    public NotificationRankingManager$filterAndSortLocked$filtered$1(Object obj) {
        super(1, obj, NotificationRankingManager.class, "filter", "filter(Lcom/android/systemui/statusbar/notification/collection/NotificationEntry;)Z", 0);
    }

    public final Object invoke(Object obj) {
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        NotificationRankingManager notificationRankingManager = (NotificationRankingManager) this.receiver;
        Objects.requireNonNull(notificationRankingManager);
        boolean shouldFilterOut = notificationRankingManager.notifFilter.shouldFilterOut(notificationEntry);
        if (shouldFilterOut) {
            notificationEntry.initializationTime = -1;
        }
        return Boolean.valueOf(shouldFilterOut);
    }
}
