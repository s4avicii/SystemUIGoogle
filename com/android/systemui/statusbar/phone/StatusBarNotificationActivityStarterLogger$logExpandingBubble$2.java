package com.android.systemui.statusbar.phone;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: StatusBarNotificationActivityStarterLogger.kt */
final class StatusBarNotificationActivityStarterLogger$logExpandingBubble$2 extends Lambda implements Function1<LogMessage, String> {
    public static final StatusBarNotificationActivityStarterLogger$logExpandingBubble$2 INSTANCE = new StatusBarNotificationActivityStarterLogger$logExpandingBubble$2();

    public StatusBarNotificationActivityStarterLogger$logExpandingBubble$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Expanding bubble for ");
        m.append(((LogMessage) obj).getStr1());
        m.append(" (rather than firing intent)");
        return m.toString();
    }
}
