package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.ConversationNotificationManager;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import java.util.Map;
import kotlin.Pair;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.notification.ConversationNotificationManager$onNotificationPanelExpandStateChanged$expanded$1 */
/* compiled from: ConversationNotifications.kt */
public final class C1229x7388b338 extends Lambda implements Function1<Map.Entry<? extends String, ? extends ConversationNotificationManager.ConversationState>, Pair<? extends String, ? extends NotificationEntry>> {
    public final /* synthetic */ ConversationNotificationManager this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1229x7388b338(ConversationNotificationManager conversationNotificationManager) {
        super(1);
        this.this$0 = conversationNotificationManager;
    }

    public final Object invoke(Object obj) {
        String str = (String) ((Map.Entry) obj).getKey();
        NotificationEntry entry = this.this$0.notifCollection.getEntry(str);
        if (entry == null) {
            return null;
        }
        ExpandableNotificationRow expandableNotificationRow = entry.row;
        boolean z = true;
        if (expandableNotificationRow == null || !expandableNotificationRow.isExpanded(false)) {
            z = false;
        }
        if (z) {
            return new Pair(str, entry);
        }
        return null;
    }
}
