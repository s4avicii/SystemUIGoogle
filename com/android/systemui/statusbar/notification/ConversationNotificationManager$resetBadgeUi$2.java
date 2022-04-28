package com.android.systemui.statusbar.notification;

import android.view.View;
import com.android.internal.widget.ConversationLayout;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: ConversationNotifications.kt */
public final class ConversationNotificationManager$resetBadgeUi$2 extends Lambda implements Function1<View, ConversationLayout> {
    public static final ConversationNotificationManager$resetBadgeUi$2 INSTANCE = new ConversationNotificationManager$resetBadgeUi$2();

    public ConversationNotificationManager$resetBadgeUi$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        ConversationLayout conversationLayout = (View) obj;
        if (conversationLayout instanceof ConversationLayout) {
            return conversationLayout;
        }
        return null;
    }
}
