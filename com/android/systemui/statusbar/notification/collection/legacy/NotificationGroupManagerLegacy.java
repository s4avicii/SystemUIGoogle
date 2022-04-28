package com.android.systemui.statusbar.notification.collection.legacy;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.service.notification.StatusBarNotification;
import android.util.ArraySet;
import android.util.Log;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.bubbles.Bubbles;
import com.android.systemui.Dumpable;
import com.android.systemui.R$array;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.people.widget.PeopleSpaceWidgetManager$$ExternalSyntheticLambda3;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManager;
import com.android.systemui.statusbar.notification.collection.render.GroupMembershipManager;
import com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.C1381xbae1b0c0;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import dagger.Lazy;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TreeSet;
import java.util.function.Function;

public final class NotificationGroupManagerLegacy implements OnHeadsUpChangedListener, StatusBarStateController.StateListener, GroupMembershipManager, GroupExpansionManager, Dumpable {
    public final Optional<Bubbles> mBubblesOptional;
    public final GroupEventDispatcher mEventDispatcher;
    public final ArraySet<GroupExpansionManager.OnGroupExpansionChangeListener> mExpansionChangeListeners = new ArraySet<>();
    public final HashMap<String, NotificationGroup> mGroupMap;
    public HeadsUpManager mHeadsUpManager;
    public boolean mIsUpdatingUnchangedGroup;
    public HashMap<String, StatusBarNotification> mIsolatedEntries;
    public final Lazy<PeopleNotificationIdentifier> mPeopleNotificationIdentifier;

    public static class GroupEventDispatcher {
        public int mBufferScopeDepth = 0;
        public boolean mDidGroupsChange = false;
        public final ArraySet<OnGroupChangeListener> mGroupChangeListeners = new ArraySet<>();
        public final Function<String, NotificationGroup> mGroupMapGetter;
        public final HashMap<String, NotificationEntry> mOldAlertOverrideByGroup = new HashMap<>();
        public final HashMap<String, Boolean> mOldSuppressedByGroup = new HashMap<>();

        public final void closeBufferScope() {
            boolean z;
            NotificationEntry notificationEntry;
            int i = this.mBufferScopeDepth - 1;
            this.mBufferScopeDepth = i;
            if (i > 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                for (Map.Entry next : this.mOldSuppressedByGroup.entrySet()) {
                    NotificationGroup apply = this.mGroupMapGetter.apply((String) next.getKey());
                    if (!(apply == null || apply.suppressed == ((Boolean) next.getValue()).booleanValue())) {
                        notifySuppressedChanged(apply);
                    }
                }
                this.mOldSuppressedByGroup.clear();
                for (Map.Entry next2 : this.mOldAlertOverrideByGroup.entrySet()) {
                    NotificationGroup apply2 = this.mGroupMapGetter.apply((String) next2.getKey());
                    if (!(apply2 == null || apply2.alertOverride == (notificationEntry = (NotificationEntry) next2.getValue()))) {
                        notifyAlertOverrideChanged(apply2, notificationEntry);
                    }
                }
                this.mOldAlertOverrideByGroup.clear();
                if (this.mDidGroupsChange) {
                    notifyGroupsChanged();
                    this.mDidGroupsChange = false;
                }
            }
        }

        public final void notifyAlertOverrideChanged(NotificationGroup notificationGroup, NotificationEntry notificationEntry) {
            boolean z;
            if (this.mBufferScopeDepth > 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                Iterator<OnGroupChangeListener> it = this.mGroupChangeListeners.iterator();
                while (it.hasNext()) {
                    NotificationEntry notificationEntry2 = notificationGroup.alertOverride;
                    it.next().onGroupAlertOverrideChanged(notificationGroup, notificationEntry);
                }
            } else if (!this.mOldAlertOverrideByGroup.containsKey(notificationGroup.groupKey)) {
                this.mOldAlertOverrideByGroup.put(notificationGroup.groupKey, notificationEntry);
            }
        }

        public final void notifyGroupsChanged() {
            boolean z;
            if (this.mBufferScopeDepth > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                this.mDidGroupsChange = true;
                return;
            }
            Iterator<OnGroupChangeListener> it = this.mGroupChangeListeners.iterator();
            while (it.hasNext()) {
                it.next().onGroupsChanged();
            }
        }

        public final void notifySuppressedChanged(NotificationGroup notificationGroup) {
            boolean z;
            if (this.mBufferScopeDepth > 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                this.mOldSuppressedByGroup.putIfAbsent(notificationGroup.groupKey, Boolean.valueOf(!notificationGroup.suppressed));
                return;
            }
            Iterator<OnGroupChangeListener> it = this.mGroupChangeListeners.iterator();
            while (it.hasNext()) {
                it.next().onGroupSuppressionChanged(notificationGroup, notificationGroup.suppressed);
            }
        }

        public GroupEventDispatcher(PeopleSpaceWidgetManager$$ExternalSyntheticLambda3 peopleSpaceWidgetManager$$ExternalSyntheticLambda3) {
            this.mGroupMapGetter = peopleSpaceWidgetManager$$ExternalSyntheticLambda3;
        }
    }

    public static class NotificationGroup {
        public NotificationEntry alertOverride;
        public final HashMap<String, NotificationEntry> children = new HashMap<>();
        public boolean expanded;
        public final String groupKey;
        public final TreeSet<PostRecord> postBatchHistory = new TreeSet<>();
        public NotificationEntry summary;
        public boolean suppressed;

        public static void appendEntry(StringBuilder sb, NotificationEntry notificationEntry) {
            Object obj;
            Throwable th;
            sb.append("\n      ");
            if (notificationEntry != null) {
                obj = notificationEntry.mSbn;
            } else {
                obj = "null";
            }
            sb.append(obj);
            if (notificationEntry != null && (th = notificationEntry.mDebugThrowable) != null) {
                sb.append(Log.getStackTraceString(th));
            }
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("    groupKey: ");
            m.append(this.groupKey);
            m.append("\n    summary:");
            appendEntry(m, this.summary);
            m.append("\n    children size: ");
            m.append(this.children.size());
            for (NotificationEntry appendEntry : this.children.values()) {
                appendEntry(m, appendEntry);
            }
            m.append("\n    alertOverride:");
            appendEntry(m, this.alertOverride);
            m.append("\n    summary suppressed: ");
            m.append(this.suppressed);
            return m.toString();
        }

        public NotificationGroup(String str) {
            this.groupKey = str;
        }
    }

    public interface OnGroupChangeListener {
        void onGroupAlertOverrideChanged(NotificationGroup notificationGroup, NotificationEntry notificationEntry) {
        }

        void onGroupCreated(NotificationGroup notificationGroup, String str) {
        }

        void onGroupRemoved(String str) {
        }

        void onGroupSuppressionChanged(NotificationGroup notificationGroup, boolean z) {
        }

        void onGroupsChanged() {
        }
    }

    public static class PostRecord implements Comparable<PostRecord> {
        public final String key;
        public final long postTime;

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || PostRecord.class != obj.getClass()) {
                return false;
            }
            PostRecord postRecord = (PostRecord) obj;
            return this.postTime == postRecord.postTime && this.key.equals(postRecord.key);
        }

        public final int hashCode() {
            return Objects.hash(new Object[]{Long.valueOf(this.postTime), this.key});
        }

        public final int compareTo(Object obj) {
            PostRecord postRecord = (PostRecord) obj;
            int compare = Long.compare(this.postTime, postRecord.postTime);
            if (compare == 0) {
                return String.CASE_INSENSITIVE_ORDER.compare(this.key, postRecord.key);
            }
            return compare;
        }

        public PostRecord(NotificationEntry notificationEntry) {
            Objects.requireNonNull(notificationEntry);
            this.postTime = notificationEntry.mSbn.getPostTime();
            this.key = notificationEntry.mKey;
        }
    }

    public final void onEntryRemovedInternal(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
        onEntryRemovedInternal(notificationEntry, statusBarNotification.getGroupKey(), statusBarNotification.isGroup(), statusBarNotification.getNotification().isGroupSummary());
    }

    public final void onStateChanged(int i) {
        if (i == 1) {
            collapseGroups();
        }
    }

    public final void setGroupExpanded(NotificationEntry notificationEntry, boolean z) {
        HashMap<String, NotificationGroup> hashMap = this.mGroupMap;
        Objects.requireNonNull(notificationEntry);
        NotificationGroup notificationGroup = hashMap.get(getGroupKey(notificationEntry.mSbn));
        if (notificationGroup != null) {
            setGroupExpanded(notificationGroup, z);
        }
    }

    public final void collapseGroups() {
        ArrayList arrayList = new ArrayList(this.mGroupMap.values());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            NotificationGroup notificationGroup = (NotificationGroup) arrayList.get(i);
            if (notificationGroup.expanded) {
                setGroupExpanded(notificationGroup, false);
            }
            updateSuppression(notificationGroup);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "GroupManagerLegacy state:", "  number of groups: ");
        m.append(this.mGroupMap.size());
        printWriter.println(m.toString());
        for (Map.Entry next : this.mGroupMap.entrySet()) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("\n    key: ");
            m2.append(R$array.logKey((String) next.getKey()));
            printWriter.println(m2.toString());
            printWriter.println(next.getValue());
        }
        StringBuilder m3 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("\n    isolated entries: ");
        m3.append(this.mIsolatedEntries.size());
        printWriter.println(m3.toString());
        for (Map.Entry next2 : this.mIsolatedEntries.entrySet()) {
            printWriter.print("      ");
            printWriter.print(R$array.logKey((String) next2.getKey()));
            printWriter.print(", ");
            printWriter.println(next2.getValue());
        }
    }

    public final ArrayList<NotificationEntry> getLogicalChildren(StatusBarNotification statusBarNotification) {
        NotificationGroup notificationGroup = this.mGroupMap.get(statusBarNotification.getGroupKey());
        if (notificationGroup == null) {
            return null;
        }
        ArrayList<NotificationEntry> arrayList = new ArrayList<>(notificationGroup.children.values());
        for (StatusBarNotification next : this.mIsolatedEntries.values()) {
            if (next.getGroupKey().equals(statusBarNotification.getGroupKey())) {
                arrayList.add(this.mGroupMap.get(next.getKey()).summary);
            }
        }
        return arrayList;
    }

    public final int getNumberOfIsolatedChildren(String str) {
        int i = 0;
        for (StatusBarNotification next : this.mIsolatedEntries.values()) {
            if (next.getGroupKey().equals(str) && isIsolated(next.getKey())) {
                i++;
            }
        }
        return i;
    }

    public final boolean isGroupExpanded(NotificationEntry notificationEntry) {
        HashMap<String, NotificationGroup> hashMap = this.mGroupMap;
        Objects.requireNonNull(notificationEntry);
        NotificationGroup notificationGroup = hashMap.get(getGroupKey(notificationEntry.mSbn));
        if (notificationGroup == null) {
            return false;
        }
        return notificationGroup.expanded;
    }

    public final boolean isIsolated(String str) {
        return this.mIsolatedEntries.containsKey(str);
    }

    public final void onEntryRemoved(NotificationEntry notificationEntry) {
        GroupEventDispatcher groupEventDispatcher = this.mEventDispatcher;
        Objects.requireNonNull(groupEventDispatcher);
        groupEventDispatcher.mBufferScopeDepth++;
        Objects.requireNonNull(notificationEntry);
        onEntryRemovedInternal(notificationEntry, notificationEntry.mSbn);
        StatusBarNotification remove = this.mIsolatedEntries.remove(notificationEntry.mKey);
        if (remove != null) {
            updateSuppression(this.mGroupMap.get(remove.getGroupKey()));
        }
        this.mEventDispatcher.closeBufferScope();
    }

    public final void onEntryUpdated(NotificationEntry notificationEntry, String str, boolean z, boolean z2) {
        boolean z3;
        String groupKey = notificationEntry.mSbn.getGroupKey();
        boolean z4 = true;
        boolean z5 = !str.equals(groupKey);
        if (!isIsolated(notificationEntry.mKey) && z && !z2) {
            z3 = true;
        } else {
            z3 = false;
        }
        boolean isGroupChild = isGroupChild(notificationEntry.mSbn);
        GroupEventDispatcher groupEventDispatcher = this.mEventDispatcher;
        Objects.requireNonNull(groupEventDispatcher);
        groupEventDispatcher.mBufferScopeDepth++;
        if (z5 || z3 != isGroupChild) {
            z4 = false;
        }
        this.mIsUpdatingUnchangedGroup = z4;
        HashMap<String, NotificationGroup> hashMap = this.mGroupMap;
        String str2 = notificationEntry.mKey;
        if (!isIsolated(str2)) {
            str2 = str;
        }
        if (hashMap.get(str2) != null) {
            onEntryRemovedInternal(notificationEntry, str, z, z2);
        }
        onEntryAddedInternal(notificationEntry);
        this.mIsUpdatingUnchangedGroup = false;
        if (isIsolated(notificationEntry.mSbn.getKey())) {
            this.mIsolatedEntries.put(notificationEntry.mKey, notificationEntry.mSbn);
            if (z5) {
                updateSuppression(this.mGroupMap.get(str));
            }
            updateSuppression(this.mGroupMap.get(groupKey));
        } else if (!z3 && isGroupChild) {
            updateIsolation(notificationEntry);
        }
        this.mEventDispatcher.closeBufferScope();
    }

    public final void registerGroupExpansionChangeListener(C1381xbae1b0c0 notificationStackScrollLayoutController$$ExternalSyntheticLambda1) {
        this.mExpansionChangeListeners.add(notificationStackScrollLayoutController$$ExternalSyntheticLambda1);
    }

    public final boolean toggleGroupExpansion(NotificationEntry notificationEntry) {
        HashMap<String, NotificationGroup> hashMap = this.mGroupMap;
        Objects.requireNonNull(notificationEntry);
        NotificationGroup notificationGroup = hashMap.get(getGroupKey(notificationEntry.mSbn));
        if (notificationGroup == null) {
            return false;
        }
        setGroupExpanded(notificationGroup, !notificationGroup.expanded);
        return notificationGroup.expanded;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x008e, code lost:
        if (r1 == false) goto L_0x0091;
     */
    /* JADX WARNING: Removed duplicated region for block: B:32:0x008b  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0094  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00be  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateIsolation(com.android.systemui.statusbar.notification.collection.NotificationEntry r7) {
        /*
            r6 = this;
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$GroupEventDispatcher r0 = r6.mEventDispatcher
            java.util.Objects.requireNonNull(r0)
            int r1 = r0.mBufferScopeDepth
            r2 = 1
            int r1 = r1 + r2
            r0.mBufferScopeDepth = r1
            java.util.Objects.requireNonNull(r7)
            android.service.notification.StatusBarNotification r0 = r7.mSbn
            java.lang.String r0 = r0.getKey()
            boolean r0 = r6.isIsolated(r0)
            android.service.notification.StatusBarNotification r1 = r7.mSbn
            boolean r3 = r1.isGroup()
            r4 = 0
            if (r3 == 0) goto L_0x0091
            android.app.Notification r3 = r1.getNotification()
            boolean r3 = r3.isGroupSummary()
            if (r3 == 0) goto L_0x002d
            goto L_0x0091
        L_0x002d:
            dagger.Lazy<com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier> r3 = r6.mPeopleNotificationIdentifier
            java.lang.Object r3 = r3.get()
            com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier r3 = (com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier) r3
            int r3 = r3.getPeopleNotificationType(r7)
            r5 = 3
            if (r3 != r5) goto L_0x003e
            r3 = r2
            goto L_0x003f
        L_0x003e:
            r3 = r4
        L_0x003f:
            if (r3 == 0) goto L_0x0042
            goto L_0x0092
        L_0x0042:
            com.android.systemui.statusbar.policy.HeadsUpManager r3 = r6.mHeadsUpManager
            if (r3 == 0) goto L_0x004f
            java.lang.String r5 = r7.mKey
            boolean r3 = r3.isAlerting(r5)
            if (r3 != 0) goto L_0x004f
            goto L_0x0091
        L_0x004f:
            java.util.HashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$NotificationGroup> r3 = r6.mGroupMap
            java.lang.String r5 = r1.getGroupKey()
            java.lang.Object r3 = r3.get(r5)
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$NotificationGroup r3 = (com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy.NotificationGroup) r3
            android.app.Notification r1 = r1.getNotification()
            android.app.PendingIntent r1 = r1.fullScreenIntent
            if (r1 != 0) goto L_0x0092
            if (r3 == 0) goto L_0x0092
            boolean r1 = r3.expanded
            if (r1 == 0) goto L_0x0092
            com.android.systemui.statusbar.notification.collection.NotificationEntry r1 = r3.summary
            if (r1 == 0) goto L_0x008d
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r1 = r1.row
            if (r1 == 0) goto L_0x0087
            int r3 = r1.mClipTopAmount
            if (r3 > 0) goto L_0x0081
            float r1 = r1.getTranslationY()
            r3 = 0
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 >= 0) goto L_0x007f
            goto L_0x0081
        L_0x007f:
            r1 = r4
            goto L_0x0082
        L_0x0081:
            r1 = r2
        L_0x0082:
            if (r1 == 0) goto L_0x0085
            goto L_0x0087
        L_0x0085:
            r1 = r4
            goto L_0x0088
        L_0x0087:
            r1 = r2
        L_0x0088:
            if (r1 == 0) goto L_0x008b
            goto L_0x008d
        L_0x008b:
            r1 = r4
            goto L_0x008e
        L_0x008d:
            r1 = r2
        L_0x008e:
            if (r1 == 0) goto L_0x0091
            goto L_0x0092
        L_0x0091:
            r2 = r4
        L_0x0092:
            if (r2 == 0) goto L_0x00be
            if (r0 != 0) goto L_0x00d4
            android.service.notification.StatusBarNotification r0 = r7.mSbn
            r6.onEntryRemovedInternal(r7, r0)
            java.util.HashMap<java.lang.String, android.service.notification.StatusBarNotification> r0 = r6.mIsolatedEntries
            java.lang.String r1 = r7.mKey
            android.service.notification.StatusBarNotification r2 = r7.mSbn
            r0.put(r1, r2)
            r6.onEntryAddedInternal(r7)
            java.util.HashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$NotificationGroup> r0 = r6.mGroupMap
            android.service.notification.StatusBarNotification r7 = r7.mSbn
            java.lang.String r7 = r7.getGroupKey()
            java.lang.Object r7 = r0.get(r7)
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$NotificationGroup r7 = (com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy.NotificationGroup) r7
            r6.updateSuppression(r7)
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$GroupEventDispatcher r7 = r6.mEventDispatcher
            r7.notifyGroupsChanged()
            goto L_0x00d4
        L_0x00be:
            if (r0 == 0) goto L_0x00d4
            android.service.notification.StatusBarNotification r0 = r7.mSbn
            r6.onEntryRemovedInternal(r7, r0)
            java.util.HashMap<java.lang.String, android.service.notification.StatusBarNotification> r0 = r6.mIsolatedEntries
            java.lang.String r1 = r7.mKey
            r0.remove(r1)
            r6.onEntryAddedInternal(r7)
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$GroupEventDispatcher r7 = r6.mEventDispatcher
            r7.notifyGroupsChanged()
        L_0x00d4:
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$GroupEventDispatcher r6 = r6.mEventDispatcher
            r6.closeBufferScope()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy.updateIsolation(com.android.systemui.statusbar.notification.collection.NotificationEntry):void");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:72:0x01b2, code lost:
        if (r7 == false) goto L_0x01b6;
     */
    /* JADX WARNING: Removed duplicated region for block: B:110:? A[RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01bd  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x01bf  */
    /* JADX WARNING: Removed duplicated region for block: B:80:0x01c2  */
    /* JADX WARNING: Removed duplicated region for block: B:84:0x01c9  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x01d0  */
    /* JADX WARNING: Removed duplicated region for block: B:89:0x01d9  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateSuppression(com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy.NotificationGroup r18) {
        /*
            r17 = this;
            r0 = r17
            r1 = r18
            if (r1 != 0) goto L_0x0007
            return
        L_0x0007:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r2 = r1.alertOverride
            r3 = 0
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r1.summary
            r5 = 0
            r6 = 1
            if (r4 != 0) goto L_0x0012
            goto L_0x0142
        L_0x0012:
            java.lang.String r4 = r4.mKey
            boolean r4 = r0.isIsolated(r4)
            if (r4 == 0) goto L_0x001c
            goto L_0x0142
        L_0x001c:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r1.summary
            java.util.Objects.requireNonNull(r4)
            android.service.notification.StatusBarNotification r4 = r4.mSbn
            android.app.Notification r4 = r4.getNotification()
            int r4 = r4.getGroupAlertBehavior()
            r7 = 2
            if (r4 != r7) goto L_0x0030
            goto L_0x0142
        L_0x0030:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r1.summary
            java.util.Objects.requireNonNull(r4)
            android.service.notification.StatusBarNotification r4 = r4.mSbn
            java.lang.String r4 = r4.getGroupKey()
            java.util.HashMap<java.lang.String, android.service.notification.StatusBarNotification> r7 = r0.mIsolatedEntries
            java.util.Collection r7 = r7.values()
            java.util.Iterator r7 = r7.iterator()
            r8 = r3
        L_0x0046:
            boolean r9 = r7.hasNext()
            if (r9 == 0) goto L_0x008d
            java.lang.Object r9 = r7.next()
            android.service.notification.StatusBarNotification r9 = (android.service.notification.StatusBarNotification) r9
            java.lang.String r10 = r9.getGroupKey()
            boolean r10 = r10.equals(r4)
            if (r10 == 0) goto L_0x0046
            java.util.HashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$NotificationGroup> r10 = r0.mGroupMap
            java.lang.String r11 = r9.getKey()
            java.lang.Object r10 = r10.get(r11)
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$NotificationGroup r10 = (com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy.NotificationGroup) r10
            com.android.systemui.statusbar.notification.collection.NotificationEntry r10 = r10.summary
            dagger.Lazy<com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier> r11 = r0.mPeopleNotificationIdentifier
            java.lang.Object r11 = r11.get()
            com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier r11 = (com.android.systemui.statusbar.notification.people.PeopleNotificationIdentifier) r11
            int r11 = r11.getPeopleNotificationType(r10)
            r12 = 3
            if (r11 != r12) goto L_0x007b
            r11 = r6
            goto L_0x007c
        L_0x007b:
            r11 = r5
        L_0x007c:
            if (r11 == 0) goto L_0x0046
            if (r8 != 0) goto L_0x0085
            java.util.HashMap r8 = new java.util.HashMap
            r8.<init>()
        L_0x0085:
            java.lang.String r9 = r9.getKey()
            r8.put(r9, r10)
            goto L_0x0046
        L_0x008d:
            if (r8 == 0) goto L_0x0142
            boolean r4 = r8.isEmpty()
            if (r4 == 0) goto L_0x0097
            goto L_0x0142
        L_0x0097:
            java.util.HashSet r4 = new java.util.HashSet
            java.util.Set r7 = r8.keySet()
            r4.<init>(r7)
            java.util.HashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.NotificationEntry> r7 = r1.children
            r8.putAll(r7)
            java.util.Collection r7 = r8.values()
            java.util.Iterator r7 = r7.iterator()
        L_0x00ad:
            boolean r9 = r7.hasNext()
            if (r9 == 0) goto L_0x00ca
            java.lang.Object r9 = r7.next()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r9 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r9
            java.util.Objects.requireNonNull(r9)
            android.service.notification.StatusBarNotification r9 = r9.mSbn
            android.app.Notification r9 = r9.getNotification()
            int r9 = r9.getGroupAlertBehavior()
            if (r9 == r6) goto L_0x00ad
            goto L_0x0142
        L_0x00ca:
            java.util.TreeSet r7 = new java.util.TreeSet
            java.util.TreeSet<com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$PostRecord> r9 = r1.postBatchHistory
            r7.<init>(r9)
            java.util.Iterator r9 = r4.iterator()
        L_0x00d5:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x00ef
            java.lang.Object r10 = r9.next()
            java.lang.String r10 = (java.lang.String) r10
            java.util.HashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$NotificationGroup> r11 = r0.mGroupMap
            java.lang.Object r10 = r11.get(r10)
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$NotificationGroup r10 = (com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy.NotificationGroup) r10
            java.util.TreeSet<com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$PostRecord> r10 = r10.postBatchHistory
            r7.addAll(r10)
            goto L_0x00d5
        L_0x00ef:
            trimPostBatchHistory(r7)
            java.util.HashSet r9 = new java.util.HashSet
            r9.<init>()
            r10 = -1
            java.util.NavigableSet r7 = r7.descendingSet()
            java.util.Iterator r7 = r7.iterator()
            r12 = r3
        L_0x0102:
            boolean r13 = r7.hasNext()
            if (r13 == 0) goto L_0x0137
            java.lang.Object r13 = r7.next()
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$PostRecord r13 = (com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy.PostRecord) r13
            java.lang.String r14 = r13.key
            boolean r14 = r9.contains(r14)
            if (r14 == 0) goto L_0x0117
            goto L_0x0137
        L_0x0117:
            java.lang.String r14 = r13.key
            r9.add(r14)
            java.lang.String r13 = r13.key
            java.lang.Object r13 = r8.get(r13)
            com.android.systemui.statusbar.notification.collection.NotificationEntry r13 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r13
            if (r13 == 0) goto L_0x0102
            android.service.notification.StatusBarNotification r14 = r13.mSbn
            android.app.Notification r14 = r14.getNotification()
            long r14 = r14.when
            if (r12 == 0) goto L_0x0134
            int r16 = (r14 > r10 ? 1 : (r14 == r10 ? 0 : -1))
            if (r16 <= 0) goto L_0x0102
        L_0x0134:
            r12 = r13
            r10 = r14
            goto L_0x0102
        L_0x0137:
            if (r12 == 0) goto L_0x0142
            java.lang.String r7 = r12.mKey
            boolean r4 = r4.contains(r7)
            if (r4 == 0) goto L_0x0142
            r3 = r12
        L_0x0142:
            r1.alertOverride = r3
            java.util.HashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.NotificationEntry> r3 = r1.children
            java.util.Collection r3 = r3.values()
            java.util.Iterator r3 = r3.iterator()
            r4 = r5
            r7 = r4
        L_0x0150:
            boolean r8 = r3.hasNext()
            if (r8 == 0) goto L_0x0182
            java.lang.Object r8 = r3.next()
            com.android.systemui.statusbar.notification.collection.NotificationEntry r8 = (com.android.systemui.statusbar.notification.collection.NotificationEntry) r8
            java.util.Optional<com.android.wm.shell.bubbles.Bubbles> r9 = r0.mBubblesOptional
            boolean r9 = r9.isPresent()
            if (r9 == 0) goto L_0x017f
            java.util.Optional<com.android.wm.shell.bubbles.Bubbles> r9 = r0.mBubblesOptional
            java.lang.Object r9 = r9.get()
            com.android.wm.shell.bubbles.Bubbles r9 = (com.android.p012wm.shell.bubbles.Bubbles) r9
            java.util.Objects.requireNonNull(r8)
            java.lang.String r10 = r8.mKey
            android.service.notification.StatusBarNotification r8 = r8.mSbn
            java.lang.String r8 = r8.getGroupKey()
            boolean r8 = r9.isBubbleNotificationSuppressedFromShade(r10, r8)
            if (r8 == 0) goto L_0x017f
            r7 = r6
            goto L_0x0150
        L_0x017f:
            int r4 = r4 + 1
            goto L_0x0150
        L_0x0182:
            boolean r3 = r1.suppressed
            com.android.systemui.statusbar.notification.collection.NotificationEntry r8 = r1.summary
            if (r8 == 0) goto L_0x01b6
            boolean r9 = r1.expanded
            if (r9 != 0) goto L_0x01b6
            if (r4 == r6) goto L_0x01b4
            if (r4 != 0) goto L_0x01b6
            android.service.notification.StatusBarNotification r4 = r8.mSbn
            android.app.Notification r4 = r4.getNotification()
            boolean r4 = r4.isGroupSummary()
            if (r4 == 0) goto L_0x01b6
            com.android.systemui.statusbar.notification.collection.NotificationEntry r4 = r1.summary
            java.util.Objects.requireNonNull(r4)
            android.service.notification.StatusBarNotification r4 = r4.mSbn
            java.lang.String r4 = r4.getGroupKey()
            int r4 = r0.getNumberOfIsolatedChildren(r4)
            if (r4 == 0) goto L_0x01af
            r4 = r6
            goto L_0x01b0
        L_0x01af:
            r4 = r5
        L_0x01b0:
            if (r4 != 0) goto L_0x01b4
            if (r7 == 0) goto L_0x01b6
        L_0x01b4:
            r4 = r6
            goto L_0x01b7
        L_0x01b6:
            r4 = r5
        L_0x01b7:
            r1.suppressed = r4
            com.android.systemui.statusbar.notification.collection.NotificationEntry r7 = r1.alertOverride
            if (r2 == r7) goto L_0x01bf
            r7 = r6
            goto L_0x01c0
        L_0x01bf:
            r7 = r5
        L_0x01c0:
            if (r3 == r4) goto L_0x01c3
            r5 = r6
        L_0x01c3:
            if (r7 != 0) goto L_0x01c7
            if (r5 == 0) goto L_0x01de
        L_0x01c7:
            if (r7 == 0) goto L_0x01ce
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$GroupEventDispatcher r3 = r0.mEventDispatcher
            r3.notifyAlertOverrideChanged(r1, r2)
        L_0x01ce:
            if (r5 == 0) goto L_0x01d5
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$GroupEventDispatcher r2 = r0.mEventDispatcher
            r2.notifySuppressedChanged(r1)
        L_0x01d5:
            boolean r1 = r0.mIsUpdatingUnchangedGroup
            if (r1 != 0) goto L_0x01de
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$GroupEventDispatcher r0 = r0.mEventDispatcher
            r0.notifyGroupsChanged()
        L_0x01de:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy.updateSuppression(com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$NotificationGroup):void");
    }

    public NotificationGroupManagerLegacy(StatusBarStateController statusBarStateController, Lazy<PeopleNotificationIdentifier> lazy, Optional<Bubbles> optional, DumpManager dumpManager) {
        HashMap<String, NotificationGroup> hashMap = new HashMap<>();
        this.mGroupMap = hashMap;
        this.mEventDispatcher = new GroupEventDispatcher(new PeopleSpaceWidgetManager$$ExternalSyntheticLambda3(hashMap, 1));
        this.mIsolatedEntries = new HashMap<>();
        statusBarStateController.addCallback(this);
        this.mPeopleNotificationIdentifier = lazy;
        this.mBubblesOptional = optional;
        dumpManager.registerDumpable(this);
    }

    public static void trimPostBatchHistory(TreeSet treeSet) {
        if (treeSet.size() > 1) {
            long j = ((PostRecord) treeSet.last()).postTime - 5000;
            while (!treeSet.isEmpty() && ((PostRecord) treeSet.first()).postTime < j) {
                treeSet.pollFirst();
            }
        }
    }

    public final List<NotificationEntry> getChildren(ListEntry listEntry) {
        NotificationEntry representativeEntry = listEntry.getRepresentativeEntry();
        HashMap<String, NotificationGroup> hashMap = this.mGroupMap;
        Objects.requireNonNull(representativeEntry);
        NotificationGroup notificationGroup = hashMap.get(representativeEntry.mSbn.getGroupKey());
        if (notificationGroup == null) {
            return null;
        }
        return new ArrayList(notificationGroup.children.values());
    }

    public final String getGroupKey(StatusBarNotification statusBarNotification) {
        String key = statusBarNotification.getKey();
        String groupKey = statusBarNotification.getGroupKey();
        if (isIsolated(key)) {
            return key;
        }
        return groupKey;
    }

    public final NotificationEntry getGroupSummary(NotificationEntry notificationEntry) {
        Objects.requireNonNull(notificationEntry);
        NotificationGroup notificationGroup = this.mGroupMap.get(getGroupKey(notificationEntry.mSbn));
        if (notificationGroup == null) {
            return null;
        }
        return notificationGroup.summary;
    }

    public final NotificationEntry getLogicalGroupSummary(NotificationEntry notificationEntry) {
        Objects.requireNonNull(notificationEntry);
        NotificationGroup notificationGroup = this.mGroupMap.get(notificationEntry.mSbn.getGroupKey());
        if (notificationGroup == null) {
            return null;
        }
        return notificationGroup.summary;
    }

    public final boolean isChildInGroup(NotificationEntry notificationEntry) {
        NotificationGroup notificationGroup;
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        if (isGroupChild(statusBarNotification) && (notificationGroup = this.mGroupMap.get(getGroupKey(statusBarNotification))) != null && notificationGroup.summary != null && !notificationGroup.suppressed && !notificationGroup.children.isEmpty()) {
            return true;
        }
        return false;
    }

    public final boolean isGroupChild(StatusBarNotification statusBarNotification) {
        String key = statusBarNotification.getKey();
        boolean isGroup = statusBarNotification.isGroup();
        boolean isGroupSummary = statusBarNotification.getNotification().isGroupSummary();
        if (!isIsolated(key) && isGroup && !isGroupSummary) {
            return true;
        }
        return false;
    }

    public final boolean isGroupSummary(NotificationEntry notificationEntry) {
        boolean z;
        NotificationGroup notificationGroup;
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        if (isIsolated(statusBarNotification.getKey())) {
            z = true;
        } else {
            z = statusBarNotification.getNotification().isGroupSummary();
        }
        if (!z || (notificationGroup = this.mGroupMap.get(getGroupKey(statusBarNotification))) == null || notificationGroup.summary == null) {
            return false;
        }
        if (!notificationGroup.children.isEmpty()) {
            NotificationEntry notificationEntry2 = notificationGroup.summary;
            Objects.requireNonNull(notificationEntry2);
            if (Objects.equals(notificationEntry2.mSbn, statusBarNotification)) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x0037 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0038  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean isOnlyChildInGroup(com.android.systemui.statusbar.notification.collection.NotificationEntry r7) {
        /*
            r6 = this;
            java.util.Objects.requireNonNull(r7)
            android.service.notification.StatusBarNotification r0 = r7.mSbn
            android.app.Notification r1 = r0.getNotification()
            boolean r1 = r1.isGroupSummary()
            r2 = 1
            r3 = 0
            if (r1 != 0) goto L_0x0034
            java.lang.String r1 = r0.getGroupKey()
            int r1 = r6.getNumberOfIsolatedChildren(r1)
            java.util.HashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$NotificationGroup> r4 = r6.mGroupMap
            java.lang.String r5 = r0.getGroupKey()
            java.lang.Object r4 = r4.get(r5)
            com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy$NotificationGroup r4 = (com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy.NotificationGroup) r4
            if (r4 == 0) goto L_0x002e
            java.util.HashMap<java.lang.String, com.android.systemui.statusbar.notification.collection.NotificationEntry> r4 = r4.children
            int r4 = r4.size()
            goto L_0x002f
        L_0x002e:
            r4 = r3
        L_0x002f:
            int r1 = r1 + r4
            if (r1 != r2) goto L_0x0034
            r1 = r2
            goto L_0x0035
        L_0x0034:
            r1 = r3
        L_0x0035:
            if (r1 != 0) goto L_0x0038
            return r3
        L_0x0038:
            com.android.systemui.statusbar.notification.collection.NotificationEntry r6 = r6.getLogicalGroupSummary(r7)
            if (r6 == 0) goto L_0x0047
            android.service.notification.StatusBarNotification r6 = r6.mSbn
            boolean r6 = r6.equals(r0)
            if (r6 != 0) goto L_0x0047
            goto L_0x0048
        L_0x0047:
            r2 = r3
        L_0x0048:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy.isOnlyChildInGroup(com.android.systemui.statusbar.notification.collection.NotificationEntry):boolean");
    }

    public final boolean isSummaryOfSuppressedGroup(StatusBarNotification statusBarNotification) {
        boolean z;
        if (statusBarNotification.getNotification().isGroupSummary()) {
            NotificationGroup notificationGroup = this.mGroupMap.get(getGroupKey(statusBarNotification));
            if (notificationGroup == null || !notificationGroup.suppressed) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    public final void onEntryAddedInternal(NotificationEntry notificationEntry) {
        boolean z;
        String str;
        if (notificationEntry.isRowRemoved()) {
            notificationEntry.mDebugThrowable = new Throwable();
        }
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        boolean isGroupChild = isGroupChild(statusBarNotification);
        String groupKey = getGroupKey(statusBarNotification);
        NotificationGroup notificationGroup = this.mGroupMap.get(groupKey);
        if (notificationGroup == null) {
            notificationGroup = new NotificationGroup(groupKey);
            this.mGroupMap.put(groupKey, notificationGroup);
            GroupEventDispatcher groupEventDispatcher = this.mEventDispatcher;
            Objects.requireNonNull(groupEventDispatcher);
            String str2 = notificationGroup.groupKey;
            Iterator<OnGroupChangeListener> it = groupEventDispatcher.mGroupChangeListeners.iterator();
            while (it.hasNext()) {
                it.next().onGroupCreated(notificationGroup, str2);
            }
        }
        if (isGroupChild) {
            NotificationEntry notificationEntry2 = notificationGroup.children.get(notificationEntry.mKey);
            if (!(notificationEntry2 == null || notificationEntry2 == notificationEntry)) {
                Throwable th = notificationEntry2.mDebugThrowable;
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Inconsistent entries found with the same key ");
                m.append(R$array.logKey(notificationEntry.mKey));
                m.append("existing removed: ");
                m.append(notificationEntry2.isRowRemoved());
                if (th != null) {
                    str = Log.getStackTraceString(th) + "\n";
                } else {
                    str = "";
                }
                m.append(str);
                m.append(" added removed");
                m.append(notificationEntry.isRowRemoved());
                Log.wtf("NotifGroupManager", m.toString(), new Throwable());
            }
            notificationGroup.children.put(notificationEntry.mKey, notificationEntry);
            if (notificationGroup.postBatchHistory.add(new PostRecord(notificationEntry))) {
                trimPostBatchHistory(notificationGroup.postBatchHistory);
            }
            updateSuppression(notificationGroup);
            return;
        }
        notificationGroup.summary = notificationEntry;
        if (notificationGroup.postBatchHistory.add(new PostRecord(notificationEntry))) {
            trimPostBatchHistory(notificationGroup.postBatchHistory);
        }
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        if (expandableNotificationRow == null || !expandableNotificationRow.mChildrenExpanded) {
            z = false;
        } else {
            z = true;
        }
        notificationGroup.expanded = z;
        updateSuppression(notificationGroup);
        if (!notificationGroup.children.isEmpty()) {
            Iterator it2 = new ArrayList(notificationGroup.children.values()).iterator();
            while (it2.hasNext()) {
                updateIsolation((NotificationEntry) it2.next());
            }
            this.mEventDispatcher.notifyGroupsChanged();
        }
    }

    public final void onEntryRemovedInternal(NotificationEntry notificationEntry, String str, boolean z, boolean z2) {
        Objects.requireNonNull(notificationEntry);
        String str2 = notificationEntry.mKey;
        if (isIsolated(str2)) {
            str = str2;
        }
        NotificationGroup notificationGroup = this.mGroupMap.get(str);
        if (notificationGroup != null) {
            boolean z3 = false;
            if (!isIsolated(notificationEntry.mKey) && z && !z2) {
                z3 = true;
            }
            if (z3) {
                notificationGroup.children.remove(notificationEntry.mKey);
            } else {
                notificationGroup.summary = null;
            }
            updateSuppression(notificationGroup);
            if (notificationGroup.children.isEmpty() && notificationGroup.summary == null) {
                this.mGroupMap.remove(str);
                GroupEventDispatcher groupEventDispatcher = this.mEventDispatcher;
                Objects.requireNonNull(groupEventDispatcher);
                String str3 = notificationGroup.groupKey;
                Iterator<OnGroupChangeListener> it = groupEventDispatcher.mGroupChangeListeners.iterator();
                while (it.hasNext()) {
                    it.next().onGroupRemoved(str3);
                }
            }
        }
    }

    public final void setGroupExpanded(NotificationGroup notificationGroup, boolean z) {
        notificationGroup.expanded = z;
        if (notificationGroup.summary != null) {
            Iterator<GroupExpansionManager.OnGroupExpansionChangeListener> it = this.mExpansionChangeListeners.iterator();
            while (it.hasNext()) {
                NotificationEntry notificationEntry = notificationGroup.summary;
                Objects.requireNonNull(notificationEntry);
                it.next().onGroupExpansionChange(notificationEntry.row, z);
            }
        }
    }

    public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        updateIsolation(notificationEntry);
    }
}
