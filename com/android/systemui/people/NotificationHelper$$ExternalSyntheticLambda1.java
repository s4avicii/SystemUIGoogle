package com.android.systemui.people;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationHelper$$ExternalSyntheticLambda1 implements Predicate {
    public static final /* synthetic */ NotificationHelper$$ExternalSyntheticLambda1 INSTANCE = new NotificationHelper$$ExternalSyntheticLambda1();

    public final boolean test(Object obj) {
        return NotificationHelper.isMissedCallOrHasContent((NotificationEntry) obj);
    }
}
