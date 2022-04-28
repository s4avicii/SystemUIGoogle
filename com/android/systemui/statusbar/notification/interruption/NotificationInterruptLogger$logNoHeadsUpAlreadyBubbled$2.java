package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
final class NotificationInterruptLogger$logNoHeadsUpAlreadyBubbled$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logNoHeadsUpAlreadyBubbled$2 INSTANCE = new NotificationInterruptLogger$logNoHeadsUpAlreadyBubbled$2();

    public NotificationInterruptLogger$logNoHeadsUpAlreadyBubbled$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("No heads up: in unlocked shade where notification is shown as a bubble: ", ((LogMessage) obj).getStr1());
    }
}
