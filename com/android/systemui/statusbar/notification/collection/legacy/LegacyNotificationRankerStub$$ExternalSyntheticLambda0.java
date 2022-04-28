package com.android.systemui.statusbar.notification.collection.legacy;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;
import java.util.function.ToLongFunction;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LegacyNotificationRankerStub$$ExternalSyntheticLambda0 implements ToLongFunction {
    public static final /* synthetic */ LegacyNotificationRankerStub$$ExternalSyntheticLambda0 INSTANCE = new LegacyNotificationRankerStub$$ExternalSyntheticLambda0();

    public final long applyAsLong(Object obj) {
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        Objects.requireNonNull(notificationEntry);
        return notificationEntry.mSbn.getNotification().when;
    }
}
