package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
final class NotificationInterruptLogger$logNoAlertingFilteredOut$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logNoAlertingFilteredOut$2 INSTANCE = new NotificationInterruptLogger$logNoAlertingFilteredOut$2();

    public NotificationInterruptLogger$logNoAlertingFilteredOut$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("No alerting: filtered notification: ", ((LogMessage) obj).getStr1());
    }
}
