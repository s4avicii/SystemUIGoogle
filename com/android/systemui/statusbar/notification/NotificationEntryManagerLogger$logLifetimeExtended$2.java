package com.android.systemui.statusbar.notification;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationEntryManagerLogger.kt */
public final class NotificationEntryManagerLogger$logLifetimeExtended$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationEntryManagerLogger$logLifetimeExtended$2 INSTANCE = new NotificationEntryManagerLogger$logLifetimeExtended$2();

    public NotificationEntryManagerLogger$logLifetimeExtended$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("NOTIF LIFETIME EXTENDED ");
        m.append(logMessage.getStr1());
        m.append(" extender=");
        m.append(logMessage.getStr2());
        m.append(" status=");
        m.append(logMessage.getStr3());
        return m.toString();
    }
}
