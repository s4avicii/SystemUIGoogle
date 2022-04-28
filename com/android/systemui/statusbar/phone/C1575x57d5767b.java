package com.android.systemui.statusbar.phone;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarterLogger$logSendingFullScreenIntent$2 */
/* compiled from: StatusBarNotificationActivityStarterLogger.kt */
final class C1575x57d5767b extends Lambda implements Function1<LogMessage, String> {
    public static final C1575x57d5767b INSTANCE = new C1575x57d5767b();

    public C1575x57d5767b() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Notification ");
        m.append(logMessage.getStr1());
        m.append(" has fullScreenIntent; sending fullScreenIntent ");
        m.append(logMessage.getStr2());
        return m.toString();
    }
}
