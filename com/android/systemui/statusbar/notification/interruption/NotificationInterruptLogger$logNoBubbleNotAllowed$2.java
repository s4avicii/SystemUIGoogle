package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
final class NotificationInterruptLogger$logNoBubbleNotAllowed$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logNoBubbleNotAllowed$2 INSTANCE = new NotificationInterruptLogger$logNoBubbleNotAllowed$2();

    public NotificationInterruptLogger$logNoBubbleNotAllowed$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("No bubble up: not allowed to bubble: ", ((LogMessage) obj).getStr1());
    }
}
