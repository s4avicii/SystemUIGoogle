package com.android.systemui.statusbar.notification.interruption;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
final class NotificationInterruptLogger$logNoHeadsUpSuppressedBy$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logNoHeadsUpSuppressedBy$2 INSTANCE = new NotificationInterruptLogger$logNoHeadsUpSuppressedBy$2();

    public NotificationInterruptLogger$logNoHeadsUpSuppressedBy$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("No heads up: aborted by suppressor: ");
        m.append(logMessage.getStr2());
        m.append(" sbnKey=");
        m.append(logMessage.getStr1());
        return m.toString();
    }
}
