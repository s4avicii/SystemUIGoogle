package com.android.systemui.statusbar.notification;

import android.view.View;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import java.util.Objects;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;
import kotlin.sequences.Sequence;

/* compiled from: ConversationNotifications.kt */
public final class ConversationNotificationManager$resetBadgeUi$1 extends Lambda implements Function1<NotificationContentView, Sequence<? extends View>> {
    public static final ConversationNotificationManager$resetBadgeUi$1 INSTANCE = new ConversationNotificationManager$resetBadgeUi$1();

    public ConversationNotificationManager$resetBadgeUi$1() {
        super(1);
    }

    public final Object invoke(Object obj) {
        NotificationContentView notificationContentView = (NotificationContentView) obj;
        Objects.requireNonNull(notificationContentView);
        return ArraysKt___ArraysKt.asSequence(new View[]{notificationContentView.mContractedChild, notificationContentView.mHeadsUpChild, notificationContentView.mExpandedChild, notificationContentView.mSingleLineView});
    }
}
