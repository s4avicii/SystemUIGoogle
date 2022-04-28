package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
final class NotificationInterruptLogger$logNoAlertingGroupAlertBehavior$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logNoAlertingGroupAlertBehavior$2 INSTANCE = new NotificationInterruptLogger$logNoAlertingGroupAlertBehavior$2();

    public NotificationInterruptLogger$logNoAlertingGroupAlertBehavior$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("No alerting: suppressed due to group alert behavior: ", ((LogMessage) obj).getStr1());
    }
}
