package com.android.systemui.statusbar.notification.row;

import android.view.View;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationContentInflater$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ NotificationContentInflater f$0;
    public final /* synthetic */ ExpandableNotificationRow f$1;
    public final /* synthetic */ NotificationEntry f$2;

    public /* synthetic */ NotificationContentInflater$$ExternalSyntheticLambda1(NotificationContentInflater notificationContentInflater, ExpandableNotificationRow expandableNotificationRow, NotificationEntry notificationEntry) {
        this.f$0 = notificationContentInflater;
        this.f$1 = expandableNotificationRow;
        this.f$2 = notificationEntry;
    }

    public final void run() {
        NotificationContentInflater notificationContentInflater = this.f$0;
        ExpandableNotificationRow expandableNotificationRow = this.f$1;
        NotificationEntry notificationEntry = this.f$2;
        Objects.requireNonNull(notificationContentInflater);
        Objects.requireNonNull(expandableNotificationRow);
        expandableNotificationRow.mPrivateLayout.setHeadsUpChild((View) null);
        notificationContentInflater.mRemoteViewCache.removeCachedView(notificationEntry, 4);
        NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
        Objects.requireNonNull(notificationContentView);
        notificationContentView.mHeadsUpInflatedSmartReplies = null;
        notificationContentView.mHeadsUpSmartReplyView = null;
    }
}
