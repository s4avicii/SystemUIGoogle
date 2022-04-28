package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.notification.ConversationNotificationManager$updateNotificationRanking$activeConversationEntries$1 */
/* compiled from: ConversationNotifications.kt */
final class C1230x2916e3cd extends Lambda implements Function1<String, NotificationEntry> {
    public final /* synthetic */ ConversationNotificationManager this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1230x2916e3cd(ConversationNotificationManager conversationNotificationManager) {
        super(1);
        this.this$0 = conversationNotificationManager;
    }

    public final Object invoke(Object obj) {
        return this.this$0.notifCollection.getEntry((String) obj);
    }
}
