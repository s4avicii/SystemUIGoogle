package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import java.util.Map;
import java.util.function.BiFunction;

/* renamed from: com.android.systemui.statusbar.notification.ConversationNotificationManager$onNotificationPanelExpandStateChanged$1 */
/* compiled from: ConversationNotifications.kt */
public final class C1227x5e24d3bf<T, U, R> implements BiFunction {
    public final /* synthetic */ Map<String, NotificationEntry> $expanded;

    public C1227x5e24d3bf(Map<String, NotificationEntry> map) {
        this.$expanded = map;
    }

    public final Object apply(Object obj, Object obj2) {
        ConversationNotificationManager.ConversationState conversationState = (ConversationNotificationManager.ConversationState) obj2;
        if (this.$expanded.containsKey((String) obj)) {
            return new ConversationNotificationManager.ConversationState(0, conversationState.notification);
        }
        return conversationState;
    }
}
