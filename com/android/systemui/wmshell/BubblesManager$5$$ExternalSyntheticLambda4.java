package com.android.systemui.wmshell;

import android.util.ArraySet;
import com.android.p012wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda9;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.wmshell.BubblesManager;
import java.util.ArrayList;
import java.util.Objects;
import java.util.function.Consumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubblesManager$5$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ BubblesManager.C17525 f$0;
    public final /* synthetic */ ArraySet f$1;
    public final /* synthetic */ Consumer f$2;

    public /* synthetic */ BubblesManager$5$$ExternalSyntheticLambda4(BubblesManager.C17525 r1, ArraySet arraySet, BubbleController$$ExternalSyntheticLambda9 bubbleController$$ExternalSyntheticLambda9) {
        this.f$0 = r1;
        this.f$1 = arraySet;
        this.f$2 = bubbleController$$ExternalSyntheticLambda9;
    }

    public final void run() {
        BubblesManager.C17525 r0 = this.f$0;
        ArraySet arraySet = this.f$1;
        Consumer consumer = this.f$2;
        Objects.requireNonNull(r0);
        ArrayList arrayList = new ArrayList();
        for (NotificationEntry next : BubblesManager.this.mCommonNotifCollection.getAllNotifs()) {
            NotificationLockscreenUserManager notificationLockscreenUserManager = BubblesManager.this.mNotifUserManager;
            Objects.requireNonNull(next);
            if (notificationLockscreenUserManager.isCurrentProfile(next.mSbn.getUserId()) && arraySet.contains(next.mKey) && BubblesManager.this.mNotificationInterruptStateProvider.shouldBubbleUp(next) && next.isBubble()) {
                arrayList.add(BubblesManager.notifToBubbleEntry(next));
            }
        }
        consumer.accept(arrayList);
    }
}
