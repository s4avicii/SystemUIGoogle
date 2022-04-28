package com.android.systemui.statusbar.notification.row;

import android.app.Notification;
import android.view.View;
import android.view.ViewStub;
import com.android.systemui.statusbar.NotificationGroupingUtil;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ExpandableNotificationRow$$ExternalSyntheticLambda1 implements ViewStub.OnInflateListener {
    public final /* synthetic */ ExpandableNotificationRow f$0;

    public /* synthetic */ ExpandableNotificationRow$$ExternalSyntheticLambda1(ExpandableNotificationRow expandableNotificationRow) {
        this.f$0 = expandableNotificationRow;
    }

    public final void onInflate(ViewStub viewStub, View view) {
        ExpandableNotificationRow expandableNotificationRow = this.f$0;
        ExpandableNotificationRow.C13092 r1 = ExpandableNotificationRow.TRANSLATE_CONTENT;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationChildrenContainer notificationChildrenContainer = (NotificationChildrenContainer) view;
        expandableNotificationRow.mChildrenContainer = notificationChildrenContainer;
        boolean z = expandableNotificationRow.mIsLowPriority;
        Objects.requireNonNull(notificationChildrenContainer);
        notificationChildrenContainer.mIsLowPriority = z;
        if (notificationChildrenContainer.mContainingNotification != null) {
            notificationChildrenContainer.recreateLowPriorityHeader((Notification.Builder) null);
            notificationChildrenContainer.updateHeaderVisibility(false);
        }
        boolean z2 = notificationChildrenContainer.mUserLocked;
        if (z2) {
            notificationChildrenContainer.setUserLocked(z2);
        }
        NotificationChildrenContainer notificationChildrenContainer2 = expandableNotificationRow.mChildrenContainer;
        Objects.requireNonNull(notificationChildrenContainer2);
        notificationChildrenContainer2.mContainingNotification = expandableNotificationRow;
        notificationChildrenContainer2.mGroupingUtil = new NotificationGroupingUtil(expandableNotificationRow);
        expandableNotificationRow.mChildrenContainer.onNotificationUpdated();
        expandableNotificationRow.mTranslateableViews.add(expandableNotificationRow.mChildrenContainer);
    }
}
