package com.android.systemui.statusbar.notification;

import android.app.Notification;
import android.content.pm.LauncherApps;
import android.content.pm.ShortcutInfo;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ConversationNotifications.kt */
public final class ConversationNotificationProcessor {
    public final ConversationNotificationManager conversationNotificationManager;
    public final LauncherApps launcherApps;

    public ConversationNotificationProcessor(LauncherApps launcherApps2, ConversationNotificationManager conversationNotificationManager2) {
        this.launcherApps = launcherApps2;
        this.conversationNotificationManager = conversationNotificationManager2;
    }

    public final void processNotification(NotificationEntry notificationEntry, Notification.Builder builder) {
        Notification.MessagingStyle messagingStyle;
        int i;
        Notification.Style style = builder.getStyle();
        if (style instanceof Notification.MessagingStyle) {
            messagingStyle = (Notification.MessagingStyle) style;
        } else {
            messagingStyle = null;
        }
        if (messagingStyle != null) {
            if (notificationEntry.mRanking.getChannel().isImportantConversation()) {
                i = 2;
            } else {
                i = 1;
            }
            messagingStyle.setConversationType(i);
            ShortcutInfo conversationShortcutInfo = notificationEntry.mRanking.getConversationShortcutInfo();
            if (conversationShortcutInfo != null) {
                messagingStyle.setShortcutIcon(this.launcherApps.getShortcutIcon(conversationShortcutInfo));
                CharSequence label = conversationShortcutInfo.getLabel();
                if (label != null) {
                    messagingStyle.setConversationTitle(label);
                }
            }
            ConversationNotificationManager conversationNotificationManager2 = this.conversationNotificationManager;
            Objects.requireNonNull(conversationNotificationManager2);
            ConversationNotificationManager.ConversationState compute = conversationNotificationManager2.states.compute(notificationEntry.mKey, new ConversationNotificationManager$getUnreadCount$1(notificationEntry, conversationNotificationManager2, builder));
            Intrinsics.checkNotNull(compute);
            messagingStyle.setUnreadMessageCount(compute.unreadCount);
        }
    }
}
