package com.android.systemui.statusbar.notification;

import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.notification.ConversationNotificationManager$onNotificationPanelExpandStateChanged$2 */
/* compiled from: ConversationNotifications.kt */
public final class C1228x5e24d3c0 extends Lambda implements Function1<NotificationEntry, ExpandableNotificationRow> {
    public static final C1228x5e24d3c0 INSTANCE = new C1228x5e24d3c0();

    public C1228x5e24d3c0() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return ((NotificationEntry) obj).row;
    }
}
