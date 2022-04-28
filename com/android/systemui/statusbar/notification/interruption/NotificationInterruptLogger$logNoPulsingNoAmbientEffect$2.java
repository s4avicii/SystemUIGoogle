package com.android.systemui.statusbar.notification.interruption;

import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
final class NotificationInterruptLogger$logNoPulsingNoAmbientEffect$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logNoPulsingNoAmbientEffect$2 INSTANCE = new NotificationInterruptLogger$logNoPulsingNoAmbientEffect$2();

    public NotificationInterruptLogger$logNoPulsingNoAmbientEffect$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        return Intrinsics.stringPlus("No pulsing: ambient effect suppressed: ", ((LogMessage) obj).getStr1());
    }
}
