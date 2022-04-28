package com.android.systemui.statusbar.notification;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationClickerLogger.kt */
final class NotificationClickerLogger$logChildrenExpanded$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationClickerLogger$logChildrenExpanded$2 INSTANCE = new NotificationClickerLogger$logChildrenExpanded$2();

    public NotificationClickerLogger$logChildrenExpanded$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Ignoring click on ");
        m.append(((LogMessage) obj).getStr1());
        m.append("; children are expanded");
        return m.toString();
    }
}
