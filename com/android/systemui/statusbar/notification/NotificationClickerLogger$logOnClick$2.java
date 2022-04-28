package com.android.systemui.statusbar.notification;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationClickerLogger.kt */
final class NotificationClickerLogger$logOnClick$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationClickerLogger$logOnClick$2 INSTANCE = new NotificationClickerLogger$logOnClick$2();

    public NotificationClickerLogger$logOnClick$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("CLICK ");
        m.append(logMessage.getStr1());
        m.append(" (channel=");
        m.append(logMessage.getStr2());
        m.append(')');
        return m.toString();
    }
}
