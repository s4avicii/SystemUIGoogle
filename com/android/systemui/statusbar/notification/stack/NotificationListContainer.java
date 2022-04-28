package com.android.systemui.statusbar.notification.stack;

import android.view.View;
import android.view.ViewGroup;
import com.android.systemui.statusbar.notification.ExpandAnimationParameters;
import com.android.systemui.statusbar.notification.VisibilityLocationProvider;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.logging.NotificationLogger;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableView;

public interface NotificationListContainer extends ExpandableView.OnHeightChangedListener, VisibilityLocationProvider {
    void addContainerView(View view);

    void addContainerViewAt(View view, int i);

    void applyExpandAnimationParams(ExpandAnimationParameters expandAnimationParameters) {
    }

    void bindRow(ExpandableNotificationRow expandableNotificationRow) {
    }

    void changeViewPosition(ExpandableView expandableView, int i);

    void cleanUpViewStateForEntry(NotificationEntry notificationEntry);

    boolean containsView(View view) {
        return true;
    }

    void generateAddAnimation(ExpandableNotificationRow expandableNotificationRow, boolean z);

    void generateChildOrderChangedEvent();

    View getContainerChildAt(int i);

    int getContainerChildCount();

    NotificationSwipeHelper getSwipeActionHelper();

    int getTopClippingStartLocation() {
        return 0;
    }

    NotificationStackScrollLayout getViewParentForNotification();

    void notifyGroupChildAdded(ExpandableNotificationRow expandableNotificationRow);

    void notifyGroupChildRemoved(ExpandableNotificationRow expandableNotificationRow, ViewGroup viewGroup);

    void removeContainerView(View view);

    void resetExposedMenuView();

    void setChildLocationsChangedListener(NotificationLogger.C12971 r1);

    void setChildTransferInProgress(boolean z);

    void setExpandingNotification(ExpandableNotificationRow expandableNotificationRow) {
    }
}
