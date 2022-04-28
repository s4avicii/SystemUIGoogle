package com.android.systemui.statusbar.policy;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: HeadsUpManagerLogger.kt */
final class HeadsUpManagerLogger$logNotificationActuallyRemoved$2 extends Lambda implements Function1<LogMessage, String> {
    public static final HeadsUpManagerLogger$logNotificationActuallyRemoved$2 INSTANCE = new HeadsUpManagerLogger$logNotificationActuallyRemoved$2();

    public HeadsUpManagerLogger$logNotificationActuallyRemoved$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("notification removed ");
        m.append(((LogMessage) obj).getStr1());
        m.append(' ');
        return m.toString();
    }
}
