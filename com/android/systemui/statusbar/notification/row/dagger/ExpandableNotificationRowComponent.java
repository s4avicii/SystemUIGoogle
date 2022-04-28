package com.android.systemui.statusbar.notification.row.dagger;

import com.android.systemui.statusbar.NotificationPresenter;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRowController;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;

public interface ExpandableNotificationRowComponent {

    public interface Builder {
        ExpandableNotificationRowComponent build();

        Builder expandableNotificationRow(ExpandableNotificationRow expandableNotificationRow);

        Builder listContainer(NotificationListContainer notificationListContainer);

        Builder notificationEntry(NotificationEntry notificationEntry);

        Builder onExpandClickListener(NotificationPresenter notificationPresenter);
    }

    ExpandableNotificationRowController getExpandableNotificationRowController();
}
