package com.android.systemui.wmshell;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.wmshell.BubblesManager;
import java.util.Iterator;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubblesManager$5$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ BubblesManager.C17525 f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ int f$2 = 2;

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda6(BubblesManager.C17525 r1, String str) {
        this.f$0 = r1;
        this.f$1 = str;
    }

    public final void run() {
        BubblesManager.C17525 r0 = this.f$0;
        String str = this.f$1;
        int i = this.f$2;
        Objects.requireNonNull(r0);
        NotificationEntry entry = BubblesManager.this.mCommonNotifCollection.getEntry(str);
        if (entry != null) {
            Iterator it = BubblesManager.this.mCallbacks.iterator();
            while (it.hasNext()) {
                ((BubblesManager.NotifCallback) it.next()).removeNotification(entry, BubblesManager.this.getDismissedByUserStats(entry, true), i);
            }
        }
    }
}
