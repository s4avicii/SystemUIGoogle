package com.android.systemui.statusbar;

import android.net.Uri;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.SmartReplyController;
import com.android.systemui.statusbar.notification.InflationException;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationRemoteInputManager$$ExternalSyntheticLambda1 implements SmartReplyController.Callback {
    public final /* synthetic */ NotificationRemoteInputManager f$0;

    public /* synthetic */ NotificationRemoteInputManager$$ExternalSyntheticLambda1(NotificationRemoteInputManager notificationRemoteInputManager) {
        this.f$0 = notificationRemoteInputManager;
    }

    public final void onSmartReplySent(NotificationEntry notificationEntry, CharSequence charSequence) {
        NotificationRemoteInputManager notificationRemoteInputManager = this.f$0;
        Objects.requireNonNull(notificationRemoteInputManager);
        RemoteInputNotificationRebuilder remoteInputNotificationRebuilder = notificationRemoteInputManager.mRebuilder;
        Objects.requireNonNull(remoteInputNotificationRebuilder);
        StatusBarNotification rebuildWithRemoteInputInserted = remoteInputNotificationRebuilder.rebuildWithRemoteInputInserted(notificationEntry, charSequence, true, (String) null, (Uri) null);
        NotificationEntryManager notificationEntryManager = notificationRemoteInputManager.mEntryManager;
        Objects.requireNonNull(notificationEntryManager);
        try {
            notificationEntryManager.updateNotificationInternal(rebuildWithRemoteInputInserted, (NotificationListenerService.RankingMap) null);
        } catch (InflationException e) {
            notificationEntryManager.handleInflationException(rebuildWithRemoteInputInserted, e);
        }
    }
}
