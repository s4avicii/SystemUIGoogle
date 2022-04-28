package com.android.systemui.statusbar.notification.collection.render;

import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.Coordinator;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.stack.C1381xbae1b0c0;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

public final class GroupExpansionManagerImpl implements GroupExpansionManager, Coordinator {
    public final HashSet mExpandedGroups = new HashSet();
    public final GroupMembershipManager mGroupMembershipManager;
    public final GroupExpansionManagerImpl$$ExternalSyntheticLambda0 mNotifTracker = new GroupExpansionManagerImpl$$ExternalSyntheticLambda0(this);
    public final HashSet mOnGroupChangeListeners = new HashSet();

    public final void attach(NotifPipeline notifPipeline) {
        notifPipeline.addOnBeforeRenderListListener(this.mNotifTracker);
    }

    public final void collapseGroups() {
        Iterator it = new ArrayList(this.mExpandedGroups).iterator();
        while (it.hasNext()) {
            setGroupExpanded((NotificationEntry) it.next(), false);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "NotificationEntryExpansion state:", "  # expanded groups: ");
        m.append(this.mExpandedGroups.size());
        printWriter.println(m.toString());
        Iterator it = this.mExpandedGroups.iterator();
        while (it.hasNext()) {
            NotificationEntry notificationEntry = (NotificationEntry) it.next();
            StringBuilder sb = new StringBuilder();
            sb.append("    summary key of expanded group: ");
            Objects.requireNonNull(notificationEntry);
            sb.append(notificationEntry.mKey);
            printWriter.println(sb.toString());
        }
    }

    public final boolean isGroupExpanded(NotificationEntry notificationEntry) {
        return this.mExpandedGroups.contains(this.mGroupMembershipManager.getGroupSummary(notificationEntry));
    }

    public final void registerGroupExpansionChangeListener(C1381xbae1b0c0 notificationStackScrollLayoutController$$ExternalSyntheticLambda1) {
        this.mOnGroupChangeListeners.add(notificationStackScrollLayoutController$$ExternalSyntheticLambda1);
    }

    public final void setGroupExpanded(NotificationEntry notificationEntry, boolean z) {
        NotificationEntry groupSummary = this.mGroupMembershipManager.getGroupSummary(notificationEntry);
        if (z) {
            this.mExpandedGroups.add(groupSummary);
        } else {
            this.mExpandedGroups.remove(groupSummary);
        }
        Iterator it = this.mOnGroupChangeListeners.iterator();
        while (it.hasNext()) {
            Objects.requireNonNull(notificationEntry);
            ((GroupExpansionManager.OnGroupExpansionChangeListener) it.next()).onGroupExpansionChange(notificationEntry.row, z);
        }
    }

    public GroupExpansionManagerImpl(GroupMembershipManager groupMembershipManager) {
        this.mGroupMembershipManager = groupMembershipManager;
    }

    public final boolean toggleGroupExpansion(NotificationEntry notificationEntry) {
        setGroupExpanded(notificationEntry, !isGroupExpanded(notificationEntry));
        return isGroupExpanded(notificationEntry);
    }
}
