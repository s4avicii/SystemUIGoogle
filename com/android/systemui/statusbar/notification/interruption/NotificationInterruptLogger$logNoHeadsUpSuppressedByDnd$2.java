package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
final class NotificationInterruptLogger$logNoHeadsUpSuppressedByDnd$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logNoHeadsUpSuppressedByDnd$2 INSTANCE = new NotificationInterruptLogger$logNoHeadsUpSuppressedByDnd$2();

    public NotificationInterruptLogger$logNoHeadsUpSuppressedByDnd$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("No heads up: suppressed by DND: ", ((LogMessage) obj).getStr1());
    }
}
