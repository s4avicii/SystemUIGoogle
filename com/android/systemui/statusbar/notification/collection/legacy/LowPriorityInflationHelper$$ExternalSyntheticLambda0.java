package com.android.systemui.statusbar.notification.collection.legacy;

import android.app.Notification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class LowPriorityInflationHelper$$ExternalSyntheticLambda0 implements NotifBindPipeline.BindCallback {
    public final /* synthetic */ ExpandableNotificationRow f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ LowPriorityInflationHelper$$ExternalSyntheticLambda0(ExpandableNotificationRow expandableNotificationRow, boolean z) {
        this.f$0 = expandableNotificationRow;
        this.f$1 = z;
    }

    public final void onBindFinished(NotificationEntry notificationEntry) {
        ExpandableNotificationRow expandableNotificationRow = this.f$0;
        boolean z = this.f$1;
        Objects.requireNonNull(expandableNotificationRow);
        expandableNotificationRow.mIsLowPriority = z;
        Objects.requireNonNull(expandableNotificationRow.mPrivateLayout);
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.mIsLowPriority = z;
            if (notificationChildrenContainer.mContainingNotification != null) {
                notificationChildrenContainer.recreateLowPriorityHeader((Notification.Builder) null);
                notificationChildrenContainer.updateHeaderVisibility(false);
            }
            boolean z2 = notificationChildrenContainer.mUserLocked;
            if (z2) {
                notificationChildrenContainer.setUserLocked(z2);
            }
        }
    }
}
