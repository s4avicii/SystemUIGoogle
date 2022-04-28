package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
final class NotificationInterruptLogger$logWillDismissAll$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logWillDismissAll$2 INSTANCE = new NotificationInterruptLogger$logWillDismissAll$2();

    public NotificationInterruptLogger$logWillDismissAll$2() {
        super(1);
    }

    public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        return "dismissing any existing heads up notification on disable event";
    }
}
