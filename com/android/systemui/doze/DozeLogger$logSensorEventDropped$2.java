package com.android.systemui.doze;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: DozeLogger.kt */
final class DozeLogger$logSensorEventDropped$2 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logSensorEventDropped$2 INSTANCE = new DozeLogger$logSensorEventDropped$2();

    public DozeLogger$logSensorEventDropped$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("SensorEvent [");
        m.append(logMessage.getInt1());
        m.append("] dropped, reason=");
        m.append(logMessage.getStr1());
        return m.toString();
    }
}
