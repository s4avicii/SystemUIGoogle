package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.NotificationChannel;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifPromoter;

/* compiled from: ConversationCoordinator.kt */
public final class ConversationCoordinator$notificationPromoter$1 extends NotifPromoter {
    public ConversationCoordinator$notificationPromoter$1() {
        super("ConversationCoordinator");
    }

    public final boolean shouldPromoteToTopLevel(NotificationEntry notificationEntry) {
        NotificationChannel channel = notificationEntry.getChannel();
        if (channel != null && channel.isImportantConversation()) {
            return true;
        }
        return false;
    }
}
