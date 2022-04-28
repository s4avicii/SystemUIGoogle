package com.android.systemui.statusbar.phone;

import android.os.SystemClock;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.notification.NotificationEntryListener;
import com.android.systemui.statusbar.notification.NotificationEntryManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.legacy.NotificationGroupManagerLegacy;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.RowContentBindParams;
import com.android.systemui.statusbar.notification.row.RowContentBindStage;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class NotificationGroupAlertTransferHelper implements OnHeadsUpChangedListener, StatusBarStateController.StateListener {
    public NotificationEntryManager mEntryManager;
    public final ArrayMap<String, GroupAlertEntry> mGroupAlertEntries = new ArrayMap<>();
    public final NotificationGroupManagerLegacy mGroupManager;
    public HeadsUpManager mHeadsUpManager;
    public boolean mIsDozing;
    public final C14652 mNotificationEntryListener = new NotificationEntryListener() {
        public final void onEntryRemoved(NotificationEntry notificationEntry, boolean z) {
            NotificationGroupAlertTransferHelper.this.mPendingAlerts.remove(notificationEntry.mKey);
        }

        public final void onPendingEntryAdded(NotificationEntry notificationEntry) {
            GroupAlertEntry groupAlertEntry = NotificationGroupAlertTransferHelper.this.mGroupAlertEntries.get(NotificationGroupAlertTransferHelper.this.mGroupManager.getGroupKey(notificationEntry.mSbn));
            if (groupAlertEntry != null && groupAlertEntry.mGroup.alertOverride == null) {
                NotificationGroupAlertTransferHelper.this.checkShouldTransferBack(groupAlertEntry);
            }
        }
    };
    public final C14641 mOnGroupChangeListener = new NotificationGroupManagerLegacy.OnGroupChangeListener() {
        public final void onGroupAlertOverrideChanged(NotificationGroupManagerLegacy.NotificationGroup notificationGroup, NotificationEntry notificationEntry) {
            NotificationGroupAlertTransferHelper.m238$$Nest$monGroupChanged(NotificationGroupAlertTransferHelper.this, notificationGroup, notificationEntry);
        }

        public final void onGroupCreated(NotificationGroupManagerLegacy.NotificationGroup notificationGroup, String str) {
            NotificationGroupAlertTransferHelper.this.mGroupAlertEntries.put(str, new GroupAlertEntry(notificationGroup));
        }

        public final void onGroupRemoved(String str) {
            NotificationGroupAlertTransferHelper.this.mGroupAlertEntries.remove(str);
        }

        public final void onGroupSuppressionChanged(NotificationGroupManagerLegacy.NotificationGroup notificationGroup, boolean z) {
            NotificationGroupAlertTransferHelper.m238$$Nest$monGroupChanged(NotificationGroupAlertTransferHelper.this, notificationGroup, notificationGroup.alertOverride);
        }
    };
    public final ArrayMap<String, PendingAlertInfo> mPendingAlerts = new ArrayMap<>();
    public final RowContentBindStage mRowContentBindStage;

    public final void onStateChanged(int i) {
    }

    public final boolean releaseChildAlerts(ArrayList arrayList) {
        boolean z = false;
        for (int i = 0; i < arrayList.size(); i++) {
            NotificationEntry notificationEntry = (NotificationEntry) arrayList.get(i);
            if (onlySummaryAlerts(notificationEntry) && this.mHeadsUpManager.isAlerting(notificationEntry.mKey)) {
                this.mHeadsUpManager.removeNotification(notificationEntry.mKey, true);
                z = true;
            }
            if (this.mPendingAlerts.containsKey(notificationEntry.mKey)) {
                this.mPendingAlerts.get(notificationEntry.mKey).mAbortOnInflation = true;
                z = true;
            }
        }
        return z;
    }

    public static class GroupAlertEntry {
        public boolean mAlertSummaryOnNextAddition;
        public final NotificationGroupManagerLegacy.NotificationGroup mGroup;
        public long mLastAlertTransferTime;

        public GroupAlertEntry(NotificationGroupManagerLegacy.NotificationGroup notificationGroup) {
            this.mGroup = notificationGroup;
        }
    }

    public class PendingAlertInfo {
        public boolean mAbortOnInflation;
        public final NotificationEntry mEntry;
        public final StatusBarNotification mOriginalNotification;

        public PendingAlertInfo(NotificationEntry notificationEntry) {
            Objects.requireNonNull(notificationEntry);
            this.mOriginalNotification = notificationEntry.mSbn;
            this.mEntry = notificationEntry;
        }
    }

    public final void alertNotificationWhenPossible(NotificationEntry notificationEntry) {
        Objects.requireNonNull(this.mHeadsUpManager);
        RowContentBindParams rowContentBindParams = (RowContentBindParams) this.mRowContentBindStage.getStageParams(notificationEntry);
        if ((rowContentBindParams.mContentViews & 4) == 0) {
            ArrayMap<String, PendingAlertInfo> arrayMap = this.mPendingAlerts;
            Objects.requireNonNull(notificationEntry);
            arrayMap.put(notificationEntry.mKey, new PendingAlertInfo(notificationEntry));
            rowContentBindParams.requireContentViews(4);
            this.mRowContentBindStage.requestRebind(notificationEntry, new NotificationGroupAlertTransferHelper$$ExternalSyntheticLambda0(this, notificationEntry));
            return;
        }
        HeadsUpManager headsUpManager = this.mHeadsUpManager;
        Objects.requireNonNull(notificationEntry);
        if (headsUpManager.isAlerting(notificationEntry.mKey)) {
            this.mHeadsUpManager.updateNotification(notificationEntry.mKey, true);
        } else {
            this.mHeadsUpManager.showNotification(notificationEntry);
        }
    }

    public final void checkForForwardAlertTransfer(NotificationEntry notificationEntry, NotificationEntry notificationEntry2) {
        NotificationGroupManagerLegacy.NotificationGroup notificationGroup;
        boolean z;
        NotificationGroupManagerLegacy.NotificationGroup notificationGroup2;
        ArrayList<NotificationEntry> logicalChildren;
        NotificationGroupManagerLegacy notificationGroupManagerLegacy = this.mGroupManager;
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        Objects.requireNonNull(notificationGroupManagerLegacy);
        if (statusBarNotification.getNotification().isGroupSummary()) {
            notificationGroup = notificationGroupManagerLegacy.mGroupMap.get(notificationGroupManagerLegacy.getGroupKey(statusBarNotification));
        } else {
            notificationGroup = null;
        }
        if (notificationGroup != null && notificationGroup.alertOverride != null) {
            GroupAlertEntry groupAlertEntry = this.mGroupAlertEntries.get(this.mGroupManager.getGroupKey(notificationEntry.mSbn));
            NotificationGroupManagerLegacy notificationGroupManagerLegacy2 = this.mGroupManager;
            StatusBarNotification statusBarNotification2 = notificationEntry.mSbn;
            Objects.requireNonNull(notificationGroupManagerLegacy2);
            if (statusBarNotification2.getNotification().isGroupSummary()) {
                notificationGroup2 = notificationGroupManagerLegacy2.mGroupMap.get(notificationGroupManagerLegacy2.getGroupKey(statusBarNotification2));
            } else {
                notificationGroup2 = null;
            }
            if (notificationGroup2 != null && notificationGroup2.alertOverride != null && groupAlertEntry != null) {
                if (this.mHeadsUpManager.isAlerting(notificationEntry.mKey)) {
                    tryTransferAlertState(notificationEntry, notificationEntry, notificationGroup2.alertOverride, groupAlertEntry);
                } else if (canStillTransferBack(groupAlertEntry) && (logicalChildren = this.mGroupManager.getLogicalChildren(notificationEntry.mSbn)) != null) {
                    logicalChildren.remove(notificationGroup2.alertOverride);
                    if (releaseChildAlerts(logicalChildren)) {
                        tryTransferAlertState(notificationEntry, (NotificationEntry) null, notificationGroup2.alertOverride, groupAlertEntry);
                    }
                }
            }
        } else if (this.mGroupManager.isSummaryOfSuppressedGroup(notificationEntry.mSbn)) {
            GroupAlertEntry groupAlertEntry2 = this.mGroupAlertEntries.get(this.mGroupManager.getGroupKey(notificationEntry.mSbn));
            if (this.mGroupManager.isSummaryOfSuppressedGroup(notificationEntry.mSbn) && groupAlertEntry2 != null) {
                boolean isAlerting = this.mHeadsUpManager.isAlerting(notificationEntry.mKey);
                boolean z2 = true;
                if (notificationEntry2 == null || !this.mHeadsUpManager.isAlerting(notificationEntry2.mKey)) {
                    z = false;
                } else {
                    z = true;
                }
                if (isAlerting || z) {
                    NotificationGroupManagerLegacy.NotificationGroup notificationGroup3 = groupAlertEntry2.mGroup;
                    NotificationEntryManager notificationEntryManager = this.mEntryManager;
                    if (notificationEntryManager != null) {
                        notificationEntryManager.mNotifPipelineFlags.checkLegacyPipelineEnabled();
                        Iterator<T> it = notificationEntryManager.mPendingNotifications.values().iterator();
                        while (true) {
                            if (it.hasNext()) {
                                if (isPendingNotificationInGroup((NotificationEntry) it.next(), notificationGroup3)) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    }
                    z2 = false;
                    if (!z2) {
                        NotificationEntry next = this.mGroupManager.getLogicalChildren(notificationEntry.mSbn).iterator().next();
                        if (isAlerting) {
                            tryTransferAlertState(notificationEntry, notificationEntry, next, groupAlertEntry2);
                        } else if (canStillTransferBack(groupAlertEntry2)) {
                            tryTransferAlertState(notificationEntry, notificationEntry2, next, groupAlertEntry2);
                        }
                    }
                }
            }
        }
    }

    public final boolean isPendingNotificationInGroup(NotificationEntry notificationEntry, NotificationGroupManagerLegacy.NotificationGroup notificationGroup) {
        NotificationGroupManagerLegacy notificationGroupManagerLegacy = this.mGroupManager;
        NotificationEntry notificationEntry2 = notificationGroup.summary;
        Objects.requireNonNull(notificationEntry2);
        String groupKey = notificationGroupManagerLegacy.getGroupKey(notificationEntry2.mSbn);
        NotificationGroupManagerLegacy notificationGroupManagerLegacy2 = this.mGroupManager;
        Objects.requireNonNull(notificationEntry);
        if (!notificationGroupManagerLegacy2.isGroupChild(notificationEntry.mSbn) || !Objects.equals(this.mGroupManager.getGroupKey(notificationEntry.mSbn), groupKey) || notificationGroup.children.containsKey(notificationEntry.mKey)) {
            return false;
        }
        return true;
    }

    public final void onDozingChanged(boolean z) {
        if (this.mIsDozing != z) {
            for (GroupAlertEntry next : this.mGroupAlertEntries.values()) {
                next.mLastAlertTransferTime = 0;
                next.mAlertSummaryOnNextAddition = false;
            }
        }
        this.mIsDozing = z;
    }

    public final void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
        if (z && notificationEntry.mSbn.getNotification().isGroupSummary()) {
            checkForForwardAlertTransfer(notificationEntry, (NotificationEntry) null);
        }
    }

    public final void tryTransferAlertState(NotificationEntry notificationEntry, NotificationEntry notificationEntry2, NotificationEntry notificationEntry3, GroupAlertEntry groupAlertEntry) {
        if (notificationEntry3 != null) {
            ExpandableNotificationRow expandableNotificationRow = notificationEntry3.row;
            Objects.requireNonNull(expandableNotificationRow);
            if (!expandableNotificationRow.mKeepInParent && !notificationEntry3.isRowRemoved() && !notificationEntry3.isRowDismissed()) {
                if (!this.mHeadsUpManager.isAlerting(notificationEntry3.mKey) && onlySummaryAlerts(notificationEntry)) {
                    groupAlertEntry.mLastAlertTransferTime = SystemClock.elapsedRealtime();
                }
                if (notificationEntry2 != null) {
                    this.mHeadsUpManager.removeNotification(notificationEntry2.mKey, true);
                }
                alertNotificationWhenPossible(notificationEntry3);
            }
        }
    }

    /* renamed from: -$$Nest$monGroupChanged  reason: not valid java name */
    public static void m238$$Nest$monGroupChanged(NotificationGroupAlertTransferHelper notificationGroupAlertTransferHelper, NotificationGroupManagerLegacy.NotificationGroup notificationGroup, NotificationEntry notificationEntry) {
        Objects.requireNonNull(notificationGroupAlertTransferHelper);
        NotificationEntry notificationEntry2 = notificationGroup.summary;
        if (notificationEntry2 != null) {
            if (notificationGroup.suppressed || notificationGroup.alertOverride != null) {
                notificationGroupAlertTransferHelper.checkForForwardAlertTransfer(notificationEntry2, notificationEntry);
                return;
            }
            GroupAlertEntry groupAlertEntry = notificationGroupAlertTransferHelper.mGroupAlertEntries.get(notificationGroupAlertTransferHelper.mGroupManager.getGroupKey(notificationEntry2.mSbn));
            if (groupAlertEntry.mAlertSummaryOnNextAddition) {
                HeadsUpManager headsUpManager = notificationGroupAlertTransferHelper.mHeadsUpManager;
                NotificationEntry notificationEntry3 = notificationGroup.summary;
                Objects.requireNonNull(notificationEntry3);
                if (!headsUpManager.isAlerting(notificationEntry3.mKey)) {
                    notificationGroupAlertTransferHelper.alertNotificationWhenPossible(notificationGroup.summary);
                }
                groupAlertEntry.mAlertSummaryOnNextAddition = false;
                return;
            }
            notificationGroupAlertTransferHelper.checkShouldTransferBack(groupAlertEntry);
        }
    }

    public NotificationGroupAlertTransferHelper(RowContentBindStage rowContentBindStage, StatusBarStateController statusBarStateController, NotificationGroupManagerLegacy notificationGroupManagerLegacy) {
        this.mRowContentBindStage = rowContentBindStage;
        this.mGroupManager = notificationGroupManagerLegacy;
        statusBarStateController.addCallback(this);
    }

    public static boolean canStillTransferBack(GroupAlertEntry groupAlertEntry) {
        if (SystemClock.elapsedRealtime() - groupAlertEntry.mLastAlertTransferTime < 300) {
            return true;
        }
        return false;
    }

    public static boolean onlySummaryAlerts(NotificationEntry notificationEntry) {
        Objects.requireNonNull(notificationEntry);
        if (notificationEntry.mSbn.getNotification().getGroupAlertBehavior() == 1) {
            return true;
        }
        return false;
    }

    public final void checkShouldTransferBack(GroupAlertEntry groupAlertEntry) {
        int i;
        if (canStillTransferBack(groupAlertEntry)) {
            NotificationEntry notificationEntry = groupAlertEntry.mGroup.summary;
            if (onlySummaryAlerts(notificationEntry)) {
                ArrayList<NotificationEntry> logicalChildren = this.mGroupManager.getLogicalChildren(notificationEntry.mSbn);
                int size = logicalChildren.size();
                NotificationGroupManagerLegacy.NotificationGroup notificationGroup = groupAlertEntry.mGroup;
                NotificationEntryManager notificationEntryManager = this.mEntryManager;
                boolean z = false;
                if (notificationEntryManager == null) {
                    i = 0;
                } else {
                    notificationEntryManager.mNotifPipelineFlags.checkLegacyPipelineEnabled();
                    i = 0;
                    for (NotificationEntry notificationEntry2 : notificationEntryManager.mPendingNotifications.values()) {
                        if (isPendingNotificationInGroup(notificationEntry2, notificationGroup) && onlySummaryAlerts(notificationEntry2)) {
                            i++;
                        }
                    }
                }
                if (i + size > 1 && releaseChildAlerts(logicalChildren) && !this.mHeadsUpManager.isAlerting(notificationEntry.mKey)) {
                    if (size > 1) {
                        z = true;
                    }
                    if (z) {
                        alertNotificationWhenPossible(notificationEntry);
                    } else {
                        groupAlertEntry.mAlertSummaryOnNextAddition = true;
                    }
                    groupAlertEntry.mLastAlertTransferTime = 0;
                }
            }
        }
    }
}
