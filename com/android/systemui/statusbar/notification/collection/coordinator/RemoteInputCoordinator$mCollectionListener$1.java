package com.android.systemui.statusbar.notification.collection.coordinator;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.Log;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import java.util.Objects;

/* compiled from: RemoteInputCoordinator.kt */
public final class RemoteInputCoordinator$mCollectionListener$1 implements NotifCollectionListener {
    public final /* synthetic */ RemoteInputCoordinator this$0;

    public RemoteInputCoordinator$mCollectionListener$1(RemoteInputCoordinator remoteInputCoordinator) {
        this.this$0 = remoteInputCoordinator;
    }

    public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
        if (RemoteInputCoordinatorKt.access$getDEBUG()) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("mCollectionListener.onEntryRemoved(entry=");
            m.append(notificationEntry.mKey);
            m.append(')');
            Log.d("RemoteInputCoordinator", m.toString());
        }
        this.this$0.mSmartReplyController.stopSending(notificationEntry);
        if (i == 1 || i == 2) {
            NotificationRemoteInputManager notificationRemoteInputManager = this.this$0.mNotificationRemoteInputManager;
            Objects.requireNonNull(notificationRemoteInputManager);
            if (notificationRemoteInputManager.isRemoteInputActive(notificationEntry)) {
                notificationEntry.mRemoteEditImeVisible = false;
                notificationRemoteInputManager.mRemoteInputController.removeRemoteInput(notificationEntry, (Object) null);
            }
        }
    }

    public final void onEntryUpdated(NotificationEntry notificationEntry, boolean z) {
        if (RemoteInputCoordinatorKt.access$getDEBUG()) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("mCollectionListener.onEntryUpdated(entry=");
            m.append(notificationEntry.mKey);
            m.append(", fromSystem=");
            m.append(z);
            m.append(')');
            Log.d("RemoteInputCoordinator", m.toString());
        }
        if (z) {
            this.this$0.mSmartReplyController.stopSending(notificationEntry);
        }
    }
}
