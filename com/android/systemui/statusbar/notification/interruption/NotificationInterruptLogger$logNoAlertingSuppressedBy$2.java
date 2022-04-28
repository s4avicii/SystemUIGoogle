package com.android.systemui.statusbar.notification.interruption;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
public final class NotificationInterruptLogger$logNoAlertingSuppressedBy$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logNoAlertingSuppressedBy$2 INSTANCE = new NotificationInterruptLogger$logNoAlertingSuppressedBy$2();

    public NotificationInterruptLogger$logNoAlertingSuppressedBy$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("No alerting: aborted by suppressor: ");
        m.append(logMessage.getStr2());
        m.append(" awake=");
        m.append(logMessage.getBool1());
        m.append(" sbnKey=");
        m.append(logMessage.getStr1());
        return m.toString();
    }
}
