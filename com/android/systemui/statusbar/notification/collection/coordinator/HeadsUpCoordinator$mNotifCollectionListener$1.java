package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.Objects;

/* compiled from: HeadsUpCoordinator.kt */
public final class HeadsUpCoordinator$mNotifCollectionListener$1 implements NotifCollectionListener {
    public final /* synthetic */ HeadsUpCoordinator this$0;

    public HeadsUpCoordinator$mNotifCollectionListener$1(HeadsUpCoordinator headsUpCoordinator) {
        this.this$0 = headsUpCoordinator;
    }

    public final void onEntryAdded(NotificationEntry notificationEntry) {
        this.this$0.mPostedEntries.put(notificationEntry.mKey, new HeadsUpCoordinator.PostedEntry(notificationEntry, true, false, this.this$0.mNotificationInterruptStateProvider.shouldHeadsUp(notificationEntry), true, false, false));
    }

    public final void onEntryCleanUp(NotificationEntry notificationEntry) {
        this.this$0.mHeadsUpViewBinder.abortBindCallback(notificationEntry);
    }

    public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        boolean z;
        this.this$0.mPostedEntries.remove(notificationEntry.mKey);
        HeadsUpCoordinator headsUpCoordinator = this.this$0;
        Objects.requireNonNull(headsUpCoordinator);
        headsUpCoordinator.mEntriesBindingUntil.remove(notificationEntry.mKey);
        headsUpCoordinator.mHeadsUpViewBinder.abortBindCallback(notificationEntry);
        String str = notificationEntry.mKey;
        if (this.this$0.mHeadsUpManager.isAlerting(str)) {
            if (!this.this$0.mRemoteInputManager.isSpinning(str) || NotificationRemoteInputManager.FORCE_REMOTE_INPUT_HISTORY) {
                z = false;
            } else {
                z = true;
            }
            this.this$0.mHeadsUpManager.removeNotification(notificationEntry.mKey, z);
        }
    }

    public final void onEntryUpdated(NotificationEntry notificationEntry) {
        boolean z;
        boolean shouldHeadsUp = this.this$0.mNotificationInterruptStateProvider.shouldHeadsUp(notificationEntry);
        Objects.requireNonNull(this.this$0);
        if (!notificationEntry.interruption || (notificationEntry.mSbn.getNotification().flags & 8) == 0) {
            z = true;
        } else {
            z = false;
        }
        this.this$0.mPostedEntries.compute(notificationEntry.mKey, new HeadsUpCoordinator$mNotifCollectionListener$1$onEntryUpdated$1(notificationEntry, shouldHeadsUp, z, this.this$0.mHeadsUpManager.isAlerting(notificationEntry.mKey), this.this$0.isEntryBinding(notificationEntry)));
    }
}
