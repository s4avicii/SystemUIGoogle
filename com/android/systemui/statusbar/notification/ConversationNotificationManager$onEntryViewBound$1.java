package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.Objects;

/* compiled from: ConversationNotifications.kt */
public final class ConversationNotificationManager$onEntryViewBound$1 implements ExpandableNotificationRow.OnExpansionChangedListener {
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ ConversationNotificationManager this$0;

    public ConversationNotificationManager$onEntryViewBound$1(NotificationEntry notificationEntry, ConversationNotificationManager conversationNotificationManager) {
        this.$entry = notificationEntry;
        this.this$0 = conversationNotificationManager;
    }

    public final void onExpansionChanged(final boolean z) {
        NotificationEntry notificationEntry = this.$entry;
        Objects.requireNonNull(notificationEntry);
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        boolean z2 = true;
        if (expandableNotificationRow == null || !expandableNotificationRow.isShown()) {
            z2 = false;
        }
        if (!z2 || !z) {
            ConversationNotificationManager.onEntryViewBound$updateCount(this.this$0, this.$entry, z);
            return;
        }
        NotificationEntry notificationEntry2 = this.$entry;
        Objects.requireNonNull(notificationEntry2);
        ExpandableNotificationRow expandableNotificationRow2 = notificationEntry2.row;
        final ConversationNotificationManager conversationNotificationManager = this.this$0;
        final NotificationEntry notificationEntry3 = this.$entry;
        C12261 r1 = new Runnable() {
            public final void run() {
                ConversationNotificationManager.onEntryViewBound$updateCount(conversationNotificationManager, notificationEntry3, z);
            }
        };
        Objects.requireNonNull(expandableNotificationRow2);
        expandableNotificationRow2.mOnIntrinsicHeightReachedRunnable = r1;
        if (expandableNotificationRow2.mActualHeight == expandableNotificationRow2.getIntrinsicHeight()) {
            expandableNotificationRow2.mOnIntrinsicHeightReachedRunnable.run();
            expandableNotificationRow2.mOnIntrinsicHeightReachedRunnable = null;
        }
    }
}
