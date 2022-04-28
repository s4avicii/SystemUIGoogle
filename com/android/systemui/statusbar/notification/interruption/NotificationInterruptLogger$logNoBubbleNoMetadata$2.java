package com.android.systemui.statusbar.notification.interruption;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationInterruptLogger.kt */
final class NotificationInterruptLogger$logNoBubbleNoMetadata$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationInterruptLogger$logNoBubbleNoMetadata$2 INSTANCE = new NotificationInterruptLogger$logNoBubbleNoMetadata$2();

    public NotificationInterruptLogger$logNoBubbleNoMetadata$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("No bubble up: notification: ");
        m.append(((LogMessage) obj).getStr1());
        m.append(" doesn't have valid metadata");
        return m.toString();
    }
}
