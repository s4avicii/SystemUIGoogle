package com.android.systemui.statusbar.notification.collection.coordinator;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.android.systemui.statusbar.RemoteInputNotificationRebuilder;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.collection.NotifCollection$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;

/* compiled from: RemoteInputCoordinator.kt */
public /* synthetic */ class RemoteInputCoordinator$setRemoteInputController$1 implements SmartReplyController.Callback {
    public final /* synthetic */ RemoteInputCoordinator $tmp0;

    public RemoteInputCoordinator$setRemoteInputController$1(RemoteInputCoordinator remoteInputCoordinator) {
        this.$tmp0 = remoteInputCoordinator;
    }

    public final void onSmartReplySent(NotificationEntry notificationEntry, CharSequence charSequence) {
        RemoteInputCoordinator remoteInputCoordinator = this.$tmp0;
        Objects.requireNonNull(remoteInputCoordinator);
        if (RemoteInputCoordinatorKt.access$getDEBUG()) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("onSmartReplySent(entry=");
            m.append(notificationEntry.mKey);
            m.append(')');
            Log.d("RemoteInputCoordinator", m.toString());
        }
        RemoteInputNotificationRebuilder remoteInputNotificationRebuilder = remoteInputCoordinator.mRebuilder;
        Objects.requireNonNull(remoteInputNotificationRebuilder);
        StatusBarNotification rebuildWithRemoteInputInserted = remoteInputNotificationRebuilder.rebuildWithRemoteInputInserted(notificationEntry, charSequence, true, (String) null, (Uri) null);
        NotifCollection$$ExternalSyntheticLambda0 notifCollection$$ExternalSyntheticLambda0 = remoteInputCoordinator.mNotifUpdater;
        if (notifCollection$$ExternalSyntheticLambda0 == null) {
            notifCollection$$ExternalSyntheticLambda0 = null;
        }
        notifCollection$$ExternalSyntheticLambda0.onInternalNotificationUpdate(rebuildWithRemoteInputInserted, "Adding smart reply spinner for sent");
        remoteInputCoordinator.mRemoteInputActiveExtender.endLifetimeExtensionAfterDelay(notificationEntry.mKey, 500);
    }
}
