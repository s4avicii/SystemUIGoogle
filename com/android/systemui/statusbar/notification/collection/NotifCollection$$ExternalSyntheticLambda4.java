package com.android.systemui.statusbar.notification.collection;

import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda4 implements Predicate {
    public final /* synthetic */ String f$0;

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda4(String str) {
        this.f$0 = str;
    }

    public final boolean test(Object obj) {
        String str = this.f$0;
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        Objects.requireNonNull(notificationEntry);
        return Objects.equals(notificationEntry.mSbn.getGroup(), str);
    }
}
