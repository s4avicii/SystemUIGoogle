package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
final class NotificationInterruptLogger$logNoHeadsUpNotInUse$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logNoHeadsUpNotInUse$2 INSTANCE = new NotificationInterruptLogger$logNoHeadsUpNotInUse$2();

    public NotificationInterruptLogger$logNoHeadsUpNotInUse$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("No heads up: not in use: ", ((LogMessage) obj).getStr1());
    }
}
