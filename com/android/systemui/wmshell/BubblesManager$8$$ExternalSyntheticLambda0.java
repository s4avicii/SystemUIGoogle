package com.android.systemui.wmshell;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.wmshell.BubblesManager;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubblesManager$8$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ BubblesManager.C17558 f$0;

    public /* synthetic */ BubblesManager$8$$ExternalSyntheticLambda0(BubblesManager.C17558 r1) {
        this.f$0 = r1;
    }

    public final void accept(Object obj) {
        BubblesManager.C17558 r3 = this.f$0;
        Objects.requireNonNull(r3);
        NotificationEntry activeNotificationUnfiltered = BubblesManager.this.mNotificationEntryManager.getActiveNotificationUnfiltered((String) obj);
        if (activeNotificationUnfiltered != null) {
            BubblesManager bubblesManager = BubblesManager.this;
            bubblesManager.mNotificationEntryManager.performRemoveNotification(activeNotificationUnfiltered.mSbn, bubblesManager.getDismissedByUserStats(activeNotificationUnfiltered, false), 0);
        }
    }
}
