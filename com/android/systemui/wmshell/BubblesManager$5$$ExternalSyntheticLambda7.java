package com.android.systemui.wmshell;

import com.android.p012wm.shell.bubbles.BubbleEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.wmshell.BubblesManager;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubblesManager$5$$ExternalSyntheticLambda7 implements Runnable {
    public final /* synthetic */ BubblesManager.C17525 f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ Consumer f$2;

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda7(BubblesManager.C17525 r1, String str, Consumer consumer) {
        this.f$0 = r1;
        this.f$1 = str;
        this.f$2 = consumer;
    }

    public final void run() {
        BubbleEntry bubbleEntry;
        BubblesManager.C17525 r0 = this.f$0;
        String str = this.f$1;
        Consumer consumer = this.f$2;
        Objects.requireNonNull(r0);
        NotificationEntry entry = BubblesManager.this.mCommonNotifCollection.getEntry(str);
        if (entry == null) {
            bubbleEntry = null;
        } else {
            bubbleEntry = BubblesManager.notifToBubbleEntry(entry);
        }
        consumer.accept(bubbleEntry);
    }
}
