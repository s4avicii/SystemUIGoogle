package com.android.systemui.statusbar.notification.collection;

import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShadeListBuilder$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ ShadeListBuilder f$0;
    public final /* synthetic */ List f$1;

    public /* synthetic */ ShadeListBuilder$$ExternalSyntheticLambda2(ShadeListBuilder shadeListBuilder, List list) {
        this.f$0 = shadeListBuilder;
        this.f$1 = list;
    }

    public final boolean test(Object obj) {
        NotifPromoter notifPromoter;
        ShadeListBuilder shadeListBuilder = this.f$0;
        List list = this.f$1;
        NotificationEntry notificationEntry = (NotificationEntry) obj;
        Objects.requireNonNull(shadeListBuilder);
        boolean z = false;
        int i = 0;
        while (true) {
            if (i >= shadeListBuilder.mNotifPromoters.size()) {
                notifPromoter = null;
                break;
            }
            notifPromoter = (NotifPromoter) shadeListBuilder.mNotifPromoters.get(i);
            if (notifPromoter.shouldPromoteToTopLevel(notificationEntry)) {
                break;
            }
            i++;
        }
        Objects.requireNonNull(notificationEntry);
        ListAttachState listAttachState = notificationEntry.mAttachState;
        Objects.requireNonNull(listAttachState);
        listAttachState.promoter = notifPromoter;
        if (notifPromoter != null) {
            z = true;
        }
        if (z) {
            notificationEntry.setParent(GroupEntry.ROOT_ENTRY);
            list.add(notificationEntry);
        }
        return z;
    }
}
