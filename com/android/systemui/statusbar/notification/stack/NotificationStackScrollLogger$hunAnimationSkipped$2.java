package com.android.systemui.statusbar.notification.stack;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationStackScrollLogger.kt */
final class NotificationStackScrollLogger$hunAnimationSkipped$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationStackScrollLogger$hunAnimationSkipped$2 INSTANCE = new NotificationStackScrollLogger$hunAnimationSkipped$2();

    public NotificationStackScrollLogger$hunAnimationSkipped$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("heads up animation skipped: key: ");
        m.append(logMessage.getStr1());
        m.append(" reason: ");
        m.append(logMessage.getStr2());
        return m.toString();
    }
}
