package com.android.systemui.statusbar.notification.stack;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationStackScrollLogger.kt */
final class NotificationStackScrollLogger$hunSkippedForUnexpectedState$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationStackScrollLogger$hunSkippedForUnexpectedState$2 INSTANCE = new NotificationStackScrollLogger$hunSkippedForUnexpectedState$2();

    public NotificationStackScrollLogger$hunSkippedForUnexpectedState$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("HUN animation skipped for unexpected hun state: key: ");
        m.append(logMessage.getStr1());
        m.append(" expected: ");
        m.append(logMessage.getBool1());
        m.append(" actual: ");
        m.append(logMessage.getBool2());
        return m.toString();
    }
}
