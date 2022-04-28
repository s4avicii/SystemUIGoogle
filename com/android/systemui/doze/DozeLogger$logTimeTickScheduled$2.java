package com.android.systemui.doze;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import com.android.systemui.log.LogMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: DozeLogger.kt */
final class DozeLogger$logTimeTickScheduled$2 extends Lambda implements Function1<LogMessage, String> {
    public static final DozeLogger$logTimeTickScheduled$2 INSTANCE = new DozeLogger$logTimeTickScheduled$2();

    public DozeLogger$logTimeTickScheduled$2() {
        super(1);
    }

    public final Object invoke(Object obj) {
        LogMessage logMessage = (LogMessage) obj;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Time tick scheduledAt=");
        SimpleDateFormat simpleDateFormat = DozeLoggerKt.DATE_FORMAT;
        m.append(simpleDateFormat.format(new Date(logMessage.getLong1())));
        m.append(" triggerAt=");
        m.append(simpleDateFormat.format(new Date(logMessage.getLong2())));
        return m.toString();
    }
}
