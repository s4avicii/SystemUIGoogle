package com.android.systemui.doze;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: DozeLogger.kt */
final class DozeLogger$logFling$2 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logFling$2 INSTANCE = new DozeLogger$logFling$2();

    public DozeLogger$logFling$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Fling expand=");
        m.append(logMessage.getBool1());
        m.append(" aboveThreshold=");
        m.append(logMessage.getBool2());
        m.append(" thresholdNeeded=");
        m.append(logMessage.getBool3());
        m.append(" screenOnFromTouch=");
        m.append(logMessage.getBool4());
        return m.toString();
    }
}
