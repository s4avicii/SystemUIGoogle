package com.android.systemui.statusbar.phone;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logStartNotificationIntent$2 */
/* compiled from: StatusBarNotificationActivityStarterLogger.kt */
final class C1577x1850e613 extends Lambda implements Function1<LogMessage, String> {
    public static final C1577x1850e613 INSTANCE = new C1577x1850e613();

    public C1577x1850e613() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("(4/4) Starting ");
        m.append(logMessage.getStr2());
        m.append(" for notification ");
        m.append(logMessage.getStr1());
        return m.toString();
    }
}
