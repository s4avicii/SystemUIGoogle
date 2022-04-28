package com.android.systemui.statusbar.notification.stack;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: NotificationStackScrollLogger.kt */
final class NotificationStackScrollLogger$hunAnimationEventAdded$2 extends Lambda implements Function1<LogMessage, String> {
    public static final NotificationStackScrollLogger$hunAnimationEventAdded$2 INSTANCE = new NotificationStackScrollLogger$hunAnimationEventAdded$2();

    public NotificationStackScrollLogger$hunAnimationEventAdded$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("heads up animation added: ");
        m.append(logMessage.getStr1());
        m.append(" with type ");
        m.append(logMessage.getStr2());
        return m.toString();
    }
}
