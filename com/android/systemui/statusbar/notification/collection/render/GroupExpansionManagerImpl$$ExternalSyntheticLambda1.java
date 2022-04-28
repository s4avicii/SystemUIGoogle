package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class GroupExpansionManagerImpl$$ExternalSyntheticLambda1 implements Predicate {
    public final /* synthetic */ Set f$0;

    public /* synthetic */ GroupExpansionManagerImpl$$ExternalSyntheticLambda1(HashSet hashSet) {
        this.f$0 = hashSet;
    }

    public final boolean test(Object obj) {
        return !this.f$0.contains((NotificationEntry) obj);
    }
}
