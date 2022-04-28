package com.android.systemui.statusbar.notification.row;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationInlineImageCache$$ExternalSyntheticLambda0 implements Predicate {
    public final /* synthetic */ Set f$0;

    public /* synthetic */ NotificationInlineImageCache$$ExternalSyntheticLambda0(HashSet hashSet) {
        this.f$0 = hashSet;
    }

    public final boolean test(Object obj) {
        return !this.f$0.contains(((Map.Entry) obj).getKey());
    }
}
