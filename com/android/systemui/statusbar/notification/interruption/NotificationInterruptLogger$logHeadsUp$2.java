package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
final class NotificationInterruptLogger$logHeadsUp$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logHeadsUp$2 INSTANCE = new NotificationInterruptLogger$logHeadsUp$2();

    public NotificationInterruptLogger$logHeadsUp$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("Heads up: ", ((LogMessage) obj).getStr1());
    }
}
