package com.android.systemui.statusbar.notification;

import android.app.Notification;
import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Objects;
import java.util.function.BiFunction;

/* compiled from: ConversationNotifications.kt */
public final class ConversationNotificationManager$getUnreadCount$1<T, U, R> implements BiFunction {
    public final /* synthetic */ NotificationEntry $entry;
    public final /* synthetic */ Notification.Builder $recoveredBuilder;
    public final /* synthetic */ ConversationNotificationManager this$0;

    public ConversationNotificationManager$getUnreadCount$1(NotificationEntry notificationEntry, ConversationNotificationManager conversationNotificationManager, Notification.Builder builder) {
        this.$entry = notificationEntry;
        this.this$0 = conversationNotificationManager;
        this.$recoveredBuilder = builder;
    }

    public final Object apply(Object obj, Object obj2) {
        boolean z;
        String str = (String) obj;
        ConversationNotificationManager.ConversationState conversationState = (ConversationNotificationManager.ConversationState) obj2;
        int i = 1;
        if (conversationState != null) {
            ConversationNotificationManager conversationNotificationManager = this.this$0;
            Notification.Builder builder = this.$recoveredBuilder;
            Objects.requireNonNull(conversationNotificationManager);
            Notification notification = conversationState.notification;
            if ((notification.flags & 8) != 0) {
                z = false;
            } else {
                z = Notification.areStyledNotificationsVisiblyDifferent(Notification.Builder.recoverBuilder(conversationNotificationManager.context, notification), builder);
            }
            if (z) {
                i = 1 + conversationState.unreadCount;
            } else {
                i = conversationState.unreadCount;
            }
        }
        NotificationEntry notificationEntry = this.$entry;
        Objects.requireNonNull(notificationEntry);
        return new ConversationNotificationManager.ConversationState(i, notificationEntry.mSbn.getNotification());
    }
}
