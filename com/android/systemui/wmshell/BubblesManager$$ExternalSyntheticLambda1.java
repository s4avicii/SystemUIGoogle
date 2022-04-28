package com.android.systemui.wmshell;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.wmshell.BubblesManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.IntConsumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubblesManager$$ExternalSyntheticLambda1 implements IntConsumer {
    public final /* synthetic */ BubblesManager f$0;
    public final /* synthetic */ List f$1;
    public final /* synthetic */ NotificationEntry f$2;

    public /* synthetic */ BubblesManager$$ExternalSyntheticLambda1(BubblesManager bubblesManager, ArrayList arrayList, NotificationEntry notificationEntry) {
        this.f$0 = bubblesManager;
        this.f$1 = arrayList;
        this.f$2 = notificationEntry;
    }

    public final void accept(int i) {
        BubblesManager bubblesManager = this.f$0;
        List list = this.f$1;
        NotificationEntry notificationEntry = this.f$2;
        Objects.requireNonNull(bubblesManager);
        if (i >= 0) {
            Iterator it = bubblesManager.mCallbacks.iterator();
            while (it.hasNext()) {
                ((BubblesManager.NotifCallback) it.next()).removeNotification((NotificationEntry) list.get(i), bubblesManager.getDismissedByUserStats((NotificationEntry) list.get(i), true), 12);
            }
            return;
        }
        bubblesManager.mNotificationGroupManager.onEntryRemoved(notificationEntry);
    }
}
