package com.android.systemui.statusbar.notification;

import com.android.internal.widget.ConversationLayout;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ConversationNotifications.kt */
final class ConversationNotificationManager$updateNotificationRanking$3 extends Lambda implements Function1<ConversationLayout, Boolean> {
    public final /* synthetic */ boolean $important;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public ConversationNotificationManager$updateNotificationRanking$3(boolean z) {
        super(1);
        this.$important = z;
    }

    public final Object invoke(Object obj) {
        boolean z;
        if (((ConversationLayout) obj).isImportantConversation() == this.$important) {
            z = true;
        } else {
            z = false;
        }
        return Boolean.valueOf(z);
    }
}
