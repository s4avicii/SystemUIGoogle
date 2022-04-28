package com.android.systemui.statusbar.notification;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationEntryManagerLogger.kt */
final class NotificationEntryManagerLogger$logNotifInflated$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationEntryManagerLogger$logNotifInflated$2 INSTANCE = new NotificationEntryManagerLogger$logNotifInflated$2();

    public NotificationEntryManagerLogger$logNotifInflated$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("NOTIF INFLATED ");
        m.append(logMessage.getStr1());
        m.append(" isNew=");
        m.append(logMessage.getBool1());
        m.append('}');
        return m.toString();
    }
}
