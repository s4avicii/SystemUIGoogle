package com.android.systemui.statusbar.notification.collection;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.collection.notifcollection.BindEntryEvent;
import com.android.systemui.statusbar.notification.collection.notifcollection.EntryUpdatedEvent;
import com.android.systemui.util.Assert;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotifCollection$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ NotifCollection f$0;
    public final /* synthetic */ StatusBarNotification f$1;
    public final /* synthetic */ String f$2;
    public final /* synthetic */ String f$3;

    public /* synthetic */ NotifCollection$$ExternalSyntheticLambda3(NotifCollection notifCollection, StatusBarNotification statusBarNotification, String str, String str2) {
        this.f$0 = notifCollection;
        this.f$1 = statusBarNotification;
        this.f$2 = str;
        this.f$3 = str2;
    }

    public final void run() {
        NotifCollection notifCollection = this.f$0;
        StatusBarNotification statusBarNotification = this.f$1;
        String str = this.f$2;
        String str2 = this.f$3;
        Objects.requireNonNull(notifCollection);
        Assert.isMainThread();
        notifCollection.checkForReentrantCall();
        NotificationEntry notificationEntry = (NotificationEntry) notifCollection.mNotificationSet.get(statusBarNotification.getKey());
        if (notificationEntry == null) {
            notifCollection.mLogger.logNotifInternalUpdateFailed(statusBarNotification.getKey(), str, str2);
            return;
        }
        notifCollection.mLogger.logNotifInternalUpdate(statusBarNotification.getKey(), str, str2);
        notificationEntry.setSbn(statusBarNotification);
        notifCollection.mEventQueue.add(new BindEntryEvent(notificationEntry, statusBarNotification));
        notifCollection.mLogger.logNotifUpdated(statusBarNotification.getKey());
        notifCollection.mEventQueue.add(new EntryUpdatedEvent(notificationEntry, false));
        notifCollection.dispatchEventsAndRebuildList();
    }
}
