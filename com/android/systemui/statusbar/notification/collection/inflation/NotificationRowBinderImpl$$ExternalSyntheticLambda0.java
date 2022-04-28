package com.android.systemui.statusbar.notification.collection.inflation;

import android.app.Notification;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifBindPipeline;
import com.android.systemui.statusbar.notification.row.NotificationRowContentBinder;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class NotificationRowBinderImpl$$ExternalSyntheticLambda0 implements NotifBindPipeline.BindCallback {
    public final /* synthetic */ ExpandableNotificationRow f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ boolean f$2;
    public final /* synthetic */ NotificationRowContentBinder.InflationCallback f$3;

    public /* synthetic */ NotificationRowBinderImpl$$ExternalSyntheticLambda0(ExpandableNotificationRow expandableNotificationRow, boolean z, boolean z2, NotificationRowContentBinder.InflationCallback inflationCallback) {
        this.f$0 = expandableNotificationRow;
        this.f$1 = z;
        this.f$2 = z2;
        this.f$3 = inflationCallback;
    }

    public final void onBindFinished(NotificationEntry notificationEntry) {
        ExpandableNotificationRow expandableNotificationRow = this.f$0;
        boolean z = this.f$1;
        boolean z2 = this.f$2;
        NotificationRowContentBinder.InflationCallback inflationCallback = this.f$3;
        Objects.requireNonNull(expandableNotificationRow);
        expandableNotificationRow.mUseIncreasedCollapsedHeight = z;
        expandableNotificationRow.mIsLowPriority = z2;
        Objects.requireNonNull(expandableNotificationRow.mPrivateLayout);
        NotificationChildrenContainer notificationChildrenContainer = expandableNotificationRow.mChildrenContainer;
        if (notificationChildrenContainer != null) {
            notificationChildrenContainer.mIsLowPriority = z2;
            if (notificationChildrenContainer.mContainingNotification != null) {
                notificationChildrenContainer.recreateLowPriorityHeader((Notification.Builder) null);
                notificationChildrenContainer.updateHeaderVisibility(false);
            }
            boolean z3 = notificationChildrenContainer.mUserLocked;
            if (z3) {
                notificationChildrenContainer.setUserLocked(z3);
            }
        }
        if (inflationCallback != null) {
            inflationCallback.onAsyncInflationFinished(notificationEntry);
        }
    }
}
