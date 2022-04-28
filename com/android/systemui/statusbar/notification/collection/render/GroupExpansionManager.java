package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.C1381xbae1b0c0;

public interface GroupExpansionManager extends Dumpable {

    public interface OnGroupExpansionChangeListener {
        void onGroupExpansionChange(ExpandableNotificationRow expandableNotificationRow, boolean z);
    }

    void collapseGroups();

    boolean isGroupExpanded(NotificationEntry notificationEntry);

    void registerGroupExpansionChangeListener(C1381xbae1b0c0 notificationStackScrollLayoutController$$ExternalSyntheticLambda1);

    void setGroupExpanded(NotificationEntry notificationEntry, boolean z);

    boolean toggleGroupExpansion(NotificationEntry notificationEntry);
}
