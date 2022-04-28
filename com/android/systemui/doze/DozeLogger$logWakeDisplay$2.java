package com.android.systemui.doze;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: DozeLogger.kt */
final class DozeLogger$logWakeDisplay$2 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logWakeDisplay$2 INSTANCE = new DozeLogger$logWakeDisplay$2();

    public DozeLogger$logWakeDisplay$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Display wakefulness changed, isAwake=");
        m.append(logMessage.getBool1());
        m.append(", reason=");
        m.append(DozeLog.reasonToString(logMessage.getInt1()));
        return m.toString();
    }
}
